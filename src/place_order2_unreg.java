import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class place_order2_unreg extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session=req.getSession(true);
        
        if(session.getAttribute("cus_det")!=null)
           session.removeAttribute("cus_det");
        
        String email= req.getParameter("email");      
        String name= req.getParameter("name");      
        String pincode= req.getParameter("pincode");      
        String address= req.getParameter("address");      
        String city= req.getParameter("city");      
        String state= req.getParameter("state");      
        String phone= req.getParameter("phone");      
        
        ArrayList cus_det = new ArrayList();
        cus_det.add(email);
        cus_det.add(name);
        cus_det.add(pincode);
        cus_det.add(address);
        cus_det.add(city);
        cus_det.add(state);
        cus_det.add(phone);
        session.setAttribute("cus_det",cus_det);
        
        res.sendRedirect("home_page/place_order3.jsp");
        }
    }

   