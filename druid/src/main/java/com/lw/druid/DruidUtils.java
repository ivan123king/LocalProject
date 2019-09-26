package com.lw.druid;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.lw.bean.DatabaseConn;
import com.lw.bean.DatabaseType;

import lombok.extern.slf4j.Slf4j;

/**
 * druid操作
 * 
 * @author Administrator
 *
 */
@Slf4j
public class DruidUtils {

	/**
	 * 同步锁
	 */
	private static final Lock mLock = new ReentrantLock();

	public static DataSource getDataSource(DatabaseConn dbConn)
			throws SQLException, Exception {
		DataSource ds = null;

		try {
			// 获取读写锁
			mLock.lock();

			String driver = ""; // jdbc驱动类名
			String url = ""; // 连接URL

			// 设置driver和url
			if (StringUtils.containsIgnoreCase(dbConn.getType(),
					DatabaseType.MSSQL)) {
				driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				url = MessageFormat.format(
						"jdbc:sqlserver://{0}:{1}; DatabaseName={2}",
						new Object[] { dbConn.getServer(), dbConn.getPort(),
								dbConn.getDatabase() });
			} else if (StringUtils.containsIgnoreCase(dbConn.getType(),
					DatabaseType.MYSQL)) {
				driver = "com.mysql.jdbc.Driver";
				url = MessageFormat.format("jdbc:mysql://{0}:{1}/{2}",
						new Object[] { dbConn.getServer(), dbConn.getPort(),
								dbConn.getDatabase() });
				// url = url +
				// "?autoReconnect=true&failOverReadOnly=false&maxReconnects=10&connectTimeout=120000&socketTimeout=120000";
				// //防止获取不到连接
				url = url
						+ "?characterEncoding=utf8&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&connectTimeout=120000&socketTimeout=120000&useSSL=false"; // 防止获取不到连接
			} else if (StringUtils.containsIgnoreCase(dbConn.getType(),
					DatabaseType.ORACLE)) {
				driver = "oracle.jdbc.driver.OracleDriver";
				url = MessageFormat.format("jdbc:oracle:thin:@{0}:{1}:{2}",
						new Object[] { dbConn.getServer(), dbConn.getPort(),
								dbConn.getDatabase() });
			} else if (StringUtils.containsIgnoreCase(dbConn.getType(),
					DatabaseType.DB2)) {
				driver = "com.ibm.db2.jcc.DB2Driver";
				url = MessageFormat.format("jdbc:db2://{0}:{1}/{2}",
						new Object[] { dbConn.getServer(), dbConn.getPort(),
								dbConn.getDatabase() });
				url = url
						+ ":progressiveStreaming=2;retrieveMessagesFromServerOnGetMessage=true;"; // 添加一些附加属性
			} else if (StringUtils.containsIgnoreCase(dbConn.getType(),
					DatabaseType.SYBASE)) {
				driver = "com.sybase.jdbc2.jdbc.SybDriver";
				url = MessageFormat.format("jdbc:sybase:Tds:{0}:{1}/{2}",
						new Object[] { dbConn.getServer(), dbConn.getPort(),
								dbConn.getDatabase() });
			}

			if (StringUtils.isNotEmpty(driver) && StringUtils.isNotEmpty(url)) {
				// 设置连接池配置信息
				Properties pro = new Properties();
				pro.setProperty("driverClassName", driver);
				pro.setProperty("url", url);
				pro.setProperty("username", dbConn.getUsername());
				pro.setProperty("password", dbConn.getPassword());

				pro.setProperty("initialSize",
						String.valueOf(5)); // 初始化连接数
				pro.setProperty("minldle",
						String.valueOf(5)); // 最小连接数
				pro.setProperty("maxActive",
						String.valueOf(5)); // 最大连接数
				pro.setProperty("maxWait",
						String.valueOf(3000)); // 最大等待时间

				// 防止连接池溢出添加回收机制
				pro.setProperty("removeAbandoned", "true"); // 是否关闭未关闭的连接，默认为false，如果设置为true，即超过removeAbandonedTimeout的连接进行强制关闭.
				pro.setProperty("removeAbandonedTimeout", String.valueOf(1000)); // 默认为300(单位秒)即5分钟
				pro.setProperty("logAbandoned", "false"); // 关闭连接是否记录日志,
															// 默认为false

				// 指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败, 则连接将被从池中去除.
				// 注意: 设置为true后如果要生效,validationQuery参数必须设置为非空字符串
				// <property name="testWhileIdle" value="true" />
				pro.setProperty("testWhileIdle", "true");

				// 指明是否在从池中取出连接前进行检验,如果检验失败 则从池中去除连接并尝试取出另一个. 注意:
				// 设置为true后如果要生效,validationQuery参数必须设置为非空字符串
				pro.setProperty("testOnBorrow", "true");

				// 连接Oracle、mssql时需要设置validationQuery，否则会报'testWhileIdle is
				// true, validationQuery not set'错误提示信息
				if (StringUtils.containsIgnoreCase(dbConn.getType(),
						DatabaseType.ORACLE)) {
					pro.setProperty("validationQuery", "select 1 from dual");
				} else if (StringUtils.containsIgnoreCase(dbConn.getType(),
						DatabaseType.MYSQL)
						|| StringUtils.containsIgnoreCase(dbConn.getType(),
								DatabaseType.MSSQL)) {
					pro.setProperty("validationQuery", "select 1");
				} else if (StringUtils.containsIgnoreCase(dbConn.getType(),
						DatabaseType.DB2)) {
					pro.setProperty("validationQuery",
							"select 1 from sysibm.sysdummy1");
				} else if (StringUtils.containsIgnoreCase(dbConn.getType(),
						DatabaseType.SYBASE)) {
					pro.setProperty("validationQuery", "select 1");
				}

				// 获取DataSource
				ds = new DruidDataSource();
				DruidDataSource druidDataSource = (DruidDataSource) ds;
				DruidDataSourceFactory.config(druidDataSource, pro);

				druidDataSource.setConnectionErrorRetryAttempts(2); // 重试次数
				druidDataSource.setBreakAfterAcquireFailure(true);
				
				createThread(druidDataSource);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			mLock.unlock();
		}

		return ds;
	}
	
	/**
	 * 创建druid线程监测连接状况
	 */
	private static void createThread(DruidDataSource ds){
		 ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		 ds.setCreateScheduler(service);
	}

}
