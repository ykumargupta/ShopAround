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

public class category_add extends HttpServlet 
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
       if(saveFile1[i].equalsIgnoreCase("value="))
           i--;
       else if(saveFile1[i].equalsIgnoreCase("box1"))
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
       /*for(i=0;i<50;i++)
       {
           if(saveFile1[i]==null || saveFile3[i]==null)
               break;
           out.print(" ");
           out.print(saveFile1[i]);
           out.print(" ");
           out.print(saveFile3[i]);
           out.print(i);
       }*/
       
       int exc=0;
       try
       {
        con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "eshopping", "nolin"); 
        Statement stmt=null;
        stmt = con.createStatement();
        //out.print(" ");
        
        stmt.executeUpdate("create table "+saveFile3[0]+"(I_id varchar(3) primary key)");
        exc++;
      /*  if(i==1)    //no column added condition
        {
         int q= 1/0;
        }  */
        for(int j=1;j<i;j++)
                {
                  stmt.executeUpdate("ALTER TABLE "+saveFile3[0]+" ADD "+saveFile3[j]+" varchar2(50)");
                }
        
        Statement st= con.createStatement();
        ResultSet rs= st.executeQuery("select * from c_id"); 
        rs.next();
        int l=rs.getInt(1)+1;
        
        PreparedStatement ps= con.prepareStatement("insert into category(c_id,c_name,hpv) values(?,?,?)");
        ps.setInt(1,l);
        ps.setString(2,saveFile3[0]);
        ps.setInt(3,0);
        ps.executeQuery();
        
        PreparedStatement ps1=con.prepareStatement("update c_id set c_id='"+l+"'");
        ps1.executeUpdate();
        
        session.setAttribute("msg","success");
        response.sendRedirect("admin/add_category.jsp");
        //out.print("successful");
        
       }catch(Exception e)
            {
             if(exc==0)
             {
                 session.setAttribute("msg","already");
                 response.sendRedirect("admin/add_category.jsp");
             //out.print("table already exists");
             }
           /*  else if(i==1)
             {
                 PreparedStatement ps= con.prepareStatement("drop table "+saveFile3[0]+"");
                 ps.executeUpdate();
                 out.print("no columns added");
             }  */
             else
             {
                 PreparedStatement ps= con.prepareStatement("drop table "+saveFile3[0]+"");
                 ps.executeUpdate();
                 session.setAttribute("msg","notcreated");
                 response.sendRedirect("admin/add_category.jsp");
                 //out.print("table cant be created");
             }
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

    