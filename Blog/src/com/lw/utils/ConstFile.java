package com.lw.utils;

public class ConstFile {
	//word start=================将word文档元素转义为以下元素
	public static String PIC_PATH = null;//word中图片保存路径
	public static final String PARAGRAPH = "paragraph";
	public static final String TABLE = "table";
	public static final String ROW = "row";
	public static final String COL = "col";
	public static final String PICTURE = "picture";
	//word end=========================
	
	//html start==========================将word文档中转义的元素变成如下html中可以展示的元素
	public static String PIC_PATH_H = null;//html展示图片的路径
	public static final String SPANH = "div"; //paragraph
	public static final String TABLEH = "table";//table
	public static final String ROWH = "row";//row
	public static final String COLH = "col";//col
	public static final String IMGH = "img";//picture
	//html end=============================
}
