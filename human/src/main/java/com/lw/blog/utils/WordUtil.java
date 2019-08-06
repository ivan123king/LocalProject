package com.lw.blog.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.core.IURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lw.blog.constinfo.ConstFile;


/**
 * 解析word文档的工具类
 * @author lenovo
 *
 */
public class WordUtil {

	/**
	 * 读取word文档，然后将信息保存为定义的xml格式
	 * @param filePath
	 * @return
	 */
	public static Document readWord(String filePath) {
		
		Document document = DocumentHelper.createDocument();
		Element wordE = DocumentHelper.createElement("word");
		document.add(wordE);
		
		Map<String,String> picMap = new HashMap<String,String>();//保存图片文件的word中id和生成的UUID作为文件名
		
		String text = "";
		File file = new File(filePath);
		// 2003
		if (file.getName().endsWith(".doc")) {
			try {
				FileInputStream stream = new FileInputStream(file);
				WordExtractor word = new WordExtractor(stream);
				text = word.getText();
				String texts[] = word.getParagraphText();
				for (String t : texts) {
					t = t.replaceAll("(\\r\\n){2,}", "\r\n");
					t = t.replaceAll("(\\n){2,}", "\n");
					System.out.println(t);
				}
				// 去掉word文档中的多个换行
				text = text.replaceAll("(\\r\\n){2,}", "\r\n");
				text = text.replaceAll("(\\n){2,}", "\n");
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (file.getName().endsWith(".docx")) { // 2007
			try {
				OPCPackage oPCPackage = POIXMLDocument.openPackage(filePath);
				XWPFDocument xwpf = new XWPFDocument(oPCPackage);
//				POIXMLTextExtractor ex = new XWPFWordExtractor(xwpf);
				
				Iterator<IBodyElement> iter = xwpf.getBodyElementsIterator();
				int curT = 0;//当前操作表格对象索引
				int curP = 0;//当前操作文字对象索引
				StringBuffer content = new StringBuffer();
				while(iter.hasNext()){
					IBodyElement ibe = iter.next();
					if(ibe.getElementType().equals(BodyElementType.TABLE)){//处理表格
						XWPFTable table = ibe.getBody().getTableArray(curT);
						
						Element tableE = DocumentHelper.createElement(ConstFile.TABLE);
						
						List<XWPFTableRow> rowList = table.getRows();
						if(rowList!=null&&rowList.size()>0){
							for(XWPFTableRow row:rowList){
								
								Element rowE = DocumentHelper.createElement(ConstFile.ROW);
								
								List<XWPFTableCell> cellList = row.getTableCells();
								if(cellList!=null&&cellList.size()>0){
									for(XWPFTableCell cell:cellList){
										
										Element colE = DocumentHelper.createElement(ConstFile.COL);
										colE.setText(cell.getText());
//										colE.addAttribute("color", cell.getColor());
										rowE.add(colE);
										
									}
								}
								
								tableE.add(rowE);
							}
						}
					
						wordE.add(tableE);
						
						curT++;
					}else if(ibe.getElementType().equals(BodyElementType.PARAGRAPH)){//处理文字
						boolean isPic = false;//表示是否是图片标志
						XWPFParagraph p = ibe.getBody().getParagraphArray(curP);
						curP++;
						//下面的代码来确定图片所在位置
						List<XWPFRun> runList = p.getRuns();
						if(OtherUtil.isNotEmpty(runList)){
							for(XWPFRun run:runList){
								String runXmlText = run.getCTR().xmlText();
								//图片索引
								if(runXmlText.indexOf("<w:drawing")!=-1){
									int rIdIndex = runXmlText.indexOf("r:embed=");
									int ridEndIndex = runXmlText.indexOf("/>",rIdIndex);
									/*
									 * rIdText格式：
									 *  rId4 
										rId5
									 */
									String rIdText = runXmlText.substring(rIdIndex+"r:embed=".length()+1, ridEndIndex-1);
									
									String pfName = OtherUtil.cUUID();
									picMap.put(rIdText,pfName);
									isPic = true;
									
									Element pictureE = DocumentHelper.createElement(ConstFile.PICTURE);
									pictureE.setText(pfName);
									wordE.add(pictureE);
								}else if(runXmlText.indexOf("<w:t")!=-1){//文字
									Element paragraphE = DocumentHelper.createElement(ConstFile.PARAGRAPH);
									paragraphE.addAttribute("color", OtherUtil.isNotEmpty(run.getColor())? run.getColor():"000000");//格式：0000FF
									paragraphE.addAttribute("fontSize",String.valueOf(run.getFontSize()));
									paragraphE.setText(run.toString());
									wordE.add(paragraphE);
								}
							}
						}
						
					}
				}
				//这里输出只是为了看效果，不是工具类的一部分
//				System.out.println(content.toString());
//				OutputFormat opf = new OutputFormat(" ",true);
//		    	opf.setEncoding("UTF-8");
//		    	XMLWriter xmlW = new XMLWriter(new FileOutputStream("d:\\tmp\\person.xml"),opf);
//		    	xmlW.write(document);
//		    	xmlW.close();
				//对word中读取生成一个xml格式
		    	/*
				 * 读取图片所在位置，然后保存图片
				 * 输出格式： rId4
						  rId5
				 */
		    	List<XWPFPictureData> xwpfPicList =  xwpf.getAllPictures();
				for(XWPFPictureData p:xwpfPicList){
					 String id = p.getParent().getRelationId(p);
					 String pfName = picMap.get(id);
					 byte[] pb = p.getData();
					 String pName = p.getFileName();
					 FileOutputStream fos = new FileOutputStream(ConstFile.PIC_PATH+File.separator+pfName+pName.substring(pName.indexOf("."))); 
			         fos.write(pb);
			         fos.flush();
			         fos.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return document;
	}

	public static void readPicture(String filePath) throws IOException {
		FileInputStream fis = new FileInputStream(new File(filePath));
		String path = filePath.substring(0,
				filePath.lastIndexOf(File.separator) + 1);
		if (filePath.endsWith("doc")) {
			HWPFDocument doc = new HWPFDocument(fis);
			int length = doc.characterLength();
			PicturesTable pTable = doc.getPicturesTable();
			for (int i = 0; i < length; i++) {
				Range range = new Range(i, i + 1, doc);

				CharacterRun cr = range.getCharacterRun(0);
				if (pTable.hasPicture(cr)) {
					Picture pic = pTable.extractPicture(cr, false);
					String afileName = pic.suggestFullFileName();
					OutputStream out = new FileOutputStream(new File(path
							+ UUID.randomUUID() + afileName));
					pic.writeImageContent(out);

				}
			}
		} else if (filePath.endsWith("docx")) {
			try {
				XWPFDocument document = new XWPFDocument(fis);
				// 读取文字
				// XWPFWordExtractor xwpfWordExtractor = new
				// XWPFWordExtractor(document);
				// String text = xwpfWordExtractor.getText();
				List<XWPFPictureData> picList = document.getAllPictures();
				for (XWPFPictureData pic : picList) {
					byte[] bytev = pic.getData();
					FileOutputStream fos = new FileOutputStream(path
							+ pic.getFileName());
					fos.write(bytev);
				}
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将word转变为html
	 * @param filePath
	 * @param fileName
	 * @param htmlName
	 * @throws IOException 
	 * @throws FactoryConfigurationError 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 */
	public static void wordToHtml(String filePath,String fileName,String htmlPath,String htmlName) throws IOException, ParserConfigurationException, FactoryConfigurationError, TransformerException{
		
		final String fileStr = filePath+fileName;
		File file = new File(fileStr);
		/*
		 * 1.判断是doc还是docx
		 */
		if(fileName.endsWith(".docx")||fileName.endsWith(".DOCX")){
			/*
			 * 进行docx文件变为html文件的转换
			 */
			InputStream is = new FileInputStream(file);
			XWPFDocument document = new XWPFDocument(is);
			//设置用来存放图片的目录
			File imageFolderFile = new File(htmlPath+htmlName.substring(0,htmlName.lastIndexOf(".html"))+File.separator);
			if(!imageFolderFile.exists()){
				imageFolderFile.mkdir();
			}
			XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));
			options.setExtractor(new FileImageExtractor(imageFolderFile));
			options.setIgnoreStylesIfUnused(false);
			options.setFragment(true);
			//将XWPFDocument转换成Html
			OutputStream out = new FileOutputStream(new File(htmlPath+htmlName));
			XHTMLConverter.getInstance().convert(document, out, options);
		}else if(fileName.endsWith(".doc")||fileName.endsWith(".DOC")){
			/*
			 * 进行doc文件变为html文件的转换 
			 */
			InputStream is = new FileInputStream(file);
			HWPFDocument document = new HWPFDocument(is);
			WordToHtmlConverter converter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
			//解析word文档
			converter.processDocument(document);
			org.w3c.dom.Document htmlDocument = converter.getDocument();
			File htmlFile = new File(htmlPath+htmlName);
			OutputStream outStream = new FileOutputStream(htmlFile);
			DOMSource domSource = new DOMSource(htmlDocument);
			StreamResult streamResult = new StreamResult(outStream);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer serializer = factory.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			serializer.setOutputProperty(OutputKeys.INDENT,"yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
			
			serializer.transform(domSource, streamResult);
			outStream.close();
		}
		
		
		
	}

	public static void main(String[] args) {
		String filePath = "D:\\tmp\\cc.docx";
		filePath = "D:\\tmp\\";
		String fileName = "test2018.docx";
		String htmlPath = filePath;
		String htmlName = "test2018.html";
		try {
			// WordUtil.readPicture(filePath);
//			WordUtil.readWord(filePath);
//			WordUtil.wordToHtml(filePath, fileName, htmlPath,htmlName);
			
			String fileP = "D:/own_workspace/human/target/m2e-wtp/web-resources/upload/word/cfdac58f308e4431b5a30d7f5e6b89cd.docx";
			String uploadPath = "D:/own_workspace/human/target/m2e-wtp/web-resources/upload/word/";
			System.out.println(fileP.substring(fileP.lastIndexOf("/")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(text);
	}
	
	
	/**
	 * 读取Table内容
	 *  List<XWPFTable> tableList = xwT.getTables();
		if(tableList!=null&&tableList.size()>0){
			for(XWPFTable table:tableList){
				List<XWPFTableRow> rowList = table.getRows();
				if(rowList!=null&&rowList.size()>0){
					for(XWPFTableRow row:rowList){
						List<XWPFTableCell> cellList = row.getTableCells();
						if(cellList!=null&&cellList.size()>0){
							for(XWPFTableCell cell:cellList){
								System.out.println(cell.getText());
							}
						}
					}
				}
			}
		}
	 */
}
