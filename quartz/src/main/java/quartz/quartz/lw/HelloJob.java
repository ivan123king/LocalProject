package quartz.quartz.lw;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements FirstJob{

	public void execute(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("HelloJob:"+df.format(new Date()));
	}

}
