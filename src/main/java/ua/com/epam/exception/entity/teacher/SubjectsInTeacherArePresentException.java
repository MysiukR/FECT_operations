package ua.com.epam.exception.entity.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SubjectsInTeacherArePresentException extends RuntimeException {
    private long teacherId;
    private long subjectsCount;
}
