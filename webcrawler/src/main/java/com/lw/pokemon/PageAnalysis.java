package com.lw.pokemon;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 * 页面分析
 * 
 * @author lenovo
 *
 */
public class PageAnalysis {

	/**
	 * 页面分析的方法
	 * 解析此页面：https://wiki.52poke.com/wiki/%E5%AE%9D%E5%8F%AF%E6%A2%A6%E5%
	 * 88%97%E8
	 * %A1%A8%EF%BC%88%E6%8C%89%E5%85%A8%E5%9B%BD%E5%9B%BE%E9%89%B4%E7%BC
	 * %96%E5%8F%B7%EF%BC%89
	 * 
	 * @param indexType
	 */
	public static void pageAnalysis(String indexType, String htmlContent) {
		Document doc = Jsoup.parse(htmlContent);
		if (indexType.equalsIgnoreCase(ConstClass.firstHtml)) {
			analysisFirstHtml(doc);
		} else if (indexType.equalsIgnoreCase(ConstClass.pokemonHtml)) {
			analysisPokemonHtml(doc);
		}
	}

	/**
	 * 分析具体某个小精灵的数据页面
	 * 
	 * @param doc
	 */
	private static void analysisPokemonHtml(Document doc) {

		Elements all = doc.children();
		Element body = all.get(0).child(1);
		Element div = body.children().get(2).getElementById("mw-content-text");
		// 此处firstTable对应pageinfo/pokemonhtmlinfo.txt中内容
		Element firstTable = div.child(1);
		Element tbody = firstTable.child(0);
		// 第一部分分析 第一child是得到tbody
		{
			Element firstTr01 = tbody.child(0);
			Element fulltable = firstTr01.getElementsByTag("table").first();
			Elements tds = fulltable.getElementsByTag("td");
			Element b = tds.get(0).select("b").first();
			String pokemonName = b.text();// 得到宠物小精灵名字
			Element img = tds.get(1).select("img").first();
			String pokemonAttrPicUrl = img.attr("data-url");// 得到精灵属性图片地址
			Element a = fulltable.getElementsByTag("th").first().select("a")
					.first();
			String pokemonNum = a.text();// 精灵全国编号

			// System.out.println(pokemonName+"\r\n"+pokemonAttrPicUrl+"\r\n"+pokemonNum);
		}
		// 第二部分，得到小精灵的图片
		{
			Element firstTr02 = tbody.child(1);
			Element img02 = firstTr02.select("img").first();
			String pokemonPicUrl = img02.attr("data-url");// 得到小精灵图片地址
			// System.out.println(pokemonPicUrl);
		}
		
		// 第三部分，得到属性，分类说明
		{
			Element firstTr03 = tbody.child(2);
			Element td02 = firstTr03.child(0).child(0).child(0).child(0).child(0)
					.getElementsByTag("td").first();
			Element a02 = td02.select("a").last();
			//此处得到的是&nbsp;水&nbsp;，结果是用text()方法得到的 水 字两边有空格，还无法去掉
			String pokemonAttrStr = a02.html().replaceAll("&nbsp;", "").trim();// 得到小精灵属性文字说明
//			System.out.println(pokemonAttrStr);
			td02 = firstTr03.child(1).child(0).child(0).getElementsByTag("table")
					.first().getElementsByTag("td").first();
			String pokemonClassification = td02.text().trim();//得到小精灵分类
//			 System.out.println(pokemonClassification);
		}
		
		//第四部分 得到特性
		{
			Element firstTr04 = tbody.child(3);
			Element table04 = firstTr04.child(0).child(0).child(0).getElementsByTag("table").first();
			Elements tds04 = table04.getElementsByTag("td");
			String[] characters = new String[tds04.size()]; //保存小精灵特性处，第一个是可见特性，第二个是隐藏特性
			for(int cindex=0,len=tds04.size();cindex<len;cindex++){
				characters[cindex] = tds04.get(cindex).text();
//				System.out.println(characters[cindex]);
			}
		}
		
		//第五部分 地区图鉴编号
		{
			Element firstTr05 = tbody.child(5);
			Element table05 = firstTr05.child(0).child(0).child(0).child(0)
						.getElementsByTag("table").first();
			Elements trs05 = table05.getElementsByTag("tr");
			String area = "";//地区
			String areaNum = "";//地区编号
			for(Element tr:trs05){
				if(tr.hasClass("hide")) continue;
				Element a05 = tr.select("a").first();
				area = a05.text();
				Element td05 = tr.select("td").last();
				areaNum = td05.text();
			}
//			System.out.println(area+" "+areaNum);
		}
		
		//第六部分 身高，体重，体型
		{
			Element firstTr06 = tbody.child(7);
			Element td06 = firstTr06.child(0);
			Element a06 = td06.select("a").first();
			Element innerTd06 = td06.child(0).child(0).getElementsByTag("table").first()
								.getElementsByTag("td").first();
			String height = a06.text()+":"+innerTd06.text();//身高
			
			td06 = firstTr06.child(1);
			a06 = td06.select("a").first();
			innerTd06 = td06.child(0).child(0).getElementsByTag("table").first()
								.getElementsByTag("td").first();
			String weight = a06.text()+":"+innerTd06.text();//体重
			
			firstTr06 = tbody.child(8);
			td06 = firstTr06.child(0);
			a06 = td06.select("a").first();
			Element img06 = td06.getElementsByTag("img").first();
			String somatotype = a06.text()+":"+img06.attr("alt");//体型
//			System.out.println(height+"-->"+weight+"-->"+somatotype);
		}
		
		//第7部分，图鉴颜色，捕获度
		{
			Element firstTr07 = tbody.child(9);
			Element td07 = firstTr07.child(0);
			Element a07 = td07.select("a").first();
			Element innerTd07 = td07.child(0).child(0).getElementsByTag("table").first()
								.getElementsByTag("td").first();
			String color = a07.text()+":"+innerTd07.text();//小精灵颜色
			
			td07 = firstTr07.child(1);
			a07 = td07.select("a").first();
			innerTd07 = td07.child(0).child(0).getElementsByTag("table").first()
					.getElementsByTag("td").first();
			String catchDegree = a07.text()+":"+innerTd07.text();//捕获度
			Element span07 = innerTd07.select("small").first().child(0);
			String catchDegreeFullStrength = span07.attr("title")+":"+span07.text();//小精灵满体力捕获度
			
//			System.out.println(color);
//			System.out.println(catchDegree);
//			System.out.println(catchDegreeFullStrength);
		}
		
		//第八部分  性别比例
		{
			Element firstTr08 = tbody.child(10);
			Element table = firstTr08.getElementsByTag("table").last();
			Elements tds = table.getElementsByTag("td");
			String sexScale = "";//性别比例
			for(Element td:tds){
				if(td.hasClass("hide")|| td.hasAttr("style")) continue;
				else if(td.html().indexOf("span")>0){
					Elements spans = td.getElementsByTag("span");
					sexScale = spans.get(0).text()+"--"+spans.get(1).text();
				}else{
					sexScale = td.text();
				}
				break;
			}
//			System.out.println(sexScale);
		}
		
		//第二大部分 说明
		//第一部分：基本介绍
		{
			boolean isPass = false;
			Elements allChild = div.children();
			for(int i=2,len=allChild.size();i<len;i++){
				String content = allChild.get(i).text();
				if(content.indexOf("漫画中")>=0||content.indexOf("动画中")>=0) 
					isPass = true;
				if(content.indexOf("游戏中")>=0) 
					isPass= false;
				if(i<2||i>2&&i<4||isPass) continue;
				System.out.println(content);
			}
//			Element explain = div.child(2);
//			System.out.println(explain.text());
//			
//			Element iBigTitle = div.child(4);
//			System.out.println(iBigTitle.text());
//			Element isTitle01 = div.child(5);
//			System.out.println(isTitle01.text());
		}
		
		
	}

	/**
	 * 用来分析首页得到pokemon粗略数据集
	 * 
	 * @param doc
	 */
	private static void analysisFirstHtml(Document doc) {
		Elements tables = doc.getElementsByTag("table");
		int count = 0;
		for (int i = 0, len = tables.size(); i < len; i++) {
			if (i == 0 || i >= len - 1)
				continue;// 跳过第一个table
			Element table = tables.get(i);
			Elements trs = table.getElementsByTag("tr");
			for (int tri = 0, trlen = trs.size(); tri < trlen; tri++) {
				if (tri <= 1)
					continue;
				Elements tds = trs.get(tri).getElementsByTag("td");
				for (int tdi = 0, tdlen = tds.size(); tdi < tdlen; tdi++) {
					Element td = tds.get(tdi);
					String printC = "";
					if (td.hasClass("hide"))
						continue;
					// if(td.hasAttr("title")){
					// printC = td.attr("title");
					// }else{
					// }
					printC = td.text();
					System.out.print(printC + " ");
					/*
					 * 输出数据格式： #151 #151 梦幻 ミュウ Mew 超能力 #001 #001 #152 菊草叶 チコリータ
					 * Chikorita 草
					 */
				}
				count++;
				System.out.println();
			}
		}

		System.out.println("总数：" + count);
	}
}
