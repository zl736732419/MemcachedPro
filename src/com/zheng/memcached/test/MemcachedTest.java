package com.zheng.memcached.test;

import org.junit.Test;

import com.zheng.memcached.utils.MemcachedManager;

public class MemcachedTest {

	@Test
	public void set() {
		String key = "name";
		String value = "hello";
		MemcachedManager.set(key, value);
	}

	@Test
	public void testGet() {
		String key = "name";
		String value = (String) MemcachedManager.get(key);
		System.out.println(value);
	}
}
