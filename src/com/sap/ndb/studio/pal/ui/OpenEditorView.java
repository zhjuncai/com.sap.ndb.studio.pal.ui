package com.sap.ndb.studio.pal.ui;

import java.util.ArrayList;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

import com.sap.ndb.studio.sql.editor.CatalogEditorInput;
import com.sap.ndb.studio.sql.editor.OpenEditorHelper;
import com.sap.ndb.studio.sql.editor.resulteditor.ResultEditor;

public class OpenEditorView extends OpenEditorHelper{
	public static ResultEditor openResultEditorInUI(final CatalogEditorInput editorInput, final String tmpQuery, final IWorkbenchPage page) {
		Assert.isNotNull(page);
		final ResultEditor[] editor = new ResultEditor[1];
		// run in UI thread
		Display.getDefault().syncExec(new Runnable() {
	
			@Override
			public void run() {
				try {
					editor[0] = (ResultEditor)page.openEditor(editorInput, ResultEditor.EDITOR_ID);
				} catch (PartInitException e1) {
					// do nothing
				}
					editor[0].selectAndShowResult(tmpQuery);
			}
		});
		return editor[0];
	}
}
