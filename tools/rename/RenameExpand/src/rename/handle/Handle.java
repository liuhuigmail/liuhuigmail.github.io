package rename.handle;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import rename.model.DataModel;
import rename.model.ResultModel;
import rename.parser.Parser;
import rename.util.Util;

public class Handle {
	
	public ResultModel handleRename(RenameJavaElementDescriptor renameDescriptor){
		
		ResultModel result = new ResultModel();
		
		DataModel renameData = getRenameData(renameDescriptor);		
		if(renameData == null) return null;
		
		IJavaElement iJavaElement = renameDescriptor.getJavaElement();
		IResource iResource = iJavaElement.getResource();
		IFile file = (IFile) iResource;
		
		
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		IEditorPart editorPart = page.getActiveEditor();
		ITextEditor editor = (ITextEditor) editorPart;
		ISelection selection = editor.getSelectionProvider().getSelection();
		int line = 0;
		if(selection instanceof ITextSelection){
			line = ((ITextSelection)selection).getStartLine();
		}
		
				
		Parser parser = new Parser();				
		parser.parse(renameData);		
		result = parser.result;		
		if(result == null) return null;
		result.renameIFile = file;
		result.renameLine = line;
		
		if(renameData.refactorType.equals("org.eclipse.jdt.ui.rename.type")){
			ICompilationUnit icu = Util.getCompilationUnit1(renameData);
			result.renameIFile = (IFile)(icu.getResource());
		}
		
		
		return result;

	}
	
	
	
	private DataModel getRenameData(RenameJavaElementDescriptor renameDescriptor){
		DataModel renameData = new DataModel();		
		
		if(renameDescriptor.getID().equals("org.eclipse.jdt.ui.rename.type")){			
			renameData = handleTypeRename(renameDescriptor);
			
		} else if(renameDescriptor.getID().equals("org.eclipse.jdt.ui.rename.method")){
			renameData = handleMethodRename(renameDescriptor);
			
		} else if(renameDescriptor.getID().equals("org.eclipse.jdt.ui.rename.field")){
			renameData = handleFieldRename(renameDescriptor);
			
		} else if(renameDescriptor.getID().equals("org.eclipse.jdt.ui.rename.local.variable")){
			renameData = handleLocalVariableRename(renameDescriptor);
		} else {
			return null;
		}
		
		return renameData;
	}	
	
 	private DataModel handleTypeRename(RenameJavaElementDescriptor renameDescriptor){
		DataModel renameData = new DataModel();
		
		String projectName = renameDescriptor.getProject();
		renameData.projectName = projectName;
		
		renameData.refactorType = renameDescriptor.getID();
		
		String[] refactoringInformention = renameDescriptor.toString().split(System.lineSeparator());		
		String originalElement = refactoringInformention[2];
		originalElement = originalElement.substring(originalElement.indexOf("'")+1, originalElement.length()-1);
		renameData.originalName = originalElement.substring(originalElement.lastIndexOf(".")+1);		
		renameData.typeName = originalElement.substring(originalElement.lastIndexOf(".")+1);
		originalElement = originalElement.substring(0, originalElement.lastIndexOf("."));
		renameData.packageName = originalElement;
		
		String renameElement = refactoringInformention[3];
		renameElement = renameElement.substring(renameElement.lastIndexOf(".")+1, renameElement.length()-1);
		renameData.subsequentName = renameElement;
		
		return renameData;
	}
	
	private DataModel handleMethodRename(RenameJavaElementDescriptor renameDescriptor){
		DataModel renameData = new DataModel();
		
		String projectName = renameDescriptor.getProject();
		renameData.projectName = projectName;
		
		renameData.refactorType = renameDescriptor.getID();
		
		String[] refactoringInformention = renameDescriptor.toString().split(System.lineSeparator());		
		String originalElement = refactoringInformention[2];
		originalElement = originalElement.substring(originalElement.indexOf("'")+1);
		originalElement = originalElement.substring(0, originalElement.indexOf("("));
		renameData.originalName = originalElement.substring(originalElement.lastIndexOf(".")+1);
		originalElement = originalElement.substring(0, originalElement.lastIndexOf("."));		
		renameData.typeName = originalElement.substring(originalElement.lastIndexOf(".")+1);
		originalElement = originalElement.substring(0, originalElement.lastIndexOf("."));
		renameData.packageName = originalElement;
		
		String renameElement = refactoringInformention[3];
		renameElement = renameElement.substring(0, renameElement.indexOf("("));
		renameElement = renameElement.substring(renameElement.lastIndexOf(".")+1);
		renameData.subsequentName = renameElement;					

		IJavaElement iJavaElement = renameDescriptor.getJavaElement();
		String methodName = iJavaElement.toString().substring(0, iJavaElement.toString().indexOf(")") + 1);
		renameData.methodName = methodName;
		
		return renameData;
	}
	
	private DataModel handleFieldRename(RenameJavaElementDescriptor renameDescriptor){
		DataModel renameData = new DataModel();
		
		String projectName = renameDescriptor.getProject();
		renameData.projectName = projectName;
		
		renameData.refactorType = renameDescriptor.getID();
		
		String[] refactoringInformention = renameDescriptor.toString().split(System.lineSeparator());		
		String originalElement = refactoringInformention[2];
		originalElement = originalElement.substring(originalElement.indexOf("'")+1, originalElement.length()-1);
		renameData.originalName = originalElement.substring(originalElement.lastIndexOf(".")+1);
		originalElement = originalElement.substring(0, originalElement.lastIndexOf("."));
		renameData.typeName = originalElement.substring(originalElement.lastIndexOf(".")+1);
		originalElement = originalElement.substring(0, originalElement.lastIndexOf("."));
		renameData.packageName = originalElement;
		
		String renameElement = refactoringInformention[3];
		renameElement = renameElement.substring(renameElement.lastIndexOf(".")+1, renameElement.length()-1);
		renameData.subsequentName = renameElement;
		
		return renameData;
	}
	
	private DataModel handleLocalVariableRename(RenameJavaElementDescriptor renameDescriptor){
		DataModel renameData = new DataModel();
		
		String projectName = renameDescriptor.getProject();
		renameData.projectName = projectName;
		
		renameData.refactorType = renameDescriptor.getID();
		
		String[] refactoringInformention = renameDescriptor.toString().split(System.lineSeparator());		
		String originalElement = refactoringInformention[2];
		originalElement = originalElement.substring(originalElement.indexOf("'")+1, originalElement.length()-1);
		renameData.originalName = originalElement.substring(originalElement.lastIndexOf(".")+1);
		originalElement = originalElement.substring(0, originalElement.lastIndexOf("."));
		renameData.methodName = originalElement.substring(originalElement.lastIndexOf(".")+1);
		originalElement = originalElement.substring(0, originalElement.lastIndexOf("."));
		
		renameData.typeName = originalElement.substring(originalElement.lastIndexOf(".")+1);
		originalElement = originalElement.substring(0, originalElement.lastIndexOf("."));
		renameData.packageName = originalElement;
		
		String renameElement = refactoringInformention[3];
		renameElement = renameElement.substring(renameElement.indexOf("'")+1, renameElement.length()-1);
		renameData.subsequentName = renameElement;
		
		return renameData;
	}
	
	
			
}
