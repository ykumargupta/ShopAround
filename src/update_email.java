import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class update_email extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        
        String cus_id1=session.getAttribute("cus_id").toString();
        int cus_id=Integer.parseInt(cus_id1);
        String new_email_id= req.getParameter("new_email_id").trim();      
        
        Connection con=null;
        Statement st;
        ResultSet rs;
        Statement st1;
        ResultSet rs1;
        
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
            st=con.createStatement();
            rs=st.executeQuery("select * from customer");
            st1=con.createStatement();
            rs1=st1.executeQuery("select * from admin");
            int ext=0;
            while(rs.next())
            {
              if(rs.getString(2).equals(new_email_id))
                  ext=1;
            }
            while(rs1.next())
            {
              if(rs1.getString(2).equals(new_email_id))
                  ext=1;
            }
            if(ext==0)
            {
            PreparedStatement ps1=con.prepareStatement("update customer set EMAIL_ID='"+new_email_id+"' where cus_id='"+cus_id+"'");
            ps1.executeUpdate();
            session.setAttribute("email_chng_success", "email_chng_success");
            res.sendRedirect("customer/update_email.jsp");
            }
            else if(ext==1)
            {
            session.setAttribute("email_exist", "email_exist");
            res.sendRedirect("customer/update_email.jsp");  
            }
            }catch(Exception e){} 
             finally
             {
               try{
               con.close();}catch(Exception e){}
             }
        }
    }

   