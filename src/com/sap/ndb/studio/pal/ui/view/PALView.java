package com.sap.ndb.studio.pal.ui.view;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.part.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.sap.ndb.studio.pal.ui.model.PALModel;;

public class PALView extends ViewPart {

	private TableViewer viewer;

	public PALView() {

	}

	public void createPartControl(final Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
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
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		parent.setLayoutData(gridData);
		parent.setLayout(new GridLayout(1, false));

		Text txtTest = new Text(parent, SWT.NONE);
		txtTest.setText("Testing");

		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		txtTest.setLayoutData(gridData);

		Text txtMoreTests = new Text(parent, SWT.NONE);
		txtMoreTests.setText("Another test");
		parent.layout();
	}

	public void setFocus() {
		
		viewer.getControl().setFocus();
	}
}
