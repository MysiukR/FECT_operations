package ua.com.epam.exception.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubjectNotFoundException extends RuntimeException {
    private long subjectId;
}
