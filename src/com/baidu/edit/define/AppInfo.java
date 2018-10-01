package com.baidu.edit.define;

import java.util.HashMap;
import java.util.Map;

public final class AppInfo
{
  public static final int SUCCESS = 0;
  public static final int MAX_SIZE = 1;
  public static final int PERMISSION_DENIED = 2;
  public static final int FAILED_CREATE_FILE = 3;
  public static final int IO_ERROR = 4;
  public static final int NOT_MULTIPART_CONTENT = 5;
  public static final int PARSE_REQUEST_ERROR = 6;
  public static final int NOTFOUND_UPLOAD_DATA = 7;
  public static final int NOT_ALLOW_FILE_TYPE = 8;
  public static final int INVALID_ACTION = 101;
  public static final int CONFIG_ERROR = 102;
  public static final int PREVENT_HOST = 201;
  public static final int CONNECTION_ERROR = 202;
  public static final int REMOTE_FAIL = 203;
  public static final int NOT_DIRECTORY = 301;
  public static final int NOT_EXIST = 302;
  public static final int ILLEGAL = 401;
  public static Map<Integer, String> info = new HashMap(){};
  
  static {
	  info.put(0, "SUCCESS");
	  info.put(1, "MAX_SIZE");
	  info.put(2, "PERMISSION_DENIED");
	  info.put(3, "FAILED_CREATE_FILE");
	  info.put(4, "IO_ERROR");
	  info.put(5, "NOT_MULTIPART_CONTENT");
	  info.put(6, "PARSE_REQUEST_ERROR");
	  info.put(7, "NOTFOUND_UPLOAD_DATA");
	  info.put(8, "NOT_ALLOW_FILE_TYPE");
	  info.put(101, "INVALID_ACTION");
	  info.put(102, "CONFIG_ERROR");
	  info.put(201, "PREVENT_HOST");
	  info.put(202, "CONNECTION_ERROR");
	  info.put(203, "REMOTE_FAIL");
	  info.put(301, "NOT_DIRECTORY");
	  info.put(302, "NOT_EXIST");
	  info.put(401, "ILLEGAL");
  }

  public static String getStateInfo(int key)
  {
    return (String)info.get(Integer.valueOf(key));
  }
}