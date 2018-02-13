package response;

public class Response{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String source;
	String element;
	String code;
	String description;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	/*public String toXML() {
		return MigrateVoyageDetails.bean2Xml(this);
	
	}*/

	

	
}
