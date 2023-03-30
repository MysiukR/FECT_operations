package ua.com.epam.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.com.epam.entity.DetailedLesson;
import ua.com.epam.entity.dto.item.DetailedLessonDto;
import ua.com.epam.entity.dto.lesson.LessonDto;
import ua.com.epam.entity.dto.subject.SubjectDto;
import ua.com.epam.service.LessonService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${server.base.url}")
@Api(value = "Lesson", description = "Lesson endpoints", tags = {"Lesson"})
public class LessonController {

  @Autowired
  private LessonService lessonService;

  @ApiOperation(value = "get all Lessons", tags = {"Lesson"})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Array of Subject objects",
                  responseContainer = "Set", response = SubjectDto.class),
          @ApiResponse(code = 400, message = "Something wrong...")
  })
  @GetMapping(value = "/lesson",
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAllLessons(
          @ApiParam(value = "paginate response")
          @RequestParam(name = "pagination", defaultValue = "true")
          Boolean pagination,

          @ApiParam(value = "page number")
          @RequestParam(name = "page", defaultValue = "1")
          Integer page,

          @ApiParam(value = "count of objects per one page")
          @RequestParam(name = "size", defaultValue = "10")
          Integer size,

          @ApiParam(value = "custom sort parameter\ncan also assume 'square' and 'volume' parameters")
          @RequestParam(name = "sortBy", defaultValue = "subjectId")
          String sortBy,

          @ApiParam(allowableValues = "asc,desc", value = "sorting order")
          @RequestParam(name = "orderType", defaultValue = "asc")
          String orderType) {

    List<LessonDto> response = lessonService.findAllLesson(sortBy, orderType, page, size, pagination);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @ApiOperation(value = "get all detailed Lessons", tags = {"Lesson"})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Array of Subject objects",
                  responseContainer = "Set", response = DetailedLesson.class),
          @ApiResponse(code = 400, message = "Something wrong...")
  })
  @GetMapping(value = "/items",
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAllDetailedLessons()
  {
    HttpHeaders headers = new HttpHeaders();

    List<DetailedLessonDto> response = lessonService.findAllDetailedLesson();
    return new ResponseEntity<>(response, headers, HttpStatus.OK);
  }

  @ApiOperation(value = "create new Lesson", tags = {"Lesson"})
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "newly created Lesson", response = SubjectDto.class),
          @ApiResponse(code = 400, message = "Something wrong..."),
          @ApiResponse(code = 409, message = "Lesson with such id already exists")
  })

  @ResponseStatus(value = HttpStatus.CREATED)
  @PostMapping(value = "/lesson/{teacherId}/{roomId}",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addNewSubject(
          @ApiParam(required = true, value = "existed Teacher ID")
          @PathVariable
          Long teacherId,

          @ApiParam(required = true, value = "existed Room ID")
          @PathVariable
          Long roomId,

          @ApiParam(required = true, value = "Subject to add", name = "Subject object")
          @RequestBody @Valid
          SubjectDto newSubject) {
    SubjectDto response = lessonService.addNewSubject(teacherId, roomId, newSubject);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
