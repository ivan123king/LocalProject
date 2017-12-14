package com.lw.pokemon.fight.utils;

import java.util.Random;

/**
 * 工具类
 * @author lenovo
 *
 */
public class OU {

	public static Random r = new Random();
	public static int getRandom(int in){
		if(in<=0){
			return r.nextInt();
		}else{
			return r.nextInt(in);
		}
	}
	
	public static void main(String[] args) {
		int a = OU.getRandom(1);
		System.out.println(Math.random());
	}
}
