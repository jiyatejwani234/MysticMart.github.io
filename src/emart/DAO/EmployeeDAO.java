
package emart.DAO;

import emart.dbutil.DBConnection;
import emart.pojo.EmployeePojo;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmployeeDAO {
   public static String getNextEmpId()throws SQLException{
       Connection conn=DBConnection.getConnection();
       Statement stmt=conn.createStatement();
       ResultSet rs=stmt.executeQuery("select max(empid) from Employees");
       rs.next();
       String empid=rs.getString(1);
       int eid=Integer.parseInt(empid.substring(1));
       String nxteid="E"+(eid+1);
       return nxteid;
   }
   public static boolean addEmployee(EmployeePojo e)throws SQLException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement pst=conn.prepareStatement("Insert into employees values(?,?,?,?)");
       pst.setString(1,e.getEmpid());
       pst.setString(2,e.getEmpname());
       pst.setString(3,e.getJob());
       pst.setDouble(4,e.getSalary());
       int c=pst.executeUpdate();
       return c==1;
           
   }
   public static List<EmployeePojo> getAllEmployee() throws SQLException{
       Connection conn=DBConnection.getConnection();
       Statement stmt=conn.createStatement();
       ResultSet rs=stmt.executeQuery("Select * from Employees order by empid");
       List<EmployeePojo> al=new ArrayList<>();
     
       while(rs.next()){
          EmployeePojo emp=new EmployeePojo();
          emp.setEmpid(rs.getString(1));
          emp.setEmpname(rs.getString(2));
          emp.setJob(rs.getString(3));
          emp.setSalary(rs.getDouble(4));
          al.add(emp);
       }
      return al;
   }
   public static List<String> getAllEmployeeId() throws SQLException {
        Connection conn=DBConnection.getConnection();
       Statement stmt=conn.createStatement();
       ResultSet rs=stmt.executeQuery("Select empid from Employees order by empid"); 
       List<String> al=new ArrayList<>();
       while(rs.next()){
           al.add(rs.getString(1));
       }
       return al;
   }
   public static EmployeePojo getEmployeeById(String id) throws SQLException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement pst=conn.prepareStatement("select * from employees where empid=?");
       pst.setString(1, id);
       EmployeePojo emp=new EmployeePojo();
     
       ResultSet rs=pst.executeQuery();
       if(rs.next()){
       emp.setEmpid(rs.getString(1));
       emp.setEmpname(rs.getString(2));
       emp.setJob(rs.getString(3));
       emp.setSalary(rs.getDouble(4));}
       return emp;
   }
   public static boolean UpdateEmployee(EmployeePojo e)throws SQLException{
     Connection conn=DBConnection.getConnection();
       PreparedStatement pst=conn.prepareStatement("update employees set empname=?,job=?,Salary=? where empid=?");
       pst.setString(1,e.getEmpname());
       pst.setString(2,e.getJob());
       pst.setDouble(3,e.getSalary());
       pst.setString(4,e.getEmpid());
       int c=pst.executeUpdate();
       if(c==0)return false;
       else if(c==1){
          boolean res=UserDAO.isUserPresent(e.getEmpid());
          if(res==false)return true;
       }
        pst=conn.prepareStatement("update users set username=?,usertype=? where empid=?");
        pst.setString(1,e.getEmpname());
        pst.setString(2,e.getJob());
        pst.setString(3,e.getEmpid());
        int y=pst.executeUpdate();
        return (y==1);
        

       }
   public static boolean deleteEmployee(String empid)throws SQLException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement pst=conn.prepareStatement("delete from employees where empid=?");
       pst.setString(1,empid);
       int c=pst.executeUpdate();
       return c==1;
      
   }
   
}

