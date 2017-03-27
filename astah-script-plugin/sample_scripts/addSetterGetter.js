//This script generates the setter/getter operations for the selected attributes.
//You should select attributes in the StructureTree before running this.
run();

function run() {
    var attributes = getSelectedAttributesInProjectView();

    if (attributes.length === 0) {
        print('Please select attributes that you want to add setter/getter in StructureTree');
        return;
    }

    with(new JavaImporter(
            com.change_vision.jude.api.inf.editor)) {
        TransactionManager.beginTransaction();
        for (var i in attributes) {
            addSetterGetter(attributes[i]);
        }
        TransactionManager.endTransaction();
    }
}

function getSelectedAttributesInProjectView() {
    with(new JavaImporter(
            com.change_vision.jude.api.inf.model)) {
        var attributes = [];
        var projectViewManager = astah.getViewManager().getProjectViewManager();
        var selectedEntities = projectViewManager.getSelectedEntities();
        for (var i in selectedEntities) {
            var entity = selectedEntities[i];
            if (entity instanceof IAttribute) {
                attributes.push(entity);
                print('Target attribute: ' + entity.getName());
            }
        }
    }

    return attributes;
}

function addSetterGetter(attribute) {
    var editor = astah.getModelEditorFactory().getBasicModelEditor();
    var clazz = attribute.getOwner();
    var attributeName = attribute.getName();
    //setter
    var setter = editor.createOperation(clazz, getSetterName(attributeName), 'void');
    editor.createParameter(setter, attribute.getName(), attribute.getType());
    print('Added Setter Operation: ' + setter.getName());
    //getter
    var getter = editor.createOperation(clazz, getGetterName(attributeName), attribute.getType());
    print('Added Getter Operation: ' + getter.getName());
}

function getSetterName(attributeName) {
    return 'set' + attributeName;
}

function getGetterName(attributeName) {
    return 'get' + attributeName;
}
