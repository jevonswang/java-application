import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.*;


public class SpreadSheet extends JFrame implements ActionListener{
		
	private static final long serialVersionUID = 1L;

	static JTable table = null;
	
	public SpreadSheet(){
		super("SpreadSheet"); 
	    setSize(800,600);  
		setLocation(200,100);  
		setVisible(true);  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE ); 
		
		this.setLayout(new BorderLayout());
		
		JButton butObj1 = new JButton("打开");
		butObj1.addActionListener(this);
		JButton butObj2 = new JButton("保存");
		butObj2.addActionListener(this);
		
		JPanel pan = new JPanel();
		pan.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
		pan.add(butObj1);
		pan.add(butObj2);
		
		this.add(pan,"South");
		
		table = new JTable();
		
		table.setPreferredScrollableViewportSize(new Dimension(550,30));
		JScrollPane scrollPane=new JScrollPane(table);
		this.add(scrollPane,"Center");
		//this.pack();
		this.show();
	}
	
	public void actionPerformed(ActionEvent e){
		String s = e.getActionCommand();  
		if (s.equals("打开")){
			
			JFileChooser jc = new JFileChooser();
			int retValue = jc.showOpenDialog(this);
			if(retValue!=JFileChooser. APPROVE_OPTION){
				return;
			}

			File file = jc.getSelectedFile();
			TableModel Info = getFileStats(file);
			table.setModel(Info);
			
			
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		    render.setHorizontalAlignment(SwingConstants.CENTER);

		    
			int col_count = table.getColumnCount();
			String ColumnName;
			for(int i=0;i<col_count;i++){
				ColumnName = table.getColumnName(i);
			    table.getColumn(ColumnName).setCellRenderer(render);
			}
		}
		
		
		if (s.equals("保存")){
			
			JFileChooser jc = new JFileChooser();
			int retValue = jc.showSaveDialog(this);
			if(retValue!=JFileChooser. APPROVE_OPTION){
				return;
			}

			File file = jc.getSelectedFile();
			saveFileStats(file);
		}
		table.revalidate();
	}
	
	private static TableModel getFileStats(File a) {
		String data;
		Object[] object = null;
		
		DefaultTableModel dt = new DefaultTableModel();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(a));
			//不是文件尾一直读
			data = br.readLine();
			object = data.split(",");
			dt.setColumnIdentifiers(object);
			
			while ((data = br.readLine()) != null) {
				object = data.split(",");
				dt.addRow(object);
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	private static void saveFileStats(File a){ 
				
		try {
			FileWriter fw = new FileWriter(a);   
			
			int col_count = table.getColumnCount();
			int row_count = table.getRowCount();
			
			String ColumnName;
			for(int i=0;i<col_count;i++){
				ColumnName = table.getColumnName(i);
				fw.write(ColumnName);
				if(i!=col_count-1) fw.write(",");
				else fw.write("\r\n");
			}
			
			for(int i=0;i<row_count;i++)
				for(int j=0;j<col_count;j++){
					fw.write(table.getValueAt(i,j).toString());
					if(j!=col_count-1) fw.write(",");
					else fw.write("\r\n");
				}
			
			fw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String args[]){
		new SpreadSheet();
	}
	

}