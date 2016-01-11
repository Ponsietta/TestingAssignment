<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="bootstrap.css">
<title>Chat - CPS 3222 Assignment</title>
<script src="jquery-1.11.3.min.js"></script>

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
					<li><a href="Chat.jsp" id="chat"> Chat </a></li>
					<li><a href=""> About </a></li>
				</ul>
				
			<div class="navbar-right">
				<ul class="nav navbar-nav">
					<li><a href="LogIn.jsp" id="logoutForm"> Log Out </a></li>
				</ul>
			</div>
			</div>
		</div>
	</div>
	<div class="container body-content">
		<br /> <br /> <br /> <br />
		<div class="col-md-8">
			<div class="panel panel-info">
				<div class="panel-heading">RECENT CHAT HISTORY</div>
				<div class="panel-body">
					<ul id="media-list" class="media-list">

						<li class="media">

							<div class="media-body">

								<div class="media">
									<div class="media-body">
										Hello :) <br /> <small class="text-muted">Marzia
											Cutajar | 23rd June at 5:00pm</small>
										<hr />
									</div>
								</div>

							</div>
						</li>
					</ul>
				</div>
				<!--     
            
            <form method="post" >
				<div class="panel-footer">
                <div class="input-group">
                	<input style="width:300px" name="chatmessage" id="chatmessage" type="text" class="form-control" placeholder="Enter Message" />
                    <input type="checkbox" name="parentalLock" id="parentalLock" style="margin-left:200px; margin-top:10px"/> Parental Lock
                    <span class="input-group-btn">
                     <input class="btn btn-info"  id="sendbutton"
							type="submit" value="SEND">
                        </span>
                </div>
                </div>
                </form>
			 -->
				<div class="panel-footer">
					<div class="input-group">
						<input style="width: 300px" name="chatmessage" id="chatmessage"
							type="text" class="form-control" placeholder="Enter Message" />
						<input type="checkbox" name="parentalLock" id="parentalLock"
							style="margin-left: 200px; margin-top: 10px" /> Parental Lock <span
							class="input-group-btn">
							<button class="btn btn-info" id="sendbutton" type="button">
								SEND</button>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
    $('#sendbutton').click(function () {

        var postData = $("#chatmessage").val();
        var parentalLock = document.getElementById("parentalLock").checked.toString();
        var data = { chatmessage: postData, parentalLock: parentalLock };

        $.post('SendMessage', data, function (res) {
            //based on server response do whatever you require
            if (res == 0) {
                var htmlcode = "<li class=\"media\"> <div class=\"media-body\"> <div class=\"media\">"
                    + "<a class=\"pull-left\" href=\"#\"> <img class=\"media-object img-circle "
                    + "src=\"~/Content/img/user.png\" /> </a><div class=\"media-body\"> "
                    + postData
                    + "<br /><small class=\"text-muted\">"
                    + "${username}" + " | "
                    + new Date().toLocaleString() + "</small>"
                    + "<hr /> </div> </div></div> </li> ";
               
                document.getElementById("media-list").innerHTML += htmlcode;
                
                $.post('Reply', null, function (res) {
                	var htmlcode = "<li class=\"media\"> <div class=\"media-body\"> <div class=\"media\">"
                        + "<a class=\"pull-right\" href=\"#\"> <img class=\"media-object img-circle "
                        + "src=\"~/Content/img/user.png\" /> </a><div class=\"media-body\"> "
                        + res
                        + "<br /><small class=\"text-muted\">"
                        + "Friend" + " | "
                        + new Date().toLocaleString()  + "</small>"
                        + "<hr /> </div> </div></div> </li> ";
                        
                	document.getElementById("media-list").innerHTML += htmlcode;
                });
                
                
            }
            else if (res==4) {
               alert("No naughty words please!");
            }
            else if(res==6)
            {
            	document.getElementById("logoutForm").click();
            }
            else if (res == 7){
            	alert("You may not send more than 10 messages per minute.");
            }
            $('#chatmessage').val('');
        });

    });
</script>
</body>
</html>