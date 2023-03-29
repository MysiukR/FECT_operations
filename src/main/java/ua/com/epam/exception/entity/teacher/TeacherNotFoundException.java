package ua.com.epam.exception.entity.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeacherNotFoundException extends RuntimeException {
    private long teacherId;
}
