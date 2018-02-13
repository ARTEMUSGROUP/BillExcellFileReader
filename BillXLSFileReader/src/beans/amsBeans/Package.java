package beans.amsBeans;

import java.io.Serializable;

public class Package implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int index;
	String pieceCount;
	String packages;
	int equipmentIndex;
	String Marks;
	Attribute[] attribute;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getPieceCount() {
		return pieceCount;
	}
	public void setPieceCount(String pieceCount) {
		this.pieceCount = pieceCount;
	}
	public String getPackages() {
		return packages;
	}
	public void setPackages(String packages) {
		this.packages = packages;
	}
	public int getEquipmentIndex() {
		return equipmentIndex;
	}
	public void setEquipmentIndex(int equipmentIndex) {
		this.equipmentIndex = equipmentIndex;
	}
	public String getMarks() {
		return Marks;
	}
	public void setMarks(String marks) {
		Marks = marks;
	}
	public Attribute[] getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute[] attribute) {
		this.attribute = attribute;
	}
	

}
