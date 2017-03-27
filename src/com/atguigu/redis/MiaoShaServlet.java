package com.atguigu.redis;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/miaosha")
public class MiaoShaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MiaoShaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("访问");
		String userid = request.getParameter("userid");
		String prodid = request.getParameter("prodid");
		boolean success = Miaosha_redis.domiaosha(userid,prodid);
		response.getWriter().print(success);
	}

}
