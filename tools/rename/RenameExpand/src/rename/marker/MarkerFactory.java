package rename.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import rename.model.ResultModel;


public class MarkerFactory {

	public static final String MARKER_ID = "rename.marker.result";
	public static final String ANNOTATION_ID = "rename.annotation.result";

	private static IMarker createMarker(IResource res, int line) {
		IMarker marker = null;
		try {
			marker = res.createMarker(MARKER_ID);
			marker.setAttribute(IMarker.LINE_NUMBER, line + 1);
			marker.setAttribute("markerFlag", "result");
		} catch (CoreException e) {
			marker = null;
		}

		return marker;
	}

	public static void addMarker(IResource resource, ResultModel result) {
		IMarker marker = null;
		try {
			marker = createMarker(resource, result.renameLine);
			marker.setAttribute(IMarker.MESSAGE, result.recommendRefactorType + " " + result.recommendOriginalName + " to " + result.recommendSubsequentName);
			marker.setAttribute("recommendRefactorType", result.recommendRefactorType);
			marker.setAttribute("recommendProjectName", result.recommendProjectName);
			marker.setAttribute("recommendPackageName", result.recommendPackageName);
			marker.setAttribute("recommendTypeName", result.recommendTypeName);
			marker.setAttribute("recommendStartPosition", result.recommendStartPosition);
			marker.setAttribute("recommendSubsequentName", result.recommendSubsequentName);
		} catch (CoreException e) {
			
		}
		
	}

	public static void removeMarkers(IResource resource) {
		try {
			resource.deleteMarkers(MARKER_ID, false, 0);
		} catch (CoreException e) {
		}
	}
	
	
}
