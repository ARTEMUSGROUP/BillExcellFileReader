package migrate;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import mail.SendMail;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.xmlbeans.impl.regex.REUtil;

import response.ErrorMsgBean;
import response.Response;
import beans.amsBeans.Attribute;
import beans.amsBeans.BLXLSBean;
import beans.amsBeans.BillDetailBean;
import beans.amsBeans.CargoBean;
import beans.amsBeans.CustomerProfileBean;
import beans.amsBeans.DistrictPortBean;
import beans.amsBeans.EquipmentBean;
import beans.amsBeans.ForeignPortBean;
import beans.amsBeans.PackageBean;
import beans.amsBeans.VesselBean;
import dao.BillHeaderDAO;
import dao.CountryDAO;
import dao.CustomerProfileDAO;
import dao.LoadProperty;
import dao.LocationDAO;
import dao.VesselDAO;
import dao.VoyageDAO;

public class MigrateBLXLS extends LoadProperty{
	ArrayList<BillDetailBean>objBillDetailBeans = null;
	ArrayList <BillDetailBean> objBillDetailBeanList=null;
	ArrayList <ErrorMsgBean> objErrorMsgBeanList=null;
	ArrayList<Response> objresponselist=new ArrayList<Response>();
	BillDetailBean objBillDetailBean = null;
	ErrorMsgBean objErrorMsgBean = null;
	CargoBean objmCargoBean=null;
	VesselBean objVesselBean = null;
	String loginScac = ""; 
	String message="";
	String subject="";
	static String description="";
	static String code="";
	static String element="";
	static String Source="";
	int ShipperCustomerId ;
	boolean result=true;
	boolean mailResult=true;
	BillHeaderDAO objmBillHeaderDAO=null;
	CustomerProfileDAO objCustomerProfileDAO=null;
	VoyageDAO objmVoyageDAO = null;
	LocationDAO objmLocationDAO = null;
	CountryDAO objCountryDAO = null;
	VesselDAO objmVesselDAO = null;
	FileInputStream objmFileInputStream=null;
	String bodyText="Successfully Uploaded Bill's are : \n";
	File f1;
	String path;
	String errorMsg = "";
	
	public void readfile(File objmFile,String mainpath,String dbname ) throws Exception{
		//String filename = "onlyFileName" ;
		//filename = "/home/tushar/Desktop/MANIFIESTO EDI.xls";
		System.out.println("File to read: " + objmFile);
		f1=objmFile;
		path=mainpath;
		try {
			ArrayList<BLXLSBean>  objBlxlsBeanList =new ArrayList<BLXLSBean>();
			BLXLSBean objBlxlsBean =null;
			objmFileInputStream = new FileInputStream(objmFile);
			org.apache.poi.ss.usermodel.Workbook myWorkBook = null;
			try {
				myWorkBook = WorkbookFactory.create(objmFileInputStream);
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}

			org.apache.poi.ss.usermodel.Sheet mySheet = myWorkBook.getSheetAt(0);
			for (int j = 4; j < mySheet.getLastRowNum()+1; j++) {
				objBlxlsBean = new BLXLSBean();
				boolean isBlankRow = false;
				String cellValue ="";
				Row row = mySheet.getRow(j);
				if(row!=null){
					for (int i = 0; i <=row.getLastCellNum()-1 && !row.equals(""); i++) {
						Cell cell = row.getCell(i);
						cellValue ="";
						if( cell!=null ){

							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								//System.out.print(cell.getStringCellValue()+ "\t");
								cellValue = cell.getStringCellValue();
								break;
							case Cell.CELL_TYPE_NUMERIC:
								Double cw = cell.getNumericCellValue();
								int rowVal = cw.intValue();
								cellValue  = String.valueOf(rowVal);
								//System.out.println(cellValue);
								break;
								//	                case Cell.CELL_TYPE_BLANK:
								//	                	cellValue ="";
								//	                	break;
							default:
							} 
							if (cell.getColumnIndex()==0) {
								objBlxlsBean.setScacCode(cellValue);
							} else if (cell.getColumnIndex()==1) {
								objBlxlsBean.setBlNumber(cellValue);
							}else if (cell.getColumnIndex()==2) {
								objBlxlsBean.setShipper(cellValue);
							}else if (cell.getColumnIndex()==3) {
								objBlxlsBean.setConsignee(cellValue);
							}else if (cell.getColumnIndex()==4) {
								objBlxlsBean.setNotify(cellValue);
							}else if (cell.getColumnIndex()==5) {
								objBlxlsBean.setCarrier(cellValue);
							}else if (cell.getColumnIndex()==6) {
								objBlxlsBean.setLoadPort(cellValue);
							}else if (cell.getColumnIndex()==7) {
								objBlxlsBean.setDischargePort(cellValue);
							}else if (cell.getColumnIndex()==8) {
								objBlxlsBean.setMoveType(cellValue);
							}else if (cell.getColumnIndex()==9) {
								objBlxlsBean.setContainer(cellValue);
							}else if (cell.getColumnIndex()==10) {
								objBlxlsBean.setSeal(cellValue);
							}else if (cell.getColumnIndex()==11) {
								objBlxlsBean.setBoxes(cellValue);
							}else if (cell.getColumnIndex()==12) {
								objBlxlsBean.setContainerWeight(cellValue);
							}else if (cell.getColumnIndex()==13) {
								objBlxlsBean.setUnit(cellValue);
							}else if (cell.getColumnIndex()==14) {
								objBlxlsBean.setDescription(cellValue);
							}else if (cell.getColumnIndex()==15) {
								objBlxlsBean.setHsCode(cellValue);
							}else if (cell.getColumnIndex()==16) {
								objBlxlsBean.setCountry(cellValue);
							}else if (cell.getColumnIndex()==17) {
								objBlxlsBean.setStatus(cellValue);
							}else if (cell.getColumnIndex()==18){
								objBlxlsBean.setMarksAndNumbers(cellValue);
							}

							if((cellValue.equals("")||cellValue==null)&&i==0)
								isBlankRow=true;
						}
					}
				}
				if(!isBlankRow)
					objBlxlsBeanList.add(objBlxlsBean);
			}      

			for(int a=0;a<objBlxlsBeanList.size();a++){
				if (objBlxlsBeanList.get(a).getBillNumber() == null || objBlxlsBeanList.get(a).getBillNumber().trim().equals("")) {
					objBlxlsBeanList.remove(a);
					a--;
				}
			}
			if(objBlxlsBeanList!=null){
				populateBillDetails(objBlxlsBeanList,dbname);
			}
		}catch(Exception e){
			e.printStackTrace();
			result=false;
			//Set Error message
		}finally{
			try {
				if(result){
					System.out.println("Migration complete");
				}else{
					System.out.println("Migration incomplete");
				}
				//objCustomerProfileDAO.commitTransaction(result);
				objmBillHeaderDAO.commitTransaction(true);
				objmFileInputStream.close();
				closeAllDAO();

				//SendMail objmSendMail=new SendMail();
				//objmSendMail.sendMail(dbname,mainpath+"RESPONSE/"+objmFile.getName(),objmFile.getName()," Artemus :Bill Excel Response file");
				//While putting on the server use following code
				//objmSendMail.sendMail(dbname,mainpath+"RESPONSE\\"+objmFile.getName(),objmFile.getName()," Artemus :Bill Response file");
				if(result)
					//moveFile(objmFile,mainpath+"PROCESSED/"+objmFile.getName());
					moveFile(objmFile,mainpath+"PROCESSED\\"+objmFile.getName());
				else
					//moveFile(objmFile,mainpath+"ERROR/"+objmFile.getName());
					moveFile(objmFile,mainpath+"ERROR\\"+objmFile.getName());
			} catch (Exception e2) {
				e2.printStackTrace();
			}finally{
				try {
					closeAllDAO();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	
	public void populateBillDetails(ArrayList<BLXLSBean> objBlxlsBeanList,String dbname)  throws Exception {
		objBillDetailBeanList = new ArrayList <BillDetailBean>();
		objErrorMsgBeanList = new ArrayList <ErrorMsgBean>();
		initializeDAO();
		for (int i = 0; i < objBlxlsBeanList.size(); i++) {
			objBillDetailBean = new BillDetailBean();
			objErrorMsgBean = new ErrorMsgBean();
			String billLadingNumber = "";
			String vesselVoyage = "";
			String harmonizedCode = "";
			String country = "";
			String hblScac = "";
			loginScac=dbname;
			hblScac = objBlxlsBeanList.get(i).getScacCode();
			if(objBlxlsBeanList.get(i).getBillNumber()!=null){
				billLadingNumber=objBlxlsBeanList.get(i).getBillNumber().toUpperCase().trim();
			}
			vesselVoyage = objBlxlsBeanList.get(i).getCarrier().toUpperCase().trim();
			harmonizedCode = objBlxlsBeanList.get(i).getHsCode();
			if(objBlxlsBeanList.get(i).getCountry()!=null){
				country = objBlxlsBeanList.get(i).getCountry();
				country = country.trim();
			}
			
			objBillDetailBean.setLoginScac(loginScac);
			objBillDetailBean.setHblScac(hblScac);
			
			if(billLadingNumber != null && !billLadingNumber.equals("")){
				objBillDetailBean.setBillLadingNumber(billLadingNumber);
			}
			if(billLadingNumber.length()>12){
				errorMsg = "- The Bill Number exceeds its maximum number of characters, it should have max 12 char.";
			}
			errorMsg = errorMsg + validateCustomer(objBlxlsBeanList.get(i));
			errorMsg = errorMsg + validateVesselVoyage(vesselVoyage);
			errorMsg = errorMsg + validateLoadPortDischargePort(objBlxlsBeanList.get(i));
			errorMsg = errorMsg + validateHarmonizedCode(objBlxlsBeanList.get(i));
			errorMsg = errorMsg + validateCountry(country);
			
			populateEquipment(objBlxlsBeanList.get(i));
			populatePackage(objBlxlsBeanList.get(i));
			populateCargo(objBlxlsBeanList.get(i));
			
			
			/*//Check BL is transmitted or not
			boolean flag=false;
			int billLadingId = objmBillHeaderDAO.getBillLadingId(objBillDetailBean.getBillLadingNumber(), loginScac);
			//objBillDetailBean.setBillLadingId(billLadingId);
			if(billLadingId!=0){
				flag = objmBillHeaderDAO.isExistsInAMSSent(billLadingId, loginScac);
				if(flag==true){
					errorMsg = errorMsg + " BL Can't updated, because it is already transmitted.";
				}
			}*/
			
			if (errorMsg.equals("")){
				populateBillDetailBean(objBlxlsBeanList.get(i));
				objBillDetailBeanList.add(objBillDetailBean);
			}else{
				objErrorMsgBean.setBillLadingNumber(billLadingNumber);
				objErrorMsgBean.setErrorMsg(errorMsg);
				objErrorMsgBeanList.add(objErrorMsgBean);
			}
		}
		removeDuplicateBills();
		sendResponseMail(dbname);
	}

	public void initializeDAO() {
		objmBillHeaderDAO = new BillHeaderDAO();
		objCustomerProfileDAO=new CustomerProfileDAO();
		objmVoyageDAO = new VoyageDAO();
		objmVesselDAO = new VesselDAO();
		objmLocationDAO = new LocationDAO();
		objCountryDAO = new CountryDAO();
	}
	public void closeAllDAO() {
		objmBillHeaderDAO.closeAll();
		objCustomerProfileDAO.closeAll();
		objmVoyageDAO.closeAll();
		objmVesselDAO.closeAll();
		objmLocationDAO.closeAll();
		objCountryDAO.closeAll();
	}

	public void removeDuplicateBills() {
		objBillDetailBeans = new ArrayList<BillDetailBean>();
		
		if(objBillDetailBeanList.size()!=0){
			for(int i=0; i<objBillDetailBeanList.size();i++){
				//BillDetailBean objBean = new BillDetailBean();
				if(objBillDetailBeans.size()==0){
					objBillDetailBeans.add(objBillDetailBeanList.get(i));
					//objBillDetailBeans.addAll(objBillDetailBeanList);
				}else{
					boolean billfound=false;
					for(int j=0; j<objBillDetailBeans.size(); j++){
						if(objBillDetailBeanList.get(i).getBillLadingNumber().equals(objBillDetailBeans.get(j).getBillLadingNumber())){
							
							if(!objBillDetailBeanList.get(i).getObjmEquipmentBean().get(0).getEquipment().equals(objBillDetailBeans.get(j).getObjmEquipmentBean().get(0).getEquipment())){
							//if(objBillDetailBeanList.get(i).getObjmEquipmentBean().contains(objBillDetailBeans.get(j).getObjmEquipmentBean().get(j).getEquipment())){
	
								mergerEquipment(objBillDetailBeanList.get(i),objBillDetailBeans.get(j));
								mergerPackage(objBillDetailBeanList.get(i),objBillDetailBeans.get(j));
								mergeCargo(objBillDetailBeanList.get(i),objBillDetailBeans.get(j));
								billfound=true;
								break;
							}else{
								
								mergerDuplicateEquipment(objBillDetailBeanList.get(i),objBillDetailBeans.get(j));
								mergerPackage(objBillDetailBeanList.get(i),objBillDetailBeans.get(j));
								mergeCargo(objBillDetailBeanList.get(i),objBillDetailBeans.get(j));
								billfound=true;
								break;
							}
						}
					}
					if (!billfound)
						objBillDetailBeans.add(objBillDetailBeanList.get(i));
				}
				
			}
		}
		insertIntoBillHeader(objBillDetailBeans);
	}
	
	public void mergerEquipment(BillDetailBean objBillDetailBean, BillDetailBean objBillDetailBean2){
		
		objBillDetailBean2.getObjmEquipmentBean().add(objBillDetailBean.getObjmEquipmentBean().get(0));
	}
	
	public void mergerDuplicateEquipment(BillDetailBean objBillDetailBean, BillDetailBean objBillDetailBean2){
		objBillDetailBean2.getObjmEquipmentBean().set(0, objBillDetailBean.getObjmEquipmentBean().get(0));
		// (objBillDetailBean.getObjmEquipmentBean().get(0));
	}
	
	public void mergerPackage(BillDetailBean objBillDetailBean, BillDetailBean objBillDetailBean2){

		objBillDetailBean2.getObjmPackageBean().add(objBillDetailBean.getObjmPackageBean().get(0));

	}
	public void mergeCargo(BillDetailBean objBillDetailBean, BillDetailBean objBillDetailBean2){

		objBillDetailBean2.getObjmCargoBean().add(objBillDetailBean.getObjmCargoBean().get(0));

	}
	
	public void insertIntoBillHeader(ArrayList<BillDetailBean> objBillDetailBean) {
		boolean flag=false;
		if(objBillDetailBean.size()!=0){
			
			objmBillHeaderDAO=new BillHeaderDAO();
			
			int i;
			String a="";
			for(i=0;i<objBillDetailBean.size();i++){
				objErrorMsgBean = new ErrorMsgBean();
				
				try {
					if(objmBillHeaderDAO.isExists(objBillDetailBean.get(i).getBillLadingNumber(), loginScac)){
						int billLadingId = objmBillHeaderDAO.getBillLadingId(objBillDetailBean.get(i).getBillLadingNumber(), loginScac);
						objBillDetailBean.get(i).setBillLadingId(billLadingId);
						
							if(objmBillHeaderDAO.update(objBillDetailBean.get(i)).equals("Success")){
								System.out.println("Bill '" +objBillDetailBean.get(i).getBillLadingNumber()+ "' Updated Succesfully");
							}else{
								objErrorMsgBean.setBillLadingNumber(objBillDetailBean.get(i).getBillLadingNumber().trim());
								objErrorMsgBean.setErrorMsg("Diplicate Containers not allowed in the system, Please check CONTAINER'S for this BL.");
								objErrorMsgBeanList.add(objErrorMsgBean);
								a = objmBillHeaderDAO.delete(objBillDetailBean.get(i));
								System.out.println("Bill '" +objBillDetailBean.get(i).getBillLadingNumber()+ "' Not Saved");
								if (a.equals("Success") ){
									objBillDetailBean.remove(objBillDetailBean.get(i));
									i--;
								}
							}
						
					}else{
						if(objmBillHeaderDAO.insert(objBillDetailBean.get(i)).equals("Success")){
							System.out.println("Bill '" +objBillDetailBean.get(i).getBillLadingNumber()+ "' Saved Succesfully");
						}else{
							objErrorMsgBean.setBillLadingNumber(objBillDetailBean.get(i).getBillLadingNumber().trim());
							objErrorMsgBean.setErrorMsg("Diplicate Containers not allowed in the system, Please check CONTAINER'S for this BL.");
							objErrorMsgBeanList.add(objErrorMsgBean);
							a = objmBillHeaderDAO.delete(objBillDetailBean.get(i));
							System.out.println("Bill '" +objBillDetailBean.get(i).getBillLadingNumber()+ "' Not Saved");
							if (a.equals("Success") ){
								objBillDetailBean.remove(objBillDetailBean.get(i));
								i--;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendResponseMail(String dbname) {
		SendMail objmSendMail=new SendMail();
		if (objBillDetailBeans.size()!=0) {
			//These BLs has been uploaded.
			
			for (int i = 0; i < objBillDetailBeans.size(); i++) {
					bodyText= bodyText +"\n "+ "("+(i+1)+") "+objBillDetailBeans.get(i).getBillLadingNumber();
			}
		}else{
			bodyText = "";
		}
		
		bodyText = bodyText+"\n\n\n"+"Rejected Bill's are :";
		if (objErrorMsgBeanList.size()!=0) {
				//These BL's has been error.
				for (int j = 0; j < objErrorMsgBeanList.size(); j++) {
						bodyText= bodyText +"\n "+"("+(j+1)+") "+ objErrorMsgBeanList.get(j).getBillLadingNumber() + ": "+objErrorMsgBeanList.get(j).getErrorMsg();
					result=false;
				}
			} 
		//objmSendMail.sendMail(dbname, path+"RESPONSE/"+f1.getName(), f1.getName(),"Response : Bill Excel file." ,bodyText);
		objmSendMail.sendMail(dbname, path+"RESPONSE\\"+f1.getName(), f1.getName(),"Response : Bill Excel file." ,bodyText);
	}

/*	public String validateBillNumber(String billLadingNumber, String loginScac) throws Exception {
		String result = "";
		objmBillHeaderDAO=new BillHeaderDAO();
		try{
			if(billLadingNumber != null && !billLadingNumber.equals("") ){
			if(objmBillHeaderDAO.isExists(billLadingNumber, loginScac)){
				//System.out.println("Bill with "+billLadingNumber+" already exists");
				result = "Bill with BL number "+"'"+billLadingNumber+"'"+" already exists. ";
			}else
			{
				objBillDetailBean.setBillLadingNumber(billLadingNumber);
				result = "";
			}}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				//objmBillHeaderDAO.closeAll();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	*/
	public String validateCustomer(BLXLSBean blxlsBean) throws Exception {
		
		
		CustomerProfileBean objCustomerProfileBean = new CustomerProfileBean();
		String returnMsg="";
		String shipeerName="";
		String consignee="";
		String notify="";
		
		try{
			if(blxlsBean.getShipper()!=null){
				shipeerName=blxlsBean.getShipper().trim();
			}else{
				shipeerName="";
			}
			if(blxlsBean.getConsignee()!=null){
				consignee=blxlsBean.getConsignee().trim();
			}else{
				consignee="";
			}
			if(blxlsBean.getNotify()!=null){
				notify=blxlsBean.getNotify().trim();
			}else{
				notify="";
			}
				if(!shipeerName.equals("")){
					if(objCustomerProfileDAO.isExist(shipeerName, loginScac)){
						objBillDetailBean.setShipperName(shipeerName);
						//objBillDetailBean.setBookingPartyName(blxlsBean.getShipper().trim());
						objBillDetailBean.setConsolidatorName(shipeerName);
						objBillDetailBean.setSellerName(shipeerName);
						objBillDetailBean.setStufferName(shipeerName);
						ShipperCustomerId = objCustomerProfileDAO.getCustomerId(shipeerName, loginScac);
						if(ShipperCustomerId!=0){
							objCustomerProfileBean = objCustomerProfileDAO.getData(ShipperCustomerId);
							if(objCustomerProfileBean.getAddress2().length()>2){
								objBillDetailBean.setShipperAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getAddress2()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setShipperId(objCustomerProfileBean.getCustomerId());
							
								/*objBillDetailBean.setBookingPartyAddress(objCustomerProfileBean.getAddress1()+"\n"+objCustomerProfileBean.getAddress2()+"\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setBookingPartyId(objCustomerProfileBean.getCustomerId());*/
								
								objBillDetailBean.setSellerAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getAddress2()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setSellerId(objCustomerProfileBean.getCustomerId());
								
								objBillDetailBean.setConsolidatorAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getAddress2()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setConsolidatorId(objCustomerProfileBean.getCustomerId());
								
								objBillDetailBean.setStufferAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getAddress2()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setStufferId(objCustomerProfileBean.getCustomerId());
							}else{
								objBillDetailBean.setShipperAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setShipperId(objCustomerProfileBean.getCustomerId());
							
								objBillDetailBean.setSellerAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setSellerId(objCustomerProfileBean.getCustomerId());
								
								objBillDetailBean.setConsolidatorAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setConsolidatorId(objCustomerProfileBean.getCustomerId());
								
								objBillDetailBean.setStufferAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setStufferId(objCustomerProfileBean.getCustomerId());
							}
													
						}
					}else{
						returnMsg =" The Shipper "+"'"+shipeerName+"'"+" does not exist in the system. ";
					}
			}
				if(!consignee.equals("")){
					if(objCustomerProfileDAO.isExist(consignee, loginScac)){
						objBillDetailBean.setConsigneeName(consignee);
						//objBillDetailBean.setNotifyName(blxlsBean.getConsignee().trim());
						objBillDetailBean.setImporterName(consignee);
						objBillDetailBean.setBuyerName(consignee);
						objBillDetailBean.setShipToName(consignee);
						
						int customerId = objCustomerProfileDAO.getCustomerId(consignee, loginScac);
						if(customerId!=0){
							objCustomerProfileBean = objCustomerProfileDAO.getData(customerId);
							
							if(objCustomerProfileBean.getAddress2().length()>2){
								objBillDetailBean.setConsigneeAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getAddress2()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setConsigneeId(objCustomerProfileBean.getCustomerId());
	
								objBillDetailBean.setImporterAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getAddress2()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setImporterId(objCustomerProfileBean.getCustomerId());
								
								objBillDetailBean.setBuyerAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getAddress2()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setBuyerId(objCustomerProfileBean.getCustomerId());
								
								objBillDetailBean.setShipToAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getAddress2()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setShipToId(objCustomerProfileBean.getCustomerId());
								
							}else{
								objBillDetailBean.setConsigneeAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setConsigneeId(objCustomerProfileBean.getCustomerId());
	
								objBillDetailBean.setImporterAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setImporterId(objCustomerProfileBean.getCustomerId());
								
								objBillDetailBean.setBuyerAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setBuyerId(objCustomerProfileBean.getCustomerId());
								
								objBillDetailBean.setShipToAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setShipToId(objCustomerProfileBean.getCustomerId());
								
							}
							
						}
					}else{
						returnMsg = returnMsg +" The Consignee "+"'"+consignee+"'"+ " does not exist in the system. ";
					}
				}
				if(!notify.equals("")){
					if(objCustomerProfileDAO.isExist(notify, loginScac)){
						objBillDetailBean.setNotifyName(notify);
						
						int customerId = objCustomerProfileDAO.getCustomerId(notify, loginScac);
						if(customerId!=0){
							objCustomerProfileBean = objCustomerProfileDAO.getData(customerId);
							if(objCustomerProfileBean.getAddress2().length()>2){
								objBillDetailBean.setNotifyAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getAddress2()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setNotifyId(objCustomerProfileBean.getCustomerId());
							}else{
								objBillDetailBean.setNotifyAddress(objCustomerProfileBean.getAddress1()+"\r\n"+objCustomerProfileBean.getCity()
										+" "+objCustomerProfileBean.getState()+" "+objCustomerProfileBean.getZipCode()+" "+objCustomerProfileBean.getCountry());
								objBillDetailBean.setNotifyId(objCustomerProfileBean.getCustomerId());
							}
						}
						
					}else{
						returnMsg = returnMsg +" The Notify "+ "'"+notify+"'"+" does not exist in the system. ";
					}
				}
				if (!returnMsg.equals(""))
					return returnMsg;
				 objBillDetailBean.setBookingPartyName("");
				 objBillDetailBean.setBookingPartyAddress("");
				 objBillDetailBean.setIsBookingParty(false);
				 /* objBillDetailBean.setSellerAddress("");
				 objBillDetailBean.setSellerName("");
				 objBillDetailBean.setIsSeller(false);
				 objBillDetailBean.setStufferName("");
				 objBillDetailBean.setStufferAddress("");
				 objBillDetailBean.setIsStuffer(false);
				 objBillDetailBean.setConsolidatorAddress("");
				 objBillDetailBean.setIsConsolidator(false);
				 objBillDetailBean.setConsolidatorName("");
				 objBillDetailBean.setIsImporter(false);
				 objBillDetailBean.setImporterName("");
				 objBillDetailBean.setImporterAddress("");
				 objBillDetailBean.setBuyerAddress("");
				 objBillDetailBean.setBuyerName("");
				 objBillDetailBean.setIsBuyer(false);
				 objBillDetailBean.setShipToName("");
				 objBillDetailBean.setShipToAddress("");
				 objBillDetailBean.setIsShipTo(false);
				 objBillDetailBean.setNotifyName("");
				 objBillDetailBean.setNotifyAddress("");
				 objBillDetailBean.setIsNotify(false);*/
				
				 if(objBillDetailBean.getConsigneeId()==objBillDetailBean.getNotifyId())
						objBillDetailBean.setIsNotify(true);
					else
						objBillDetailBean.setIsNotify(false);
					
					if(objBillDetailBean.getConsigneeId()==objBillDetailBean.getImporterId())
						objBillDetailBean.setIsImporter(true);
					else
						objBillDetailBean.setIsImporter(false);
					
					if(objBillDetailBean.getConsigneeId()==objBillDetailBean.getBuyerId())
						objBillDetailBean.setIsBuyer(true);
					else
						objBillDetailBean.setIsBuyer(false);
					
					if(objBillDetailBean.getConsigneeId()==objBillDetailBean.getShipToId())
						objBillDetailBean.setIsShipTo(true);
					else
						objBillDetailBean.setIsShipTo(false);
					
					if(objBillDetailBean.getShipperId()==objBillDetailBean.getBookingPartyId())
						objBillDetailBean.setIsBookingParty(true);
					else
						objBillDetailBean.setIsBookingParty(false);
					
					if(objBillDetailBean.getShipperId()==objBillDetailBean.getSellerId())
						objBillDetailBean.setIsSeller(true);
					else
						objBillDetailBean.setIsSeller(false);
					
					if(objBillDetailBean.getShipperId()==objBillDetailBean.getConsolidatorId())
						objBillDetailBean.setIsConsolidator(true);
					else
						objBillDetailBean.setIsConsolidator(false);
					
					if(objBillDetailBean.getShipperId()==objBillDetailBean.getStufferId())
						objBillDetailBean.setIsStuffer(true);
					else
						objBillDetailBean.setIsStuffer(false);
				
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnMsg;
	}
	
	public String validateVesselVoyage(String vesselVoyage) throws Exception {
		String returnMsg="";
		if (vesselVoyage!="" && vesselVoyage!=null) {
			String[] parts = vesselVoyage.split("-");

			String vessel = parts[0]; 
			String voyageNumber = parts[1];
			int vesselId;
			int voyageId;
			
			objVesselBean = new VesselBean();
			vesselId = objmVoyageDAO.getVesselId(vessel, loginScac);
			objVesselBean = objmVesselDAO.getData(vesselId);
			try{
				if(objmVoyageDAO.isExist(loginScac, vesselId, voyageNumber)){
					objBillDetailBean.setVesselName(vessel+"-"+voyageNumber);
					objBillDetailBean.setVoyageNumber(voyageNumber);
					voyageId = objmVoyageDAO.getVoyageId(loginScac, vesselId, voyageNumber);
					objBillDetailBean.setVoyageId(voyageId);
					objBillDetailBean.setCarrierScac(objVesselBean.getUsaScacCode());
				}else{
					returnMsg = "Voyage "+"'"+voyageNumber+"'" + " does not exist in the system. ";
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnMsg;
	}
	
	public String validateLoadPortDischargePort(BLXLSBean blxlsBean) throws Exception {
		
		ArrayList<ForeignPortBean> objmForeignPortBean = new ArrayList<ForeignPortBean>();
		ArrayList<DistrictPortBean> objmdDistrictPortBean = new ArrayList<DistrictPortBean>();
		String resultMSg = "";
		String loadPort="";
		String dischargePort="";
		try{
			if(blxlsBean.getLoadPort()!=null ){
				loadPort=blxlsBean.getLoadPort().trim();
			}
			if(blxlsBean.getDischargePort()!=null ){
				dischargePort=blxlsBean.getDischargePort().trim();
			}
			if(!loadPort.equals("")){
				if(objmLocationDAO.checkForLocationName(loadPort, loginScac)){
					objmForeignPortBean = objmLocationDAO.getLoadPortListFromName(objBillDetailBean.getVoyageId(), loginScac,loadPort);
					if(objmForeignPortBean.size()!=0){
						objBillDetailBean.setLoadPort(objmForeignPortBean.get(0).getPortCode());
					}else{
						resultMSg = "'"+loadPort+"'" +" Load Port does not exist in the system. ";
					}
				}else{
					resultMSg = "'"+loadPort+"'" +"Load Port does not exist in the system. ";
				}

			}else{
				resultMSg = "Load Port does not exist in the system. ";
			}

			if(!dischargePort.equals("")){
				if(objmLocationDAO.checkForLocationName(dischargePort, loginScac)){
					objmdDistrictPortBean = objmLocationDAO.getDischargePortListFromName(objBillDetailBean.getVoyageId(), loginScac, dischargePort);
					
					if(objmdDistrictPortBean.size()!=0){
						objBillDetailBean.setDischargePort(objmdDistrictPortBean.get(0).getPortCode());
					}else{
						resultMSg = "'"+dischargePort+"'" +" Discharge Port does not exist in the system. ";
					}
				}else{
					resultMSg = "'"+dischargePort+"'" +"Discharge Port does not exist in the system. ";
				}
			}else{
				resultMSg = resultMSg + " Discharge Port does not exist in the system. ";
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultMSg;
	}
	public String validateHarmonizedCode(BLXLSBean blxlsBean) throws Exception {
		ArrayList<CargoBean> objmCargoBeans=new ArrayList<CargoBean>();
		CargoBean objmCargoBean=new CargoBean();
		String returnMsg="";
		String hsCode="";
		try{
			if(blxlsBean.getHsCode()!=null){
				hsCode = blxlsBean.getHsCode().trim();
			}
			if(!hsCode.equals("")){
				if(objmBillHeaderDAO.isExistHarmonizedCode(hsCode)){
					objmCargoBean.setHarmonizedCode(hsCode);
					objmCargoBeans.add(objmCargoBean);
					objBillDetailBean.setObjmCargoBean(objmCargoBeans);
				}
				else{
					returnMsg = "'"+hsCode+"'"+ " HarmonizedCode does not exist in the system";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnMsg;
	}
	
	public String validateCountry(String country) throws Exception {
	
		String returnCountry = "";
		try{
			if(country!=null){
				country = country.trim();
			}
			if(!country.equals("")){
				if(objCountryDAO.isCountryExist(country)){
					String countryCode = objCountryDAO.getCountryCode(country);
					objBillDetailBean.setOriginCountry(countryCode);
				}
				else{
					returnCountry = country+ "Country does not exist in the system";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnCountry;
	}

	public void populateEquipment(BLXLSBean blxlsBean) throws Exception{
		ArrayList<EquipmentBean> objmEquipmentBeans=new ArrayList<EquipmentBean>();
		EquipmentBean objEquipmentBean=new EquipmentBean();
				if(blxlsBean.getContainer()==null)
					objEquipmentBean.setEquipment("");
				else
					objEquipmentBean.setEquipment(blxlsBean.getContainer());

				if(objEquipmentBean.getEquipment().equalsIgnoreCase("N/C")||objEquipmentBean.getEquipment().equals(""))
					objEquipmentBean.setSizeType("");
				else
					objEquipmentBean.setSizeType("40RFH");
				
				String SEAL1=blxlsBean.getSeal();
				if(SEAL1!=null){
					String[] SEAL=SEAL1.split(",");
					if(SEAL!=null)
						for(int i=0;i<SEAL.length;i++)
							if(SEAL[i]==null)
								SEAL[i]="";
					objEquipmentBean.setSeal(SEAL);
				}
					
				
				/*for(int a=0;a<objmEquipmentBeans.size();a++){
					System.out.println("equi2 :"+ objmEquipmentBeans.get(a).getEquipment());
				}*/
				
				objmEquipmentBeans.add(objEquipmentBean);
				objBillDetailBean.setObjmEquipmentBean(objmEquipmentBeans);
	}
	
	public void populatePackage(BLXLSBean blxlsBean) throws Exception{
		ArrayList<PackageBean> objmPackageBeans=new ArrayList<PackageBean>();
		//Equipment[] objmEquipmentsbean=blbean.getEquipment();
		//Package[] objmPackagesbean=blbean.getPackage();
		Attribute attributebean = new Attribute();
		String containerWeight;
		String weightUnit;
		String marksAndNumber="";
		PackageBean objPackageBean=new PackageBean();
		objPackageBean.setPackageEquipment(blxlsBean.getContainer());
		if(blxlsBean.getMarksAndNumbers()!=null){
			marksAndNumber = blxlsBean.getMarksAndNumbers();
		}
		marksAndNumber=marksAndNumber.trim();
		marksAndNumber=marksAndNumber.toUpperCase();
		objPackageBean.setMarks(marksAndNumber);
		objPackageBean.setPieces(blxlsBean.getBoxes());

		objPackageBean.setPackages("CTN");
		//weight
		containerWeight = blxlsBean.getContainerWeight();
		if(containerWeight!=null){
			containerWeight = containerWeight.replaceAll("[^0-9.]", "");
		}
		
		weightUnit = blxlsBean.getUnit();
		
		if(weightUnit!=null && weightUnit!=""){
				attributebean.setType(("Wt. (M)")); 
					if(containerWeight==null ||containerWeight.equals("")){
						objPackageBean.setWtm(Double.parseDouble("0.0"));
					}
					else
						objPackageBean.setWtm(Double.parseDouble(containerWeight));
					if(weightUnit!=null)
						objPackageBean.setWtmUnit(weightUnit);
					objPackageBean.setWti(Double.parseDouble("0.0"));
					objPackageBean.setWtiUnit("");
				}else{
					if(attributebean.getValue()==null)
						objPackageBean.setWti(Double.parseDouble("0.0"));
					else
						objPackageBean.setWti(Double.parseDouble(attributebean.getValue()));
					if(attributebean.getUnits()!=null)
						objPackageBean.setWtiUnit(attributebean.getUnits());
					objPackageBean.setWtm(Double.parseDouble("0.0"));
					objPackageBean.setWtmUnit("");
				}
			
		objPackageBean.setLength(0.0);
		objPackageBean.setLengthUnit("");
		objPackageBean.setWidth(0.0);
		objPackageBean.setWidthUnit("");
		objPackageBean.setHeight(0.0);
		objPackageBean.setHeightUnit("");
		objPackageBean.setSet(0.0);
		objPackageBean.setSetUnit("");
		objPackageBean.setMin(0.0);
		objPackageBean.setMinUnit("");
		objPackageBean.setMax(0.0);
		objPackageBean.setMaxUnit("");
		objPackageBean.setVents(0.0);
		objPackageBean.setVentsUnit("");
		objPackageBean.setDrainage(0.0);
		objPackageBean.setDrainageUnit("");   

		objmPackageBeans.add(objPackageBean);

	objBillDetailBean.setObjmPackageBean(objmPackageBeans);
	
}
	
	public void populateCargo(BLXLSBean blxlsBean) throws Exception{
		ArrayList<CargoBean> objmCargoBeans=new ArrayList<CargoBean>();
		String country="";
		//Package[] objmPackagesbean=blbean.getPackage();
		//Cargo[] objcagobean=blbean.getCargo();
		//Equipment[] objmEquipmentsbean=blbean.getEquipment();
		//Party[] objmPartybean=amsbean.getParty();

		CargoBean objmCargoBean=new CargoBean();
		objmCargoBean.setCargoEquipment(blxlsBean.getContainer());
		if(blxlsBean.getHsCode()==null)
			objmCargoBean.setHarmonizedCode("");
		else
			objmCargoBean.setHarmonizedCode(blxlsBean.getHsCode());
		if(blxlsBean.getDescription()==null)
			objmCargoBean.setGoodsDescription("");
		else
			objmCargoBean.setGoodsDescription(blxlsBean.getDescription().trim());

		objmCargoBean.setHazardCode("");


/*		for(Party partybean:objmPartybean){
			if(cargobean.getManufacturerIndex()==partybean.getIndex()){
				CustomerProfileBean objmCustomerProfileBean=new CustomerProfileBean();
				objmCustomerProfileBean=convertPARTYBeanToCustomerProfileBean(partybean, objmCustomerProfileBean);
				objCustomerProfileDAO.isExistXMLCustomer(objmCustomerProfileBean);
				if(objmCustomerProfileBean.getCustomerId()!=0){
					objmCargoBean.setManufacturer(objmCustomerProfileBean.getCustomerName());
					objmCargoBean.setManufacturerId(objmCustomerProfileBean.getCustomerId());
				}
			}
		}*/
		if(ShipperCustomerId!=0){
			
			objmCargoBean.setManufacturer(blxlsBean.getShipper());
			objmCargoBean.setManufacturerId(ShipperCustomerId);
		}else{
			objmCargoBean.setManufacturer("");
			objmCargoBean.setManufacturerId(0);
		}
		if(blxlsBean.getCountry()!=null){
			country = blxlsBean.getCountry().trim();
		}
		
		String countryCode = objCountryDAO.getCountryCode(country);
		objmCargoBean.setCargoCountry(countryCode);	
		objmCargoBeans.add(objmCargoBean);

		objBillDetailBean.setObjmCargoBean(objmCargoBeans);
	}	
	
	public BillDetailBean populateBillDetailBean(BLXLSBean blxlsBean) {

		/*	AMSNotify[] objmNotifybean=blbean.getAmsNotify();
		if(objmNotifybean!=null){
		for(AMSNotify amsNotifybean:objmNotifybean){
			NotifyBean objmNotifyBean=new NotifyBean();
			if(amsNotifybean.getScac()==null)
				objmNotifyBean.setScacCode("");
			else
				objmNotifyBean.setScacCode(amsNotifybean.getScac());
		objmNotifyBeans.add(objmNotifyBean);
		}
		objBillDetailBean.setObjmNotifyBean(objmNotifyBeans);*/
		if(blxlsBean.getStatus()!=null){
			if(blxlsBean.getStatus().equalsIgnoreCase("C")){
				objBillDetailBean.setBillStatus("COMPLETE");
			}else if(blxlsBean.getStatus().equalsIgnoreCase("D")){
				objBillDetailBean.setBillStatus("DELETED");
			}else{
				objBillDetailBean.setBillStatus("COMPLETE");
			}
		}else{
			objBillDetailBean.setBillStatus("COMPLETE");
		}
		//default value of billdetailbean
		objBillDetailBean.setReceiptPlace("");
		objBillDetailBean.setDeliveryPlace("");
		objBillDetailBean.setBillType("");
		objBillDetailBean.setMasterBill("");
		objBillDetailBean.setScacBill("");
		objBillDetailBean.setMoveType(blxlsBean.getMoveType());
		objBillDetailBean.setNvoType("NON NVO");

		//objBillDetailBean.setOriginCountry("");
		objBillDetailBean.setCreatedUser("admin");
		objBillDetailBean.setCarnetNumber("");
		objBillDetailBean.setCarnetCountry("");
		objBillDetailBean.setShipmentSubType("");
		objBillDetailBean.setEstimatedValue(1);
		objBillDetailBean.setEstimatedQuantity(2);
		objBillDetailBean.setUnitOfMeasure("");
		objBillDetailBean.setEstimatedWeight(25);
		objBillDetailBean.setWeightQualifier("");
		objBillDetailBean.setIsAmendment(false);
		objBillDetailBean.setIsIsfSent(false);
		objBillDetailBean.setIsValidAms(false);
		objBillDetailBean.setIsValidIsf(false);	
		objBillDetailBean.setAmsErrorDescription("");
		objBillDetailBean.setIsfErrorDescription("");
		objBillDetailBean.setShipmentType("01");
		objBillDetailBean.setTransmissionType("CT");
		
		return objBillDetailBean;
	}
	
}
