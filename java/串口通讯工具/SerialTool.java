import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import gnu.io.*;

class m_Frame extends Frame implements Runnable,ActionListener,SerialPortEventListener{
	static CommPortIdentifier portId;
	static Enumeration portList;
	InputStream inputStream;
	OutputStream outputStream;
	SerialPort serialPort;
	Thread readThread;
	String str="";
	
	Choice c1,c2,c3,c4,c5;
	JButton jbLink,jbBreak,jbClear,jbSend;
	static JTextArea receArea;
	JTextField jtSend,jtMessage;
	
	String port;
	int baud,data,stop,parity;
	
	
	@SuppressWarnings("restriction")
	m_Frame(){
		
		super("SerialTool");
		setSize(500,600);
		setVisible(true);
		this.setResizable(false);
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		JPanel statPanel = new JPanel();
		JPanel recePanel = new JPanel();
		JPanel sendPanel = new JPanel();

		JLabel lb1 = new JLabel("Port:");
		JLabel lb2 = new JLabel("Baud:");
		JLabel lb3 = new JLabel("Data:");
		JLabel lb4 = new JLabel("Parity:");
		JLabel lb5 = new JLabel("Stop:");


		statPanel.setLayout(new GridLayout(2,3));
		
		JPanel sub1 = new JPanel();
		sub1.add(lb1);
        c1 = new Choice(); 
        c1.addItem("COM1");
        c1.addItem("COM2");
        c1.addItem("COM3");
        c1.addItem("COM4");
        c1.addItem("COM5");
        sub1.add(c1);
        statPanel.add(sub1);
        
		JPanel sub2 = new JPanel();
        sub2.add(lb2);
        c2 = new Choice(); 
        c2.addItem("4800");
        c2.addItem("9600");
        c2.addItem("14400");
        c2.addItem("19200");
        sub2.add(c2);
        statPanel.add(sub2);
        
		JPanel sub3 = new JPanel();
        sub3.add(lb3);
        c3 = new Choice(); 
        c3.addItem("8");
        c3.addItem("7");
        c3.addItem("6");
        c3.addItem("5");
        sub3.add(c3);
        statPanel.add(sub3);
        
		JPanel sub4 = new JPanel();
        sub4.add(lb4);
        c4 = new Choice(); 
        c4.addItem("NONE");
        c4.addItem("ODD");
        c4.addItem("EVEN");
        c4.addItem("MARK");
        c4.addItem("SPACE");
        sub4.add(c4);
        statPanel.add(sub4);
        
		JPanel sub5 = new JPanel();
        sub5.add(lb5);
        c5 = new Choice(); 
        c5.addItem("1");
        c5.addItem("1.5");
        c5.addItem("2");
        sub5.add(c5);
        statPanel.add(sub5);
        
        JPanel sub6 = new JPanel(new FlowLayout());
        jbLink = new JButton("Link");
		jbLink.addActionListener(this);
		jbBreak = new JButton("Break");
		jbBreak.addActionListener(this);
        sub6.add(jbLink);
        sub6.add(jbBreak);
        statPanel.add(sub6);
        
        
        
        recePanel.setLayout(new BorderLayout());
        
        JPanel sub7 = new JPanel();
        sub7.setLayout(new FlowLayout());
        jbClear = new JButton("Clear");
        jbClear.addActionListener(this);
        sub7.add(jbClear);
        recePanel.add(sub7,"South");
        
        JPanel sub8 = new JPanel();
        sub8.setLayout(new BorderLayout());
        receArea = new JTextArea();
        receArea.setLineWrap(true);
        
        receArea.setEditable(false);
        JScrollPane js = new JScrollPane(receArea);
        sub8.add(js);
        recePanel.add(sub8,"Center");
        
        
        
        sendPanel.setLayout(new BorderLayout());
        
        JPanel sub9 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jl6 = new JLabel("Send Data");
        sub9.add(jl6);
        sendPanel.add(sub9,"North");
             
        
        JPanel sub10 = new JPanel(new FlowLayout());
        
        jtSend = new JTextField();
        sub10.add(jtSend);
        sendPanel.add(jtSend,"Center");
        
        JPanel sub11 = new JPanel();
        jbSend = new JButton("Send");
        jbSend.addActionListener(this);
        sub11.add(jbSend);
        sendPanel.add(sub11,"South");

        
        
		jtMessage = new JTextField();
		jtMessage.setText("Welcome to use this tool!");
		jtMessage.setEditable(false);
		JPanel sub12 = new JPanel(new BorderLayout());
		sub12.add(jtMessage);
        
        
        
        
		//±ß¿ò  
		Border border=BorderFactory.createEtchedBorder(Color.BLACK,Color.GRAY);

		Border border1=BorderFactory.createTitledBorder(
				border,"Com Status",TitledBorder.LEFT,TitledBorder.TOP,new Font("ºÚÌå",Font.ITALIC
		, 16),Color.GRAY);   
		Border border2=BorderFactory.createTitledBorder(
				border,"Receive",TitledBorder.LEFT,TitledBorder.TOP,new Font("ºÚÌå",Font.ITALIC
		, 16),Color.GRAY); 
		Border border3=BorderFactory.createTitledBorder(
				border,"Send",TitledBorder.LEFT,TitledBorder.TOP,new Font("ºÚÌå",Font.ITALIC
		, 16),Color.GRAY); 
 		

		
		statPanel.setBorder(border1);
		recePanel.setBorder(border2);
		sendPanel.setBorder(border3);
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		


	//	BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		
		statPanel.setPreferredSize(new Dimension(500,100));
		recePanel.setPreferredSize(new Dimension(500,300));
		sendPanel.setPreferredSize(new Dimension(500,90));
		sub12.setPreferredSize(new Dimension(500,2));
		
		add(statPanel);
		add(recePanel);
		add(sendPanel);	
		add(sub12);
		
		port="COM1";
		baud=9600;
		data=SerialPort.DATABITS_8;
		stop=SerialPort.STOPBITS_1;
		parity=SerialPort.PARITY_NONE;
		
		readThread = new Thread(this);
		readThread.start();
		
		this.show();
		
	}
	


	public void actionPerformed(ActionEvent event){
		
		String s = event.getActionCommand(); 
		
		if(s.equals("Link")){
			
			refresh_para();
			
			portList = CommPortIdentifier.getPortIdentifiers();
		
			while(portList.hasMoreElements()){
				portId = (CommPortIdentifier)portList.nextElement();
				if(portId.getPortType() == CommPortIdentifier.PORT_SERIAL){
					if(portId.getName().equals(port)){
						try{
							serialPort = (SerialPort)portId.open("ReadComm",2000);
						}
						catch(PortInUseException e){}
						
						try{
							outputStream = serialPort.getOutputStream();
						}
						catch (IOException e){}
				
						try{
							serialPort.addEventListener(this);
						}
						catch(TooManyListenersException e){}
				
						serialPort.notifyOnDataAvailable(true);
					}
				}
			}
			String message = "The port "+port+" has been openned successfully.";
			jtMessage.setText(message);

		}
		else if(s.equals("Break")){
			serialPort.close();
			String message = "The port "+port+" has been closed successfully.";
			jtMessage.setText(message);
		}
		else if(s.equals("Clear")){
			receArea.setText("");
		}
		else if(s.equals("Send")){
			try{
				serialPort.setSerialPortParams(baud,data,stop,parity);
			}
			catch(UnsupportedCommOperationException e){}
			
			byte senddata[] = new byte[30];
			String str = jtSend.getText();
			int len = str.length();
			char ch;
			int t=0;
			boolean pair = true;
			for(int i=0;i<len;i++){
				ch = str.charAt(i);
				if((ch>='0')&&(ch<='9')){
					if(!pair) senddata[t]*=16;
					senddata[t]+=(byte)(ch-48);
					pair = !pair;
				}
				else if((ch>='A')&&(ch<='F')){
					if(!pair) senddata[t]*=16;
					senddata[t]+=(byte)(ch-55);
					pair = !pair;
				}else continue;
				
				if(pair)t++;
			}
			senddata[t]='\0';
			
			
			try{
				outputStream.write(senddata,0,t);
			}
			catch(IOException e){}
			
			String message = "The data has been sent successfully.";
			jtMessage.setText(message);
		}
		
		
	}
	
	private void refresh_para() {
		
		port = c1.getSelectedItem();
		baud = Integer.parseInt(c2.getSelectedItem());
		data = Integer.parseInt(c3.getSelectedItem());	
		
		String t = c4.getSelectedItem();
		if(t.equals("NONE"))
			parity = SerialPort.PARITY_NONE;
		else if(t.equals("ODD"))
			parity = SerialPort.PARITY_ODD;
		else if(t.equals("EVEN"))
			parity = SerialPort.PARITY_EVEN;
		else if(t.equals("MARK"))
			parity = SerialPort.PARITY_MARK;
		else if(t.equals("SPACE"))
			parity = SerialPort.PARITY_SPACE;

		t = c5.getSelectedItem();
		if(t.equals("1.5"))
			stop = SerialPort.STOPBITS_1_5;
		else if(t.equals("1"))
			stop = SerialPort.STOPBITS_1;
		else if(t.equals("2"))
			stop = SerialPort.STOPBITS_2;
		
	}



	public void run(){
		try{
			Thread.sleep(20000);
		}
		catch(InterruptedException e){}
	}
	
	
	public void serialEvent(SerialPortEvent event){
		
		try{
			serialPort.setSerialPortParams(baud,data,stop,parity);
		}
		catch(UnsupportedCommOperationException e){}
		
		byte[] readBuffer = new byte[1000];
		try{
			inputStream = serialPort.getInputStream();
		}
		catch(IOException e){}
		
		int numBytes = 0;
		try{
			while(inputStream.available()>0){
				numBytes = inputStream.read(readBuffer);
			}
			
			str = new String(readBuffer);
			
			str = "";
			String hex = "";
			for(int i=0;i<numBytes;i++){
				hex = Integer.toHexString(readBuffer[i] & 0xFF);
				if (hex.length() == 1) { 
					hex = '0' + hex; 
				} 
				if((i+1)%23!=0)
					str+=hex.toUpperCase() +"  ";
				else 
					str+=hex.toUpperCase()+"\n";
				//System.out.printf("%02x ",readBuffer[i]);
			}
			
			System.out.println(str);
			
		receArea.append(str+"\n");

		}
		catch(IOException e){}
		
		String message = "Data received successfully...";
		jtMessage.setText(message);
	}
}



public class SerialTool{
	public static void main(String args[]){
		m_Frame win = new m_Frame();
		win.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{System.exit(0);}
		});
	}
	
}





