

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
        <link rel="stylesheet" href="css/popup.css" type="text/css" media="all" />
        <script>
            function update_email()
            {
                var new_email_id=document.frm.new_email_id.value;
                var atpos=new_email_id.indexOf("@");
                var dotpos=new_email_id.lastIndexOf(".");
                
                if(new_email_id=="")
                    alert("Enter New Email ID");
                else if (atpos<1 || dotpos<atpos+2 || dotpos+2>=new_email_id.length) 
                    alert("Not a valid Email");
                else
                    document.frm.submit();   
            }
        </script>
    </head>
    <body>
        <div class="shell">
        <%@include file="header.jsp"%>
        <br><br><br>
        <div style=" float: left; position: fixed; margin-top: 20px">
        <div class="boxac categoriesac">
        <h2><b>My Account</b></h2>
        <div class="box-contentac">
            <ul>
                <br>
                <li><a href="my_orders.jsp"><b>My Order</b></a></li>
                <br><br>
                <li><a href="account.jsp"><b>Personal Information</b></a></li>
                <br><br>
                <li><a href="change_pass.jsp"><b>Change Password</b></a></li>
                <br><br>
                <li><a href="address.jsp"><b>Address</b></a></li>
                <br><br>
                <li><a href="update_email.jsp"><b>Update Email</b></a></li>
            </ul>
        </div>   
        </div>
        </div>
        <div style=" margin-left: 300px">
            <table width="400"  border="0">
                    <tr>
                        <td height="45" style=""><u><p style=" font-size: 18px"><b>Update Email</b></p></u></td>
                    </tr>
            </table>
            <table width="400"  border="0">
                    <tr>
                        <td height="45" width="150"><h2>Email Address</h2></td>
                        <td><p style="font-size: 14px"><%=email%></p></td>
                    </tr>
                    <form name="frm" action="../update_email" method="post">
                    <tr>
                        <td height="45"><h2>New Email ID</h2></td>
                        <td><input type="text" style=" height: 30px; border:solid 1px #999999" size="40" name="new_email_id" tabindex="1"></td>
                    </tr>
                    
                    <tr>
                        <td height="45"></td>
                        <td><input type="button" name="btn1" onclick="update_email()" value="Save" style="width: 80px; height: 30px; border:solid 1px #999999; background-color: #990000; color: white; font-size: 14px"></td>
                    </tr>
                    </form>
                </table>
        </div>
    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
    <br><br><br><br><br>
    
    <%@include file="footer.jsp"%>
    </div>
    
    <%
if(session.getAttribute("email_chng_success")=="email_chng_success")
        {
                   %><script language="javascript">alert("Email changed Successfully");</script><%
                   session.removeAttribute("email_chng_success");
        }
else if(session.getAttribute("email_exist")=="email_exist")
        {
                   %><script language="javascript">alert("Email alredy exist");</script><%
                   session.removeAttribute("email_exist");
        }
%>
    </body>
</html>
