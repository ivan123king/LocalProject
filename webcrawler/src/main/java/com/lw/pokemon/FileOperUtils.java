package com.lw.pokemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件操作的工具类
 * @author lenovo
 *
 */
public class FileOperUtils {
	
	/**
	 * 将内容保存到文件中
	 * @param content
	 * @param fileName
	 */
	public static void saveFile(String content,String fileName){
		File saveFile = new File(ConstClass.saveFile+File.separator+fileName);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(saveFile));
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(bw!=null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 提取对应文件中内容
	 * @param fileName
	 * @return
	 */
	public static String getFileContent(String fileName){
		File readFile = new File(ConstClass.saveFile+File.separator+fileName);
		BufferedReader br = null;
		StringBuffer contentStr = new StringBuffer();
		try {
			br = new BufferedReader(new FileReader(readFile));
			String content = null;
			//此处注意下，StringUtils.isNotEmpty(content)时判断了!="",这时文件还没有读取完，只是空了一行，结果读取失败
			while((content = br.readLine())!=null){
				contentStr.append(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if(br!=null) br.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			return contentStr.toString();
		}
	}
}
