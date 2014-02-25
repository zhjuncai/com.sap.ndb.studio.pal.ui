package com.sap.ndb.studio.pal.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.sap.ndb.studio.catalog.IConnectionResource;
import com.sap.ndb.studio.pal.ui.action.ShowResultAction;

public class PalHandler extends AbstractHandler {
	
	public PalHandler(){
		
	}
	public Object execute(ExecutionEvent event) throws ExecutionException {

		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element instanceof IConnectionResource) {				
				ShowResultAction action = new ShowResultAction();
				action.run((IConnectionResource)element);
			}
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
		
		}
		return null;
	}

}