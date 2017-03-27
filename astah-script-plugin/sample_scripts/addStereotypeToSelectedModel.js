//Only for Astah UML and Professional.
//This script adds a new stereotype to the selected classes
//in the Diagram Editor.
var newStereotype = "New Stereotype";

run();

function run() {
    var targets = getSelectedClassesInDiagramEditor();

    if (targets.length === 0) {
        print('Please select classes you want to add the stereotype to.');
        return;
    }

    with(new JavaImporter(
            com.change_vision.jude.api.inf.editor)) {
        TransactionManager.beginTransaction();
        for (var i in targets) {
            addStereotype(targets[i]);
        }
        TransactionManager.endTransaction();
    }
}

function getSelectedClassesInDiagramEditor() {
    var targets = [];
    var diagramViewManager = astah.getViewManager().getDiagramViewManager();
    var selectedPss = diagramViewManager.getSelectedPresentations();
    for (var i in selectedPss) {
        var ps = selectedPss[i];
        //println(ps.getType());
        if (ps.getType() == 'Class' && !(ps.getModel() in targets)) {
            targets.push(ps.getModel());
            print('HIT: ' + ps.getModel().getName());
        }
    }

    return targets;
}

function addStereotype(target) {
    target.addStereotype(newStereotype);
}
