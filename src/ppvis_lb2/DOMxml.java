 package ppvis_lb2;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class DOMxml {
 public static void downloadedData(ArrayList<Footballer> list, String path) throws ParserConfigurationException, IOException, SAXException, XMLStreamException, TransformerFactoryConfigurationError, TransformerException {
  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
  DocumentBuilder builder = factory.newDocumentBuilder();
  Document document = builder.newDocument();
  Element footballers = document.createElement("footballers");
  document.appendChild(footballers);
  for(int i = 0; i < list.size(); i++) {
   Element footballer = document.createElement("footballer");
   footballer.setAttribute("name", list.get(i).getName());
   Format formatter = new SimpleDateFormat("dd/MM/yyyy");
   String sdate = formatter.format(list.get(i).getDate());
   footballer.setAttribute("date", sdate);
   footballer.setAttribute("club", list.get(i).getTeam());
   footballer.setAttribute("hometowm", list.get(i).getTown());
   footballer.setAttribute("category", list.get(i).getCategory());
   footballer.setAttribute("position", list.get(i).getPosition());
   footballers.appendChild(footballer);
  }
  Transformer t = TransformerFactory.newInstance().newTransformer();
  t.setOutputProperty(OutputKeys.INDENT, "yes");
  t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(path)));

 }
 
 public static void main(String[] args) {}
 }
