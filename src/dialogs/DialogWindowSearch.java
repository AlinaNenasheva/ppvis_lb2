package dialogs;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import dataUnit.Footballer;

public class DialogWindowSearch extends Dialog {
	private DateTime calendar;
	private Button buttonNameAndDate;
	private Button buttonTeamOrTown;
	private Button buttonCategoryOrPosition;
	private Table table;
	private Text firstCondition;
	private Text secondCondition;

	public DialogWindowSearch(Shell parent) {
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	private DialogWindowSearch(Shell parent, int style) {
		super(parent, style);
		setText("Search for footballer(s)");
	}

	private List<Footballer> search(int key, List<Footballer> footballers) throws ParseException {
		List<Footballer> foundFootballers = new ArrayList<>();
		switch (key) {
		case 1:
			@SuppressWarnings("deprecation")
			Date date = new Date(calendar.getYear() - 1900, calendar.getMonth(), calendar.getDay());
			foundFootballers = byNameAndDate(firstCondition.getText(), date, footballers);
			break;
		case 2:
			foundFootballers = byCategoryOrPosition(firstCondition.getText(), secondCondition.getText(), footballers);
			break;
		case 3:
			foundFootballers = byTeamOrTown(firstCondition.getText(), secondCondition.getText(), footballers);
			break;
		}
		return foundFootballers;
	}

	private List<Footballer> byNameAndDate(String name, Date date, List<Footballer> footballers) {
		List<Footballer> foundFootballers = new ArrayList<>();
		for (Footballer footballer : footballers) {
			if (footballer.getName().contains(name) && footballer.getDate().compareTo(date) == 0) {
				foundFootballers.add(footballer);
			}
		}
		return foundFootballers;
	}

	private List<Footballer> byCategoryOrPosition(String category, String position, List<Footballer> footballers) {
		List<Footballer> foundFootballers = new ArrayList<>();
		for (Footballer footballer : footballers) {
			if (footballer.getCategory().equals(category) || footballer.getPosition().equals(position)) {
				foundFootballers.add(footballer);
			}
		}
		return foundFootballers;
	}

	private List<Footballer> byTeamOrTown(String team, String town, List<Footballer> footballers) {
		List<Footballer> foundFootballers = new ArrayList<>();
		for (Footballer footballer : footballers) {
			if (footballer.getTeam().equals(team) || footballer.getTown().equals(town)) {
				foundFootballers.add(footballer);
			}
		}
		return foundFootballers;
	}

	public void open(List<Footballer> footballers) {
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		shell.setSize(1000, 330);
		createContents(shell, footballers);
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void fillTheRows(List<Footballer> footballers) {
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
		table.setBounds(5, 99, 375, 197);
		;
	}

	private void createContents(final Shell shell, List<Footballer> footballers) {
		shell.setLayout(new GridLayout(2, true));

		Button search = new Button(shell, SWT.PUSH);
		search.setText("Search");
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		search.setLayoutData(data);
		Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		data = new GridData(GridData.FILL_HORIZONTAL);
		cancel.setLayoutData(data);
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});

		Group searchGroup = new Group(shell, SWT.NONE);
		searchGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
		searchGroup.setText("Search by");
		buttonCategoryOrPosition = new Button(searchGroup, SWT.RADIO);
		buttonCategoryOrPosition.setText("Category OR position");
		buttonTeamOrTown = new Button(searchGroup, SWT.RADIO);
		buttonTeamOrTown.setText("Club OR hometown");
		buttonNameAndDate = new Button(searchGroup, SWT.RADIO);
		buttonNameAndDate.setText("Name AND date");
		buttonCategoryOrPosition.setSelection(true);
		Group conditions = new Group(shell, SWT.NONE);
		conditions.setLayout(new RowLayout(SWT.HORIZONTAL));
		Label conditionsLabel = new Label(conditions, SWT.NONE);
		conditionsLabel.setText("Enter category or position");
		firstCondition = new Text(conditions, SWT.NONE);
		secondCondition = new Text(conditions, SWT.NONE);
		calendar = new DateTime(conditions, SWT.NONE);
		calendar.setVisible(false);
		buttonNameAndDate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				conditionsLabel.setText("Enter name and date");
				secondCondition.setVisible(false);
				calendar.setVisible(true);

			}
		});
		buttonTeamOrTown.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				conditionsLabel.setText("Enter team or town");
				calendar.setVisible(false);
				secondCondition.setVisible(true);
			}
		});

		buttonCategoryOrPosition.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				conditionsLabel.setText("Enter category or position");
				calendar.setVisible(false);
				secondCondition.setVisible(true);
			}
		});

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

		search.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try {
					if (buttonNameAndDate.getSelection())
						fillTheRows(search(1, footballers));
					else if (buttonCategoryOrPosition.getSelection())
						fillTheRows(search(2, footballers));
					else
						fillTheRows(search(3, footballers));

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		shell.setDefaultButton(search);
	}
}
