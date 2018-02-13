/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.amsBeans.VesselBean;
import connectionfactory.DBConnectionFactory;

/**
 * @author Rohit
 * @Date 28 feb 2011
 */
public class VesselDAO {
	private Connection con;
	private PreparedStatement stmt = null;
	ResultSet rs=null;

	public VesselDAO() {
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
	public void closeAll(){
		try {
			if(rs!=null){
				rs.close();
			}
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
 * @Date 28 feb 2011
 * @Descripion This method is created for the Inserting the Vessel Details .
 * @method insert
 * @return result;
 * @param objmUserProfileBean
**/
	
	public int getVesselId(String vesselName, String loginScac) {
		try {
			stmt = con.prepareStatement("Select vessel_id from vessel " +
					" where vessel_name=? and login_scac=?");
			stmt.setString(1, vesselName);
			stmt.setString(2, loginScac);
			rs=stmt.executeQuery();
			rs=stmt.executeQuery();
			if (rs.next()){
				return rs.getInt(1);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	public String insert(VesselBean objmVesselBean) {

		String result="";
		int autoIncrementId=0;
		try {
			
			stmt= con.prepareStatement("Insert into vessel " +
					"(login_scac, vessel_name, vessel_type, " +
					" owner, lloyds_code, send_to_custom1, send_to_custom2, " +
					" country, usa_scac_code, is_voyage_created, created_user, created_date)" +
					" VALUES (?,?,?,?,?,?,?,?,?,?,?,now()) ",Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1,objmVesselBean.getLoginScac());
			stmt.setString(2,objmVesselBean.getVesselName());
			stmt.setString(3,objmVesselBean.getVesselType());
			stmt.setString(4,objmVesselBean.getOwner());
			stmt.setString(5,objmVesselBean.getLloydsCode());
			stmt.setBoolean(6,objmVesselBean.getSendToCustome1());
			stmt.setBoolean(7,objmVesselBean.getSendToCustom2());
			stmt.setString(8,objmVesselBean.getCountry());
			stmt.setString(9,objmVesselBean.getUsaScacCode());
			stmt.setBoolean(10,objmVesselBean.getIsVoyageCreated());
			stmt.setString(11,objmVesselBean.getCreatedUser());
				stmt.execute();
				rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				autoIncrementId = rs.getInt(1);
			}
			else  
				System.out.print("Autogenerated key nt returned");
			stmt= con.prepareStatement("Insert into vessel_details " +
					"(vessel_id,login_scac, vessel_registry_place, vessel_registry_number, " +
					" length, length_unit, ship_owner, net_weight, net_weight_unit," +
					" gross_weight, gross_weight_unit, containerized_weight, " +
					" containerized_weight_unit, non_containerized_weight, " +
					" non_containerized_weight_unit, summer_dead_weight, " +
					" summer_dead_weight_unit, ship_certificate, ship_certificate_exp_date, " +
					" radio_certificate, radio_certificate_exp_date, equipment_certificate, " +
					" equipment_certificate_exp_date, load_line_certificate, " +
					" load_line_certificate_exp_date, derat_certificate, " +
					" derat_certificate_exp_date, health_certificate, health_certificate_exp_date, " +
					" oil_certificate, oil_certificate_exp_date)" +
					" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, autoIncrementId);
			stmt.setString(2,  objmVesselBean.getLoginScac());
			stmt.setString(3,  objmVesselBean.getRegistryPlace());
			stmt.setString(4,  objmVesselBean.getRegistryNumber());
			stmt.setString(5,  objmVesselBean.getLength());
			stmt.setString(6,  objmVesselBean.getLengthUnit());
			stmt.setString(7,  objmVesselBean.getShipOwner());
			stmt.setString(8,  objmVesselBean.getNetWeight());
			stmt.setString(9,  objmVesselBean.getNetWeightUnit());
			stmt.setString(10, objmVesselBean.getGrossWeight());
			stmt.setString(11, objmVesselBean.getGrossWeightUnit());
			stmt.setString(12, objmVesselBean.getContainerizedWeight());
			stmt.setString(13, objmVesselBean.getContainerizedWeightUnit());
			stmt.setString(14, objmVesselBean.getNonContainerizedweight());
			stmt.setString(15, objmVesselBean.getNonContainerizedweightUnit());
			stmt.setString(16, objmVesselBean.getSummerDeadweight());
			stmt.setString(17, objmVesselBean.getSummerDeadweightUnit());
			stmt.setString(18, objmVesselBean.getShipCertificate());
			stmt.setString(19, objmVesselBean.getShipExpiryDate());
			stmt.setString(20, objmVesselBean.getRadioCertificate());
			stmt.setString(21, objmVesselBean.getRadioExpiryDate());
			stmt.setString(22, objmVesselBean.getEquipmentCertificate());
			stmt.setString(23, objmVesselBean.getEquipmentExpiryDate());
			stmt.setString(24, objmVesselBean.getLoadLineCertificate());
			stmt.setString(25, objmVesselBean.getLoadLineExpiryDate());
			stmt.setString(26, objmVesselBean.getDeratCertificate());
			stmt.setString(27, objmVesselBean.getDeratExpiryDate());
			stmt.setString(28, objmVesselBean.getHealthCertificate());
			stmt.setString(29, objmVesselBean.getHealthExpiryDate());
			stmt.setString(30, objmVesselBean.getOilCertificate());
			stmt.setString(31, objmVesselBean.getOilExpiryDate());
			
			stmt.execute();

			result="Success";
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (!result.equals("Success")) {
					con.rollback();
					result="Error";
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
 * @Date 28 feb 2011
 * @Descripion This method is for the Update Vessel Details.
 * @method Update
 * @return result;
 * @param objmVesselBean
**/
	public String update (VesselBean objmVesselBean) {

		String result="";
		int a=0;
		try {
			stmt = con.prepareStatement("Update vessel set" +
					" vessel_name=?, vessel_type=?, owner=?, lloyds_code=?, " +
					" send_to_custom1=?, send_to_custom2=?, country=?, usa_scac_code=? " +
					" where vessel_id=?");
			stmt.setString(1,objmVesselBean.getVesselName());
			stmt.setString(2,objmVesselBean.getVesselType());
			stmt.setString(3,objmVesselBean.getOwner());
			stmt.setString(4,objmVesselBean.getLloydsCode());
			stmt.setBoolean(5,objmVesselBean.getSendToCustome1());
			stmt.setBoolean(6,objmVesselBean.getSendToCustom2());
			stmt.setString(7,objmVesselBean.getCountry());
			stmt.setString(8,objmVesselBean.getUsaScacCode());
			stmt.setInt(9, objmVesselBean.getVesselId());
			
			a=stmt.executeUpdate();
			
//			stmt = con.prepareStatement("Update vessel_details set " +
//					" vessel_registry_place=?, vessel_registry_number=?, length=?, length_unit=?," +
//					" ship_owner=?, net_weight=?, net_weight_unit=?, gross_weight=?, gross_weight_unit=?," +
//					" containerized_weight=?, containerized_weight_unit=?, non_containerized_weight=?," +
//					" non_containerized_weight_unit=?, summer_dead_weight=?, summer_dead_weight_unit=?," +
//					" ship_certificate=?, ship_certificate_exp_date=?, radio_certificate=?," +
//					" radio_certificate_exp_date=?, equipment_certificate=?, equipment_certificate_exp_date=?," +
//					" load_line_certificate=?, load_line_certificate_exp_date=?, derat_certificate=?," +
//					" derat_certificate_exp_date=?, health_certificate=?, health_certificate_exp_date=?," +
//					" oil_certificate=?, oil_certificate_exp_date=?" +
//					" where vessel_id=? " );
//			stmt.setString(1,  objmVesselBean.getRegistryPlace());
//			stmt.setString(2,  objmVesselBean.getRegistryNumber());
//			stmt.setString(3,  objmVesselBean.getLength());
//			stmt.setString(4,  objmVesselBean.getLengthUnit());
//			stmt.setString(5,  objmVesselBean.getShipOwner());
//			stmt.setString(6,  objmVesselBean.getNetWeight());
//			stmt.setString(7,  objmVesselBean.getNetWeightUnit());
//			stmt.setString(8,  objmVesselBean.getGrossWeight());
//			stmt.setString(9,  objmVesselBean.getGrossWeightUnit());
//			stmt.setString(10, objmVesselBean.getContainerizedWeight());
//			stmt.setString(11, objmVesselBean.getContainerizedWeightUnit());
//			stmt.setString(12, objmVesselBean.getNonContainerizedweight());
//			stmt.setString(13, objmVesselBean.getNonContainerizedweightUnit());
//			stmt.setString(14, objmVesselBean.getSummerDeadweight());
//			stmt.setString(15, objmVesselBean.getSummerDeadweightUnit());
//			stmt.setString(16, objmVesselBean.getShipCertificate());
//			stmt.setString(17, objmVesselBean.getShipExpiryDate());
//			stmt.setString(18, objmVesselBean.getRadioCertificate());
//			stmt.setString(19, objmVesselBean.getRadioExpiryDate());
//			stmt.setString(20, objmVesselBean.getEquipmentCertificate());
//			stmt.setString(21, objmVesselBean.getEquipmentExpiryDate());
//			stmt.setString(22, objmVesselBean.getLoadLineCertificate());
//			stmt.setString(23, objmVesselBean.getLoadLineExpiryDate());
//			stmt.setString(24, objmVesselBean.getDeratCertificate());
//			stmt.setString(25, objmVesselBean.getDeratExpiryDate());
//			stmt.setString(26, objmVesselBean.getHealthCertificate());
//			stmt.setString(27, objmVesselBean.getHealthExpiryDate());
//			stmt.setString(28, objmVesselBean.getOilCertificate());
//			stmt.setString(29, objmVesselBean.getOilExpiryDate());
//			stmt.setInt(30, objmVesselBean.getVesselId());
//			b=stmt.executeUpdate();
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
	 * @Date 28 feb 2011
	 * @Descripion This method is for the delete Vessel Details.
	 * @method delete
	 * @return result;
	 * @param vesselId
	 *  
	 */
	public String delete (int vesselId ) {
		String result="";
		int a=0;
		try {
			stmt = con.prepareStatement("Delete from vessel where vessel_id=? and is_voyage_created = ?");
			stmt.setInt(1, vesselId);
			stmt.setBoolean(2, false);
			a=stmt.executeUpdate();
//			stmt = con.prepareStatement("Delete from vessel_details where vessel_id=?");
//			stmt.setInt(1, vesselId);
//			b= stmt.executeUpdate();
			if(a>0){
			result="Success";
			}else {
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
					result="Error";
				}	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	public Boolean isVoyageCreated(String loginScac,int vesselId){
		Boolean result=false;
		try {
			stmt=con.prepareStatement("select is_voyage_created from vessel where vessel_id=? and login_scac=?");
			stmt.setInt(1, vesselId);
			stmt.setString(2, loginScac);
			rs=stmt.executeQuery();
			if(rs.next()){
				result=rs.getBoolean(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result=false;
		}
		return result;
	}
	/**
	 * @author Rohit
	 * @Date 1 March 2011
	 * @Descripion This method is for reriving the list of Vessel Details.
	 * @method getList
	 * @return objmList;
	 *  @param searchVessel,loginScac
	 */
	public ArrayList<VesselBean> getList (String searchVessel,String loginScac ) {
		ArrayList<VesselBean> objmList=null;
		ResultSet rs=null;
		try {
			objmList = new ArrayList<VesselBean>();
			
			VesselBean objmVesselBean;
			stmt = con.prepareStatement("Select vessel_id, vessel_name,usa_scac_code  " +
					" from vessel where vessel_name like ? and login_scac=?");
			stmt.setString(1, searchVessel+"%");
			stmt.setString(2,loginScac);
			rs= stmt.executeQuery();
			while(rs.next()){
				objmVesselBean =new VesselBean();
				objmVesselBean.setVesselId(rs.getInt(1));
				objmVesselBean.setVesselName(rs.getString(2));
				objmVesselBean.setUsaScacCode(rs.getString(3));
				objmList.add(objmVesselBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objmList;
	}
	
	/**
	 * @author Rohit 
	 * @Date 1 march 2011
	 * @Descripion This method is for the retriving the Vessel Details .
	 * @method getData
	 * @return objmVesselBean;
	 * @param vesselId
	 */
	public VesselBean getData (int vesselId) {
		ResultSet rs=null;
		VesselBean objmVesselBean = new VesselBean();
		try {
			stmt = con.prepareStatement("Select vessel_id, vessel_name, vessel_type, owner, " +
					" lloyds_code, send_to_custom1, send_to_custom2, country, usa_scac_code ,login_scac" +
					" from vessel where vessel_id=?");
			stmt.setInt(1, vesselId);
			rs= stmt.executeQuery();
			if (rs.next()){
				objmVesselBean.setVesselId(rs.getInt(1));
				objmVesselBean.setVesselName(rs.getString(2));
				objmVesselBean.setVesselType(rs.getString(3));
				objmVesselBean.setOwner(rs.getString(4));
				objmVesselBean.setLloydsCode(rs.getString(5));
				objmVesselBean.setSendToCustome1(rs.getBoolean(6));
				objmVesselBean.setSendToCustom2(rs.getBoolean(7));
				objmVesselBean.setCountry(rs.getString(8));
				objmVesselBean.setUsaScacCode(rs.getString(9));
				objmVesselBean.setLoginScac(rs.getString(10));
			}
			stmt = con.prepareStatement("Select vessel_registry_place, vessel_registry_number, " +
					" length, length_unit, ship_owner, net_weight, net_weight_unit, gross_weight, " +
					" gross_weight_unit, containerized_weight, containerized_weight_unit, " +
					" non_containerized_weight, non_containerized_weight_unit, summer_dead_weight, " +
					" summer_dead_weight_unit, ship_certificate, ship_certificate_exp_date, " +
					" radio_certificate, radio_certificate_exp_date, equipment_certificate, " +
					" equipment_certificate_exp_date, load_line_certificate, load_line_certificate_exp_date," +
					" derat_certificate, derat_certificate_exp_date, health_certificate, " +
					" health_certificate_exp_date, oil_certificate, oil_certificate_exp_date " +
					" from vessel_details where vessel_id=?");
			stmt.setInt(1, vesselId);
			rs= stmt.executeQuery();
			if(rs.next()){
				
				objmVesselBean.setRegistryPlace(rs.getString(1));
				objmVesselBean.setRegistryNumber(rs.getString(2));
				objmVesselBean.setLength(rs.getString(3));
				objmVesselBean.setLengthUnit(rs.getString(4));
				objmVesselBean.setShipOwner(rs.getString(5));
				objmVesselBean.setNetWeight(rs.getString(6));
				objmVesselBean.setNetWeightUnit(rs.getString(7));
				objmVesselBean.setGrossWeight(rs.getString(8));
				objmVesselBean.setGrossWeightUnit(rs.getString(9));
				objmVesselBean.setContainerizedWeight(rs.getString(10));
				objmVesselBean.setContainerizedWeightUnit(rs.getString(11));
				objmVesselBean.setNonContainerizedweight(rs.getString(12));
				objmVesselBean.setNonContainerizedweightUnit(rs.getString(13));
				objmVesselBean.setSummerDeadweight(rs.getString(14));
				objmVesselBean.setSummerDeadweightUnit(rs.getString(15));
				objmVesselBean.setShipCertificate(rs.getString(16));
				objmVesselBean.setShipExpiryDate(rs.getString(17));
				objmVesselBean.setRadioCertificate(rs.getString(18));
				objmVesselBean.setRadioExpiryDate(rs.getString(19));
				objmVesselBean.setEquipmentCertificate(rs.getString(20));
				objmVesselBean.setEquipmentExpiryDate(rs.getString(21));
				objmVesselBean.setLoadLineCertificate(rs.getString(22));
				objmVesselBean.setLoadLineExpiryDate(rs.getString(23));
				objmVesselBean.setDeratCertificate(rs.getString(24));
				objmVesselBean.setDeratExpiryDate(rs.getString(25));
				objmVesselBean.setHealthCertificate(rs.getString(26));
				objmVesselBean.setHealthExpiryDate(rs.getString(27));
				objmVesselBean.setOilCertificate(rs.getString(28));
				objmVesselBean.setOilExpiryDate(rs.getString(29));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objmVesselBean;
	}
	
	/**
	 * @author Rohit
	 * @date 1 March 2011
	 * @method isExist
	 * @return result
	 * @parm vesselName,loginScac
	 **/
		public Boolean isExist(String vesselName,String loginScac){
			ResultSet rs=null;
			Boolean result=true;
			try{
				stmt = con.prepareStatement("Select * from vessel " +
						" where vessel_name=? and login_scac=?");
				stmt.setString(1, vesselName);
				stmt.setString(2, loginScac);
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
		
		
		public ArrayList<VesselBean> getVesselList(String searchVessel,String loginScac ) {
			ArrayList<VesselBean> objmList=null;
			ResultSet rs=null;
			try {
				objmList = new ArrayList<VesselBean>();
				
				VesselBean objmVesselBean;
				stmt = con.prepareStatement("Select vessel_id, vessel_name,usa_scac_code  " +
						" from vessel where vessel_name like ? and login_scac=? and is_voyage_created = true");
				stmt.setString(1, searchVessel+"%");
				stmt.setString(2,loginScac);
				rs= stmt.executeQuery();
				while(rs.next()){
					objmVesselBean =new VesselBean();
					objmVesselBean.setVesselId(rs.getInt(1));
					objmVesselBean.setVesselName(rs.getString(2));
					objmVesselBean.setUsaScacCode(rs.getString(3));
					objmList.add(objmVesselBean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return objmList;
		}
		public VesselBean getDataXML (String lloydscode,String loginScac) {
			ResultSet rs=null;
			VesselBean objmVesselBean = null;
			try {
				stmt = con.prepareStatement("Select vessel_id, vessel_name,usa_scac_code from vessel " +
						"where vessel_name=? and login_scac=?");
				stmt.setString(1, lloydscode);
				stmt.setString(2, loginScac);
				rs= stmt.executeQuery();
				if (rs.next()){
					objmVesselBean=new VesselBean();
					objmVesselBean.setVesselId(rs.getInt(1));
					objmVesselBean.setVesselName(rs.getString(2));
					objmVesselBean.setUsaScacCode(rs.getString(3));
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return objmVesselBean;
		}
			
}