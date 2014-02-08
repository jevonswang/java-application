import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class OSMviewer extends JFrame {
	
	private static final long serialVersionUID = 1107415428276820062L;
	
	public OSMviewer() {
		
		setTitle("OSM viewer");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		MapPanel mapPanel = new MapPanel();
		add(mapPanel, BorderLayout.CENTER);
		
		show();
	}
	
	public static void main(String[] args) {

		OSMviewer frame = new OSMviewer();
		
	}
}


class MapPanel extends JPanel {
	
	private static final long serialVersionUID = -4331243188878138645L;
	
	public static final double DEFAULT_CENTER_LON = 120.1170 * 10000;
	public static final double DEFAULT_CENTER_LAT = -30.2663 * 10000;
		
	private double scale;
	private Point2D.Double center;
	private boolean mouseEntered;
	private Point mouseLoc;

	private MapData mapData;
	private AffineTransform atf;
	
	public MapPanel() {
		
		scale = 1.0;
		center = new Point2D.Double(DEFAULT_CENTER_LON, DEFAULT_CENTER_LAT);
		
		System.out.println("Loading the map,please wait...'");
		
		mapData = new MapData();
		
		
		JFileChooser jc = new JFileChooser();
		int retValue = jc.showOpenDialog(this);
		if(retValue!=JFileChooser. APPROVE_OPTION){
			return;
		}

		File file = jc.getSelectedFile();
		
	//	String filename = file.getAbsolutePath();
		
		//System.out.println(filename);
		
		try {
			mapData.loadMap(file);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		System.out.println("Map loaded");
		
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				mouseEntered = true;
				mouseLoc = e.getPoint();
			}
			public void mouseExited(MouseEvent e) {
				mouseEntered = false;
			}
			public void mousePressed(MouseEvent e) {
				mouseLoc = e.getPoint();
			}
			public void mouseReleased(MouseEvent e) {
				setCursor(Cursor.getDefaultCursor());
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				if ( mouseEntered ) {
					int dx = e.getX()-mouseLoc.x;
					int dy = e.getY()-mouseLoc.y;
					setCenter(center.x - dx / scale, center.y - dy / scale);
					mouseLoc = e.getPoint();
				}
			}
		});
		
		addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation()<0){
					scale+=0.1f;
					setScale(scale);
				}else
				if(e.getWheelRotation()>0){
					scale-=0.1f;
					setScale(scale);
				}
				
			}
		});
		
	}
	

	public void setCenter(double lon, double lat) {
		center.setLocation(lon, lat);
		repaint();
	}
	
	public void setScale(double scale) {
			this.scale = scale;
			repaint();
	}
	
	
	
	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		
		int width = getSize().width;
        int height = getSize().height;
		g2.clearRect(0, 0, width, height);
		
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

		
		
		atf = new AffineTransform((double)scale,0,0,(double)scale,
			(double) width / 2 - center.x * scale,
			(double) height/ 2 - center.y * scale);

		g2.transform(atf);
		if ( mapData != null ) {
			mapData.draw(g2, scale);
		}
	}
	
	
}
