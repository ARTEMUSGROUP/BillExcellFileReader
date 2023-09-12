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
	 
	private String smtp_username;
	private String smtp_password;
	
	public String getSmtp_username() {
		return smtp_username;
	}
	/**
	 * @param smtp_username the smtp_username to set
	 */
	public void setSmtp_username(String smtp_username) {
		this.smtp_username = smtp_username;
	}
	/**
	 * @return the smtp_password
	 */
	public String getSmtp_password() {
		return smtp_password;
	}
	/**
	 * @param smtp_password the smtp_password to set
	 */
	public void setSmtp_password(String smtp_password) {
		this.smtp_password = smtp_password;
	}
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
