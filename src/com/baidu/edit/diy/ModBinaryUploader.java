package com.baidu.edit.diy;

import java.io.File;
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
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import com.baidu.edit.define.BaseState;
import com.baidu.edit.define.State;
import cn.gov.nlc.util.FtpUtil;
import cn.gov.nlc.util.PropertiesUtils;
import cn.gov.nlc.util.UUIDGenerator;

public class ModBinaryUploader {
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
	      
	      String originFileName = fileStream.getName();		//原文件名，要返回  22.jpg
	      String suffix = originFileName.substring(originFileName.lastIndexOf(".")).toLowerCase();	//取文件扩展名
		  String imageName = UUIDGenerator.getUUID() + suffix;	//生成新文件名
		  DateTime dateTime = new DateTime();
		 // String filePath = dateTime.toString("/yyyy/MM/dd");	//文件在图片服务器的存放路径，应该使用日期分隔的目录结构
		  String filePath = "";
		  
	     /* String savePath = (String)conf.get("savePath");	//没用 /ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}
	    //String suffix = FileType.getSuffixByFilename(originFileName);	//后缀 .jpg
	      originFileName = originFileName.substring(0, originFileName.length() - suffix.length());	//不含后缀的文件名 22
	      savePath = savePath + suffix;  ///ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}.jpg
*/
	      
	      long maxSize = ((Long)conf.get("maxSize")).longValue();	//2048000
	      if (!validType(suffix, (String[])conf.get("allowFiles"))) {
	        return new BaseState(false, 8);
	      }

	    /*  savePath = PathFormat.parse(savePath, originFileName);  ///ueditor/jsp/upload/image/20160903/1472872347277014148.jpg
	      String physicalPath = (String)conf.get("rootPath") + savePath;	//D:/develop/tomcat7/apache-tomcat-7.0.64/webapps/nlcms//ueditor/jsp/upload/image/20160903/1472872347277014148.jpg
*/
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
		  
		 /* State state = null;
		  File tmpFile = getTmpFile();
		  byte[] dataBuf = new byte[2048];
		  BufferedInputStream bis = new BufferedInputStream(is, 8192);
		  try{
		      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile), 8192);
		      int count = 0;
		      while ((count = bis.read(dataBuf)) != -1) {
		        bos.write(dataBuf, 0, count);
		      }
		      bos.flush();
		      bos.close();

		      if (tmpFile.length() > maxSize) {
		        tmpFile.delete();
		        return new BaseState(false, 1);
		      }

		      state = saveTmpFile(is, path, imageName);
		      if(state.isSuccess()) {
		    	  state.putInfo("size", tmpFile.length());
		    	  state.putInfo("title", imageName);
		      }
		      tmpFile.delete();
		      return state;
		    }catch (IOException localIOException) {
		    }
		    return new BaseState(false, 4);*/
	  }
	  
	  private static File getTmpFile() {
		  File tmpDir = FileUtils.getTempDirectory();
		  String tmpFileName = (Math.random() * 10000.0D + "").replace(".", "");
		  return new File(tmpDir, tmpFileName);
	  }
	  
	  private static State saveTmpFile(InputStream is, String path, String imageName) {
		  String ftpaddress = PropertiesUtils.getPropertyValue("FTP_ADDRESS");
		  String ftpport = PropertiesUtils.getPropertyValue("FTP_PORT");
		  String ftpusername = PropertiesUtils.getPropertyValue("FTP_USER_NAME");
		  String ftppassword = PropertiesUtils.getPropertyValue("FTP_PASSWORD");
		  String ftpimgbasepath = PropertiesUtils.getPropertyValue("FTP_IMG_BASE_PATH");
		  boolean flag = true;
		  try {
				flag = FtpUtil.uploadFile(ftpaddress, Integer.parseInt(ftpport), ftpusername, ftppassword, ftpimgbasepath, path, imageName, is);
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
