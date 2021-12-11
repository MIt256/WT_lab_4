package by.bsuir.lab.servlethotel.dao.role;

import by.bsuir.lab.servlethotel.entity.security.Role;
import by.bsuir.lab.servlethotel.dao.IEntityField;
import by.bsuir.lab.servlethotel.entity.security.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
@Getter
public enum RoleField implements IEntityField<Role> {
    ROLE("role", Preparer.ENUM_PREPARER);

    private final String field;
    private final Preparer preparer;

    public static Role from(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getInt("id"));
        return injectValues(role, resultSet);
    }

    public static Role injectValues(Role role, ResultSet resultSet) throws SQLException {
        role.setRole(UserRole.valueOf(resultSet.getString(ROLE.getField())));
        return role;
    }

    @Override
    public void setValue(PreparedStatement statement, Object value, int index) throws SQLException {
        preparer.prepare(statement, value, index);
    }
}
