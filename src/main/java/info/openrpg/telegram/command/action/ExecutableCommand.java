package info.openrpg.telegram.command.action;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import javax.persistence.EntityManager;
import java.util.List;

public interface ExecutableCommand {
    List<SendMessage> execute(EntityManager entityManager, Update update);
    List<SendMessage> handleCrash(RuntimeException e, Update update);
}
