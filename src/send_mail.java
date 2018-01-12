import java.io.*;

import java.sql.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.*;

public class send_mail extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        
        final String to,subject,body,id,pass;
        
        String email=req.getParameter("email");
        String subject1=req.getParameter("subject");
        String body1=req.getParameter("body");
        String mail_id=req.getParameter("mail_id");
        String e_id=req.getParameter("e_id");
        Connection con=null;
       
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","eshopping","nolin");
            
            //start code to send message
            to=email;
            subject=subject1;
            body=body1;
            id="ms.deepak456@gmail.com";
            pass="865163";
            String host="smtp.gmail.com";
            
            Properties props=new Properties();
            props.put("mail.smtp.starttls.enable","true" );
            props.put("mail.smtp.host", host);
            props.setProperty("mail.transport.protocol", "smtps");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.user", id);
            props.put("mail.smtp.password", pass);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            
            System.out.println("properties set");
               
            Session ses=Session.getDefaultInstance(props,new javax.mail.Authenticator() { 
                protected PasswordAuthentication getPasswordAuthentication(){ 
                    return new PasswordAuthentication(id,pass);}});
            try
            {
                Message message=new MimeMessage(ses);
                message.setFrom(new InternetAddress(id.trim()));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);
                System.out.println("before send");
                Transport trans= ses.getTransport("smtps");
                trans.connect(host,id,pass);
                trans.send(message);
                trans.close();
                System.out.println("after send");
            }catch(Exception e){
                out.print("connect to internet first");
                //throw new RuntimeException(e);
                e.printStackTrace();
                }
            //ens code to send message
            
            session.setAttribute("msg_sent", "msg_sent");
            if(mail_id.equals("1"))
            {
             String status="solved";
             PreparedStatement ps=con.prepareStatement("update feedback set status='"+status+"' where f_id='"+e_id+"'");
             ps.executeUpdate();
             res.sendRedirect("admin/mail.jsp?f_id="+e_id+"");
            }
            else if(mail_id.equals("2"))
             res.sendRedirect("admin/mail_1.jsp?cus_id="+e_id+"");
            }catch(Exception e)
            {
            session.setAttribute("msg_sent_fail", "msg_sent_fail");
            if(mail_id.equals("1"))
             res.sendRedirect("admin/mail.jsp?f_id="+e_id+"");
            else if(mail_id.equals("2"))
             res.sendRedirect("admin/mail_1.jsp?cus_id="+e_id+"");
            } 
             finally
             {
               try{
               con.close();}catch(Exception e){}
             }
        }
    }

   