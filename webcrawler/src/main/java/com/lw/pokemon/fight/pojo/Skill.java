package com.lw.pokemon.fight.pojo;

import com.lw.pokemon.fight.constv.ConstVariable;

/**
 * 技能类型
 * @author lenovo
 *
 */
public class Skill{

	private String name;//技能名称
	//技能类型  对应ConstVariable中的skillType
	private String skillType; 
	//技能类型，毒，火，超能力这些   对应ConstVariable中的skillOriType
	private String skillOriType;
	
	private String atkType;//物攻，特攻
	private String atkArea;//范围攻击，单体攻击
	private String atkDistance;//远程攻击，近程攻击
	
	private int power;//技能基础威力 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSkillType() {
		return skillType;
	}
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}
	public String getSkillOriType() {
		return skillOriType;
	}
	public void setSkillOriType(String skillOriType) {
		this.skillOriType = skillOriType;
	}
	public String getAtkType() {
		return atkType;
	}
	public void setAtkType(String atkType) {
		this.atkType = atkType;
	}
	public String getAtkArea() {
		return atkArea;
	}
	public void setAtkArea(String atkArea) {
		this.atkArea = atkArea;
	}
	public String getAtkDistance() {
		return atkDistance;
	}
	public void setAtkDistance(String atkDistance) {
		this.atkDistance = atkDistance;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	
	
}
