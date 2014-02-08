import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

class MyCompare implements Comparator<Record> 
{
	public int compare(Record r1, Record r2) {

		if(r1.name.compareTo(r2.name)>0)//这样比较是降序,如果把-1改成1就是升序.
		{
			return -1;
		}
		else if(r1.name.compareTo(r2.name)<0)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}







public class ListAll {


	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<Record> gradebook = new Vector<Record>();
		
		
		Generic db = new Generic();
		gradebook = db.loadDB("D:\\database.txt");
		
		Comparator<Record> ct = new MyCompare();
		Collections.sort(gradebook, ct);
		
		System.out.println("+-----------------------+---------------+---------------+");
		System.out.println("|     Student Name      |    Course     +    Score      |");
		System.out.println("+-----------------------+---------------+---------------+");
        for (Record x : gradebook) { 
        System.out.println("|\t"+x.name+"\t|\t"+x.course+"\t|\t"+String.valueOf(x.score)+"\t|");
        }
        System.out.println("+-----------------------+---------------+---------------+");
	}

}
