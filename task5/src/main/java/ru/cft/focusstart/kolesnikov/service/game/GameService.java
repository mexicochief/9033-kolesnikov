package ru.cft.focusstart.kolesnikov.service.game;

import ru.cft.focusstart.kolesnikov.dto.game.GameMessage;

import java.sql.Date;
import java.util.List;

public interface GameService {
    List<GameMessage> get(String name, String developerName);

    GameMessage add(GameMessage reqMsg);

    GameMessage getById(long id);

    List<GameMessage> getByReleaseDate(Date releaseDate);

    GameMessage update(GameMessage reqMsg, long id);

    void delete(long id);
}
