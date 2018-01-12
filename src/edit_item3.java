import java.io.*;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class edit_item3 extends HttpServlet 
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
    throws ServletException, IOException 
    {
     FileOutputStream fileOut=null;
     response.setContentType("text/html");
     HttpSession session=request.getSession(true);
     int c_id=0;
     PrintWriter out = response.getWriter();
     try
     {
      String contentType = request.getContentType();
      if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
       {
       DataInputStream in = new DataInputStream(request.getInputStream());
       int formDataLength = request.getContentLength();
       byte dataBytes[] = new byte[formDataLength];
       int byteRead = 0;
       int totalBytesRead = 0;
       while (totalBytesRead < formDataLength) 
        {
        byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
        totalBytesRead += byteRead;
        }
       String file = new String(dataBytes);
       out.println(file);
       
       String saveFile = file.substring(file.indexOf("filename=\"") + 10);
       saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
       saveFile = saveFile.substring(saveFile.lastIndexOf("\\")+ 1,saveFile.indexOf("\""));
       int lastIndex = contentType.lastIndexOf("=");
       String boundary = contentType.substring(lastIndex + 1,contentType.length());
       int pos;
       pos = file.indexOf("filename=\"");
       pos = file.indexOf("\n", pos) + 1;
       pos = file.indexOf("\n", pos) + 1;
       pos = file.indexOf("\n", pos) + 1;
       int boundaryLocation = file.indexOf(boundary, pos) - 4;
       int startPos = ((file.substring(0, pos)).getBytes()).length;
       int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
       fileOut = new FileOutputStream(saveFile);
       fileOut.write(dataBytes, startPos, (endPos - startPos));
       
       String saveFile1 = file.substring(file.indexOf("name=\"i_id\"") + 11);
       saveFile1 = saveFile1.substring(4,(saveFile1.indexOf("-")-2));
       
       out.print(endPos - startPos); 
       out.print(" ");
       out.print(saveFile);
       out.print(" ");
       out.print(saveFile1);
       
       try
       {
        con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "eshopping", "nolin"); 
        PreparedStatement ps1=con.prepareStatement("update item_image set IMAGE_ICON=?,IMAGE_NAME=? where i_id='"+saveFile1+"' ");
        FileInputStream fis= new FileInputStream(saveFile);
        ps1.setBinaryStream(1,fis, (int)(endPos - startPos));
        ps1.setString(2,saveFile);
        ps1.executeUpdate();
        
        PreparedStatement ps2= con.prepareStatement("update item set IMAGE_NAME='"+saveFile+"' where i_id='"+saveFile1+"'");
        ps2.executeUpdate();
   
        session.setAttribute("suc","suc");
        response.sendRedirect("admin/edit_item3.jsp");
       }catch(Exception e){out.print("not successfull");}  
      }
     }catch(Exception ex){out.print("outer try error");}
      finally
        {
         try{
         fileOut.flush();
         fileOut.close();
         con.close();}catch(Exception e){}
        }
    }
 }

    