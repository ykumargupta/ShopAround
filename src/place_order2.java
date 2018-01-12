import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class place_order2 extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        
        String last_cart_id=session.getAttribute("last_cart_id").toString();
        String name= req.getParameter("name").trim();      
        String pincode= req.getParameter("pincode").trim();      
        String address= req.getParameter("address").trim();      
        String city= req.getParameter("city").trim();      
        String state= req.getParameter("state").trim();      
        String phone= req.getParameter("phone").trim();      
        
        Connection con=null;
        Statement st;
        ResultSet rs;
        String cus_id1=session.getAttribute("cus_id").toString();
        int cus_id=Integer.parseInt(cus_id1);
        
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
            st=con.createStatement();
            rs = st.executeQuery("select email_id from customer where cus_id='"+cus_id+"'");
            rs.next();
            String email=rs.getString(1);
        
            PreparedStatement ps1=con.prepareStatement("update cart1 set SHIPPING_EMAIL='"+email+"',SHIPPING_NAME='"+name+"', "
                    + "SHIPPING_ADDRESS='"+address+"',SHIPPING_CITY='"+city+"',"
                    + "SHIPPING_STATE='"+state+"',SHIPPING_PINCODE='"+pincode+"',SHIPPING_PHONE='"+phone+"' where cart_id='"+last_cart_id+"' ");
            ps1.executeUpdate();
            
            
            }catch(Exception e){} 
             finally
             {
               try{
               con.close();}catch(Exception e){}
             }
        res.sendRedirect("customer/place_order3.jsp");
        }
    }

   