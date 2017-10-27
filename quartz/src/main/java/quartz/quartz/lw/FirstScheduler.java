package quartz.quartz.lw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

public class FirstScheduler implements Runnable{
	private static final String fileName = "config.properties";//配置文件
	
	
	private void createScheduler(Class c,Date startTime, Date endTime, int minutes,
			int repeatType) {
		// 创建一个jobDetail实例 ，将该实例与HelloJob绑定
		JobDetail jobDetail = JobBuilder.newJob(c)
				.withIdentity(c.getName(), "group1").build();
		// 创建Trigger实例
		SimpleScheduleBuilder ssBuilder = SimpleScheduleBuilder
				.simpleSchedule().withIntervalInSeconds(minutes);
		switch (repeatType) {
		case 1://永久执行
			ssBuilder.repeatForever();
			break;
		case 2://执行一次
			ssBuilder.repeatSecondlyForTotalCount(1);
			break;
		}
		TriggerBuilder tb = TriggerBuilder.newTrigger()
				.withIdentity(c.getName(), "group1");
		if(startTime!=null) tb.startAt(startTime);
		if(endTime!=null) tb.endAt(endTime);
		tb.withSchedule(ssBuilder);
		Trigger trigger = tb.build();
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

	public void run() {
		File configFile = new File("src/main/java/quartz/quartz/lw/"+fileName);
		try {
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			br.readLine();
			String fContent = br.readLine().trim();
			while(fContent!=null){
				if(fContent!=null&&!fContent.equals("")){
					String values[] = fContent.split(",");
					if(values!=null&&values.length>0){
						String className = null;
						String interValMinutes = null;
						String startTime = null;
						String endTime = null;
						
						if(values.length>=1&&values.length<=4){//立即执行，只执行一次
							className = values[0];
						}
						if(values.length>=2&&values.length<=4){
							interValMinutes = values[1];
						}
						if(values.length>=3&&values.length<=4){//指定开始时间
							startTime = values[2];
						}
						if(values.length==4){//指定开始和结束时间
							endTime = values[3];
						}
						if(values.length>4){
							throw new Exception("配置参数不合法！："+fContent);
						}
						Class c = Class.forName(className);
						if(c.newInstance() instanceof FirstJob){
							Date sTime = null;
							if(startTime!=null&&!"".equals(startTime)){
								sTime = new Date();
								sTime.setTime(sTime.getTime()+Long.parseLong(startTime));
							}
							Date eTime = null;
							if(endTime!=null&&!"".equals(endTime)){
								eTime = new Date();
								eTime.setTime(eTime.getTime()+Long.parseLong(endTime));
							}
							int intValMin = 10;
							if(interValMinutes!=null&&!"".equals(interValMinutes)){
								intValMin = Integer.parseInt(interValMinutes);
							}
							createScheduler(c,
									sTime,eTime,
									intValMin,
									1);
						}
					}
				}
				fContent = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		FirstScheduler fs = new FirstScheduler();
		new Thread(fs).start();
	
	}

}
