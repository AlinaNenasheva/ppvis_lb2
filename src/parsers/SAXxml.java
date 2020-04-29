package parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dataUnit.Footballer;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXxml {
	public static List<Footballer> extractedData(String path)
			throws ParserConfigurationException, SAXException, IOException {
		List<Footballer> footballers = new ArrayList<>();
		DefaultHandler handler = new DefaultHandler() {
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if (qName.equals("footballer")) {
					String name = attributes.getValue("name");
					String sdate = attributes.getValue("date");
					String club = attributes.getValue("club");
					String hometown = attributes.getValue("hometown");
					String category = attributes.getValue("category");
					String position = attributes.getValue("position");
					Date date = new Date();
					try {
						date = toDate(sdate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					footballers.add(new Footballer(name, date, club, hometown, category, position));
				}
			}
		};
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(new File(path), handler);
		return footballers;
	}

	private static Date toDate(String sdate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = formatter.parse(sdate);
		return date;
	}

}
