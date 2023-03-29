package ua.com.epam.exception.entity.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomNotFoundException extends RuntimeException {
    private long roomId;
}
