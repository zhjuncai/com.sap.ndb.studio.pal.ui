package com.sap.ndb.studio.pal.ui;
import org.eclipse.ui.PlatformUI;

import com.sap.ndb.studio.catalog.IConnectionResource;
import com.sap.ndb.studio.sql.SQLStudioUIPlugin;
import com.sap.ndb.studio.pal.ui.jobs.ShowResultJob;

public class ResultStudioUIPlugin extends SQLStudioUIPlugin{
	public static void showResult(IConnectionResource resource) {

		ShowResultJob openJob = new ShowResultJob("Show PAL Result", resource, getPreferences(), PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()); //$NON-NLS-1$
		
		openJob.setUser(true);
		openJob.schedule();
	}
}
