package com.lw.designer.factory.simple;


/**
 * 发型工厂：生成各种发型
 * 
 * @author lenovo
 *
 */
public class HairFactory {

	/**
	 * 方法一、 根据关键字获取发型
	 * @param key
	 * @return
	 */
	public HairInterface getHair(String key) {
		HairInterface hi = null;
		if ("left".equalsIgnoreCase(key)) {
			hi = new LeftHair();
		} else if ("right".equalsIgnoreCase(key)) {
			hi = new RightHair();
		}
		return hi;
	}

	/**
	 * 方法二、根据类名来获取发型
	 * @param className eg. com.lw.designer.factory.LeftHair
	 * @return
	 */
	public HairInterface getHairByClass(String className) {
		HairInterface hi = null;;
		try {
			hi = (HairInterface) Class.forName(className)
					.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return hi;
	}
}
