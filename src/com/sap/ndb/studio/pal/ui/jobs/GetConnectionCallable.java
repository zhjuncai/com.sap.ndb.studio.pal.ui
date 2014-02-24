package com.sap.ndb.studio.pal.ui.jobs;

import java.sql.Connection;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.sap.ndb.studio.catalog.IConnectionResource;

class GetConnectionCallable implements Callable<IStatus> {
	
	private IConnectionResource resource;
	private Connection connection = null;
	private boolean autoCommit;
	private boolean isAutoCommitParameterSet = false;

	public GetConnectionCallable(IConnectionResource resource) {
		this.resource = resource;		
	}
	
	public GetConnectionCallable(IConnectionResource resource, boolean autoCommit) {
		this.resource = resource;		
		this.autoCommit = autoCommit;
		this.isAutoCommitParameterSet = true;
	}
	
	public Connection getConnection() {
		return connection;
	}

	@Override
	public IStatus call() throws Exception {
		if (resource != null) {
			if (isAutoCommitParameterSet) {
				this.connection= resource.getConnection();
			} else {
				this.connection= resource.getConnection(autoCommit);
			}			
		}
		return Status.OK_STATUS;
	}
	
}