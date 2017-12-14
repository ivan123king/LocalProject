package com.lw.pokemon.fight.constv;
/**
 * 持久型变量，保存一些特定设置
 * @author lenovo
 *
 */
public class ConstVariable {

	//--------------pokenmen start---------------
	//体型设置，0,1,2 小，中，大
	public static enum BODILYFORM{Small,Middle,Big;}
	//特性设置 分别为：0无,1懒惰，2猛火，3激流，4茂盛，
	public static enum FEATURES{None,Lazy,Fire,Water,Grass};
	//状态设置：0无，1中毒，2冰结，3麻痹，4迷人，5混乱，6睡眠
	public static enum STATUS{None,Poisoning,Ice,Paralysis,
		Charming,Muddledness,Sleeping};
	//心情设置：0亢奋，1平静，3悲伤，4快乐，5愤怒，6恐惧  默认平静
	public static enum FEELING{Excited,Calm,Sad,Happy,Angry,Fear};
	/*
	 * 精灵类型：一般，冰，地面，妖精，岩石，幽灵，恶，格斗，毒，水，火，电，草
	 * 			虫，超能力，钢，飞行,龙
	 */
	public static enum pokemonType{common,ice,ground,yaojing,rock,ghost,evil,fight,poison,
		water,fire,electricity,grass,bug,psychokinesis,steel,fly,Long};
	//--------------pokenmen end---------------
		
	//--------------skill start---------------	
	//技能类型 格斗型,特攻型,速度型,身体型,念力型,持久型,迷惑型
	public static enum skillType{fight,satk,speed,body,
		psychokinesis,lasting,puzzle};
	/*
	 * 精灵技能类型：一般，冰，地面，妖精，岩石，幽灵，恶，格斗，毒，水，火，电，草
	 * 			虫，超能力，钢，飞行,龙
	 */
	public static enum skillOriType{common,ice,ground,yaojing,rock,ghost,evil,fight,poison,
		water,fire,electricity,grass,bug,psychokinesis,steel,fly,Long};
	//物攻，特攻
	public static enum atkType{atk,satk};
	//范围攻击，单体攻击
	public static enum atkArea{area,single};
	//远程攻击，近程攻击
	public static enum atkDistance{far,near};
	//--------------skill end---------------		
	
	//--------------field start---------------
	//天气情况:雨，雪，冰雹，沙尘，干燥，晴朗，阴云
	public static enum weather{rain,snow,ice,sand,dry,fine,cloudy};
	//地面情况:青草，树木，荒野，沙漠，水
	public static enum ground{meadow,forest,wilderness,desert,rivers};
	//时间情况:白天，黑夜
	public static enum day{day,night};
	//领域情况:无领域，电气领域，青草领域，水之领域
	public static enum domain{none,electrical_field,grass_field,water_field};
	//--------------field start---------------
	public static void main(String[] args) {
		//用这种方式变为数字2，toString()变为字符串 Big
		System.out.println(ConstVariable.BODILYFORM.Big.ordinal());
	}
	
}
