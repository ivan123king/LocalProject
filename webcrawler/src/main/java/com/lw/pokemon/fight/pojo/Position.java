package com.lw.pokemon.fight.pojo;

/**
 * 位置类型
 * @author lenovo
 *
 */
public class Position implements Cloneable{
	protected int x;
	protected int y;
	protected int z;
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
