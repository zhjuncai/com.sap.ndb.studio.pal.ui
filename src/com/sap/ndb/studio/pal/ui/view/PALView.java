package com.sap.ndb.studio.pal.ui.view;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.part.*;

public class PALView extends ViewPart  {

	private TableViewer viewer;
	
	
	public PALView(){
		
	}
	
	public void createPartControl(final Composite parent) {
		
	}
	
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
