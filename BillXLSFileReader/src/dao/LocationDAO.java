package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import beans.amsBeans.CanadaBean;
import beans.amsBeans.DistrictPortBean;
import beans.amsBeans.ForeignPortBean;
import beans.amsBeans.LocationBean;
import beans.amsBeans.UnlocodeBean;
import connectionfactory.DBConnectionFactory;

/**
 * @author rohit
 * @date 25 feb 2011 
 */
public class LocationDAO {
	
	private Connection con;
	private PreparedStatement stmt = null,stmt2 = null,stmt3= null,stmt4= null;
	private ResultSet rs = null;
	private static Log log = LogFactory.getLog("dao.LocationDAO");

	public LocationDAO() {
		try {
			con = new DBConnectionFactory().getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setAutoCommit()throws Exception{
		con.setAutoCommit(false);
	}
	public void commit(boolean commit) throws Exception{
		if(commit)
			con.commit();
		else
			con.rollback();
	}
	public void closeAll(){
		try {
			if (rs!=null)
				rs.close();
			if (stmt!=null)
				stmt.close();
			if (stmt2!=null)
				stmt2.close();
			if (stmt3!=null)
				stmt3.close();
			if (stmt4!=null)
				stmt4.close();
			if(con!=null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
/**
 * @author Rohit
 * @date 25Feb 2011
 * @method insert
 * @param LocationBean
 * @description This Method is for the inserting the location details
 * @return result
*/
	public String insert(LocationBean objmLocationBean){
		String result="";
		Boolean flag=true;
		try{
			stmt=con.prepareStatement("select location_id from location where location_code=? and login_scac=? and location_code!=''");
				stmt.setString(1, objmLocationBean.getLocationCode());
				stmt.setString(2, objmLocationBean.getLoginScac());
			rs=stmt.executeQuery();
			if(rs.next()){
				objmLocationBean.setLocationId(rs.getInt(1));
				
				stmt=con.prepareStatement("select ifnull(max(sequence_number),0)+1 from alt_location where location_id=? and login_scac=?");
				stmt.setInt(1, objmLocationBean.getLocationId());
				stmt.setString(2, objmLocationBean.getLoginScac());
				rs=stmt.executeQuery();
				int sequenceNumber=1;
				if(rs.next())
					sequenceNumber=rs.getInt(1);
				
				stmt=con.prepareStatement("insert into alt_location values(?,?,?,?)");
				stmt.setInt(1, objmLocationBean.getLocationId());
				stmt.setInt(2, sequenceNumber);
				stmt.setString(3, objmLocationBean.getLoginScac());
				stmt.setString(4, objmLocationBean.getLocationName());
				stmt.executeUpdate();
				
				stmt2=con.prepareStatement("update location set hold_at_lp=?,location_type=?,canada_custom_code=?, unlocode=? where location_id=? and login_scac=?");
				stmt2.setString(1, objmLocationBean.getHoldAtLp());
				stmt2.setString(2, objmLocationBean.getLocationType());
				stmt2.setString(3, objmLocationBean.getCanadaCustomCode());
				stmt2.setString(4, objmLocationBean.getUnlocode());
				stmt2.setInt(5, objmLocationBean.getLocationId());
				stmt2.setString(6, objmLocationBean.getLoginScac());
				
				if(stmt2.executeUpdate()<0)
					flag=false;
			}else{
				stmt=con.prepareStatement("INSERT INTO location (location_code," +
						"  login_scac, location_name,country, state, location_type, " +
						" hold_at_lp,is_voyage_created, created_user,created_date,is_custom_foreign,canada_custom_code,unlocode ) " +
						" Values (?,?,?,?,?,?,?,?,?,now(),?,?,?)",Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, objmLocationBean.getLocationCode());
				stmt.setString(2, objmLocationBean.getLoginScac());
				stmt.setString(3, objmLocationBean.getLocationName());
				stmt.setString(4, objmLocationBean.getCountry());
				stmt.setString(5, objmLocationBean.getState());
				stmt.setString(6, objmLocationBean.getLocationType());
				stmt.setString(7, objmLocationBean.getHoldAtLp());
				stmt.setBoolean(8,objmLocationBean.getIsVoyageCreated());
				stmt.setString(9, objmLocationBean.getCreatedUser());
				stmt.setBoolean(10, objmLocationBean.getIsCustomForeign());
				stmt.setString(11, objmLocationBean.getCanadaCustomCode());
				stmt.setString(12, objmLocationBean.getUnlocode());
				
				if(stmt.executeUpdate()<0)
					flag=false;
				else{
					rs = stmt.getGeneratedKeys();
					rs.next();
					objmLocationBean.setLocationId(rs.getInt(1));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			flag=false;
		}finally{
			if (flag) {
				result="Success";
			}else{ 
				result="Error";
			}
		}
		return result;
	}
	/**
	 * @author Rohit
	 * @date 25Feb 2011
	 * @method update
	 * @param LocationBean
	 * @description This Method is for the inserting the location details
	 * @return result
	*/
	public String update(LocationBean objmLocationBean){
		String result="";
		try {
			stmt = con.prepareStatement("Update location set hold_at_lp=?,location_type=?,canada_custom_code=?, unlocode=? where location_id=?");
			stmt.setString(1, objmLocationBean.getHoldAtLp());
			stmt.setString(2, objmLocationBean.getLocationType());
			stmt.setString(3, objmLocationBean.getCanadaCustomCode());
			stmt.setString(4, objmLocationBean.getUnlocode());
			stmt.setInt(5, objmLocationBean.getLocationId());
			
			if(stmt.executeUpdate()>0)
				result="Success";
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			result="Error";
		}
		return result;
	}
/**
 * @author Rohit
 * @Date 25 feb 2011
 * @Descripion This method is for the delete User Profile .
 * @method delete
 * @param locationId
 * @return result;
*/
	public Boolean isNotValidToUpdate(LocationBean objmLocationBean){
		Boolean result=false;
		try {
			stmt = con.prepareStatement("select is_voyage_created from location where location_id=? and login_scac=?");
			stmt.setInt(1, objmLocationBean.getLocationId());
			stmt.setString(2, objmLocationBean.getLoginScac());
			rs=stmt.executeQuery();
			if(rs.next())
				result=rs.getBoolean(1);
		} catch (Exception e) {
			 e.printStackTrace();
			 result=false;
			 log.error(e);
		}
		return result;
	}
	public String delete(LocationBean objmLocationBean){
	String result="Success";
	String tableName="";
	try {
		//code to find out in which table the location name exists
		stmt3=con.prepareStatement("select * from location where location_id=? and login_scac=? and location_name=?");
		stmt3.setInt(1, objmLocationBean.getLocationId());
		stmt3.setString(2, objmLocationBean.getLoginScac());
		stmt3.setString(3, objmLocationBean.getLocationName());
		rs=stmt3.executeQuery();
		if(rs.next())
			tableName="location";
		else
			tableName="alt_location";
		//code to delete reocrd from alt_location/location
		if(tableName.equals("alt_location")){
			stmt3 = con.prepareStatement("delete from "+tableName+" where location_id=? and login_scac=? and alt_name=?");
			stmt3.setInt(1, objmLocationBean.getLocationId());
			stmt3.setString(2, objmLocationBean.getLoginScac());
			stmt3.setString(3, objmLocationBean.getLocationName());
			if(stmt3.executeUpdate()!=1)
				result="Error";
		}else{
			String altLocationName="";
			int count=1;
			stmt3=con.prepareStatement("select * from alt_location where location_id=? and login_scac=? limit 1");
			stmt3.setInt(1, objmLocationBean.getLocationId());
			stmt3.setString(2, objmLocationBean.getLoginScac());
			rs=stmt3.executeQuery();
			if(rs.next())
				altLocationName=rs.getString(4);
			
			if(!altLocationName.equals("")){
				stmt3=con.prepareStatement("delete from alt_location where location_id=? and alt_name=? and login_scac=?");
				stmt3.setInt(1, objmLocationBean.getLocationId());
				stmt3.setString(2, altLocationName);
				stmt3.setString(3, objmLocationBean.getLoginScac());
				if(stmt3.executeUpdate()!=1)
					result="Error";
				stmt4 = con.prepareStatement("update "+tableName+" set location_name=? where location_id=? and login_scac=? and location_name=?");
				stmt4.setString(count++, altLocationName);
			}else{
				stmt4 = con.prepareStatement("delete from "+tableName+" where location_id=? and login_scac=? and location_name=?");
			}
			if(!result.equals("Error")){
				stmt4.setInt(count++, objmLocationBean.getLocationId());
				stmt4.setString(count++, objmLocationBean.getLoginScac());
				stmt4.setString(count++, objmLocationBean.getLocationName());
				if(stmt4.executeUpdate()!=1)
					result="Error";
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		log.error(e);
		result="Exception";
	}
	return result;
}
	/**
	 * @author Rohit
	 * @Date 25 feb 2011
	 * @Descripion This method is for reriving the list of Locations.
	 * @method getList
	 * @return objmList;
	 *  @param locationName,loginScac
	*/
		public ArrayList<LocationBean> getList(String locationName ,String loginScac){
			ArrayList<LocationBean> objmList=null;
			ResultSet rs=null;
			try {
				objmList = new ArrayList<LocationBean>();
				LocationBean objmLocationBean;
				stmt = con.prepareStatement("(Select a.location_id,c.alt_name,a.location_code, b.country_name, a.state, a.location_type," +
						"a.is_custom_foreign from location a left outer join alt_location c on a.location_id=c.location_id, country b " +
						"where c.alt_name like ? and a.login_scac=? and a.country = b.iso_code) " +
						"union " +
						"(Select location_id,location_name,location_code, country_name, state, location_type," +
						" is_custom_foreign from location a, country b " +
						" where location_name like ? " +
						" and login_scac=? " +
						" and a.country = b.iso_code)");
				stmt.setString(1,locationName+"%");
				stmt.setString(2, loginScac);
				stmt.setString(3,locationName+"%");
				stmt.setString(4, loginScac);
				log.info(stmt.toString());
				rs= stmt.executeQuery();
				while(rs.next()){
					objmLocationBean =new LocationBean();
					objmLocationBean.setLocationId(rs.getInt(1));
					objmLocationBean.setLocationName(rs.getString(2));
					objmLocationBean.setLocationCode(rs.getString(3));
					objmLocationBean.setCountry(rs.getString(4));
					objmLocationBean.setState(rs.getString(5));
					objmLocationBean.setLocationType(rs.getString(6));
					objmList.add(objmLocationBean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return objmList;
		}
		
		public ArrayList<LocationBean> getListByLocationCode(String locationCode,String loginScac){
			ArrayList<LocationBean> objmList=null;
			ResultSet rs=null;
			try {
				objmList = new ArrayList<LocationBean>();
				LocationBean objmLocationBean;
				stmt = con.prepareStatement("(Select a.location_id,c.alt_name,a.location_code, b.country_name, a.state, a.location_type," +
						"a.is_custom_foreign,a.canada_custom_code from location a ,alt_location c ,country b " +
						"where a.location_code like ? and a.login_scac=? and a.country = b.iso_code and a.location_id=c.location_id) " +
						"union " +
						"(Select location_id,location_name,location_code, country_name, state, location_type,is_custom_foreign,canada_custom_code " +
						"from location a, country b " +
						"where a.location_code like ? and login_scac=? and a.country = b.iso_code)");
				stmt.setString(1,locationCode+"%");
				stmt.setString(2, loginScac);
				stmt.setString(3,locationCode+"%");
				stmt.setString(4, loginScac);
				log.info(stmt.toString());
				rs= stmt.executeQuery();
				while(rs.next()){
					objmLocationBean =new LocationBean();
					objmLocationBean.setLocationId(rs.getInt(1));
					objmLocationBean.setLocationName(rs.getString(2));
					objmLocationBean.setLocationCode(rs.getString(3));
					objmLocationBean.setCountry(rs.getString(4));
					objmLocationBean.setState(rs.getString(5));
					objmLocationBean.setLocationType(rs.getString(6));
					objmLocationBean.setCanadaCustomCode(rs.getString(8));
					objmList.add(objmLocationBean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return objmList;
		}
		/**
		 * @author vikas
		 * @Date 4 july 2011
		 * @Descripion This method use to get the list of marine location
		 * @method getMarineList
		 * @return objmList;
		 *  @param locationName,loginScac
		*/
			public ArrayList<LocationBean> getMarineList(String locationName ,String loginScac){
				ArrayList<LocationBean> objmList=null;
				ResultSet rs=null;
				try {
					objmList = new ArrayList<LocationBean>();
					LocationBean objmLocationBean;
					stmt = con.prepareStatement("Select location_id,location_name,location_code, country_name, " +
							"state, location_type,is_custom_foreign from location a, country b " +
							" where location_name like ? " +
							" and login_scac=? " +
							" and a.country = b.iso_code " +
							" and location_type='M' " +
							"union " +
							"Select a.location_id,c.alt_name,a.location_code, b.country_name,a.state, a.location_type,a.is_custom_foreign " +
							"from location a , country b,alt_location c " +
							"where c.alt_name like ? and a.login_scac=? and a.country = b.iso_code and a.location_type='M' " +
							"and a.location_id=c.location_id");
					
					stmt.setString(1,locationName+"%");
					stmt.setString(2, loginScac);
					stmt.setString(3,locationName+"%");
					stmt.setString(4, loginScac);
					
					log.info(stmt.toString());
					rs= stmt.executeQuery();
					while(rs.next()){
						objmLocationBean =new LocationBean();
						objmLocationBean.setLocationId(rs.getInt(1));
						objmLocationBean.setLocationName(rs.getString(2));
						objmLocationBean.setLocationCode(rs.getString(3));
						objmLocationBean.setCountry(rs.getString(4));
						objmLocationBean.setState(rs.getString(5));
						objmLocationBean.setLocationType(rs.getString(6));
						objmList.add(objmLocationBean);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return objmList;
			}
			/**
			 * @author vikas
			 * @Date 4 july 2011
			 * @Descripion This method use to get the list of marine location
			 * @method getMarineList
			 * @return objmList;
			 *  @param locationName,loginScac
			*/
				public ArrayList<LocationBean> getInlandList(String locationName ,String loginScac){
					ArrayList<LocationBean> objmList=null;
					ResultSet rs=null;
					try {
						objmList = new ArrayList<LocationBean>();
						LocationBean objmLocationBean;
						stmt = con.prepareStatement("Select location_id,location_name,location_code, country_name, " +
								"state, location_type,is_custom_foreign from location a, country b " +
								" where location_name like ? " +
								" and login_scac=? " +
								" and a.country = b.iso_code " +
								" and location_type='I' " +
								"union " +
								"Select a.location_id,c.alt_name,a.location_code, b.country_name,a.state, a.location_type,a.is_custom_foreign " +
								"from location a, country b, alt_location c " +
								"where c.alt_name like ? and a.login_scac=? and a.country = b.iso_code and a.location_type='I' " +
								"and a.location_id=c.location_id");
						
						stmt.setString(1,locationName+"%");
						stmt.setString(2, loginScac);
						stmt.setString(3,locationName+"%");
						stmt.setString(4, loginScac);
						
						log.info(stmt.toString());
						rs= stmt.executeQuery();
						while(rs.next()){
							objmLocationBean =new LocationBean();
							objmLocationBean.setLocationId(rs.getInt(1));
							objmLocationBean.setLocationName(rs.getString(2));
							objmLocationBean.setLocationCode(rs.getString(3));
							objmLocationBean.setCountry(rs.getString(4));
							objmLocationBean.setState(rs.getString(5));
							objmLocationBean.setLocationType(rs.getString(6));
							objmList.add(objmLocationBean);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return objmList;
				}
			
			
/**
 * @author Rohit
 * @Date 25 feb 2011
 * @Descripion This method is for reriving the Location Data.
 * @method getData
 * @return objmList;
 *  @param locationId,loginScac
*/	
	
	public LocationBean getData(int locationId ,String loginScac,String locationName){
		ResultSet rs=null;
		LocationBean objmLocationBean = new LocationBean();;
		try {
			stmt = con.prepareStatement("select location_code, location_name, " +
					" country, state, location_type, hold_at_lp,is_custom_foreign, is_voyage_created,canada_custom_code,unlocode "+
					"from location where location_id=? and login_scac=? and location_name=? " +
					"union " +
					" select a.location_code, alt_name,a.country, a.state, a.location_type, a.hold_at_lp,a.is_custom_foreign, " +
					" a.is_voyage_created,a.canada_custom_code,a.unlocode " +
					"from location a,alt_location b " +
					"where a.location_id=? and a.login_scac=? and b.alt_name=? and a.location_id=b.location_id" );
			stmt.setInt(1,locationId);
			stmt.setString(2,loginScac);
			stmt.setString(3, locationName);
			stmt.setInt(4,locationId);
			stmt.setString(5,loginScac);
			stmt.setString(6, locationName);
			rs= stmt.executeQuery();
			if (rs.next()){
				objmLocationBean.setLocationCode(rs.getString(1));
				objmLocationBean.setLocationName(rs.getString(2));
				objmLocationBean.setCountry(rs.getString(3));
				objmLocationBean.setState(rs.getString(4));
				objmLocationBean.setLocationType(rs.getString(5));
				objmLocationBean.setHoldAtLp(rs.getString(6));
				objmLocationBean.setIsCustomForeign(rs.getBoolean(7));
				objmLocationBean.setIsVoyageCreated(rs.getBoolean(8));
				objmLocationBean.setCanadaCustomCode(rs.getString(9));
				objmLocationBean.setUnlocode(rs.getString(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objmLocationBean;
	}	
/**
 * @author Rohit
 * @Date 25 feb 2011
 * @Descripion This method is for reriving the Location Data.
 * @method isExist
 * @return result;
 * @param locationId,loginScac,locationName,country,state
*/		
	public Boolean isExist(String locationCode,String loginScac){
		ResultSet rs=null;
		Boolean result=true;
		try{
			stmt = con.prepareStatement("Select * from location where location_code=?" +
									" and login_scac=?");
			stmt.setString(1, locationCode);
			stmt.setString(2,loginScac);
			rs=stmt.executeQuery();
			if (rs.next()){
				result=true;
		}else{
			result=false;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
		
	}
	public Boolean checkForLocationName(String locationName,String loginScac){
		ResultSet rs=null;
		Boolean result=true;
		try{
			stmt = con.prepareStatement("Select location_name from location where location_name=? and login_scac=? " +
					"union Select alt_name from alt_location where alt_name=? and login_scac=?");
			stmt.setString(1, locationName);
			stmt.setString(2,loginScac);
			stmt.setString(3, locationName);
			stmt.setString(4,loginScac);
			rs=stmt.executeQuery();
			if (rs.next()){
				result=true;
		}else{
			result=false;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
		
	}
	
	public ArrayList<ForeignPortBean> getLoadPortList(int voyageId,
			String loginScac) {
		ArrayList<ForeignPortBean> objmList = null;
		try {
			objmList = new ArrayList<ForeignPortBean>();
			
			String LoadPortListQuery = "SELECT b.location_name, c.location_code "
					+ " FROM voyage a, voyage_details b, location c "
					+ " WHERE a.voyage_id= ?"					
					+ " and a.voyage_id=b.voyage_id "
					+ " and c.location_id=b.location_id "
					+ " AND b.is_load_port=true "
					+ " and a.login_scac=?";
			stmt = con.prepareStatement(LoadPortListQuery);
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			log.info(stmt.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ForeignPortBean objmForeignPortBean = new ForeignPortBean();
				objmForeignPortBean.setPortName(rs.getString(1));
				objmForeignPortBean.setPortCode(rs.getString(2));
				objmList.add(objmForeignPortBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
	
	
	public ArrayList<ForeignPortBean> getLoadPortListFromName(int voyageId,
			String loginScac, String locationName) {
		ArrayList<ForeignPortBean> objmList = null;
		try {
			objmList = new ArrayList<ForeignPortBean>();
			
			String LoadPortListQuery = "SELECT b.location_name, c.location_code "
					+ " FROM voyage a, voyage_details b, location c "
					+ " WHERE a.voyage_id= ?"					
					+ " and a.voyage_id=b.voyage_id "
					+ " and c.location_id=b.location_id "
					+ " AND b.is_load_port=true "
					+ " and a.login_scac=?"
					+ " and b.location_name=?";
			stmt = con.prepareStatement(LoadPortListQuery);
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			stmt.setString(3, locationName);
			log.info(stmt.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ForeignPortBean objmForeignPortBean = new ForeignPortBean();
				objmForeignPortBean.setPortName(rs.getString(1));
				objmForeignPortBean.setPortCode(rs.getString(2));
				objmList.add(objmForeignPortBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
	
	public ArrayList<ForeignPortBean> getJpnLoadPortList(int voyageId,
			String loginScac) {
		ArrayList<ForeignPortBean> objmList = null;
		try {
			objmList = new ArrayList<ForeignPortBean>();
			
			String LoadPortListQuery = "SELECT b.location_name, c.location_code "
					+ " FROM jp_voyage a, jp_voyage_details b, location c "
					+ " WHERE a.voyage_id= ?"					
					+ " and a.voyage_id=b.voyage_id "
					+ " and c.location_id=b.location_id "
					+ " AND b.is_load_port=true "
					+ " and a.login_scac=?";
			stmt = con.prepareStatement(LoadPortListQuery);
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			log.info(stmt.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ForeignPortBean objmForeignPortBean = new ForeignPortBean();
				objmForeignPortBean.setPortName(rs.getString(1));
				objmForeignPortBean.setPortCode(rs.getString(2));
				objmList.add(objmForeignPortBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
	
	
	
	public ArrayList<DistrictPortBean> getDischargePortList(int voyageId,
			String loginScac) {
		ArrayList<DistrictPortBean> objmList = new ArrayList<DistrictPortBean>();
		try {
			
			stmt = con.prepareStatement(" SELECT b.location_name, c.location_code "
					+ " FROM voyage a, voyage_details b, location c "
					+ " WHERE a.voyage_id= ? "
					+ " AND a.voyage_id=b.voyage_id "
					+ " AND c.location_id=b.location_id "
					+ " AND b.is_discharge_port=true " 
					+ " AND a.login_scac= ? ");
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			log.info(stmt.toString());
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				DistrictPortBean objmDistrictPortBean = new DistrictPortBean();
				objmDistrictPortBean.setPortName(rs.getString(1));
				objmDistrictPortBean.setPortCode(rs.getString(2));
				objmList.add(objmDistrictPortBean);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
	
	public ArrayList<DistrictPortBean> getDischargePortListFromName(int voyageId,
			String loginScac, String locationName) {
		ArrayList<DistrictPortBean> objmList = new ArrayList<DistrictPortBean>();
		try {
			
			stmt = con.prepareStatement(" SELECT b.location_name, c.location_code "
					+ " FROM voyage a, voyage_details b, location c "
					+ " WHERE a.voyage_id= ? "
					+ " AND a.voyage_id=b.voyage_id "
					+ " AND c.location_id=b.location_id "
					+ " AND b.is_discharge_port=true " 
					+ " AND a.login_scac= ? and b.location_name=? ");
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			stmt.setString(3, locationName);
			log.info(stmt.toString());
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				DistrictPortBean objmDistrictPortBean = new DistrictPortBean();
				objmDistrictPortBean.setPortName(rs.getString(1));
				objmDistrictPortBean.setPortCode(rs.getString(2));
				objmList.add(objmDistrictPortBean);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
	
	public ArrayList<DistrictPortBean> getJpnDischargePortList(int voyageId,
			String loginScac) {
		ArrayList<DistrictPortBean> objmList = new ArrayList<DistrictPortBean>();
		try {
			
			stmt = con.prepareStatement(" SELECT b.location_name, c.location_code "
					+ " FROM jp_voyage a, jp_voyage_details b, location c "
					+ " WHERE a.voyage_id= ? "
					+ " AND a.voyage_id=b.voyage_id "
					+ " AND c.location_id=b.location_id "
					+ " AND b.is_discharge_port=true " 
					+ " AND a.login_scac= ? ");
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			log.info(stmt.toString());
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				DistrictPortBean objmDistrictPortBean = new DistrictPortBean();
				objmDistrictPortBean.setPortName(rs.getString(1));
				objmDistrictPortBean.setPortCode(rs.getString(2));
				objmList.add(objmDistrictPortBean);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
	
	
	public ArrayList<DistrictPortBean> getUSDistrictPortList(int voyageId,
			String loginScac) {
		ArrayList<DistrictPortBean> objmList = new ArrayList<DistrictPortBean>();
		try {
			
			stmt = con.prepareStatement(" SELECT b.location_name, c.location_code "
					+ " FROM voyage a, voyage_details b, location c,district_port d "
					+ " WHERE a.voyage_id= ? "
					+ " AND a.voyage_id=b.voyage_id "
					+ " AND c.location_id=b.location_id "
					+ " AND b.is_discharge_port=true " 
					+ " AND a.login_scac= ? " 
					+ " AND d.port_code=c.location_code");
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			log.info(stmt.toString());
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				DistrictPortBean objmDistrictPortBean = new DistrictPortBean();
				objmDistrictPortBean.setPortName(rs.getString(1));
				objmDistrictPortBean.setPortCode(rs.getString(2));
				objmList.add(objmDistrictPortBean);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
	
	
	
	
	/*
	 * Code for load port and discharge port list of original manifest 
	 */
	public ArrayList<ForeignPortBean> getOrignalManifestLoadPortList(int voyageId,
			String loginScac) {
		ArrayList<ForeignPortBean> objmList = null;
		try {
			objmList = new ArrayList<ForeignPortBean>();
			
			String LoadPortListQuery = "SELECT distinct c.location_name, c.location_code "
					+ " FROM voyage a, voyage_details b, location c, bill_header d"
					+ " WHERE a.voyage_id= ?"					
					+ " and a.voyage_id=b.voyage_id "
					+ " and c.location_id=b.location_id "
					+ " AND b.is_load_port=true "
					+ " and a.login_scac=?"
					+ " and d.voyage_id = a.voyage_id "
					+ " and d.login_scac = a.login_scac";
			stmt = con.prepareStatement(LoadPortListQuery);
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			log.info(stmt.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ForeignPortBean objmForeignPortBean = new ForeignPortBean();
				objmForeignPortBean.setPortName(rs.getString(1));
				objmForeignPortBean.setPortCode(rs.getString(2));
				objmList.add(objmForeignPortBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
	
	public ArrayList<DistrictPortBean> getOrignalManifestDischargePortList(int voyageId,
			String loginScac) {
		ArrayList<DistrictPortBean> objmList = new ArrayList<DistrictPortBean>();
		try {
			
			stmt = con.prepareStatement(" SELECT distinct c.location_name, c.location_code "
					+ " FROM voyage a, voyage_details b, location c , bill_header d"
					+ " WHERE a.voyage_id= ? "
					+ " AND a.voyage_id=b.voyage_id "
					+ " AND c.location_id=b.location_id "
					+ " AND b.is_discharge_port=true " 
					+ " AND a.login_scac= ? "+ " and d.voyage_id = a.voyage_id "
					+ " and d.login_scac = a.login_scac");
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			log.info(stmt.toString());
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				DistrictPortBean objmDistrictPortBean = new DistrictPortBean();
				objmDistrictPortBean.setPortName(rs.getString(1));
				objmDistrictPortBean.setPortCode(rs.getString(2));
				objmList.add(objmDistrictPortBean);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
	/*
	 * end of code for load port and discharge port list of original manifest 
	 */

	public LocationBean getPortName(String locationCode,String loginScac){
		LocationBean objmLocationBean = new LocationBean();
		try{
			
			stmt=con.prepareStatement("SELECT location_name from location where location_code=? and login_scac=?");
			stmt.setString(1, locationCode);
			stmt.setString(2, loginScac);
			rs=stmt.executeQuery();
			while(rs.next()){
				objmLocationBean.setLocationName(rs.getString(1));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return objmLocationBean;
	}

	public int getLocationId(String locationName, String loginScac) {
		try {
			stmt = con.prepareStatement("Select location_id from location where location_name=?" +
			" and login_scac=?");
			stmt.setString(1, locationName);
			stmt.setString(2,loginScac);
			log.info(stmt.toString());
			rs=stmt.executeQuery();
			if (rs.next()){
				return rs.getInt(1);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}

	public LocationBean getDischargePortListFromVoyageDetails(int voyageId,String locationCode,String loginScac) {
		LocationBean objmLocationBean = new LocationBean();
		try {
			
			stmt = con.prepareStatement(" SELECT b.location_name, c.location_code "
					+ " FROM voyage a, voyage_details b, location c "
					+ " WHERE a.voyage_id= ? "
					+ " AND a.voyage_id=b.voyage_id "
					+ " AND c.location_id=b.location_id "
					+ " AND b.is_discharge_port=true " 
					+ " AND a.login_scac= ? and c.location_code=?");
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			stmt.setString(3, locationCode);
			log.info(stmt.toString());
			rs = stmt.executeQuery();
			if(rs.next()) 
				objmLocationBean.setLocationName(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmLocationBean;
	}public LocationBean getLoadPortListFromVoyageDetails(int voyageId,String locationCode,String loginScac) {
		LocationBean objmLocationBean = new LocationBean();
		try {
			
			stmt = con.prepareStatement(" SELECT b.location_name, c.location_code "
					+ " FROM voyage a, voyage_details b, location c "
					+ " WHERE a.voyage_id= ? "
					+ " AND a.voyage_id=b.voyage_id "
					+ " AND c.location_id=b.location_id "
					+ " AND b.is_load_port=true " 
					+ " AND a.login_scac= ? and c.location_code=?");
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			stmt.setString(3, locationCode);
			log.info(stmt.toString());
			rs = stmt.executeQuery();
			if(rs.next()) 
				objmLocationBean.setLocationName(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmLocationBean;
	}
	
	public boolean isCandaport(String locationName) {
		ResultSet rs=null;
		Boolean result=true;
		try{
			stmt = con.prepareStatement(" SELECT * FROM canada_port WHERE port_name=? ");
			stmt.setString(1, locationName);
			rs=stmt.executeQuery();
			if (rs.next()){
				result=true;
		}else{
			result=false;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public ArrayList<CanadaBean> getCanadaList(String locationName) {
		ArrayList<CanadaBean> objCanadaBeans=new ArrayList<CanadaBean>();
		try{
			
			stmt=con.prepareStatement(" select port_code, port_name FROM canada_port where port_name like ? ");
			stmt.setString(1, locationName+"%");
			System.out.println(stmt.toString());
			rs=stmt.executeQuery();
			while(rs.next()){
				CanadaBean objCanadaBean=new CanadaBean();
				objCanadaBean.setPortCode(rs.getString(1));
				objCanadaBean.setPortName(rs.getString(2));
				objCanadaBeans.add(objCanadaBean);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return objCanadaBeans;
	}
	
	
	public boolean isExists(String unlocode)throws Exception{
		boolean result=false;
		Statement objmStatement=con.createStatement();
		ResultSet objmResultSet=objmStatement.executeQuery("select code from new_port_code where code='"+unlocode+"'");
		log.info(objmStatement.toString());	
		if(objmResultSet.next())
			result=true;
		return result;
	}
	
	public ArrayList<UnlocodeBean> getWorldsUnlocode (String SearchUnlocode ) {
		ArrayList<UnlocodeBean> objmList=null;
		if(SearchUnlocode==null)
			SearchUnlocode="";
		try {
			objmList = new ArrayList<UnlocodeBean>();
			
			UnlocodeBean objmUnlocodeBean;
			stmt = con.prepareStatement(" Select " +
					" unlocode, country, areaName " +
					" from worlds_unlocode " +
					" where unlocode like ? OR country like ? OR areaName like ?");
			
			stmt.setString(1, SearchUnlocode+"%");
			stmt.setString(2, SearchUnlocode+"%");
			stmt.setString(3, SearchUnlocode+"%");
			log.info(stmt.toString());
			rs= stmt.executeQuery();
			while(rs.next()){
				objmUnlocodeBean  = new UnlocodeBean();				
				objmUnlocodeBean.setUnlocode(rs.getString(1));
				objmUnlocodeBean.setCountry(rs.getString(2));
				objmUnlocodeBean.setAreaName(rs.getString(3));
				objmList.add(objmUnlocodeBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objmList;
	}
	
}