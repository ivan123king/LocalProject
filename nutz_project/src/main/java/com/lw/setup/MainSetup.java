package com.lw.setup;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

/**
 * 工程启动需要做的预先工作
 * @author king
 *
 */
public class MainSetup implements Setup{

	@Override
	public void destroy(NutConfig nc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(NutConfig nc) {
		//获取IOC容器
		Ioc ioc = nc.getIoc();
		Dao dao = ioc.get(Dao.class);
		//创建包com.lw.pojo下的实体类对应表,false表示如果有表就不创建
		Daos.createTablesInPackage(dao, "com.lw.pojo", false);
		
	}

}
