package com.lw.pokemon.fight.pojo;

/**
 * 对战场地情况
 * 天气情况：雨，雪，冰雹，沙尘，干燥，晴朗，阴云
		地面情况：青草，树木，荒野，沙漠，水
		时间情况：白天，黑夜
		领域情况：无领域，电气领域，青草领域，水之领域
 * @author lenovo
 */
public class Field {

	private String weather;//天气情况
	private String ground;//地面情况
	private String day;//时间情况
	private String domain;//领域情况
	
	@Override
	public String toString() {
		return "Field [weather=" + weather + ", ground=" + ground + ", day="
				+ day + ", domain=" + domain + "]";
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getGround() {
		return ground;
	}
	public void setGround(String ground) {
		this.ground = ground;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
}
