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

public class single_upload_page1 extends HttpServlet 
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
     HttpSession session=request.getSession(true);
     response.setContentType("text/html");
     int c_id=0;
     PrintWriter out = response.getWriter();
     try
     {
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
       String file = new String(dataBytes);
       //out.println(file);
       //for saving the file name
       String saveFile = file.substring(file.indexOf("filename=\"") + 10);
       //out.println(saveFile);
       saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
       saveFile = saveFile.substring(saveFile.lastIndexOf("\\")+ 1,saveFile.indexOf("\""));
       //out.println(saveFile);
       int lastIndex = contentType.lastIndexOf("=");
       //out.println(lastIndex);
       String boundary = contentType.substring(lastIndex + 1,contentType.length());
       //out.println(boundary);
       int pos;
       //out.println(file);
       //extracting the index of file 
       pos = file.indexOf("filename=\"");
       //out.println(pos);
       pos = file.indexOf("\n", pos) + 1;
       //out.println(pos);
       pos = file.indexOf("\n", pos) + 1;
       //out.println(pos);
       pos = file.indexOf("\n", pos) + 1;
       //out.println(pos);
       //out.println(file.substring(0,pos));
       int boundaryLocation = file.indexOf(boundary, pos) - 4;
       //out.println(boundaryLocation);
       int startPos = ((file.substring(0, pos)).getBytes()).length;
       //out.println(startPos);
       int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
       //out.println(endPos);
       // creating a new file with the same name and writing the content in new file
       fileOut = new FileOutputStream(saveFile);
       fileOut.write(dataBytes, startPos, (endPos - startPos));
       //out.print(endPos - startPos);  

       String saveFile0 = file.substring(file.indexOf("name=\"i_name\"") + 13);
       saveFile0 = saveFile0.substring(4,(saveFile0.indexOf("-")-2));
       //out.print(saveFile0);

       String saveFile1="";
       saveFile1 = file.substring(file.indexOf("name=\"drop1\"") + 12);
       saveFile1 = saveFile1.substring(4,(saveFile1.indexOf("-")-2));
       //out.print(saveFile1);
       c_id=Integer.parseInt(saveFile1);

       String saveFile2="";
       try
        {
        saveFile2 = file.substring(file.indexOf("name=\"t2\"") + 9);
        saveFile2 = saveFile2.substring(4,(saveFile2.indexOf("-")-2));
        }catch(Exception e)
        {
        saveFile2 = file.substring(file.indexOf("name=\"drop2\"") + 12);
        saveFile2 = saveFile2.substring(4,(saveFile2.indexOf("-")-2));
        }
       //out.print(saveFile2);
            
       String saveFile3 = file.substring(file.indexOf("name=\"av\"") + 9);
       saveFile3 = saveFile3.substring(4,(saveFile3.indexOf("-")-2));
       //out.print(saveFile3);
       int av= Integer.parseInt(saveFile3);
               
       String saveFile4 = file.substring(file.indexOf("name=\"pp\"") + 9);
       saveFile4 = saveFile4.substring(4,(saveFile4.indexOf("-")-2));
       int saveFile41=Integer.parseInt(saveFile4);
       //out.print(saveFile4);
        
       String saveFile5 = file.substring(file.indexOf("name=\"sp\"") + 9);
       saveFile5 = saveFile5.substring(4,(saveFile5.indexOf("-")-2));
       int saveFile51=Integer.parseInt(saveFile5);
       //out.print(saveFile5);
       
       String saveFile6 = file.substring(file.indexOf("name=\"discount\"") + 15);
       saveFile6 = saveFile6.substring(4,(saveFile6.indexOf("-")-2));
       int saveFile61=Integer.parseInt(saveFile6);
       
       int disc_price=saveFile51-(saveFile51*saveFile61/100);
            
       //out.print(endPos - startPos);
       
       //out.print(saveFile);
           
       try
       {
        con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "eshopping", "nolin"); 
        Statement st= con.createStatement();
        Statement st1= con.createStatement();
        ResultSet rs1= st1.executeQuery("select * from item");
        int xy=0;
        while(rs1.next())
        {
            if(rs1.getString(3).equalsIgnoreCase(saveFile0))
            {
                xy=1;
                break;
            }
        }
        if(xy==1)
        {
            session.setAttribute("alrdy","alrdy");
            response.sendRedirect("admin/add_item_1.jsp");
        }
        else
        {
        ResultSet rs= st.executeQuery("select * from i_id"); 
        rs.next();
        int j=rs.getInt(1)+1;
        //out.print(j);
        
        PreparedStatement ps= con.prepareStatement("insert into item(I_ID,A_ID,I_NAME,C_ID,BRAND_NAME,"
                + "AVAILIBILITY,NO_OF_ITEMS_SOLD,IMAGE_NAME,PURCHASED_PRICE,SELLING_PRICE,ADD_DATE,"
                + "ITEM_VIEW,HPVI,DISCOUNT,DISCOUNT_PRICE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        
        ps.setInt(1,j);
        ps.setInt(2,1);
        ps.setString(3,saveFile0);
        ps.setInt(4,c_id);
        ps.setString(5,saveFile2);
        ps.setInt(6,av);
        ps.setInt(7,0); 
        ps.setString(8,saveFile);
        ps.setInt(9,saveFile41);
        ps.setInt(10,saveFile51);
        
        
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
     
        ps.setString(11,date); 
        
        ps.setInt(12,0); 
        ps.setInt(13,0); 
        ps.setInt(14,saveFile61);
        ps.setInt(15,disc_price);
        
        ps.executeQuery();
      
        PreparedStatement ps1= con.prepareStatement("insert into ITEM_IMAGE(I_ID,IMAGE_ICON,IMAGE_NAME,C_ID) values(?,?,?,?)");
        FileInputStream fis= new FileInputStream(saveFile);
        ps1.setInt(1,j);
        ps1.setBinaryStream(2,fis, (int)(endPos - startPos));
        ps1.setString(3,saveFile);
        ps1.setInt(4,c_id);
        ps1.executeQuery();
        
        PreparedStatement ps2=con.prepareStatement("update i_id set i_id='"+j+"'");
        ps2.executeUpdate();
      
        response.sendRedirect("admin/add_item_detail.jsp?c_id="+c_id+"&i_id="+j+" ");
       }
        //out.print("successful");
       }catch(Exception e){out.print("not successfull");}  
      }
     }catch(Exception ex){out.print("outer try error");}
      finally
        {
            try{
         fileOut.flush();
         fileOut.close();
         con.close();
        }catch(Exception e){}}
    }
 }

    