package ru.cft.focusstart.kolesnikov.service.developer;

import ru.cft.focusstart.kolesnikov.repository.developer.DeveloperDBManager;
import ru.cft.focusstart.kolesnikov.repository.developer.JdbcDeveloperManager;
import ru.cft.focusstart.kolesnikov.repository.game.GameDBManager;
import ru.cft.focusstart.kolesnikov.repository.game.JdbcGameManager;
import ru.cft.focusstart.kolesnikov.dto.developer.DeveloperMessage;
import ru.cft.focusstart.kolesnikov.dto.game.GameMessage;
import ru.cft.focusstart.kolesnikov.exception.ObjectNotFoundException;
import ru.cft.focusstart.kolesnikov.service.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class DefaultDeveloperService implements DeveloperService {
    private static final DefaultDeveloperService INSTANCE = new DefaultDeveloperService();
    private DeveloperDBManager developerManager;
    private GameDBManager gameManager;

    private DefaultDeveloperService() {
        this.developerManager = JdbcDeveloperManager.getInstance();
        this.gameManager = JdbcGameManager.getInstance();
    }

    public static DefaultDeveloperService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<DeveloperMessage> get(String name) {
        List<DeveloperMessage> respMsg = developerManager.get(name);
        if (respMsg == null) {
            return new ArrayList<>();

        }
        return developerManager.get(name);
    }

    @Override
    public DeveloperMessage add(DeveloperMessage reqMsg) {
        validate(reqMsg);
        return developerManager.add(reqMsg);
    }

    @Override
    public DeveloperMessage getById(long id) {
        DeveloperMessage respMsg = developerManager.getById(id);
        if (respMsg == null) {
            throw new ObjectNotFoundException(String.format("Developer with id - %s not found", id));
        }
        return respMsg;
    }

    @Override
    public List<GameMessage> getGames(long id) {
        if (developerManager.getById(id) == null) {
            throw new ObjectNotFoundException(String.format("Developer with id %s not found", id));
        }
        List<GameMessage> respMsg = gameManager.getByDeveloperId(id);
        if (respMsg == null) {
            return new ArrayList<>();
        }
        return respMsg;
    }

    @Override
    public DeveloperMessage update(DeveloperMessage reqMsg, long id) {
        validate(reqMsg);
        if (developerManager.getById(id) == null) {
            return developerManager.add(reqMsg);
        }
        developerManager.update(reqMsg, id);
        return new DeveloperMessage(reqMsg.getName(), id, reqMsg.getDescription());
    }

    private void validate(DeveloperMessage msg) {
        Validator.checkNotNull(msg.getName(), "name");
        Validator.checkNotNull(msg.getDescription(), "description");
        Validator.checkNull(msg.getId(), "id");
        Validator.checkSize(msg.getName(), 1, 256);
        Validator.checkSize(msg.getDescription(), 1, 4096);
    }
}
