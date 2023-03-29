package ua.com.epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.epam.entity.Room;
import ua.com.epam.entity.dto.room.RoomDto;
import ua.com.epam.exception.entity.IdMismatchException;
import ua.com.epam.exception.entity.teacher.TeacherNotFoundException;
import ua.com.epam.exception.entity.subject.SubjectNotFoundException;
import ua.com.epam.exception.entity.room.RoomAlreadyExistsException;
import ua.com.epam.exception.entity.room.RoomNameAlreadyExistsException;
import ua.com.epam.exception.entity.room.RoomNotFoundException;
import ua.com.epam.exception.entity.room.SubjectsInRoomArePresentException;
import ua.com.epam.exception.entity.search.SearchQueryIsBlankException;
import ua.com.epam.exception.entity.search.SearchQueryIsTooShortException;
import ua.com.epam.repository.*;
import ua.com.epam.service.mapper.DtoToModelMapper;
import ua.com.epam.service.mapper.ModelToDtoMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private SearchFor searchFor;

    @Autowired
    private ModelToDtoMapper toDtoMapper;

    @Autowired
    private DtoToModelMapper toModelMapper;

    private Sort.Direction resolveDirection(String order) {
        return Sort.Direction.fromString(order);
    }

    private List<RoomDto> mapToDto(List<Room> rooms) {
        return rooms.stream()
                .map(toDtoMapper::mapRoomToRoomDto)
                .collect(Collectors.toList());
    }

    public RoomDto findRoom(long roomId) {
        Room toGet = roomRepository.getOneByRoomId(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        return toDtoMapper.mapRoomToRoomDto(toGet);
    }

    public RoomDto findRoomOfSubject(long subjectId) {
        subjectRepository.getOneBySubjectId(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(subjectId));
        Room toGet = roomRepository.getRoomOfSubject(subjectId);

        return toDtoMapper.mapRoomToRoomDto(toGet);
    }

    public List<RoomDto> findAllRooms(String sortBy, String order, int page, int size, boolean pageable) {
        Sort.Direction direction = resolveDirection(order);
        String sortParam = JsonKeysConformity.getPropNameByJsonKey(sortBy);
        Sort sorter = Sort.by(direction, sortParam);

        List<Room> rooms;

        if (!pageable) {
            rooms = roomRepository.findAll(sorter);
        } else {
            rooms = roomRepository.getAllRooms(PageRequest.of(page - 1, size, sorter));
        }

        return mapToDto(rooms);
    }

    public List<RoomDto> searchForExistedRooms(String searchQuery) {
        searchQuery = searchQuery.trim();

        if (searchQuery.isEmpty()) {
            throw new SearchQueryIsBlankException();
        } else if (searchQuery.length() <= 2) {
            throw new SearchQueryIsTooShortException(searchQuery, 3);
        }

        List<String> keywords = Arrays.stream(searchQuery.split(" "))
                .filter(e -> e.length() > 2)
                .collect(Collectors.toList());

        List<Room> searched = searchFor.rooms(searchQuery, keywords);

        return mapToDto(searched);
    }

    public List<RoomDto> findAllRoomsOfTeacher(long teacherId, String sortBy, String order) {
        if (!teacherRepository.existsByTeacherId(teacherId)) {
            throw new TeacherNotFoundException(teacherId);
        }

        Sort.Direction direction = resolveDirection(order);
        String sortParam = JsonKeysConformity.getPropNameByJsonKey(sortBy);
        Sort sorter = Sort.by(direction, sortParam);

        return mapToDto(roomRepository.getAllRoomsOfTeacherOrdered(teacherId, sorter));
    }

    public RoomDto addNewRoom(RoomDto room) {
        if (roomRepository.existsByRoomId(room.getRoomId())) {
            throw new RoomAlreadyExistsException();
        }

        if (roomRepository.existsByRoomName(room.getRoomName())) {
            throw new RoomNameAlreadyExistsException();
        }

        Room toPost = toModelMapper.mapRoomDtoToRoom(room);
        Room response = roomRepository.save(toPost);

        return toDtoMapper.mapRoomToRoomDto(response);
    }

    public RoomDto updateExistedRoom(long roomId, RoomDto room) {
        Optional<Room> opt = roomRepository.getOneByRoomId(roomId);

        if (!opt.isPresent()) {
            throw new RoomNotFoundException(roomId);
        }
        if (roomId != room.getRoomId()) {
            throw new IdMismatchException();
        }

        Room proxy = opt.get();

        proxy.setRoomId(room.getRoomId());
        proxy.setRoomName(room.getRoomName());
        proxy.setDescription(room.getRoomDescription());

        Room updated = roomRepository.save(proxy);
        return toDtoMapper.mapRoomToRoomDto(updated);
    }

    public void deleteExistedRoom(long roomId, boolean forcibly) {
        Room toDelete = roomRepository.getOneByRoomId(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        long subjectsCount = lessonRepository.getAllSubjectsInRoomCount(roomId);

        if (subjectsCount > 0 && !forcibly) {
            throw new SubjectsInRoomArePresentException(roomId, subjectsCount);
        }

        roomRepository.delete(toDelete);
    }
}