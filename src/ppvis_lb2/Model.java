package ppvis_lb2;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.xml.sax.SAXException;


public class Model {
	private ArrayList<Footballer> footballers;
	public int pagesOverall;
	public int currentPage;
	public int numberOfRows;

	Model(){
		footballers = new ArrayList();
		currentPage = 1; 
		pagesOverall = 1;
		numberOfRows = 10;
	}
	
	public void getInfo(String path) throws ParserConfigurationException, SAXException, IOException {
		footballers = SAXxml.extractedData(path);
	}
	
	public ArrayList<Footballer> getFootballers() {
		return footballers;
	}

	public void addFootballer(Footballer newFootballer) {
		footballers.add(newFootballer);
	}
	
	public void delete(Shell shell ) {
		   	DialogWindowDelete dialog = new DialogWindowDelete(shell);
		   	dialog.open(footballers);
		   	footballers = dialog.footballers;
	}
	
	
	
	public ArrayList<Footballer> extractBlock(Label label1, Label label2, Label label3) {
		if(footballers.size() % numberOfRows == 0)
			pagesOverall = footballers.size()/numberOfRows;
			else pagesOverall = footballers.size()/ numberOfRows + 1;
		label3.setText("All elements:"+footballers.size()+"  ");
		label1.setText(currentPage + "/" + pagesOverall);
		if(footballers.size() > numberOfRows) {
			ArrayList<Footballer> temporary = new ArrayList<>();
			int counter = 0;
			for(int i = (currentPage - 1)*numberOfRows; i < footballers.size(); i++) {
				counter++;
				temporary.add(footballers.get(i));
				if(counter == numberOfRows) break;
			}
			label2.setText("Elements on this page:"+counter+" ");
			return temporary;
		}
		else
		return footballers;
	}
	
	public void incrementPage() {
		currentPage++;
	}
	
	public void decrementPage() {
		currentPage--;
	}
	
	public void toFirstPage() {
		currentPage = 1;
	}
	
	public void toLastPage() {
		currentPage = pagesOverall;
	}
	
}


