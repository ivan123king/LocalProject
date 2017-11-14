package com.lw.blog.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringEscapeUtils;
import org.dom4j.Document;

import com.lw.utils.ConstFile;
import com.lw.utils.HtmlUtil;
import com.lw.utils.OtherUtil;
import com.lw.utils.WordUtil;

/**
 * Servlet implementation class UploadWordServlet
 * 上传word文档的
 */
@WebServlet("/blog/uploadword.action")
public class UploadWordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static boolean isInitPath = false;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadWordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//D:\own_workspace\Blog\WebContent
		if(!isInitPath){
			ConstFile.PIC_PATH = request.getSession().getServletContext().getRealPath("")+ConstFile.PIC_PATH;
			ConstFile.PIC_PATH_H = request.getContextPath()+ConstFile.PIC_PATH_H;
			isInitPath = true;
		}
		
		String uploadPath = request.getSession().getServletContext().getRealPath("")+ConstFile.WORD_PATH+File.separator;
		String fileP = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		if(!ServletFileUpload.isMultipartContent(request)){
			return ;
		} 
		try {
			List<FileItem> list = upload.parseRequest(request);
			if(list!=null&&list.size()>0){
				for(FileItem item:list){
					if(item.isFormField()){
						String name = item.getFieldName();
						String value = item.getString("UTF-8");
					}else{
						String fileName = item.getName();
						if(fileName!=null&&!"".equals(fileName.trim())){
							InputStream is = item.getInputStream();
							String extF = fileName.substring(fileName.indexOf("."));
							if(fileName.indexOf("docx")<=0)
								return ;
							fileP = uploadPath+OtherUtil.cUUID()+extF;
							File file = new File(fileP);
							FileOutputStream out = new FileOutputStream(file);
							byte[] buffer = new byte[1024];
							int len = 0;
							while((len=is.read(buffer))>0){
								out.write(buffer, 0, len);
							}
							is.close();
							out.close();
						}
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		//解析成html的数据
		String html = "";
		if(fileP!=null&&!"".equals(fileP)){
			Document document = WordUtil.readWord(fileP);
			html = HtmlUtil.createHtmlPage(document);
		}
		
		request.setAttribute("html", html);
		request.getRequestDispatcher("/blog/blog.jsp").forward(request, response);
		
	}

}
