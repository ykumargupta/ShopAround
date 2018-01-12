import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class address extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        
        String cus_id1=session.getAttribute("cus_id").toString();
        int cus_id=Integer.parseInt(cus_id1);
        String pincode= req.getParameter("pincode");      
        String address= req.getParameter("address");      
        String city= req.getParameter("city"); 
        String state= req.getParameter("state"); 
        
        Connection con=null;
       
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
            
            PreparedStatement ps1=con.prepareStatement("update customer set STREET_ADDRESS='"+address+"',"
                    + "CITY='"+city+"',STATE='"+state+"', PINCODE='"+pincode+"'  where cus_id='"+cus_id+"'");
            ps1.executeUpdate();
            session.setAttribute("addrs_chng", "addrs_chng");
            res.sendRedirect("customer/address.jsp");
            
            }catch(Exception e){} 
             finally
             {
               try{
               con.close();}catch(Exception e){}
             }
        }
    }

   