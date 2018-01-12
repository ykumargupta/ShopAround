import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class signup extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        String email= req.getParameter("email").trim();      
        String pass= req.getParameter("pass");      
        String repass= req.getParameter("repass");   
        
        Connection con=null;
        Statement st;
        ResultSet rs;
        Statement st1;
        ResultSet rs1;
        Statement st2;
        ResultSet rs2;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
            st= con.createStatement();
            st1=con.createStatement();
            st2=con.createStatement();
            
            rs= st.executeQuery("select * from customer_id"); 
            rs.next();
            int j=rs.getInt(1)+1;
            
            rs1=st1.executeQuery("select EMAIL from admin");
            rs2=st2.executeQuery("select EMAIL_ID from customer");
           
            int i=0;
            while(rs1.next())
            {
                if(rs1.getString(1).equals(email))
                {
                   i=1;
                   break;
                }
            }
            if(i==1)
            {
                session.setAttribute("logupfail", "logupfail");
                res.sendRedirect("home_page/home.jsp");  
            }
            else
            {
                int k=0;
                while(rs2.next())
                {
                 if(rs2.getString(1).equals(email))
                 {
                   k=1;
                   break;
                 }
                }
               if(k==1)
                {
                session.setAttribute("logupfail", "logupfail");
                res.sendRedirect("home_page/home.jsp");  
                }
               else
               {
                PreparedStatement ps=con.prepareStatement("insert into customer(CUS_ID,EMAIL_ID,PASSWORD) values(?,?,?)");
                ps.setInt(1,j);
                ps.setString(2, email);
                ps.setString(3, pass);
                ps.executeQuery();
            
                PreparedStatement ps1=con.prepareStatement("update customer_id set customer_id='"+j+"'");
                ps1.executeUpdate();
                session.setAttribute("logupsuccess", "logupsuccess");
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
   