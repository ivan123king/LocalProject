package com.lw.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.builder.InitializingObject;
import org.apache.ibatis.cache.Cache;

/**
 * 自定义mybatis的缓存类
 * 
 * @author lenovo
 *
 */
public class MyMybatisCache implements Cache, InitializingObject {

	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private ConcurrentHashMap<Object, Object> cache = new ConcurrentHashMap<Object, Object>();
	private String id;

	public MyMybatisCache() {}
	public MyMybatisCache(String id) {
		this.id =id;
	}

	public void clear() {
		cache.clear();
	}

	public String getId() {
		return this.id;
	}

	public Object getObject(Object arg0) {
		return null;
	}

	public ReadWriteLock getReadWriteLock() {
		return lock;
	}

	public int getSize() {
		return cache.size();
	}

	public void putObject(Object key, Object value) {
		cache.put(key, value);
	}

	public Object removeObject(Object key) {
		return cache.remove(key);
	}

	public void initialize() throws Exception {

	}

	public void setCacheFile(String file) throws Exception {

	}
}
