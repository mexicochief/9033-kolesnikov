package ru.cft.focusstart.kolesnikov.repository.game;

import ru.cft.focusstart.kolesnikov.dto.game.GameMessage;

import java.sql.Date;
import java.util.List;

public interface GameDBManager {

    GameMessage add(GameMessage reqMsg);

    GameMessage getById(long id); // мб заменить на optional

    List<GameMessage> get(String name, String developerName); // надо чтоб get получал строку по которой искать в базе

    List<GameMessage> getByReleaseDate(Date releaseDate);

    void update(GameMessage reqMsg, Long id);

    List<GameMessage> getByDeveloperId(long id);

    List<GameMessage> getByPublisherId(long id);

    void delete(long id);
}
