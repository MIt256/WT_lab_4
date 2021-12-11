package by.bsuir.lab.servlethotel.entity.hotel;

import by.bsuir.lab.servlethotel.entity.IntegerId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Room extends IntegerId {

    public Room(Integer id) {
        setId(id);
    }

    private Integer label;

    private Boolean hasKitchen;

    private Boolean hasBath;

    private Reservation reservation;
}
