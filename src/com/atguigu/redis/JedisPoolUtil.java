package com.atguigu.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** 
  ^_^ 2017年3月25日 ^_^ 下午9:09:27 ^_^ 
 */
public class JedisPoolUtil {
	
	private static volatile JedisPool jedisPool = null;
	// 被volatile修饰的变量不会被本地线程缓存，对该变量的读写都是直接操作共享内存
	
	private  JedisPoolUtil() {}

	/**
	 * maxActive：控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted。
maxIdle：控制一个pool最多有多少个状态为idle(空闲)的jedis实例；
whenExhaustedAction：表示当pool中的jedis实例都被allocated完时，pool要采取的操作；默认有三种。
 WHEN_EXHAUSTED_FAIL --> 表示无jedis实例时，直接抛出NoSuchElementException；
 WHEN_EXHAUSTED_BLOCK --> 则表示阻塞住，或者达到maxWait时抛出JedisConnectionException；
 WHEN_EXHAUSTED_GROW --> 则表示新建一个jedis实例，也就说设置的maxActive无用；
maxWait：表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛JedisConnectionException；
testOnBorrow：获得一个jedis实例的时候是否检查连接可用性（ping()）；如果为true，则得到的jedis实例均是可用的；
 
testOnReturn：return 一个jedis实例给pool时，是否检查连接可用性（ping()）；
 
testWhileIdle：如果为true，表示有一个idle object evitor线程对idle object进行扫描，如果validate失败，此object会被从pool中drop掉；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；
 
timeBetweenEvictionRunsMillis：表示idle object evitor两次扫描之间要sleep的毫秒数；
 
numTestsPerEvictionRun：表示idle object evitor每次扫描的最多的对象数；
 
minEvictableIdleTimeMillis：表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；
 
softMinEvictableIdleTimeMillis：在minEvictableIdleTimeMillis基础上，加入了至少minIdle个对象已经在pool里面了。如果为-1，evicted不会根据idle time驱逐任何对象。如果minEvictableIdleTimeMillis>0，则此项设置无意义，且只有在timeBetweenEvictionRunsMillis大于0时才有意义；
 
lifo：borrowObject返回对象时，是采用DEFAULT_LIFO（last in first out，即类似cache的最频繁使用队列），如果为False，则表示FIFO队列；

	 * @return
	 */
	public static JedisPool getJedisPoolInstance() {
		if (jedisPool == null) {
			synchronized (JedisPoolUtil.class) { 
				if (jedisPool == null) {
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					//最大连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
//					poolConfig.setMaxActive(1000);
					poolConfig.setMaxTotal(1000);
					 //最大空闲连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
					poolConfig.setMaxIdle(32);
					poolConfig.setMaxWaitMillis(100*1000);
					poolConfig.setTestOnBorrow(true);
					jedisPool = new JedisPool(poolConfig, "127.0.0.1");
				}
				
			}
		}
		return jedisPool;
	}
	
	public static void main(String[] args) {
		
	}

}
