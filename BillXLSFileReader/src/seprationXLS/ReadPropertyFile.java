package seprationXLS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadPropertyFile {
   String[] arrystr;
   FileReader filereader;
   BufferedReader br;
   int count=0;
   String path;
   String foldername;
   String dbname;
   
	public static void main(String[] args) throws IOException {
		ReadPropertyFile objReadPropertyFile=new ReadPropertyFile();
		objReadPropertyFile.readfile();
	}
    public void readfile()  {
    	try{
	    	File f = new File("general.property");
	    	XLSSepration objXLSSepration=new XLSSepration();
	    	filereader=new FileReader(f);
			br=new BufferedReader(filereader);
			String str;
			while((str=br.readLine())!=null){
				arrystr=str.split("=");
				if(count==0){
					path=arrystr[1];
				}else{
					foldername=arrystr[0];
					dbname=arrystr[1];
					objXLSSepration.XLSSeprationfile(path,foldername,dbname);
				}
				count++;
			}
    	}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				filereader.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
