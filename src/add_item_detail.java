import java.io.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class add_item_detail extends HttpServlet 
 {
  private Connection con;
    
  public void init(ServletConfig conf)throws ServletException
    {
     super.init(conf);
     try {
          Class.forName("oracle.jdbc.driver.OracleDriver");
         } catch (ClassNotFoundException ex) {}
    }
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {
     FileOutputStream fileOut=null;
     response.setContentType("text/html");
     HttpSession session=request.getSession(true);
     try
     {
         PrintWriter out = response.getWriter();
      String contentType = request.getContentType();
       //out.println(contentType);
      if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
       {
       DataInputStream in = new DataInputStream(request.getInputStream());
       //we are taking the length of Content type data
       int formDataLength = request.getContentLength();
       byte dataBytes[] = new byte[formDataLength];
       int byteRead = 0;
       int totalBytesRead = 0;
       //this loop converting the uploaded file into byte code
       while (totalBytesRead < formDataLength) 
        {
        byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
        totalBytesRead += byteRead;
        }
       String saveFile0 = new String(dataBytes);
       //out.println(saveFile0);
       
       String saveFile1[]=new String[50];
       String saveFile2;
       String saveFile3[]=new String[50];
       
       int i;
       for(i=0;i<50;i++)
       {
       saveFile0 = saveFile0.substring(saveFile0.indexOf("name=\"") + 6);
       saveFile1[i] = saveFile0.substring(0,(saveFile0.indexOf("\"")));
       if(saveFile1[i].equalsIgnoreCase("btn1"))
           break;
       //out.print(saveFile1[i]);
       else
       {
       saveFile2 = saveFile0.substring(saveFile0.indexOf("\"") + 1);
       //out.print(" ");
       saveFile3[i] = saveFile2.substring(4,(saveFile2.indexOf("-")-2));
       //out.print(saveFile3[i]);
       }
       }
       
       /*
       for(i=0;i<50;i++)
       {
           if(saveFile1[i]==null || saveFile3[i]==null)
               break;
           out.print(" ");
           out.print(saveFile1[i]);
           out.print(" ");
           out.print(saveFile3[i]);
           //out.print(i);
       } */
       
       
       try
       {
        con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "eshopping", "nolin"); 
        
        String i_id=session.getAttribute("i_id").toString();
        int l=Integer.parseInt(i_id);
        session.removeAttribute(i_id);
        /*Statement st= con.createStatement();
        ResultSet rs= st.executeQuery("select * from i_id"); 
        rs.next();
        int l=rs.getInt(1)+1; */
        
        PreparedStatement ps= con.prepareStatement("insert into "+saveFile3[0]+"(i_id) values(?)");
        ps.setInt(1,l);
        ps.executeQuery();
    
        for(int j=1;j<i;j++)
                {
                  PreparedStatement ps1=con.prepareStatement("update "+saveFile3[0]+" set "+saveFile1[j]+"='"+saveFile3[j]+"' where i_id='"+l+"' ");
                  ps1.executeUpdate();
                }
        
        session.setAttribute("success","success");
        response.sendRedirect("admin/add_item_1.jsp");
        
       }catch(Exception e)
            {
             //out.print(e);
            }
      }
     }catch(Exception ex){}
     finally
     {
         try
         {
             fileOut.flush();
             fileOut.close();
             con.close();
         }catch(Exception e){}
     }
    }
 }

    