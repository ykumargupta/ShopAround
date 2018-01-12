import java.io.*;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class edit_category extends HttpServlet 
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
       if(saveFile1[i].equalsIgnoreCase("chk"))
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
       } 
       out.print(i);  */
       
       Statement st=null;
       ResultSet rs=null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","eshopping","nolin");
        
                for(int j=1;j<i;j++)
                {
                    try{
                  if(!saveFile1[j].equalsIgnoreCase(saveFile3[j]))  
                  {
                  PreparedStatement ps=con.prepareStatement("alter table "+saveFile3[0]+" rename column "+saveFile1[j]+" to "+saveFile3[j]+"");
                  ps.executeUpdate();
                  }}catch(Exception e)
                  {
                      if(j==1)
                          out.print("reserved word cant be used");
                      else
                      {
                       for(int k=1;k<j;k++)
                        {
                        try{
                            if(!saveFile1[k].equalsIgnoreCase(saveFile3[k]))  
                             {
                              PreparedStatement ps1=con.prepareStatement("alter table "+saveFile3[0]+" rename column "+saveFile3[k]+" to "+saveFile1[k]+"");
                              ps1.executeUpdate();
                              out.print("reserved word cant be used");
                             }}catch(Exception e1){}
                        }
                      }
                }
                //out.print("successful");
            }
                st=con.createStatement();
                rs=st.executeQuery("Select c_id from category where c_name='"+saveFile3[0]+"'");
                rs.next();
                session.setAttribute("suc_cat","suc_cat");
       response.sendRedirect("admin/edit_category.jsp?c_id="+rs.getString(1)+"&name="+saveFile3[0]+"");
                
        }catch(Exception e2)
            {
                //out.print("not successful");
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

    