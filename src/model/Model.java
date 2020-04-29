package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.xml.sax.SAXException;

import dataUnit.Footballer;
import dialogs.DialogWindowDelete;
import parsers.SAXxml;

public class Model {
	private List<Footballer> footballers;
	private int pagesOverall;
	private int currentPage;
	private int numberOfRows;

	public Model() {
		footballers = new ArrayList();
		currentPage = 1;
		pagesOverall = 1;
		numberOfRows = 10;
	}

	public void getInfo(String path) throws ParserConfigurationException, SAXException, IOException {
		footballers = SAXxml.extractedData(path);
	}

	public List<Footballer> getFootballers() {
		return footballers;
	}

	public void addFootballer(Footballer newFootballer) {
		footballers.add(newFootballer);
	}

	public void delete(Shell shell) {
		DialogWindowDelete dialog = new DialogWindowDelete(shell);
		dialog.open(footballers);
		footballers = dialog.getFootballers();
	}

	public List<Footballer> extractBlock(Label label1, Label label2, Label label3) {
		if (footballers.size() % numberOfRows == 0)
			pagesOverall = footballers.size() / numberOfRows;
		else
			pagesOverall = footballers.size() / numberOfRows + 1;
		label3.setText("All elements:" + footballers.size() + "  ");
		label1.setText(currentPage + "/" + pagesOverall);
		if (footballers.size() > numberOfRows) {
			List<Footballer> temporary = new ArrayList<>();
			int counter = 0;
			for (int i = (currentPage - 1) * numberOfRows; i < footballers.size(); i++) {
				counter++;
				temporary.add(footballers.get(i));
				if (counter == numberOfRows)
					break;
			}
			label2.setText("Elements on this page:" + counter + " ");
			return temporary;
		} else
			return footballers;
	}

	public int getPagesOverall() {
		return pagesOverall;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setNumberOfRow(int num) {
		numberOfRows = num;
	}

	public void setCurrentPage(int num) {
		currentPage = num;
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
