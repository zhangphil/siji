package com.wlqq.huodi.http;

import com.wlqq.huodi.bean.JsonResponse;
import com.wlqq.huodi.bean.Session;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.encrypt.DESUtils;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.exception.ServerInternalException;
import com.wlqq.huodi.exception.WuliuQQException;
import com.wlqq.huodi.json.Parser;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.LogUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * @author Tiger Tang
 * @since 110612
 * Date: 11-12-29
 */
public class ServiceInvoker {

	private static final String TAG = "ServiceInvoker";

	public static synchronized <T> JsonResponse<T> invoke(String host, final String serviceUrl, final Map<String, Object> params, final Parser<T> parser, CustomMultiPartEntity.ProgressListener progressListener, boolean isEncrypt) throws IOException, ServerInternalException, JSONException, WuliuQQException {

		String url = "";
		boolean isLoginUrl = false;
		Map<String, Object> tempParams = new HashMap<String, Object>();
		final HttpClient httpClient = HttpClientFactory.createHttpClient();
		if (isEncrypt) {
			if (serviceUrl.equals(HuoDiConstants.API_URL_DRIVIER_TAUTH_SIGN_IN) || serviceUrl.equals(HuoDiConstants.API_DRIVER_URL_LOGIN) || serviceUrl.equals(HuoDiConstants.API_REGISTER) || serviceUrl.equals(HuoDiConstants.API_GET_SMS_CODE) || serviceUrl.equals(HuoDiConstants.API_CHECK_PLN_IS_EXIST)) {
				isLoginUrl = true;
			}
			url = new StringBuilder(host).append(HuoDiConstants.API_URL_DISPATCH).toString();

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(serviceUrl).append("?");
			for (String key : params.keySet()) {
				stringBuilder.append(key).append("=").append(params.get(key)).append("&");
			}
			final String encryptApiParams = stringBuilder.substring(0, stringBuilder.length() - 1);

			LogUtils.i(TAG, "remote send data is" + encryptApiParams);
			LogUtils.i(TAG, "url is " + url);
			try {
				final Session session = AuthenticationHolder.getSession();
				String encryptContentString = "";
				if (isLoginUrl) {
					encryptContentString = DESUtils.encrypt(encryptApiParams, DESUtils.SID_OF_NO_SESSION, DESUtils.TOKEN_OF_NO_SESSION);
				} else {
					if (session != null) {
						encryptContentString = DESUtils.encrypt(encryptApiParams, session.getId(), session.getToken());
					}
				}
				tempParams.put("content", encryptContentString);
			} catch (Exception e) {
				LogUtils.i(TAG, e.toString());
			}
		} else {
			url = new StringBuilder(host).append(serviceUrl).toString();
			tempParams = params;
			LogUtils.i(TAG, "remote send data is" + String.format("%s", params));
		}

		final HttpPost httpPost = HttpPostFactory.createHttpPost(url, tempParams, progressListener);

		final HttpResponse httpResponse = httpClient.execute(httpPost);
		final int statusCode = httpResponse.getStatusLine().getStatusCode();
		LogUtils.i(TAG, "statusCode = " + statusCode);
		// if status code is greater or equals 400, which means ERROR
		if (statusCode >= HuoDiConstants.HTTP_STATUS_BAD_REQUEST) {
			// call this to consume the response for re-use http client
			EntityUtils.toString(httpResponse.getEntity());
			throw new ServerInternalException();
		}
		HttpEntity entity = httpResponse.getEntity();
		String encryptResponse;
		String content = "";
		if (isSupportGzip(httpResponse)) {
			InputStream is = new GZIPInputStream(entity.getContent());
			Reader reader = new InputStreamReader(is, EntityUtils.getContentCharSet(entity));
			CharArrayBuffer buffer = new CharArrayBuffer(1);
			try {
				char[] tmp = new char[1024];
				int l;
				while ((l = reader.read(tmp)) != -1) {
					buffer.append(tmp, 0, l);
				}
			} finally {
				reader.close();
			}
			encryptResponse = buffer.toString();
		} else {
			encryptResponse = IOUtils.toString(httpResponse.getEntity().getContent());
		}

		if (isEncrypt) {
			if (!encryptResponse.equals(HuoDiConstants.ERROR)) {

				//first decrypt
				try {
					final String firstDecrypt = DESUtils.doDecrypt(encryptResponse, DESUtils.PUBLIC_KEY);
					final String[] split = firstDecrypt.split("\\|");

					long sid = new Long(split[1]);
					String token;
					if (sid == -1) {
						token = DESUtils.TOKEN_OF_NO_SESSION;
					} else {
						final Session session = AuthenticationHolder.getSession();
						token = session.getToken();
					}
					String cipherTextForSecondDecrypt = split[0];
					content = DESUtils.doDecrypt(cipherTextForSecondDecrypt, token);

				} catch (Exception e) {
					LogUtils.e(TAG, e.toString());
				}
			} else {
				throw new ServerInternalException();
			}
		} else {
			content = encryptResponse;
		}

		JSONObject json = new JSONObject(content);

		LogUtils.i(TAG, String.format("remote service response: %s", content));
		final String status = json.getString(HuoDiConstants.JSON_RESPONSE_STATUS);
		LogUtils.i("status", status);
		if (HuoDiConstants.JSON_RESPONSE_STATUS_OK.equalsIgnoreCase(status)) {
			return new JsonResponse<T>(status, parser.parse(json.optString("content")));
		} else if (HuoDiConstants.JSON_RESPONSE_STATUS_ERROR.equalsIgnoreCase(status)) {

			String errorCode = json.optString("errorCode");
			if (StringUtils.isNotBlank(errorCode)) {
				ErrorCode.UNKNOWN_ERROR.setCode(errorCode);
				String errorMsg = json.optString("errorMsg");
				if (StringUtils.isNotBlank(errorMsg)) {
					ErrorCode.UNKNOWN_ERROR.setMessage(errorMsg);
				} else {
					ErrorCode.UNKNOWN_ERROR.setCode("-1");
					ErrorCode.UNKNOWN_ERROR.setMessage(ErrorCode.fromCode(errorCode).getMessage());
					throw new WuliuQQException(ErrorCode.UNKNOWN_ERROR);
				}
				throw new WuliuQQException(ErrorCode.fromCode(errorCode));
			} else {
				throw new WuliuQQException(ErrorCode.fromCode(json.optString("content")));
			}
		} else {
			throw new ServerInternalException();
		}
	}


	public static boolean isSupportGzip(HttpResponse response) {
		Header contentEncoding = response.getFirstHeader("Content-Encoding");

		return contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip");
	}
}
