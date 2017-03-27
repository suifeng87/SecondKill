package com.atguigu.redis;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

@WebServlet("/sendCode")
public class SendCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SendCodeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 生成随机4位验证码
		Random random = new Random();
		int num = random.nextInt(9999);
		String code = String.format("%04d", num);
		// 接收请求 存放Redis验证码 
		Jedis jedis = new Jedis("192.168.119.128",6379);
		jedis.set("code", code);
		jedis.expire("code", 120);
		// 输出控制台 及手机
		System.out.println("请在2分钟内输入验证码，过期无效！" + code);
	}

}
