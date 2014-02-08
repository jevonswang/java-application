import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.*;


class shape{
	public shape(shape currShape) {
		this.type = currShape.type;
		this.color = currShape.color;
		this.startx = currShape.startx;
		this.starty = currShape.starty;
		this.endx = currShape.endx;
		this.endy = currShape.endy;
		this.text = currShape.text;
		this.ima = currShape.ima;
	}
	
	public shape() {
		// TODO Auto-generated constructor stub
	}
	int type=0;//0��Line  1:Circle  2:Rect  3:Text	4:Image
	int color=0;//0:black 1:red 2:yellow 3:blue 4:green
	int startx=0,starty=0;
	int endx=0,endy=0;
	String text ="";
	Image ima;
}

public class miniCAD extends JFrame implements MouseListener,MouseMotionListener,ActionListener{
	
	
	
	static shape currShape = new shape();
	
	static ArrayList<shape> drawlist = new ArrayList<shape>();
	
    static BufferedImage bi = new BufferedImage(800, 600, BufferedImage.TYPE_3BYTE_BGR);
	
	static int tool=0;
	
	private static final long serialVersionUID = 1L;

	static JMenuBar mb;
	
	public miniCAD(){
		super("miniCAD"); 
		
	    setSize(800,600);  
		setLocation(200,100);  
		setVisible(true);  
		setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE );
		
	    Graphics g = bi.getGraphics(); 
	    g.setColor(Color.WHITE);
	    g.fillRect(0, 0, 800, 600);
		
		
		addMouseListener(this);
		addMouseMotionListener(this);
		 
		mb = new JMenuBar(); 

		JMenu mnFile= new JMenu("�ļ�");  
		JMenu mnTool= new JMenu("����");
		JMenu mnColor= new JMenu("��ɫ");
		JMenu mnHelp= new JMenu("����");
		

		JMenuItem miOpen= new JMenuItem("��");
		JMenuItem miSave= new JMenuItem("����");
		JMenuItem miExit= new JMenuItem("�˳�");
		
		miOpen.addActionListener(this); 		
		miSave.addActionListener(this); 
		miExit.addActionListener(this); 
		

		mnFile.add(miOpen);
		mnFile.add(miSave);
		mnFile.addSeparator(); 
		mnFile.add(miExit);

		ButtonGroup buttonTool = new ButtonGroup();
		
		JMenuItem  miLine = new JRadioButtonMenuItem("ֱ��");
		JMenuItem  miCircle = new JRadioButtonMenuItem("Բ");
		JMenuItem  miRectangle = new JRadioButtonMenuItem("����");
		JMenuItem  miText = new JRadioButtonMenuItem("����");

		JMenuItem miMove = new JRadioButtonMenuItem("�ƶ�");

		
		miLine.addActionListener(this); 
		miCircle.addActionListener(this); 
		miRectangle.addActionListener(this); 
		miText.addActionListener(this); 
		miMove.addActionListener(this); 

		
		
		buttonTool.add(miLine);
		buttonTool.add(miCircle);
		buttonTool.add(miRectangle);
		buttonTool.add(miText);
		buttonTool.add(miMove);

		
		mnTool.add(miLine);
		mnTool.add(miCircle);
		mnTool.add(miRectangle);
		mnTool.add(miText);
		mnTool.add(miMove);

		
		
		ButtonGroup buttonColor=new ButtonGroup();
		
		JRadioButtonMenuItem miBlack = new JRadioButtonMenuItem("��ɫ");
		JRadioButtonMenuItem miRed = new JRadioButtonMenuItem("��ɫ");
		JRadioButtonMenuItem miYellow = new JRadioButtonMenuItem("��ɫ");
		JRadioButtonMenuItem miBlue = new JRadioButtonMenuItem("��ɫ");
		JRadioButtonMenuItem miGreen = new JRadioButtonMenuItem("��ɫ");
		
		miBlack.addActionListener(this);
		miRed.addActionListener(this);
		miYellow.addActionListener(this);
		miBlue.addActionListener(this);
		miGreen.addActionListener(this);
		
		buttonColor.add(miBlack);
		buttonColor.add(miRed);
		buttonColor.add(miYellow);
		buttonColor.add(miBlue);
		buttonColor.add(miGreen);
		
		mnColor.add(miBlack);
		mnColor.add(miRed);
		mnColor.add(miYellow);
		mnColor.add(miBlue);
		mnColor.add(miGreen);
		
		JMenuItem miHelp = new JMenuItem("����");
		miHelp.addActionListener(this);
		mnHelp.add(miHelp);
		
		mb.add(mnFile);
		mb.add(mnTool);
		mb.add(mnColor);
		mb.add(mnHelp);
		
		this.setJMenuBar(mb);  
		//update();
	//	Graphics g = getGraphics();
//		update(g);
	//	paint(g);
	}
	
	
	public static void main(String args[]){
		new miniCAD();
	}
	
	public void actionPerformed(ActionEvent e){
		String s = e.getActionCommand();  
		System.out.println(s);
		if(s.equals("�˳�")){
			System.exit(0); 
		}else 
		if(s.equals("��")){
			File curImageFile;
			Image curImage;

			JFileChooser jc = new JFileChooser();
			int retValue = jc.showOpenDialog(this);
			
			if(retValue!=JFileChooser. APPROVE_OPTION){
			/*specify your handle option*/
			return;
			}
			/*here we deal with when choose approve option*/
			
			curImageFile = jc.getSelectedFile();
			curImage = Toolkit.getDefaultToolkit().getImage(curImageFile.toString());  
			
			int type = currShape.type;
			currShape.type = 4;
			currShape.ima = curImage;
			drawlist.add(new shape(currShape));
			currShape.type = type;
			
			//Graphics g = getGraphics();
			//g.drawImage(curImage, 200, 100, this);
			
			Graphics g = bi.getGraphics(); 
			g.drawImage(curImage, 200, 100, this);

		}else 
		if(s.equals("����")){
			File curImageFile;

			JFileChooser jc = new JFileChooser();
			int retValue = jc.showSaveDialog(this);
			
			if(retValue!=JFileChooser. APPROVE_OPTION){
				return;
			}
			
			int w = getWidth(); 
		    int h = getHeight();
		    
			
			curImageFile = jc.getSelectedFile();	

		    Graphics g = bi.getGraphics(); 

			Iterator<shape> it = drawlist.iterator();
			while(it.hasNext()){
				draw(g, it.next());
			}
	    	
		    
		    try { 
		    	ImageIO.write(bi,"jpg",curImageFile); 
		     } catch (IOException ee) { 
		            // TODO Auto-generated catch block 
		            ee.printStackTrace(); 
		     } 
			
			
			
		}else 
		if(s.equals("ֱ��")){
			tool = 0;
			currShape.type = 0;
		}else 
		if(s.equals("Բ")){
			tool = 0;
			currShape.type = 1;  
		}else 
		if(s.equals("����")){
			tool = 0;
			currShape.type =2;  
		}else 
		if(s.equals("����")){
			tool = 0;
			currShape.type = 3;  
			currShape.text = JOptionPane.showInputDialog("��������Ҫд�����֣�");
		}else 
		if(s.equals("�ƶ�")){
			tool = 1;  
		}else 
		if(s.equals("��ɫ")){
			currShape.color = 0;  
		}else 
		if(s.equals("��ɫ")){
			currShape.color =1;  
		}else 
		if(s.equals("��ɫ")){
			currShape.color = 2;  
		}else 
		if(s.equals("��ɫ")){
			currShape.color = 3;  
		}else 
		if(s.equals("��ɫ")){
			currShape.color = 4;  
		}else
		if(s.equals("����")){
			JOptionPane.showMessageDialog(null,"Made by ���ܷ壨311000026��\n              2013/11/22");
		}
		
	}


	public void mousePressed(MouseEvent e){
		currShape.startx = e.getX(); 
		currShape.starty = e.getY();
	}
	
	public void mouseReleased(MouseEvent e){
		
		currShape.endx=e.getX();
		currShape.endy=e.getY();
		
		
		if(tool == 0){
			drawlist.add(new shape(currShape));
		}else
		if(tool == 1){
			if(!drawlist.isEmpty()){
				shape moved =drawlist.remove(drawlist.size()-1);
				
				Graphics g = getGraphics();

				
				//erase the former shape
				g.setColor(new Color(255,255,255));
				switch(moved.type){
				case 0:		g.drawLine(moved.startx,moved.starty,moved.endx,moved.endy);break;
				case 1:		g.drawOval(moved.startx,moved.starty,Math.abs(moved.endx-moved.startx),Math.abs(moved.endy-moved.starty));break;
				case 2:		g.drawRect(moved.startx,moved.starty,moved.endx-moved.startx,moved.endy-moved.starty);break;
				case 3:		g.setFont(new Font("Tahoma", Font.BOLD, 30)); g.drawString(moved.text,moved.startx,moved.starty);break;
				}
				
				//change the position of the shape
				int x=currShape.endx-currShape.startx;
				int y=currShape.endy-currShape.starty;
				moved.startx+=x;
				moved.starty+=y;
				moved.endx+=x;
				moved.endy+=y;
				drawlist.add(moved);
			}
		}
		
		repaint();
	}

	public void update(Graphics g){
		paint(g);
	}
	
	public void mouseDragged(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseMoved(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void draw(Graphics g,shape s){
		
		switch(s.color){
		case 0: g.setColor(new Color(0,0,0));break;
		case 1: g.setColor(new Color(255,0,0));break;
		case 2: g.setColor(new Color(255,255,0));break;
		case 3: g.setColor(new Color(0,0,255));break;
		case 4: g.setColor(new Color(0,255,0));break;
		}
		
		switch(s.type){
			case 0:		g.drawLine(s.startx,s.starty,s.endx,s.endy);break;
			case 1:		g.drawOval(s.startx,s.starty,Math.abs(s.endx-s.startx),Math.abs(s.endy-s.starty));break;
			case 2:		g.drawRect(s.startx,s.starty,s.endx-s.startx,s.endy-s.starty);break;
			case 3:		g.setFont(new Font("Tahoma", Font.BOLD, 30)); g.drawString(s.text,s.startx,s.starty);break;
			case 4:		g.drawImage(s.ima, 200, 100, this);
		}
	}
	
	
	
	public void paint(Graphics g){
		
		Iterator<shape> it = drawlist.iterator();
		while(it.hasNext()){
			draw(g, it.next());
		}

		this.setJMenuBar(mb);  
	}




}