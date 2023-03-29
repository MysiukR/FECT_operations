package ua.com.epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.epam.entity.Teacher;
import ua.com.epam.entity.dto.teacher.TeacherDto;
import ua.com.epam.exception.entity.IdMismatchException;
import ua.com.epam.exception.entity.teacher.SubjectsInTeacherArePresentException;
import ua.com.epam.exception.entity.teacher.TeacherAlreadyExistsException;
import ua.com.epam.exception.entity.teacher.TeacherNotFoundException;
import ua.com.epam.exception.entity.subject.SubjectNotFoundException;
import ua.com.epam.exception.entity.room.RoomNotFoundException;
import ua.com.epam.exception.entity.search.SearchQueryIsBlankException;
import ua.com.epam.exception.entity.search.SearchQueryIsTooShortException;
import ua.com.epam.repository.*;
import ua.com.epam.service.mapper.DtoToModelMapper;
import ua.com.epam.service.mapper.ModelToDtoMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RoomRepository roomRepository;

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

    private List<TeacherDto> mapToDto(List<Teacher> teachers) {
        return teachers.stream()
                .map(toDtoMapper::mapTeacherToTeacherDto)
                .collect(Collectors.toList());
    }

    public TeacherDto findTeacher(long teacherId) {
        Teacher toGet = teacherRepository.getOneByTeacherId(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(teacherId));

        return toDtoMapper.mapTeacherToTeacherDto(toGet);
    }

    public TeacherDto findTeacherOfSubject(long subjectId) {
        if (!subjectRepository.existsBySubjectId(subjectId)) {
            throw new SubjectNotFoundException(subjectId);
        }

        Teacher toGet = teacherRepository.getTeacherOfSubject(subjectId);

        return toDtoMapper.mapTeacherToTeacherDto(toGet);
    }

    public List<TeacherDto> findAllTeachers(String sortBy, String order, int page, int size, boolean pageable) {
        Sort.Direction direction = resolveDirection(order);
        String sortParam = JsonKeysConformity.getPropNameByJsonKey(sortBy);
        Sort sorter = Sort.by(direction, sortParam);

        List<Teacher> teachers;

        if (!pageable) {
            teachers = teacherRepository.findAll(sorter);
        } else {
            teachers = teacherRepository.getAllTeachers(PageRequest.of(page - 1, size, sorter));
        }

        return mapToDto(teachers);
    }

    public List<TeacherDto> searchForExistedTeachers(String searchQuery) {
        List<Teacher> result = new ArrayList<>();

        searchQuery = searchQuery.trim();

        if (searchQuery.isEmpty()) throw new SearchQueryIsBlankException();
        else if (searchQuery.length() <= 2) throw new SearchQueryIsTooShortException(searchQuery, 3);

        List<String> splitQuery = Arrays.asList(searchQuery.split(" "));
        List<Teacher> searched = searchFor.teachers(searchQuery, splitQuery);

        if (searched.isEmpty()) return new ArrayList<>();

        for (String word : splitQuery) {
            searched.stream()
                    .filter(a -> a.getFirstName().startsWith(word))
                    .forEach(result::add);

            searched.stream()
                    .filter(a -> a.getSecondName().startsWith(word))
                    .forEach(result::add);

            searched.removeAll(result);
        }

        result.addAll(searched);

        return mapToDto(result.stream()
                .limit(5)
                .collect(Collectors.toList()));
    }

    public List<TeacherDto> findAllTeachersInRoom(long roomId, String sortBy, String order, int page, int size, boolean pageable) {
        if (!roomRepository.existsByRoomId(roomId)) {
            throw new RoomNotFoundException(roomId);
        }

        Sort.Direction direction = resolveDirection(order);
        String sortParam = JsonKeysConformity.getPropNameByJsonKey(sortBy);
        Sort sorter = Sort.by(direction, sortParam);

        List<Teacher> teachers;

        if (!pageable) {
            teachers = teacherRepository.findAll(sorter);
        } else {
            teachers = teacherRepository.getAllTeachersInRoom(roomId, PageRequest.of(page - 1, size, sorter));
        }

        return mapToDto(teachers);
    }

    public TeacherDto addNewTeacher(TeacherDto teacher) {
        if (teacherRepository.existsByTeacherId(teacher.getTeacherId())) {
            throw new TeacherAlreadyExistsException();
        }

        Teacher toPost = toModelMapper.mapTeacherDtoToTeacher(teacher);
        Teacher response = teacherRepository.save(toPost);

        return toDtoMapper.mapTeacherToTeacherDto(response);
    }

    public TeacherDto updateExistedTeacher(long teacherId, TeacherDto teacherDto) {
        Optional<Teacher> opt = teacherRepository.getOneByTeacherId(teacherId);

        if (!opt.isPresent()) {
            throw new TeacherNotFoundException(teacherId);
        }

        if (teacherId != teacherDto.getTeacherId()) {
            throw new IdMismatchException();
        }

        Teacher proxy = opt.get();

        proxy.setFirstName(teacherDto.getTeacherName().getFirst());
        proxy.setSecondName(teacherDto.getTeacherName().getSecond());
        proxy.setDescription(teacherDto.getTeacherDescription());
        proxy.setSiteReference(teacherDto.getSiteReference());

        Teacher updated = teacherRepository.save(proxy);

        return toDtoMapper.mapTeacherToTeacherDto(updated);
    }

    public void deleteExistedTeacher(long teacherId, boolean forcibly) {
        Teacher toDelete = teacherRepository.getOneByTeacherId(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(teacherId));

        long subjectsCount = lessonRepository.getAllSubjectsOfTeacherCount(teacherId);

        if (subjectsCount > 0 && !forcibly) {
            throw new SubjectsInTeacherArePresentException(teacherId, subjectsCount);
        }

        teacherRepository.delete(toDelete);
    }
}