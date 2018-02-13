/**
 * 
 */
package beans.amsBeans;

import java.util.ArrayList;

/**
 * @author Vikas
 *
 */
public class BillDetailBean {
	String loginScac;
	int billLadingId;
	int voyageId;
	String billLadingNumber;
	String billStatus;
	String billType;
	String hblScac;
	String nvoType;
	String nvoBill;
	String scacBill;
	String masterBill;
	String vesselName;
	String originCountry;
	String receiptPlace;
	String loadPort;
	String dischargePort;
	String deliveryPlace;
	String moveType;
	String shipmentType;
	String transmissionType;
	String sizeType;
	String carrierScac;
	String createdUser;
	String createdDate;
	String shipperName;
	String shipperAddress;
	String bookingPartyName;
	String bookingPartyAddress;
	String sellerName;
	String sellerAddress;
	String consolidatorName;
	String consolidatorAddress;
	String stufferName;
	String stufferAddress;
	Boolean isBookingParty;
	Boolean isSeller;
	Boolean isConsolidator;
	Boolean isStuffer;
	Boolean isImporter;
	Boolean isBuyer;
	Boolean isNotify;
	Boolean isShipTo;
	String consigneeName;
	String consigneeAddress;
	String importerName;
	String importerAddress;
	String buyerName;
	String buyerAddress;
	String notifyName;
	String notifyAddress;
	String shipToName;
	String shipToAddress;

	Boolean isValidAms;
	Boolean isValidIsf;
	
	Boolean isAmsSent;
	Boolean isIsfSent;
	String amsErrorDescription;
	String isfErrorDescription;
	Boolean isAmendment;
	Boolean isReadonly;
	String voyageNumber;
	
	ArrayList<NotifyBean> objmNotifyBean;
	ArrayList<EquipmentBean> objmEquipmentBean;
	ArrayList<PackageBean> objmPackageBean;
	ArrayList<CargoBean> objmCargoBean;
	
	String actionCode;
	String amendmentCode;
	String isSplitBill;
	String splitBillNumber;
	String carnetNumber;
	String carnetCountry;
	String shipmentSubType;
	int estimatedValue;
	int estimatedQuantity;
	String unitOfMeasure;
	int estimatedWeight;
	String weightQualifier;
	String[] stuffersName;
	String[] stuffersAddress;
	String[] consolidatorsName;
	String[] consolidatorsAddress;
	int[] stuffersId;
	int[] consolidatorsId;
	
	int shipperId;
	int bookingPartyId;
	int sellerId;
	int consolidatorId;
	int stufferId;
	
	int consigneeId;
	int importerId;
	int buyerId;
	int notifyId;
	int shipToId;
	
	
	public int getShipperId() {
		return shipperId;
	}
	public void setShipperId(int shipperId) {
		this.shipperId = shipperId;
	}
	public int getBookingPartyId() {
		return bookingPartyId;
	}
	public void setBookingPartyId(int bookingPartyId) {
		this.bookingPartyId = bookingPartyId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getConsolidatorId() {
		return consolidatorId;
	}
	public void setConsolidatorId(int consolidatorId) {
		this.consolidatorId = consolidatorId;
	}
	public int getStufferId() {
		return stufferId;
	}
	public void setStufferId(int stufferId) {
		this.stufferId = stufferId;
	}
	public int getConsigneeId() {
		return consigneeId;
	}
	public void setConsigneeId(int consigneeId) {
		this.consigneeId = consigneeId;
	}
	public int getImporterId() {
		return importerId;
	}
	public void setImporterId(int importerId) {
		this.importerId = importerId;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public int getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(int notifyId) {
		this.notifyId = notifyId;
	}
	public int getShipToId() {
		return shipToId;
	}
	public void setShipToId(int shipToId) {
		this.shipToId = shipToId;
	}
	public int[] getStuffersId() {
		return stuffersId;
	}
	public void setStuffersId(int[] stuffersId) {
		this.stuffersId = stuffersId;
	}
	public int[] getConsolidatorsId() {
		return consolidatorsId;
	}
	public void setConsolidatorsId(int[] consolidatorsId) {
		this.consolidatorsId = consolidatorsId;
	}
	public String[] getStuffersName() {
		return stuffersName;
	}
	public void setStuffersName(String[] stuffersName) {
		this.stuffersName = stuffersName;
	}
	public String[] getStuffersAddress() {
		return stuffersAddress;
	}
	public void setStuffersAddress(String[] stuffersAddress) {
		this.stuffersAddress = stuffersAddress;
	}
	public String[] getConsolidatorsName() {
		return consolidatorsName;
	}
	public void setConsolidatorsName(String[] consolidatorsName) {
		this.consolidatorsName = consolidatorsName;
	}
	public String[] getConsolidatorsAddress() {
		return consolidatorsAddress;
	}
	public void setConsolidatorsAddress(String[] consolidatorsAddress) {
		this.consolidatorsAddress = consolidatorsAddress;
	}
	public int getEstimatedValue() {
		return estimatedValue;
	}
	public void setEstimatedValue(int estimatedValue) {
		this.estimatedValue = estimatedValue;
	}
	public int getEstimatedQuantity() {
		return estimatedQuantity;
	}
	public void setEstimatedQuantity(int estimatedQuantity) {
		this.estimatedQuantity = estimatedQuantity;
	}
	public int getEstimatedWeight() {
		return estimatedWeight;
	}
	public void setEstimatedWeight(int estimatedWeight) {
		this.estimatedWeight = estimatedWeight;
	}
	public String getShipmentSubType() {
		return shipmentSubType;
	}
	public void setShipmentSubType(String shipmentSubType) {
		this.shipmentSubType = shipmentSubType;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public String getWeightQualifier() {
		return weightQualifier;
	}
	public void setWeightQualifier(String weightQualifier) {
		this.weightQualifier = weightQualifier;
	}
	public String getCarnetNumber() {
		return carnetNumber;
	}
	public void setCarnetNumber(String carnetNumber) {
		this.carnetNumber = carnetNumber;
	}
	public String getCarnetCountry() {
		return carnetCountry;
	}
	public void setCarnetCountry(String carnetCountry) {
		this.carnetCountry = carnetCountry;
	}
	public String getTransmissionType() {
		return transmissionType;
	}
	public void setTransmissionType(String transmissionType) {
		this.transmissionType = transmissionType;
	}
	public String getShipmentType() {
		return shipmentType;
	}
	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}
	public String getIsSplitBill() {
		return isSplitBill;
	}
	public void setIsSplitBill(String isSplitBill) {
		this.isSplitBill = isSplitBill;
	}
	public String getSplitBillNumber() {
		return splitBillNumber;
	}
	public void setSplitBillNumber(String splitBillNumber) {
		this.splitBillNumber = splitBillNumber;
	}
	/**
	 * @return the voyageNumber
	 */
	public String getVoyageNumber() {
		return voyageNumber;
	}
	/**
	 * @param voyageNumber the voyageNumber to set
	 */
	public void setVoyageNumber(String voyageNumber) {
		this.voyageNumber = voyageNumber;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getAmendmentCode() {
		return amendmentCode;
	}
	public void setAmendmentCode(String amendmentCode) {
		this.amendmentCode = amendmentCode;
	}
	public Boolean getIsReadonly() {
		return isReadonly;
	}
	public void setIsReadonly(Boolean isReadonly) {
		this.isReadonly = isReadonly;
	}
	public String getLoginScac() {
		return loginScac;
	}
	public void setLoginScac(String loginScac) {
		this.loginScac = loginScac;
	}
	public int getBillLadingId() {
		return billLadingId;
	}
	public void setBillLadingId(int billLadingId) {
		this.billLadingId = billLadingId;
	}
	public int getVoyageId() {
		return voyageId;
	}
	public void setVoyageId(int voyageId) {
		this.voyageId = voyageId;
	}
	public String getBillLadingNumber() {
		return billLadingNumber;
	}
	public void setBillLadingNumber(String billLadingNumber) {
		this.billLadingNumber = billLadingNumber;
	}
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getHblScac() {
		return hblScac;
	}
	public void setHblScac(String hblScac) {
		this.hblScac = hblScac;
	}
	public String getNvoType() {
		return nvoType;
	}
	public void setNvoType(String nvoType) {
		this.nvoType = nvoType;
	}
	public String getNvoBill() {
		return nvoBill;
	}
	public void setNvoBill(String nvoBill) {
		this.nvoBill = nvoBill;
	}
	public String getScacBill() {
		return scacBill;
	}
	public void setScacBill(String scacBill) {
		this.scacBill = scacBill;
	}
	public String getMasterBill() {
		return masterBill;
	}
	public void setMasterBill(String masterBill) {
		this.masterBill = masterBill;
	}
	public String getVesselName() {
		return vesselName;
	}
	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}
	public String getOriginCountry() {
		return originCountry;
	}
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}
	public String getReceiptPlace() {
		return receiptPlace;
	}
	public void setReceiptPlace(String receiptPlace) {
		this.receiptPlace = receiptPlace;
	}
	public String getLoadPort() {
		return loadPort;
	}
	public void setLoadPort(String loadPort) {
		this.loadPort = loadPort;
	}
	public String getDischargePort() {
		return dischargePort;
	}
	public void setDischargePort(String dischargePort) {
		this.dischargePort = dischargePort;
	}
	public String getDeliveryPlace() {
		return deliveryPlace;
	}
	public void setDeliveryPlace(String deliveryPlace) {
		this.deliveryPlace = deliveryPlace;
	}
	public String getMoveType() {
		return moveType;
	}
	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}
	public String getSizeType() {
		return sizeType;
	}
	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}
	public String getCarrierScac() {
		return carrierScac;
	}
	public void setCarrierScac(String carrierScac) {
		this.carrierScac = carrierScac;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public String getShipperAddress() {
		return shipperAddress;
	}
	public void setShipperAddress(String shipperAddress) {
		this.shipperAddress = shipperAddress;
	}
	public String getBookingPartyName() {
		return bookingPartyName;
	}
	public void setBookingPartyName(String bookingPartyName) {
		this.bookingPartyName = bookingPartyName;
	}
	public String getBookingPartyAddress() {
		return bookingPartyAddress;
	}
	public void setBookingPartyAddress(String bookingPartyAddress) {
		this.bookingPartyAddress = bookingPartyAddress;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerAddress() {
		return sellerAddress;
	}
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}
	public String getConsolidatorName() {
		return consolidatorName;
	}
	public void setConsolidatorName(String consolidatorName) {
		this.consolidatorName = consolidatorName;
	}
	public String getConsolidatorAddress() {
		return consolidatorAddress;
	}
	public void setConsolidatorAddress(String consolidatorAddress) {
		this.consolidatorAddress = consolidatorAddress;
	}
	public String getStufferName() {
		return stufferName;
	}
	public void setStufferName(String stufferName) {
		this.stufferName = stufferName;
	}
	public String getStufferAddress() {
		return stufferAddress;
	}
	public void setStufferAddress(String stufferAddress) {
		this.stufferAddress = stufferAddress;
	}
	public Boolean getIsBookingParty() {
		return isBookingParty;
	}
	public void setIsBookingParty(Boolean isBookingParty) {
		this.isBookingParty = isBookingParty;
	}
	public Boolean getIsSeller() {
		return isSeller;
	}
	public void setIsSeller(Boolean isSeller) {
		this.isSeller = isSeller;
	}
	public Boolean getIsConsolidator() {
		return isConsolidator;
	}
	public void setIsConsolidator(Boolean isConsolidator) {
		this.isConsolidator = isConsolidator;
	}
	public Boolean getIsStuffer() {
		return isStuffer;
	}
	public void setIsStuffer(Boolean isStuffer) {
		this.isStuffer = isStuffer;
	}
	public Boolean getIsImporter() {
		return isImporter;
	}
	public void setIsImporter(Boolean isImporter) {
		this.isImporter = isImporter;
	}
	public Boolean getIsBuyer() {
		return isBuyer;
	}
	public void setIsBuyer(Boolean isBuyer) {
		this.isBuyer = isBuyer;
	}
	public Boolean getIsNotify() {
		return isNotify;
	}
	public void setIsNotify(Boolean isNotify) {
		this.isNotify = isNotify;
	}
	public Boolean getIsShipTo() {
		return isShipTo;
	}
	public void setIsShipTo(Boolean isShipTo) {
		this.isShipTo = isShipTo;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getImporterName() {
		return importerName;
	}
	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}
	public String getImporterAddress() {
		return importerAddress;
	}
	public void setImporterAddress(String importerAddress) {
		this.importerAddress = importerAddress;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerAddress() {
		return buyerAddress;
	}
	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}
	public String getNotifyName() {
		return notifyName;
	}
	public void setNotifyName(String notifyName) {
		this.notifyName = notifyName;
	}
	public String getNotifyAddress() {
		return notifyAddress;
	}
	public void setNotifyAddress(String notifyAddress) {
		this.notifyAddress = notifyAddress;
	}
	public String getShipToName() {
		return shipToName;
	}
	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}
	public String getShipToAddress() {
		return shipToAddress;
	}
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	public Boolean getIsValidAms() {
		return isValidAms;
	}
	public void setIsValidAms(Boolean isValidAms) {
		this.isValidAms = isValidAms;
	}
	public Boolean getIsValidIsf() {
		return isValidIsf;
	}
	public void setIsValidIsf(Boolean isValidIsf) {
		this.isValidIsf = isValidIsf;
	}
	public Boolean getIsAmsSent() {
		return isAmsSent;
	}
	public void setIsAmsSent(Boolean isAmsSent) {
		this.isAmsSent = isAmsSent;
	}
	public Boolean getIsIsfSent() {
		return isIsfSent;
	}
	public void setIsIsfSent(Boolean isIsfSent) {
		this.isIsfSent = isIsfSent;
	}
	public String getAmsErrorDescription() {
		return amsErrorDescription;
	}
	public void setAmsErrorDescription(String amsErrorDescription) {
		this.amsErrorDescription = amsErrorDescription;
	}
	public String getIsfErrorDescription() {
		return isfErrorDescription;
	}
	public void setIsfErrorDescription(String isfErrorDescription) {
		this.isfErrorDescription = isfErrorDescription;
	}
	public Boolean getIsAmendment() {
		return isAmendment;
	}
	public void setIsAmendment(Boolean isAmendment) {
		this.isAmendment = isAmendment;
	}
	public ArrayList<NotifyBean> getObjmNotifyBean() {
		return objmNotifyBean;
	}
	public void setObjmNotifyBean(ArrayList<NotifyBean> objmNotifyBean) {
		this.objmNotifyBean = objmNotifyBean;
	}
	public ArrayList<EquipmentBean> getObjmEquipmentBean() {
		return objmEquipmentBean;
	}
	public void setObjmEquipmentBean(ArrayList<EquipmentBean> objmEquipmentBean) {
		this.objmEquipmentBean = objmEquipmentBean;
	}
	public ArrayList<PackageBean> getObjmPackageBean() {
		return objmPackageBean;
	}
	public void setObjmPackageBean(ArrayList<PackageBean> objmPackageBean) {
		this.objmPackageBean = objmPackageBean;
	}
	public ArrayList<CargoBean> getObjmCargoBean() {
		return objmCargoBean;
	}
	public void setObjmCargoBean(ArrayList<CargoBean> objmCargoBean) {
		this.objmCargoBean = objmCargoBean;
	}
	
	
}