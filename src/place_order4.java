import java.io.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.*;

public class place_order4 extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        
        String last_cart_id1=session.getAttribute("last_cart_id").toString();
        int last_cart_id=Integer.parseInt(last_cart_id1);   
        String cus_id1=session.getAttribute("cus_id").toString();
        int cus_id=Integer.parseInt(cus_id1);   
        String pay_mode=req.getParameter("pay_mode");
        final String to,subject,body,id,pass;
        
        Connection con=null;
        Statement stt;
        ResultSet rss;
        int j=0;
        String total_price="";
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
            
            total_price=req.getParameter("total_price");
            stt=con.createStatement();
            
            Statement st= con.createStatement();
        ResultSet rs= st.executeQuery("select * from order_item_id"); 
        rs.next();
        j=rs.getInt(1)+1;
        
        rss = stt.executeQuery("select email_id from customer where cus_id='"+cus_id+"'");
        rss.next();
        String email=rss.getString(1);
        
        //start code to send message
            /*to=email;
            subject="ORD"+j;
            body="Dear";
            id="nolinneogtsk@gmail.com";
            pass="nolin222288";
           
            Properties props=new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
               
            Session ses=Session.getDefaultInstance(props,new javax.mail.Authenticator() { 
                protected PasswordAuthentication getPasswordAuthentication(){ 
                    return new PasswordAuthentication(id,pass);}});
            try
            {
                Message message=new MimeMessage(ses);
                message.setFrom(new InternetAddress(id));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);
                Transport.send(message);
            }catch(MessagingException e){
                throw new RuntimeException(e);
                }
            //ens code to send message
        */
            
            Calendar cal=new GregorianCalendar();
        int dt=cal.get(Calendar.DATE);
        int mn=cal.get(Calendar.MONTH);
        int yr=cal.get(Calendar.YEAR);
        String month="";
        if(mn==0)
          month="JAN";
        else if(mn==1)
          month="FEB";
        else if(mn==2)
          month="MAR";
        else if(mn==3)
          month="APR";
        else if(mn==4)
          month="MAY";
        else if(mn==5)
          month="JUN";
        else if(mn==6)
          month="JUL";
        else if(mn==7)
          month="AUG";
        else if(mn==8)
          month="SEP";
        else if(mn==9)
          month="OCT";
        else if(mn==10)
          month="NOV";
        else if(mn==11)
          month="DEC";
        String date=dt+"-"+month+"-"+yr;
        
        //out.println(j);
        //out.println(cus_id);
        //out.println(last_cart_id);
        //out.println(date);
        
        Statement st3= con.createStatement();
        ResultSet rs3= st3.executeQuery("select tracking_id from tracking_id");
        rs3.next();
        int tracking_id=rs3.getInt(1)+1;
        
        if(pay_mode.equalsIgnoreCase("cash_on_delivery"))
        {
            PreparedStatement ps1=con.prepareStatement("insert into order_item values(?,?,?,?,?,?)"); 
            ps1.setInt(1,j);
            ps1.setInt(2,last_cart_id);
            ps1.setString(3,date);
            ps1.setString(4, pay_mode);
            ps1.setInt(5,0);
            ps1.setString(6, "pending");
            ps1.executeUpdate();
        }
        else
        {    
        PreparedStatement ps1=con.prepareStatement("insert into order_item values(?,?,?,?,?,?)"); 
            ps1.setInt(1,j);
            //ps1.setInt(2,cus_id);
            ps1.setInt(2,last_cart_id);
            ps1.setString(3,date);
            ps1.setString(4, pay_mode);
            ps1.setInt(5,tracking_id);
            ps1.setString(6, "pending");
            int res1=ps1.executeUpdate();
            //out.print(res1);
        PreparedStatement ps5=con.prepareStatement("update tracking_id set tracking_id='"+tracking_id+"'");
        ps5.executeUpdate();    
        }    
            
            PreparedStatement ps2=con.prepareStatement("update order_item_id set o_id='"+j+"'");
            ps2.executeUpdate();
          
            Statement st1= con.createStatement();
            Statement st2= con.createStatement();
            ResultSet rs1= st1.executeQuery("select i_id,quantity from cart_item1 where cart_id='"+last_cart_id+"'"); 
            ResultSet rs2;
            while(rs1.next())
            {
            rs2= st2.executeQuery("select AVAILIBILITY,NO_OF_ITEMS_SOLD from item where i_id='"+rs1.getInt(1)+"'"); 
            rs2.next();
            int cnt=rs2.getInt(1)-rs1.getInt(2);
            int cnt1=rs2.getInt(2)+rs1.getInt(2);
            PreparedStatement ps3=con.prepareStatement("update item set AVAILIBILITY='"+cnt+"' where i_id='"+rs1.getInt(1)+"'");
            ps3.executeUpdate();
            PreparedStatement ps4=con.prepareStatement("update item set NO_OF_ITEMS_SOLD='"+cnt1+"' where i_id='"+rs1.getInt(1)+"'");
            ps4.executeUpdate();
            }
            res.sendRedirect("customer/place_order6.jsp?o_id="+j+"");
        }catch(Exception e)
        {
            session.setAttribute("mail_fail", "mail_fail");
            res.sendRedirect("customer/place_order4.jsp?price="+total_price+"&last_cart_id="+last_cart_id+"");
        } 
             finally
             {
               try{
               con.close();}catch(Exception e){}
             }
        
        }
    }

   