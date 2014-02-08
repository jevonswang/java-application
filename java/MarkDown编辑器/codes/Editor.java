import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

import org.markdownj.*;


public class Editor extends JFrame implements KeyListener{
		
	private static final long serialVersionUID = 3381608566709141916L;


	static JTextArea inputArea = new JTextArea();
	static JEditorPane htmlPane = new JEditorPane();

	public Editor(){
		super("MarkdownPad"); 
	    setSize(800,600);  
		setLocation(200,100);
		setVisible(true);  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE ); 
		
		this.setLayout(new GridLayout(1,2));
		
		inputArea.setLineWrap(true);
		inputArea.addKeyListener(this);

		HTMLEditorKit kit = new HTMLEditorKit();
		htmlPane.setEditorKit(kit);
		htmlPane.setEditable(false);

		
		JScrollPane scrollPane1 = new JScrollPane(inputArea);
		JScrollPane scrollPane2 = new JScrollPane(htmlPane);

		this.add(scrollPane1);
		this.add(scrollPane2);
		
		this.show();
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e){
		String temp = inputArea.getText(); 
		String mark = new MarkdownProcessor().markdown(temp);
		System.out.println(mark);
		htmlPane.setText(mark);
	 }
		
	public static void main(String args[]){
		new Editor();
	}

}