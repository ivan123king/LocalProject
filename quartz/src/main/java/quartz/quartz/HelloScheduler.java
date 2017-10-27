package quartz.quartz;

import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class HelloScheduler {
	public static void main(String[] args) {
		// 创建一个jobDetail实例 ，将该实例与HelloJob绑定
		JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
				.withIdentity("myJob", "group1")
				.usingJobData("message", "hello myJob1")
				.usingJobData("mathJobValue", 3.14F).build();
		// 创建Trigger实例
		Date startDate = new Date();
		startDate.setTime(startDate.getTime()+3000);//当前时间加上3秒
		Date endDate = new Date();
		endDate.setTime(endDate.getTime()+6000);//当前时间加上六秒
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("myTrigger", "group1")
				.startAt(startDate)//设置启动开始时间
				.endAt(endDate)//设置此进程超过此时间停止
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(2).repeatForever())
				.build();
		// 创建Schedule实例
		SchedulerFactory sfact = new StdSchedulerFactory();
		Scheduler scheduler = null;
		try {
			scheduler = sfact.getScheduler();
			scheduler.start();
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}
}
