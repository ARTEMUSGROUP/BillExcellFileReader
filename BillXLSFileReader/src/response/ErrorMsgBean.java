package response;

public class ErrorMsgBean {
	
	String billLadingNumber;
	String errorMsg;
	String loadPort;
	String dischargePort;
	String hsCode;
	String country;
	
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHsCode() {
		return hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getBillLadingNumber() {
		return billLadingNumber;
	}

	public void setBillLadingNumber(String billLadingNumber) {
		this.billLadingNumber = billLadingNumber;
	}
	

}
