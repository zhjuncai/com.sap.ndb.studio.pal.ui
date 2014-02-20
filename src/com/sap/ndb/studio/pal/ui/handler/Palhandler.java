package com.sap.ndb.studio.pal.ui.handler;

import org.eclipse.core.commands.*;
import org.eclipse.ui.*;



public class PalHandler extends AbstractHandler {
	
	public PalHandler(){
		
	}
	public Object execute(ExecutionEvent event) throws ExecutionException {
/*		// get workbench window
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		// set selection service
		ISelectionService service = window.getSelectionService();
		// set structured selection
		IStructuredSelection structured = (IStructuredSelection) service.getSelection();*/
	    
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("com.sap.ndb.studio.pal.ui.view.PALView");
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
