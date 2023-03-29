package ua.com.epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.epam.entity.DetailedLesson;
import ua.com.epam.entity.Lesson;
import ua.com.epam.entity.dto.item.DetailedLessonDto;
import ua.com.epam.entity.dto.lesson.LessonDto;
import ua.com.epam.entity.dto.subject.SubjectDto;
import ua.com.epam.repository.ItemRepository;
import ua.com.epam.repository.JsonKeysConformity;
import ua.com.epam.repository.LessonRepository;
import ua.com.epam.service.mapper.ModelToDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {
  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ModelToDtoMapper toDtoMapper;
  private Sort.Direction resolveDirection(String order) {
    return Sort.Direction.fromString(order);
  }

  public List<LessonDto> findAllLesson(String sortBy, String order, int page, int size, boolean pageable) {
    Sort.Direction direction = resolveDirection(order);
    String sortParam = JsonKeysConformity.getPropNameByJsonKey(sortBy);
    Sort sorter = Sort.by(direction, sortParam);

    List<Lesson> lessons;

    if (!pageable) {
      lessons = lessonRepository.findAll(sorter);
    } else {
      lessons = lessonRepository.getAllLessons(PageRequest.of(page - 1, size, sorter));
    }

    return mapToDto(lessons);
  }

  public List<DetailedLessonDto> findAllDetailedLesson() {
    List<DetailedLesson> detailedLesson;
    detailedLesson = itemRepository.getAllDetailedLessons();
    return mapItemToDto(detailedLesson);
  }

  public SubjectDto addNewSubject(long teacherId, long roomId, SubjectDto newSubject) {
//    if (!teacherRepository.existsByTeacherId(teacherId)) {
//      throw new TeacherNotFoundException(teacherId);
//    }
//
//    if (!roomRepository.existsByRoomId(roomId)) {
//      throw new RoomNotFoundException(roomId);
//    }
//
//    if (subjectRepository.existsBySubjectId(newSubject.getSubjectId())) {
//      throw new SubjectAlreadyExistsException();
//    }

//    Subject toPost = toModelMapper.mapSubjectDtoToSubject(newSubject);
//    toPost.setTeacherId(teacherId);
//    toPost.setRoomId(roomId);
//
//    Subject response = subjectRepository.save(toPost);
//
//    return toDtoMapper.mapSubjectToSubjectDto(response);
    return null;
  }

  private List<LessonDto> mapToDto(List<Lesson> teachers) {
    return teachers.stream()
            .map(toDtoMapper::mapLessonToLessonDto)
            .collect(Collectors.toList());
  }

  private List<DetailedLessonDto> mapItemToDto(List<DetailedLesson> teachers) {
    return teachers.stream()
            .map(toDtoMapper::mapItemToItemDto)
            .collect(Collectors.toList());
  }

}
