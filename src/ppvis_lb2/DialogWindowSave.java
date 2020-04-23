package ppvis_lb2;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import org.eclipse.swt.widgets.Shell;

class DialogWindowSave{
	  public String path;
	  public FileDialog dialog;
	  private static final String[][] FILTERS = {{"Файлы XML (*.xml)" , "*.xml"}};

	  DialogWindowSave(Shell shell){
		  dialog = new FileDialog(shell, SWT.SAVE);
	  }

	  private void setFilters(FileDialog dialog)
	  {
	      String[] names = new String[FILTERS.length];
	      String[] exts  = new String[FILTERS.length];
	      for (int i = 0; i < FILTERS.length; i++) {
	          names[i] = FILTERS[i][0];
	          exts [i] = FILTERS[i][1];
	      }
	      dialog.setFilterNames(names);
	      dialog.setFilterExtensions(exts);
	  }
	  
	  public String open(Shell shell) {
		  dialog = new FileDialog(shell, SWT.SAVE);
		  setFilters(dialog);
		  String path = dialog.open();
		  return path;
		  
		  }
	 
		}
	  


