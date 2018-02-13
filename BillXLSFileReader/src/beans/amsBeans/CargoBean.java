/**
 * 
 */
package beans.amsBeans;

/**
 * @author Vikas
 *
 */
public class CargoBean {
	String cargoEquipment;
	String goodsDescription;
	String harmonizedCode;
	String hazardCode;
	String manufacturer;
	String cargoCountry;
	int manufacturerId;
	
	public int getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public String getCargoEquipment() {
		return cargoEquipment;
	}
	public void setCargoEquipment(String cargoEquipment) {
		this.cargoEquipment = cargoEquipment;
	}
	public String getGoodsDescription() {
		return goodsDescription;
	}
	public void setGoodsDescription(String goodsDescription) {
		this.goodsDescription = goodsDescription.replaceAll("\\s", " ");
	}
	public String getHarmonizedCode() {
		return harmonizedCode;
	}
	public void setHarmonizedCode(String harmonizedCode) {
		this.harmonizedCode = harmonizedCode;
	}
	public String getHazardCode() {
		return hazardCode;
	}
	public void setHazardCode(String hazardCode) {
		this.hazardCode = hazardCode;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getCargoCountry() {
		return cargoCountry;
	}
	public void setCargoCountry(String cargoCountry) {
		this.cargoCountry = cargoCountry;
	}
}
