package com.lw.spring.springframework.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class MyAspectJ {

	/**
	 * 前置通知
	 */
	public void myBefore(JoinPoint joinPoint){
		System.out.println("前置通知：模拟权限检查.");
		System.out.print("目标类是："+joinPoint.getTarget());
		System.out.println(", 被植入增强处理的目标方法为:"+joinPoint.getSignature().getName()+"\r\n");
	}
	
	/**
	 * 后置通知
	 */
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
	public void myAfterThrowing(JoinPoint joinPoint,Throwable e){
		System.out.println("异常通知:"+joinPoint.getTarget()+" 出错了 "+e.getMessage());
	}
	
	/**
	 * 最终通知
	 */
	public void myAfter(){
		System.out.println("最终通知：模拟方法结束后释放资源.");
	}
}
