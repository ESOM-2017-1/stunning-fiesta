// This script prints the properties of the selected element.
// You should select an element in the DiagramEditor before running this.
run();

function run() {
    var targets = getSelectedPresentationsInDiagramEditor();

    if (targets.length === 0) {
        print('Please select an element in a diagram');
        return;
    }

    for (var i in targets) {
        printAllProperties(targets[i]);
    }
}

function getSelectedPresentationsInDiagramEditor() {
    var diagramViewManager = astah.getViewManager().getDiagramViewManager();
    return diagramViewManager.getSelectedPresentations();
}

function printAllProperties(presentation) {
    with(new JavaImporter(
            java.util)) {
        var props = presentation.getProperties();
        var keyList = new ArrayList(props.keySet());
        Collections.sort(keyList);
        print('---------------------------');
        for (var i = 0; i < keyList.size(); i++) {
            var key = keyList.get(i);
            var value = props.get(key);
            print(key + ': ' + value);
        }
        print('---------------------------');
    }
}
