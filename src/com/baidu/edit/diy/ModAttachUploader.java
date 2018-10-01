package com.baidu.edit.diy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;
import com.baidu.edit.define.BaseState;
import com.baidu.edit.define.State;
import cn.gov.nlc.util.FtpUtil;
import cn.gov.nlc.util.PropertiesUtils;
import cn.gov.nlc.util.UUIDGenerator;

public class ModAttachUploader {
	 public static final State save(HttpServletRequest request, Map<String, Object> conf)
	  {
	    FileItemStream fileStream = null;
	    boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

	    if (!ServletFileUpload.isMultipartContent(request)) {
	      return new BaseState(false, 5);
	    }

	    ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

	    if (isAjaxUpload) {
	      upload.setHeaderEncoding("UTF-8");
	    }
	    try
	    {
	      FileItemIterator iterator = upload.getItemIterator(request);

	      while (iterator.hasNext()) {
	        fileStream = iterator.next();

	        if (!fileStream.isFormField())
	          break;
	        fileStream = null;
	      }

	      if (fileStream == null) {
	        return new BaseState(false, 7);
	      }
	      
	      String originFileName = fileStream.getName();		//原文件名，要返回  33.txt
	      String suffix = originFileName.substring(originFileName.lastIndexOf(".")).toLowerCase();	//取文件扩展名
		  String imageName = UUIDGenerator.getUUID() + suffix;	//生成新文件名
		  DateTime dateTime = new DateTime();
		 // String filePath = dateTime.toString("/yyyy/MM/dd");	//文件在图片服务器的存放路径，应该使用日期分隔的目录结构
		  String filePath = "";
		  
	      long maxSize = ((Long)conf.get("maxSize")).longValue();	
	      if (!validType(suffix, (String[])conf.get("allowFiles"))) {
	        return new BaseState(false, 8);
	      }

	      InputStream is = fileStream.openStream();
	      State storageState = saveFileByInputStream(is, filePath, imageName, maxSize);
	      is.close();

	      if (storageState.isSuccess()) {
	        storageState.putInfo("url", filePath + "/" + imageName);
	        storageState.putInfo("type", suffix);
	        storageState.putInfo("original", originFileName);
	      }

	      return storageState;
	    } catch (FileUploadException e) {
	      return new BaseState(false, 6);
	    } catch (IOException localIOException) {
	    }
	    return new BaseState(false, 4);
	  }

	  private static boolean validType(String type, String[] allowTypes) {
	    List list = Arrays.asList(allowTypes);
	    return list.contains(type);
	  }
	  
	  public static State saveFileByInputStream(InputStream is, String path, String imageName, long maxSize) {
		  State state = saveTmpFile(is, path, imageName);
		  
		  if(state.isSuccess()) {
	    	  state.putInfo("title", imageName);
	      }
		  return state;
	  }
	  
	  private static State saveTmpFile(InputStream is, String path, String imageName) {
		  String ftpaddress = PropertiesUtils.getPropertyValue("FTP_ADDRESS");
		  String ftpport = PropertiesUtils.getPropertyValue("FTP_PORT");
		  String ftpusername = PropertiesUtils.getPropertyValue("FTP_USER_NAME");
		  String ftppassword = PropertiesUtils.getPropertyValue("FTP_PASSWORD");
		  String ftpattachbasepath = PropertiesUtils.getPropertyValue("FTP_ATTACH_BASE_PATH");
		  boolean flag = true;
		  try {
				flag = FtpUtil.uploadFile(ftpaddress, Integer.parseInt(ftpport), ftpusername, ftppassword, ftpattachbasepath, path, imageName, is);
			} catch (Exception e) {
				e.printStackTrace();
				return new BaseState(false, 4);
			}
		  
		  if(flag) {
			  return new BaseState(true);
		  }else {
			  return new BaseState(false, 4);
		  }
	  }
}
