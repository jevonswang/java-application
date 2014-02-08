import java.io.*;
import java.util.Scanner;
import java.util.Vector;



public  class InputGrades {


	public static void main(String[] args) {

		
		
		Scanner in=new Scanner(System.in);
		
		String input;
		if(args.length > 0){
			input = args[0];
			for (int i=1; args != null && i<args.length; i++) {   
				input = input +" " + args[i];
			}
		}
		else input = in.nextLine();
		
		
		Vector<Record> gradebook = new Vector<Record>();
		
		
		Generic db = new Generic();
		gradebook = db.loadDB("D:\\database.txt");


			
		
		
		
		Record r = db.phaseToRecord(input);
		
		boolean found = false;
		for (Record x : gradebook) { 
			if((r.course.equals(x.course))&&(r.name.equals(x.name))){
				x.score = r.score;
				System.out.println(x.name+"\'s "+x.course+" has been replaced to "+x.score);
				found = true;
				break;
			}
		}
		if(!found){
			gradebook.add(r);
			System.out.println(r.name+"\'s "+r.course+" has been scored "+r.score);
		}

		
		db.storeDB("D:\\database.txt",gradebook);
		
		//in.close();	
	}

}
