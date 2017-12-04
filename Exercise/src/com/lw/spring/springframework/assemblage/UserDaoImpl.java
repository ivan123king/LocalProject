package com.lw.spring.springframework.assemblage;

import org.springframework.stereotype.Repository;

/**
 * @Repository("userDao")相当于xml中：
 * <bean id="userDao" class="com.lw.spring.springframework.assemblage.UserDaoImpl"/>
 * @author lenovo
 *
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao{

	@Override
	public void save() {
		System.out.println("userDao save function.");
	}

}
