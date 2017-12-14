package com.lw.pokemon.fight.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lw.pokemon.fight.constv.Attributes;

/**
 * 裁定的一些流程配置,也是裁判员
 * @author lenovo
 *
 */
public class JudgeStep {

	
	/**
	 * 判断对战伤害过程，
		pokemons  对战小精灵
		orders  训练家指令
		atkFirst = -(atkFirst-1);  经过这种变化得出的是先攻，另一个是防御方
	 */
	public final int judgeAttackStep(Pokemon[] pokemons,String[] orders,int atkFirst){
		int atk = -(atkFirst-1),def = atkFirst;//攻击，防御方
		//获取攻击方和防御方精灵
		Pokemon atkP = pokemons[atk];
		Pokemon defP = pokemons[def];
		
		Map atkMap = atkP.getMap();
		Map defMap = defP.getMap();
		//获取攻击方技能情况：
		Skill[] atkSkill = (Skill[]) atkMap.get("skills");//小精灵的四个技能
		Skill[] defSkill = (Skill[]) defMap.get("skills");//小精灵的四个技能
		//获取指令
		String atkOrder = orders[atk];
		String defOrder = orders[def];
		//1.判断攻击方攻击是否命中
		boolean atkIn = this.isAttacked(pokemons, orders, atkFirst);
		//2.攻击如果命中，计算伤害
		if(atkIn){
			int atkValue = this.attackHarm(atkP);
			defP.setBlood(defP.getBlood()-atkValue);
			System.out.println(atkP.getName()+"命中。");
		}
		//3.判断防御方是否是进攻攻击，如果是需要计算命中率和伤害
		if("a,b,c".indexOf(defOrder.toLowerCase())<0){
			boolean defIn = this.isAttacked(pokemons, orders, -(atkFirst-1));
			if(defIn){
				int defAtkValue = this.attackHarm(defP);
				atkP.setBlood(atkP.getBlood()-defAtkValue);
				System.out.println(defP.getName()+"命中。");
			}
		}
		
		//4.判断对战是否结束
		int winner = -1;
		if(atkP.getBlood()<=0&&defP.getBlood()>0) winner = def;//防御方胜利
		else if(defP.getBlood()<=0&&atkP.getBlood()>0) winner = atk;//攻击方胜利
		else if(atkP.getBlood()<=0&&defP.getBlood()<=0) winner = 100;//平手
		
		return winner;//对战胜利方的编号
		
	}
	/**
	 * 计算技能伤害
	 * @return
	 */
	private int attackHarm(Pokemon pokemon){
		return 30;
		
	}
	
	//对战指令记录
//	private List<String> orderInfo01 = new ArrayList<String>();
//	private List<String> orderInfo02 = new ArrayList<String>();
	private Map<String,Integer> orderMap01 = new HashMap<String,Integer>();
	private Map<String,Integer> orderMap02 = new HashMap<String,Integer>();
	/**
	 * 判断攻击是否命中
	 * @return
	 */
	private boolean isAttacked(Pokemon[] pokemons,String[] orders,int atkFirst){
		int atk = -(atkFirst-1),def = atkFirst;//攻击，防御方
		//获取攻击方和防御方精灵
		Pokemon atkP = pokemons[atk];
		Pokemon defP = pokemons[def];
		//获取指令
		String atkOrder = orders[atk];//攻击方指令
		String defOrder = orders[def];//防御方指令
		Map atkMap = atkP.getMap();
		Map defMap = defP.getMap();
		//获取攻击方技能情况：
		Skill[] atkSkill = (Skill[]) atkMap.get("skills");//小精灵的四个技能
		Skill[] defSkill = (Skill[]) defMap.get("skills");//小精灵的四个技能
		
		int isAttacked = 0;//是否命中标志  0初始，1命中，2没有命中，3需要计算命中率和躲避率
		//先判断攻击方向和移动方向
		String atkattackment = atkMap.get("attackment").toString();
		String defMovement = defMap.get("movement").toString();
		
		System.out.println(atkP.getName()+"移动方向，"+atkattackment+"\r\n"
						+defP.getName()+"移动方向，"+defMovement);
		
		//攻击方向（上下，左右）如果和移动方向（上下，右左）相对应，命中；
		//防御方小精灵前后移动肯定命中
		if(defMovement.equals(PokemonMovement.direction.front.toString())
				||defMovement.equals(PokemonMovement.direction.behind.toString())){
			isAttacked = 1;
		}else if(atkattackment.equals(Attack.attackment.down.toString())){
			if(defMovement.equals(PokemonMovement.direction.down.toString())){
				isAttacked = 1;
			}else if(defMovement.equals(PokemonMovement.direction.up.toString())){
				isAttacked = 2;
			}else isAttacked = 3;
		}else if(atkattackment.equals(Attack.attackment.up.toString())){
			if(defMovement.equals(PokemonMovement.direction.up.toString())){
				isAttacked = 1;
			}else if(defMovement.equals(PokemonMovement.direction.down.toString())){
				isAttacked = 2;
			}else isAttacked = 3;
		}//特别注意Left 和 right，这是相对的面对面的左右，所以左对右，右对左
		else if(atkattackment.equals(Attack.attackment.left.toString())){
			if(defMovement.equals(PokemonMovement.direction.right.toString())){
				isAttacked = 1;
			}else if(defMovement.equals(PokemonMovement.direction.left.toString())){
				isAttacked = 2;
			}else isAttacked = 3;
		}else if(atkattackment.equals(Attack.attackment.right.toString())){
			if(defMovement.equals(PokemonMovement.direction.left.toString())){
				isAttacked = 1;
			}else if(defMovement.equals(PokemonMovement.direction.right.toString())){
				isAttacked = 2;
			}else isAttacked = 3;
		}
		//需要计算时才记录此时指令
		double dodge = 0.0;//躲避率
		if(isAttacked==3){
			//对战指令：A接近对方，B远离对方，C闪避，D攻击
			defOrder = defOrder.toLowerCase();
			atkOrder = atkOrder.toLowerCase();
			Map<String,Integer> orderMap = null;
			//获取防御方的指令数据记录
			if(def==0){
				orderMap = orderMap01;
			}else{
				orderMap = orderMap02;
			}
			/*
			 * 是否躲避计算公式：100%-（0~1随机数%）*n*系数>(0~1随机数%)
			 */
			double quotiety = 0;//系数
			if(defOrder.equals("c")){//闪避
				quotiety = 1;
			}else if(defOrder.equals("a")){//接近
				quotiety = 1.2;
			}else if(defOrder.equals("b")){//远离
				quotiety = 0.8;
			}
			if(quotiety!=0){//表示防御方没有攻击，采取ABC闪避措施
				int orderCount = 1;//指令次数
				if(orderMap.containsKey(defOrder)){
					orderCount = orderMap.get(defOrder);
					orderCount++;
				}
				orderMap.put(defOrder, orderCount);
				if(orderCount%3==0) isAttacked = 1;
				else{
					/*
					 * 躲避  100%-（0~1随机数%）*n>(0~1随机数%)  每使用一次后躲避率下降一定值，n是次数>=1
					 	如果大于后面随机数，躲避成功。
					 */
					double num1 = 100.0-Math.random()*100*orderCount;
					double num2 = Math.random()*100;
					isAttacked = num1>num2? 2:1;
				}
			}else{//防御方采取攻击应对
				//1.将防御方连续计数的ABC三操作计数重置为1
				orderMap.put("a", 0);
				orderMap.put("b", 0);
				orderMap.put("c", 0);
				//2.获取攻击类型 
				String defType = (String) defMap.get("atkType");
				Skill aSkill = atkSkill[Integer.parseInt(atkOrder)-1];//得到攻击技能
				String aSkillOriType = aSkill.getSkillOriType();
				String atkSType = aSkill.getAtkType();//物攻，特攻
				Skill dSkill = defSkill[Integer.parseInt(defOrder)-1];//得到防御方应对技能
				String dSkillOriType = dSkill.getSkillOriType();
				String defSType =dSkill.getAtkType();//物攻，特攻
				double a2dSkill = Attributes.attrMap.get(aSkillOriType+"-"+dSkillOriType);
				int defValue = 0;//得到防御值
				int atkValue = atkP.getBlood();
				if(atkSType.equals("atk")){//物攻
					defValue = defP.getAtk();//物防
					atkValue += atkP.getAtk();
				}else if(atkSType.equals("satk")){//特攻
					defValue = defP.getSatk();//特防
					atkValue += atkP.getSatk();
				}
				//进攻攻击 ： (体力+物攻or特攻*(0~1随机值))*技能相克度*0.6>命中率
				int blood = defP.getBlood();
				double q = 0.5;
				int defAtkValue = 0;//防御方攻击力
				if(defSType.equals("atk")){
					defAtkValue = defP.getAtk();
				}else if(defSType.equals("satk")){
					defAtkValue = defP.getSatk();
				}
				if(defType.equals("atk")){
					q = 0.6;
				}
				//防御攻击： (体力+物攻or特攻*随机值（0~1）)*技能相克度*0.8>命中率
				else if(defType.equals("def")){
					q = 0.8;
				}
				double dodgeProbability = (blood+defAtkValue*Math.random())*q*a2dSkill;
				if(dodgeProbability>atkValue) isAttacked=2;
				else isAttacked = 1;
			}
		}
		if(isAttacked==1) return true;
		else return false;
	}
}
