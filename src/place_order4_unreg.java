import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.*;
import javax.mail.internet.*;

public class place_order4_unreg extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        
        
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        Statement st1=null;
        ResultSet rs1=null;
        Statement st2=null;
        ResultSet rs2=null;
        Statement st3=null;
        ResultSet rs3=null;
        Statement st4=null;
        ResultSet rs4=null;
        final String to,subject,body,id,pass;
        String total_price1="";
        try{
            
        ArrayList cus_det=(ArrayList) session.getAttribute("cus_det");
        String email= cus_det.get(0).toString().trim();
        String name= cus_det.get(1).toString().trim();
        String pincode= cus_det.get(2).toString().trim();
        String address= cus_det.get(3).toString().trim();
        String city= cus_det.get(4).toString().trim();
        String state= cus_det.get(5).toString().trim();
        String phone= cus_det.get(6).toString().trim();
        
        total_price1=req.getParameter("total_price");
        int total_price=Integer.parseInt(total_price1);
        
        String pay_mode=req.getParameter("pay_mode");
        
        ArrayList al1=(ArrayList) session.getAttribute("cart");
        
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
            st=con.createStatement();
            st1=con.createStatement();
            st2=con.createStatement();
            st3=con.createStatement();
            st4=con.createStatement();
            
            rs3=st3.executeQuery("select o_id from order_item_id");
            rs3.next();
            int o_id=rs3.getInt(1)+1;
            
            //start code to send message
            /*
            to=email;
            subject="ORD"+o_id;
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
            rs=st.executeQuery("select cart_id from cart_id");
            rs.next();
            int cart_id=rs.getInt(1)+1;
            
            //out.print(cart_id+" "+email+"  "+name+"  "+pincode+"  "+address+"  "+city+"  "+state+"  "+phone+" "+total_price);        
            PreparedStatement ps=con.prepareStatement("insert into CART1(CART_ID,SHIPPING_EMAIL,SHIPPING_NAME,SHIPPING_ADDRESS,"
                    + "SHIPPING_CITY,SHIPPING_STATE,SHIPPING_PINCODE,SHIPPING_PHONE,TOTAL_PRICE) values(?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, cart_id);
            ps.setString(2, email);
            ps.setString(3, name);
            ps.setString(4, address);
            ps.setString(5, city);
            ps.setString(6, state);
            ps.setString(7, pincode);
            ps.setString(8, phone);
            ps.setInt(9, total_price);
            ps.executeUpdate();
            //out.print("ok");
            
            rs1=st1.executeQuery("select ci_id from cart_item_id");
            rs1.next();
            int ci_id=rs1.getInt(1)+1;
            //out.print(ci_id);
            
            for(int j=0,k=ci_id;j<al1.size();j++,k++)
            {
               String i_id1=al1.get(j).toString();
               int i_id=Integer.parseInt(i_id1);
               //out.print(" "+i_id);
               rs2=st2.executeQuery("select * from item where i_id='"+i_id+"'");   
               rs2.next(); 
               j=j+1;
               String qty1=al1.get(j).toString();
               int qty=Integer.parseInt(qty1);
               //out.print(" "+qty);
               int price=0;
               if(rs2.getInt(14)==0)
                 price=Integer.parseInt(rs2.getString(10));
               else 
                 price=rs2.getInt(10)-(rs2.getInt(10)*rs2.getInt(14)/100);  
               price=qty*price;
               //out.print(" "+price);
               PreparedStatement ps1=con.prepareStatement("insert into CART_ITEM1(CI_ID,CART_ID,I_ID,"
                       + "QUANTITY,PRICE) values(?,?,?,?,?)");
               ps1.setInt(1, k);
               ps1.setInt(2, cart_id);
               ps1.setInt(3, i_id);
               ps1.setInt(4, qty);
               ps1.setInt(5, price);
               ps1.executeUpdate();
               
               PreparedStatement ps2=con.prepareStatement("update cart_item_id set ci_id='"+k+"'");
               ps2.executeUpdate();
               
            rs4= st4.executeQuery("select AVAILIBILITY,NO_OF_ITEMS_SOLD from item where i_id='"+i_id+"'"); 
            rs4.next();
            int cnt=rs4.getInt(1)-qty;
            int cnt1=rs4.getInt(2)+qty;
            PreparedStatement ps6=con.prepareStatement("update item set AVAILIBILITY='"+cnt+"' where i_id='"+i_id+"'");
            ps6.executeUpdate();
            PreparedStatement ps7=con.prepareStatement("update item set NO_OF_ITEMS_SOLD='"+cnt1+"' where i_id='"+i_id+"'");
            ps7.executeUpdate();
            } 
            
            PreparedStatement ps3=con.prepareStatement("update cart_id set cart_id='"+cart_id+"'");
            ps3.executeUpdate();
            
            
            
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
            
        
        Statement st5= con.createStatement();
        ResultSet rs5= st5.executeQuery("select tracking_id from tracking_id");
        rs5.next();
        int tracking_id=rs5.getInt(1)+1;
        
            PreparedStatement ps8=con.prepareStatement("insert into order_item(o_id,cart_id,o_date,pay_mode,tracking_id,status) values(?,?,?,?,?,?)");
            ps8.setInt(1, o_id);
            ps8.setInt(2, cart_id);
            ps8.setString(3, date);
            ps8.setString(4, pay_mode);
            ps8.setInt(5, tracking_id);
            ps8.setString(6, "pending");
            ps8.executeUpdate();
            
        PreparedStatement ps9=con.prepareStatement("update tracking_id set tracking_id='"+tracking_id+"'");
        ps9.executeUpdate();        
            
            PreparedStatement ps5=con.prepareStatement("update order_item_id set o_id='"+o_id+"'");
            ps5.executeUpdate();
            
            
            session.removeAttribute("cus_det");
            session.removeAttribute("cart");
            con.close();
            res.sendRedirect("home_page/place_order6.jsp?o_id="+o_id+"");
        }catch(Exception e)
        {
            session.setAttribute("mail_fail", "mail_fail");
            res.sendRedirect("home_page/place_order4.jsp?price="+total_price1+"");
        } 
             finally
             {
               try{
               con.close();}catch(Exception e){}
             }   
        }
    }

   