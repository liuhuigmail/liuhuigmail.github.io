package rename.marker;

import org.eclipse.core.resources.IFile;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.swt.widgets.Display;

import rename.model.ResultModel;

public class HandleMarker {
	
	private IFile file = null;
	private ResultModel result = null;
	
	public HandleMarker(ResultModel result){
		this.result = result;
		this.file = result.renameIFile;	
	}
	
	
	public void addMarker() {
		
		Display.getDefault().asyncExec(new Runnable() {
			
			public void run() {
				if(file == null) return;
				MarkerFactory.removeMarkers(file);				
				MarkerFactory.addMarker(file, result);
				
			}
		});
		
	}
	
	
	
}
