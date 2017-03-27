package com.atguigu.redis;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

@WebServlet("/checkCode")
public class CheckCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckCodeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String codeJSP = request.getParameter("code");
		System.out.println("页面接收的验证码：" + codeJSP);
		Jedis jedis = new Jedis("192.168.119.128",6379);
		String codeRedis = jedis.get("code");
		System.out.println("Redis中存放的验证码是：" + codeRedis);
		boolean success = codeJSP.equals(codeRedis);
		response.getWriter().print(success);
	}

}
