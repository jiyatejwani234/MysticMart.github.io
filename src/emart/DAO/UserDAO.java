
package emart.DAO;
import emart.dbutil.DBConnection;
import emart.pojo.UserPojo;
import emart.pojo.UserProfile;
import java.sql.*;

public class UserDAO {
    public static boolean validate(UserPojo user)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement pst=conn.prepareStatement("select * from users where userid=? and password=? and usertype=?"); 
         pst.setString(1,user.getUserid());
        
         pst.setString(2,user.getPassword());
          

         pst.setString(3,user.getUsertype());
                  
         ResultSet rs=pst.executeQuery();
         if(rs.next()){
            
             UserProfile.setUsername(rs.getString("username"));
             return true;
         }
         return false;
    }
    public static boolean isUserPresent(String empid) throws SQLException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement pst=conn.prepareStatement("select 1 from users where empid=?");
       pst.setString(1,empid);
       ResultSet rs=pst.executeQuery();
       return rs.next();
         
   }
}
