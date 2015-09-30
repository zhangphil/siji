package com.wlqq.huodi.task;

import android.os.AsyncTask;

import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.http.HttpClientFactory;
import com.wlqq.huodi.utils.HostProvider;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by caitiancai on 14-7-11.
 */
public class DownloadHostsTask extends AsyncTask<String, String, Void> {

	@Override
	protected Void doInBackground(String... objects) {

		String url = objects[0];
		String filename = objects[1];
		if ((url != null && !"".equals(url)) && (filename != null && !"".equals(filename))) {
			final HttpClient httpClient = HttpClientFactory.createHttpClient();
			final HttpGet request = new HttpGet(url);


			FileOutputStream outStream = null;
			try {
				final HttpResponse httpResponse = httpClient.execute(request);
				final InputStream input = httpResponse.getEntity().getContent();
				final File file = new File(HuoDiApplication.getContext().getFilesDir().getPath(), filename);
				outStream = new FileOutputStream(file, false);
				outStream.write("".getBytes());
				int temp = 0;
				byte[] data = new byte[1024];
				while ((temp = input.read(data)) != -1) {
					outStream.write(data, 0, temp);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {

					HostProvider.readFile(HuoDiApplication.getContext().getFilesDir().getPath().concat("/").concat(filename));
					if (outStream != null) {
						outStream.flush();
						outStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return null;
	}
}
