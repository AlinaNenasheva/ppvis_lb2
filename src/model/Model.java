package model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
	private static String deleteFromTableString;

	public Model() {
		footballers = new ArrayList();
		currentPage = 1;
		pagesOverall = 1;
		numberOfRows = 10;
	}

	public String getDeleteFromTableString() {
		return deleteFromTableString;
	}

	public void getInfo(String path) throws ParserConfigurationException, SAXException, IOException {
		footballers = SAXxml.extractedData(path);
	}

	public List<Footballer> getFootballers() {
		return footballers;
	}

	public void setFootballers(List<Footballer> footballers2) {
		footballers = footballers2;
	}

	public void addFootballer(Footballer newFootballer) {
		footballers.add(newFootballer);
	}

	public List<Footballer> removedFootballers(List<Footballer> oldFootballers, List<Footballer> newFootballers) {
		List<Footballer> removedFootballers = new ArrayList<>();
		for (Footballer footballer : oldFootballers) {
			if (!newFootballers.contains(footballer)) {
				removedFootballers.add(footballer);
			}
		}

		return removedFootballers;
	}

	public void delete(Shell shell) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException, IOException {
		List<Footballer> oldFootballers = new ArrayList<>(footballers);
		DialogWindowDelete dialog = new DialogWindowDelete(shell);
		dialog.open(footballers);
		footballers = dialog.getFootballers();
		List<Footballer> removedFootballers = removedFootballers(oldFootballers, footballers);
		DatabaseHandler db = new DatabaseHandler();
		for (Footballer footballer : removedFootballers) {
			deleteFromTableString = "DELETE FROM " + Const.FOOTBALLER_TABLE + " WHERE " + Const.FOOTBALLER_NAME + "="
					+ "'" + footballer.getName() + "'" + " AND " + Const.FOOTBALLER_DATE + "=" + "'"
					+ footballer.getDate() + "'" + " AND " + Const.FOOTBALLER_TEAM + "=" + "'" + footballer.getTeam()
					+ "'" + " AND " + Const.FOOTBALLER_HOMETOWN + "=" + "'" + footballer.getTown() + "'" + " AND "
					+ Const.FOOTBALLER_CATEGORY + "=" + "'" + footballer.getCategory() + "'" + " AND "
					+ Const.FOOTBALLER_POSITION + "=" + "'" + footballer.getPosition() + "'" + ";";
			db.deleteFromTable(deleteFromTableString);
		}
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
