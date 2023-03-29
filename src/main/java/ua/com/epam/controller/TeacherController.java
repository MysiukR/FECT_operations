package ua.com.epam.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.epam.entity.dto.teacher.TeacherDto;
import ua.com.epam.exception.entity.NoSuchJsonKeyException;
import ua.com.epam.exception.entity.type.InvalidOrderTypeException;
import ua.com.epam.exception.entity.type.InvalidPageValueException;
import ua.com.epam.exception.entity.type.InvalidSizeValueException;
import ua.com.epam.repository.JsonKeysConformity;
import ua.com.epam.service.TeacherService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${server.base.url}")
@Api(value = "Teacher", description = "Teacher endpoints", tags = {"Teacher"})
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    private void checkOrdering(String orderType) {
        if (!orderType.equals("asc") && !orderType.equals("desc")) {
            throw new InvalidOrderTypeException(orderType);
        }
    }

    private void checkSortByKeyInGroup(String sortBy) {
        if (!JsonKeysConformity.ifJsonKeyExistsInGroup(sortBy, JsonKeysConformity.Group.TEACHER)) {
            throw new NoSuchJsonKeyException(sortBy);
        }
    }

    private void checkPaginateParams(int page, int size) {
        if (page <= 0) {
            throw new InvalidPageValueException();
        }
        if (size <= 0) {
            throw new InvalidSizeValueException();
        }
    }

    @ApiOperation(value = "get Teacher object by 'teacherId'", tags = {"Teacher"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Special Teacher object in JSON", response = TeacherDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Teacher not found")
    })
    @GetMapping(value = "/teacher/{teacherId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTeacher(
            @ApiParam(required = true, value = "existed Teacher ID")
            @PathVariable
                    Long teacherId) {
        TeacherDto response = teacherService.findTeacher(teacherId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "get Teacher of special Subject", tags = {"Teacher"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Teacher object of special Subject in JSON", response = TeacherDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Subject not found")
    })
    @GetMapping(value = "/subject/{subjectId}/teacher",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTeacherOfSubject(
            @ApiParam(required = true, value = "existed Subject ID")
            @PathVariable
                    Long subjectId) {
      TeacherDto response = teacherService.findTeacherOfSubject(subjectId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "get all Teachers", tags = {"Teacher"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of Teacher objects",
                    responseContainer = "Set", response = TeacherDto.class),
            @ApiResponse(code = 400, message = "Something wrong...")
    })
    @GetMapping(value = "/teachers",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTeachers(
            @ApiParam(value = "paginate response")
            @RequestParam(name = "pagination", defaultValue = "true")
                    Boolean pagination,

            @ApiParam(value = "page number")
            @RequestParam(name = "page", defaultValue = "1")
                    Integer page,

            @ApiParam(value = "count of objects per one page")
            @RequestParam(name = "size", defaultValue = "10")
                    Integer size,

            @ApiParam(value = "custom sort parameter")
            @RequestParam(name = "sortBy", defaultValue = "teacherId")
                    String sortBy,

            @ApiParam(allowableValues = "asc,desc", value = "sorting order")
            @RequestParam(name = "orderType", defaultValue = "asc")
                    String orderType) {
        checkSortByKeyInGroup(sortBy);
        checkOrdering(orderType);
        checkPaginateParams(page, size);

        List<TeacherDto> response = teacherService.findAllTeachers(sortBy, orderType, page, size, pagination);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "search for teacher by it name and surname", tags = "Teacher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of Teachers objects",
                    responseContainer = "Set", response = TeacherDto.class),
            @ApiResponse(code = 400, message = "Something wrong...")
    })
    @GetMapping(value = "/teachers/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchForTeachers(
            @ApiParam(value = "Searched query. At least 3 symbols exclude spaces in each word.", required = true)
            @RequestParam(name = "query")
                    String query) {
        List<TeacherDto> response = teacherService.searchForExistedTeachers(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "get all Teachers in special Room", tags = {"Teacher"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of Teachers objects",
                    responseContainer = "Set", response = TeacherDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Room not found")
    })
    @GetMapping(value = "/room/{roomId}/teachers",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTeachersOfRoom(
            @ApiParam(required = true, value = "existed Room ID")
            @PathVariable
                    Long roomId,

            @ApiParam(value = "paginate response")
            @RequestParam(name = "pagination", defaultValue = "true")
                    Boolean pagination,

            @ApiParam(value = "page number")
            @RequestParam(name = "page", defaultValue = "1")
                    Integer page,

            @ApiParam(value = "count of objects per one page")
            @RequestParam(name = "size", defaultValue = "10")
                    Integer size,

            @ApiParam(value = "custom sort parameter", defaultValue = "teacherId")
            @RequestParam(name = "sortBy", defaultValue = "teacherId")
                    String sortBy,

            @ApiParam(allowableValues = "asc,desc", value = "sorting order")
            @RequestParam(name = "orderType", defaultValue = "asc")
                    String orderType) {
        checkSortByKeyInGroup(sortBy);
        checkOrdering(orderType);
        checkPaginateParams(page, size);

        List<TeacherDto> response = teacherService.findAllTeachersInRoom(roomId, sortBy, orderType, page, size, pagination);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "create new Teacher", tags = {"Teacher"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "newly created Teacher", response = TeacherDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 409, message = "Teacher with such id already exists")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/teacher",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewTeacher(
            @ApiParam(required = true, value = "Teacher to add", name = "Teacher object")
            @RequestBody @Valid
            TeacherDto postTeacher) {
      TeacherDto response = teacherService.addNewTeacher(postTeacher);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "update existed Teacher", tags = {"Teacher"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "updated Teacher object", response = TeacherDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Teacher to update not found")
    })
    @PutMapping(value = "/teacher/{teacherId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTeacher(
            @ApiParam(required = true, value = "existed Teacher ID")
            @PathVariable
                    Long teacherId,

            @ApiParam(required = true, value = "Teacher to update", name = "Teacher object")
            @RequestBody @Valid
                    TeacherDto updatedTeacher) {
        TeacherDto response = teacherService.updateExistedTeacher(teacherId, updatedTeacher);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "delete existed Teacher", tags = {"Teacher"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Teacher deleted successfully"),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Teacher to delete not found")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/teacher/{teacherId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTeacher(
            @ApiParam(required = true, value = "existed Teacher ID")
            @PathVariable
                    Long teacherId,

            @ApiParam(value = "if false and Teacher has related Subjects, it will produce fault")
            @RequestParam(name = "forcibly", defaultValue = "false")
                    Boolean forcibly) {
        teacherService.deleteExistedTeacher(teacherId, forcibly);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}