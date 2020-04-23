package ppvis_lb2;


import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

class DialogWindowAdd extends Dialog{
	  private String input1;
	  private Date input2;
	  private String input3;
	  private String input4;
	  private String input5;
	  private String input6;
	  private Footballer footballer;

	  public DialogWindowAdd(Shell parent) {
		  this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		  }
	
	  public DialogWindowAdd(Shell parent, int style) {
		    super(parent, style);
		    setText("Add new footballer");
		  }
	  
	  public Footballer open() {
		    Shell shell = new Shell(getParent(), getStyle());
		    shell.setText(getText());
		    createContents(shell);
		    shell.pack();
		    shell.open();
		    Display display = getParent().getDisplay();
		    while (!shell.isDisposed()) {
		      if (!display.readAndDispatch()) {
		        display.sleep();
		      }
		    }
		    return footballer;
		  }
	  
	  private void createContents(final Shell shell) {
		    shell.setLayout(new GridLayout(2, true));
			  
			Composite name = new Composite(shell, SWT.NONE);
			name.setLayout(new RowLayout(SWT.HORIZONTAL));
		    Label fullName = new Label(name, SWT.NONE);
			fullName.setText("Enter Full Name:");
			Text textName = new Text(name, SWT.SINGLE | SWT.BORDER);
		      
			Composite team = new Composite(shell, SWT.NONE);
			team.setLayout(new RowLayout(SWT.HORIZONTAL));
			Label teamLabel = new Label(team, SWT.NONE);
			teamLabel.setText("Enter club name:");
			Text clubName = new Text(team, SWT.SINGLE | SWT.BORDER);
			  
			Composite town = new Composite(shell, SWT.NONE);
			town.setLayout(new RowLayout(SWT.HORIZONTAL));
		    Label townLabel = new Label(town, SWT.NONE);
			townLabel.setText("Enter town name:");
			Text townName = new Text(town, SWT.SINGLE | SWT.BORDER);
			  
			Composite category = new Composite(shell, SWT.NONE);
			category.setLayout(new RowLayout(SWT.HORIZONTAL));
			Label categoryLabel = new Label(category, SWT.NONE);
			categoryLabel.setText("Enter category:");
			Text categoryName = new Text(category, SWT.SINGLE | SWT.BORDER);
			  
			Composite position = new Composite(shell, SWT.NONE);
			position.setLayout(new RowLayout(SWT.HORIZONTAL));
		    Label positionLabel = new Label(position, SWT.NONE);
			positionLabel.setText("Enter playing position:");
			Text positionName = new Text(position, SWT.SINGLE | SWT.BORDER);

			Composite date = new Composite(shell, SWT.NONE);
			date.setLayout(new RowLayout(SWT.HORIZONTAL));
		    Label dateLabel = new Label(date, SWT.NONE);
			dateLabel.setText("Choose date:");
		    DateTime calendar = new DateTime(date, SWT.CALENDAR);
			  
		    Button ok = new Button(shell, SWT.PUSH);
		    ok.setText("OK");
		    GridData data = new GridData(GridData.FILL_HORIZONTAL);
		    ok.setLayoutData(data);
		    ok.addSelectionListener(new SelectionAdapter() {
		      @SuppressWarnings("deprecation")
			public void widgetSelected(SelectionEvent event) {
		        input1 = textName.getText();
		        input2 = new Date(calendar.getYear()-1900, calendar.getMonth(), calendar.getDay());
		        input3 = clubName.getText();
		        input4 = townName.getText();
		        input5 = categoryName.getText();
		        input6 = positionName.getText();
		        footballer = new Footballer(input1, input2, input3, input4, input5, input6);
		        shell.close();
		      }
		    });

		    Button cancel = new Button(shell, SWT.PUSH);
		    cancel.setText("Cancel");
		    data = new GridData(GridData.FILL_HORIZONTAL);
		    cancel.setLayoutData(data);
		    cancel.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent event) {
		        footballer = null;
		        shell.close();
		      }
		    });
		    shell.setDefaultButton(ok);
		  }
		}
	  

