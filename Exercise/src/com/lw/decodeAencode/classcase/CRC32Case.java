package com.lw.decodeAencode.classcase;

import java.util.zip.CRC32;

/**
 * CRC冗余校验，用于校验数据完整性
 * @author lenovo
 *
 */
public class CRC32Case {

	public static void main(String[] args) {
		String str = "测试CRC-32";
		CRC32 crc32 = new CRC32();
		crc32.update(str.getBytes());
		String hex = Long.toHexString(crc32.getValue());
		System.out.println("原文:\t"+str);
		System.out.println("CRC-32:\t"+hex);//冗余校验码
	}
}
