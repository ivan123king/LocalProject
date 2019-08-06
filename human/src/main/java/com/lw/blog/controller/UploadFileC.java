package com.lw.blog.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.dom4j.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.blog.constinfo.ConstFile;
import com.lw.blog.utils.HtmlUtil;
import com.lw.blog.utils.OtherUtil;
import com.lw.blog.utils.WordUtil;

@Controller
@RequestMapping(value = "/blog/upload")
public class UploadFileC {

	private static boolean isInitPath = false;// 表示是否初始化了文件上传路径

	/**
	 * 上传图片
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/uploadimage")
	public void uploadImage(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		if (!isInitPath) {
			ConstFile.CK_PIC_PATH = request.getSession().getServletContext()
					.getRealPath("")
					+ ConstFile.CK_PIC_PATH;
			isInitPath = true;
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			try {
				List<FileItem> fileList = upload.parseRequest(request);
				if (fileList != null && fileList.size() > 0) {
					for (FileItem item : fileList) {
						String extF = item.getName().substring(
								item.getName().indexOf("."));
						String fileName = "CKEDITOR_" + OtherUtil.cUUID()
								+ extF;
						String imgPath = ConstFile.CK_PIC_PATH + File.separator
								+ fileName;
						File file = new File(imgPath);
						item.write(file);
						String callBack = request
								.getParameter("CKEditorFuncNum");// ckeditor的回调函数
						out.println("<script>");
						String imgShowPath = request.getContextPath()
								+ "/upload/images/" + fileName;
						out.println("window.parent.CKEDITOR.tools.callFunction("
								+ callBack + ",'" + imgShowPath + "','')");
						out.println("</script>");
						out.flush();
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (out != null)
					out.close();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
	/**
	 * 上传Word文档
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/uploadword")
	public String uploadWord(HttpServletRequest request,HttpServletResponse response,Model model){
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
			return null;
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
								return null;
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
							break;
						}
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//解析成html的数据
		String html = "";
		if(fileP!=null&&!"".equals(fileP)){
//			Document document = WordUtil.readWord(fileP);
//			html = HtmlUtil.createHtmlPage(document);
			String filePath = uploadPath;
			String fileName = fileP.substring(fileP.lastIndexOf(File.separator)+1);
			String htmlPath =  request.getSession().getServletContext().getRealPath("")+ConstFile.HTML_PATH+File.separator;
			String htmlName = fileName.substring(0,fileName.lastIndexOf("."))+".html";
			try {
				WordUtil.wordToHtml(filePath, fileName, htmlPath, htmlName);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (FactoryConfigurationError e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
		}
		
		
		request.setAttribute("html", html);
		return "blog/blog";
	}
}
