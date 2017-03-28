import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IElement;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;

public class CountActors implements IPluginActionDelegate {
	
	/*run = "main" do plugin*/
	public Object run(IWindow window) throws UnExpectedException
	{
		try
		{
			/* cria um acessor de projeto pela API (que é só um objeto usado para manipular projetos)
            e pega o projeto aberto no momento, que se apresenta como uma árvore de elementos (classes
            e casos de uso). a ideia aqui é adicionar todos os atores (método getActors) a uma lista de
            atores e imprimir a quantidade que houver`
            */
			ProjectAccessor pjAcs = AstahAPI.getAstahAPI().getProjectAccessor();
			IModel iProject = pjAcs.getProject();
			List <IClass> actorList = new ArrayList<>();
			getActors(iProject,actorList);
			 JOptionPane.showMessageDialog(window.getParent(), 
	            		"O modelo tem " + actorList.size() + " classes.");
		} catch (ClassNotFoundException | ProjectNotFoundException e)
		{
			e.printStackTrace();
		}
	    return null;
	}
	
	public void getActors(IElement element,List<IClass> classes )
	{
		/*
        Tudo de relevante que estiver no modelo ou é um package (que pode ter mais coisas dentro)
        ou é uma classe, que pode ter outras classes aninhadas e/ou ser um ator.
        Se for um package, apenas procura os atores dentro (chamada recursiva).
        Se for uma classe, entra algo aqui que eu custei a descobrir/lembrar:
        no Astah, um ator é um **objeto** que só é marcado como ator com um stereotype.
        Assim, ele procura outros atores dentro da classe (chamada recursiva) e, se a classe
        for um ator, adiciona à lista.
        */
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


}
