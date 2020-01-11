package ru.cft.focusstart.kolesnikov.service.publisher;

import ru.cft.focusstart.kolesnikov.dto.game.GameMessage;
import ru.cft.focusstart.kolesnikov.dto.publisher.PublisherMessage;

import java.util.List;

public interface PublisherService {

    PublisherMessage add(PublisherMessage reqMsg);

    List<PublisherMessage> get(String name);

    PublisherMessage getById(long id);

    PublisherMessage update(PublisherMessage reqMsg, long id);

    List<GameMessage> getGames(long id);

}
