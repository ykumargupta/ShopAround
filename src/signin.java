import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class signin extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        String email= req.getParameter("email").trim();      
        String pass= req.getParameter("password"); 
        
        Connection con=null;
        Statement st;
        ResultSet rs;
        Statement st1;
        ResultSet rs1;
        String cus_id="";
        String a_id="";
        
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
     
            st=con.createStatement();
            st1=con.createStatement();
            
            rs=st.executeQuery("select CUS_ID,EMAIL_ID,PASSWORD from customer");
            rs1=st1.executeQuery("select A_ID,EMAIL,PASSWORD from admin");
       
            int i=0;
            while(rs1.next())
            {
                if(rs1.getString(2).equals(email) && rs1.getString(3).equals(pass))
                {
                   i=1;
                   a_id=rs1.getString("a_id");
                   break;
                }
            }
            if(i==1)
            {
            session.setAttribute("a_id", a_id);   
            res.sendRedirect("admin/admin.jsp");  
            }
            else
            {
                int j=0;
                while(rs.next())
            {
                if(rs.getString(2).equals(email) && rs.getString(3).equals(pass))
                {
                   j=1;
                   cus_id=rs.getString("cus_id");
                   break;
                }
            }
            if(j==1)
            {
              session.setAttribute("cus_id", cus_id);
              res.sendRedirect("customer/home.jsp");  
            }
            else
            {
              session.setAttribute("loginfail", "loginfail");
              res.sendRedirect("home_page/home.jsp");  
            }
            }
            
            }catch(Exception e){}
        finally
        {
        try{
        con.close();}catch(Exception e){}
        }
        }
    }

   