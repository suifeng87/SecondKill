package com.rjth.redis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/** 
  ^_^ 2017年3月26日 ^_^ 上午8:57:12 ^_^ 
 */
public class Miaosha_redis {
	private static int i = 1;
	private static int j = 1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static boolean domiaosha(String userid, String prodid) {
		Jedis jedis = new Jedis("192.168.119.128",6379);
		//使用乐观锁
		jedis.watch(prodid);
		// 在减库存之前做一下判断
		Integer num = Integer.valueOf(jedis.get(prodid));
		// 1、不是高并发，默认不让程序睡眠，程序是正常的，只能抢购十次
		//
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 2、判断数量不能是等于0 如果是等于0，遇到高并发的时候，等于0 就没有判断，一致执行
		
		if (num.intValue() <= 0) {
			jedis.unwatch();
			return false;
		}
		// 减库存和增加人数都是同一个事务
		Transaction transaction = jedis.multi();
		transaction.decr(prodid);// 减库存
		// 加用户
		transaction.lpush(prodid + ":usr", userid);
		
		List<Object> list = transaction.exec();
		if (list == null) {
			System.out.println("用户 " + userid + "秒杀失败-------" + j++ + "次");
		} else {
			System.out.println("用户 " + userid + "秒杀成功========" + i++ + "次");
		}
		jedis.close();
		return true;
	}

	
}
