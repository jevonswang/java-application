import java.util.Scanner;
import java.util.Vector;


public class SearchStu {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner in=new Scanner(System.in);
		
		String input;
		if(args.length > 0){
			input = args[0];
			for (int i=1; args != null && i<args.length; i++) {   
				input = input +" " + args[i];
			}
		}
		else input = in.nextLine();
		
		Generic db = new Generic();
		
		Vector<Record> gradebook = new Vector<Record>();
		gradebook = db.loadDB("D:\\database.txt");
		
		Vector<Record> stugrades = new Vector<Record>();
		
		for(Record x:gradebook){
			if(x.name.equals(input)){
				stugrades.add(x);
			}	
		}
		
		
		if(stugrades.size()!=0)
		{
			int total = 0;
			double average;
			int num = stugrades.size();
			System.out.println("The gradebook of "+input+" are listed as follows:");
			System.out.println("+---------------+---------------+");
			System.out.println("|    Course     +    Score      |");
			System.out.println("+---------------+---------------+");
	        for (Record x : stugrades) { 
	        	System.out.println("|\t"+x.course+"\t|\t"+String.valueOf(x.score)+"\t|");
	        	total+=x.score;
	        }
	        System.out.println("+---------------+---------------+");
	        average = total*1.0/num;
	        System.out.println("He has taken "+num+" courses.The total mark is "+total+",the average mark is "+String.format("%.2f", average));
		}
		else{
			System.out.println("There is no such student in the database.");
		}
		
	}

}

