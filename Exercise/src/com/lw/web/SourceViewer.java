package com.lw.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 获取http头部信息
 * @author lenovo
 *
 */
public class SourceViewer {

	public static void main(String[] args) {
		for(int i=0;i<args.length;i++){
			try{
				//打开URLConnection进行读取
				URL u = new URL(args[i]);
				HttpURLConnection uc = (HttpURLConnection)u.openConnection();
				int code = uc.getResponseCode();//获取响应码
				String response = uc.getResponseMessage();
				System.out.println("HTTP/1.x "+code+" "+response);
				for(int j=1;;j++){
					String header = uc.getHeaderField(j);
					String key = uc.getHeaderFieldKey(j);
					if(header==null||key==null) break;
					System.out.println(key+":"+header);
				}
				System.out.println();
				try(InputStream in = new BufferedInputStream(uc.getInputStream())){
					//将InputStream串链到一个Reader
					Reader r = new InputStreamReader(in);
					int c;
					while((c=r.read())!=-1){
						System.out.print((char)c);
					}
				}
			}catch(MalformedURLException e){
				System.err.println(args[0]+" is not a parseable URL");
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
