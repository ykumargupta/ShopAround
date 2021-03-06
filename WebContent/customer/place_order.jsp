

<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
        <link rel="stylesheet" href="css/popup.css" type="text/css" media="all" />
        <script language="javascript" type="text/javascript">
        function printDiv(divID) {
            //Get the HTML of div
            var divElements = document.getElementById(divID).innerHTML;
            //Get the HTML of whole page
            var oldPage = document.body.innerHTML;

            //Reset the page's HTML with div's HTML only
            document.body.innerHTML = 
              "<html><body><div class='shell'>"+divElements+ "</div></body></html>";

            //Print Page
            window.print();

            //Restore orignal HTML
            document.body.innerHTML = oldPage;

          
        }
    </script>
    </head>
    <body>
        <div class="shell">
        <%@include file="header.jsp"%>
        <br><br>
        <table align="right" border="0">
            <tr>
                <td><a href="" onclick="javascript:printDiv('printablediv')">Print</a></td>
            </tr>
        </table>
        <br><br>
        <%
        String o_id=request.getParameter("o_id");
        Connection con=null;
    Statement st;
    ResultSet rs;
    Statement st1;
    ResultSet rs1;
    Statement st2;
    ResultSet rs2;
    Statement st3;
    ResultSet rs3;
    int cart_id=0;
    int new_price=0;
    try{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con=DriverManager.getConnection
           ("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
      
        st=con.createStatement();
        st1=con.createStatement();
        st2=con.createStatement();
        st3=con.createStatement();
        
        rs=st.executeQuery("select * from order_item where o_id='"+o_id+"'");
        rs.next();
        cart_id=rs.getInt(2);
        
        rs1=st1.executeQuery("select * from cart1 where cart_id='"+cart_id+"'");
        rs1.next();
        
        rs2=st2.executeQuery("select * from cart_item1 where cart_id='"+cart_id+"'");
        
                      
        
        %>
        <div id="printablediv">
        <div align="center">
                <table border="0" style=" width: 840px; height: 50px">
                    <tr>
                        <td rowspan="2" style=" width: 190px"><p style=" font-size: 21px"><b>ShopAround.com</b></p></td>
                        <td height="40"><h2>Contact us: +919957546483 || nolinneogtsk@gmail.com</h2></td>
                    </tr>
                    <tr>
                        <td><p style=" font-size: 12px">WS Retail Services Pvt. Ltd. Warehouse Address: Makum Chinapatty, Tinsukia, Assam - 786170</p></td>
                    </tr>
                </table>
            <table style=" width: 840px">
                    <tr>
                        <td>
                            <p>-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</p>
                        </td>
                    </tr>
                </table>    
        </div>
        
        <table style=" float: left; padding-left: 65px" border="0" width="400">
            <tr>
                <td height="30" width="110"><h2>Order ID: </h2> </td>
                <td>ORD0<%=o_id%></td>
            </tr>
            <tr>
                <td height="30"><h2>Order Date:</h2> </td>
                <td><%=rs.getString(3).substring(0, 10) %></td>
            </tr>
            <tr>
                <td height="30"><h2>Invoice No:</h2> </td>
                <td>INV033289<%=o_id%></td>
            </tr>
            <tr>
                <td height="30"><h2>Invoice Date:</h2> </td>
                <td><%=rs.getString(3).substring(0, 10) %></td>
            </tr>
        </table>
        
        <table border="0" width="480">
            <tr>
                <td height="20"><u><h2>Shipping Address</h2></u></td>
            </tr>
            <tr>
                <td height="20"><%=rs1.getString(4)%></td>
            </tr>
            <tr>
                <td height="20"><%=rs1.getString(5)%></td>
            </tr>
            <tr>
                <td height="20"><%=rs1.getString(6)%></td>
            </tr>
            <tr>
                <td height="20"><%=rs1.getString(7)%></td>
            </tr>
            <tr>
                <td height="20"><%=rs1.getString(8)%></td>
            </tr>
            <tr>
                <td height="20">Phone: <%=rs1.getString(9)%></td>
            </tr>
        </table>
        
        
        <br><br>
        <center>
            <table width="840" border="0">
            <tr>
                <th height="30" style="border:solid 2px #dedede;" width="300"><h2>Item Description</h2></th>
                <th style="border:solid 2px #dedede;"><h2>Price</h2></th>
                <th style="border:solid 2px #dedede;"><h2>Qty</h2></th>
                <th style="border:solid 2px #dedede;"><h2>SubTotal</h2></th>
            </tr>
                <%
                while(rs2.next())
                   {
             rs3=st3.executeQuery("select * from item where i_id='"+rs2.getString(3) +"'");
             rs3.next(); 
             
                %>
            <tr>
                <th height="40" width="300"><h2><%=rs3.getString(3)%></h2></th>
                <th><h2><%=(rs2.getInt(5)/rs2.getInt(4))%></h2></th>
                <th><h2><%=rs2.getString(4)%></h2></th>
                <th><h2><%=rs2.getInt(5)%></h2></th>
            </tr>
                <%
                 } 
                %>
            </table>
            <table>
                <tr>
                    <td height="20"></td>
                </tr>
            </table>
            <table width="840" border="0">
                <tr>
                <th style="border:solid 2px #dedede;" height="35"><h2>Grand Total: Rs. <%=rs1.getString(10)%></h2></th>
                </tr>
                <tr>
                <th><p style=" font-size: 10px">This is a computer generated invoice. No signature required.</p></th>
                </tr>
            </table>
            </center>
        </div>
      <%
      
      }catch(Exception se){}
        finally
        {
            try
            {
             con.close();
            }catch(Exception e){}
        }
      %>
        
        <br><br><br>
        <%@include file="footer.jsp"%>
        </div>
    </body>
</html>
