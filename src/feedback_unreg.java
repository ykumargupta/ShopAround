import java.io.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.*;
import javax.servlet.http.*;

public class feedback_unreg extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        
        
        String email= req.getParameter("email");      
        String message= req.getParameter("message");      
        
        
        Connection con=null;
        ResultSet rs=null;
        Statement st=null;
       
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
            st=con.createStatement();
            rs= st.executeQuery("select * from f_id"); 
            rs.next();
            int j=rs.getInt(1)+1;
            
            PreparedStatement ps1=con.prepareStatement("insert into feedback(f_id,email_id,message,f_date,status) values(?,?,?,?,?)");
            ps1.setInt(1, j);
            ps1.setString(2, email);
            ps1.setString(3, message);
            
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
            ps1.setString(4, date);
            ps1.setString(5, "unsolved");
            ps1.executeUpdate();
            
            PreparedStatement ps2=con.prepareStatement("update f_id set f_id='"+j+"'");
            ps2.executeUpdate();
        
            session.setAttribute("suc_sent", "suc_sent");
            res.sendRedirect("home_page/feedback.jsp");
            
            }catch(Exception e){} 
             finally
             {
               try{
               con.close();}catch(Exception e){}
             }
        }
    }

   