package by.bsuir.lab.servlethotel.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public interface IEntityField<E> {

    String getField();

    void setValue(PreparedStatement statement, Object value, int index) throws SQLException;

    interface Preparer {

        Preparer ENUM_PREPARER = (statement, value, index) -> statement.setObject(index, ((Enum<?>)value).name(), Types.OTHER);
        Preparer STRING_PREPARER = (statement, value, index) -> statement.setString(index, (String) value);
        Preparer INTEGER_PREPARER = (statement, value, index) -> statement.setInt(index, (Integer) value);
        Preparer BOOLEAN_PREPARER = (statement, value, index) -> statement.setBoolean(index, (Boolean) value);

        void prepare(PreparedStatement statement, Object value, int index) throws SQLException;
    }
}
