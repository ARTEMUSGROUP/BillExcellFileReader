package seprationXLS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import migrate.MigrateBLXLS;
import dao.LoadProperty;


public class XLSSepration extends LoadProperty {
	public void XLSSeprationfile(String path, String foldername,String dbname){
		String mainpath=path+foldername+"/";
		try {
			File objmDir=new File(mainpath);
			File[] f1=objmDir.listFiles();
			for (File file : f1) {
				if(!file.isDirectory())
					differntiateXml(file,mainpath,dbname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void differntiateXml(File f1, String mainpath,String dbname){
		FileReader inf=null;
		BufferedReader inbr=null;

		try{
			System.out.println("FileName:"+f1.getName());
			/*inf= new FileReader(f1);
			inbr= new BufferedReader(inf);
			String firstLine;
			StringBuffer objmStringBuffer=new StringBuffer("");
			while((firstLine=inbr.readLine())!= null){	
				firstLine=firstLine.trim();
				objmStringBuffer.append(firstLine);
			}
			if(inf!=null)
				inf.close();
			if(inbr!=null)
				inbr.close();*/
	
			MigrateBLXLS objMigrateBLXLS=new  MigrateBLXLS();
			//String xmlstr=objFormateXml.format(f2);
			objMigrateBLXLS.readfile(f1,mainpath,dbname);
			System.out.print("Migration sucessfully");
	 
	}catch (Exception e) {
		// TODO: handle exception
	}
}
}
