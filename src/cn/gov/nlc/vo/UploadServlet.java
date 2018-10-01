package cn.gov.nlc.vo;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import cn.gov.nlc.util.FileUtils;
import cn.gov.nlc.util.FtpsUtil;
import cn.gov.nlc.util.PropertiesUtils;

public class UploadServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.vo.UploadServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		upload.setSizeMax(1024 * 1024 * 120);
		boolean sflag = ServletFileUpload.isMultipartContent(request);

		if (sflag) {
			try {
				List<FileItem> list = upload.parseRequest(request);
				if(null != list && list.size() > 0) {
					for (FileItem item : list) {
						boolean flag = item.isFormField();
						if (!flag) {
							String filename = item.getName();
							String realname = FileUtils.getRealName(filename);
							String randomFileName = FileUtils.getRandomNameM(realname);
							boolean resBoolean = FtpsUtil.uploadFile(randomFileName, item.getInputStream());
							if(resBoolean) {
								String attchshow = PropertiesUtils.getPropertyValue("ATTACH_SHOW");
								String withUrlNewUpFilename = attchshow + "/" + randomFileName;
								response.getWriter().print("{result:true, filename:\"" + withUrlNewUpFilename + "\"}");
								return ;
							}
						} 
					}
				}

			} catch (FileUploadException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		response.getWriter().print("{result:false}");
	}
}
