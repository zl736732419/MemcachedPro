package com.zheng.memcached.consistent;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 通过传统的加密方式md5/sha来生成散列的数
 * 需要说明的是，通过messagedigest生成的是16字节长度的bytes, 那么其实可以分成4组生成4个不同的long
 * 所以为了充分利用字节数据，可以将虚拟节点分成4个一组，一个虚拟节点名称生成4个对应的hash随机数对应服务器节点
 * 
 * nodeName 为当前真实服务器名称
 * bytes 为生成的16字节加密bytes
 * virtualNodeNums 为一个服务器节点生成的虚拟节点数，这里假定为64
 * node为真实添加的服务器节点名次
 * SortedMap<Long, String> virtualNodes; //为环形上虚拟节点对应的hash排序列表
 * 
 * 
 * byte[] bytes = null;
 * String virtualNodeName = null;
 * long num = -1L;
 * for(int i = 0; i < virtualNodeNums / 4; i++) {
 * 	virtualNodeName = getVirtualNodeName(nodeName, i); // nodeName + "-" + i;
 *  bytes = MessageDigestUtils.encrypt(virtualNodeName);
 *  for(int h = 0; h < 4; h++) {
 *  	num = ((long)(bytes[3 + 4*h]) << 24)
 *  		| ((long)(bytes[2 + 4*h) << 16)
 *  		| ((long)(bytes[1 + 4*h) << 8)
 *  		| (long)(bytes[0] & 0xff);
 *  
 *  	virtualNodes.put(num, nodeName);
 *  }
 * 
 * }
 * 
 * 
 * 
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
