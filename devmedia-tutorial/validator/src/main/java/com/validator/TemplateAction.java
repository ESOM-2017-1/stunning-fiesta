package com.validator;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;

public class TemplateAction implements IPluginActionDelegate {
	public Object run(IWindow window) throws UnExpectedException {
		try {
			AstahAPI api = AstahAPI.getAstahAPI();
			
			ProjectAccessor projectAccessor = api.getProjectAccessor();			
			IModel iCurrentProject = projectAccessor.getProject();
	        String error = validate(iCurrentProject);
	        if (error != null)
	          JOptionPane.showMessageDialog(window.getParent(), error,
	            "Error", JOptionPane.ERROR_MESSAGE);
	        else
	          JOptionPane.showMessageDialog(window.getParent(), "There are not errors!", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (ProjectNotFoundException e) {
			String message = "Project is not opened.Please open the project or create new project.";
			JOptionPane.showMessageDialog(window.getParent(), message, "Warning", JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(window.getParent(), "Unexpected error has occurred.", "Alert",
					JOptionPane.ERROR_MESSAGE);
			throw new UnExpectedException();
		}
		return null;
	}
	
	private String validate(IModel iCurrentProject) {
		List <IClass> actorList = new ArrayList<>();
		getActors(iProject,actorList);
         	 JOptionPane.showMessageDialog(window.getParent(), 
	            		"Model contains " + actorList.size() + " actors.");
		StringBuffer error = new StringBuffer();
		boolean hasError = false;
		for (IClass cl : classList) {
			String name = cl.getName();
			String upperCase = name.toUpperCase();
			if (name.charAt(0) != upperCase.charAt(0)) {
				error.append("[ERROR] Class: " + name + "\n");
				hasError = true;
			}
		}
		if (hasError)
			return error.toString();
		else
			return null;
	}
	
	public void getActors(IElement element,List<IClass> classes )
	{
		if (element instanceof IPackage)
			for(IElement inPkg : ((IPackage) element).getOwnedElements())
				getActors(inPkg,classes);
		else if(element instanceof IClass && element.hasStereotype("actor"))
		{
			classes.add((IClass)element);
			for(IClass nested : ((IClass) element).getNestedClasses())
				getActors(nested,classes);
		}
	}

	private void getAllClasses(INamedElement element, List<IClass> classList) {
		if (element instanceof IPackage) {
			for (INamedElement ownedNamedElement : ((IPackage) element).getOwnedElements()) {
				getAllClasses(ownedNamedElement, classList);
			}
		} else if (element instanceof IClass) {
			classList.add((IClass) element);
		}
	}
}
