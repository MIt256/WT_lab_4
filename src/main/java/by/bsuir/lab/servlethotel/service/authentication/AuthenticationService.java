package by.bsuir.lab.servlethotel.service.authentication;

import by.bsuir.lab.servlethotel.service.authentication.exception.AuthenticationException;
import by.bsuir.lab.servlethotel.util.ServiceLocator;
import by.bsuir.lab.servlethotel.config.Configuration;
import by.bsuir.lab.servlethotel.dao.iface.IAuthenticationDao;
import by.bsuir.lab.servlethotel.dao.iface.IRoleDao;
import by.bsuir.lab.servlethotel.dao.iface.IUserDao;
import by.bsuir.lab.servlethotel.entity.security.AuthenticationEntity;
import by.bsuir.lab.servlethotel.entity.security.Role;
import by.bsuir.lab.servlethotel.entity.security.User;
import by.bsuir.lab.servlethotel.entity.security.UserRole;
import by.bsuir.lab.servlethotel.service.iface.IAuthenticationService;
import by.bsuir.lab.servlethotel.util.Errors;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

public class AuthenticationService implements IAuthenticationService {

    private final IAuthenticationDao authenticationDao = ServiceLocator.get(IAuthenticationDao.class);
    private final IUserDao userDao = ServiceLocator.get(IUserDao.class);
    private final IRoleDao roleDao = ServiceLocator.get(IRoleDao.class);

    @Override
    public Authentication login(String username, String password) throws AuthenticationException {
        User user = userDao.getByUsername(username);
        if (user == null) throw new AuthenticationException("No such user: " + username);
        if (!user.getPassword().equals(encodePassword(password)))
            throw new AuthenticationException("Incorrect password");

        return generateAuthentication(username);
    }

    @Override
    public Authentication authorize(Authentication authentication) {
        AuthenticationEntity entity = authenticationDao.getByValue(authentication.toString());
        if (entity == null) return null;

        authenticationDao.delete(entity);
        if (authentication.isExpired()) return null;

        return generateAuthentication(authentication.getUsername());
    }

    @Override
    public Errors register(User user) {
        Errors errors = Errors.empty();
        if(user.getUsername() == null || user.getUsername().isBlank()) errors.push("Blank username.");
        if(user.getPassword() == null || user.getPassword().isBlank()) errors.push("Blank password.");
        if (userDao.getByUsername(user.getUsername()) != null) errors.push("Such user is already exists.");
        if (user.getRole() == null) errors.push("Role isn't assigned.");
        if (errors.size() > 0) return errors;

        user.setPassword(encodePassword(user.getPassword()));
        userDao.save(user);
        return Errors.empty();
    }

    @Override
    public Role getRole(UserRole role) {
        return roleDao.getByRoleName(role);
    }

    private Authentication generateAuthentication(String username) {
        Authentication authentication = Authentication.builder()
                .token(UUID.randomUUID())
                .username(username)
                .expirationDate(new Date(new Date().getTime() + Configuration.TOKEN_MINUTE_LIFETIME * 60 * 1000))
                .build();
        AuthenticationEntity entity = new AuthenticationEntity();
        entity.setValue(authentication.toString());
        authenticationDao.save(entity);
        return authentication;
    }

    private String encodePassword(String password) {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-512").digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot encode password.");
        }
    }
}
