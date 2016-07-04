package com.zheng.memcached.consistent;

/**
 * 发布接口，维护整个服务器虚拟节点以及根据键值得到某一个指定的存储服务器节点
 * 
 * @author zhenglian
 *
 */
public interface Distribution {
	public String lookUp(String key);

	public void deleteNode(String node);

	public void addNode(String node);
}
