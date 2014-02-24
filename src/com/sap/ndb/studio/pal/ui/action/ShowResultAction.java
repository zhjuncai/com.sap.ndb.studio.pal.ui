package com.sap.ndb.studio.pal.ui.action;
 
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.EditorPart;
import com.sap.ndb.studio.catalog.IConnectionResource;
import com.sap.ndb.studio.images.Images;
import com.sap.ndb.studio.pal.ui.ResultStudioUIPlugin;
import com.sap.ndb.studio.sql.action.CatalogObjectAction;
import com.sap.ndb.studio.sql.editor.common.ResourceEditorInput;
import com.sap.ndb.studio.sql.SQLStudioUIPlugin;

public class ShowResultAction extends CatalogObjectAction{

	private static String ID = "com.sap.ndb.studio.pal.ui.action.ShowResultAction";
	private EditorPart     m_parent;
	public ShowResultAction() {
    	m_parent = null;
    }
    
    public ShowResultAction(String name) {
    	super(name);
    	m_parent = null;
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(m_parent == null){
    		super.run();
    	}else{
    		run(((ResourceEditorInput)m_parent.getEditorInput()).getResource());
    	}
	}
public void setActiveEditor(IEditorPart targetEditor) {
        
        if(targetEditor == null) return;
        
        m_parent = (EditorPart)targetEditor;
        
        setId(ShowResultAction.ID);
        setToolTipText("Show Result");
		setImageDescriptor(Images.IMAGES_OBJ16.RESULT.imageDescriptor());
    }
 
    public void run(IConnectionResource resource)  {
        
    	if(resource == null) return;    	
		ResultStudioUIPlugin.showResult(resource);
    }    

}
