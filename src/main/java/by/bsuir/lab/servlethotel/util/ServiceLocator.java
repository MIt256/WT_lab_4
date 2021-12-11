package by.bsuir.lab.servlethotel.util;

import by.bsuir.lab.servlethotel.dao.iface.*;
import by.bsuir.lab.servlethotel.dao.user.UserDao;
import by.bsuir.lab.servlethotel.service.reservation.RoomService;
import by.bsuir.lab.servlethotel.dao.authentication.AuthenticationDao;
import by.bsuir.lab.servlethotel.dao.reservation.ReservationDao;
import by.bsuir.lab.servlethotel.dao.role.RoleDao;
import by.bsuir.lab.servlethotel.dao.room.RoomDao;
import by.bsuir.lab.servlethotel.service.authentication.AuthenticationService;
import by.bsuir.lab.servlethotel.service.iface.IAuthenticationService;
import by.bsuir.lab.servlethotel.service.iface.IRoomService;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static final Map<Class<?>, Object> services = new HashMap<>();

    static {
        register(IRoleDao.class, new RoleDao());
        register(IUserDao.class, new UserDao());
        register(IRoomDao.class, new RoomDao());
        register(IReservationDao.class, new ReservationDao());
        register(IRoomService.class, new RoomService());
        register(IAuthenticationDao.class, new AuthenticationDao());
        register(IAuthenticationService.class, new AuthenticationService());
    }

    public static <R> void register(Class<R> declarationType, R implementation) {
        if (!declarationType.isAssignableFrom(implementation.getClass()))
            throw new IllegalArgumentException("Implementation type: " +
                    implementation.getClass() +
                    " is not assignable to" +
                    declarationType);
        services.put(declarationType, implementation);
    }

    @SuppressWarnings("unchecked")
    public static <R> R get(Class<R> declarationType) {
        R service = (R) services.get(declarationType);
        if (service == null) throw new IllegalArgumentException("No such service: " + declarationType);
        return service;
    }
}
