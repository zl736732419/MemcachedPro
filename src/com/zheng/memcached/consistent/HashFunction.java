package com.zheng.memcached.consistent;

public interface HashFunction {
	/**
	 * 给定一个字符串，转化为对应的数字表示形式
	 * @param str
	 * @return
	 */
	public long hashToInt(String str);
}
