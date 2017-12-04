package com.lw.spring.springframework.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 通过AspectJ注解来实现Spring AOP 
 * @author lenovo
 *
 */
@Aspect
@Component
public class MyAspectJByAnnotation {
	
	@Pointcut("execution(* com.lw.spring.springframework.aspect.*.*.*(..))")
	private void myPointCut(){};
	
	@Before("myPointCut()")//此处的myPointCut对应@PointCut的方法名
	public void myBefore(JoinPoint joinPoint){
		System.out.println("前置通知：模拟权限检查。");
		System.out.print("目标类："+joinPoint.getTarget());
		System.out.println(",被植入的增强处理目标方法为："+joinPoint.getSignature().getName());
	}
	
	/**
	 * 后置通知
	 */
	@AfterReturning(value="myPointCut()")
	public void myAfterReturning(JoinPoint joinPoint){
		System.out.print("后置通知：模拟日志记录. ");
		System.out.println("被植入增强处理的目标方法为:"+joinPoint.getSignature().getName());
	}
	
	/**
	 * ProceedingJoinPoint 是JoinPoint的子接口，表示可以执行目标方法
	 * @param pjp  必须接受一个ProceedingJoinPoint参数
	 * @return 必须是一个Object的返回值
	 * @throws Throwable 必须抛出此异常
	 */
	@Around("myPointCut()")
	public Object myAround(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("环绕开始，执行目标方法前，模拟开启事务。");
		Object obj = pjp.proceed();
		System.out.println("环绕结束，执行目标方法之后，模拟关闭事务。");
		return obj;
	}
	
	/**
	 * 异常通知
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(value="myPointCut()",throwing="e")
	public void myAfterThrowing(JoinPoint joinPoint,Throwable e){
		System.out.println("异常通知:"+joinPoint.getTarget()+" 出错了 "+e.getMessage());
	}
	
	/**
	 * 最终通知
	 */
	@After("myPointCut()")
	public void myAfter(){
		System.out.println("最终通知：模拟方法结束后释放资源.");
	}
}
