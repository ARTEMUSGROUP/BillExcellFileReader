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

import connectionfactory.DBConnectionFactory;
import beans.amsBeans.BillDetailBean;
import beans.amsBeans.CargoBean;
import beans.amsBeans.EquipmentBean;
import beans.amsBeans.HarmonizedBean;
import beans.amsBeans.NotifyBean;
import beans.amsBeans.PackageBean;
/**
 * @author Vikas
 *
 */
public class BillHeaderDAO {
	private Connection con;
	private PreparedStatement stmt = null,stmt1=null,MIstmt=null;
	private ResultSet rs = null,rs1=null,MIrs=null;
	public BillHeaderDAO(){
		getConnection();
	}
	public void setAutoCommit(boolean autoCommit)throws Exception{
		con.setAutoCommit(autoCommit);
	}
	public void commitTransaction(boolean autoCommit)throws Exception{
		if(autoCommit)
			con.commit();
		else
			con.rollback();
	}
	public void getConnection() {
		try {
			con = new DBConnectionFactory().getConnection();
			con.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void closeAll(){
		try {
			if(rs!=null)
				rs.close();
			if (stmt!=null)
				stmt.close();
			if(con!=null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
/**
 * @author Vikas
 * @method isExist
 * @param billLadingNumber,loginScac
 * @return result
 */
	public boolean isExists(String billLadingNumber,String loginScac)throws Exception{
		boolean result=false;
			Statement objmStatement=con.createStatement();
			ResultSet objmResultSet=objmStatement.executeQuery("select bill_lading_id from bill_header where bill_lading_number='"+billLadingNumber+"' and login_scac='"+loginScac+"'");
			if(objmResultSet.next())
					result=true;
		return result;
	}
	/**
	 * @author Rohit
	 * @method getList
	 * @param billLadingNumber,loginScac
	 * @return objmList
	 * @throws Exception 
	 * @date 6 april 2011
	 * @description This method is for the retriving the bill details .
	 */
	public ArrayList<BillDetailBean>getList(String billLadingNumber ,String loginScac) {
		ArrayList<BillDetailBean> objmList=null;
		ResultSet rs=null;
		try {
			objmList = new ArrayList<BillDetailBean>();
			
			BillDetailBean objmBillDetailBean;
			String Query="SELECT "+
			" b.bill_lading_id, b.bill_lading_number, b.login_scac"+
			" FROM bill_header b" +
			" where bill_lading_number like ? and b.login_scac=?";
			stmt = con.prepareStatement(Query);
			stmt.setString(1, billLadingNumber+"%");
			stmt.setString(2, loginScac);
			rs= stmt.executeQuery();
			while(rs.next()){
				objmBillDetailBean =new BillDetailBean();
				objmBillDetailBean.setBillLadingId(rs.getInt(1));
				objmBillDetailBean.setBillLadingNumber(rs.getString(2));
				objmBillDetailBean.setLoginScac(rs.getString(3));
				objmList.add(objmBillDetailBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
	

	public String insert(BillDetailBean objmBillDetailBean) throws Exception{
		String result="Success";
		int autoIncrementId=0;
		try {
			stmt= con.prepareStatement("Insert into bill_header " +
					"(login_scac, bill_lading_number, bill_status, bill_type, hbl_scac," +
					" nvo_type, nvo_bl, scac_bill, master_bill, master_carrier_scac, voyage_number, voyage_id ," +
					" load_port, discharge_port, country_of_origin, place_of_receipt, " +
					" place_of_delivery, move_type, created_user, created_date,split_bill_number," +
					"shipment_type,transmission_type,carnet_number,carnet_country,shipment_sub_type, " +
					"estimated_value, estimated_quantity, unit_of_measure, estimated_weight, weight_qualifier)"+
					"  values (?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, now(),?,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
				
				stmt.setString(1,objmBillDetailBean.getLoginScac());
				stmt.setString(2, objmBillDetailBean.getBillLadingNumber());
				stmt.setString(3, objmBillDetailBean.getBillStatus());
				stmt.setString(4,objmBillDetailBean.getBillType());
				stmt.setString(5, objmBillDetailBean.getHblScac());
				stmt.setString(6, objmBillDetailBean.getNvoType());
				stmt.setString(7, objmBillDetailBean.getNvoBill());
				stmt.setString(8, objmBillDetailBean.getScacBill());
				stmt.setString(9,objmBillDetailBean.getMasterBill());
				stmt.setString(10, objmBillDetailBean.getCarrierScac());
				//here we  splite the vesselName bcoz it contain vessel + voyageNumber we take voyage no.only . 
				String VoyageNumber= (objmBillDetailBean.getVesselName()).substring((objmBillDetailBean.getVesselName()).indexOf("-")+1);
				stmt.setString(11, VoyageNumber);
				stmt.setInt(12, objmBillDetailBean.getVoyageId());
				stmt.setString(13, objmBillDetailBean.getLoadPort());
				stmt.setString(14, objmBillDetailBean.getDischargePort());
				stmt.setString(15, objmBillDetailBean.getOriginCountry());
				stmt.setString(16, objmBillDetailBean.getReceiptPlace());
				stmt.setString(17,objmBillDetailBean.getDeliveryPlace());
				stmt.setString(18, objmBillDetailBean.getMoveType());
				stmt.setString(19, objmBillDetailBean.getCreatedUser());
				stmt.setString(20, objmBillDetailBean.getSplitBillNumber());
				stmt.setString(21, objmBillDetailBean.getShipmentType());
				stmt.setString(22, objmBillDetailBean.getTransmissionType());
				stmt.setString(23, objmBillDetailBean.getCarnetNumber());
				stmt.setString(24, objmBillDetailBean.getCarnetCountry());
				
				stmt.setString(25,objmBillDetailBean.getShipmentSubType());
				stmt.setInt(26,objmBillDetailBean.getEstimatedValue());
				stmt.setInt(27,objmBillDetailBean.getEstimatedQuantity());
				stmt.setString(28,objmBillDetailBean.getUnitOfMeasure());
				stmt.setInt(29,objmBillDetailBean.getEstimatedWeight());
				stmt.setString(30,objmBillDetailBean.getWeightQualifier());
				
				if(stmt.executeUpdate()!=1){
					result="Error";
		}else{
				rs = stmt.getGeneratedKeys();
				rs.next();
				autoIncrementId = rs.getInt(1);
				
				//------------The Function Called For Common Queries ----------------------------
				boolean b=CommanQueries(autoIncrementId,objmBillDetailBean);
				if(!b)
					result="Error";
				else{
				
					//-------------------------------------------------------------------------------
					
					//following query till end of the list bill Status to insert notify party records in bill_detail_status  table
						

					stmt=con.prepareStatement("Insert into bill_detail_status (login_scac, bill_lading_id, is_ams_sent, is_isf_sent, is_readonly, " +
							"error_code, isf_error_code, error_description, isf_error_description, is_manifest_error, is_isf_error, is_ams_error, " +
							"updated_date)" +
							"values(?,?,?,?,?,?,?,?,?,?,?,?,now())");
								
								stmt.setString(1, objmBillDetailBean.getLoginScac());
								stmt.setInt(2,autoIncrementId);
								if(objmBillDetailBean.getIsAmendment())
									stmt.setBoolean(3,true);
								else
									stmt.setBoolean(3,false);
								stmt.setBoolean(4,objmBillDetailBean.getIsIsfSent());
								stmt.setBoolean(5,false);
								stmt.setString(6,"");
								stmt.setString(7,"");
								stmt.setString(8, objmBillDetailBean.getAmsErrorDescription());
								stmt.setString(9, objmBillDetailBean.getIsfErrorDescription());
								stmt.setBoolean(10,objmBillDetailBean.getIsValidAms());
								stmt.setBoolean(11,objmBillDetailBean.getIsValidIsf());
								stmt.setBoolean(12,false);
								if(stmt.executeUpdate()!=1){
									result="Error";
								}
				}if(result.equals("Success")){
					stmt= con.prepareStatement("select arrival_date from voyage_details a, location b " +
							"where a.login_scac=? and a.voyage_id=? and a.is_discharge_port=? " +
							"and a.location_id=b.location_id and b.location_code=? ");
					stmt.setString(1, objmBillDetailBean.getLoginScac());
					stmt.setInt(2, objmBillDetailBean.getVoyageId());
					stmt.setBoolean(3, true);
					stmt.setString(4, objmBillDetailBean.getDischargePort());
					
					rs=stmt.executeQuery();
					if(rs.next()){
						stmt= con.prepareStatement("replace into voyage_port_details " +
						"(login_scac, voyage_id, load_port, discharge_port, is_ams_sent, eta) values(?,?,?,?,?,?)");
						stmt.setString(1, objmBillDetailBean.getLoginScac());
						stmt.setInt(2, objmBillDetailBean.getVoyageId());
						stmt.setString(3, objmBillDetailBean.getLoadPort());
						stmt.setString(4, objmBillDetailBean.getDischargePort());
						stmt.setBoolean(5, isMISent(objmBillDetailBean.getVoyageId(), objmBillDetailBean.getLoadPort(), objmBillDetailBean.getDischargePort(), objmBillDetailBean.getLoginScac()));
						stmt.setString(6, rs.getString(1));
						stmt.executeUpdate();
					}
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result="Error";
		}catch (Exception e) {
			e.printStackTrace();
			result="Error";
		}		
		return result;
	}

// This Menthod is for the executing the comman Queries of (Insert And Delete Finction).	

	public Boolean CommanQueries (int autoIncrementId, BillDetailBean objmBillDetailBean){
		String[] objmtemp;
				String Address="",Address1="",Address2="";

		try{
			
			//Execute query to insert consignee_shipper_details table for Shipper info
			
			stmt= con.prepareStatement("Insert into consignee_shipper_details values " +
					" (?, ?, ?, ?, ?, ?, false,?)");
				stmt.setInt(1, autoIncrementId);
				
				stmt.setString(2, objmBillDetailBean.getShipperName());
					objmtemp = (objmBillDetailBean.getShipperAddress()).split("\r");
					if(objmtemp.length>0){
						Address=objmtemp[0];
						if(objmtemp.length>1)
							Address1=objmtemp[1];
						else
							Address1="";
						if(objmtemp.length>2)
							Address2=objmtemp[2];
						else
							Address2="";
					}else{
						Address="";
						Address1="";
						Address2="";
					}
				stmt.setString(3,"shipper");
				stmt.setString(4,Address);
				stmt.setString(5,Address1);
				stmt.setString(6,Address2);
				stmt.setInt(7, objmBillDetailBean.getShipperId());
				if (stmt.executeUpdate() != 1){
					return false;}
				
		//Execute query to insert consignee_shipper_details table for bookibg info
		
		stmt=con.prepareStatement("Insert into consignee_shipper_details " +
				" values (?, ?, ?, ? ,?, ? , ?,?)");
		
		stmt.setInt(1, autoIncrementId);
		stmt.setString(2,objmBillDetailBean.getBookingPartyName());
		
		objmtemp = (objmBillDetailBean.getBookingPartyAddress()).split("\r");
			if(objmtemp.length>0){
				Address=objmtemp[0];
				if(objmtemp.length>1)
					Address1=objmtemp[1];
				else
					Address1="";
				if(objmtemp.length>2)
					Address2=objmtemp[2];
				else
					Address2="";
			}else{
				Address="";
				Address1="";
				Address2="";
			}
			stmt.setString(3,"booking");
			stmt.setString(4,Address);
		stmt.setString(5,Address1);
		stmt.setString(6,Address2);
		stmt.setBoolean(7, objmBillDetailBean.getIsBookingParty());
		stmt.setInt(8, objmBillDetailBean.getBookingPartyId());
		if (stmt.executeUpdate() != 1){
			return false;}
		
		//Execute query to insert consignee_shipper_details table for seller Info
		
		stmt=con.prepareStatement("Insert into consignee_shipper_details " +
				" values (?, ?, ?, ?, ?, ? , ?,?)");
		
		stmt.setInt(1,autoIncrementId);
		stmt.setString(2,objmBillDetailBean.getSellerName());
		objmtemp = (objmBillDetailBean.getSellerAddress()).split("\r");
		if(objmtemp.length>0){
			Address=objmtemp[0];
			if(objmtemp.length>1)
				Address1=objmtemp[1];
			else
				Address1="";
			if(objmtemp.length>2)
				Address2=objmtemp[2];
			else
				Address2="";
			}else{
				Address="";
				Address1="";
				Address2="";
			}
		stmt.setString(3,"seller");
		stmt.setString(4,Address);
		stmt.setString(5,Address1);
		stmt.setString(6,Address2);
		stmt.setBoolean(7, objmBillDetailBean.getIsSeller());
		stmt.setInt(8, objmBillDetailBean.getSellerId());
		
		if (stmt.executeUpdate() != 1){
			return false;}
			
		//Execute query to insert consignee_shipper_details table for stuffer 
		
		stmt=con.prepareStatement("Insert into consignee_shipper_details " +
				" values (?, ?, ?, ?, ?, ?, ?,?)");
		
			stmt.setInt(1,autoIncrementId);
			stmt.setString(2, objmBillDetailBean.getStufferName());
			objmtemp = (objmBillDetailBean.getStufferAddress()).split("\r");
			if(objmtemp.length>0){
				Address=objmtemp[0];
				if(objmtemp.length>1)
					Address1=objmtemp[1];
				else
					Address1="";
				if(objmtemp.length>2)
					Address2=objmtemp[2];
				else
					Address2="";
				}else{
					Address="";
					Address1="";
					Address2="";
				}

				stmt.setString(3, "stuffer");
				stmt.setString(4,Address);
			stmt.setString(5,Address1);
			stmt.setString(6,Address2);
			stmt.setBoolean(7, objmBillDetailBean.getIsStuffer());
			stmt.setInt(8, objmBillDetailBean.getStufferId());
			if (stmt.executeUpdate() != 1){
				return false;}
		//Insert the recipient information in the consignee_shipper_details table for Consignee
		
		stmt=con.prepareStatement("Insert into consignee_shipper_details " +
				" values (?, ?, ?, ?, ?, ?, false,?)");
			
			stmt.setInt(1,autoIncrementId);
			stmt.setString(2, objmBillDetailBean.getConsigneeName());
			objmtemp = (objmBillDetailBean.getConsigneeAddress()).split("\r");
			if(objmtemp.length>0){
				Address=objmtemp[0];
				if(objmtemp.length>1)
					Address1=objmtemp[1];
				else
					Address1="";
				if(objmtemp.length>2)
					Address2=objmtemp[2];
				else
					Address2="";
			}else{
				Address="";
				Address1="";
				Address2="";
			}
				stmt.setString(3,"consignee");
				stmt.setString(4,Address);
			stmt.setString(5,Address1);
			stmt.setString(6,Address2);
			stmt.setInt(7, objmBillDetailBean.getConsigneeId());
			if (stmt.executeUpdate() != 1){
				return false;}	

		//Insert the recipient information in the consignee_shipper_details table for notify
							
		stmt=con.prepareStatement("Insert into consignee_shipper_details " +
				" values (?, ?, ?, ?, ?, ?, ?,?)");
			
			stmt.setInt(1,autoIncrementId);
			stmt.setString(2, objmBillDetailBean.getNotifyName());
			objmtemp = (objmBillDetailBean.getNotifyAddress()).split("\r");
				if(objmtemp.length>0){
					Address=objmtemp[0];
					if(objmtemp.length>1)
						Address1=objmtemp[1];
					else
						Address1="";
					if(objmtemp.length>2)
						Address2=objmtemp[2];
					else
						Address2="";
				}else{
					Address="";
					Address1="";
					Address2="";
				}
				stmt.setString(3, "notify");
				stmt.setString(4,Address);
			stmt.setString(5,Address1);
			stmt.setString(6,Address2);
			stmt.setBoolean(7, objmBillDetailBean.getIsNotify());
			stmt.setInt(8, objmBillDetailBean.getNotifyId());
			if (stmt.executeUpdate() != 1){
				return false;}
		
		stmt=con.prepareStatement("Insert into consignee_shipper_details " +
				" values (?, ?, ?, ?, ?, ?, ?,?)");
		
			stmt.setInt(1,autoIncrementId);
			stmt.setString(2, objmBillDetailBean.getConsolidatorName());
			objmtemp = (objmBillDetailBean.getConsolidatorAddress()).split("\r");
				if(objmtemp.length>0){
					Address=objmtemp[0];
					if(objmtemp.length>1)
						Address1=objmtemp[1];
					else
						Address1="";
					if(objmtemp.length>2)
						Address2=objmtemp[2];
					else
						Address2="";
				}else{
					Address="";
					Address1="";
					Address2="";
				}
				stmt.setString(3, "consolidator");
				stmt.setString(4,Address);
			stmt.setString(5,Address1);
			stmt.setString(6,Address2);
			stmt.setBoolean(7, objmBillDetailBean.getIsConsolidator());
			stmt.setInt(8, objmBillDetailBean.getConsolidatorId());
			if (stmt.executeUpdate() != 1){
				return false;}

		//Insert the recipient information in the consignee_shipper_details table for Importer
			
		stmt=con.prepareStatement("Insert into consignee_shipper_details " +
				" values (?, ?, ?, ? ,?, ?, ?,?)");
			
			stmt.setInt(1,autoIncrementId);
			stmt.setString(2, objmBillDetailBean.getImporterName());
			objmtemp = (objmBillDetailBean.getImporterAddress()).split("\r");

				if(objmtemp.length>0){
					Address=objmtemp[0];
					if(objmtemp.length>1)
						Address1=objmtemp[1];
					else
						Address1="";
					if(objmtemp.length>2)
						Address2=objmtemp[2];
					else
						Address2="";
				}else{
					Address="";
					Address1="";
					Address2="";
				}
				stmt.setString(3, "importer");
				stmt.setString(4,Address);
			stmt.setString(5,Address1);
			stmt.setString(6,Address2);
			stmt.setBoolean(7, objmBillDetailBean.getIsImporter());
			stmt.setInt(8, objmBillDetailBean.getImporterId());
			if (stmt.executeUpdate() != 1){
				return false;}
		
		//Insert the recipient information in the consignee_shipper_details table for Bayer
		
		stmt=con.prepareStatement("Insert into consignee_shipper_details " +
				" values (?,?,?,?,?,?, ?,?)");
			
			stmt.setInt(1,autoIncrementId);
			stmt.setString(2, objmBillDetailBean.getBuyerName());
			objmtemp = (objmBillDetailBean.getBuyerAddress()).split("\r");

				if(objmtemp.length>0){
					Address=objmtemp[0];
					if(objmtemp.length>1)
						Address1=objmtemp[1];
					else
						Address1="";
					if(objmtemp.length>2)
						Address2=objmtemp[2];
					else
						Address2="";
				}else{
					Address="";
					Address1="";
					Address2="";
				}
				stmt.setString(3, "buyer");
				stmt.setString(4,Address);
			stmt.setString(5,Address1);
			stmt.setString(6,Address2);
			stmt.setBoolean(7, objmBillDetailBean.getIsBuyer());
			stmt.setInt(8, objmBillDetailBean.getBuyerId());
			if (stmt.executeUpdate() != 1){
				return false;}

		//Insert the recipient information in the consignee_shipper_details table for Ship To Address
		
		stmt=con.prepareStatement("Insert into consignee_shipper_details " +
				" values (?, ?, ?, ?, ?, ?, ?,?)");
			
			stmt.setInt(1,autoIncrementId);
			stmt.setString(2, objmBillDetailBean.getShipToName());
			objmtemp = (objmBillDetailBean.getShipToAddress()).split("\r");

				if(objmtemp.length>0){
					Address=objmtemp[0];
					if(objmtemp.length>1)
						Address1=objmtemp[1];
					else
						Address1="";
					if(objmtemp.length>2)
						Address2=objmtemp[2];
					else
						Address2="";
				}else{
					Address="";
					Address1="";
					Address2="";
				}
				stmt.setString(3, "shipTo");
				stmt.setString(4,Address);
			stmt.setString(5,Address1);
			stmt.setString(6,Address2);
			stmt.setBoolean(7, objmBillDetailBean.getIsShipTo());
			stmt.setInt(8, objmBillDetailBean.getShipToId());
			if (stmt.executeUpdate() != 1){
				return false;
			}
	
			
			if(objmBillDetailBean.getStuffersId()!=null){
				stmt=con.prepareStatement("Insert into consignee_shipper_details " +
				" values (?, ?, ?, ?, ?, ?, ?,?)");
				String[] stuffersName=objmBillDetailBean.getStuffersName();
				String[] stuffersAddress=objmBillDetailBean.getStuffersAddress();
				int[] stuffersId=objmBillDetailBean.getStuffersId();
				
				for(int i=0;i<objmBillDetailBean.getStuffersName().length;i++){
					String address[]=stuffersAddress[i].split("\r\n");
					stmt.setInt(1, objmBillDetailBean.getBillLadingId());
					stmt.setString(2, stuffersName[i]);
					stmt.setString(3, "stuffers");
					if(address.length>=1){
						stmt.setString(4, address[0]);
					}else{
						stmt.setString(4, "");
					}if(address.length>=2){
						stmt.setString(5, address[1]);
					}else{
						stmt.setString(5, "");
					}if(address.length>=3){
						stmt.setString(6, address[2]);
					}else{
						stmt.setString(6, "");
					}
					stmt.setBoolean(7, false);
					stmt.setInt(8, stuffersId[i]);
					if(stmt.executeUpdate()!=1){
						return false;
					}
				}
			}
			
			if(objmBillDetailBean.getConsolidatorsName()!=null){
				stmt=con.prepareStatement("Insert into consignee_shipper_details " +
				" values (?, ?, ?, ?, ?, ?, ?,?)");
				String[] consolidatorsName=objmBillDetailBean.getConsolidatorsName();
				String[] consolidatorsAddress=objmBillDetailBean.getConsolidatorsAddress();
				int[] consolidatorsId=objmBillDetailBean.getConsolidatorsId();
				
				for(int i=0;i<objmBillDetailBean.getConsolidatorsName().length;i++){
					String address[]=consolidatorsAddress[i].split("\r\n");
					stmt.setInt(1, objmBillDetailBean.getBillLadingId());
					stmt.setString(2, consolidatorsName[i]);
					stmt.setString(3, "consolidators");
					if(address.length>=1){
						stmt.setString(4, address[0]);
					}else{
						stmt.setString(4, "");
					}if(address.length>=2){
						stmt.setString(5, address[1]);
					}else{
						stmt.setString(5, "");
					}if(address.length>=3){
						stmt.setString(6, address[2]);
					}else{
						stmt.setString(6, "");
					}
					stmt.setBoolean(7, false);
					stmt.setInt(8, consolidatorsId[i]);
					if(stmt.executeUpdate()!=1){
						return false;
					}
				}
				
			}
		//following query till end of the list NotifyBean to insert notify party records in notify_party_details table
		
		stmt=con.prepareStatement("Insert into notify_party_details values (?,?)");
			
			stmt.setInt(1,autoIncrementId);
			if(objmBillDetailBean.getObjmNotifyBean()!=null)
				for(int i=0;i<objmBillDetailBean.getObjmNotifyBean().size();i++){
					stmt.setString(2, objmBillDetailBean.getObjmNotifyBean().get(i).getScacCode());
					if (stmt.executeUpdate() != 1){
						return false;}
				}


		//following query till end of the list EquipmentBean to insert notify party records in equipment table
		
		stmt=con.prepareStatement("Insert ignore into equipment values (?,?,?)",Statement.RETURN_GENERATED_KEYS);
		stmt1=con.prepareStatement("Insert into seal values (?, ?, ?)");		
			stmt.setInt(1,autoIncrementId);
			if(objmBillDetailBean.getObjmEquipmentBean()!=null)
				for(int i=0;i<objmBillDetailBean.getObjmEquipmentBean().size();i++){
					stmt.setString(2, objmBillDetailBean.getObjmEquipmentBean().get(i).getEquipment());
					stmt.setString(3, objmBillDetailBean.getObjmEquipmentBean().get(i).getSizeType());
					
					objmtemp = objmBillDetailBean.getObjmEquipmentBean().get(i).getSeal();
					if (stmt.executeUpdate() != 1){
						return false;
					}
				//following query till end of the list SealBean to insert notify party records in seal table
					if(objmtemp!=null)
						for(int x=0;x<objmtemp.length;x++){
							stmt1.setInt(1,autoIncrementId);
							stmt1.setString(2, objmBillDetailBean.getObjmEquipmentBean().get(i).getEquipment());
							stmt1.setString(3, objmtemp[x]);
							if (stmt1.executeUpdate() != 1){
								return false;
							}
						}
				}

		//following query till end of the list PackagesBean to insert notify party records in packages  table
		
		stmt=con.prepareStatement("Insert into packages " +
				" (bill_lading_id, package_id,equipment_number, marks, pieces, packages)" +
				" values (?, ?, ?, ?, ?, ?)");
		stmt1=con.prepareStatement("Insert into packages_details " +
		" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
			stmt.setInt(1,autoIncrementId);
			if(objmBillDetailBean.getObjmPackageBean()!=null)
				for(int i=0;i<objmBillDetailBean.getObjmPackageBean().size();i++){
					stmt.setInt(2, i);
					stmt.setString(3, objmBillDetailBean.getObjmPackageBean().get(i).getPackageEquipment());
					stmt.setString(4, objmBillDetailBean.getObjmPackageBean().get(i).getMarks());
					stmt.setString(5, objmBillDetailBean.getObjmPackageBean().get(i).getPieces());
					stmt.setString(6, objmBillDetailBean.getObjmPackageBean().get(i).getPackages());
					
					if (stmt.executeUpdate() != 1){{
						return false;}
					}
					//following query till end of the list PackagesBean to insert notify party records in packages  table
			
					stmt1.setInt(1,autoIncrementId);			
					stmt1.setInt(2, i);
					stmt1.setString(3, objmBillDetailBean.getObjmPackageBean().get(i).getPackageEquipment());
					stmt1.setDouble(4, objmBillDetailBean.getObjmPackageBean().get(i).getWti());
					stmt1.setDouble(5, objmBillDetailBean.getObjmPackageBean().get(i).getWtm());
					stmt1.setDouble(6, objmBillDetailBean.getObjmPackageBean().get(i).getMsi());
					stmt1.setDouble(7, objmBillDetailBean.getObjmPackageBean().get(i).getMsm());
					stmt1.setDouble(8, objmBillDetailBean.getObjmPackageBean().get(i).getLength());
					stmt1.setDouble(9, objmBillDetailBean.getObjmPackageBean().get(i).getWidth());
					stmt1.setDouble(10, objmBillDetailBean.getObjmPackageBean().get(i).getHeight());
					stmt1.setDouble(11, objmBillDetailBean.getObjmPackageBean().get(i).getSet());
					stmt1.setDouble(12, objmBillDetailBean.getObjmPackageBean().get(i).getMin());
					stmt1.setDouble(13,objmBillDetailBean.getObjmPackageBean().get(i).getMax());
					stmt1.setDouble(14,objmBillDetailBean.getObjmPackageBean().get(i).getVents());
					stmt1.setDouble(15,objmBillDetailBean.getObjmPackageBean().get(i).getDrainage());
					stmt1.setString(16, objmBillDetailBean.getObjmPackageBean().get(i).getWtiUnit());
					stmt1.setString(17,objmBillDetailBean.getObjmPackageBean().get(i).getWtmUnit());
					stmt1.setString(18,objmBillDetailBean.getObjmPackageBean().get(i).getMsiUnit());
					stmt1.setString(19, objmBillDetailBean.getObjmPackageBean().get(i).getMsmUnit());
					stmt1.setString(20, objmBillDetailBean.getObjmPackageBean().get(i).getLengthUnit());
					stmt1.setString(21, objmBillDetailBean.getObjmPackageBean().get(i).getWidthUnit());
					stmt1.setString(22,objmBillDetailBean.getObjmPackageBean().get(i).getHeightUnit());
					stmt1.setString(23,objmBillDetailBean.getObjmPackageBean().get(i).getSetUnit());
					stmt1.setString(24, objmBillDetailBean.getObjmPackageBean().get(i).getMinUnit());
					stmt1.setString(25, objmBillDetailBean.getObjmPackageBean().get(i).getMaxUnit());
					stmt1.setString(26, objmBillDetailBean.getObjmPackageBean().get(i).getVentsUnit());
					stmt1.setString(27, objmBillDetailBean.getObjmPackageBean().get(i).getDrainageUnit());
					
					if (stmt1.executeUpdate() != 1){
						return false;}
				}

		//following query till end of the list CargoBean to insert notify party records in cargo  table
		
		stmt=con.prepareStatement("Insert into cargo " +
				" (bill_lading_id, cargo_id, equipment_number, description, harmonize_code, " +
				" hazard_code, manufacturer, country,customer_id) values " +
				" (?,?,?,?,?,?,?,?,?)");
			if(objmBillDetailBean.getObjmCargoBean()!=null)
				for(int i=0;i<objmBillDetailBean.getObjmCargoBean().size();i++){
					stmt.setInt(1,autoIncrementId);
					stmt.setInt(2,i);
					stmt.setString(3, objmBillDetailBean.getObjmCargoBean().get(i).getCargoEquipment());
					stmt.setString(4, objmBillDetailBean.getObjmCargoBean().get(i).getGoodsDescription());
					stmt.setString(5, objmBillDetailBean.getObjmCargoBean().get(i).getHarmonizedCode());
					stmt.setString(6, objmBillDetailBean.getObjmCargoBean().get(i).getHazardCode());
					stmt.setString(7, objmBillDetailBean.getObjmCargoBean().get(i).getManufacturer());
					stmt.setString(8, objmBillDetailBean.getObjmCargoBean().get(i).getCargoCountry());
					stmt.setInt(9, objmBillDetailBean.getObjmCargoBean().get(i).getManufacturerId());
					if (stmt.executeUpdate() != 1){
						return false;}
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}catch(Exception e ){
				e.printStackTrace();
				return false;
			}
			return true;
		}
	
/**
 * @author Rohit
 * @method update
 * @param BillDetailBean
 * @return result
 * @date 14 april 2011
 * @description This method is for updateing the bill details.
 */
	public String update(BillDetailBean objmBillDetailBean){
		String result="Success";
		try{
			checkForAmendment(objmBillDetailBean);
			stmt=con.prepareStatement("Update bill_header set bill_status=?, bill_type=?," +
					" hbl_scac=?, nvo_type=?, nvo_bl=?, scac_bill=?, master_bill=?, " +
					" master_carrier_scac=?,voyage_number=?,voyage_id=?, load_port=?, discharge_port=?," +
					" country_of_origin= ?, place_of_receipt= ?, place_of_delivery= ?," +
					" move_type= ?, split_bill_number=?, shipment_type=?,transmission_type=?, " +
					" carnet_number=?, carnet_country=?, shipment_sub_type=?, estimated_value=?, estimated_quantity=?, " +
					"unit_of_measure=?, estimated_weight=?, weight_qualifier=?,update_date = now() " +
					"where bill_lading_id=? and login_scac=?");
			stmt.setString(1, objmBillDetailBean.getBillStatus());
			stmt.setString(2,objmBillDetailBean.getBillType());
			stmt.setString(3, objmBillDetailBean.getHblScac());
			stmt.setString(4, objmBillDetailBean.getNvoType());
			stmt.setString(5, objmBillDetailBean.getNvoBill());
			stmt.setString(6, objmBillDetailBean.getScacBill());
			stmt.setString(7, objmBillDetailBean.getMasterBill());
			stmt.setString(8, objmBillDetailBean.getCarrierScac());
			//here we  splite the vesselName bcoz it contain vessel + voyageNumber we take voyage no.only . 
			String VoyageNumber= (objmBillDetailBean.getVesselName()).substring((objmBillDetailBean.getVesselName()).indexOf("-")+1);
			stmt.setString(9, VoyageNumber);
			stmt.setInt(10, objmBillDetailBean.getVoyageId());
			stmt.setString(11,objmBillDetailBean.getLoadPort());
			stmt.setString(12, objmBillDetailBean.getDischargePort());
			stmt.setString(13,objmBillDetailBean.getOriginCountry());
			stmt.setString(14, objmBillDetailBean.getReceiptPlace());
			stmt.setString(15,objmBillDetailBean.getDeliveryPlace());
			stmt.setString(16, objmBillDetailBean.getMoveType());
			stmt.setString(17, objmBillDetailBean.getSplitBillNumber());
			stmt.setString(18, objmBillDetailBean.getShipmentType());
			stmt.setString(19, objmBillDetailBean.getTransmissionType());
			stmt.setString(20, objmBillDetailBean.getCarnetNumber());
			stmt.setString(21, objmBillDetailBean.getCarnetCountry());
			
			stmt.setString(22,objmBillDetailBean.getShipmentSubType());
			stmt.setInt(23,objmBillDetailBean.getEstimatedValue());
			stmt.setInt(24,objmBillDetailBean.getEstimatedQuantity());
			stmt.setString(25,objmBillDetailBean.getUnitOfMeasure());
			stmt.setInt(26,objmBillDetailBean.getEstimatedWeight());
			stmt.setString(27,objmBillDetailBean.getWeightQualifier());
			
			stmt.setInt(28, objmBillDetailBean.getBillLadingId());
			stmt.setString(29, objmBillDetailBean.getLoginScac());
			
			if(stmt.executeUpdate()!=1)
				result="Error";
				
			//Deleting the records from consignee_shipper_table
				
			stmt=con.prepareStatement("Delete from consignee_shipper_details where bill_lading_id=?");
				stmt.setInt(1, objmBillDetailBean.getBillLadingId());
				stmt.executeUpdate();
				
			stmt=con.prepareStatement("Delete from notify_party_details where bill_lading_id=?");
				stmt.setInt(1, objmBillDetailBean.getBillLadingId());
				stmt.executeUpdate();
				
			stmt=con.prepareStatement("Delete from equipment where bill_lading_id=?");
				stmt.setInt(1, objmBillDetailBean.getBillLadingId());
				stmt.executeUpdate();
				
			stmt=con.prepareStatement("Delete from seal where bill_lading_id=?");
				stmt.setInt(1, objmBillDetailBean.getBillLadingId());
				
				stmt.executeUpdate();
				
			stmt=con.prepareStatement("Delete from packages where bill_lading_id=?");
				stmt.setInt(1, objmBillDetailBean.getBillLadingId());
				stmt.executeUpdate();
				
			stmt=con.prepareStatement("Delete from packages_details where bill_lading_id=?");
				stmt.setInt(1, objmBillDetailBean.getBillLadingId());
				stmt.executeUpdate();
				
			stmt=con.prepareStatement("Delete from cargo where bill_lading_id=?");
				stmt.setInt(1, objmBillDetailBean.getBillLadingId());
				stmt.executeUpdate();
				//------------The Function Called For Common Queries ----------------------------
				
				boolean b=CommanQueries(objmBillDetailBean.getBillLadingId(),objmBillDetailBean);
			
			//boolean b=CommanQueries(autoIncrementId,objmBillDetailBean);
				if(!b)
					result="Error";
				
			//-------------------------------------------------------------------------------
			/*stmt= con.prepareStatement("Update bill_detail_status " +
					" set is_readonly=?, error_description=?, isf_error_description=?, is_manifest_error=?, is_isf_error=?, is_ams_sent=? " +
					"where bill_lading_id=? and login_scac=?");
			stmt.setBoolean(1,false);
			stmt.setString(2,objmBillDetailBean.getAmsErrorDescription());
			stmt.setString(3,objmBillDetailBean.getIsfErrorDescription());
			stmt.setBoolean(4,objmBillDetailBean.getIsValidAms());
			stmt.setBoolean(5,objmBillDetailBean.getIsValidIsf());
			stmt.setBoolean(6,objmBillDetailBean.getIsAmsSent());			
			stmt.setInt(7, objmBillDetailBean.getBillLadingId());
			stmt.setString(8,objmBillDetailBean.getLoginScac());
			stmt.executeUpdate();*/
			if(result.equals("Success")){
			stmt= con.prepareStatement("select arrival_date from voyage_details a, location b " +
					"where a.login_scac=? and a.voyage_id=? and a.is_discharge_port=? " +
					"and a.location_id=b.location_id and b.location_code=? ");
			stmt.setString(1, objmBillDetailBean.getLoginScac());
			stmt.setInt(2, objmBillDetailBean.getVoyageId());
			stmt.setBoolean(3, true);
			stmt.setString(4, objmBillDetailBean.getDischargePort());
			
			rs=stmt.executeQuery();
			if(rs.next()){
				stmt= con.prepareStatement("replace into voyage_port_details " +
				"(login_scac, voyage_id, load_port, discharge_port, is_ams_sent, eta) values(?,?,?,?,?,?)");
				stmt.setString(1, objmBillDetailBean.getLoginScac());
				stmt.setInt(2, objmBillDetailBean.getVoyageId());
				stmt.setString(3, objmBillDetailBean.getLoadPort());
				stmt.setString(4, objmBillDetailBean.getDischargePort());
				stmt.setBoolean(5, isMISent(objmBillDetailBean.getVoyageId(), objmBillDetailBean.getLoadPort(), objmBillDetailBean.getDischargePort(), objmBillDetailBean.getLoginScac()));
				stmt.setString(6, rs.getString(1));
				stmt.executeUpdate();
			}
				}
		} catch (Exception e) {
			e.printStackTrace();
			result="Error";
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
 * @method Delete
 * @param BillDetailBean
 * @return result
 * @date 14 april 2011
 * @description This method is for Delete the bill details.
 */
	public String delete(BillDetailBean objmBillDetailBean){
		
		String result="Success";
		try{
					stmt=con.prepareStatement("Delete from bill_header where bill_lading_number=? and login_scac=?");
					stmt.setString(1,objmBillDetailBean.getBillLadingNumber());
					stmt.setString(2, objmBillDetailBean.getLoginScac());
					stmt.executeUpdate();
					
					/*stmt=con.prepareStatement("Delete from consignee_shipper_details where bill_lading_number=?");
					stmt.setInt(1,objmBillDetailBean.getBillLadingId());
					stmt.executeUpdate();
					
					stmt=con.prepareStatement("Delete from notify_party_details where bill_lading_number=?");
					stmt.setInt(1,objmBillDetailBean.getBillLadingId());
					stmt.executeUpdate();
					
					stmt=con.prepareStatement("Delete from equipment where bill_lading_number=?");
					stmt.setInt(1,objmBillDetailBean.getBillLadingId());
					stmt.executeUpdate();
					
					stmt=con.prepareStatement("Delete from seal where bill_lading_number=?");
					stmt.setInt(1,objmBillDetailBean.getBillLadingId());
					stmt.executeUpdate();
					
					stmt=con.prepareStatement("Delete from packages where bill_lading_id=?");
					stmt.setInt(1,objmBillDetailBean.getBillLadingId());
					stmt.executeUpdate();
					
					stmt=con.prepareStatement("Delete from packages_details where bill_lading_id=?");
					stmt.setInt(1,objmBillDetailBean.getBillLadingId());
					stmt.executeUpdate();
					
					stmt=con.prepareStatement("Delete from cargo where bill_lading_id=?");
					stmt.setInt(1,objmBillDetailBean.getBillLadingId());
					stmt.executeUpdate();
					
					stmt=con.prepareStatement("Delete from bill_detail_status where bill_lading_id=?");
					stmt.setInt(1,objmBillDetailBean.getBillLadingId());
					stmt.executeUpdate();*/
			/*}else{
				checkForAmendment(objmBillDetailBean);
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
			result = "Fail";
		}finally{
			try {
				if (!result.equals("Success")) {
					con.rollback();
					result="Error";
				}else{ 
					con.commit();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	public Boolean isMISent(int voyageId,String loadPortCode,String dischargePortCode,String loginScac) throws Exception{
		Boolean result=false;
	
		MIstmt=con.prepareStatement("Select * from voyage_port_details where login_scac=? and voyage_id=? and load_port=? and discharge_port=? and is_ams_sent=true");
		MIstmt.setString(1,loginScac);
		MIstmt.setInt(2,voyageId);
		MIstmt.setString(3,loadPortCode);
		MIstmt.setString(4,dischargePortCode);
		MIrs= MIstmt.executeQuery();
		if (MIrs.next())		
			result=true;
		else
			result=false;
	
		return result;
	}
	public Boolean isMISentForBillHeader(int voyageId,String loadPortCode,String dischargePortCode,String loginScac) throws Exception{
		Boolean result=false;
	
		MIstmt=con.prepareStatement("Select a.* from voyage_port_details a,ams_sent b where a.login_scac=? and a.voyage_id=? and a.load_port=? " +
				"and a.discharge_port=? and a.is_ams_sent=true and " +
				"a.voyage_id=b.voyage_id and a.load_port=b.load_port and a.discharge_port=b.discharge_port and b.control_identifier='MI'");
		MIstmt.setString(1,loginScac);
		MIstmt.setInt(2,voyageId);
		MIstmt.setString(3,loadPortCode);
		MIstmt.setString(4,dischargePortCode);
		MIrs= MIstmt.executeQuery();
		if (MIrs.next())		
			result=true;
		else
			result=false;
	
		return result;
	}
	
	public BillDetailBean getData(int BillLadingId,String loginScac){
		BillDetailBean objmBillDetailBean=new BillDetailBean();
		try {
			stmt=con.prepareStatement("Select bill_lading_id, b.login_scac, bill_lading_number," +
					" bill_status, bill_type, hbl_scac, nvo_type, nvo_bl, scac_bill, master_bill," +
					" master_carrier_scac, b.voyage_number, b.voyage_id, load_port, discharge_port, country_of_origin," +
					" place_of_receipt, place_of_delivery, move_type, b.created_user,"+
					" b.created_date,vs.vessel_name,ifnull(b.split_bill_number,''),shipment_type," +
					" transmission_type, carnet_number,carnet_country,shipment_sub_type, estimated_value, " +
					"estimated_quantity, unit_of_measure, estimated_weight, weight_qualifier " +
					"from bill_header b,voyage v,vessel vs "+
					" where b.bill_lading_id=? and b.login_scac=? and v.voyage_id = b.voyage_id  and v.voyage_number = b.voyage_number "+
					" and v.vessel_id= vs.vessel_id");
			stmt.setInt(1,BillLadingId);
			stmt.setString(2,loginScac);
			rs= stmt.executeQuery();
			if (rs.next()){
				objmBillDetailBean.setBillLadingId(rs.getInt(1));
				objmBillDetailBean.setLoginScac(rs.getString(2));
				objmBillDetailBean.setBillLadingNumber(rs.getString(3));
				objmBillDetailBean.setBillStatus(rs.getString(4));
				objmBillDetailBean.setBillType(rs.getString(5));
				objmBillDetailBean.setHblScac(rs.getString(6));
				objmBillDetailBean.setNvoType(rs.getString(7));
				objmBillDetailBean.setNvoBill(rs.getString(8));
				objmBillDetailBean.setScacBill(rs.getString(9));
				objmBillDetailBean.setMasterBill(rs.getString(10));
				objmBillDetailBean.setCarrierScac(rs.getString(11));
				objmBillDetailBean.setVoyageNumber(rs.getString(12));
				objmBillDetailBean.setVoyageId(rs.getInt(13));
				objmBillDetailBean.setLoadPort(rs.getString(14));
				objmBillDetailBean.setDischargePort(rs.getString(15));
				objmBillDetailBean.setOriginCountry(rs.getString(16));
				objmBillDetailBean.setReceiptPlace(rs.getString(17));
				objmBillDetailBean.setDeliveryPlace(rs.getString(18));
				objmBillDetailBean.setMoveType(rs.getString(19));
				objmBillDetailBean.setCreatedUser(rs.getString(20));
				objmBillDetailBean.setCreatedDate(rs.getString(21));
				objmBillDetailBean.setVesselName(rs.getString(22));
				if(rs.getString(23).equals(""))
					objmBillDetailBean.setIsSplitBill("Off");
				else
					objmBillDetailBean.setIsSplitBill("On");
				objmBillDetailBean.setSplitBillNumber(rs.getString(23));
				objmBillDetailBean.setShipmentType(rs.getString(24));
				objmBillDetailBean.setTransmissionType(rs.getString(25));
				objmBillDetailBean.setCarnetNumber(rs.getString(26));
				objmBillDetailBean.setCarnetCountry(rs.getString(27));
				objmBillDetailBean.setShipmentSubType(rs.getString(28));
				objmBillDetailBean.setEstimatedValue(rs.getInt(29));
				objmBillDetailBean.setEstimatedQuantity(rs.getInt(30));
				objmBillDetailBean.setUnitOfMeasure(rs.getString(31));
				objmBillDetailBean.setEstimatedWeight(rs.getInt(32));
				objmBillDetailBean.setWeightQualifier(rs.getString(33));
			}
			
			stmt=con.prepareStatement("Select bill_lading_id, " +
					" customer_name, customer_as, address1, address2, address3, is_same, ifnull(customer_id ,0) " +
					" from consignee_shipper_details where bill_lading_id=?");
			stmt.setInt(1, BillLadingId);
			rs=stmt.executeQuery();
			
			while(rs.next()){
				if(rs.getString(3).equals("booking")){
					objmBillDetailBean.setBookingPartyName(rs.getString(2));
					objmBillDetailBean.setBookingPartyAddress(rs.getString(4)+"\r"+rs.getString(5)+"\r"+rs.getString(6));
					objmBillDetailBean.setIsBookingParty(rs.getBoolean(7));
					objmBillDetailBean.setBookingPartyId(rs.getInt(8));
				}else if(rs.getString(3).equals("buyer")){
					objmBillDetailBean.setBuyerName(rs.getString(2));
					objmBillDetailBean.setBuyerAddress(rs.getString(4)+"\r"+rs.getString(5)+"\r"+rs.getString(6));
					objmBillDetailBean.setIsBuyer(rs.getBoolean(7));
					objmBillDetailBean.setBuyerId(rs.getInt(8));
				}else if(rs.getString(3).equals("consolidator")){
					objmBillDetailBean.setConsolidatorName(rs.getString(2));
					objmBillDetailBean.setConsolidatorAddress(rs.getString(4)+"\r"+rs.getString(5)+"\r"+rs.getString(6));
					objmBillDetailBean.setIsConsolidator(rs.getBoolean(7));
					objmBillDetailBean.setConsolidatorId(rs.getInt(8));
				}else if(rs.getString(3).equals("importer")){
					objmBillDetailBean.setImporterName(rs.getString(2));
					objmBillDetailBean.setImporterAddress(rs.getString(4)+"\r"+rs.getString(5)+"\r"+rs.getString(6));
					objmBillDetailBean.setIsImporter(rs.getBoolean(7));
					objmBillDetailBean.setImporterId(rs.getInt(8));
				}else if(rs.getString(3).equals("notify")){
					objmBillDetailBean.setNotifyName(rs.getString(2));
					objmBillDetailBean.setNotifyAddress(rs.getString(4)+"\r"+rs.getString(5)+"\r"+rs.getString(6));
					objmBillDetailBean.setIsNotify(rs.getBoolean(7));
					objmBillDetailBean.setNotifyId(rs.getInt(8));
				}else if(rs.getString(3).equals("seller")){
					objmBillDetailBean.setSellerName(rs.getString(2));
					objmBillDetailBean.setSellerAddress(rs.getString(4)+"\r"+rs.getString(5)+"\r"+rs.getString(6));
					objmBillDetailBean.setIsSeller(rs.getBoolean(7));
					objmBillDetailBean.setSellerId(rs.getInt(8));
				}else if(rs.getString(3).equals("shipTo")){
					objmBillDetailBean.setShipToName(rs.getString(2));
					objmBillDetailBean.setShipToAddress(rs.getString(4)+"\r"+rs.getString(5)+"\r"+rs.getString(6));
					objmBillDetailBean.setIsShipTo(rs.getBoolean(7));
					objmBillDetailBean.setShipToId(rs.getInt(8));
				}else if(rs.getString(3).equals("stuffer")){
					objmBillDetailBean.setStufferName(rs.getString(2));
					objmBillDetailBean.setStufferAddress(rs.getString(4)+"\r"+rs.getString(5)+"\r"+rs.getString(6));
					objmBillDetailBean.setIsStuffer(rs.getBoolean(7));
					objmBillDetailBean.setStufferId(rs.getInt(8));
				}else if(rs.getString(3).equals("consignee")){
					objmBillDetailBean.setConsigneeName(rs.getString(2));
					objmBillDetailBean.setConsigneeAddress(rs.getString(4)+"\r"+rs.getString(5)+"\r"+rs.getString(6));
					objmBillDetailBean.setConsigneeId(rs.getInt(8));
				}else if(rs.getString(3).equals("shipper")){
					objmBillDetailBean.setShipperName(rs.getString(2));
					objmBillDetailBean.setShipperAddress(rs.getString(4)+"\r"+rs.getString(5)+"\r"+rs.getString(6));
					objmBillDetailBean.setShipperId(rs.getInt(8));
				}
			}
			
			stmt=con.prepareStatement("Select bill_lading_id, " +
					" customer_name, customer_as, address1, address2, address3, is_same,ifnull(customer_id ,0)" +
					" from consignee_shipper_details where bill_lading_id=? and customer_as='stuffers'");
			stmt.setInt(1, BillLadingId);
			rs=stmt.executeQuery();
			if(rs.next()){
				rs.last();
				int length=rs.getRow();
				rs.beforeFirst();
				
				int[] stuffersId=new int[length];
				String[] stuffersName=new String[length];
				String[] stuffersAddress=new String[length];
				
				int i=0;
				while(rs.next()){
					stuffersName[i]=rs.getString(2);
					stuffersAddress[i]=rs.getString(4)+"\r\n"+rs.getString(5)+"\r\n"+rs.getString(6);
					stuffersId[i]=rs.getInt(8);
					i++;
				}
				objmBillDetailBean.setStuffersName(stuffersName);
				objmBillDetailBean.setStuffersAddress(stuffersAddress);
				objmBillDetailBean.setStuffersId(stuffersId);
			}
			
			stmt=con.prepareStatement("Select bill_lading_id, " +
					" customer_name, customer_as, address1, address2, address3, is_same,ifnull(customer_id ,0)" +
					" from consignee_shipper_details where bill_lading_id=? and customer_as='consolidators'");
			stmt.setInt(1, BillLadingId);
			rs=stmt.executeQuery();
			if(rs.next()){
				rs.last();
				int length=rs.getRow();
				rs.beforeFirst();
				
				int[] consolidatorsId=new int[length];
				String[] consolidatorsName=new String[length];
				String[] consolidatorsAddress=new String[length];
				
				int i=0;
				while(rs.next()){
					consolidatorsName[i]=rs.getString(2);
					consolidatorsAddress[i]=rs.getString(4)+"\r\n"+rs.getString(5)+"\r\n"+rs.getString(6);
					consolidatorsId[i]=rs.getInt(8);
					i++;
				}
				objmBillDetailBean.setConsolidatorsName(consolidatorsName);
				objmBillDetailBean.setConsolidatorsAddress(consolidatorsAddress);
				objmBillDetailBean.setConsolidatorsId(consolidatorsId);
			}

			{
				stmt=con.prepareStatement("SELECT n.scac_code,s.name FROM notify_party_details n," +
						"scac s where n.bill_lading_id=? and n.scac_code=s.scac_code and s.login_scac=?");
				stmt.setInt(1, BillLadingId);
				stmt.setString(2, objmBillDetailBean.getLoginScac());
				rs=stmt.executeQuery();
				ArrayList<NotifyBean> objmList=new ArrayList<NotifyBean>();
				NotifyBean objmNotifyBean;
				while(rs.next()){
					objmNotifyBean=new NotifyBean();
					objmNotifyBean.setScacCode(rs.getString(1));
					objmNotifyBean.setCarrierName(rs.getString(2));
					objmList.add(objmNotifyBean);
				}
				objmBillDetailBean.setObjmNotifyBean(objmList);
			}
			
			{ 
				stmt=con.prepareStatement("SELECT equipment_number, size FROM equipment where bill_lading_id=? order by 1");
				stmt.setInt(1, BillLadingId);
				rs=stmt.executeQuery();
				ArrayList<EquipmentBean> objmList=new ArrayList<EquipmentBean>();
				EquipmentBean objmEquipmentBean;
				while(rs.next()){
					objmEquipmentBean=new EquipmentBean();
					objmEquipmentBean.setEquipment(rs.getString(1));
					objmEquipmentBean.setSizeType(rs.getString(2));
					stmt1=con.prepareStatement("SELECT seal_number FROM seal where bill_lading_id=? " +
							"and equipment_number=?");
					stmt1.setInt(1, BillLadingId);
					stmt1.setString(2, rs.getString(1));
					rs1=stmt1.executeQuery();
					String sealDetails="";
					String s[]=null;
					if(rs1.next()){
						rs1.last();
						s=new String[rs1.getRow()];
						int i=0;
						rs1.beforeFirst();
						while(rs1.next()){
							sealDetails=sealDetails+rs1.getString(1)+",";
							s[i]=rs1.getString(1);
							i++;
						}
					}
					objmEquipmentBean.setSealDetails(sealDetails);
					objmEquipmentBean.setSeal(s);
					objmList.add(objmEquipmentBean);
				}
				objmBillDetailBean.setObjmEquipmentBean(objmList);
			}
			
			{
				stmt=con.prepareStatement("SELECT package_id, equipment_number, marks, if(pieces='','0',pieces) as pieces, packages " +
						" FROM packages where bill_lading_id=? order by 2");
				stmt.setInt(1, BillLadingId);
				rs=stmt.executeQuery();
				ArrayList<PackageBean> objmList=new ArrayList<PackageBean>();
				PackageBean objmPackageBean;
				while(rs.next()){
					objmPackageBean=new PackageBean();
					objmPackageBean.setPackageEquipment(rs.getString(2));
					objmPackageBean.setMarks(rs.getString(3));
					objmPackageBean.setPieces(rs.getString(4));
					objmPackageBean.setPackages(rs.getString(5));
					stmt1=con.prepareStatement("SELECT wti, wtm, msi, msm, length, width, height, set_value, min_value, max_value, vents, " +
							"drainage, wti_unit, wtm_unit, msi_unit, msm_unit, length_unit, width_unit, height_unit, " +
							"set_unit, min_unit, max_unit, vents_unit, drainage_unit FROM packages_details " +
							"where bill_lading_id=? and package_id=?");
					stmt1.setInt(1, BillLadingId);
					stmt1.setInt(2, rs.getInt(1));
					rs1=stmt1.executeQuery();
					if(rs1.next()){
						objmPackageBean.setWti(rs1.getDouble(1));
						objmPackageBean.setWtm(rs1.getDouble(2));
						objmPackageBean.setMsi(rs1.getDouble(3));
						objmPackageBean.setMsm(rs1.getDouble(4));
						objmPackageBean.setLength(rs1.getDouble(5));
						objmPackageBean.setWidth(rs1.getDouble(6));
						objmPackageBean.setHeight(rs1.getDouble(7));
						objmPackageBean.setSet(rs1.getDouble(8));
						objmPackageBean.setMin(rs1.getDouble(9));
						objmPackageBean.setMax(rs1.getDouble(10));
						objmPackageBean.setVents(rs1.getDouble(11));
						objmPackageBean.setDrainage(rs1.getDouble(12));
						objmPackageBean.setWtiUnit(rs1.getString(13));
						objmPackageBean.setWtmUnit(rs1.getString(14));
						objmPackageBean.setMsiUnit(rs1.getString(15));
						objmPackageBean.setMsmUnit(rs1.getString(16));
						objmPackageBean.setLengthUnit(rs1.getString(17));
						objmPackageBean.setWidthUnit(rs1.getString(18));
						objmPackageBean.setHeightUnit(rs1.getString(19));
						objmPackageBean.setSetUnit(rs1.getString(20));
						objmPackageBean.setMinUnit(rs1.getString(21));
						objmPackageBean.setMaxUnit(rs1.getString(22));
						objmPackageBean.setVentsUnit(rs1.getString(23));
						objmPackageBean.setDrainageUnit(rs1.getString(24));
					}
					objmList.add(objmPackageBean);
				}
				objmBillDetailBean.setObjmPackageBean(objmList);
			}
			
			{
				stmt=con.prepareStatement("SELECT equipment_number, description, harmonize_code, hazard_code, manufacturer, country,ifnull(customer_id,0) FROM cargo where bill_lading_id=? order by 1");
				stmt.setInt(1, BillLadingId);
				rs=stmt.executeQuery();
				ArrayList<CargoBean> objmList=new ArrayList<CargoBean>();
				CargoBean objmCargoBean;
				while(rs.next()){
					objmCargoBean=new CargoBean();
					objmCargoBean.setCargoEquipment(rs.getString(1));
					objmCargoBean.setGoodsDescription(rs.getString(2));
					objmCargoBean.setHarmonizedCode(rs.getString(3));
					objmCargoBean.setHazardCode(rs.getString(4));
					objmCargoBean.setManufacturer(rs.getString(5));
					objmCargoBean.setCargoCountry(rs.getString(6));
					objmCargoBean.setManufacturerId(rs.getInt(7));
					objmList.add(objmCargoBean);
				}
				objmBillDetailBean.setObjmCargoBean(objmList);
			}
			
			{
				stmt=con.prepareStatement("SELECT is_ams_sent, is_isf_sent, is_readonly, error_description, isf_error_description, is_manifest_error, is_isf_error FROM bill_detail_status where bill_lading_id=? and login_scac=?");
				stmt.setInt(1, BillLadingId);
				stmt.setString(2, loginScac);
				rs=stmt.executeQuery();
				while(rs.next()){
					objmBillDetailBean.setIsAmsSent(rs.getBoolean(1));
					objmBillDetailBean.setIsIsfSent(rs.getBoolean(2));
					objmBillDetailBean.setIsReadonly(rs.getBoolean(3));
					objmBillDetailBean.setAmsErrorDescription(rs.getString(4));
					objmBillDetailBean.setIsfErrorDescription(rs.getString(5));
					objmBillDetailBean.setIsValidAms(rs.getBoolean(6));
					objmBillDetailBean.setIsValidIsf(rs.getBoolean(7));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmBillDetailBean;
	}
	public void backupBillHeader(String loginScac,int billLadingId)throws Exception{
		stmt=con.prepareStatement("replace into bill_header_history (bill_lading_id, login_scac, " +
				"bill_lading_number, bill_status, bill_type, hbl_scac, nvo_type, nvo_bl, scac_bill, master_bill, " +
				"master_carrier_scac, voyage_number, voyage_id, load_port, discharge_port, country_of_origin, " +
				"place_of_receipt, place_of_delivery, move_type, created_user, created_date)" +
				"select bill_lading_id, login_scac, bill_lading_number, bill_status, bill_type, hbl_scac, " +
				"nvo_type, nvo_bl, scac_bill, master_bill, master_carrier_scac, voyage_number, voyage_id, " +
				"load_port, discharge_port, country_of_origin, place_of_receipt, place_of_delivery, move_type, " +
				"created_user, created_date from bill_header where bill_lading_id=? and login_scac=?");
		stmt.setInt(1, billLadingId);
		stmt.setString(2, loginScac);
		rs=stmt.executeQuery();
	}
	public boolean isMIChanged(BillDetailBean objmBillDetailBean)throws Exception{
		Boolean result=false;
		BillDetailBean objmBillDetailBean2=getData(objmBillDetailBean.getBillLadingId(), objmBillDetailBean.getLoginScac());
		if(objmBillDetailBean2!=null){
			if(
				objmBillDetailBean.getVoyageId()==objmBillDetailBean2.getVoyageId()&&
				objmBillDetailBean.getLoadPort().equals(objmBillDetailBean2.getLoadPort())&&
				objmBillDetailBean.getDischargePort().equals(objmBillDetailBean2.getDischargePort())
			){
				result=false;
			}else{ 
				result=true;
			}
		}else{
			result=false;
		}
		return result;
	}
	public void checkForAmendment(BillDetailBean objmBillDetailBean) throws Exception{
		if(objmBillDetailBean.getIsAmendment()){
			if(isMIChanged(objmBillDetailBean)==true){
				BillDetailBean objmBillDetailBean2=getData(objmBillDetailBean.getBillLadingId(), objmBillDetailBean.getLoginScac());
				if(isMISent(objmBillDetailBean2.getVoyageId(), objmBillDetailBean2.getLoadPort(), objmBillDetailBean2.getDischargePort(), objmBillDetailBean.getLoginScac())){
					String dischargePort="";
					if(isFROBBill(objmBillDetailBean2.getDischargePort()))
						dischargePort=getDistrictPortForFROB(objmBillDetailBean2.getVoyageId(), objmBillDetailBean2.getLoginScac());
					else
						dischargePort=objmBillDetailBean2.getDischargePort();
					
					stmt1=con.prepareStatement("insert into ams_to_be_sent (login_scac, voyage_id, load_port, " +
							"discharge_port, bill_lading_id, equipment_number, control_identifier, action_code, " +
							"amendment_code, sent_date, sent_user) values(?,?,?,?,?,?,?,?,?,now(),?)");
					stmt1.setString(1, objmBillDetailBean.getLoginScac());
					stmt1.setInt(2, objmBillDetailBean2.getVoyageId());
					stmt1.setString(3, objmBillDetailBean2.getLoadPort());
					
					stmt1.setString(4, dischargePort);
					stmt1.setInt(5, objmBillDetailBean.getBillLadingId());
					stmt1.setString(6, "");
					stmt1.setString(7, "AI");
					stmt1.setString(8, "D");
					stmt1.setString(9, "03");
					stmt1.setString(10, objmBillDetailBean.getCreatedUser());
					if(stmt1.executeUpdate()!=0){
						System.out.print("amendment to delete a bill is saved");
					}else{
						System.out.print("error while saving an amendment for delete a bill");
					}
				}
			}
			if(isMISent(objmBillDetailBean.getVoyageId(), objmBillDetailBean.getLoadPort(), objmBillDetailBean.getDischargePort(), objmBillDetailBean.getLoginScac())){
				String dischargePort="";
				
				if(isFROBBill(objmBillDetailBean.getDischargePort()))
					dischargePort=getDistrictPortForFROB(objmBillDetailBean.getVoyageId(), objmBillDetailBean.getLoginScac());
				else
					dischargePort=objmBillDetailBean.getDischargePort();
				
				stmt=con.prepareStatement("insert into ams_to_be_sent (login_scac, voyage_id, load_port, " +
						"discharge_port, bill_lading_id, equipment_number, control_identifier, action_code, " +
						"amendment_code, sent_date, sent_user) values(?,?,?,?,?,?,?,?,?,now(),?)");
				stmt.setString(1, objmBillDetailBean.getLoginScac());
				stmt.setInt(2, objmBillDetailBean.getVoyageId());
				stmt.setString(3, objmBillDetailBean.getLoadPort());
				stmt.setString(4, dischargePort);
				stmt.setInt(5, objmBillDetailBean.getBillLadingId());
				stmt.setString(6, "");
				stmt.setString(7, "AI");
				stmt.setString(8, objmBillDetailBean.getActionCode());
				stmt.setString(9, objmBillDetailBean.getAmendmentCode());
				stmt.setString(10, objmBillDetailBean.getCreatedUser());
				if(stmt.executeUpdate()!=0){
					System.out.print("amendment saved");
				}else{
					System.out.print("error while saving an amendment");
				}
			}
		}
	}
/**
 * @author Rohit
 * @method getList
 * @param voyageId,loginScac
 * @return objmList
 * @throws Exception 
 * @date 19 may 2011
 * @description This method is for the retriving the bill Lading Ids For Arrival notice report.
 */
	public ArrayList<BillDetailBean>getList(int voyageId,String loginScac) {
		ArrayList<BillDetailBean> objmList=null;
		ResultSet rs=null;
		try {
			objmList = new ArrayList<BillDetailBean>();
			
			BillDetailBean objmBillDetailBean;
			String Query="SELECT bill_lading_id,login_scac FROM bill_header " +
			" where voyage_id=?  and login_scac=?"+
			"  ORDER BY load_port,discharge_port,place_of_delivery ";
			stmt = con.prepareStatement(Query);
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			rs= stmt.executeQuery();
			while(rs.next()){
				objmBillDetailBean =new BillDetailBean();
				objmBillDetailBean.setBillLadingId(rs.getInt(1));
				objmBillDetailBean.setLoginScac(rs.getString(2));
				objmList.add(objmBillDetailBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
/**
 * @author Rohit
 * @method getList
 * @param voyageId,loginScac,billLadingNumber
 * @return objmList
 * @throws Exception 
 * @date 19 may 2011
 * @description This method is for the retriving the bill Lading Ids For Arrival Notice report.
 */
	public ArrayList<BillDetailBean>getList(int voyageId,String loginScac,String billLadingNumber) {
		ArrayList<BillDetailBean> objmList=null;
		ResultSet rs=null;
		try {
			objmList = new ArrayList<BillDetailBean>();
			
			BillDetailBean objmBillDetailBean;
			String Query="SELECT bill_lading_id,login_scac FROM bill_header " +
			" where voyage_id=?  and login_scac=? and bill_lading_number=?";
			stmt = con.prepareStatement(Query);
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			stmt.setString(3, billLadingNumber);
			rs= stmt.executeQuery();
			while(rs.next()){
				objmBillDetailBean =new BillDetailBean();
				objmBillDetailBean.setBillLadingId(rs.getInt(1));
				objmBillDetailBean.setLoginScac(rs.getString(2));
				objmList.add(objmBillDetailBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
		
/**
 * @author Rohit
 * @method getList
 * @param voyageId,loginScac,billLadingNumber
 * @return objmList
 * @throws Exception 
 * @date 19 may 2011
 * @description This method is for the retriving the bill Lading Ids For Arrival Notice report.
 */
	public ArrayList<BillDetailBean>getList(int voyageId,String loginScac,String loadPort,String dischargePort) {
		ArrayList<BillDetailBean> objmList=null;
		ResultSet rs=null;
		try {
			objmList = new ArrayList<BillDetailBean>();
			
			BillDetailBean objmBillDetailBean;
			String Query="SELECT bill_lading_id,login_scac FROM bill_header" +
			" where voyage_id=? and login_scac=? and load_port like ? and discharge_port like ? "+
			" ORDER BY load_port,discharge_port,place_of_delivery ";
			stmt = con.prepareStatement(Query);
			stmt.setInt(1, voyageId);
			stmt.setString(2, loginScac);
			stmt.setString(3, loadPort + "%");
			stmt.setString(4, dischargePort + "%");
			rs= stmt.executeQuery();
			while(rs.next()){
				objmBillDetailBean =new BillDetailBean();
				objmBillDetailBean.setBillLadingId(rs.getInt(1));
				objmBillDetailBean.setLoginScac(rs.getString(2));
				objmList.add(objmBillDetailBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}
	
	
	
	public ArrayList<BillDetailBean> getDataBillLadingId(String billladingNumber,String loginScac){
		ArrayList<BillDetailBean> objmBillDetailBeanList = new ArrayList<BillDetailBean>();
		try{
			
			stmt=con.prepareStatement("SELECT bill_lading_id,login_scac FROM bill_header" +
			" where bill_lading_number =? and login_scac=? ");
			stmt.setString(1, billladingNumber);
			stmt.setString(2, loginScac);
			rs=stmt.executeQuery();
			while(rs.next()){
				BillDetailBean objmBillDetailBean = new BillDetailBean();
				objmBillDetailBean.setBillLadingId(rs.getInt(1));
				objmBillDetailBean.setLoginScac(rs.getString(2));
				objmBillDetailBeanList.add(objmBillDetailBean);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return objmBillDetailBeanList;
	}
	
	public int getBillLadingId(String billladingNumber,String loginScac){
		int billLadingId = 0;
		try{
			
			stmt=con.prepareStatement("SELECT bill_lading_id,login_scac FROM bill_header" +
			" where bill_lading_number =? and login_scac=? ");
			stmt.setString(1, billladingNumber);
			stmt.setString(2, loginScac);
			rs=stmt.executeQuery();
			while(rs.next()){
				billLadingId = rs.getInt(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return billLadingId;
	}
	
	public boolean isExistsInAMSSent(int billLadingId,String loginScac)throws Exception{
		boolean result=false;
			Statement objmStatement=con.createStatement();
			ResultSet objmResultSet=objmStatement.executeQuery("select bill_lading_id from ams_sent where bill_lading_id='"+billLadingId+"' and login_scac='"+loginScac+"'");
			if(objmResultSet.next())
					result=true;
		return result;
	}
	
	
	/*public ArrayList<BillDetailBean> getEDIBillDetailBeans(ArrayList<AmsToBeSentBean> objmAmsToBeSent){
		ArrayList<BillDetailBean> objmBillDetailBeans=new ArrayList<BillDetailBean>();
		PreparedStatement objmPreparedStatement=null;
		ResultSet objmResultSet=null;
		String billLadingIds="";
		String loginScac="";
		for(int i=0;i<objmAmsToBeSent.size();i++){
			billLadingIds=billLadingIds+objmAmsToBeSent.get(i).getBillLadingId()+",";
			if(loginScac.equals(""))
				loginScac=objmAmsToBeSent.get(i).getLoginScac();
		}
		try {
			String query="select a.bill_lading_id from bill_header a" +
					" where a.bill_lading_id in ("+billLadingIds.substring(0,billLadingIds.lastIndexOf(","))+") order by hbl_scac";
			objmPreparedStatement=con.prepareStatement(query);
			objmResultSet=objmPreparedStatement.executeQuery();
			while(objmResultSet.next()){
				objmBillDetailBeans.add(getData(objmResultSet.getInt(1), loginScac));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmBillDetailBeans;
	}*/
	
	public Boolean isFROBBill(String dischargePort){
		Boolean isFROB=false;
		try {
			stmt=con.prepareStatement("select port_code from foreign_port where port_code=?");
			stmt.setString(1, dischargePort);
			if(stmt.executeQuery().next())
				isFROB=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFROB;
	}
	public String getDistrictPortForFROB(int voyageId,String login_scac){
		String districtPort="";
		try {
			stmt=con.prepareStatement("select b.location_code,a.arrival_date from " +
					"voyage_details a,location b where a.login_scac=? and a.voyage_id=? and " +
					"a.location_id=b.location_id and is_discharge_port=true order by 2 asc");
			stmt.setString(1, login_scac);
			stmt.setInt(2, voyageId);
			rs=stmt.executeQuery();
			if(rs.next()){
				districtPort=rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return districtPort;
	}
	/*public ArrayList<ForeignPortBean> getFrobPorts(int voyageId,String loginScac){
		ArrayList<ForeignPortBean> objmArrayList=new ArrayList<ForeignPortBean>();
		try {
			PreparedStatement objmPreparedStatement=con.prepareStatement("select distinct a.load_port,a.discharge_port from " +
					"bill_header a,foreign_port b where a.login_scac=? and a.voyage_id=? and " +
					"a.discharge_port=b.port_code");
			objmPreparedStatement.setString(1, loginScac);
			objmPreparedStatement.setInt(2, voyageId);
			rs=objmPreparedStatement.executeQuery();
			while(rs.next()){
				ForeignPortBean objmForeignPortBean=new ForeignPortBean();
				objmForeignPortBean.setPortCode(rs.getString(1));
				objmForeignPortBean.setPortName(rs.getString(2));
				objmArrayList.add(objmForeignPortBean);
			}
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return objmArrayList;
	}*/
	
	public String getDischargePort(String loginScac,int billLadingId){
		String dischargePort="";
		try {
			stmt=con.prepareStatement("select discharge_port from bill_header where bill_lading_id=? and login_scac=?");
			stmt.setInt(1, billLadingId);
			stmt.setString(2, loginScac);
			rs=stmt.executeQuery();
			if(rs.next())
				dischargePort=rs.getString(1);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return dischargePort;
	} 
	/*public Boolean removeReadonly(ArrayList<AmsSentBean> objmAmsSentBeans,boolean isAmsSent){
		Boolean result;
		try {
			String billLadingIdString="";
			for(int i=0;i<objmAmsSentBeans.size();i++)
				billLadingIdString=billLadingIdString+"?,";
			stmt=con.prepareStatement("update bill_detail_status set is_readonly=false,is_ams_sent="+isAmsSent+" where bill_lading_id in ("+billLadingIdString.substring(0,billLadingIdString.lastIndexOf(","))+") and login_scac=?");
			int i=0;
			for(;i<objmAmsSentBeans.size();){
				stmt.setInt(i+1, objmAmsSentBeans.get(i).getBillLadingId());
				i++;
			}
			stmt.setString(i+1, objmAmsSentBeans.get(0).getLoginScac());
			
			if(stmt.executeUpdate()>=1)
				result=true;
			else
				result=false;
		} catch (Exception e) {
			 e.printStackTrace();
			 result=false;
		}
		return result;
	}*/
	public ArrayList<BillDetailBean> getSplitBillList(String billLadingNumber, String loginScac) {
		ArrayList<BillDetailBean> objmList=null;
		ResultSet rs=null;
		try {
			objmList = new ArrayList<BillDetailBean>();
			
			BillDetailBean objmBillDetailBean;
			String Query="SELECT distinct a.bill_lading_number,e.vessel_name,a.voyage_number," +
					"b.location_name,c.location_name " +
					"FROM bill_header a,location b,location c,voyage d,vessel e " +
					"where a.bill_lading_number like ? and a.login_scac=? "+
					"and a.login_scac=b.login_scac and a.login_scac=c.login_scac and a.login_scac=d.login_scac " +
					"and a.login_scac=e.login_scac " +
					"and a.load_port=b.location_code and a.discharge_port=c.location_code " +
					"and a.voyage_id=d.voyage_id and d.vessel_id=e.vessel_id ";
			stmt = con.prepareStatement(Query);
			stmt.setString(1, billLadingNumber+"%");
			stmt.setString(2, loginScac);
			
			rs= stmt.executeQuery();
			while(rs.next()){
				objmBillDetailBean =new BillDetailBean();
				objmBillDetailBean.setBillLadingNumber(rs.getString(1));
				objmBillDetailBean.setVesselName(rs.getString(2));
				objmBillDetailBean.setVoyageNumber(rs.getString(3));
				objmBillDetailBean.setLoadPort(rs.getString(4));
				objmBillDetailBean.setDischargePort(rs.getString(5));
				objmList.add(objmBillDetailBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objmList;
	}

	public Boolean validateSplitBill(String billLadingNumber, String loginScac
			,String loadPort,String dischargePort,int voyageId) {
		Boolean result=false;
		ResultSet rs=null;
		try {
			String Query="SELECT * " +
					"FROM bill_header " +
					"where bill_lading_number=? and login_scac=? "+
					"and voyage_id=? and load_port=? and discharge_port=? ";
			stmt = con.prepareStatement(Query);
			stmt.setString(1, billLadingNumber);
			stmt.setString(2, loginScac);
			stmt.setInt(3, voyageId);
			stmt.setString(4, loadPort);
			stmt.setString(5, dischargePort);
			
			rs= stmt.executeQuery();
			if(rs.next())
				result=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean isExistHarmonizedCode(String HarmonizedCode) {
		ArrayList<HarmonizedBean> objmList=null;
		ResultSet rs=null;
		Boolean result=true;
		try {
			objmList = new ArrayList<HarmonizedBean>();
						
			String harmonizedCode="%"+HarmonizedCode+"%";
			stmt = con.prepareStatement("SELECT harmonized_code,description FROM harmonized_code where " +
				" harmonized_code like '"+harmonizedCode+"'");
			
			rs= stmt.executeQuery();
			if (rs.next()){
				result=true;
			}else{
				result=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
		
}