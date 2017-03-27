package com.change_vision.astah.extension.plugin.script.command;

import javax.swing.JOptionPane;

import com.change_vision.astah.extension.plugin.script.ScriptViewContext;
import com.change_vision.astah.extension.plugin.script.util.Messages;

public class CloseCommand {
    public static void execute(ScriptViewContext context) {
        if (context.isModified) {
            int result = JOptionPane.showConfirmDialog(context.dialog,
                    Messages.getMessage("message.ask_save"));
            if (result == JOptionPane.YES_OPTION) {
                SaveCommand.execute(context);
            }
        }

        context.dialog.setVisible(false);
    }
}