package ua.com.epam.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.epam.entity.Teacher;


import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByTeacherId(long teacherId);

    Optional<Teacher> getOneByTeacherId(long teacherId);

    @Query(value = "SELECT a FROM Teacher a JOIN Lesson b ON b.teacherId = a.teacherId AND b.subjectId = ?1")
    Teacher getTeacherOfSubject(long subjectId);

    @Query(value = "SELECT a FROM Teacher a")
    List<Teacher> getAllTeachers(PageRequest page);

    @Query(value = "SELECT DISTINCT a FROM Teacher a JOIN Lesson b ON a.teacherId = b.teacherId AND b.roomId = ?1")
    List<Teacher> getAllTeachersInRoom(long roomId, PageRequest page);
}
