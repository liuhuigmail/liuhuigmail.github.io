package rename.marker;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import rename.util.Util;


public class QuickFixResolution implements IMarkerResolution {
	private String message = null;

	public QuickFixResolution(String message) {
		this.message = message;
	}

	public String getLabel() {
		return message;
	}

	public void run(IMarker marker) {
		
		String projectName = marker.getAttribute("recommendProjectName", "");
		String packageName = marker.getAttribute("recommendPackageName", "");
		String typeName = marker.getAttribute("recommendTypeName", "");
		int startPosition = marker.getAttribute("recommendStartPosition", 0);
	
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();	
		IWorkbenchPage page = window.getActivePage();
		ICompilationUnit icu = Util.getCompilationUnit(projectName, packageName, typeName);
		icu.getResource().getFullPath();				
		IPath path = icu.getResource().getFullPath().makeRelativeTo(Platform.getLocation());
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		
		CompilationUnit unit = Util.createCompilationUnit(icu);
		int line = unit.getLineNumber(startPosition);
		
		IMarker iMarker = null;
		try {
			iMarker = file.createMarker(IMarker.TEXT);
			iMarker.setAttribute(IMarker.LINE_NUMBER, line);
		} catch (CoreException e1) {
			return;
		}
		
		try {
			IDE.openEditor(page, iMarker);
		} catch (PartInitException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			marker.delete();
		} catch (CoreException e) {
			
		}
	}
}
