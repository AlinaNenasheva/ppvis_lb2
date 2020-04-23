package ppvis_lb2;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
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

class DialogWindowSearch extends Dialog{
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
	
	  public DialogWindowSearch(Shell parent, int style) {
		    super(parent, style);
		    setText("Search for footballer(s)");
		  }
	  
		public ArrayList<Footballer> search(int key, ArrayList<Footballer> footballers) throws ParseException{
			ArrayList<Footballer> foundFootballers = new ArrayList<>();
			switch(key) {
			  case 1:
				@SuppressWarnings("deprecation") 
				Date date = new Date(calendar.getYear()-1900, calendar.getMonth(), calendar.getDay());
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
		
		public ArrayList<Footballer> byNameAndDate(String name, Date date, ArrayList<Footballer> footballers) {
			ArrayList<Footballer> foundFootballers = new ArrayList<>();
			for(int i = 0; i < footballers.size(); i++) {
			   if( footballers.get(i).getName().contains(name) && footballers.get(i).getDate().compareTo(date) == 0) {
					   foundFootballers.add(footballers.get(i));
				   	}
			   }
			   return foundFootballers;
		}
		
		public ArrayList<Footballer> byCategoryOrPosition(String category, String position, ArrayList<Footballer> footballers) {
			ArrayList<Footballer> foundFootballers = new ArrayList<>();
			for(int i = 0; i < footballers.size(); i++) {
			   if(footballers.get(i).getCategory().equals(category) || footballers.get(i).getPosition().equals(position)) {
			    foundFootballers.add(footballers.get(i));
			    }
			}
			   return foundFootballers;
	    }
		
		public ArrayList<Footballer> byTeamOrTown(String team, String town, ArrayList<Footballer> footballers) {
			ArrayList<Footballer> foundFootballers = new ArrayList<>();
			for(int i = 0; i < footballers.size(); i++) {
			   if(footballers.get(i).getTeam().equals(team) || footballers.get(i).getTown().equals(town)) {
			    foundFootballers.add(footballers.get(i));
			    }
			}
			   return foundFootballers;
	    }
	  
	  public void open(ArrayList<Footballer> footballers) {
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
	  
	  public void fillTheRows(ArrayList<Footballer> footballers) {
	    	table.removeAll();
	    	for(int i = 0; i< footballers.size(); i++) {
	        TableItem item = new TableItem(table, SWT.NONE);
	        item.setText(0, footballers.get(i).getName());
	        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
	        String sdate = formatter.format(footballers.get(i).getDate());
	        item.setText(1, sdate);
	        item.setText(2, footballers.get(i).getTeam());
	        item.setText(3, footballers.get(i).getTown());
	        item.setText(4, footballers.get(i).getCategory());
	        item.setText(5, footballers.get(i).getPosition());
	        }
	    	table.setBounds(5, 99, 375, 197);;
	    }
	  	  
	  private void createContents(final Shell shell, ArrayList<Footballer> footballers) {
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

	        for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	          TableColumn column = new TableColumn(table, SWT.NONE);
	          column.setText(titles[loopIndex]);
	        }
	        table.setBounds(10,10,250,250);
	        table.setLinesVisible(true);
	        table.setHeaderVisible(true);
	        for (int i = 0; i < titles.length; i++) {
	            table.getColumn(i).pack();
	          }
	        
		    search.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try {
					if(buttonNameAndDate.getSelection()) fillTheRows(search(1,footballers));
					else if(buttonCategoryOrPosition.getSelection()) fillTheRows(search(2,footballers));
					else fillTheRows(search(3, footballers));
						
				} catch (ParseException e) {
					e.printStackTrace();
				}
		      }
		    });
		    shell.setDefaultButton(search);
		  }
		}
	  


