package cn.gov.nlc.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpsUtil {

	private static final String FTP_ADDRESS = PropertiesUtils.getPropertyValue("FTP_ADDRESS");
	private static final String FTP_PORT = PropertiesUtils.getPropertyValue("FTP_PORT");
	private static final String FTP_USER_NAME = PropertiesUtils.getPropertyValue("FTP_USER_NAME");
	private static final String FTP_PASSWORD = PropertiesUtils.getPropertyValue("FTP_PASSWORD");
	private static final String FTP_ATTACH_BASE_PATH = PropertiesUtils.getPropertyValue("FTP_ATTACH_BASE_PATH");
	
	public static boolean uploadFile(String filename, InputStream input) {
		boolean result = false;
		int port = Integer.parseInt(FTP_PORT);
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(FTP_ADDRESS, port);
			ftp.login(FTP_USER_NAME, FTP_PASSWORD);
			ftp.enterLocalPassiveMode();
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			
			if(!ftp.changeWorkingDirectory(FTP_ATTACH_BASE_PATH)) {
				if (!ftp.makeDirectory(FTP_ATTACH_BASE_PATH)) {
					return result;
				} else {
					ftp.changeWorkingDirectory(FTP_ATTACH_BASE_PATH);
				}
			}
			
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			if (!ftp.storeFile(filename, input)) {
				return result;
			}
			input.close();
			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}
}
