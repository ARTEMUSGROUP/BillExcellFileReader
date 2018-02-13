/**
 * 
 */
package beans.amsBeans;

/**
 * @author Vikas
 *
 */
public class EquipmentBean {
	String equipment;
	String sizeType;
	String sealDetails;
	String[] seal;
	public String getEquipment() {
		return equipment;
	}
	public String[] getSeal() {
		return seal;
	}
	public void setSeal(String[] seal) {
		this.seal = seal;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getSizeType() {
		return sizeType;
	}
	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}
	public String getSealDetails() {
		return sealDetails;
	}
	public void setSealDetails(String sealDetails) {
		this.sealDetails = sealDetails;
	}
}
