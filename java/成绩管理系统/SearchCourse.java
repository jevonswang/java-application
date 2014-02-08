import java.util.Scanner;
import java.util.Vector;


public class SearchCourse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner in=new Scanner(System.in);
		
		String input;
		if(args.length > 0){
			input = args[0];
		}
		else input = in.nextLine();
		
		Generic db = new Generic();
		
		Vector<Record> gradebook = new Vector<Record>();
		gradebook = db.loadDB("D:\\database.txt");
		
		Vector<Record> crsbook = new Vector<Record>();
		
		for(Record x:gradebook){
			if(x.course.equals(input)){
				crsbook.add(x);
			}	
		}
		
		
		if(crsbook.size()!=0)
		{
			int total = 0;
			double average;
			int num = crsbook.size();
			System.out.println("+-----------------------+---------------+");
			System.out.println("|     Student Name      +    Score      |");
			System.out.println("+-----------------------+---------------+");
	        for (Record x : crsbook) { 
	        	System.out.println("|\t"+x.name+"\t|\t"+String.valueOf(x.score)+"\t|");
	        	total+=x.score;
	        }
	        System.out.println("+-----------------------+---------------+");
	        average = total*1.0/num;
	        System.out.println("The are "+num+" students who have taken this course.The average mark is "+String.format("%.2f", average));
		}
		else{
			System.out.println("There is no such course in the database.");
		}
		
	}
}


