/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.DAO;

import emart.dbutil.DBConnection;
import emart.pojo.ProductsPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class ProductDAO {
   public static String getNextProductId()throws SQLException{
       Connection conn=DBConnection.getConnection();
       Statement stmt=conn.createStatement();
       ResultSet rs=stmt.executeQuery("select max(p_id) from Products");
       
       rs.next();
       String pid=rs.getString(1);
       if(pid!=null){
       int pno=Integer.parseInt(pid.substring(1));
       String nxteid="P"+(pno+1);
       return nxteid;}
       else{
           return "P101"; }
   } 
   public static boolean addProduct(ProductsPojo p)throws SQLException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement pst=conn.prepareStatement("Insert into products values(?,?,?,?,?,?,?,'Y')");
       pst.setString(1,p.getProductId());
       pst.setString(2,p.getProductName());
       pst.setString(3,p.getProductCompany());
       pst.setDouble(4,p.getProductPrice());
       pst.setDouble(5,p.getOurPrice());
       pst.setInt(6,p.getQuantity());
       pst.setInt(7,p.getTax());
       return pst.executeUpdate()==1;
     }
   public static List<ProductsPojo> getProductDetail()throws SQLException{
        Connection conn=DBConnection.getConnection();
       Statement st=conn.createStatement();
       ResultSet rs=st.executeQuery("select * from products where status='Y' order by p_id");
       List<ProductsPojo> al=new ArrayList<>();
       while(rs.next()){
           ProductsPojo prod=new ProductsPojo();
           prod.setProductId(rs.getString(1));
           prod.setProductName(rs.getString(2));
           prod.setProductCompany(rs.getString(3));
           prod.setProductPrice(rs.getDouble(4));
           prod.setOurPrice(rs.getDouble(5));
           prod.setQuantity(rs.getInt(6));
           prod.setTax(rs.getInt(7));
           al.add(prod);
       }
       return al;
   }
   public static boolean deleteProduct(String p_id)throws SQLException{
        Connection conn=DBConnection.getConnection();
       PreparedStatement pst=conn.prepareStatement("update products set status='N' where p_id=?");
       pst.setString(1,p_id);
       return pst.executeUpdate()==1;
   }
   public static boolean updateProduct(ProductsPojo p)throws SQLException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement pst=conn.prepareStatement("update products set p_name=?,P_companyname=?,p_price=?,our_price=?,quantity=?,p_tax=? where p_id=?");
       pst.setString(1,p.getProductName()); 
       pst.setString(2,p.getProductCompany());
       pst.setDouble(3,p.getProductPrice());
       pst.setDouble(4,p.getOurPrice());
       pst.setInt(5,p.getQuantity()); 
       pst.setInt(6,p.getTax());
       pst.setString(7,p.getProductId());
       return pst.executeUpdate()==1;
   }
    public static List<ProductsPojo> getAllProducts() throws SQLException{
       Connection conn=DBConnection.getConnection();
       Statement stmt=conn.createStatement();
       ResultSet rs=stmt.executeQuery("Select * from Products  where status='Y'order by p_id");
       List<ProductsPojo> al=new ArrayList<>();
     
       while(rs.next()){
          ProductsPojo p=new ProductsPojo();
          p.setProductId(rs.getString(1));
          p.setProductName(rs.getString(2));
          p.setProductCompany(rs.getString(3));
          p.setProductPrice(rs.getDouble(4));
          p.setOurPrice(rs.getDouble(5));
          p.setQuantity(rs.getInt(7));
          p.setTax(rs.getInt(6));
          
          al.add(p);
       }
      return al;
    }
    public static ProductsPojo getProductDetails(String id)throws SQLException{
         Connection con=DBConnection.getConnection();
         PreparedStatement p=con.prepareStatement("select * from products where p_id=? and status='Y'");
         p.setString(1,id);
         ResultSet rs=p.executeQuery();
         ProductsPojo pro=new ProductsPojo();
         if(rs.next()){
         pro.setProductId(id);
         pro.setProductName(rs.getString(2));
         pro.setProductCompany(rs.getString(3));
         pro.setProductPrice(Double.parseDouble(rs.getString(4)));
         pro.setOurPrice(Double.parseDouble(rs.getString(5)));
         pro.setTax(Integer.parseInt(rs.getString(6)));
         pro.setQuantity(Integer.parseInt(rs.getString(7)));
         }
         return pro;
       }
  public static boolean updateQuantity(ArrayList<ProductsPojo> al)throws SQLException{
         Connection con=DBConnection.getConnection();
         int x=0;
         PreparedStatement p=con.prepareStatement("update products set quantity=quantity-? where p_id=?");
         for(ProductsPojo pro:al){
         p.setInt(1,pro.getQuantity());
         p.setString(2,pro.getProductId());
         int rows=p.executeUpdate();
         if(rows!=0)
             x++;
         }
         return x==al.size();
     } 
}


