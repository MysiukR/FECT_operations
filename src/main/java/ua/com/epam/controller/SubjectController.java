package ua.com.epam.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.epam.entity.dto.subject.SubjectDto;
import ua.com.epam.exception.entity.NoSuchJsonKeyException;
import ua.com.epam.exception.entity.type.InvalidOrderTypeException;
import ua.com.epam.exception.entity.type.InvalidPageValueException;
import ua.com.epam.exception.entity.type.InvalidSizeValueException;
import ua.com.epam.repository.JsonKeysConformity;
import ua.com.epam.service.SubjectService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${server.base.url}")
@Api(value = "Subject", description = "Subject endpoints", tags = {"Subject"})
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    private void checkOrdering(String orderType) {
        if (!orderType.equals("asc") && !orderType.equals("desc")) {
            throw new InvalidOrderTypeException(orderType);
        }
    }

    private void checkSortByKeyInGroup(String sortBy) {
        if (!JsonKeysConformity.ifJsonKeyExistsInGroup(sortBy, JsonKeysConformity.Group.SUBJECT)
                && !sortBy.equalsIgnoreCase("volume")
                && !sortBy.equalsIgnoreCase("square")) {
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

    @ApiOperation(value = "get Subject object by 'subjectId'", tags = {"Subject"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Special Subject object in JSON", response = SubjectDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Subject not found")
    })
    @GetMapping(value = "/subject/{subjectId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSubject(
            @ApiParam(required = true, value = "existed Subject ID")
            @PathVariable
                    Long subjectId) {
        SubjectDto response = subjectService.findSubject(subjectId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "get all Subjects", tags = {"Subject"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of Subject objects",
                    responseContainer = "Set", response = SubjectDto.class),
            @ApiResponse(code = 400, message = "Something wrong...")
    })
    @GetMapping(value = "/subjects",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSubjects(
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
        checkSortByKeyInGroup(sortBy);
        checkOrdering(orderType);
        checkPaginateParams(page, size);

        List<SubjectDto> response = subjectService.findAllSubjects(sortBy, orderType, page, size, pagination);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "get all Subjects of special Room", tags = {"Subject"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of Subject objects",
                    responseContainer = "Set", response = SubjectDto.class),
            @ApiResponse(code = 400, message = "Something wrong...")
    })
    @GetMapping(value = "/room/{roomId}/subjects",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSubjectsInRoom(
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

            @ApiParam(value = "custom sort parameter")
            @RequestParam(name = "sortBy", defaultValue = "subjectId")
                    String sortBy,

            @ApiParam(allowableValues = "asc,desc", value = "sorting order")
            @RequestParam(name = "orderType", defaultValue = "asc")
                    String orderType) {
        checkSortByKeyInGroup(sortBy);
        checkOrdering(orderType);
        checkPaginateParams(page, size);

        List<SubjectDto> response = subjectService.findSubjectsInRoom(roomId, sortBy, orderType, page, size, pagination);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "get all Subjects of special Teacher", tags = {"Subject"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of Subject objects",
                    responseContainer = "Set", response = SubjectDto.class),
            @ApiResponse(code = 400, message = "Something wrong...")
    })
    @GetMapping(value = "/teacher/{teacherId}/subjects",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTeacherSubjects(
            @ApiParam(required = true, value = "existed Teacher ID")
            @PathVariable
                    Long teacherId,

            @ApiParam(value = "custom sort parameter")
            @RequestParam(name = "sortBy", defaultValue = "subjectId")
                    String sortBy,

            @ApiParam(allowableValues = "asc,desc", value = "sorting order")
            @RequestParam(name = "orderType", defaultValue = "asc")
                    String orderType) {
        checkSortByKeyInGroup(sortBy);
        checkOrdering(orderType);

        List<SubjectDto> response = subjectService.findTeacherSubjects(teacherId, sortBy, orderType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "get all Subjects of special Teacher in special Room", tags = {"Subject"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of Subject objects",
                    responseContainer = "Set", response = SubjectDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Teacher or Room not found")
    })
    @GetMapping(value = "/teacher/{teacherId}/room/{roomId}/subjects",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTeacherSubjectsInRoom(
            @ApiParam(required = true, value = "existed Teacher ID")
            @PathVariable
                    Long teacherId,

            @ApiParam(required = true, value = "existed Room ID")
            @PathVariable
                    Long roomId) {
        List<SubjectDto> response = subjectService.findSubjectsOfTeacherInRoom(teacherId, roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "search for subjects by subject name, return first 5 the most relevant results", tags = {"Subject"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of Subject objects",
                    responseContainer = "Set", response = SubjectDto.class),
            @ApiResponse(code = 400, message = "Something wrong...")
    })
    @GetMapping(value = "/subjects/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchForExistedSubjects(
            @ApiParam(value = "Searched query. At least 5 symbols exclude spaces in each word.", required = true)
            @RequestParam(name = "q")
                    String query) {
        List<SubjectDto> response = subjectService.searchForExistedSubjects(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @ApiOperation(value = "update existed Subject", tags = {"Subject"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "updated Subject object", response = SubjectDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Subject to update not found")
    })
    @PutMapping(value = "/subject/{subjectId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSubject(
            @ApiParam(required = true, value = "existed Subject ID")
            @PathVariable
                    Long subjectId,

            @ApiParam(required = true, value = "Subject to update", name = "Subject object")
            @RequestBody @Valid
            SubjectDto updatedSubject) {
        SubjectDto response = subjectService.updateExistedSubject(subjectId, updatedSubject);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "delete existed Subject", tags = {"Subject"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Subject deleted successfully"),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Subject to delete not found")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/subject/{subjectId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteSubject(
            @ApiParam(required = true, value = "existed Subject ID")
            @PathVariable
                    Long subjectId) {
        subjectService.deleteExistedSubject(subjectId);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}