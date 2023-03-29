package ua.com.epam.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.epam.entity.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> getOneBySubjectId(long subjectId);

    boolean existsBySubjectId(long subjectId);

    @Query(value = "SELECT b FROM Subject b")
    List<Subject> getAllSubjects(PageRequest page);


}