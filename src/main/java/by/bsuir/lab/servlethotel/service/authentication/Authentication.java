package by.bsuir.lab.servlethotel.service.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class Authentication {

    private static final String TOKEN_SECTION_SEPARATOR = "?";

    private final UUID token;
    private final String username;
    private final Date expirationDate;

    public Authentication(String template) {
        String decoded = new String(Base64.getDecoder().decode(template), StandardCharsets.UTF_8);
        token = UUID.fromString(decoded.substring(0, decoded.indexOf(TOKEN_SECTION_SEPARATOR)));
        decoded = decoded.substring(decoded.indexOf(TOKEN_SECTION_SEPARATOR) + 1);
        username = decoded.substring(0, decoded.indexOf(TOKEN_SECTION_SEPARATOR));
        decoded = decoded.substring(decoded.indexOf(TOKEN_SECTION_SEPARATOR) + 1);
        expirationDate = new Date(Long.parseLong(decoded));
    }

    public boolean isExpired() {
        return expirationDate.before(new Date());
    }

    public String toString() {
        return Base64.getEncoder()
                .encodeToString(
                        (token + TOKEN_SECTION_SEPARATOR +
                                username + TOKEN_SECTION_SEPARATOR +
                                expirationDate.getTime()
                        ).getBytes(StandardCharsets.UTF_8)
                );
    }
}
