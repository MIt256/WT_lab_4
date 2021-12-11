package by.bsuir.lab.servlethotel.dao.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

@Setter
@RequiredArgsConstructor
public class ProxyConnection implements InvocationHandler {

    private Connection proxied;
    private Connection connection;
    private final ConnectionPool connectionPool;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("close")) {
            connectionPool.freeConnection(proxied);
            return null;
        }
        return method.invoke(connection, args);
    }
}
