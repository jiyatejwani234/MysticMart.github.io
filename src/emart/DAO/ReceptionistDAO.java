
package emart.DAO;

import emart.dbutil.DBConnection;
import emart.pojo.ReceptionistPojo;
import emart.pojo.UserPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceptionistDAO {
    public static Map<String,String> getNonRegisteredReceptionist()throws SQLException{
        Connection conn= DBConnection.getConnection();
        Statement stmt =conn.createStatement();
        ResultSet rs=stmt.executeQuery("select empid,Empname from employees where job='Receptionist'and empid not in (select empid from users)");
        Map<String,String> hsh=new HashMap<>();
        while(rs.next()){
           hsh.put(rs.getString(1),rs.getString(2));
           
        }
    return hsh;
}
  public static boolean addReceptionist(UserPojo user)throws SQLException{
       Connection conn= DBConnection.getConnection();
        PreparedStatement pst =conn.prepareStatement("insert into users values(?,?,?,?,?)");
        pst.setString(1,user.getUserid());
        pst.setString(2,user.getEmpid());
        pst.setString(3,user.getPassword());
        pst.setString(4,user.getUsertype());
        pst.setString(5,user.getUsername());
        int r=pst.executeUpdate();
        
        return r==1;
    }
  public static List<ReceptionistPojo> getAllReceptionistDetail()throws SQLException{
       Connection conn= DBConnection.getConnection();
        Statement stmt =conn.createStatement();
        ResultSet rs=stmt.executeQuery("select users.empid,empname,userid,job,salary from employees,users where usertype='Receptionist' and users.empid=employees.empid ");
        ArrayList<ReceptionistPojo> al=new ArrayList<>();
        ReceptionistPojo rp;
        while(rs.next()){
            rp=new ReceptionistPojo();
            rp.setEmpid(rs.getString(1));
            rp.setEmpname(rs.getString(2));
            rp.setUserid(rs.getString(3));
            rp.setJob(rs.getString(4));
            rp.setSalary(rs.getDouble(5));
            al.add(rp);
            
        }
        return al;
       }
  public static Map<String,String> getAllReceptionistId()throws SQLException{
        Connection conn= DBConnection.getConnection();
        Statement stmt =conn.createStatement();
        ResultSet rs=stmt.executeQuery("select userid,username from users where usertype='Receptionist'");
        Map<String,String> m=new HashMap<>();
        while(rs.next()){
            m.put(rs.getString(1),rs.getString(2));
        }
        
        return m;
 }
  public static boolean updatePassword(String userid,String psw)throws SQLException{
       Connection conn= DBConnection.getConnection();
        PreparedStatement pst =conn.prepareStatement("update users set password=? where userid=?");
        pst.setString(1, psw);
        pst.setString(2, userid);
       int c=pst.executeUpdate();
       return c==1;
  }
   public static List<String> getAllReceptionistUserId()throws SQLException{
        Connection conn= DBConnection.getConnection();
        Statement stmt =conn.createStatement();
        ResultSet rs=stmt.executeQuery("select userid from users where usertype='Receptionist'");
        List<String> m=new ArrayList<>();
       while(rs.next()){
            m.add(rs.getString(1));
        }
        
        return m;
   }
    public static boolean deleteReceptionist(String userid)throws SQLException{

       Connection conn= DBConnection.getConnection();
        PreparedStatement pst =conn.prepareStatement("delete from users where userid=?");
        pst.setString(1, userid);
       int c=pst.executeUpdate();
       return c==1;

    }
   
}
