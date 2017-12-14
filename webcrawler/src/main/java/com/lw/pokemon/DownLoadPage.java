package com.lw.pokemon;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/**
 * 下载网页内容的类
 * @author lenovo
 *
 */
public class DownLoadPage {
	
	/**
	 * 根据url来获取内容
	 * @param url
	 * @return
	 */
	public static String getContentFromUrl(String url){
		StringBuffer contentStr = new StringBuffer();
		//实例化一个httpClient客户端
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = null;
		
		try{
			//设置请求的报文头部的编码
			httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));

			//设置期望服务端返回的编码
			httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
			//获取信息载体
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			if(httpEntity!=null){
				//转化为文本信息
				String charset = getCharset(httpEntity);
				//此处toString默认编码是ISO8859-1
				String content = EntityUtils.toString(httpEntity,charset);
				contentStr.append(content);
			}else{
				System.out.println("获取网页( "+url+" )数据失败！");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return contentStr.toString();
	}
	
	public static String getCharset(HttpEntity entity){
		String charset = "UTF-8";
		if (entity.getContentType() != null) {    
            HeaderElement values[] = entity.getContentType().getElements();   
            if (values.length > 0) {   
                NameValuePair param = values[0].getParameterByName("charset" );   
                if (param != null) {   
                    charset = param.getValue();   
                }   
            }   
        }
		if(StringUtils.isEmpty(charset)) charset = "UTF-8";
		return charset;
	}
}
