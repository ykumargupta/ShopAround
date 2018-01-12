import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class account extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        
        String cus_id1=session.getAttribute("cus_id").toString();
        int cus_id=Integer.parseInt(cus_id1);
        String fname= req.getParameter("fname");      
        String lname= req.getParameter("lname");      
        String ph_no= req.getParameter("ph_no"); 
        String name= fname+" "+lname;
        
        Connection con=null;
       
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
            
            PreparedStatement ps1=con.prepareStatement("update customer set name='"+name+"', phone_no='"+ph_no+"' where cus_id='"+cus_id+"'");
            ps1.executeUpdate();
            session.setAttribute("p_info_chng", "p_info_chng");
            res.sendRedirect("customer/account.jsp");
            
            }catch(Exception e){} 
             finally
             {
               try{
               con.close();}catch(Exception e){}
             }
        }
    }

   