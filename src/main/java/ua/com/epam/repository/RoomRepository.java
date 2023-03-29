package ua.com.epam.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.epam.entity.Room;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByRoomId(long genreId);

    boolean existsByRoomName(String roomName);

    Optional<Room> getOneByRoomId(long roomId);

    @Query(value = "SELECT g FROM Room g")
    List<Room> getAllRooms(PageRequest page);

    @Query(value = "SELECT DISTINCT g FROM Room g JOIN Lesson b ON g.roomId = b.roomId AND b.teacherId = ?1")
    List<Room> getAllRoomsOfTeacherOrdered(long teacherId, Sort sort);

    @Query(value = "SELECT g FROM Room g JOIN Lesson b ON b.roomId = g.roomId AND b.subjectId = ?1")
    Room getRoomOfSubject(long bookId);
}
