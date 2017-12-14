package com.lw.pokemon.fight.pojo;

import java.util.Random;

/**
 * 小精灵动作 对应训练家的指令：接近，远离，闪避，攻击
 * @author lenovo
 *
 */
public class PokemonMovement {
	//方向（前后，左右）（飞行水里还包括：上下）
	public static enum direction{front,behind,left,right,up,down};
	public static enum dodge{dodge};//闪避
	
	public static void main(String[] args) {
		//利用这种方法，随机让小精灵行动
		direction[] d = PokemonMovement.direction.values();
		Random r = new Random();
		int i=10;
		while(i-->0){
			int nextdir = r.nextInt(d.length);
			System.out.println(d[nextdir]);
		}
	}
}
