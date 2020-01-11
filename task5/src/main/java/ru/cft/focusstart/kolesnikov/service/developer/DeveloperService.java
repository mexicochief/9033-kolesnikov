package ru.cft.focusstart.kolesnikov.service.developer;

import ru.cft.focusstart.kolesnikov.dto.developer.DeveloperMessage;
import ru.cft.focusstart.kolesnikov.dto.game.GameMessage;

import java.util.List;

public interface DeveloperService {
    List<DeveloperMessage> get(String name);

    DeveloperMessage add(DeveloperMessage reqMsg);

    DeveloperMessage getById(long id);

    List<GameMessage> getGames(long id);

    DeveloperMessage update(DeveloperMessage reqMsg, long id);
}
