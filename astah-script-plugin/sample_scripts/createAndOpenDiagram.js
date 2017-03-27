//Only for Astah UML and Professional.
//This script creates and opens new class diagram.
var newDiagramName = 'New Class Diagram';

run();

function run() {
    if (!isSupportedAstah()) {
        print('This edition is not supported');
    }

    with(new JavaImporter(
            com.change_vision.jude.api.inf.editor)) {
        //Edit the astah model
        TransactionManager.beginTransaction();
        var editor = astah.getDiagramEditorFactory().getClassDiagramEditor();
        var newDgm = editor.createClassDiagram(astah.getProject(), newDiagramName);
        TransactionManager.endTransaction();
        print('New Class Diagram was created!');
    }
    
    //Open the diagram
    var dgmViewManager = astah.getViewManager().getDiagramViewManager();
    dgmViewManager.open(newDgm);
}

function isSupportedAstah() {
    var edition = astah.getAstahEdition();
    print('Your edition is ' + edition);
    if (edition == 'professional' || edition == 'UML') {
        return true;
    } else {
        return false;
    }
}
