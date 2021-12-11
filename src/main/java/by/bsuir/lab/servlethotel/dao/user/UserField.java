package by.bsuir.lab.servlethotel.dao.user;

import by.bsuir.lab.servlethotel.dao.IEntityField;
import by.bsuir.lab.servlethotel.dao.role.RoleField;
import by.bsuir.lab.servlethotel.entity.security.Role;
import by.bsuir.lab.servlethotel.entity.security.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
@Getter
public enum UserField implements IEntityField<User> {
    USERNAME("username", Preparer.STRING_PREPARER),
    PASSWORD("password", Preparer.STRING_PREPARER),
    ROLE("role_id", Preparer.INTEGER_PREPARER);

    private final String field;
    private final Preparer preparer;

    public static User from(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        return injectValues(user, resultSet);
    }

    public static User injectValues(User user, ResultSet resultSet) throws SQLException {
        user.setUsername(resultSet.getString(USERNAME.getField()));
        user.setPassword(resultSet.getString(PASSWORD.getField()));
        user.setRole(RoleField.injectValues(new Role(resultSet.getInt(ROLE.getField())), resultSet));
        return user;
    }

    @Override
    public void setValue(PreparedStatement statement, Object value, int index) throws SQLException {
        preparer.prepare(statement, value, index);
    }
}
