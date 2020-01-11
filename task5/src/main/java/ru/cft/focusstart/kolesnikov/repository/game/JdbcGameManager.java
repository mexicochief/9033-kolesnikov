package ru.cft.focusstart.kolesnikov.repository.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.kolesnikov.repository.DataAccessException;
import ru.cft.focusstart.kolesnikov.repository.DataSourceProvider;
import ru.cft.focusstart.kolesnikov.dto.game.GameMessage;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcGameManager implements GameDBManager {
    private DataSource dataSource;
    private static final JdbcGameManager INSTANCE = new JdbcGameManager();
    private final static Logger log = LoggerFactory.getLogger(JdbcGameManager.class);

    private static final String ADD = "INSERT INTO games(name, description, release_date, developerid, publisherid)" +
            "values(?,?,?,?,?)";
    private static final String GET_BY_NAME = "SELECT a.id," +
            "a.developerid," +
            "a.name," +
            "a.publisherid," +
            "a.release_date," +
            "a.description," +
            "b.name " +
            "FROM games a " +
            "LEFT JOIN developers b on a.developerid = b.id " +
            "WHERE lower(a.name) LIKE lower('%'|| ? || '%') " +
            "and   lower(b.name) LIKE lower('%'|| ? || '%') ";
    private static final String GET_BY_ID = "SELECT * FROM games " +
            "WHERE id = ?";
    private static final String GET_BY_PUBLISHER_ID = "SELECT * FROM games " +
            "WHERE publisherid = ?";
    private static final String GET_BY_DEVELOPER_ID = "SELECT * FROM games " +
            "WHERE developerid = ?";
    private static final String GET_BY_DATE = "SELECT * FROM games " +
            "WHERE  release_date = ? ";
    private static final String UPDATE = "UPDATE games " +
            "SET name = ?, " +
            "description = ?," +
            "release_date = ?," +
            "developerid = ?," +
            "publisherid = ? " +
            "WHERE id = ?";
    private static final String DELETE = "DELETE FROM games " +
            "WHERE id = ?";

    private JdbcGameManager() {
        this.dataSource = DataSourceProvider.getDataSource();
    }

    public static JdbcGameManager getInstance() {
        return INSTANCE;
    }

    @Override
    public GameMessage add(GameMessage reqMsg) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS)) {
            String name = reqMsg.getName();
            String description = reqMsg.getDescription();
            Date releaseDate = reqMsg.getReleaseDate();
            long developerId = reqMsg.getDeveloperId();
            long publisherId = reqMsg.getPublisherId();
            pStatement.setString(1, name);
            pStatement.setString(2, description);
            pStatement.setDate(3, releaseDate);
            pStatement.setLong(4, developerId);
            pStatement.setLong(5, publisherId);
            pStatement.executeUpdate();
            ResultSet resultSet = pStatement.getGeneratedKeys();
            resultSet.next();
            return new GameMessage(
                    name,
                    resultSet.getLong("id"),
                    description,
                    releaseDate,
                    developerId,
                    publisherId);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public GameMessage getById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(GET_BY_ID)) {
            pStatement.setLong(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return readGames(resultSet);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public List<GameMessage> get(String name, String developerName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(GET_BY_NAME)) {
            System.out.println(name);
            System.out.println(developerName);
            pStatement.setString(1, name == null ? "" : name);
            pStatement.setString(2, developerName == null ? "" : developerName);
            return getGameMessages(pStatement);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public List<GameMessage> getByReleaseDate(Date releaseDate) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(GET_BY_DATE)
        ) {
            pStatement.setDate(1, releaseDate);
            return getGameMessages(pStatement);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public List<GameMessage> getByDeveloperId(long id) {
        return getGameByForeignId(id, GET_BY_DEVELOPER_ID);
    }

    @Override
    public List<GameMessage> getByPublisherId(long id) {
        return getGameByForeignId(id, GET_BY_PUBLISHER_ID);
    }

    private List<GameMessage> getGameByForeignId(long id, String query) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(query)) {

            pStatement.setLong(1, id);
            return getGameMessages(pStatement);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void update(GameMessage reqMsg, Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pStatementForReturn = connection.prepareStatement(GET_BY_ID);
                PreparedStatement pStatement = connection.prepareStatement(UPDATE)) {
            pStatement.setString(1, reqMsg.getName());
            pStatement.setString(2, reqMsg.getDescription());
            pStatement.setDate(3, reqMsg.getReleaseDate());
            pStatement.setLong(4, reqMsg.getDeveloperId());
            pStatement.setLong(5, reqMsg.getPublisherId());
            pStatement.setLong(6, id);
            pStatement.executeUpdate();

            pStatementForReturn.setLong(1, id);
            pStatementForReturn.executeQuery();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(DELETE)
        ) {
            pStatement.setLong(1, id);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    private List<GameMessage> getGameMessages(PreparedStatement pStatement) throws SQLException {
        ResultSet resultSet = pStatement.executeQuery();
        List<GameMessage> respMessages = new ArrayList<>();
        while (resultSet.next()) {
            respMessages.add(readGames(resultSet));
        }
        if (respMessages.isEmpty()) {
            return null;
        }
        return respMessages;
    }

    private GameMessage readGames(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        Date releaseDate = resultSet.getDate("release_date");
        long developerId = resultSet.getLong("developerId");
        long publisherId = resultSet.getLong("publisherId");
        return new GameMessage(name, id, description, releaseDate, developerId, publisherId);
    }
}
