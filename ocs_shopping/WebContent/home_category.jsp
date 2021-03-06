<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- セッションがなかったらログイン画面へ戻る　全てのページにこれを追加することでURLからの飛び込み防止可能 -->
<%
	String name = null;
	int category_id = 0;
	HttpSession session = request.getSession(false);
	if (session == null) {
		response.sendRedirect("login.jsp");
	} else {
		name = (String) session.getAttribute("username");
		category_id = (int) (request.getAttribute("category_id"));
	}
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="homepage.css" type="text/css" />
<meta charset="UTF-8">
<title>OCSshop</title>
</head>
<body>
	<div id="outer1">
		<div id="header1">
			<div class="header-inner">
				<img src="image/OCS_logo.png">
				<a1>OCSshop</a1>
				<a2>ようこそ、<%=name%>さん</a2>
			</div>
		</div>
	</div>
	<div id="outer2">
		<div id="left">
			<div class="left-title">カテゴリー</div>
			<div class="link">
				<ul>
					<li><a href="/ocs_shopping/Category_select?Category_ID=2">書籍</a></li>
					<li><a href="/ocs_shopping/Category_select?Category_ID=3">家電</a></li>
					<li><a href="/ocs_shopping/Category_select?Category_ID=1">ゲーム</a></li>
				</ul>
			</div>
		</div>
		<div id="outer">
			<div id="inner">
				<button class="button" type="button"
					onclick="location.href='./logout.jsp'">ログアウト</button>
				<button class="button" type="button"
					onclick="location.href='./setsumeisyo.jsp'">説明書</button>
				<form style="display: inline" action=CartSelect method="post">
					<button class="button" type="submit">カート</button>
				</form>
			</div>
		</div>
		<div id="content">
			<div class="inner">
				<h2>商品一覧</h2>
				<div style="position: absolute; top: 190px;">
					<script>
						var id =
					<%=category_id%>
						;
						switch (id) {
						case 1:
							document
									.write('<a href=/ocs_shopping/Goods_select?Goods_ID=1><img src="image/game1.jpg" width="200" height="200">Nintendo Switch</a><br>')
							document
									.write('<a href=/ocs_shopping/Goods_select?Goods_ID=2><img src="image/game2.jpg" width="200" height="200">PlayStation</a>')

							break;

						case 2:
							document
									.write('<a href=/ocs_shopping/Goods_select?Goods_ID=3><img src="image/book1.jpg" width="200" height="350">基本情報参考書</a><br>')
							document
									.write('<a href=/ocs_shopping/Goods_select?Goods_ID=4><img src="image/book2.jpg" width="200" height="350">応用情報参考書</a>')
							break;

						case 3:
							document
									.write('<a href=/ocs_shopping/Goods_select?Goods_ID=5><img src="image/electronics1.jpg" width="200" height="200">dyson</a><br>')
							document
									.write('<a href=/ocs_shopping/Goods_select?Goods_ID=6><img src="image/electronics2.jpg" width="200" height="200">TV</a>')
							break;

						}
					</script>

				</div>
			</div>
		</div>
</body>
</html>
