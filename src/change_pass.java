import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class change_pass extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        
        String cus_id1=session.getAttribute("cus_id").toString();
        int cus_id=Integer.parseInt(cus_id1);
        String old_pass= req.getParameter("old_pass");      
        String new_pass= req.getParameter("new_pass");      
        String re_new_pass= req.getParameter("re_new_pass");      
        
        Connection con=null;
        Statement st;
        ResultSet rs;
        
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
            st=con.createStatement();
            rs=st.executeQuery("select * from customer where cus_id='"+cus_id+"'");
            rs.next();
            if(rs.getString(3).equalsIgnoreCase(old_pass))
            {
            PreparedStatement ps1=con.prepareStatement("update customer set password='"+new_pass+"' where cus_id='"+cus_id+"'");
            ps1.executeUpdate();
            session.setAttribute("pass_chng_success", "pass_chng_success");
            res.sendRedirect("customer/change_pass.jsp");
            }
            else
            {
              session.setAttribute("old_pass_mismatch", "old_pass_mismatch");
              res.sendRedirect("customer/change_pass.jsp");
            }
            
            }catch(Exception e){} 
             finally
             {
               try{
               con.close();}catch(Exception e){}
             }
        }
    }

   