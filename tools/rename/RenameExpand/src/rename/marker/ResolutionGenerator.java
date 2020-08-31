package rename.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

public class ResolutionGenerator implements IMarkerResolutionGenerator {

	public ResolutionGenerator() {
	}

	public IMarkerResolution[] getResolutions(IMarker marker) {
		if (!marker.exists()) {
			return new IMarkerResolution[0];
		}
		String message = marker.getAttribute(IMarker.MESSAGE, "");
		message = "Would you want to rename " + message;
		QuickFixResolution resolution = new QuickFixResolution(message);
			
		return new IMarkerResolution[] { resolution };
	}

}
