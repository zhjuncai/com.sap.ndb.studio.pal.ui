package com.sap.ndb.studio.pal.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
		}
		
		return null;
	}

}
