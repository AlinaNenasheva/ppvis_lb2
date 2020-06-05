package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.xml.sax.SAXException;

import dataUnit.Footballer;
import dialogs.DialogWindowAdd;
import dialogs.DialogWindowLoad;
import dialogs.DialogWindowSave;
import dialogs.DialogWindowSearch;
import model.DatabaseHandler;
import model.Model;
import parsers.DOMxml;
import view.View;

public class Controller {

	private Model model;
	private View view;

	public Controller() {
		model = new Model();
		view = new View();
		execute();
	}

	private void disableButtons() {
		if (model.getCurrentPage() == 1) {
			view.getFirstButton().setEnabled(false);
			view.getPreviousButton().setEnabled(false);
		} else {
			view.getFirstButton().setEnabled(true);
			view.getPreviousButton().setEnabled(true);
		}
		if (model.getCurrentPage() == model.getPagesOverall()) {
			view.getLastButton().setEnabled(false);
			view.getNextButton().setEnabled(false);
		} else {
			view.getLastButton().setEnabled(true);
			view.getNextButton().setEnabled(true);
		}

	}

	private void execute() {
		disableButtons();
		view.getLoadButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
//					DialogWindowLoad dialog = new DialogWindowLoad();
//					String path = "";
//					path = dialog.open(view.getShell());
//					if (path != null)
//						model.getInfo(path);
					DatabaseHandler dataBaseHandler = new DatabaseHandler();
					try {
						dataBaseHandler.userForLoad(view, model);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				disableButtons();
			}

		});

		view.getItem2().addListener(SWT.Selection, event -> {
			try {
				DialogWindowLoad dialog = new DialogWindowLoad();
				String path = "";
				path = dialog.open(view.getShell());
				if (path != null)
					model.getInfo(path);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
			view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
			disableButtons();

		});

		view.getSaveButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
//					DialogWindowSave dialog = new DialogWindowSave();
//					String path = "";
//					path = dialog.open(view.getShell());
//					if (path != null) {
//						DOMxml domParser = new DOMxml();
//						domParser.downloadedData(model.getFootballers(), path);
//					}
					DatabaseHandler dataBaseHandler = new DatabaseHandler();
					dataBaseHandler.saveInSQL(model.getFootballers());
				} catch (TransformerFactoryConfigurationError e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		view.getItem3().addListener(SWT.Selection, event -> {
			try {
				DialogWindowSave dialog = new DialogWindowSave();
				String path = "";
				path = dialog.open(view.getShell());
				if (path != null) {
					DOMxml domParser = new DOMxml();
					domParser.downloadedData(model.getFootballers(), path);
				}
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}

		});

		view.getAddButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DialogWindowAdd dialog = new DialogWindowAdd(view.getShell());
				Footballer newFootballer = dialog.open();
				if (newFootballer != null) {
					model.addFootballer(newFootballer);
					DatabaseHandler dataBaseHandler = new DatabaseHandler();
					try {
						dataBaseHandler.addFootballer(newFootballer);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException
							| ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.print("asa");
					}
					view.fillTheRows(
							model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
					disableButtons();
				}
			}
		});

		view.getItem5().addListener(SWT.Selection, event -> {
			DialogWindowAdd dialog = new DialogWindowAdd(view.getShell());
			Footballer newFootballer = dialog.open();
			if (newFootballer != null) {
				model.addFootballer(newFootballer);
				view.fillTheRows(
						model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
				disableButtons();
			}

		});

		view.getSearchButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DialogWindowSearch dialog = new DialogWindowSearch(view.getShell());
				dialog.open(model.getFootballers());
			}
		});

		view.getItem4().addListener(SWT.Selection, event -> {
			DialogWindowSearch dialog = new DialogWindowSearch(view.getShell());
			dialog.open(model.getFootballers());

		});

		view.getDeleteButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					model.delete(view.getShell());
				} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
						| InvocationTargetException | InstantiationException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				model.setCurrentPage(1);
				view.fillTheRows(
						model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
				disableButtons();
			}
		});

		view.getItem1().addListener(SWT.Selection, event -> {
			try {
				model.delete(view.getShell());
			} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
					| InstantiationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.setCurrentPage(1);
			view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
			disableButtons();

		});

		view.getOKButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				model.setNumberOfRow(view.getText());
				model.setCurrentPage(1);
				view.fillTheRows(
						model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
				disableButtons();
			}
		});

		view.getNextButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				model.incrementPage();
				view.fillTheRows(
						model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
				disableButtons();
			}
		});

		view.getFirstButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				model.toFirstPage();
				view.fillTheRows(
						model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
				disableButtons();
			}
		});

		view.getPreviousButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				model.decrementPage();
				view.fillTheRows(
						model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
				disableButtons();
			}
		});

		view.getLastButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				model.toLastPage();
				view.fillTheRows(
						model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
				disableButtons();
			}
		});

		view.display();
	}

}
