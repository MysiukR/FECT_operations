package ua.com.epam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.epam.entity.DetailedLesson;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<DetailedLesson, Long> {

    //Attention native query we should use the same as in mysql l.ROOM_ID = m.ROOM_ID
    @Query(nativeQuery = true, value = "SELECT l.id, l.lesson_number, l.day_of_week, t.second_name, r.room_name, s.subject_name" +
            " FROM Lesson l JOIN Subject s ON l.subject_id = s.subject_id" +
            " JOIN Teacher t ON l.teacher_id = t.teacher_id " +
            "JOIN Room r ON l.room_id = r.room_id")
    List<DetailedLesson> getAllDetailedLessons();

    @Query( value = "SELECT l FROM Lesson l JOIN Subject s ON l.subjectId = s.subjectId" +
            " JOIN Teacher t ON l.teacherId = t.teacherId " +
            "JOIN Room r ON l.roomId = r.roomId")
    List<DetailedLesson> getAllDetailedLessonsWithNative();

}
