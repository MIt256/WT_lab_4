package by.bsuir.lab.servlethotel.dao.authentication;

import by.bsuir.lab.servlethotel.dao.AbstractDao;
import by.bsuir.lab.servlethotel.dao.iface.IAuthenticationDao;
import by.bsuir.lab.servlethotel.entity.security.AuthenticationEntity;

import java.sql.*;
import java.util.Map;

public class AuthenticationDao extends AbstractDao<AuthenticationEntity> implements IAuthenticationDao {

    private static final String DELETE_QUERY = "DELETE FROM authentication";
    private static final String SELECT_QUERY = "SELECT * FROM authentication";

    @Override
    protected String tableName() {
        return "authentication";
    }

    @Override
    protected PreparedStatement prepareSaveStatement(Connection connection, AuthenticationEntity entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO authentication(value) " +
                        "VALUES(?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, entity.getValue());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareUpdateStatement(Connection connection, AuthenticationEntity entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE authentication SET " +
                        "value = ? WHERE id = ?"
        );
        preparedStatement.setString(1, entity.getValue());
        preparedStatement.setInt(2, entity.getId());
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
    protected AuthenticationEntity processResultRow(ResultSet resultSet) throws SQLException {
        return AuthenticationField.from(resultSet);
    }

    @Override
    public AuthenticationEntity getByValue(String value) {
        return getByFields(Map.of(AuthenticationField.VALUE, value)).stream()
                .findAny()
                .orElse(null);
    }
}
