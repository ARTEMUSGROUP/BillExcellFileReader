/**
 * 
 */
package beans.amsBeans;

import java.util.ArrayList;

/**
 * @author Rohit
 * @date 2 march 2011
 */
public class VoyageBean {
	private int voyageId;
	private String loginScac;
	private String voyage;
	private int vesselId;
	private String vesselName;
	private String scacCode;
	private String crewMembers;
	private String passengers;
	private String reportNumber;
	private String createdUser;
	private String createdDate;
	private ArrayList<PortDetailsBean> objmPortDetailsBeans;
	
	
	/**
	 * @return the objmPortDetailsBeans
	 */
	public ArrayList<PortDetailsBean> getObjmPortDetailsBeans() {
		return objmPortDetailsBeans;
	}
	/**
	 * @param objmPortDetailsBeans the objmPortDetailsBeans to set
	 */
	public void setObjmPortDetailsBeans(
			ArrayList<PortDetailsBean> objmPortDetailsBeans) {
		this.objmPortDetailsBeans = objmPortDetailsBeans;
	}
	/**
	 * @return the voyageId
	 */
	public int getVoyageId() {
		return voyageId;
	}
	/**
	 * @param voyageId the voyageId to set
	 */
	public void setVoyageId(int voyageId) {
		this.voyageId = voyageId;
	}
	public String getLoginScac() {
		return loginScac;
	}
	/**
	 * @param loginScac the loginScac to set
	 */
	public void setLoginScac(String loginScac) {
		this.loginScac = loginScac;
	}
	/**
	 * @return the voyage
	 */
	public String getVoyage() {
		return voyage;
	}
	/**
	 * @param voyage the voyage to set
	 */
	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	/**
	 * @return the scacCode
	 */
	public String getScacCode() {
		return scacCode;
	}
	public int getVesselId() {
		return vesselId;
	}
	public void setVesselId(int vesselId) {
		this.vesselId = vesselId;
	}
	public String getVesselName() {
		return vesselName;
	}
	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}
	/**
	 * @param scacCode the scacCode to set
	 */
	public void setScacCode(String scacCode) {
		this.scacCode = scacCode;
	}
	/**
	 * @return the crewMembers
	 */
	public String getCrewMembers() {
		return crewMembers;
	}
	/**
	 * @param crewMembers the crewMembers to set
	 */
	public void setCrewMembers(String crewMembers) {
		this.crewMembers = crewMembers;
	}
	/**
	 * @return the passengers
	 */
	public String getPassengers() {
		return passengers;
	}
	/**
	 * @param passengers the passengers to set
	 */
	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}
	/**
	 * @return the reportNumber
	 */
	public String getReportNumber() {
		return reportNumber;
	}
	/**
	 * @param reportNumber the reportNumber to set
	 */
	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}
	/**
	 * @return the createdUser
	 */
	public String getCreatedUser() {
		return createdUser;
	}
	/**
	 * @param createdUser the createdUser to set
	 */
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}
