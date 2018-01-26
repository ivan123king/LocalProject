package com.lw.decodeAencode.owncase;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

/**
 * 自己的Base64编码解码
 * 原理： 
 * 	编码：
 * 		1. 首先Base64编码，然后将其中的"="统计为数字，添加到字符串末尾，
 * 		2. 将此字符串  oStr += oStr
 * 		3. 重复上述1,2步骤
 *  解码：
 * 		1. 将字符串截取直到倒数第二位（包含），得到最后一位数字
 * 		2. 根据最后一位数字添加对应的等号
 * 		3. Base64解码
 * 		4. 将 oStr 截取一半
 * 		5. 重复1,2,3步骤
 * @author lenovo
 *
 */
public class OwnBase64Case {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String str = "QJUe5iCB3JdsXQqK6oEQS9MaiAof7XVwlfYaZ5DMBYAFzSpVfo0mWtAk8iIq6th2bNjaDZpcjl1wviBMWPHuYA==";
		byte[] data = org.apache.commons.codec.binary.Base64.decodeBase64(str);
//		System.out.println(new String(data,"iso8859-1"));
		
//		String input = "my own 吐槽";
//		input = "this is a happy world";
//		String output = OwnBase64Case.ownEncrypt(input.getBytes());
//		System.out.println("encrypt:"+output);
//		output = OwnBase64Case.ownDecrypt(output.getBytes());
//		System.out.println("decrypt:"+output);
	}
	
	public static String ownEncrypt(byte[] input){
		byte[] output = encrypt(input);
		String oStr = new String(output);
		oStr += oStr;
		output = encrypt(oStr.getBytes());
		oStr = new String(output);
		return oStr;
	}
	
	public static String ownDecrypt(byte[] input){
		byte[] output = decrypt(input);
		String oStr = new String(output);
		oStr = oStr.substring(0,oStr.length()/2);
		output = decrypt(oStr.getBytes());
		oStr = new String(output);
		return oStr;
		
	}
	
	private static byte[] encrypt(byte[] input){
		byte[] output = Base64.encode(input);
		String oStr = new String(output);
		int count = 0;
		while(oStr.indexOf("=")>=0){
			oStr = oStr.replaceFirst("=", "");
			count++;
		}
		oStr += count;
		return oStr.getBytes();
	}
	
	private static byte[] decrypt(byte[] input) {
		String iStr = new String(input);
		int count = Integer.parseInt(iStr.substring(iStr.length()-1));
		iStr = iStr.substring(0,iStr.length()-1);
		while(count-->0){
			iStr += "=";
		}
		byte[] output = Base64.decode(iStr.getBytes());
		return output;
	}
}
