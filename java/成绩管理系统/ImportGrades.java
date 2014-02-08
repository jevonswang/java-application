import java.util.Scanner;
import java.util.Vector;


public class ImportGrades {

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
		
		Vector<Record> newgrade = new Vector<Record>();
		newgrade = db.loadDB(input);

		for(Record r :newgrade){
			
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
			
			
		}
				
		db.storeDB("D:\\database.txt",gradebook);
	}

}
