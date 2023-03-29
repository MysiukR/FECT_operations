package ua.com.epam.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor

@Entity
public class Lesson implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lesson_number", nullable = false)
    private Long lessonNumber;

    @Column(name = "day_of_week", nullable = false)
    private Long dayOfWeek;


    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "subject_id")
    private Long subjectId;


}
