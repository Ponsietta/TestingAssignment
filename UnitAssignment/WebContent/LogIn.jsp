<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="jumbotron.css">
<title>Log In - CPS 3222 Assignment</title>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<div class="navbar-brand">Kellimni Fejn Trid</div>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<!-- 
                    <li>@Html.ActionLink("Chat", "Index", "ChatSessions", new { area = "" }, new { @id = "chat" })</li>
                    <li>@Html.ActionLink("About", "About", "Home")</li>
                     -->
					<li><a href=""> Chat </a></li>
					<li><a href=""> About </a></li>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<li><a href="LogIn.jsp"> Login </a>
					<li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container body-content">
		<br> <br> <br>
		<h2>Log In</h2>
		<div class="row">
			<div class="col-md-8">
				<section id="loginForm">

				<hr />

				<form method="post" action="LogIn">
					<div class="form-group">
						<div class="col-md-2 control-label">Username</div>
						<input class="form-control" type="text" name="username" id="username"
							size="20px">

					</div>
					<div class="form-group">
						<div class="col-md-2 control-label">Password</div>
						<input class="form-control" type="password" name="password" id="password"
							size="20px"> <br> <input class="btn btn-default"
							type="submit" value="Log In">
					</div>
				</form>
				</section>
			</div>
	</div>
	<hr />
	<footer>
	<p>2016 - Rebecca Kai Cassar &amp; Marzia Cutajar</p>
	</footer>
	</div>
</body>
</html>
