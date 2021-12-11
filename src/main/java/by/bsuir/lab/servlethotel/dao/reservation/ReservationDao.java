package by.bsuir.lab.servlethotel.dao.reservation;

import by.bsuir.lab.servlethotel.dao.AbstractDao;
import by.bsuir.lab.servlethotel.dao.iface.IReservationDao;
import by.bsuir.lab.servlethotel.entity.hotel.Reservation;

import java.sql.*;
import java.util.Map;

public class ReservationDao extends AbstractDao<Reservation> implements IReservationDao {

    private static final String DELETE_QUERY = "DELETE FROM reservation";
    private static final String SELECT_QUERY = "SELECT * FROM reservation JOIN room ON room.id = room_id JOIN \"user\" ON \"user\".id = user_id JOIN role ON role.id = \"user\".role_id";

    @Override
    protected String tableName() {
        return "reservation";
    }

    @Override
    protected PreparedStatement prepareSaveStatement(Connection connection, Reservation reservation) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO reservation(room_id, user_id) " +
                        "VALUES(?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setInt(1, reservation.getRoom().getId());
        preparedStatement.setInt(2, reservation.getUser().getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareUpdateStatement(Connection connection, Reservation reservation) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE reservation SET " +
                        "room_id = ?, user_id = ? WHERE id = ?"
        );
        preparedStatement.setInt(1, reservation.getRoom().getId());
        preparedStatement.setInt(2, reservation.getUser().getId());
        preparedStatement.setInt(3, reservation.getId());
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
    protected Reservation processResultRow(ResultSet resultSet) throws SQLException {
        return ReservationField.from(resultSet);
    }

    @Override
    public Reservation getByRoomId(Integer roomId) {
        return getByFields(Map.of(ReservationField.ROOM, roomId)).stream()
                .findFirst()
                .orElse(null);
    }
}
