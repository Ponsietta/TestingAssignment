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
					<li><a href=""> Chat </a></li>
					<li><a href=""> About </a></li>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<li><a href="LogIn.jsp" id="loginLink"> Login </a>
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
				
				<% if ("true".equals(request.getAttribute("parentLockDisable")))
					{%>
					<div id="lockerror" style="color: red">Parental Lock Violated.
					Your account is locked.</div>
            			<br/>
            	<%}
				else if ("false".equals(request.getAttribute("accountEnabled")))
					{%>
					<div id="lockerror" style="color: red">3 invalid log in attempts made.
					Your account is locked.</div>
            			<br/>
            	<%} 
					else if("false".equals(request.getAttribute("loginsuccess")))
					{ %>
            			<div id="loginerror" style="color: red">Invalid login attempt</div>
            			<br/>
        		<% }
				   else if("true".equals(request.getAttribute("providerfailure")))
				   {%>
				   		<div id="providererror" style="color: red">Provider timed out</div>
            			<br/>
        		<% } %>

				<form method="post" action="LogIn">
					<div class="form-group">
						<div class="col-md-2 control-label">Username</div>
						<input class="form-control" type="text" name="username" id="username"
							size="20px">

					</div>
					<div class="form-group">
						<div class="col-md-2 control-label">Password</div>
						<input class="form-control" type="password" name="password" id="password"
							size="20px"> <br> <input id="login" class="btn btn-default"
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
