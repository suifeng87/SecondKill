package com.atguigu.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;

/** 
  ^_^ 2017年3月25日 ^_^ 下午6:16:27 ^_^ 
 */
public class JedisTestAPI {

	Jedis jedis = new Jedis("192.168.119.128", 6379);
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// 连接本地的Redis服务
//		Jedis jedis = new Jedis("192.168.119.128", 6379);
		// 查看服务是否运行，打出PONG 表示OK
//		System.out.println("测试Redis是否连通：" + jedis.ping());
	}

	@Test
	public void testKey() {
		Set<String> keys = jedis.keys("*");
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = jedis.get(key);
			System.out.println("存在set中的字符序列key= "+key + " ,values= " + value);
		}
		System.out.println("jedis.exists测试k2是否存在：============>" + jedis.exists("k2"));
		System.out.println("k1的存活时间是：" + jedis.ttl("k1"));
	}
	
	@Test
	public void testString() {
		System.out.println(jedis.get("k1"));
		jedis.set("k4", "k4_Redis_Eclipse");
		System.out.println("----------");
		jedis.msetnx("str1","v1","str2","v2","str3","v3");
		System.out.println("返回List集合String：" + jedis.mget("str1","str2","str3"));
	}
	
	@Test
	public void testList() {
		System.out.println("给list集合添加数据");
		jedis.lpush("mylist", "list1","list2","list3","list4","list5");
		System.out.println("-1取出所有数据,也可以取出指定索引的数据");
		List<String> list = jedis.lrange("mylist", 0, -1);
		for (String string : list) {
			System.out.println(string);
		}
	}
	
	@Test
	public void testSet() {
		jedis.sadd("orders", "jd001");
		jedis.sadd("orders", "jd002");
		jedis.sadd("orders", "jd003");
		// 取出所有值
		Set<String> set = jedis.smembers("orders");
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
		}
		System.out.println("orders集合中元素的个数" + jedis.smembers("orders").size());
		// 在集合中移除指定值
		jedis.srem("orders", "jd002");
		System.out.println("orders集合中元素的个数" + jedis.smembers("orders").size());
	}
	
	@Test
	public void testHash() {
		// 集合名 属性 值
		jedis.hsetnx("hash1", "userName", "lisi");
		System.out.println(jedis.hget("hash1", "userName"));
		Map<String,String> map = new HashMap<>();
		map.put("telphone", "1381121421");
		map.put("address", "北京");
		map.put("email", "xiaoming@163.com");
		jedis.hmset("hash2", map);
		// 如果map的key写错了，输出结果为null
		List<String> list = jedis.hmget("hash2", "telphone","address","email");
		for (String str : list) {
			System.out.println(str);
		}
	}
	
	@Test
	public void testZset() {
		// 保存的时候评分也可以任意指定 输出的时候按照平均升序输出
		jedis.zadd("zset01", 60d, "v1");
		jedis.zadd("zset01", 70d, "v2");
		jedis.zadd("zset01", 10d, "v3");
		jedis.zadd("zset01", 90d, "v4");
		Set<String> zrange = jedis.zrange("zset01", 0, -1);
		for (Iterator iterator = zrange.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
		}
		
		
	}
}
