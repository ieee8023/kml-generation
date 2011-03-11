package joecohen.kml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.csvreader.CsvReader;

public class NYCancerCases {

	public static void main(String[] args) throws IOException {

		Kml kml = new Kml();
		kml.Document = new Document();
		kml.Document.open = "1";
		kml.Document.name = "Zip Codes";
		kml.Document.description = "Zip Codes - Joseph Paul Cohen";
		Style s = new Style();
		s.id = "transRedPoly";
		s.PolyStyle.color = "ff0000ff";
		s.PolyStyle.outline = "0";
		s.LineStyle.width = "0";
		kml.Document.styles.add(s);
		
		Placemark p;
		
		CsvReader reader = new CsvReader("zips.csv");
		
		
		Map<String, Double> zip2Lat = new HashMap<String, Double>();
		Map<String, Double> zip2Lng = new HashMap<String, Double>();
		
		while (reader.readRecord()){
			Double longitude = Double.parseDouble(reader.get(4));
			Double latitude = Double.parseDouble(reader.get(5));
			String zip = reader.get(1);
			
			zip2Lat.put(zip,latitude);
			zip2Lng.put(zip,longitude);
			
		}
		
		
		//for (String zip : zip2Lat.keySet()){
		//	System.out.println(zip + ":" + zip2Lat.get(zip) + ":" + zip2Lng.get(zip));
		
		//}
			
		reader = new CsvReader("NYzipBreastCancer.csv");
		reader.readHeaders();
		
		final Double side = .01;
		
		while (reader.readRecord()){
			try{

			String zip = reader.get(0).substring(2);
			Integer amount = Integer.parseInt(reader.get(1));
			
			
			Double longitude = zip2Lng.get(zip);
			Double latitude = zip2Lat.get(zip);
			
			//scale the height to something fun
			Integer height = (int) Math.pow(amount*10,1.4);
			
			p = new Placemark();
			p.name=reader.get(1) + reader.get(3);
			p.styleUrl = "#transRedPoly";
			p.Polygon = new Polygon();
			p.Polygon.extrude = "1";
			p.Polygon.altitudeMode = "absolute";
			p.Polygon.outerBoundaryIs.LinearRing.coordinates = "\n" +
					"-" + (longitude+side) + "," +
					(latitude+side) + "," +
					height + "\n" +
					"-" + (longitude+side) + "," + (latitude-side) + "," + height + "\n" +
					"-" + (longitude-side) + "," + (latitude-side) + "," + height + "\n" +
					"-" + (longitude-side) + "," + (latitude+side) + "," + height + "\n" +
					"-" + (longitude+side) + "," + (latitude+side) + "," + height + "\n";
	
			kml.Document.Folder.placemarks.add(p);
			}catch(Exception e){}
		}
		
		System.out.println(kml.Document.Folder.placemarks.size() + " Placemarks");
		
		KMLXStream x = new KMLXStream();
		
		x.toKMLFile(kml, "cancerNY", true, true);
	}

}
