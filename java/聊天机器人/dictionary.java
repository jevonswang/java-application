import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class dictionary {
	List<entry> list = new ArrayList<entry>();
	
    void updateDic() throws IOException, ClassNotFoundException,EOFException
    {
    	
		try{
			getDic();
		} catch(EOFException e) {}
        
    	
    	Scanner sca=new Scanner(System.in);
    	
    	
    	
    	System.out.println("Please input the new entry:");
    	
    	entry temp = new entry();	
    	String ss=sca.nextLine();
    	String[] array=ss.split(" ");
    		
    	for (String x : array) { 
    			 temp.keys.add(x);
            } 
	 
    	 temp.answer = sca.nextLine();
    	
    	 list.add(temp);

    	 
    	 
    	FileOutputStream of=new FileOutputStream("d:\\dic.txt");
    	ObjectOutputStream os= new ObjectOutputStream(of); 
    	for(entry i:list)  os.writeObject(i);
    	of.close();
    }
    
    void getDic() throws IOException, ClassNotFoundException ,EOFException
    {
    	entry temp;
    	
    	FileInputStream f=new FileInputStream("d:\\dic.txt");
    	ObjectInputStream s= new ObjectInputStream(f); 
    	while((temp=(entry)s.readObject())!=null)
    	{
    		list.add(temp);
    	}
    	s.close();
    } 
    
    void queryDic()
    {
    	System.out.println("What can I help you:(Press q to quit)");
    	
    	Scanner sca=new Scanner(System.in);
    	String query = sca.nextLine();
    	
    	while(query.charAt(0)!='q')
    	{
		String[] q=query.split(" ");
		Set<String> qwords = new HashSet<String>();;
		for (String x : q)  qwords.add(x);
		 
		Set<String> result = new HashSet<String>();
		int num = 0;
		int index = -1;
        for(int i=0;i<list.size();i++)
        {
    		result.clear();
    		result.addAll(list.get(i).keys);
    		result.retainAll(qwords);
    		if(result.size()>num)
    		{
    			index = i;
    			num = result.size();
    		}	
        }
        
        if(index==-1)
        {
        	System.out.println("Sorry,I don't know the answer.o(>©n<)o");
        }  else
        {
        	System.out.println(list.get(index).answer);
        }
        
        query = sca.nextLine();
        
    	}
    }
    
}
