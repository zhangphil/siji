package com.wlqq.huodi.utils;


import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.encrypt.DESUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by caitiancai on 14-7-8.
 */
public class HostProvider {
	private static Map<String, List<String>> hostDomainMap = new HashMap<String, List<String>>();
	private static Map<String, List<String>> backupHostDomainMap = new HashMap<String, List<String>>();

	private static List<String> hostList = new ArrayList<String>();
	private static List<String> ssoList = new ArrayList<String>();
	private static List<String> advList = new ArrayList<String>();
	private static List<String> fisList = new ArrayList<String>();
	private static List<String> locList = new ArrayList<String>();
	private static List<String> pushList = new ArrayList<String>();
	private static List<String> icsList = new ArrayList<String>();

	private static List<String> hostIpList = new ArrayList<String>();
	private static List<String> ssoIpList = new ArrayList<String>();
	private static List<String> advIpList = new ArrayList<String>();
	private static List<String> fisIpList = new ArrayList<String>();
	private static List<String> locIpList = new ArrayList<String>();
	private static List<String> pushIpList = new ArrayList<String>();
	private static List<String> icsIpList = new ArrayList<String>();

	static {


//        hostList.add("http://192.168.1.204:8080");
        hostList.add("http://192.168.1.105:8080");
//        hostList.add("http://123.57.39.230");
//        hostList.add("http://192.168.1.204:8087");
        hostIpList.add("http://123.57.39.230");

//        ssoList.add("http://sso.56qq.cn");
//        ssoList.add("http://182.138.101.207:8086");
        ssoList.add("http://192.168.1.204:550");
//        ssoList.add("http://192.168.1.105:8080");
        ssoIpList.add("http://sso.56qq.cn");

        advList.add("http://123.57.39.230");
        advIpList.add("http://ad.56qq.cn");

        fisList.add("http://123.57.39.230");
        fisIpList.add("http://fis.56qq.cn");

        locList.add("http://192.168.1.105:8580");
//        locList.add("http://loc.56qq.cn");
        locIpList.add("http://loc.56qq.cn");

        pushList.add("http://123.57.39.230");
        pushIpList.add("http://push.56qq.cn");

        icsList.add("http://123.57.39.230");
        icsIpList.add("http://ics.56qq.cn");

//		advList.add("http://192.168.1.204:8880");
//        advIpList.add("http://ad.56qq.cn");
//
//        fisList.add("http://192.168.1.204:8380");
//        fisIpList.add("http://fis.56qq.cn");
//
//        locList.add("http://192.168.1.204:8580");
//        locIpList.add("http://loc.56qq.cn");
//
//        pushList.add("http://192.168.1.204:8787");
//        pushIpList.add("http://push.56qq.cn");
//
//        icsList.add("http://192.168.1.204:8480");
//        icsIpList.add("http://ics.56qq.cn");

//		hostList.add("http://www.56qq.cn");
//		hostIpList.add("http://www.56qq.cn");
//
//		ssoList.add("http://sso.56qq.cn");
//		ssoIpList.add("http://sso.56qq.cn");
//
//		advList.add("http://ad.56qq.cn");
//		advIpList.add("http://ad.56qq.cn");
//
//		fisList.add("http://fis.56qq.cn");
//		fisIpList.add("http://fis.56qq.cn");
//
//		locList.add("http://loc.56qq.cn");
//		locIpList.add("http://loc.56qq.cn");
//
//		pushList.add("http://push.56qq.cn");
//		pushIpList.add("http://push.56qq.cn");
//
//		icsList.add("http://ics.56qq.cn");
//		icsIpList.add("http://ics.56qq.cn");

		hostDomainMap.put(HostType.SSO.name(), ssoList);
		hostDomainMap.put(HostType.ADV.name(), advList);
		hostDomainMap.put(HostType.FIS.name(), fisList);
		hostDomainMap.put(HostType.ICS.name(), locList);
		hostDomainMap.put(HostType.LOC.name(), locList);
		hostDomainMap.put(HostType.HOST.name(), hostList);
		hostDomainMap.put(HostType.PUSH.name(), pushList);


		backupHostDomainMap.put(HostType.SSO_IP.name(), ssoIpList);
		backupHostDomainMap.put(HostType.ADV_IP.name(), advIpList);
		backupHostDomainMap.put(HostType.FIS_IP.name(), fisIpList);
		backupHostDomainMap.put(HostType.ICS_IP.name(), icsList);
		backupHostDomainMap.put(HostType.LOC_IP.name(), locIpList);
		backupHostDomainMap.put(HostType.HOST_IP.name(), hostIpList);
		backupHostDomainMap.put(HostType.PUSH_IP.name(), pushIpList);

		try {
			readFile(HuoDiApplication.getContext().getFilesDir().getPath().concat("/").concat(HuoDiApplication.getContext().getString(R.string.host_file_name)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public enum HostType {

		HOST,
		SSO,
		ADV,
		FIS,
		ICS,
		LOC,
		PUSH,
		HOST_IP,
		SSO_IP,
		ADV_IP,
		FIS_IP,
		ICS_IP,
		LOC_IP,
		PUSH_IP

	}


	public static String readFile(String fileName) throws IOException {
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(fileName);

			int length = fin.available();

			byte[] buffer = new byte[length];
			fin.read(buffer);

//			res = EncodingUtils.getString(buffer, "UTF-8");
			if (StringUtils.isNotBlank(res)) {
				res = DESUtils.doDecrypt(res, DESUtils.DOWNLOAD_HOSTS_KEY);
				res = RegexUtils.replaceBlank(res);
				String[] split2 = res.split("#");

				String[] split = split2[0].split(";");
				if (split.length > 0) {
					hostDomainMap.clear();
					for (String s : split) {
						String[] split1 = s.split("=");
						hostDomainMap.put(split1[0], Arrays.asList(split1[1].split(",")));
					}
				}

				String[] strings = split2[1].split(";");
				if (strings.length > 0) {
					backupHostDomainMap.clear();
					for (String s : strings) {
						String[] split1 = s.split("=");
						backupHostDomainMap.put(split1[0], Arrays.asList(split1[1].split(",")));
					}
				}
			}

			LogUtils.i(HostProvider.class.getSimpleName(), String.format(" hostDomain is %s \n" +
					"hostIp is %s", hostDomainMap, backupHostDomainMap));
			fin.close();
		} catch (Exception e) {
			LogUtils.e(HostProvider.class.getSimpleName(), e.toString());
		}
		return res;
	}

	public static String getHostDomain(HostType hostType, boolean isGetIp) {

		switch (hostType) {
			case HOST: {
				if (isGetIp) {
					String name = HostType.HOST_IP.name();
					if (backupHostDomainMap.containsKey(name)) {
						List<String> list = backupHostDomainMap.get(name);
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = backupHostDomainMap.get(name).get(i);
							return s;
						}
					}
				} else {
					if (hostDomainMap.containsKey(HostType.HOST.name())) {
						List<String> list = hostDomainMap.get(HostType.HOST.name());
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = hostDomainMap.get(HostType.HOST.name()).get(i);
							return s;
						}
					}
				}
				break;
			}
			case SSO: {
				if (isGetIp) {
					if (backupHostDomainMap.containsKey(HostType.SSO_IP.name())) {
						List<String> list = backupHostDomainMap.get(HostType.SSO_IP.name());
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = backupHostDomainMap.get(HostType.SSO_IP.name()).get(i);
							return s;
						}
					}
				} else {
					if (hostDomainMap.containsKey(HostType.SSO.name())) {
						List<String> list = hostDomainMap.get(HostType.SSO.name());
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = hostDomainMap.get(HostType.SSO.name()).get(i);
							return s;
						}
					}
				}
				break;
			}

			case ADV: {
				if (isGetIp) {
					if (backupHostDomainMap.containsKey(HostType.ADV_IP.name())) {
						List<String> list = backupHostDomainMap.get(HostType.ADV_IP.name());
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = backupHostDomainMap.get(HostType.ADV_IP.name()).get(i);
							return s;
						}
					}
				} else {
					String name = HostType.ADV.name();
					if (hostDomainMap.containsKey(name)) {
						List<String> list = hostDomainMap.get(name);
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = hostDomainMap.get(name).get(i);
							return s;
						}
					}
				}
				break;
			}

			case LOC: {
				if (isGetIp) {
					String name = HostType.LOC_IP.name();
					if (backupHostDomainMap.containsKey(name)) {
						List<String> list = backupHostDomainMap.get(name);
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = backupHostDomainMap.get(name).get(i);
							return s;
						}
					}
				} else {
					String name = HostType.LOC.name();
					if (hostDomainMap.containsKey(name)) {
						List<String> list = hostDomainMap.get(name);
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = hostDomainMap.get(name).get(i);
							return s;
						}
					}
				}
				break;
			}

			case FIS: {
				if (isGetIp) {
					String name = HostType.FIS_IP.name();
					if (backupHostDomainMap.containsKey(name)) {
						List<String> list = backupHostDomainMap.get(name);
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = backupHostDomainMap.get(name).get(i);
							return s;
						}
					}
				} else {
					String name = HostType.FIS.name();
					if (hostDomainMap.containsKey(name)) {
						List<String> list = hostDomainMap.get(name);
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = hostDomainMap.get(name).get(i);
							return s;
						}
					}
				}
				break;
			}

			case ICS: {
				if (isGetIp) {
					if (backupHostDomainMap.containsKey(HostType.ICS_IP.name())) {
						List<String> list = backupHostDomainMap.get(HostType.ICS_IP.name());
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = icsIpList.get(i);
							return s;
						}
					}
				} else {
					if (hostDomainMap.containsKey(HostType.ICS.name())) {
						List<String> list = hostDomainMap.get(HostType.ICS.name());
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = icsList.get(i);
							return s;
						}
					}
				}
				break;
			}

			case PUSH: {
				if (isGetIp) {
					if (backupHostDomainMap.containsKey(HostType.PUSH_IP.name())) {
						List<String> list = backupHostDomainMap.get(HostType.PUSH_IP.name());
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = backupHostDomainMap.get(HostType.PUSH_IP.name()).get(i);
							return s;
						}
					}
				} else {
					if (hostDomainMap.containsKey(HostType.PUSH.name())) {
						List<String> list = hostDomainMap.get(HostType.PUSH.name());
						if (!list.isEmpty()) {
							int i = getRandom(0, list.size());
							String s = hostDomainMap.get(HostType.PUSH.name()).get(i);
							return s;
						}
					}
				}
				break;
			}

			default:
				List<String> list = hostDomainMap.get(HostType.HOST.name());
				if (!list.isEmpty()) {
					int i = getRandom(0, list.size());
					String s = hostDomainMap.get(HostType.HOST.name()).get(i);
					return s;
				}
		}
		return "http://www.56qq.cn";
	}


	public static int getRandom(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}
}
