package ua.com.epam.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.epam.entity.Lesson;
import ua.com.epam.entity.Subject;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

//    @Query(value = "SELECT a FROM Lesson a JOIN Teacher b ON b.teacherId = a.teacherId AND b.subjectId = ?1")
//    Lesson getLessonOfSubject(long subjectId);

    //SELECT l FROM LESSON l JOIN SUBJECT s ON l.SUBJECT_ID = s.SUBJECT_ID JOIN ROOM m ON l.ROOM_ID = m.ROOM_ID JOIN Teacher t ON l.TEACHER_ID = t.TEACHER_ID

    @Query(value = "SELECT l FROM Lesson l JOIN Subject s ON l.subjectId = s.subjectId")
    List<Lesson> getAllLessons(PageRequest page);

//    @Query(value = "SELECT DISTINCT a FROM Lesson a JOIN Teacher b ON a.teacherId = b.teacherId AND b.roomId = ?1")
//    List<Lesson> getAllLessonsInRoom(long roomId);


    @Query(value = "SELECT COUNT(b) FROM Lesson b WHERE b.roomId=?1")
    Long getAllSubjectsInRoomCount(long roomId);

    @Query(value = "SELECT COUNT(b) FROM Lesson b WHERE b.teacherId=?1")
    Long getAllSubjectsOfTeacherCount(long teacherId);

    @Query(value = "SELECT b FROM Lesson b WHERE b.roomId=?1")
    List<Subject> getAllSubjectsInRoom(long roomId, PageRequest page);

    @Query(value = "SELECT b FROM Lesson b WHERE b.teacherId=?1")
    List<Subject> getAllTeacherSubjectsOrdered(long teacherId, Sort sort);

    @Query(value = "SELECT b FROM Lesson b WHERE b.teacherId=?1 AND b.roomId=?2")
    List<Subject> getAllTeacherSubjectsInRoom(long teacherId, long roomId);
}
