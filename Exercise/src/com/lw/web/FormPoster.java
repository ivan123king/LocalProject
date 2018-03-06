package com.lw.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 模拟表单提交
 * @author lenovo
 *
 */
public class FormPoster {

	private URL url;
	private QueryString query = new QueryString();
	
	public FormPoster(URL url){
		if(!url.getProtocol().toLowerCase().startsWith("http")){
			throw new IllegalArgumentException("Posting only works for http URLs");
		}
		this.url = url;
	}
	
	public void add(String name,String value){
		query.add(name,value);
	}
	
	public URL getURL(){
		return url;
	}
	
	public InputStream post() throws IOException{
		//打开连接，准备POST
		URLConnection uc = url.openConnection();
		uc.setDoOutput(true);//设置输出为true就是post请求
		try(OutputStreamWriter out = new OutputStreamWriter(uc.getOutputStream(),"UTF-8")){
			out.write(query.toString());
			out.write("\r\n");
			out.flush();
		}
		//返回响应
		return uc.getInputStream(); 
	}
	
	public static void main(String[] args) throws IOException{
		URL url;
		if(args.length>0){
			try{
				url = new URL(args[0]);
			}catch(MalformedURLException e){
				System.err.println("Usage: java FormPoster url");
				return;
			}
		}else{
			try{
				url = new URL("http://www.baidu.com");
			}catch(MalformedURLException e){
				System.err.println(e);
				return;
			}
		}
		FormPoster poster = new FormPoster(url);
		poster.add("name", "king");
		poster.add("email","cctv3233@cam.org");
		
		try(InputStream in = poster.post()){
			//读取响应
			Reader r = new InputStreamReader(in);
			int c;
			while((c=r.read())!=-1){
				System.out.print((char)c);
			}
			System.out.println();
		}catch(IOException e){
			System.err.println(e);
		}
		
	}
}
