import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ElementRegion {
	
	public static final int NORMAL = 0;
	public static final int WATER = 1;
	public static final int WOOD = 2;
	public static final int BUILDING = 3;
	
	private int type;
	private Shape region;
	
	public ElementRegion(ArrayList<Point2D.Double> vertice, int type) {
		this.type = type;
		GeneralPath gp = new GeneralPath();
		region = gp;
		for (int i = 0; i < vertice.size(); i++) {
			Point2D.Double vertex = vertice.get(i);
			if (i == 0) {
				gp.moveTo(vertex.getX(), vertex.getY());
			}
			else {
				gp.lineTo(vertex.getX(), vertex.getY());
			}
		}
	}
	
	public void draw(Graphics2D g2, double scale) {
		g2.draw(region);
		Paint previous = g2.getPaint();
		Color color = null;
		switch (type) {
			case WOOD:     color = new Color(174, 209, 160); break;
			case WATER:    color = new Color(181, 208, 208);  break;
			case NORMAL:   color = new Color(221, 221, 220);  break;
			case BUILDING: color = new Color(190, 173, 173); break;
			default:		break;
		}
		if (color != null) {
			g2.setPaint(color);
			g2.fill(region);
		}
		g2.setPaint(previous);
	}

}
