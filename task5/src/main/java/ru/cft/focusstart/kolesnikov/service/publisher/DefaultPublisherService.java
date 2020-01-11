package ru.cft.focusstart.kolesnikov.service.publisher;

import ru.cft.focusstart.kolesnikov.repository.game.GameDBManager;
import ru.cft.focusstart.kolesnikov.repository.game.JdbcGameManager;
import ru.cft.focusstart.kolesnikov.repository.publisher.JdbcPublisherManager;
import ru.cft.focusstart.kolesnikov.repository.publisher.PublisherDBManager;
import ru.cft.focusstart.kolesnikov.dto.game.GameMessage;
import ru.cft.focusstart.kolesnikov.dto.publisher.PublisherMessage;
import ru.cft.focusstart.kolesnikov.exception.ObjectNotFoundException;
import ru.cft.focusstart.kolesnikov.service.validation.Validator;

import java.util.List;

public class DefaultPublisherService implements PublisherService {
    static final DefaultPublisherService INSTANCE = new DefaultPublisherService();
    private PublisherDBManager publisherManager;
    private GameDBManager gameManager;

    private DefaultPublisherService() {
        this.publisherManager = JdbcPublisherManager.getInstance();
        this.gameManager = JdbcGameManager.getInstance();
    }

    public static DefaultPublisherService getInstance() {
        return INSTANCE;
    }

    @Override
    public PublisherMessage add(PublisherMessage reqMsg) {
        validate(reqMsg);
        return publisherManager.add(reqMsg);
    }

    @Override
    public List<PublisherMessage> get(String name) {
        List<PublisherMessage> respMsg = publisherManager.get(name);
        if (respMsg == null) {
            throw new ObjectNotFoundException(String.format("Publisher with name - %s not found", name));
        }
        return respMsg;
    }

    @Override
    public PublisherMessage getById(long id) {
        PublisherMessage respMsg = publisherManager.getById(id);
        if (respMsg == null) {
            throw new ObjectNotFoundException(String.format("Publisher with id - %s not found", id));
        }
        return respMsg;
    }

    @Override
    public PublisherMessage update(PublisherMessage reqMsg, long id) {
        validate(reqMsg);
        if (publisherManager.getById(id) == null) {
            return add(reqMsg);
        }
        publisherManager.update(reqMsg, id);

        return new PublisherMessage(reqMsg.getName(), id);
    }

    @Override
    public List<GameMessage> getGames(long id) {
        if (publisherManager.getById(id) == null) {
            throw new ObjectNotFoundException(String.format("Publisher with id - %s not found", id));
        }
        List<GameMessage> respMsg = gameManager.getByPublisherId(id);
        if (respMsg == null) {
            throw new ObjectNotFoundException(String.format("Publisher with id - %s have not games", id));
        }
        return respMsg;
    }

    private void validate(PublisherMessage msg) {
        Validator.checkNotNull(msg.getName(), "name");
        Validator.checkNull(msg.getId(), "id");
        Validator.checkSize(msg.getName(), 1, 256);
    }
}
