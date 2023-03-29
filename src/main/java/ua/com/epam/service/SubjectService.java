package ua.com.epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.epam.entity.Subject;
import ua.com.epam.entity.dto.subject.SubjectDto;
import ua.com.epam.exception.entity.IdMismatchException;
import ua.com.epam.exception.entity.teacher.TeacherNotFoundException;
import ua.com.epam.exception.entity.subject.SubjectNotFoundException;
import ua.com.epam.exception.entity.room.RoomNotFoundException;
import ua.com.epam.exception.entity.search.SearchQueryIsBlankException;
import ua.com.epam.exception.entity.search.SearchQueryIsTooShortException;
import ua.com.epam.repository.*;
import ua.com.epam.service.mapper.DtoToModelMapper;
import ua.com.epam.service.mapper.ModelToDtoMapper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SearchFor searchFor;

    @Autowired
    private ModelToDtoMapper toDtoMapper;

    @Autowired
    private DtoToModelMapper toModelMapper;

    private Sort.Direction resolveDirection(String order) {
        return Sort.Direction.fromString(order);
    }

    private List<SubjectDto> mapToDto(List<Subject> subjects) {
        return subjects.stream()
                .map(toDtoMapper::mapSubjectToSubjectDto)
                .collect(Collectors.toList());
    }

    public SubjectDto findSubject(long subjectId) {
        Subject subject = subjectRepository.getOneBySubjectId(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(subjectId));

        return toDtoMapper.mapSubjectToSubjectDto(subject);
    }

    public List<SubjectDto> findAllSubjects(String sortBy, String order, int page, int size, boolean pageable) {
        if (sortBy.equalsIgnoreCase("square")) {
            return findAllSubjectsSortedBySquare(order, page, size, pageable);
        } else if (sortBy.equalsIgnoreCase("volume")) {
            return findAllSubjectsSortedByVolume(order, page, size, pageable);
        }

        String sortParameter = JsonKeysConformity.getPropNameByJsonKey(sortBy);
        Sort.Direction direction = resolveDirection(order);
        Sort sorter = Sort.by(direction, sortParameter);

        List<Subject> subjects;

        if (!pageable) {
            subjects = subjectRepository.findAll(sorter);
        } else {
            subjects = subjectRepository.getAllSubjects(PageRequest.of(page - 1, size, sorter));
        }

        return mapToDto(subjects);
    }

    private List<SubjectDto> findAllSubjectsSortedBySquare(String order, int page, int size, boolean pageable) {
        Sort.Direction direction = resolveDirection(order);
        Sort sorter = Sort.by(direction, "square");

        List<Subject> subjects;

        if (!pageable) {
            subjects = subjectRepository.findAll(sorter);
        } else {
            subjects = subjectRepository.getAllSubjects(PageRequest.of(page - 1, size, sorter));
        }

        return mapToDto(subjects);
    }

    private List<SubjectDto> findAllSubjectsSortedByVolume(String order, int page, int size, boolean pageable) {
        Sort.Direction direction = resolveDirection(order);
        Sort sorter = Sort.by(direction, "volume");

        List<Subject> subjects;

        if (!pageable) {
            subjects = subjectRepository.findAll(sorter);
        } else {
            subjects = subjectRepository.getAllSubjects(PageRequest.of(page - 1, size, sorter));
        }

        return mapToDto(subjects);
    }

    public List<SubjectDto> findSubjectsInRoom(long roomId, String sortBy, String order, int page, int size, boolean pageable) {
        if (!roomRepository.existsByRoomId(roomId)) {
            throw new RoomNotFoundException(roomId);
        }

        Sort.Direction direction = resolveDirection(order);
        String sortParameter = JsonKeysConformity.getPropNameByJsonKey(sortBy);
        Sort sorter = Sort.by(direction, sortParameter);

        List<Subject> subjects;

        if (!pageable) {
            subjects = subjectRepository.findAll(sorter);
        } else {
            subjects = lessonRepository.getAllSubjectsInRoom(roomId, PageRequest.of(page - 1, size, sorter));
        }

        return mapToDto(subjects);
    }

    public List<SubjectDto> findTeacherSubjects(long teacherId, String sortBy, String order) {
        if (!teacherRepository.existsByTeacherId(teacherId)) {
            throw new TeacherNotFoundException(teacherId);
        }

        Sort.Direction direction = resolveDirection(order);
        String sortParameter = JsonKeysConformity.getPropNameByJsonKey(sortBy);
        Sort sorter = Sort.by(direction, sortParameter);

        return mapToDto(lessonRepository.getAllTeacherSubjectsOrdered(teacherId, sorter));
    }

    public List<SubjectDto> findSubjectsOfTeacherInRoom(long teacherId, long roomId) {
        if (!teacherRepository.existsByTeacherId(teacherId)) {
            throw new TeacherNotFoundException(teacherId);
        }

        if (!roomRepository.existsByRoomId(roomId)) {
            throw new RoomNotFoundException(roomId);
        }

        return mapToDto(lessonRepository.getAllTeacherSubjectsInRoom(teacherId, roomId));
    }

    public List<SubjectDto> searchForExistedSubjects(String searchQuery) {
        List<Subject> result = new ArrayList<>();

        searchQuery = searchQuery.trim();
        if (searchQuery.isEmpty()) {
            throw new SearchQueryIsBlankException();
        } else if (searchQuery.length() <= 4) {
            throw new SearchQueryIsTooShortException(searchQuery, 5);
        }

        List<String> splitQuery = Arrays.asList(searchQuery.split(" "));
        List<Subject> searched = searchFor.subjects(searchQuery, splitQuery);

        if (searched.isEmpty()) {
            return new ArrayList<>();
        }

        for (int i = splitQuery.size(); i > 0; i--) {
            String partial = splitQuery.stream()
                    .limit(i)
                    .collect(Collectors.joining(" "));

            List<Subject> filtered = searched.stream()
                    .filter(b -> b.getSubjectName().toLowerCase().startsWith(partial.toLowerCase()))
                    .sorted(Comparator.comparing(Subject::getSubjectName)).collect(Collectors.toList());

            result.addAll(filtered);

            if (result.size() >= 5) {
                return mapToDto(IntStream.range(0, 5)
                        .mapToObj(result::get)
                        .collect(Collectors.toList()));
            }

            searched.removeAll(filtered);
        }

        result.addAll(searched);

        return mapToDto(result.stream()
                .limit(5)
                .collect(Collectors.toList()));
    }



    public SubjectDto updateExistedSubject(long subjectId, SubjectDto subjectDto) {
        Optional<Subject> opt = subjectRepository.getOneBySubjectId(subjectId);

        if (!opt.isPresent()) {
            throw new SubjectNotFoundException(subjectId);
        }

        if (subjectId != subjectDto.getSubjectId()) {
            throw new IdMismatchException();
        }

        Subject proxy = opt.get();

        proxy.setSubjectName(subjectDto.getSubjectName());
        proxy.setSubjectLang(subjectDto.getSubjectLanguage());
        proxy.setDescription(subjectDto.getSubjectDescription());

        Subject updated = subjectRepository.save(proxy);

        return toDtoMapper.mapSubjectToSubjectDto(updated);
    }

    public void deleteExistedSubject(long subjectId) {
        Subject toDelete = subjectRepository.getOneBySubjectId(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(subjectId));

        subjectRepository.delete(toDelete);
    }
}
