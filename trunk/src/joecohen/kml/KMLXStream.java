package joecohen.kml;

import com.thoughtworks.xstream.XStream;

public class KMLXStream {

	XStream xstream;
	
	public KMLXStream() {
		
		xstream = new XStream();
	
		xstream.alias("kml", Kml.class);
		xstream.useAttributeFor(Kml.class, "xmlns");
		xstream.useAttributeFor(Kml.class, "hint");
		
		
        xstream.alias("Document", Document.class);
        xstream.alias("Style", Style.class);
        xstream.useAttributeFor(Style.class, "id");
        
        xstream.alias("Folder", Folder.class);
        xstream.alias("Placemark", Placemark.class);
        xstream.alias("GroundOverlay", GroundOverlay.class);
        xstream.alias("LinearRing", LinearRing.class);
        xstream.alias("Polygon", Polygon.class);
        xstream.alias("outerBoundaryIs", OuterBoundaryIs.class);
        
        xstream.addImplicitCollection(Document.class, "styles", Style.class);
        xstream.addImplicitCollection(Folder.class, "placemarks", Placemark.class);
        xstream.addImplicitCollection(Folder.class, "groundoverlays", GroundOverlay.class);
   
	}
	
	public String toKML(Kml kml){
		
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xstream.toXML(kml);
	}
	
	public Kml fromKML(String raw){
		
		return null;
	}
}
