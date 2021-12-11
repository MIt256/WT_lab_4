package by.bsuir.lab.servlethotel.dao.user;

import by.bsuir.lab.servlethotel.dao.AbstractDao;
import by.bsuir.lab.servlethotel.dao.iface.IUserDao;
import by.bsuir.lab.servlethotel.entity.security.User;

import java.sql.*;
import java.util.Map;

public class UserDao extends AbstractDao<User> implements IUserDao {

    private static final String DELETE_QUERY = "DELETE FROM \"user\"";
    private static final String SELECT_QUERY = "SELECT * FROM \"user\" JOIN role on role.id = \"user\".role_id";

    @Override
    protected String tableName() {
        return "\"user\"";
    }

    @Override
    protected PreparedStatement prepareSaveStatement(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO \"user\"(username, password, role_id) " +
                        "VALUES(?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getRole().getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareUpdateStatement(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE \"user\" SET " +
                        "username = ?, password = ?, role_id = ? WHERE id = ?"
        );
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getRole().getId());
        preparedStatement.setInt(4, user.getId());
        return preparedStatement;
    }

    @Override
    protected String prepareDeleteTemplate() {
        return DELETE_QUERY;
    }

    @Override
    protected String prepareSelectTemplate() {
        return SELECT_QUERY;
    }

    @Override
    protected User processResultRow(ResultSet resultSet) throws SQLException {
        return UserField.from(resultSet);
    }

    @Override
    public User getByUsername(String username) {
        return getByFields(Map.of(UserField.USERNAME, username)).stream()
                .findAny()
                .orElse(null);
    }
}
