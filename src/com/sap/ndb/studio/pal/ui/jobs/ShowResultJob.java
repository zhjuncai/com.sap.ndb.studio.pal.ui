package com.sap.ndb.studio.pal.ui.jobs;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

import com.sap.ndb.studio.catalog.ICatalogObject;
import com.sap.ndb.studio.catalog.IConnectionResource;
import com.sap.ndb.studio.catalog.ITableNDB;
import com.sap.ndb.studio.catalog.ITableSDB;
import com.sap.ndb.studio.common.StatusCallableJob;
import com.sap.ndb.studio.pal.ui.ResultStudioUIPlugin;
import com.sap.ndb.studio.pal.ui.sql.ClusteringAlg;
import com.sap.ndb.studio.sql.SQLStudioUIPlugin;
import com.sap.ndb.studio.sql.editor.CatalogEditorInput;
import com.sap.ndb.studio.sql.editor.OpenEditorHelper;
import com.sap.ndb.studio.sql.i18n.Resources;
import com.sap.ndb.studio.sql.preference.SQLStudioPreferences;
import com.sap.ndb.studio.ui.editors.MessageHeaderFormEditor;

public class ShowResultJob extends StatusCallableJob{
	private IConnectionResource resource;
	private SQLStudioPreferences sqlStudioPreferences;
	private IWorkbenchPage page;
	
	
	public ShowResultJob(String name, IConnectionResource resource, SQLStudioPreferences sqlStudioPreferences, IWorkbenchPage page) {
		super(name);
		this.resource = resource;
		this.sqlStudioPreferences = sqlStudioPreferences;
		Assert.isNotNull(resource);
		Assert.isNotNull(sqlStudioPreferences);

		this.page = page;
	}
	
	protected IStatus run(IProgressMonitor  monitor) {
		// TODO Auto-generated method stub
		Connection connection = null;
		MessageHeaderFormEditor editor = null;

		if (page == null)
			return Status.OK_STATUS;
		
		monitor.beginTask("Preparation", 2);		

		try {
			if (resource instanceof ICatalogObject) {
				
				GetConnectionCallable callable = new GetConnectionCallable((ICatalogObject) resource);
				ExecutorService executor = Executors.newCachedThreadPool();
				
				monitor.subTask("Preparation: Opening Connection");
				IStatus executeStatus = executeCallable(callable, executor, monitor);
				monitor.worked(1);
				
				if (executeStatus != null && executeStatus.isOK()) {
					connection = callable.getConnection();
				}
				
				
				if (connection != null) {
					String query = null; //$NON-NLS-1$

					if (resource instanceof ITableNDB) {
						subPalSQL((IConnectionResource)resource,connection);
						ITableSDB table = (ITableSDB) resource;
						query = "SELECT TOP "+sqlStudioPreferences.getForwardOnlyMaxRow()  + " * FROM \"" + table.getSchema() + "\"."+"\""+"PAL_AP_RESULTS_TBL"+"\"";
					}
					
					final CatalogEditorInput editorInput = new CatalogEditorInput(resource);
					final String tmpQuery = query;

					monitor.subTask("Opening Editor");
					editor = OpenEditorHelper.openResultEditorInUI(editorInput, tmpQuery, page);
					monitor.worked(1);
				}
			}

		} catch (Exception ex) {
			SQLStudioUIPlugin.displayEditorErrorMessage(connection, ex);
			System.out.println(ex.getStackTrace());
		} finally {
			SQLStudioUIPlugin.putConnection(connection);
			monitor.done();
		}

		if (editor == null) {
			SQLStudioUIPlugin.displayErrorMessage(Resources.ErrorText_Open_Editor_YMSG,
					NLS.bind(Resources.No_Content_Found_YMSG, resource.toString()));
		}

		return null;
	}

	private void subPalSQL(IConnectionResource resource,Connection connection) throws SQLException{
		ArrayList<String> alist = new ArrayList<String>();
		ITableSDB table = (ITableSDB) resource;
		String[] parameter={"2","500","100","0.9","0.5","1","1","2","3","0"};
		String[] columnName={"ATTRIB1","ATTRIB2"};
		String[] columnType={"DOUBLE","DOUBLE"};
		ClusteringAlg.AffinityPropagation(alist,table.getSchema(),table.getName(),parameter,columnName,columnType);
		Iterator<String> it = alist.iterator();
		Statement truncTableStmt = connection.createStatement();
		while(it.hasNext()){
			try {
			truncTableStmt.execute(it.next());	
		} catch (Exception e1) {
	   	    
		}
		}
	}
}
