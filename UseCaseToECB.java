import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.model.IModel;


public class CountActors implements IPluginActionDelegate {
	
	/*run = "main" do plugin*/
	public Object run(IWindow window) throws UnExpectedException {
        try {
            ProjectAccessor pjAcs = AstahAPI.getAstahAPI().getProjectAccessor();
            IModel iProject = pjAcs.getProject();
            getUseCases(iProject);
            
        } catch (ClassNotFoundException | ProjectNotFoundException e) {
			e.printStackTrace();
        }
    }

    

}


public List<IClass> getUseCases(IElement element) {
    List<IClass> useCases = new LinkedList<>();
    
    if (element instanceof IPackage)
		for(IElement inPkg : ((IPackage) element).getOwnedElements())
			getUseCases(inPkg);
    else if(element instanceof IClass && element.hasStereotype("usecase")) {    // TODO: find out the correct name
	    useCases.add((IClass)element);
	    for(IClass nested : ((IClass) element).getNestedClasses())
		    getUseCases(nested);
    }
    
    return useCases;
}

