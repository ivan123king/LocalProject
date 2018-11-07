package com.lw;

import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.lw.setup.MainSetup;

/**
 * nutz的入口类
 * @author king
 *
 */
@Localization(value="i18n/",defaultLocalizationKey="zh_CN")//国际化
@IocBy(type=ComboIocProvider.class,args= {
		"*js","ioc/",//*js 是JsonIocLoader,负责加载js/json结尾的ioc配置文件
		"*anno","com.lw",//*anno 是AnnotationIocLoader,负责处理注解式Ioc, 例如@IocBean
		"*tx",//事物拦截aop
		"*async"//异步执行aop
})
@SetupBy(value=MainSetup.class)//设置启动预先做的一些事
@Modules(scanPackage=true)//模块类自动扫描，添加这行注解nutz将自动扫描主模块所在包下所有的子模块以及入口函数（带有@At注解
public class MainModule {

}
