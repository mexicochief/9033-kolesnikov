package ru.cft.focusstart.kolesnikov.repository.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.kolesnikov.repository.DataAccessException;
import ru.cft.focusstart.kolesnikov.repository.DataSourceProvider;
import ru.cft.focusstart.kolesnikov.dto.developer.DeveloperMessage;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDeveloperManager implements DeveloperDBManager {
    private final DataSource dataSource;
    private static final JdbcDeveloperManager INSTANCE = new JdbcDeveloperManager();
    private final static Logger log = LoggerFactory.getLogger(JdbcDeveloperManager.class);

    private static final String ADD = "INSERT INTO developers(name, description) " +
            "values (?,?)";
    private static final String GET_BY_NAME = "SELECT * FROM developers " +
            "WHERE lower(name) LIKE lower('%'|| ? ||'%')";
    private static final String GET_BY_ID = "SELECT * from developers " +
            "WHERE id = ? ";
    private static final String UPDATE = "UPDATE developers " +
            "SET name = ?, description = ? " +
            "where id = ? ";

    private JdbcDeveloperManager() {
        this.dataSource = DataSourceProvider.getDataSource();
    }

    public static JdbcDeveloperManager getInstance() {
        return INSTANCE;
    }

    @Override
    public DeveloperMessage add(DeveloperMessage reqMsg) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS)
        ) {
            String name = reqMsg.getName();
            long id;
            String description = reqMsg.getDescription();

            pStatement.setString(1, name);
            pStatement.setString(2, description);
            pStatement.executeUpdate();

            ResultSet resultSet = pStatement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getLong("id");
            return new DeveloperMessage(name, id, description);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public DeveloperMessage getById(long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(GET_BY_ID)
        ) {
            pStatement.setLong(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return readDeveloper(resultSet);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public List<DeveloperMessage> get(String name) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(GET_BY_NAME)) {
            List<DeveloperMessage> developerMessages = new ArrayList<>();

            pStatement.setString(1, name == null ? "" : name);
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                developerMessages.add(readDeveloper(resultSet));
            }
            if (developerMessages.isEmpty()) {
                return null;
            }
            return developerMessages;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public DeveloperMessage update(DeveloperMessage reqMsg, Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(UPDATE)
        ) {
            pStatement.setString(1, reqMsg.getName());
            pStatement.setString(2, reqMsg.getDescription());
            pStatement.setLong(3, id);
            pStatement.executeUpdate();
            return new DeveloperMessage(
                    reqMsg.getName(),
                    reqMsg.getId(),
                    reqMsg.getDescription());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    } // исправить update везде

    private DeveloperMessage readDeveloper(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        long id = resultSet.getLong("id");
        String description = resultSet.getString("description");
        return new DeveloperMessage(name, id, description);
    }
}