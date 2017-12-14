package com.lw.pokemon.fight.pojo;

/**
 * 精灵攻击
 *		 方向（前后，左右，上下）  技能的攻击方向，一般朝着对手目前所在方向攻击
		距离（接近，远离）
		攻击类型：防御攻击，进攻攻击
 * @author lenovo
 *
 */
public class Attack {
	 //方向（2左 3右，4上  5下）  技能的攻击方向
	public static enum attackment{left,right,up,down}; 
	//攻击类型  1防御攻击，0进攻攻击
	public static enum attacktype{atk,def};
}
