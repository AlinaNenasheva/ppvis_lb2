package ppvis_lb2;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.xml.sax.SAXException;

public class Controller {
	
	Model model;
	View view;
	
	Controller(){
		model = new Model();
		view = new View();
		execute();
	}
	
	void disableButtons() {
		if(model.currentPage == 1) {
			view.getFirstButton().setEnabled(false);
			view.getPreviousButton().setEnabled(false);
		}else {
			view.getFirstButton().setEnabled(true);
			view.getPreviousButton().setEnabled(true);
		}
		if(model.currentPage == model.pagesOverall) {
			view.getLastButton().setEnabled(false);
			view.getNextButton().setEnabled(false);
		}else {
			view.getLastButton().setEnabled(true);
			view.getNextButton().setEnabled(true);
		}
		
	}
	
	void execute(){
		disableButtons();
		view.getLoadButton().addSelectionListener(new SelectionAdapter() {
	     	   @Override
	     	   public void widgetSelected(SelectionEvent arg0) {
					try {
						DialogWindowLoad dialog = new DialogWindowLoad(view.getShell());
						String path = new String();
	  					path = dialog.open(view.getShell());
	  					if(path!=null) model.getInfo(path);
					} catch (ParserConfigurationException | SAXException | IOException e) {
						e.printStackTrace();
					}
					view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));	
					disableButtons();
	     	   }
	     	 
	     	});
		
         view.item2.addListener(SWT.Selection, event-> {
				try {
					DialogWindowLoad dialog = new DialogWindowLoad(view.getShell());
					String path = new String();
  					path = dialog.open(view.getShell());
  					if(path!=null) model.getInfo(path);
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
  					DialogWindowSave dialog = new DialogWindowSave(view.getShell());
  					String path = new String();
  					path = dialog.open(view.getShell());
  					if(path!=null) {
  	 					DOMxml.downloadedData(model.getFootballers(), path);
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
       	   }});
        
        view.item3.addListener(SWT.Selection, event-> {
        	try {
					DialogWindowSave dialog = new DialogWindowSave(view.getShell());
					String path = new String();
					path = dialog.open(view.getShell());
					if(path!=null) {
	 					DOMxml.downloadedData(model.getFootballers(), path);
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
		  		   	if(newFootballer != null) {
		  		   		model.addFootballer(newFootballer);
		  		    	view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
		  		    	disableButtons();
		  		   	}
		  	   } 
		  	});
        
        view.item5.addListener(SWT.Selection, event-> {
  		   	DialogWindowAdd dialog = new DialogWindowAdd(view.getShell());
  		   	Footballer newFootballer = dialog.open();
  		   	if(newFootballer != null) {
  		   		model.addFootballer(newFootballer);
  		    	view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
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
        
        view.item4.addListener(SWT.Selection, event-> {
  		   	DialogWindowSearch dialog = new DialogWindowSearch(view.getShell());
  		   	dialog.open(model.getFootballers());
    	   
       });
        
        view.getDeleteButton().addSelectionListener(new SelectionAdapter() {
		  	   @Override
		  	   public void widgetSelected(SelectionEvent arg0) {
		  		    model.delete(view.getShell());
		  		   	model.currentPage = 1;
	  		    	view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
	  		    	disableButtons();
		  	   } 
		  	});
        
        view.item1.addListener(SWT.Selection, event-> {
  		    model.delete(view.getShell());
  		   	model.currentPage = 1;
		    	view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
		    	disableButtons();
    	   
       });
        
        view.getOKButton().addSelectionListener(new SelectionAdapter() {
		  	   @Override
		  	   public void widgetSelected(SelectionEvent arg0) {
		  		   	model.numberOfRows = view.getText();
		  		   	model.currentPage = 1;
		  		    view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
		  		    disableButtons();
		  	   } 
		  	});
        
        view.getNextButton().addSelectionListener(new SelectionAdapter() {
		  	   @Override
		  	   public void widgetSelected(SelectionEvent arg0) {
		  		  model.incrementPage();
		  		  view.fillTheRows(model.extractBlock(view.getPageLabel(),view.getCurElemsLabel(), view.getAllElemsLabel()));
		  		  disableButtons();
		  	   }
		  	});
        
        view.getFirstButton().addSelectionListener(new SelectionAdapter() {
		  	   @Override
		  	   public void widgetSelected(SelectionEvent arg0) {
		  		  model.toFirstPage();
		  		  view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
		  		  disableButtons();
		  	   }  	 
		  	});
        
        view.getPreviousButton().addSelectionListener(new SelectionAdapter() {
		  	   @Override
		  	   public void widgetSelected(SelectionEvent arg0) {
		  		  model.decrementPage();
		  		  view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
		  		  disableButtons();
		  	   }  	 
		  	});
        
        view.getLastButton().addSelectionListener(new SelectionAdapter() {
		  	   @Override
		  	   public void widgetSelected(SelectionEvent arg0) {
		  		  model.toLastPage();
		  		  view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
		  		  disableButtons();
		  	   }  	 
		  	});
        
        view.display();
	}
	

}
