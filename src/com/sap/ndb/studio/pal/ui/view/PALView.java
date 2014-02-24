package com.sap.ndb.studio.pal.ui.view;

import java.util.ArrayList;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.part.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.sap.ndb.studio.pal.ui.model.*;
import com.sap.ndb.studio.pal.ui.sql.ClusteringAlg;

public class PALView extends ViewPart {

	private TableViewer viewer;
	private String schema;
	private String tableName;
	private String[] parameter;
	private String[] columnName;
	private String[] columnType;
	private ArrayList<String> sqlString;
	
	
	 
	public PALView() {

	}
    public PALView(ExecutionEvent event){
    	
    }
	public void createPartControl(final Composite parent) {
		
/*		RowLayout rowlayout = new RowLayout(SWT.VERTICAL);
		rowlayout.marginWidth = 10;
		parent.setLayout(rowlayout);*/
		
		
		
		
		
		
	    GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		
	    // Column of Table	
		GridData gridData= new GridData(GridData.HORIZONTAL_ALIGN_FILL); 
		gridData.horizontalSpan = 2;

		Group group = new Group(parent,SWT.SHADOW_ETCHED_OUT);
		//group.setLayout(new FillLayout(SWT.VERTICAL));
		group.setLayoutData(gridData);
		group.setText("Select Columns");
		
		group.setLayout(new GridLayout(1,false));
			
		Button button1 = new Button(group,SWT.CHECK);
		button1.setText("column1");
		
		
		Button button2 = new Button(group,SWT.CHECK);
		button2.setText("column2");
		Button button3 = new Button(group,SWT.CHECK);
		button3.setText("column3");
		
		
		// Dropdownlist for PAL function category
		final Combo comboBox = new Combo(parent, SWT.NULL);
		comboBox.add("Please Select PAL Category", 0);
		comboBox.add(PALModel.Clustering, 1);
		comboBox.add(PALModel.Classification, 2);
		comboBox.add(PALModel.Association, 3);
		comboBox.add(PALModel.TimeSeries, 4);
		comboBox.add(PALModel.Preprocessing, 5);
		comboBox.add(PALModel.Statistics, 6);
		comboBox.add(PALModel.SocialNetWorkAnalysis, 7);
		comboBox.add(PALModel.Miscellaneous, 8);
		comboBox.select(0);

		// Dropdownlist for detail algorithms based on the category
		final Combo comboBox2 = new Combo(parent, SWT.NONE);
		comboBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				createDynCombo(parent, comboBox2, comboBox.getSelectionIndex());
			}
		});
	}

	//Create dropdownlist options
	private void createDynCombo(final Composite parent, final Combo comboBox, int index) {
/*		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		parent.setLayoutData(gridData);
		parent.setLayout(new GridLayout(2, false));*/
        
		comboBox.removeAll();
		comboBox.add("Please Select An Algorithms", 0);
		comboBox.select(0);
		switch (index) {
		case 1: {
			comboBox.add(PALModel.AffinityPropagation, 1);
			comboBox.add(PALModel.AnomalyDetection, 2);
			comboBox.add(PALModel.DBSCAN, 3);
			comboBox.add(PALModel.AgglomerateHierarchicalClustering, 4);
			comboBox.add(PALModel.KMeans, 5);
			comboBox.add(PALModel.SelfOrganizingMaps, 6);
			comboBox.add(PALModel.SlightSilhouette, 7);
			comboBox.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					showAlgorithmsProperty(parent, 1,
							comboBox.getSelectionIndex());

				}
			});
			break;
		}
		case 2: {
			comboBox.add("Bi-Variate", 1);
			comboBox.add("C4.5 Decision Tree", 2);
			comboBox.add("KNN", 3);
			comboBox.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {

					showAlgorithmsProperty(parent, 2,
							comboBox.getSelectionIndex());
				}
			});
			break;
		}

		}
	}

	private void showAlgorithmsProperty(Composite parent, int cat, int index) {
		final String[] param = new String[10];
		
		Label lblThreadNum = new Label(parent, SWT.NONE);
		lblThreadNum.setText("Thread Number: ");
	    final Text txtThreadNum = new Text(parent, SWT.LEFT|SWT.SINGLE|SWT.BORDER);
		txtThreadNum.setToolTipText(AffinityModel.ThreadNumber);

		Label lblMaxIte = new Label(parent, SWT.NONE);
		lblMaxIte.setText("Max Iteration: ");	
		final Text txtMaxIte = new Text(parent, SWT.LEFT|SWT.SINGLE|SWT.BORDER);
		txtMaxIte.setText("500");
		txtMaxIte.setToolTipText(AffinityModel.MaxIte);
		
		Label lblConIteration = new Label(parent, SWT.NONE);
		lblConIteration.setText("Con Iteration: ");	
		final Text txtConIteration = new Text(parent, SWT.LEFT|SWT.SINGLE|SWT.BORDER);
		txtConIteration.setText("100");
		
		Label lblDAMP = new Label(parent, SWT.NONE);
		lblDAMP.setText("DAMP: ");	
		final Text txtDAMP = new Text(parent, SWT.LEFT|SWT.SINGLE|SWT.BORDER);
		txtDAMP.setText("0.9");
		
		Label lblPreference = new Label(parent, SWT.NONE);
		lblPreference.setText("Preference: ");	
		final Text txtPreference = new Text(parent, SWT.LEFT|SWT.SINGLE|SWT.BORDER);
		txtPreference.setText("0.5");
		
		Label lblSeedRatio = new Label(parent, SWT.NONE);
		lblSeedRatio.setText("Seed Ratio: ");	
		final Text txtSeedRatio = new Text(parent, SWT.LEFT|SWT.SINGLE|SWT.BORDER);
		txtSeedRatio.setText("1");
		
		Label lblTimes = new Label(parent, SWT.NONE);
		lblTimes.setText("Times: ");	
		final Text txtTimes = new Text(parent, SWT.LEFT|SWT.SINGLE|SWT.BORDER);
		txtTimes.setText("1");
		
		Label lblDistanceMethod = new Label(parent, SWT.NONE);
		lblDistanceMethod.setText("Distance Method: ");	
		final Combo cbDistanceMethod = new Combo(parent, SWT.LEFT);
		cbDistanceMethod.add("Select Distance Method",0);
		cbDistanceMethod.add("Manhattan",1);
		cbDistanceMethod.add("Euclidean",2);
		cbDistanceMethod.add("Minkowski",3);
		cbDistanceMethod.add("Chebyshev",4);
		cbDistanceMethod.add("Standardised Euclidean",5);
		cbDistanceMethod.add("Cosine",6);
		cbDistanceMethod.select(0);
		
		Label lblMinkowP = new Label(parent, SWT.NONE);
		lblMinkowP.setText("Minkow P: ");	
		final Text txtMinkowP = new Text(parent, SWT.LEFT|SWT.SINGLE|SWT.BORDER);
		txtMinkowP.setText("3");
		
		Label lblClusterNumber = new Label(parent, SWT.NONE);
		lblClusterNumber.setText("Cluster Number: ");	
		final Text txtClusterNumber = new Text(parent, SWT.LEFT|SWT.SINGLE|SWT.BORDER);
		
		Button btnOk = new Button(parent, SWT.PUSH);
		btnOk.setText("Excute Algorithms");
		btnOk.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				parameter[0] = txtThreadNum.getText();
				parameter[1] = txtMaxIte.getText();
				parameter[2] = txtConIteration.getText();
				parameter[3] = txtDAMP.getText();
				parameter[4] = txtPreference.getText();
				parameter[5] = txtSeedRatio.getText();
				parameter[6] = txtTimes.getText();
				parameter[7] = cbDistanceMethod.getText();
				parameter[8] = txtMinkowP.getText();
				parameter[9] = txtClusterNumber.getText();
				
				ClusteringAlg.AffinityPropagation(sqlString, schema, tableName, parameter, columnName, columnType);

                
			}
		});
		
		parent.layout();
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
