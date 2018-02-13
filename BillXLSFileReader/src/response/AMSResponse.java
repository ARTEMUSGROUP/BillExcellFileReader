package response;

import java.io.Serializable;

public class AMSResponse implements Serializable{

	/**
	 * **
	 */
	private static final long serialVersionUID = 1L;
	Response response[];
	public Response[] getResponse() {
		return response;
	}
	public void setResponse(Response[] response) {
		this.response = response;
	}
	/*public String toXML() {
		return MigrateVoyageDetails.bean2Xml(this);
	
	}*/


}
