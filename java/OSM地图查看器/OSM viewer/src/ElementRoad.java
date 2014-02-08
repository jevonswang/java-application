import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class ElementRoad {
	
	public static final int RESIDENTIAL = 0;
	public static final int SERVICE = 0;
	public static final int UNCLASSIFIED = 0;
	public static final int UNKNOWN = -3;
	public static final int CANAL = -2;
	public static final int CYCLEWAY = -1;
	public static final int FOOTWAY = -1;
	public static final int TERTIARY = 1;
	public static final int SECONDARY = 2;
	public static final int PRIMARY = 3;
	public static final int MOTORWAY = 3;
	public static final int RAILWAY = 4;
	
	
	private ArrayList<Line2D.Double> segment;
	private int type;
	
	public ElementRoad(ArrayList<Point2D.Double> point, int type) {
		
		this.segment = new ArrayList<Line2D.Double>();
		this.type = type;

		for (int i = 0; i < point.size() - 1; i++) {
			Point2D.Double p1 = point.get(i);
			Point2D.Double p2 = point.get(i + 1);
			Line2D.Double line =  new Line2D.Double(p1, p2);
			segment.add(line);
		}
	
	}
	
	public void draw(Graphics2D g2, double scale) {
		Color color = null;
		
		BasicStroke tt = new BasicStroke(1.0f / (float) scale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		BasicStroke thin = new BasicStroke(3.0f / (float) scale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		BasicStroke soso = new BasicStroke(4.5f / (float) scale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		BasicStroke thick = new BasicStroke(6.0f / (float) scale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		BasicStroke dashline = new BasicStroke(1.0f / (float) scale, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {1.0f, 1.5f}, 0.0f);
		BasicStroke raildash = new BasicStroke(3.0f / (float) scale, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {3.0f, 2.0f}, 0.0f);
		
		switch (type) {
			case -3: color = Color.GRAY; g2.setStroke(tt); break;
			case -2: color = new Color(181, 208, 208); g2.setStroke(soso); break;
			case -1: color = new Color(248, 132, 118); g2.setStroke(dashline); break;
			case 0: color = new Color(250, 250, 250);g2.setStroke(thin); break;
			case 1: color = new Color(248, 248, 186); g2.setStroke(thin); break;
			case 2: color = new Color(248, 213, 169); g2.setStroke(soso); break;
			case 3: color = new Color(220, 158, 158); g2.setStroke(thick); break;
			default: color = Color.GRAY; g2.setStroke(tt);break;
		}
		if (color != null)
			g2.setPaint(color);
		for (int i = 0; i < segment.size(); i++) {
			Line2D.Double line = segment.get(i);
			if (type == 4) {
				g2.setPaint(new Color(250, 250, 250));
				g2.setStroke(thin);
				g2.draw(line);
				g2.setPaint(Color.GRAY);
				g2.setStroke(raildash);
				g2.draw(line);
			}
			else
				g2.draw(line);
		}
	}
	
}
