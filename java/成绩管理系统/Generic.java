import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;


class Record{
	public Record(){};
	
	String name ;
	String course ;
	int score;
}

class Generic{
	public Record phaseToRecord(String str)
	{
		Record newrcd = new Record();
		
		int comma1,comma2;
		comma1 = str.indexOf(',');
		//if(comma1==-1);
		newrcd.name = str.substring(0, comma1);
		comma2 = str.indexOf(',',comma1+1);
		newrcd.course = str.substring(comma1+1, comma2);
		str = str.substring(comma2+1);
		newrcd.score = Integer.parseInt(str);
		
		return newrcd;
	}
	
	public Vector<Record> loadDB(String fileName){ 
		 Vector<Record> gradebook = new Vector<Record>();
		File file = new File(fileName); 
		BufferedReader reader = null; 
		try{
			reader = new BufferedReader(new FileReader(file)); 
			String temp = null;
			Record newRecord = null;
			while ((temp = reader.readLine()) != null){ 
				newRecord = phaseToRecord(temp);
				gradebook.add(newRecord);
			} 	
			reader.close(); 
		}catch(IOException e){
			e.printStackTrace(); 
		}
		return gradebook;
	}
	
	public void storeDB(String fileName,Vector<Record> gradebook){
		FileWriter fw = null;  
        try {
			fw = new FileWriter(fileName);

            for (Record x : gradebook) { 
        	fw.write(x.name+","+x.course+","+String.valueOf(x.score)+"\r\n");   
        } 
         
        fw.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}
	
}
