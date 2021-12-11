package by.bsuir.lab.servlethotel.dao.iface;

import by.bsuir.lab.servlethotel.dao.IDao;
import by.bsuir.lab.servlethotel.entity.hotel.Reservation;

public interface IReservationDao extends IDao<Reservation, Integer> {

    Reservation getByRoomId(Integer roomId);
}
