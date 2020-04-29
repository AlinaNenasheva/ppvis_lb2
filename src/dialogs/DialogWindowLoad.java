package dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import org.eclipse.swt.widgets.Shell;

public class DialogWindowLoad {
	private static final String[][] FILTERS = { { "Файлы XML (*.xml)", "*.xml" } };

	private void setFilters(FileDialog dialog) {
		String[] names = new String[FILTERS.length];
		String[] exts = new String[FILTERS.length];
		for (int i = 0; i < FILTERS.length; i++) {
			names[i] = FILTERS[i][0];
			exts[i] = FILTERS[i][1];
		}
		dialog.setFilterNames(names);
		dialog.setFilterExtensions(exts);
	}

	public String open(Shell shell) {
		FileDialog dlg = new FileDialog(shell, SWT.OPEN);
		setFilters(dlg);
		String path = dlg.open();
		return path;

	}

}