package cn.gov.nlc.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

import sun.misc.BASE64Encoder;

public class FileUtils {
	/**
	 * 下载文件时，针对不同浏览器，进行附件名的编码
	 * 
	 * @param filename
	 *            下载文件名
	 * @param agent
	 *            客户端浏览器
	 * @return 编码后的下载附件名
	 * @throws IOException
	 */
	public static String encodeDownloadFilename(String filename, String agent) throws IOException {
		if (agent.contains("Firefox")) { // 火狐浏览器
			filename = "=?UTF-8?B?" + new BASE64Encoder().encode(filename.getBytes("utf-8")) + "?=";
			filename = filename.replaceAll("\r\n", "");
		} else { // IE及其他浏览器
			filename = URLEncoder.encode(filename, "utf-8");
			filename = filename.replace("+", " ");
		}
		return filename;
	}

	// 得到真正的文件名
	public static String getRealName(String filename) {
		int index = filename.lastIndexOf("\\");

		return filename.substring(index + 1);
	}

	// 得到一个文件的随机名字
	public static String getRandomName(String filename) {
		int index = filename.lastIndexOf(".");

		// 当没有后缀时
		if (index == -1) {
			return UUID.randomUUID().toString();
		}

		return UUID.randomUUID() + filename.substring(index);

	}

	public static String getRandomNameM(String filename) {
		int index = filename.lastIndexOf(".");

		// 当没有后缀时
		if (index == -1) {
			return new Long(System.currentTimeMillis()).toString();
		}

		return System.currentTimeMillis() + filename.substring(index);
	}

}
