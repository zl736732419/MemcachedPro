package com.zheng.memcached.consistent;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash implements Distribution {

	private HashFunction hashFun;
	private int virtualNodeNums; //虚拟节点数
	
	private SortedMap<Long, String> nodes = new TreeMap<>(); //用于存放虚拟节点 
	
	public ConsistentHash(HashFunction hashFun, int virtualNodeNums) {
		this.hashFun = hashFun;
		this.virtualNodeNums = virtualNodeNums;
	}

	@Override
	public String lookUp(String key) {
		if(nodes.isEmpty()) {
			return null;
		}
		long hash = this.hashFun.hashToInt(key);
		if(hash == -1L) {
			System.out.println("错误!");
			return null;
		}
		String node = null; 
		long keyNum = -1;
		String serverNode = null;
		for(Map.Entry<Long, String> entry : nodes.entrySet()) {
			serverNode = entry.getValue();
			if(node == null) {
				node = serverNode;
			}
			
			keyNum = entry.getKey().longValue();
			if(hash <= keyNum) {
				node = serverNode;
				break;
			}
		}
		
		return node;
	}

	@Override
	public void deleteNode(String node) {
		//这里需要重新优化
		Set<Long> deleteKeys = new HashSet<>();
		Iterator<Map.Entry<Long, String>> iterator = nodes.entrySet().iterator();
		Map.Entry<Long, String> entry = null;
		while(iterator.hasNext()) {
			entry = iterator.next();
			if(entry.getValue().equals(node)) {
				iterator.remove();
			}
		}
	}

	@Override
	public void addNode(String node) {
		//生成virtualNodeNums个虚拟节点，要求均衡的分布在圆环周围
		String virtualNodeName = null;
		long key = -1L;
		for(int i = 0; i < this.virtualNodeNums; i++) { 
			virtualNodeName = node + "-" + i;
			key = this.hashFun.hashToInt(virtualNodeName);
			if(key == -1L) {
				System.out.println("错误!");
				return;
			}
			nodes.put(key, node);
		}
	}
	
	
	public static void main(String[] args) {
//		HashFunction hashFun = new CRC32HashFunction();
		HashFunction hashFun = new MessageDigestHashFunction(MessageDigestHashFunction.ALGORITHM_MD5);
		ConsistentHash hash = new ConsistentHash(hashFun, 64);
		hash.addNode("A");
		hash.addNode("B");
		hash.addNode("C");
		System.out.println("当前所有节点为:");
		System.out.println(hash.nodes);
		
		String key = "hello";
		long hashKey = hash.hashFun.hashToInt(key);
		if(hashKey == -1L) {
			System.out.println("错误!");
			return;
		}
		System.out.println("当前元素" + key + "生成的数字为:" + hashKey);
		System.out.println("-----系统找到的对应节点为：");
		System.out.println(hash.lookUp(key));
		
	}
	
}
