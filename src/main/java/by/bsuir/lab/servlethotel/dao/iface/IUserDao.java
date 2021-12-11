package by.bsuir.lab.servlethotel.dao.iface;

import by.bsuir.lab.servlethotel.entity.security.User;
import by.bsuir.lab.servlethotel.dao.IDao;

public interface IUserDao extends IDao<User, Integer> {

    User getByUsername(String username);
}
