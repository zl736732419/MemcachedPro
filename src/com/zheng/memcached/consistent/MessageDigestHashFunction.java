package com.zheng.memcached.consistent;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 通过传统的加密方式md5/sha来生成散列的数
 * @author zhenglian
 *
 */
public class MessageDigestHashFunction implements HashFunction {

	public static final String ALGORITHM_MD5 = "MD5";
	public static final String ALGORITHM_SHA = "SHA";
	
	private String algorithm;
	
	public MessageDigestHashFunction(String algorithm) {
		this.algorithm = algorithm;
	}
	
	@Override
	public long hashToInt(String str) {
		long num = -1L;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			
			md.update(str.getBytes(Charset.forName("UTF-8")));
			byte[] bytes = md.digest();
			
			num = bytes[3] << 24
					| ((long)bytes[2] << 16)
					| ((long)bytes[1] << 8)
					| (long)(bytes[0] & 0xff);
			//选择前4个字节生成2^32数字
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return num;
	}

}
