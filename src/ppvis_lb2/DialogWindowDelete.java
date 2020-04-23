package ppvis_lb2;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

class DialogWindowDelete extends Dialog{
	  private DateTime calendar;
	  private Button buttonNameAndDate;
	  private Button buttonTeamOrTown;
	  private Button buttonCategoryOrPosition;
	  private Text firstCondition;
	  private Text secondCondition;
	  public ArrayList<Footballer> footballers;
	  private int counterDeletedElements = 0;
	
	  public DialogWindowDelete(Shell parent) {
		  this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		  }
	
	  public DialogWindowDelete(Shell parent, int style) {
		    super(parent, style);
		    setText("Delete footballer(s)");
		  }
	  
		public ArrayList<Footballer> delete(int key) throws ParseException{
			ArrayList<Footballer> foundFootballers = new ArrayList<>();
			switch(key) {
			  case 1:
				@SuppressWarnings("deprecation") 
				Date date = new Date(calendar.getYear()-1900, calendar.getMonth(), calendar.getDay());
			    foundFootballers = byNameAndDate(firstCondition.getText(), date);
			    break;
			  case 2:
			    foundFootballers = byCategoryOrPosition(firstCondition.getText(), secondCondition.getText());
			    break;
			  case 3:
				foundFootballers = byTeamOrTown(firstCondition.getText(), secondCondition.getText());
				break;
			}
			return foundFootballers;
		}
		
		public ArrayList<Footballer> byNameAndDate(String name, Date date) {
			ArrayList<Footballer> foundFootballers = new ArrayList<>();
			Iterator<Footballer> footballerIterator = footballers.iterator();
			while(footballerIterator.hasNext()) {
            	   Footballer nextFootballer = footballerIterator.next();
				   if( nextFootballer.getName().contains(name) && nextFootballer.getDate().compareTo(date) == 0) {
				       footballerIterator.remove();
				       counterDeletedElements++;
				   }
				}
			   return foundFootballers;
		}
		
		public ArrayList<Footballer> byCategoryOrPosition(String category, String position) {
			ArrayList<Footballer> foundFootballers = new ArrayList<>();
			Iterator<Footballer> footballerIterator = footballers.iterator();
			while(footballerIterator.hasNext()) {
            	   Footballer nextFootballer = footballerIterator.next();
				   if(nextFootballer.getCategory().equals(category) || nextFootballer.getPosition().equals(position)) {
				       footballerIterator.remove();
				       counterDeletedElements++;
				   }
				}
			   return foundFootballers;
		}
		
		public ArrayList<Footballer> byTeamOrTown(String team, String town) {
			ArrayList<Footballer> foundFootballers = new ArrayList<>();
			Iterator<Footballer> footballerIterator = footballers.iterator();
			while(footballerIterator.hasNext()) {
            	   Footballer nextFootballer = footballerIterator.next();
				   if(nextFootballer.getTeam().equals(team) || nextFootballer.getTown().equals(town)) {
				       footballerIterator.remove();			
				       counterDeletedElements++;
				   }
				}
			   return foundFootballers;
		}
	  
	  public void open(ArrayList<Footballer> footballers) {
		    Shell shell = new Shell(getParent(), getStyle());
		    shell.setText(getText());
	        shell.setSize(1000, 330);
	        this.footballers = footballers;
		    createContents(shell); 
		    shell.pack();
		    shell.open();
		    Display display = getParent().getDisplay();
		    while (!shell.isDisposed()) {
		      if (!display.readAndDispatch()) {
		        display.sleep();
		      }
		    }
		  }
	  
	  	  
	  private void createContents(final Shell shell) {
		    shell.setLayout(new GridLayout(2, true));
		    
		    Button delete = new Button(shell, SWT.PUSH);
		    delete.setText("Delete");
		    GridData data = new GridData(GridData.FILL_HORIZONTAL);
		    delete.setLayoutData(data);
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
				
		    delete.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				MessageBox messageBox= new MessageBox(shell, SWT.NONE);
				messageBox.setText("Informational list");
				try {
					if(buttonNameAndDate.getSelection()) {
						delete(1);
						if(counterDeletedElements != 0) {
						messageBox.setMessage("Number of deleted records:"+counterDeletedElements);
						messageBox.open();
					    counterDeletedElements = 0;
					    }else {
					    	messageBox.setMessage("There is(are) no such footballer(s)");
					    	messageBox.open();
					    }
					}
					else if(buttonCategoryOrPosition.getSelection()) { 
						delete(2);
						if(counterDeletedElements != 0) {
						messageBox.setMessage("Number of deleted records:"+counterDeletedElements);
						messageBox.open();
					    counterDeletedElements = 0;
					    }else {
					    	messageBox.setMessage("There is(are) no such footballer(s)");
					    	messageBox.open();
					    }
					}
					else {
						delete(3);
						if(counterDeletedElements != 0) {
						messageBox.setMessage("Number of deleted records:"+counterDeletedElements);
						messageBox.open();
					    counterDeletedElements = 0;
					    }else {
					    	messageBox.setMessage("There is(are) no such footballer(s)");
					    	messageBox.open();
					    }
					}
						
				} catch (ParseException e) {
					e.printStackTrace();
				}
				shell.close();
		      }
		    });
		    shell.setDefaultButton(delete);
		  }
		}
	  


