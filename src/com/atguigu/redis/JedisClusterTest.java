package com.atguigu.redis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/** 
  ^_^ 2017年3月26日 ^_^ 下午2:36:08 ^_^ 
 */
public class JedisClusterTest {

	public static void main(String[] args) {
		Set<HostAndPort> set = new HashSet<>();
		set.add(new HostAndPort("192.168.119.128", 6379));
		JedisCluster jedisCluster = new JedisCluster(set);
		
		jedisCluster.set("k1", "java");
		System.out.println(jedisCluster.get("k1"));

	}

}
