package by.bsuir.lab.servlethotel.dao.iface;

import by.bsuir.lab.servlethotel.entity.security.AuthenticationEntity;
import by.bsuir.lab.servlethotel.dao.IDao;

public interface IAuthenticationDao extends IDao<AuthenticationEntity, Integer> {
    AuthenticationEntity getByValue(String value);
}
