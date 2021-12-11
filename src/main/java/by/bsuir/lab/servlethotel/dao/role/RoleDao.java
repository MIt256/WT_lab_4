package by.bsuir.lab.servlethotel.dao.role;

import by.bsuir.lab.servlethotel.dao.AbstractDao;
import by.bsuir.lab.servlethotel.dao.iface.IRoleDao;
import by.bsuir.lab.servlethotel.entity.security.Role;
import by.bsuir.lab.servlethotel.entity.security.UserRole;

import java.sql.*;
import java.util.Map;

public class RoleDao extends AbstractDao<Role> implements IRoleDao {

    private static final String DELETE_QUERY = "DELETE FROM role";
    private static final String SELECT_QUERY = "SELECT * FROM role";

    @Override
    protected String tableName() {
        return "role";
    }

    @Override
    protected PreparedStatement prepareSaveStatement(Connection connection, Role role) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO role(role) " +
                        "VALUES(?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, role.getRole().toString());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareUpdateStatement(Connection connection, Role role) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE role SET " +
                        "role = ? WHERE id = ?"
        );
        preparedStatement.setString(1, role.getRole().toString());
        preparedStatement.setInt(2, role.getId());
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
    protected Role processResultRow(ResultSet resultSet) throws SQLException {
        return RoleField.from(resultSet);
    }

    @Override
    public Role getByRoleName(UserRole roleName) {
        return getByFields(Map.of(RoleField.ROLE, roleName)).stream()
                .findFirst()
                .orElse(null);
    }
}
