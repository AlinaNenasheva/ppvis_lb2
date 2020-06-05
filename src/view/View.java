package view;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import dataUnit.Footballer;

public class View {
	private Button load, save, search, delete, add, firstPage, nextPage, previousPage, lastPage, ok;
	private Table table;
	private Shell shell;
	private Display display;
	private Label pagesCounter, allElements, currentNumberElements;
	private Text textChangeNumber;
	private MenuItem item1, item2, item3, item4, item5, item;

	public View() {
		display = new Display();
		shell = new Shell(display);
		shell.setText("PPVIS_LB2");
		shell.setSize(400, 450);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		shell.setLayout(rowLayout);
		Menu dialogMenuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(dialogMenuBar);
		item = new MenuItem(dialogMenuBar, SWT.CASCADE);
		item.setText("Dialogs");
		Menu submenu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(submenu);
		item1 = new MenuItem(submenu, SWT.PUSH);
		item2 = new MenuItem(submenu, SWT.PUSH);
		item3 = new MenuItem(submenu, SWT.PUSH);
		item4 = new MenuItem(submenu, SWT.PUSH);
		item5 = new MenuItem(submenu, SWT.PUSH);
		item1.setText("Delete");
		item2.setText("Load");
		item3.setText("Save");
		item4.setText("Search");
		item5.setText("Add");
		Composite methodsNavigator = new Composite(shell, SWT.NONE);
		methodsNavigator.setLayout(new RowLayout(SWT.HORIZONTAL));
		delete = new Button(methodsNavigator, SWT.PUSH);
		delete.setText("Delete");
		load = new Button(methodsNavigator, SWT.PUSH);
		load.setText("Load");
		save = new Button(methodsNavigator, SWT.PUSH);
		save.setText("Save");
		search = new Button(methodsNavigator, SWT.PUSH);
		search.setText("Search");
		add = new Button(methodsNavigator, SWT.PUSH);
		add.setText("Add");
		allElements = new Label(shell, SWT.NONE);
		allElements.setText("All elements:0  ");
		currentNumberElements = new Label(shell, SWT.NONE);
		currentNumberElements.setText("Elements on this page:0  ");
		Composite changeNum = new Composite(shell, SWT.NONE);
		changeNum.setLayout(new RowLayout(SWT.HORIZONTAL));
		Label changeNumber = new Label(changeNum, SWT.NONE);
		changeNumber.setText("Set number of elements:");
		textChangeNumber = new Text(changeNum, SWT.NONE);
		textChangeNumber.setText("10");
		ok = new Button(changeNum, SWT.NONE);
		ok.setText("OK");
		Composite pagesNavigator = new Composite(shell, SWT.NONE);
		pagesNavigator.setLayout(new RowLayout(SWT.HORIZONTAL));
		firstPage = new Button(pagesNavigator, SWT.PUSH);
		firstPage.setText("<<");
		previousPage = new Button(pagesNavigator, SWT.PUSH);
		previousPage.setText("<");
		pagesCounter = new Label(pagesNavigator, SWT.LEFT);
		pagesCounter.setText("  1/1  ");
		nextPage = new Button(pagesNavigator, SWT.PUSH);
		nextPage.setText(">");
		lastPage = new Button(pagesNavigator, SWT.PUSH);
		lastPage.setText(">>");
		table = new Table(shell, SWT.BORDER);
		String[] titles = { "Full Name", "Date of Birth", "Football Team", "Hometown", "Category", "Position" };

		for (String loop : titles) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(loop);
		}

		table.setBounds(10, 10, 250, 250);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		shell.open();

	}

	public void display() {
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	
	
	public MenuItem getItem1() {
		return item1;
	}

	public MenuItem getItem2() {
		return item2;
	}

	public MenuItem getItem3() {
		return item3;
	}

	public MenuItem getItem4() {
		return item4;
	}

	public MenuItem getItem5() {
		return item5;
	}

	public Button getLoadButton() {
		return load;
	}

	public Button getSaveButton() {
		return save;
	}

	public Button getSearchButton() {
		return search;
	}

	public Button getDeleteButton() {
		return delete;
	}

	public Button getAddButton() {
		return add;
	}

	public Button getFirstButton() {
		return firstPage;
	}

	public Button getNextButton() {
		return nextPage;
	}

	public Button getPreviousButton() {
		return previousPage;
	}

	public Button getLastButton() {
		return lastPage;
	}

	public Button getOKButton() {
		return ok;
	}

	public Table getTable() {
		return table;
	}

	public Label getPageLabel() {
		return pagesCounter;
	}

	public Label getCurElemsLabel() {
		return currentNumberElements;
	}

	public Label getAllElemsLabel() {
		return allElements;
	}

	public int getText() {
		int result = Integer.parseInt(textChangeNumber.getText());
		return result;
	}

	public Shell getShell() {
		return shell;
	}

	public Display getDisplay() {
		return display;
	}

	public void fillTheRows(List<Footballer> footballers) {
		table.removeAll();
		for (Footballer footballer : footballers) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, footballer.getName());
			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			String sdate = formatter.format(footballer.getDate());
			item.setText(1, sdate);
			item.setText(2, footballer.getTeam());
			item.setText(3, footballer.getTown());
			item.setText(4, footballer.getCategory());
			item.setText(5, footballer.getPosition());
		}

		table.setBounds(3, 164, 383, (table.getItemCount() + 1) * table.getItemHeight());
	}

}
