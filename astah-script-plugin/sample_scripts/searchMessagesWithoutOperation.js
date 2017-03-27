//Only for Astah UML and Professional.
//This script searches Messages which is not related to an operation.

var COLOR_PROPERTY_KEY = "font.color";
var newColor = '#0000ff';

run();

function run() {
    var targets = searchMessagesWithoutOperation();

    if (targets.length === 0) {
        print('No target messages found');
        return;
    }
    
    /*
    TransactionManager.beginTransaction();
    for (var i in targets) {
        edit(targets[i]);
    }
    TransactionManager.endTransaction();

    println('The color of the messages were changed.');
    */
}

function searchMessagesWithoutOperation() {
    with(new JavaImporter(
        com.change_vision.jude.api.inf.model)) {
        var targets = [];
        var messages = astah.findElements(IMessage.class);
        for (var i in messages) {
            var message = messages[i];
            if (message.isReturnMessage() || message.isCreateMessage()) {
                continue;  //ignore
            }
            var operation = message.getOperation();
            if (operation === null) {
                targets.push(message);
                print('HIT: Message [' + message.getName()
                    + '] in Sequence diagram [' + message.getPresentations()[0].getDiagram().getFullName('::') + ']');
            }
        }
    }
    return targets;
}

function edit(target) {
    var presentations = target.getPresentations();
    for (var j in presentations) {
        var messagePs = presentations[j];
        // Change color of the message
        messagePs.setProperty(COLOR_PROPERTY_KEY, newColor);
    }
}
