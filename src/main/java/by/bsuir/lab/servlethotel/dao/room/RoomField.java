package by.bsuir.lab.servlethotel.dao.room;

import by.bsuir.lab.servlethotel.dao.reservation.ReservationField;
import by.bsuir.lab.servlethotel.dao.user.UserField;
import by.bsuir.lab.servlethotel.dao.IEntityField;
import by.bsuir.lab.servlethotel.entity.hotel.Reservation;
import by.bsuir.lab.servlethotel.entity.hotel.Room;
import by.bsuir.lab.servlethotel.entity.security.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
@Getter
public enum RoomField implements IEntityField<Room> {
    LABEL("label", Preparer.INTEGER_PREPARER),
    HAS_KITCHEN("has_kitchen", Preparer.BOOLEAN_PREPARER),
    HAS_BATH("has_bath", Preparer.BOOLEAN_PREPARER),
    RESERVATION("reservation_id", Preparer.INTEGER_PREPARER);

    private final String field;
    private final Preparer preparer;

    public static Room from(ResultSet resultSet) throws SQLException {
        Room room = new Room();
        room.setId(resultSet.getInt("id"));
        return injectValues(room, resultSet);
    }

    public static Room injectValues(Room room, ResultSet resultSet) throws SQLException {
        room.setLabel(resultSet.getInt(LABEL.getField()));
        room.setHasKitchen(resultSet.getBoolean(HAS_KITCHEN.getField()));
        room.setHasBath(resultSet.getBoolean(HAS_BATH.getField()));

        Reservation reservation = new Reservation();
        reservation.setId(resultSet.getInt(RESERVATION.getField()));
        if(!resultSet.wasNull()) {
            reservation.setRoom(room);

            User user = new User();
            user.setId(resultSet.getInt(ReservationField.USER.getField()));
            reservation.setUser(UserField.injectValues(user, resultSet));
            room.setReservation(reservation);
        }
        return room;
    }

    @Override
    public void setValue(PreparedStatement statement, Object value, int index) throws SQLException {
        preparer.prepare(statement, value, index);
    }
}
