package by.bsuir.lab.servlethotel.entity.security;

import by.bsuir.lab.servlethotel.entity.IntegerId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthenticationEntity extends IntegerId {

    private String value;
}
