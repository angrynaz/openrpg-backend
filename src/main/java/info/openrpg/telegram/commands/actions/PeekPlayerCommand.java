package info.openrpg.telegram.commands.actions;

import com.google.common.base.Joiner;
import info.openrpg.database.repositories.PlayerRepository;
import info.openrpg.telegram.input.InputMessage;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PeekPlayerCommand implements ExecutableCommand {
    private static final Joiner JOINER = Joiner.on(" ").skipNulls();
    private static final String UNKNOWN_PLAYER_MESSAGE = "Ты попытался потыкать палкой несуществующего пидора.";
    private static final String PLAYER_PEEKED_MESSAGE = "Тебя потыкал палкой";
    private static final String WRONG_ARGUMENTS_NUMBER_MESSAGE = "Неправильный формат команды\n" +
            "Пример:\n" +
            "/peek_player DarkCasual";

    private final PlayerRepository playerRepository;

    public PeekPlayerCommand(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<SendMessage> execute(InputMessage inputMessage) {
        return Optional.of(inputMessage)
                .filter(iM -> iM.hasArguments(1))
                .map(iM -> iM.getArgument(1))
                .map(userName -> getPlayerByUsername(inputMessage.getFrom(), userName, inputMessage.getChatId()))
                .map(Collections::singletonList)
                .orElse(Collections.singletonList(
                        new SendMessage()
                                .setChatId(inputMessage.getChatId())
                                .setText(WRONG_ARGUMENTS_NUMBER_MESSAGE)
                        )
                );
    }

    @Override
    public List<SendMessage> handleCrash(RuntimeException e, InputMessage inputMessage) {
        return Collections.emptyList();
    }

    private SendMessage getPlayerByUsername(User from, String userName, Long chatId) {
        return playerRepository.findPlayerByUsername(userName)
                .map(player -> new SendMessage()
                        .setChatId(String.valueOf(player.getId()))
                        .setText(JOINER.join(PLAYER_PEEKED_MESSAGE, "@".concat(from.getUserName())))
                )
                .orElse(new SendMessage()
                        .setChatId(chatId)
                        .setText(UNKNOWN_PLAYER_MESSAGE)
                );
    }
}
