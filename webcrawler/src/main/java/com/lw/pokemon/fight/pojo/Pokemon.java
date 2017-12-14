package com.lw.pokemon.fight.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 宠物小精灵基类
 * @author lenovo
 */
public class Pokemon implements Cloneable{
	private String name;
	private Integer blood;//血条（体力）
	private Integer speed;//速度
	private Integer bodilyForm;//体型
	private Integer bodilyShape;//体态
	private Integer[] nature;//属性  对应ConstVariable中pokemonType
	private Double weight;//体重
	private Integer atk;//物攻
	private Integer def;//物防
	private Integer satk;//特攻
	private Integer sdef;//特防
	private Integer intimacy;//亲密度
	private Integer psychokinesis;//意志力
	private Integer feature;//特性
	private Integer status;//状态
	private Integer feeling;//心情
	private Position position;//位置，坐标
	private Map map = new HashMap();//可能会添加动作，技能等
	private List<Object> list = new ArrayList<Object>();
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	@Override
	public String toString() {
		return "Pokemon [blood=" + blood + ", speed=" + speed + ", bodilyForm="
				+ bodilyForm + ", bodilyShape=" + bodilyShape + ", weight="
				+ weight + ", atk=" + atk + ", def=" + def + ", satk=" + satk
				+ ", sdef=" + sdef + ", intimacy=" + intimacy
				+ ", psychokinesis=" + psychokinesis + ", feature=" + feature
				+ ", status=" + status + ", feeling=" + feeling + "]";
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBlood() {
		return blood;
	}
	public void setBlood(Integer blood) {
		this.blood = blood;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public Integer getBodilyForm() {
		return bodilyForm;
	}
	public void setBodilyForm(Integer bodilyForm) {
		this.bodilyForm = bodilyForm;
	}
	public Integer getBodilyShape() {
		return bodilyShape;
	}
	public void setBodilyShape(Integer bodilyShape) {
		this.bodilyShape = bodilyShape;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getAtk() {
		return atk;
	}
	public void setAtk(Integer atk) {
		this.atk = atk;
	}
	public Integer getDef() {
		return def;
	}
	public void setDef(Integer def) {
		this.def = def;
	}
	public Integer getSatk() {
		return satk;
	}
	public void setSatk(Integer satk) {
		this.satk = satk;
	}
	public Integer getSdef() {
		return sdef;
	}
	public void setSdef(Integer sdef) {
		this.sdef = sdef;
	}
	public Integer getIntimacy() {
		return intimacy;
	}
	public void setIntimacy(Integer intimacy) {
		this.intimacy = intimacy;
	}
	public Integer getPsychokinesis() {
		return psychokinesis;
	}
	public void setPsychokinesis(Integer psychokinesis) {
		this.psychokinesis = psychokinesis;
	}
	public Integer getFeature() {
		return feature;
	}
	public void setFeature(Integer feature) {
		this.feature = feature;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getFeeling() {
		return feeling;
	}
	public void setFeeling(Integer feeling) {
		this.feeling = feeling;
	}
	public Integer[] getNature() {
		return nature;
	}
	public void setNature(Integer nature[]) {
		this.nature = nature;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	
	
	
}
