package by.bsuir.lab.servlethotel.dao.room;

import by.bsuir.lab.servlethotel.dao.AbstractDao;
import by.bsuir.lab.servlethotel.dao.iface.IRoomDao;
import by.bsuir.lab.servlethotel.entity.hotel.Room;

import java.sql.*;

public class RoomDao extends AbstractDao<Room> implements IRoomDao {

    private static final String DELETE_QUERY = "DELETE FROM room";
    private static final String SELECT_QUERY = "SELECT * FROM room LEFT OUTER JOIN reservation ON reservation_id = reservation.id LEFT OUTER JOIN \"user\" ON user_id = \"user\".id LEFT OUTER JOIN role on role.id = \"user\".role_id\n";

    @Override
    protected String tableName() {
        return "room";
    }

    @Override
    protected PreparedStatement prepareSaveStatement(Connection connection, Room room) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO room(label, has_kitchen, has_bath) " +
                        "VALUES(?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setInt(1, room.getLabel());
        preparedStatement.setBoolean(2, room.getHasKitchen());
        preparedStatement.setBoolean(3, room.getHasBath());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareUpdateStatement(Connection connection, Room room) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE room SET " +
                        "label = ?, has_kitchen = ?, has_bath = ?, reservation_id = ? WHERE id = ?"
        );
        preparedStatement.setInt(1, room.getLabel());
        preparedStatement.setBoolean(2, room.getHasKitchen());
        preparedStatement.setBoolean(3, room.getHasBath());
        if(room.getReservation() == null) {
            preparedStatement.setNull(4, Types.INTEGER);
        } else {
            preparedStatement.setInt(4, room.getReservation().getId());
        }
        preparedStatement.setInt(5, room.getId());
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
    protected Room processResultRow(ResultSet resultSet) throws SQLException {
        return RoomField.from(resultSet);
    }
}
