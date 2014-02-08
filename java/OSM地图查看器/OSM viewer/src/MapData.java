import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MapData {
	
	private Map<String, Point2D.Double> nodes = new HashMap<String, Point2D.Double>();
	private ArrayList<ElementText> texts = new ArrayList<ElementText>();
	private ArrayList<ElementRegion> regions = new ArrayList<ElementRegion>();
	private ArrayList<ElementRoad> roads = new ArrayList<ElementRoad>();
	
	
	public void loadMap(File f) throws SAXException, IOException, ParserConfigurationException {
		
		InputStream fin = new FileInputStream(f);
		
		
		System.out.println(f.getAbsolutePath());
		
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fin);
	
		Element root = doc.getDocumentElement();
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node n = list.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				if (n.getNodeName().equals("node")) {
					Element element = (Element) n;
					String id = element.getAttribute("id");
					double lon = Double.parseDouble(element.getAttribute("lon")) * 10000;
					double lat = -Double.parseDouble(element.getAttribute("lat")) * 10000;
					nodes.put(id, new Point2D.Double(lon, lat));
					NodeList subl = n.getChildNodes();
					for (int j = 0; j < subl.getLength(); j++) {
						Node subn = subl.item(j);
						if (subn.getNodeType() == Node.ELEMENT_NODE) {
							Element ee = (Element) subn;
							String name = ee.getAttribute("name");
							if (!name.isEmpty()) {
								texts.add(new ElementText(name, new Point2D.Double(lon, lat)));
							}
						}
					}
				}
				if (n.getNodeName().equals("way")) {
					NodeList subl = n.getChildNodes();
					ArrayList<Point2D.Double> refPoints = new ArrayList<Point2D.Double>();
					double avgx = 0;
					double avgy = 0;
					int count = 0;
					boolean knownTag = false;
					for (int j = 0; j < subl.getLength(); j++) {
						Node subn = subl.item(j);
						if (subn.getNodeName().equals("nd")) {
							String ref = ((Element) subn).getAttribute("ref");
							Point2D.Double refp = nodes.get(ref);
							if (refp != null) {
								refPoints.add(refp);
								avgx += refp.getX();
								avgy += refp.getY();
								count++;
							}
						}
						if (subn.getNodeName().equals("tag")) {
							String key = ((Element) subn).getAttribute("k");
							String value = ((Element) subn).getAttribute("v");
							if (key.equals("name") || key.equals("name:zh")) {
								ElementText t = new ElementText(value, new Point2D.Double(avgx / count, avgy / count));
								if (!texts.contains(t))
									texts.add(t);
							}
							if (!knownTag) {
								if (key.equals("highway")) {
									switch (value) {
									case "cycleway":
										roads.add(new ElementRoad(refPoints, ElementRoad.CYCLEWAY));
										break;
									case "footway":
										roads.add(new ElementRoad(refPoints, ElementRoad.FOOTWAY));
										break;
									case "residential":
										roads.add(new ElementRoad(refPoints, ElementRoad.RESIDENTIAL));
										break;
									case "service":
										roads.add(new ElementRoad(refPoints, ElementRoad.SERVICE));
										break;
									case "tertiary":
										roads.add(new ElementRoad(refPoints, ElementRoad.TERTIARY));
										break;
									case "secondary":
										roads.add(new ElementRoad(refPoints, ElementRoad.SECONDARY));
										break;
									case "primary":
										roads.add(new ElementRoad(refPoints, ElementRoad.PRIMARY));
										break;
									case "motorway":
										roads.add(new ElementRoad(refPoints, ElementRoad.MOTORWAY));
										break;
									default:
										roads.add(new ElementRoad(refPoints, ElementRoad.UNCLASSIFIED));
										break;
									}
									knownTag = true;
								}
								if (key.equals("waterway") && value.equals("canal")) {
									roads.add(new ElementRoad(refPoints, ElementRoad.CANAL));
									knownTag = true;
								}
								if (key.equals("railway") && value.equals("rail")) {
									roads.add(new ElementRoad(refPoints, ElementRoad.RAILWAY));
									knownTag = true;
								}
								if (key.equals("natural") && value.equals("wood")) {
									regions.add(new ElementRegion(refPoints, ElementRegion.WOOD));
									knownTag = true;
								}
								if (key.equals("natural") && value.equals("water")) {
									regions.add(new ElementRegion(refPoints, ElementRegion.WATER));
									knownTag = true;
								}
								if (key.equals("waterway") && value.equals("riverbank")) {
									regions.add(new ElementRegion(refPoints, ElementRegion.WATER));
									knownTag = true;
								}
								if (key.equals("building")) {
									regions.add(new ElementRegion(refPoints, ElementRegion.BUILDING));
									knownTag = true;
								}
							}
						}
					}
					if (!knownTag) {
						if (refPoints.size() > 1 && refPoints.get(0).equals(refPoints.get(refPoints.size() - 1))) {
							regions.add(new ElementRegion(refPoints, ElementRegion.NORMAL));
						}
						else {
							roads.add(new ElementRoad(refPoints, ElementRoad.UNKNOWN));
						}
					}
				}
			}
		}

	}
	
	
	
	public void draw(Graphics2D g2, double scale) {
		
		g2.setStroke(new BasicStroke(0.3f / (float) scale));
		
		for (int i = 0; i < regions.size(); i++) {
			regions.get(i).draw(g2, scale);
		}
		
		for (int i = 0; i < roads.size(); i++) {
			roads.get(i).draw(g2, scale);
		}
	
		Font font = new Font(Font.DIALOG, Font.PLAIN, 3);
		g2.setFont(font);
		g2.setPaint(Color.GRAY);
		
		for (int i = 0; i < texts.size(); i++) {
			texts.get(i).draw(g2, scale);
		}
		
	}
}
