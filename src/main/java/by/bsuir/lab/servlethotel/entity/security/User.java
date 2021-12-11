package by.bsuir.lab.servlethotel.entity.security;

import by.bsuir.lab.servlethotel.entity.IntegerId;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends IntegerId {

    public User(Integer id) {
        setId(id);
    }

    private String username;

    private String password;

    private Role role;
}
