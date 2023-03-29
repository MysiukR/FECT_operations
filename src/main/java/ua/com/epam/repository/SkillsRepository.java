package ua.com.epam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.epam.entity.Lesson;
import ua.com.epam.entity.Skills;

import java.util.List;

@Repository
public interface SkillsRepository extends JpaRepository<Lesson, Long> {
    @Query(value = "SELECT c FROM Skills c")
    List<Skills> getAllSkills();
}
