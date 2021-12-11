import by.bsuir.lab.servlethotel.dao.room.RoomDao;
import by.bsuir.lab.servlethotel.entity.hotel.Room;
import org.junit.Assert;
import org.junit.Test;

public class RoomTests {

    private final RoomDao roomDao = new RoomDao();

    @Test
    public void save() {
        Room room = new Room();
        room.setLabel(123);
        room.setHasKitchen(false);
        room.setHasBath(true);
        roomDao.save(room);
        Assert.assertNotNull(room.getId());
    }
}
