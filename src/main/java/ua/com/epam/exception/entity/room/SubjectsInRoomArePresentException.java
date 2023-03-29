package ua.com.epam.exception.entity.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectsInRoomArePresentException extends RuntimeException {
    private long roomId;
    private long subjectsCount;
}