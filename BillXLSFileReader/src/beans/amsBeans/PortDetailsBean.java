/**
 * 
 */
package beans.amsBeans;

/**
 * @author Rohit
 * @date 2 march 2011
 */
public class PortDetailsBean {
	private int locationId;
	private Boolean lastLoadPort;
	private String terminal;
	private String sailingDate;
	private String arrivalDate;
	private Boolean load;
	private Boolean isAmsSent;
	private Boolean discharge;
	private String location;
	
	/**
	 * @return the locationId
	 */
	public int getLocationId() {
		return locationId;
	}
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public Boolean getLastLoadPort() {
		return lastLoadPort;
	}
	/**
	 * @param lastLoadPort the lastLoadPort to set
	 */
	public void setLastLoadPort(Boolean lastLoadPort) {
		this.lastLoadPort = lastLoadPort;
	}
	/**
	 * @return the terminal
	 */
	public String getTerminal() {
		return terminal;
	}
	/**
	 * @param terminal the terminal to set
	 */
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	/**
	 * @return the sailingDate
	 */
	public String getSailingDate() {
		return sailingDate;
	}
	/**
	 * @param sailingDate the sailingDate to set
	 */
	public void setSailingDate(String sailingDate) {
		this.sailingDate = sailingDate;
	}
	/**
	 * @return the arrivalDate
	 */
	public String getArrivalDate() {
		return arrivalDate;
	}
	/**
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	/**
	 * @return the load
	 */
	public Boolean getLoad() {
		return load;
	}
	/**
	 * @param load the load to set
	 */
	public void setLoad(Boolean load) {
		this.load = load;
	}
	/**
	 * @return the isAmsSent
	 */
	public Boolean getIsAmsSent() {
		return isAmsSent;
	}
	/**
	 * @param isAmsSent the isAmsSent to set
	 */
	public void setIsAmsSent(Boolean isAmsSent) {
		this.isAmsSent = isAmsSent;
	}
	/**
	 * @return the discharge
	 */
	public Boolean getDischarge() {
		return discharge;
	}
	/**
	 * @param discharge the discharge to set
	 */
	public void setDischarge(Boolean discharge) {
		this.discharge = discharge;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
}
