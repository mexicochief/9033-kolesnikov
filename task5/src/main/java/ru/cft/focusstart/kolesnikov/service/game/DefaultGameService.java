package ru.cft.focusstart.kolesnikov.service.game;

import ru.cft.focusstart.kolesnikov.dto.game.GameMessage;
import ru.cft.focusstart.kolesnikov.exception.ObjectNotFoundException;
import ru.cft.focusstart.kolesnikov.repository.developer.DeveloperDBManager;
import ru.cft.focusstart.kolesnikov.repository.developer.JdbcDeveloperManager;
import ru.cft.focusstart.kolesnikov.repository.game.GameDBManager;
import ru.cft.focusstart.kolesnikov.repository.game.JdbcGameManager;
import ru.cft.focusstart.kolesnikov.repository.publisher.JdbcPublisherManager;
import ru.cft.focusstart.kolesnikov.repository.publisher.PublisherDBManager;
import ru.cft.focusstart.kolesnikov.service.validation.Validator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DefaultGameService implements GameService {
    private static final DefaultGameService INSTANCE = new DefaultGameService();
    private GameDBManager gameManager;
    private PublisherDBManager publisherManager;
    private DeveloperDBManager developerManager;

    DefaultGameService() {
        this.gameManager = JdbcGameManager.getInstance();
        this.publisherManager = JdbcPublisherManager.getInstance();
        this.developerManager = JdbcDeveloperManager.getInstance();
    }

    public static DefaultGameService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<GameMessage> get(String name, String developerName) {
        List<GameMessage> respMsg = gameManager.get(name, developerName);
        if (respMsg == null) {
            return new ArrayList<>();
        }
        return respMsg;
    }

    @Override
    public GameMessage add(GameMessage reqMsg) {
        validate(reqMsg);
        if (publisherManager.getById(reqMsg.getPublisherId()) == null) {
            throw new ObjectNotFoundException(String.format("Publisher with id - %s not found", reqMsg.getPublisherId()));
        }
        if (developerManager.getById(reqMsg.getDeveloperId()) == null) {
            throw new ObjectNotFoundException(String.format("Publisher with id - %s not found", reqMsg.getDeveloperId()));
        }
        return gameManager.add(reqMsg);
    }

    @Override
    public GameMessage getById(long id) {
        GameMessage respMsg = gameManager.getById(id);
        if (respMsg == null) {
            throw new ObjectNotFoundException(String.format("Game with id - %s not found", id));
        }
        return respMsg;
    }

    @Override
    public List<GameMessage> getByReleaseDate(Date releaseDate) {
        List<GameMessage> respMsg = gameManager.getByReleaseDate(releaseDate);
        if (respMsg == null) {
            throw new ObjectNotFoundException(String.format("Game with release date - %s not found", releaseDate));
        }
        return respMsg;
    }

    @Override
    public GameMessage update(GameMessage reqMsg, long id) {
        validate(reqMsg);

        if (gameManager.getById(id) == null) {
            return gameManager.add(reqMsg);
        }
        GameMessage respMsg = new GameMessage(
                reqMsg.getName(),
                id,
                reqMsg.getDescription(),
                reqMsg.getReleaseDate(),
                reqMsg.getDeveloperId(),
                reqMsg.getPublisherId());
        gameManager.update(reqMsg, id);
        return respMsg;
    }

    @Override
    public void delete(long id) {
        gameManager.delete(id);
    }

    private void validate(GameMessage msg) {
        Validator.checkNull(msg.getId(), "id");
        Validator.checkNotNull(msg.getName(), "name");
        Validator.checkNotNull(msg.getDescription(), "description");
        Validator.checkNotNull(msg.getReleaseDate(), "releaseDate");
        Validator.checkNotNull(msg.getDeveloperId(), "developerId");
        Validator.checkNotNull(msg.getPublisherId(), "publisherId");
        Validator.checkSize(msg.getName(), 1, 256);
        Validator.checkSize(msg.getDescription(), 1, 4096);
    }
}
