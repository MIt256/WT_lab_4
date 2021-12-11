package by.bsuir.lab.servlethotel.service.iface;

import by.bsuir.lab.servlethotel.entity.hotel.Room;

import java.util.List;

public interface IRoomService {

    List<Room> getAll();

    void rent(String username, Integer roomId);

    void unrent(String username, Integer roomId);
}
