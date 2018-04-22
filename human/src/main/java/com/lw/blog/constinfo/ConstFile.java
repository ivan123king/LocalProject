package com.lw.blog.constinfo;

import java.io.File;

public class ConstFile {
	//word start=================将word文档元素转义为以下元素
	public static String PIC_PATH = "upload"+File.separator+"images";//word中图片保存路径
	public static String WORD_PATH = "upload"+File.separator+"word";//word文档上传路径
	public static final String PARAGRAPH = "paragraph";
	public static final String TABLE = "table";
	public static final String ROW = "row";
	public static final String COL = "col";
	public static final String PICTURE = "picture";
	//word end=========================
	
	//html start==========================将word文档中转义的元素变成如下html中可以展示的元素
	public static String PIC_PATH_H = File.separator+"upload"+File.separator+"images";//html展示图片的路径
	public static final String DIVH = "div"; //paragraph
	public static final String TABLEH = "table";//table
	public static final String ROWH = "tr";//row
	public static final String COLH = "td";//col
	public static final String PREH = "pre";
	public static final String IMGH = "img";//picture
	public static final String SPANH = "span";
	//html end=============================
	
	//ckeditor 图片上传路径
	public static String CK_PIC_PATH = File.separator+"upload"+File.separator+"images";
	
	//博客默认题目
	public static final String BLOG_TITLE = "无题";
	
}
