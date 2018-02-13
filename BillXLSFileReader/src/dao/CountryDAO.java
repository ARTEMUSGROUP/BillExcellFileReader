/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.amsBeans.CountryBean;
import connectionfactory.DBConnectionFactory;

/**
 * @author Rohit
 * @date 28 feb 2011
 */ 
public class CountryDAO {

	private Connection con;
	private Statement stmt = null;
	public CountryDAO() {
		getConnection();
	}
	public void getConnection() {
		try {
			con = new DBConnectionFactory().getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeAll(){
		try {
			if (stmt!=null){
				stmt.close();
			}
			if(con!=null){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
/**
 * @author Rohit
 * @date 28 feb 2011
 * @return objmList
 * @description This method is for the retriving the Country list 
**/
	public ArrayList<CountryBean> getList() {
		ArrayList<CountryBean> objmList=null;
		ResultSet rs=null;
		try {
			objmList = new ArrayList<CountryBean>();
			CountryBean objmCountryBean;
			stmt=con.createStatement();
			String countryQuery="Select iso_code, country_name from country order by country_name";
			rs = stmt.executeQuery(countryQuery);
			while(rs.next()){
				objmCountryBean =new CountryBean();
				objmCountryBean.setIsoCode(rs.getString(1));
				objmCountryBean.setCountryName(rs.getString(2));
				objmList.add(objmCountryBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objmList;
	}
	
	public CountryBean getData(String countryCode){
		CountryBean objmCountryBean = new CountryBean();
		ResultSet rs=null;
		try{
			stmt=con.createStatement();
			String CountryQuery="Select iso_code, country_name from country where iso_code='"+countryCode+"' order by country_name";
			rs=stmt.executeQuery(CountryQuery);
			if(rs.next()){
				objmCountryBean.setIsoCode(rs.getString(1));
				objmCountryBean.setCountryName(rs.getString(2));
			}else{
				objmCountryBean.setIsoCode("");
				objmCountryBean.setCountryName("");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return objmCountryBean;
	}
	
	public boolean isCountryExist(String countryName) {
		ResultSet rs=null;
		Boolean result=true;
		try{
			stmt = con.createStatement();
			String isExistQuery="Select iso_code, country_name from country where country_name='"+countryName+"' order by country_name";
			rs=stmt.executeQuery(isExistQuery);
			if (rs.next()){
				result=true;
			}else{
				result=false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public String getCountryCode(String country) {
		ResultSet rs=null;
		String countryCode="";
		try {
			stmt=con.createStatement();
			String getCountryCodeQuery = "select iso_code from country where country_name='"+country+"'";
			rs=stmt.executeQuery(getCountryCodeQuery);
			if(rs.next())
				countryCode=rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countryCode;
	}
}
