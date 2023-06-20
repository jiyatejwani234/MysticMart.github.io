/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.DAO;

import emart.dbutil.DBConnection;
import emart.pojo.ProductsPojo;
import emart.pojo.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class OrderDAO {
      public static String getNextOrderId()throws SQLException{
       Connection conn=DBConnection.getConnection();
       Statement stmt=conn.createStatement();
       ResultSet rs=stmt.executeQuery("select max(order_id) from Orders");
       
       rs.next();
       String oid=rs.getString(1);
       if(oid!=null){
       int ono=Integer.parseInt(oid.substring(2));
       String nxteid="O-"+(ono+1);
       return nxteid;}
       else{
           return "O-101"; }
   } 
      public static boolean addOrder(ArrayList<ProductsPojo> al,String oid)throws SQLException{
          Connection conn=DBConnection.getConnection();
          int count=0;
          PreparedStatement ps=conn.prepareStatement("insert into orders values(?,?,?,?)");
          for(ProductsPojo p:al){
              ps.setString(1, oid);
              ps.setString(2,p.getProductId());
              ps.setInt(3, p.getQuantity());
              ps.setString(4,UserProfile.getUserid());
              count+=ps.executeUpdate();
              
          }
          
          return count==al.size();
      }
}
