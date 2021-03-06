package info.openrpg.telegram.commands.actions;

import info.openrpg.telegram.input.InputMessage;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Collections;
import java.util.List;

public class DoNothingComand implements ExecutableCommand {

    @Override
    public List<SendMessage> execute(InputMessage inputMessage) {
        return Collections.emptyList();
    }

    @Override
    public List<SendMessage> handleCrash(RuntimeException e, InputMessage inputMessage) {
        return Collections.emptyList();
    }
}
