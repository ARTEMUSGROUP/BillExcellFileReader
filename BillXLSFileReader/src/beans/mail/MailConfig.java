package beans.mail;

import java.io.Serializable;

public class MailConfig implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String host;
	 private String port;
	 private String from;
	 private String password;
	 private String[] to;
	 
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = to;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
