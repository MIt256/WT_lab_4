package by.bsuir.lab.servlethotel.dao.iface;

import by.bsuir.lab.servlethotel.dao.IDao;
import by.bsuir.lab.servlethotel.entity.security.Role;
import by.bsuir.lab.servlethotel.entity.security.UserRole;

public interface IRoleDao extends IDao<Role, Integer> {
    Role getByRoleName(UserRole roleName);
}
