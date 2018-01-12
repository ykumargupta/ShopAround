import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class edit_item1 extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        String i_name= req.getParameter("i_name");      
        
        String brand1= req.getParameter("drop2");      
        String brand2= req.getParameter("t2");      
        String brand="";
        if(brand1!=null)
            brand=brand1;
        else if(brand2!=null)
            brand=brand2;
        
        String av= req.getParameter("av");      
        String pp= req.getParameter("pp");   
        int pp1=Integer.parseInt(pp);
        String sp= req.getParameter("sp");      
        int sp1=Integer.parseInt(sp);
        String discount= req.getParameter("discount");      
        int discount1=Integer.parseInt(discount);
        int disc_price=sp1-(sp1*discount1/100);
        //out.print(sp);
        
        Connection con=null;
        
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
        
            String i_id=session.getAttribute("i_id").toString();
        
            PreparedStatement ps1=con.prepareStatement("update item set i_name='"+i_name+"', "
                    + "BRAND_NAME='"+brand+"',AVAILIBILITY='"+av+"',"
                    + "PURCHASED_PRICE='"+pp1+"',SELLING_PRICE='"+sp1+"',DISCOUNT='"+discount1+"',DISCOUNT_PRICE='"+disc_price+"' where i_id='"+i_id+"' ");
            ps1.executeUpdate();
            
            
            }catch(Exception e){} 
             finally
             {
               try{
               con.close();}catch(Exception e){}
             }
        res.sendRedirect("admin/edit_item2.jsp");
        }
    }

   