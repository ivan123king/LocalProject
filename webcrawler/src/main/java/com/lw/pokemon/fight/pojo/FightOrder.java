package com.lw.pokemon.fight.pojo;
/**
 * 训练家的对战指令
 * @author lenovo
 *
 */
public class FightOrder {
	/* 对战指令（虚拟对象）：界面展示可见的，训练家下达的指令：
		动作指令：接近，远离，闪避，攻击   
				 这是指挥精灵动作，攻击时需要用到下面攻击指令
		攻击指令：精灵技能
	 */
	//指挥精灵动作指令
	public static enum moveOrder{close,away,dodge,attack};
	//指挥精灵攻击指令
	public static enum attackOrder{none,skill};
}
