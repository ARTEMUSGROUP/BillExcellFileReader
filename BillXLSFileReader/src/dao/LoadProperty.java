package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
public class LoadProperty {
	public Properties loadProperty() throws Exception{
		File f = new File("emailList.property");
		FileInputStream fis = new FileInputStream(f);
		Properties fileProp = new Properties();
		fileProp.load(fis);
		fis.close();
		return fileProp;
	}
	public void moveFile(File objmFile,String destPath){

		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(objmFile);
			FileOutputStream fos = new FileOutputStream(destPath);
			int length = (int) objmFile.length();
			buffer = new byte [length];

			fis.read(buffer);
			fis.close();

			fos.write(buffer);
			fos.close();
			objmFile.delete();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
}
