package com.zheng.memcached.utils;

import java.util.Date;

import com.danga.MemCached.MemCachedClient;
import com.zheng.memcached.domain.Memcached;

public class MemcachedManager {

		public static MemCachedClient getClient() {
			return Memcached.getInstance().getClient();
		}
		
		public static Object get(String key) {
			return getClient().get(key);
		}
		
		public static boolean set(String key ,Object value) {
			return getClient().set(key, value);
		}
		
		public static boolean set(String key, Object value, long time) {
			return getClient().set(key, value, new Date(time));
		}
		
		public static boolean delete(String key) {
			return getClient().delete(key);
		}
}
