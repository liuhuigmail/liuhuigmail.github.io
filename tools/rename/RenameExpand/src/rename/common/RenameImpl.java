package rename.common;

import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.RenameListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

import rename.handle.Handle;
import rename.marker.HandleMarker;
import rename.model.ResultModel;

public class RenameImpl implements RenameListener {

	public RenameImpl() {

	}

	
	@Override
	public void onRename(final RefactoringDescriptor descriptor) {
		if(descriptor != null && descriptor instanceof RenameJavaElementDescriptor){
		
			try {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {				
						RenameJavaElementDescriptor renameDescriptor = (RenameJavaElementDescriptor) descriptor;
						
						Handle handle = new Handle();
						ResultModel result = handle.handleRename(renameDescriptor);
						
						if(result != null && result.recommendOriginalName != ""){
							HandleMarker handleMarker = new HandleMarker(result);
							handleMarker.addMarker();
						}
					
					}
				});
			} catch (Exception e) {
				System.out.println("has error!!!!!");
				return;
			}
			
			
		}
		
		
				
	}
	
	
	public void run(RefactoringDescriptor descriptor) {
		
		MessageBox mb = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.OK); 
		mb.setMessage(descriptor.toString()); 
		mb.setText("Demo"); 
		mb.open(); 
		
	} 
	
	
	

}
