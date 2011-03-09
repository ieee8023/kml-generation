package joecohen.kml;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.csvreader.CsvReader;

public class ZipCodesMA {

	public static void main(String[] args) throws IOException {

		Kml kml = new Kml();
		kml.Document = new Document();
		kml.Document.open = "1";
		kml.Document.name = "MA Zip Codes";
		kml.Document.description = "MA Zip Codes - Joseph Paul Cohen";
		Style s = new Style();
		s.id = "transRedPoly";
		s.PolyStyle.color = "ff0000ff";
		s.PolyStyle.outline = "0";
		s.LineStyle.width = "0";
		kml.Document.styles.add(s);
		
		Placemark p;
		
		CsvReader reader = new CsvReader("zips.csv");
		
		final Double side = .01;
		
		while (reader.readRecord()){
			if ("MA".equals(reader.get(2))){
				Double longitude = Double.parseDouble(reader.get(4));
				Double latitude = Double.parseDouble(reader.get(5));
				Integer zip = Integer.parseInt(reader.get(1));
				
				//scale the height to something fun
				Integer height = (int) Math.pow(zip,1.4);
				
				p = new Placemark();
				p.name=reader.get(1) + reader.get(3);
				p.styleUrl = "#transRedPoly";
				p.Polygon = new Polygon();
				p.Polygon.extrude = "1";
				p.Polygon.altitudeMode = "absolute";
				p.Polygon.outerBoundaryIs.LinearRing.coordinates = "\n" +
						"-" + (longitude+side) + "," + (latitude+side) + "," + height + "\n" +
						"-" + (longitude+side) + "," + (latitude-side) + "," + height + "\n" +
						"-" + (longitude-side) + "," + (latitude-side) + "," + height + "\n" +
						"-" + (longitude-side) + "," + (latitude+side) + "," + height + "\n" +
						"-" + (longitude+side) + "," + (latitude+side) + "," + height + "\n";
		
				kml.Document.Folder.placemarks.add(p);
			}
		}
		
		System.out.println(kml.Document.Folder.placemarks.size() + " Placemarks");
		
		KMLXStream x = new KMLXStream();
		
		x.toKMLFile(kml, "zipsMA", true, true);
		
	}

}
