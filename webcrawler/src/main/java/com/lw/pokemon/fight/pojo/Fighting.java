package com.lw.pokemon.fight.pojo;

import java.util.Random;
import java.util.Scanner;

import com.lw.pokemon.fight.constv.ConstVariable;
import com.lw.pokemon.fight.constv.ConstVariable.day;
import com.lw.pokemon.fight.constv.ConstVariable.ground;
import com.lw.pokemon.fight.constv.ConstVariable.weather;
import com.lw.pokemon.fight.pojo.Attack.attackment;
import com.lw.pokemon.fight.pojo.Attack.attacktype;
import com.lw.pokemon.fight.pojo.PokemonMovement.direction;
import com.lw.pokemon.fight.utils.OU;

/**
 * 开始对战
 * 1.生成对战场地
 * 2.生成对战精灵副本
 * 3.记录对战指令过程
 * 4.裁判员
 * 5.对战结束
 * 		销毁场地
 * 		销毁对战精灵副本
 * @author lenovo
 *
 */
public class Fighting{
	
	/*
	 * 1.生成对战场地
	 */
	private boolean initField = false;//表示是否初始化过本次对战场地，默认没有
	private Field field;
	//随机或者指定生成一个场地
	private void initField(Field field){
		if(field==null){//随机生成一个场地
			this.field = new Field();
			//设置天气
			weather[] weathers = ConstVariable.weather.values();
			String weather = weathers[OU.getRandom(weathers.length)].toString();
			this.field.setWeather(weather);
			//设置地面情况
			ground[] grounds = ConstVariable.ground.values();
			String ground = grounds[OU.getRandom(grounds.length)].toString();
			this.field.setGround(ground);
			//设置白天黑夜
			day[] days = ConstVariable.day.values();
			String day = days[OU.getRandom(days.length)].toString();
			this.field.setDay(day);
			//设置领域，一开始无任何领域
			this.field.setDomain(ConstVariable.domain.none.toString());
			this.initField = true;
		}else{
			this.field = field;
			this.initField = true;
		}
	}
	
	/*
	 *2.记录对战精灵副本 
	 *使用副本原因是，对战过程中可能会减物攻，减特攻等，对战结束后这些就会恢复
	 */
	private Pokemon[] pokemons = new Pokemon[2];//1VS1对战
	private void initPokemons(Pokemon p1,Pokemon p2){
		try {
			pokemons[0] = (Pokemon) p1.clone();
			pokemons[1] = (Pokemon) p2.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 3.生成裁判
	 */
	private JudgeStep judgeStep = new JudgeStep();
	
	/**
	 * 开始对战
	 */
	public void startFight(Field field,Pokemon p1,Pokemon p2){
		int count = 2;//记录对战精灵数量，全局方便以后修改
		//生成场地
		initField(field);
		//甩出两只精灵
		initPokemons(p1, p2);
		String[] order = new String[count];//两个对战指令
		Scanner sc = new Scanner(System.in);
		int round = 0;//表示回合
		
		Random r = new Random();
		int atkFirst = r.nextInt(count);
		attackment[] attackments = Attack.attackment.values();//攻击方向
		attacktype[] atkType = Attack.attacktype.values();//攻击类型
		direction[] dirs = PokemonMovement.direction.values();//移动方向
		while(true){
			if(round%2==0){
				System.out.println("先攻是"+pokemons[atkFirst].getName());
				System.out.println("训练家"+atkFirst+",发动攻击。");
				order[atkFirst] = "D";
			}else{
				System.out.println("训练家"+atkFirst+",对战指令：A接近对方，B远离对方，C闪避，D攻击");
				order[atkFirst] = sc.nextLine();
			}
			if(order[atkFirst].equalsIgnoreCase("D")){
				Skill[] skills = (Skill[]) pokemons[atkFirst].getMap().get("skills");
				System.out.print("选择精灵技能：");
				for(int index=0;index<skills.length;index++){
					System.out.print((index+1)+"-"+skills[index].getName()+"\t");
				}
				System.out.println();
				order[atkFirst] = sc.nextLine();
				//为攻击技能随机设定攻击方向
				int attackm = r.nextInt(attackments.length);
				pokemons[atkFirst].getMap().put("attackment", attackments[attackm].toString());
				//先攻一定是进攻攻击
				if(round%2==0){
					pokemons[atkFirst].getMap().put("atkType", Attack.attacktype.atk.toString());
				}else{//防御方由小精灵自己选择是防御攻击还是进攻攻击，防御攻击概率65%,后期设定是培养特训后能加大对伤害估计从而改变
					int attackType = r.nextInt(100)+1;
					String attackTypeStr = atkType[1].toString();
					if(attackType>65) attackTypeStr = atkType[0].toString();
					pokemons[atkFirst].getMap().put("atkType",attackTypeStr);
				}
			}
			//小精灵位移
			String move = dirs[r.nextInt(dirs.length)].toString();
			pokemons[atkFirst].getMap().put("movement",move);
			round++;
			if(round%2==0){//表示一回合结束，需要开始裁定两个精灵技能等
				int winner = judgeStep.judgeAttackStep(pokemons,order,atkFirst);
				
				System.out.println(pokemons[0].getName()+"->"+pokemons[0].getBlood()+"\r\n"
						+pokemons[1].getName()+"->"+pokemons[1].getBlood());
				
				
				if(winner==100){
					System.out.println("平手！");
					destroy();
					break;
				}else if(winner==0||winner==1){
					System.out.println(pokemons[winner].getName()+"胜利。");
					destroy();
					break;
				}
			}else{
				atkFirst = -(atkFirst-1);
			}
		}
	}
	
	/**
	 * 对战结束，销毁场地和精灵副本
	 */
	private void destroy(){
		for(Pokemon p:pokemons){
			if(p!=null) p = null;
		}
		if(field!=null) field = null;
	}
	
}
