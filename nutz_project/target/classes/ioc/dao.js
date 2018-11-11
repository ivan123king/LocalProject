/**
 * 1. 数据库使用统一配置
 */
var ioc = {
		conf:{
			type:"org.nutz.ioc.impl.PropertiesProxy",
			fields:{
				paths:["src/main/resources/"]
			}
		},
		dataSource:{
			factory:"$conf#make",
			args:["com.alibaba.druid.pool.DruidDataSource","db."],
			type:"com.alibaba.druid.pool.DruidDataSource",
			events:{
				create:"init",
				depose:"close"
			}
//			fields:{
//				url:"jdbc:mysql://localhost:3306/demo?characterEncoding=utf8&serverTimezone=UTC"
//				username:"root",
//				password:"root",
//				testWhileIdle:true,//预防mysql的8小时timeout问题
//				maxActive:100
//			}
		},
		dao:{
			type: "org.nutz.dao.impl.NutDao",
			args:[{refer:"dataSource"}]
		}
}