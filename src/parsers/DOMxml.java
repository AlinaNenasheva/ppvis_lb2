package parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import dataUnit.Footballer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DOMxml {
	public void downloadedData(List<Footballer> list, String path) throws ParserConfigurationException, IOException,
			SAXException, XMLStreamException, TransformerFactoryConfigurationError, TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		Element footballers = document.createElement("footballers");
		document.appendChild(footballers);

		for (Footballer listElement : list) {
			Element footballer = document.createElement("footballer");
			footballer.setAttribute("name", listElement.getName());
			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			String sdate = formatter.format(listElement.getDate());
			footballer.setAttribute("date", sdate);
			footballer.setAttribute("club", listElement.getTeam());
			footballer.setAttribute("hometowm", listElement.getTown());
			footballer.setAttribute("category", listElement.getCategory());
			footballer.setAttribute("position", listElement.getPosition());
			footballers.appendChild(footballer);
		}

		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(path)));

	}

}
