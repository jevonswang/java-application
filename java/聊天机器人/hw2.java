import java.io.*;
import java.util.*;



public class hw2 {  
	
	
    public static void main(String args[]) throws IOException {  
    	
    	Scanner sca=new Scanner(System.in);
    	dictionary d=new dictionary();
    	
    	
    	System.out.println("Please choose an option:");
    	System.out.println("      1.updateDB");
    	System.out.println("      2.query");
    	System.out.println("      3.exit");
    	
    	String ss = sca.next();
    	char ch=ss.charAt(0);
    	
    	while(ch!='3')
    	{
    		switch (ch)
    		{
    			case '1':
    				try{
    					
    					d.updateDic();
    				} catch(EOFException e) {}
    				catch(NoSuchElementException e) {}
    				 catch(IOException e){e.printStackTrace();} 
    				 catch(ClassNotFoundException e) {	e.printStackTrace();}
    				
    				break;
    			case '2':
    				try {
    					d.getDic();
    				} catch(EOFException e1){}
    				catch (IOException e2) {
    					e2.printStackTrace();
    				} catch (ClassNotFoundException e3)
    				{
    					e3.printStackTrace();
    				} 
    				
    				d.queryDic();
    				
    				break;
    			default:
    				System.out.println("Input error.Please input again.");
    				break;     
    		}
    		
        	System.out.println("Please choose an option:");
        	System.out.println("      1.updateDB");
        	System.out.println("      2.query");
        	System.out.println("      3.exit");
        	
        	String query = sca.next();
    		ch=query.charAt(0);
    	}
       
    	sca.close();
        
        
    	
    	/*
		try {
			d.getDic();
		} catch(EOFException e1){}
		catch (IOException e2) {
			e2.printStackTrace();
		} catch (ClassNotFoundException e3)
		{
			e3.printStackTrace();
		} 
    	
    	for(int i=0;i<6;i++)
    	{
    		System.out.println(d.list.get(i).keys);
    		System.out.println(d.list.get(i).answer+"\n");
    	}
    	*/
       
    }  
}  
    