import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

class Parser{
	
	HashMap<String, Integer> pageCounts = new HashMap<String, Integer>();  
	HashMap<String, Integer> ipCounts = new HashMap<String, Integer>();
	HashMap<String, Integer> dayCounts = new HashMap<String, Integer>();
	HashMap<String, Integer> weekCounts = new HashMap<String, Integer>();
	HashMap<String, Integer> hourCounts = new HashMap<String, Integer>();
	
	boolean isBroken = false;
	
	long dataDelivered = 0;
	
	
	String findmax(HashMap<String, Integer> map)
	{
		int max = -1;
		String maxstring = "";
		
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
	       String key =  (String)iter.next();
	       int value = map.get(key);
	       if(value>max){
	    	   maxstring = key;
	    	   max = value;
	       }
		}
		
		
		return maxstring;
	}
	
	
	
	void addIP(String ip){
		 if(ipCounts.containsKey(ip)){
			 int count = ipCounts.get(ip);
			 ipCounts.put(ip, ++count);
		 }
		 else
			 ipCounts.put(ip, 1);
	}
	
	void addPage(String page){
		 if(pageCounts.containsKey(page)){
			 int count = pageCounts.get(page);
			 pageCounts.put(page, ++count);
		 }
		 else
			 pageCounts.put(page, 1);
	}
	
	void addDay(String day){
		 if(dayCounts.containsKey(day)){
			 int count = dayCounts.get(day);
			 dayCounts.put(day, ++count);
		 }
		 else
			 dayCounts.put(day, 1);
	}
	
	void addWeek(String week){
		 if(weekCounts.containsKey(week)){
			 int count = weekCounts.get(week);
			 weekCounts.put(week, ++count);
		 }
		 else
			 weekCounts.put(week, 1);
	}
	
	void addHour(String hour){
		 if(hourCounts.containsKey(hour)){
			 int count = hourCounts.get(hour);
			 hourCounts.put(hour, ++count);
		 }
		 else
			 hourCounts.put(hour, 1);
	}
	
	String CalWeek(String year,String month,String day) {
		
		
		int y = 0;
		int d = 0;
		int m = 0;
		
		try{
		   y = Integer.parseInt(year);
		   d = Integer.parseInt(day);
		}catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		

		
		switch(month){
			case "Jan":m = 1;break;
			case "Feb":m = 2;break;
			case "Mar":m = 3;break;
			case "Apr":m = 4;break;
			case "May":m = 5;break;
			case "Jun":m = 6;break;
			case "Jul":m = 7;break;
			case "Aug":m = 8;break;
			case "Sep":m = 9;break;
			case "Oct":m = 10;break;
			case "Nov":m = 11;break;
			case "Dec":m = 12;break;
		
		}
		
		
        if(m==1) {m=13;y--;}
        if(m==2) {m=14;y--;}
        int week=(d+2*m+3*(m+1)/5+y+y/4-y/100+y/400)%7; 
        String weekstr="";
        switch(week)
        {
            case 0: weekstr="Monday"; break;
            case 1: weekstr="Tuesday"; break;
            case 2: weekstr="Wednesday"; break;
            case 3: weekstr="Thursday"; break;
            case 4: weekstr="Friday"; break;
            case 5: weekstr="Saturday"; break;
            case 6: weekstr="Sunday"; break;
         }
		
		return weekstr;
	}
	
	 void analyse(String line) {
		// TODO Auto-generated method stub
		 int pos1,pos2;
		 
		 
		 String ip = "";
		 pos1 = line.indexOf(" ");
		 ip = line.substring(0, pos1);


	
		// [18/Mar/2012:17:37:24 +0800]
		 
		 pos1 = line.indexOf("[");
		 String day = line.substring(pos1+1, pos1+3);
		 String month = line.substring(pos1+4,pos1+7);
		 String year = line.substring(pos1+8,pos1+12);
		 String hour = line.substring(pos1+13,pos1+15);
		 String week = CalWeek(year,month,day);
		 
		 addDay(day); 
		 addHour(hour);
		 addWeek(week);
		 
		 pos1 = line.indexOf("\"");
		 pos2 = line.indexOf(" ",pos1);
		 String method = line.substring(pos1+1,pos2);
		 
		 pos1 = line.indexOf(" ",pos2+1);
		 String page = line.substring(pos2+1,pos1);
		 
		 addPage(page);
		 
		 pos2 = line.indexOf("\"",pos1);
		 pos1 = line.indexOf(" ",pos2+2);
		 String statue = line.substring(pos2+2,pos1);
		 String data = line.substring(pos1+1);
		 
		 
		 if(method.equals("GET")) 
			 addIP(ip);
		 
		 //&&  (statue.charAt(0)=='2') 
		 if(  (  method.equals("PUT") || method.equals("POST")  )  &&  (statue.charAt(0)=='2')  && !data.equals("-"))
			 dataDelivered += Integer.parseInt(data);
		 
		 if(statue.charAt(0)=='5') isBroken = true;
		 
		 /*
		 //test
		 System.out.println("IP is "+ip);
		 System.out.println("Year is "+year);
		 System.out.println("Month is "+month);
		 System.out.println("Day is "+day);
		 System.out.println("Hour is "+hour);
		 System.out.println("Method is "+method);
		 System.out.println("page is "+page);
		 System.out.println("Statue is "+statue);
		 System.out.println("Data is "+data);
		 */
		 
		  
	}
	 
	 
	 
	 void printResult()
	 {
		 //System.out.println(pageCounts);
		// System.out.println(ipCounts);
		// System.out.println(dayCounts);
		// System.out.println(weekCounts);
		// System.out.println(hourCounts);

		 
		 
		 try {
			 FileWriter fw = new FileWriter("result.txt");
			fw.write("The weblog has been analysed successfully and the results are as follows:\r\n");
			fw.write(findmax(pageCounts)+" is the most popular pages they provide.\r\n");
			fw.write(findmax(ipCounts)+" took the most pages from the site.\r\n");
		 
		    if(isBroken)
			   fw.write("Other sites appeared to have broken links to this site¡¯s pages.\r\n");
		    else
			   fw.write("Other sites didn't appear to have broken links to this site¡¯s pages.\r\n");
		 	fw.write(dataDelivered+" bytes are been delivered to clients\r\n");
		    fw.write("Over a day, "+findmax(hourCounts)+" o'clock is the busiest period.\r\n");
		    fw.write("Over a week, "+findmax(weekCounts)+" is the busiest period.\r\n");
		    fw.write("Over a month, "+findmax(dayCounts)+" is the busiest day.\r\n");
		    fw.close();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
}


public class LogAnalyzer {


	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		try { 
			FileReader fr=new FileReader("access.log");
	        BufferedReader br=new BufferedReader(fr);
	        String line="";
	        Parser parser = new Parser();
	        while ((line=br.readLine())!=null) {	        
	       // line=br.readLine();
	        parser.analyse(line);
	        }
	        parser.printResult();
	        br.close();
	        fr.close();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

}


