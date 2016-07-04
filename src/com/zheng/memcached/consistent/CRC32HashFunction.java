package com.zheng.memcached.consistent;

import java.util.zip.CRC32;

/**
 *  通过crc32生成对应的数字
 * @author zhenglian
 *
 */
public class CRC32HashFunction implements HashFunction {

	@Override
	public long hashToInt(String str) {
		CRC32 crc32 = new CRC32();
		crc32.update(str.getBytes());
		long value = crc32.getValue();
		
		return value;
	}
}
