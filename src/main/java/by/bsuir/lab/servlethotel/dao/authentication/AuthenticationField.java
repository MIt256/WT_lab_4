package by.bsuir.lab.servlethotel.dao.authentication;

import by.bsuir.lab.servlethotel.dao.IEntityField;
import by.bsuir.lab.servlethotel.entity.security.AuthenticationEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
@Getter
public enum AuthenticationField implements IEntityField<AuthenticationEntity> {
    VALUE("value", IEntityField.Preparer.STRING_PREPARER);

    private final String field;
    private final IEntityField.Preparer preparer;

    public static AuthenticationEntity from(ResultSet resultSet) throws SQLException {
        AuthenticationEntity authentication = new AuthenticationEntity();
        authentication.setId(resultSet.getInt("id"));
        return injectValues(authentication, resultSet);
    }

    public static AuthenticationEntity injectValues(AuthenticationEntity authentication, ResultSet resultSet) throws SQLException {
        authentication.setValue(resultSet.getString(VALUE.getField()));
        return authentication;
    }

    @Override
    public void setValue(PreparedStatement statement, Object value, int index) throws SQLException {
        preparer.prepare(statement, value, index);
    }
}
