<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script>
            function chk()
            {
                var email=document.login.email.value;
                var atpos=email.indexOf("@");
                var dotpos=email.lastIndexOf(".");
                if(email=="")
                    alert("Enter Email");
                else if (atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length) 
                    alert("Not a valid Email");
                else if(document.login.pass.value=="")
                    alert("Enter Password");
                else if(document.login.repass.value=="")
                    alert("Enter Repeat Password");
                else if(document.login.pass.value!=document.login.repass.value)
                    alert("password & repeat password mismatch");
                else
                    document.login.submit();
            }
        </script>
    </head>
    <body>
        
        <div id="overlay_signup">
            <div id="popup" style="height:290px;" >
                <a href=""><img class="close_button" src="images/close.png"/></a>
                <form class="login" name="login" action="../signup" method="post">
                    <div align="center">
                        <table border="0" width="350">
                        <tr>
                            <td height="50" align="left" width="110" style=" color: #ffffff; font-size: 15px">Email Address</td>
                            <td><input type="text" name="email" style=" height: 28px; border:solid 1px #000000" size="40" tabindex="1" required></td>
                        </tr>
                        <tr>
                            <td height="50" align="left" style=" color: #ffffff; font-size: 15px">Password</td>
                            <td><input type="password" name="pass" style=" height: 28px; border:solid 1px #000000" size="40" tabindex="2" required></td>
                        </tr>
                        <tr>
                            <td height="50" align="left" style=" color: #ffffff; font-size: 15px">Repeat</td>
                            <td><input type="password" name="repass" style=" height: 28px; border:solid 1px #000000" size="40" tabindex="3" required></td>
                        </tr> 
                        <tr>
                            <td></td>
                            <td align="left" height="50">
                                <input type="button" value="Sign Up" onclick="chk()" style="width: 80px; height: 28px; border:solid 1px #333333; background-color:#333333; color: white; font-size: 14px" tabindex="4">
                            </td>
                        </tr>
                     </table> 
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
