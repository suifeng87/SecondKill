<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  ^_^ 2017年3月25日 ^_^ 下午9:47:24 ^_^ -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>苹果9！1分抢购！</h1>

	<form id="msform" action="${pageContext.request.contextPath}/miaosha">
		<input type="hidden" id="userid" name="userid" value="">
		<input type="hidden" id="prodid" name="prodid" value="pd1">
		<input type="button" id="miaosha_btn" name="miaosha_btn" value="秒杀！速速抢购！！">
	</form>
	
	<hr>
	Redis的时效
	<h1>手机验证码</h1>
	<form id="codeform" action="${pageContext.request.contextPath}/checkCode">
		请输入验证码：<input type="text" id="code" name="code"><input id="sendCode" type="button" value="点击发送验证码">
		<br><br>
		<input type="button" id="checkCode" value="提交">
	</form>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#miaosha_btn").click(function() {
			var userid = "user_" + Math.floor(Math.random() * 100);
			$("#userid").val(userid);
			var url = $("#msform").attr("action");
			$.post(url, $("#msform").serialize(),function(data){
				if (data == "false") {
					alert("抢光了");
					$("#miaosha_btn").attr("disabled",true);
				}
			});
		});// 秒杀结束
		
		$("#sendCode").click(function() {
			$.post("${pageContext.request.contextPath}/sendCode");
			alert("已发送验证码，请查收！");
			return false;
		});//发送验证码结束
		
		$("#checkCode").click(function() {
			$.ajax({
				url:$("#codeform").attr("action"),
				data:{code:$("#code").val()},
				success:function(r){
					if (r == "true") {
						alert("验证通过");
					} else {
						alert("验证失败");
					}
				}
			});
		});// 校验验证码
		
	});
	
</script>
</html>