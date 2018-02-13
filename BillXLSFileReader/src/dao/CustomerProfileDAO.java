package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import beans.amsBeans.CustomerProfileBean;
import connectionfactory.DBConnectionFactory;

/**
 * @author Rohit
 * @Date 25 feb 2011
 */
public class CustomerProfileDAO {
	private Connection con;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	private static Log log = LogFactory.getLog("dao.CustomerProfileDAO");

	public CustomerProfileDAO() {
		getConnection();
	}

	public void getConnection() {
		try {
			con = new DBConnectionFactory().getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void commitTransaction(boolean autoCommit)throws Exception{
		if(autoCommit)
			con.commit();
		else
			con.rollback();
	}
	public void closeAll(){
		try {
			if (stmt!=null){
				stmt.close();
			}
			if(con!=null){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Rohit
	 * @Date 25 feb 2011
	 * @Descripion This method is created for the Inserting the customer profile .
	 * @method insert
	 * @return result;
	 *  
	 */
	public String insert(CustomerProfileBean objmCustomerProfileBean) {
		String result="";
		int id=0;
		try {	
			stmt= con.prepareStatement("INSERT INTO customer("+
					" login_scac_code, customer_name, address_type," +
					" address1, address2, country, state, city, "+
					" zip_code,phone_number, fax_number, "+
					" entity_type, entity_number,created_user,created_date,dob,country_of_issuance)"+
					" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?)",Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, objmCustomerProfileBean.getLoginScac());
			stmt.setString(2, objmCustomerProfileBean.getCustomerName());
			stmt.setString(3, objmCustomerProfileBean.getAddressType());
			stmt.setString(4, objmCustomerProfileBean.getAddress1());
			stmt.setString(5, objmCustomerProfileBean.getAddress2());
			stmt.setString(6, objmCustomerProfileBean.getCountry());
			stmt.setString(7, objmCustomerProfileBean.getState());
			stmt.setString(8, objmCustomerProfileBean.getCity());
			stmt.setString(9, objmCustomerProfileBean.getZipCode());
			stmt.setString(10,objmCustomerProfileBean.getPhoneNo());
			stmt.setString(11,objmCustomerProfileBean.getFaxNo());
			stmt.setString(12,objmCustomerProfileBean.getEntityType());
			stmt.setString(13,objmCustomerProfileBean.getEntityNumber());
			stmt.setString(14,objmCustomerProfileBean.getCreatedUser());
			if(objmCustomerProfileBean.getDob().equals(""))
				stmt.setString(15,null);
			else
				stmt.setString(15,objmCustomerProfileBean.getDob());
			stmt.setString(16,objmCustomerProfileBean.getCountryOfIssuance());
			
		log.info("insert query :"+stmt.toString());
			stmt.executeUpdate();
			rs=stmt.getGeneratedKeys();
			if(rs.next())
				id=rs.getInt(1);
			result="Success."+id;		   
			
		} catch (SQLException e) {
			e.printStackTrace();
			result="Exception."+id;
		}finally{
			try {
				if (!result.startsWith("Success")) {
					con.rollback();
					log.error("Error in Insert query...");
					result="Error."+id;
				}
				else 
					con.commit();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * @author Rohit
	 * @Date 25 feb 2011
	 * @Descripion This method is for the Update Customer Profile .
	 * @method Update
	 * @return result;
	 *  
	 */
	public String update (CustomerProfileBean objmCustomerProfileBean) {

		String result="";
		int a=0;
		try {
			stmt = con.prepareStatement("Update customer set customer_name=?, " +
							"address_type=?, address1=?, address2=?, " +
							"country=?, state=?, city=?, zip_code=?, " +
							"phone_number=?, fax_number=?, entity_type=?, " +
							"entity_number=?,dob=?,country_of_issuance=? where customer_id=?");
			stmt.setString(1, objmCustomerProfileBean.getCustomerName());
			stmt.setString(2, objmCustomerProfileBean.getAddressType());
			stmt.setString(3, objmCustomerProfileBean.getAddress1());
			stmt.setString(4, objmCustomerProfileBean.getAddress2());
			stmt.setString(5, objmCustomerProfileBean.getCountry());
			stmt.setString(6, objmCustomerProfileBean.getState());
			stmt.setString(7, objmCustomerProfileBean.getCity());
			stmt.setString(8, objmCustomerProfileBean.getZipCode());
			stmt.setString(9, objmCustomerProfileBean.getPhoneNo());
			stmt.setString(10,objmCustomerProfileBean.getFaxNo());
			stmt.setString(11,objmCustomerProfileBean.getEntityType());
			stmt.setString(12,objmCustomerProfileBean.getEntityNumber());
			if(objmCustomerProfileBean.getDob().equals(""))
				stmt.setString(13,null);
			else
				stmt.setString(13,objmCustomerProfileBean.getDob());
			stmt.setString(14,objmCustomerProfileBean.getCountryOfIssuance());
			stmt.setInt(15,objmCustomerProfileBean.getCustomerId());
			
			a=stmt.executeUpdate();
			if(a>0){
			result="Success";
			}else{
				result="Error";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (result.equals("Success"))
					con.commit();
				else{
					con.rollback();
					log.error("Error in Update query...");
					result="Error";
				}	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * @author Rohit
	 * @Date 25 feb 2011
	 * @Descripion This method is for the delete Customer Profile .
	 * @method delete
	 * @return result;
	 *  
	 */
	public String delete (int cutomerId) {
		String result="";
		int a=0;
		try {
			stmt = con.prepareStatement("Delete from customer Where customer_id=?");
			stmt.setInt(1, cutomerId);
			a= stmt.executeUpdate();
			if(a>0){
			result="Success";
			}else{
				result="Error";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (result.equals("Success"))
					con.commit();
				else{
					con.rollback();
					log.error("Error in Delete query...");
					result="Error";
				}	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * @author Rohit 
	 * @Date 25 feb 2011
	 * @Descripion This method is for reriving the list of Customer Profiles .
	 * @method getList
	 * @return objmList;
	 *  @param SearchString,loginScac
	 */
	public ArrayList<CustomerProfileBean> getList (String SearchString,String loginScac) {
		ArrayList<CustomerProfileBean> objmList=null;
		ResultSet rs=null;
		try {
			objmList = new ArrayList<CustomerProfileBean>();
			
			CustomerProfileBean objmCustomerProfileBean;
			stmt = con.prepareStatement("select " +
					" login_scac_code, customer_id, customer_name, address_type, " +
					" address1, address2, country, state, city, zip_code, phone_number, " +
					" fax_number, entity_type, entity_number, created_user, created_date" +
					" from customer " +
					" where customer_name = ? and login_scac_code=?");
			stmt.setString(1, SearchString);
			stmt.setString(2,loginScac);
			rs= stmt.executeQuery();
			while(rs.next()){
				objmCustomerProfileBean =new CustomerProfileBean();
				objmCustomerProfileBean.setLoginScac(rs.getString(1));
				objmCustomerProfileBean.setCustomerId(rs.getInt(2));
				objmCustomerProfileBean.setCustomerName(rs.getString(3));
				objmCustomerProfileBean.setAddressType(rs.getString(4));
				objmCustomerProfileBean.setAddress1(rs.getString(5));
				objmCustomerProfileBean.setAddress2(rs.getString(6));
				objmCustomerProfileBean.setCountry(rs.getString(7));
				objmCustomerProfileBean.setState(rs.getString(8));
				objmCustomerProfileBean.setCity(rs.getString(9));
				objmCustomerProfileBean.setZipCode(rs.getString(10));
				objmCustomerProfileBean.setPhoneNo(rs.getString(11));
				objmCustomerProfileBean.setFaxNo(rs.getString(12));
				objmCustomerProfileBean.setEntityType(rs.getString(13));
				objmCustomerProfileBean.setEntityNumber(rs.getString(14));
				objmCustomerProfileBean.setCreatedUser(rs.getString(15));
				objmCustomerProfileBean.setCreatedDate(rs.getString(16));
				objmList.add(objmCustomerProfileBean);
			}
		} catch (SQLException e) {
			log.error("Error In List retriving..");
			e.printStackTrace();
		}
		return objmList;
	}
	
	public CustomerProfileBean getData (int customerId) {
		ResultSet rs=null;
		CustomerProfileBean objmCustomerProfileBean = new CustomerProfileBean();
		try {
			
			stmt = con.prepareStatement("Select login_scac_code, customer_id, " +
					" customer_name, address_type, address1, address2, country," +
					" state, city, zip_code, phone_number, fax_number, entity_type," +
					" entity_number, created_user, created_date,ifNull(dob,''),country_of_issuance from customer " +
					" where customer_id=?" );
			stmt.setInt(1,customerId);
			rs= stmt.executeQuery();
			if (rs.next()){
				objmCustomerProfileBean.setLoginScac(rs.getString(1));
				objmCustomerProfileBean.setCustomerId(rs.getInt(2));
				objmCustomerProfileBean.setCustomerName(rs.getString(3));
				objmCustomerProfileBean.setAddressType(rs.getString(4));
				objmCustomerProfileBean.setAddress1(rs.getString(5));
				objmCustomerProfileBean.setAddress2(rs.getString(6));
				objmCustomerProfileBean.setCountry(rs.getString(7));
				objmCustomerProfileBean.setState(rs.getString(8));
				objmCustomerProfileBean.setCity(rs.getString(9));
				objmCustomerProfileBean.setZipCode(rs.getString(10));
				objmCustomerProfileBean.setPhoneNo(rs.getString(11));
				objmCustomerProfileBean.setFaxNo(rs.getString(12));
				objmCustomerProfileBean.setEntityType(rs.getString(13));
				objmCustomerProfileBean.setEntityNumber(rs.getString(14));
				objmCustomerProfileBean.setCreatedUser(rs.getString(15));
				objmCustomerProfileBean.setCreatedDate(rs.getString(16));
				objmCustomerProfileBean.setDob(rs.getString(17));
				objmCustomerProfileBean.setCountryOfIssuance(rs.getString(18));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objmCustomerProfileBean;
	}

	public boolean isExist(String customer, String loginScac) {
		Boolean result=false;
		try {
			stmt= con.prepareStatement("select customer_name "+
					" from customer " +
					" where login_scac_code=? and customer_name=? ");
			stmt.setString(1,loginScac);
			stmt.setString(2,customer);
			
			log.info("select query :"+stmt.toString());
			rs=stmt.executeQuery();
			if(rs.next()){
				return result=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public int getCustomerId(String customer, String loginScac) {
		Boolean result=false;
		int customerId = 0;
		try {
			stmt= con.prepareStatement("select customer_id "+
					" from customer " +
					" where login_scac_code=? and customer_name=? ");
			stmt.setString(1,loginScac);
			stmt.setString(2,customer);
			
			log.info("select query :"+stmt.toString());
			rs=stmt.executeQuery();
			if(rs.next()){
				customerId = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerId;
	}
	public boolean isExistCustomerID(int customerId) {
		Boolean result=false;
		try {
			stmt=con.prepareStatement("SELECT customer_id  "+
					 " FROM cargo " +
					 " where customer_id=?" +
					 " union " +
					 " SELECT customer_id "+
					 " FROM jp_cargo " +
					 " where customer_id=?" );
			
			stmt.setInt(1, customerId);
			stmt.setInt(2, customerId);
			System.out.println("select query :"+stmt.toString());
			rs=stmt.executeQuery();
			if(rs.next()){
				result=true;
			}
			else{
			stmt= con.prepareStatement("SELECT customer_id  "+
						" from  consignee_shipper_details " +
						" where customer_id=? "+
						" union " +
						" SELECT customer_id  "+
						" from  jp_consignee_shipper_details " +
						" where customer_id=? ");
			stmt.setInt(1, customerId);
			stmt.setInt(2, customerId);
			System.out.println("select query :"+stmt.toString());
			rs=stmt.executeQuery();
			if(rs.next()){
				result=true;
			   }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
