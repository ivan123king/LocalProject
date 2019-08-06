package com.lw.jdbc;

import java.util.*;
import java.util.Map.Entry;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * mongoDB数据库最基本的增删改查操作
 * 以及连接数据库，关闭连接操作封装
 * @author lenovo
 *
 */
public class MGJDBC {
	private static final String IP = "118.89.108.120";
	private static final int Port = 27017;
	
	private static int connectionCount = 0;//链接数量，超过指定数量不再提供链接
	private static final int MaxConnection = 50;//最大链接数
	
	//连接池
	private static Stack<MongoClient> mList = new Stack<MongoClient>();
	
	/**
	 * 获取mongoDB数据库连接
	 * @return
	 */
	public MongoClient getMongoDBClient(){
		//如果连接池中有那么就取连接池的返回，如果没有那么就新增一个连接
		MongoClient mongoClient = null;
		if(mList!=null&&!mList.isEmpty()){
			mongoClient = mList.pop();
		}else{
			if(connectionCount<=MaxConnection){
				mongoClient = new MongoClient(IP,Port);
				connectionCount++;
			}
		}
		return mongoClient;
	}
	
	/**
	 * 关闭MongoDB连接
	 * @param mongoClient
	 */
	public boolean closeMongoDBConnection(MongoClient mongoClient){
		boolean isSuccess = true;
		try{
			if(mongoClient!=null){
				if(mList.size()<10){
					mList.add(mongoClient);
				}else mongoClient.close();
			}else isSuccess = false;
		}catch(Exception e){
			isSuccess = false;
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	/**
	 * 获取指定的数据库，如果不存在那么mongodb就会创建一个
	 * @param mongoClient
	 * @return
	 */
	public MongoDatabase getDataBase(MongoClient mongoClient,String dbName){
		return mongoClient.getDatabase(dbName);
	}
	
	/**
	 * 创建集合
	 * @param colName  集合名  不能为"error"
	 * @return  集合名
	 */
	public String createCollection(MongoDatabase mgDB,String colName){
		if(mgDB!=null&&colName!=null&&!"".equals(colName)
				&&!"error".equals(colName)){
			mgDB.createCollection(colName);
			return colName;
		}else return "error";
	}
	
	/**
	 * 得到指定的集合
	 * @param mgDB
	 * @param colName
	 * @return
	 */
	public MongoCollection<Document> getCollection(MongoDatabase mgDB,String colName){
		MongoCollection<Document> mgCollection = null;
		if(mgDB!=null&&colName!=null&&!"".equals(colName)
				&&!"error".equals(colName)){
			mgCollection = mgDB.getCollection(colName);
		}
		return mgCollection;
	}
	
	/**
	 * 插入文档即插入数据
	 * @param mgCollection
	 * @param document  Document document = new Document("key","value")
	 * @return
	 */
	public boolean insertDocument(MongoCollection<Document> mgCollection,Document document){
		boolean isSuccess = true;
		if(mgCollection!=null&&document!=null){
			mgCollection.insertOne(document);
		}else isSuccess = false;
		return isSuccess;
	}
	
	/**
	 * 插入多个文档即插入多条数据
	 * @param mgCollection
	 * @param documents
	 * @return
	 */
	public boolean insertDocuments(MongoCollection<Document> mgCollection,List<Document> documents){
		boolean isSuccess = true;
		if(mgCollection!=null&&documents!=null&&documents.size()>0){
			mgCollection.insertMany(documents);
		}else isSuccess = false;
		return isSuccess ;
	}
	
	/**
	 * 文档更新，不完善，需要修改
	 * @param mgCollection
	 * @param inPara
	 * @param document
	 * @return
	 */
	public boolean updateDocuments(MongoCollection<Document> mgCollection,Map<String,String> inPara,Document document){
		boolean isSuccess = true;
		if(mgCollection!=null&&document!=null){
			if(inPara!=null&&inPara.size()>0){
				String key = null;
				String value = null;
				String type = null;//这个表示过滤条件，是eq,glt,lt,gt等
				Bson bson = null;
				try{
					Set<Entry<String,String>> set = inPara.entrySet();
					Iterator<Entry<String,String>> it = set.iterator();
					while(it.hasNext()){
						Entry<String,String> entry = it.next();
						key = entry.getKey().split("\\|")[0];
						type = entry.getKey().split("\\|")[1];
						value = entry.getValue();
						mgCollection.updateMany(Filters.eq(key, value),new Document("$set",document));
						break;//此处只能做到单个过滤，需要修改，不完善
					}
				}catch(Exception e){
					//此处的异常捕获是为了split时出错，说明没有指定type类型
					return false;
				}
			}
		}else isSuccess = false;
		return isSuccess;
	}
	
	/**
	 * 遍历数据库中所有文档输出
	 * @param mgCollection
	 */
	public void getAllDocument(MongoCollection<Document> mgCollection){
		if(mgCollection!=null){
			FindIterable<Document> findIterable = mgCollection.find();
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while(mongoCursor.hasNext()){
				Document doc = mongoCursor.next();
				Set<Entry<String,Object>> set = doc.entrySet();
				Iterator<Entry<String,Object>> it = set.iterator();
				while(it.hasNext()){
					Entry<String,Object> entry = it.next();
					System.out.println(entry.getKey()+":"+entry.getValue());
				}
			}
		}
	}
	
	/**
	 * 删除一条记录
	 * @param mgCollection
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean deleteDocument(MongoCollection<Document> mgCollection,String key,String value){
		boolean isSuccess = true;
		if(mgCollection!=null){
			mgCollection.deleteOne(Filters.eq(key, value));
		}else isSuccess = false;
		return isSuccess;
	}
	
	/**
	 * 关闭mongoClient链接
	 * @param mongoClient
	 * @return
	 */
	public boolean closeMongoClient(MongoClient mongoClient){
		boolean isSuccess = true;
		if(mongoClient!=null){
			if(mList!=null&&mList.size()<10){//如果号池内少于10条链接，那么就放回号池
				mList.add(mongoClient);
			}else{
				mongoClient.close();
				connectionCount--;
			}
		}else isSuccess = false;
		return isSuccess;
	}
}
