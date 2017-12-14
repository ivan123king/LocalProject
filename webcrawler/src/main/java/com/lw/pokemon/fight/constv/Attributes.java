package com.lw.pokemon.fight.constv;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 属性相克表
 * https://wiki.52poke.com/wiki/%E5%B1%9E%E6%80%A7%E7%9B%B8%E5%85%8B%E8%A1%A8 
 * 此网页的第六世代
 * @author lenovo
 *
 */
public class Attributes {
	/*
	 * 属性：一般，冰，地面，妖精，岩石，幽灵，恶，格斗，毒，水，火，电，草
	 * 			虫，超能力，钢，飞行,龙
	 */
	public static enum type{common,ice,ground,yaojing,rock,ghost,evil,fight,poison,
		water,fire,electricity,grass,bug,psychokinesis,steel,fly,Long};
	
	//属性相克表
	public static Map<String,Double> attrMap = new HashMap<String,Double>();
	static{
		/*
		 * 初始化属性相克，Map<String,Double> String="攻击方-防御方" 
		 * Double是数值表示克制系数，2表示2倍伤害，0.5一半伤害  0没有伤害   默认为一基础伤害
		 */
		type[] atktypes = Attributes.type.values();
		type[] deftypes = Attributes.type.values();
		double quotiety= 1,quotiety2 = 2,quotietyh = 0.5,quotiety0 = 0 ;
		for(type atkt:atktypes){
			for(type deft:deftypes){
				quotiety = 1;
				String atkStr = atkt.toString();
				String defStr = deft.toString();
				if(atkStr.equals("yaojing")){
					if("fight,Long,evil".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("poison,steel,fire".indexOf(defStr)>=0) quotiety = quotietyh;
				}
				else if(atkStr.equals("evil")){
					if("ghost,psychokinesis".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("fight,yaojing,evil".indexOf(defStr)>=0) quotiety = quotietyh;
				}
				else if(atkStr.equals("Long")){
					if("Long".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("steel".indexOf(defStr)>=0) quotiety = quotietyh;
					else if("yaojing".indexOf(defStr)>=0) quotiety = quotiety0;
				}
				else if(atkStr.equals("ice")){
					if("fly,ground,grass,Long".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("steel,fire,water,ice".indexOf(defStr)>=0) quotiety = quotietyh;
				}
				else if(atkStr.equals("psychokinesis")){
					if("fight,poison".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("steel,psychokinesis".indexOf(defStr)>=0) quotiety = quotietyh;
					else if("evil".indexOf(defStr)>=0) quotiety = quotiety0;
				}
				else if(atkStr.equals("electricity")){
					if("fly,water".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("grass,Long,electricity".indexOf(defStr)>=0) quotiety = quotietyh;
					else if("ground".indexOf(defStr)>=0) quotiety = quotiety0;
				}
				else if(atkStr.equals("grass")){
					if("rock,ground,water".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("fly,poison,bug,steel,fire,grass,Long".indexOf(defStr)>=0) quotiety = quotietyh;
				}
				else if(atkStr.equals("water")){
					if("ground,rock,fire".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("grass,Long,water".indexOf(defStr)>=0) quotiety = quotietyh;
				}
				else if(atkStr.equals("fire")){
					if("bug,steel,grass,ice".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("rock,fire,water,Long".indexOf(defStr)>=0) quotiety = quotietyh;
				}
				else if(atkStr.equals("steel")){
					if("rock,ice,yaojing".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("steel,fire,water,electricity".indexOf(defStr)>=0) quotiety = quotietyh;
				}
				else if(atkStr.equals("ghost")){
					if("ghost,psychokinesis".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("evil".indexOf(defStr)>=0) quotiety = quotietyh;
					else if("common".indexOf(defStr)>=0) quotiety = quotiety0;
				}
				else if(atkStr.equals("bug")){
					if("grass,psychokinesis,evil".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("fight,fly,poison,ghost,steel,fire,yaojing".indexOf(defStr)>=0) quotiety = quotietyh;
				}
				else if(atkStr.equals("rock")){
					if("fly,bug,fire,ice".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("fight,ground,steel".indexOf(defStr)>=0) quotiety = quotietyh;
				}
				else if(atkStr.equals("ground")){
					if("poison,rock,steel,fire,electricity".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("bug,grass".indexOf(defStr)>=0) quotiety = quotietyh;
					else if("fly".indexOf(defStr)>=0) quotiety = quotiety0;
				}
				else if(atkStr.equals("poison")){
					if("grass,yaojing".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("poison,ground,rock,ghost".indexOf(defStr)>=0) quotiety = quotietyh;
					else if("steel".indexOf(defStr)>=0) quotiety = quotiety0;
				}
				else if(atkStr.equals("common")){
					if(defStr.equals("rock")||defStr.equals("steel")) quotiety = quotietyh;
					else if(defStr.equals("ghost")) quotiety = quotiety0;
				}else if(atkStr.equals("fight")){
					if("common,rock,steel,ice,evil".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("fly,poison,bug,psychokinesis,yaojing".indexOf(defStr)>=0) quotiety = quotietyh;
					else if("ghost".indexOf("defStr")>=0) quotiety = quotiety0;
				}else if(atkStr.equals("fly")){
					if("fight,bug,grass".indexOf(defStr)>=0) quotiety = quotiety2;
					else if("rock,steel,electricity".indexOf(defStr)>=0) quotiety = quotietyh;
				}
				attrMap.put(atkStr+"-"+defStr,quotiety);
			}
		}
	};
	
	public static void main(String[] args) {
		Set<String> set = attrMap.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String key = it.next();
			double value = attrMap.get(key);
			System.out.println(key+"--->"+value);
		}
	}
	
}
