package ru.cft.focusstart.kolesnikov.repository.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.kolesnikov.repository.DataAccessException;
import ru.cft.focusstart.kolesnikov.repository.DataSourceProvider;
import ru.cft.focusstart.kolesnikov.dto.publisher.PublisherMessage;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcPublisherManager implements PublisherDBManager {
    private final DataSource dataSource;
    private static JdbcPublisherManager INSTANCE = new JdbcPublisherManager();
    private final static Logger log = LoggerFactory.getLogger(JdbcPublisherManager.class);

    private static final String ADD = "INSERT INTO publishers(name) " +
            "values (?)";
    private static final String GET = "SELECT * FROM publishers" +
            " WHERE lower(name) LIKE lower('%'|| ? ||'%')";
    private static final String GET_BY_ID = "SELECT * from publishers " +
            "WHERE id = ? ";
    private static final String UPDATE = "UPDATE publishers " +
            "SET name = ? " +
            "where id = ? ";

    private JdbcPublisherManager() {
        this.dataSource = DataSourceProvider.getDataSource();
    }

    public static JdbcPublisherManager getInstance() {
        return INSTANCE;
    }

    @Override
    public PublisherMessage add(PublisherMessage reqMsg) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS)
        ) {
            String name = reqMsg.getName();
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            long id = resultSet.getLong("id");
            return new PublisherMessage(name, id);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public PublisherMessage getById(long id) { // может сделать чтоб возвращал optional
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(GET_BY_ID)
        ) {
            pStatement.setLong(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return readPublishers(resultSet);

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public List<PublisherMessage> get(String name) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(GET)) {
            List<PublisherMessage> responseMessages = new ArrayList<>();
            pStatement.setString(1, name == null ? "" : name);
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                responseMessages.add(readPublishers(resultSet));
            }
            if (responseMessages.isEmpty()) {
                return null;
            }
            return responseMessages;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void update(PublisherMessage reqMsg, Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(UPDATE)
        ) {
            pStatement.setString(1, reqMsg.getName());
            pStatement.setLong(2, id);
            pStatement.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    private PublisherMessage readPublishers(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        long id = resultSet.getLong("id");
        return new PublisherMessage(name, id);
    }
}
