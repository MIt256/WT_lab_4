package by.bsuir.lab.servlethotel.entity.security;

import by.bsuir.lab.servlethotel.entity.IntegerId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Role extends IntegerId {

    public Role(Integer id) {
        setId(id);
    }

    private UserRole role;
}
