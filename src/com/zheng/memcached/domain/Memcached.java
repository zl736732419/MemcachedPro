package com.zheng.memcached.domain;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * java 连接memcached工具下载地址：
 * https://github.com/gwhalin/Memcached-Java-Client/downloads
 * 
 * @author zhenglian
 */
public class Memcached {
	private static Memcached instance = null;

	private MemCachedClient client = new MemCachedClient();

	static {
		String servers[] = {"192.168.1.200:11211"};
		Integer[] weights = {3};
		SockIOPool pool = SockIOPool.getInstance();
		//设置服务器信息
		pool.setServers(servers);
		pool.setWeights(weights);
	
		//设置初始连接数，最小最大连接数，最大处理时间
		pool.setInitConn(5);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaxBusyTime(6*60*60*1000);
		
		//设置主线程睡眠时间
		pool.setMaintSleep(30);
		
		pool.setNagle(false);
		pool.setSocketTO(30);
		pool.setSocketConnectTO(0);
		
		pool.initialize();
		
	}
	
	
	private Memcached() {
	}

	public static Memcached getInstance() {
		if (instance == null) {
			instance = new Memcached();
		}

		return instance;
	}

	public MemCachedClient getClient() {
		return client;
	}

}
