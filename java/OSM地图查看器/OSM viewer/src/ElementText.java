import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class ElementText {

	private String content;
	private Point2D.Double location;
	
	public ElementText(String content, Point2D.Double location) {
		this.content = content;
		this.location = location;
	}
	
	public void draw(Graphics2D g2, double scale) {
		g2.drawString(content, (float) location.getX(), (float) location.getY());
	}
	
}
