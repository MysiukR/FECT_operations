package ua.com.epam.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.epam.entity.dto.room.RoomDto;
import ua.com.epam.exception.entity.NoSuchJsonKeyException;
import ua.com.epam.exception.entity.type.InvalidOrderTypeException;
import ua.com.epam.exception.entity.type.InvalidPageValueException;
import ua.com.epam.exception.entity.type.InvalidSizeValueException;
import ua.com.epam.repository.JsonKeysConformity;
import ua.com.epam.service.RoomService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${server.base.url}")
@Api(value = "Room", description = "Room endpoints", tags = {"Room"})
public class RoomController {

    @Autowired
    private RoomService roomService;

    private void checkOrdering(String orderType) {
        if (!orderType.equals("asc") && !orderType.equals("desc")) {
            throw new InvalidOrderTypeException(orderType);
        }
    }

    private void checkSortByKeyInGroup(String sortBy) {
        if (!JsonKeysConformity.ifJsonKeyExistsInGroup(sortBy, JsonKeysConformity.Group.ROOM)) {
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

    @ApiOperation(value = "get Room object by 'roomId'", tags = {"Room"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Special Room object in JSON", response = RoomDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Room not found")
    })
    @GetMapping(value = "/room/{roomId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRoom(
            @ApiParam(required = true, value = "existed Room ID")
            @PathVariable
                    Long roomId) {
        RoomDto response = roomService.findRoom(roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "get Room of special Book", tags = {"Room"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Room object of special Book in JSON", response = RoomDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @GetMapping(value = "subject/{subjectId}/room",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookRoom(
            @ApiParam(required = true, value = "existed Book ID")
            @PathVariable
                    Long bookId) {
        RoomDto response = roomService.findRoomOfSubject(bookId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "get all Rooms", tags = {"Room"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of Room objects",
                    responseContainer = "Set", response = RoomDto.class),
            @ApiResponse(code = 400, message = "Something wrong...")
    })
    @GetMapping(value = "/rooms",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRooms(
            @ApiParam(value = "paginate response")
            @RequestParam(name = "pagination", defaultValue = "true")
                    Boolean pagination,

            @ApiParam(value = "custom sort parameter")
            @RequestParam(name = "sortBy", defaultValue = "roomId")
                    String sortBy,

            @ApiParam(allowableValues = "asc,desc", value = "sorting order")
            @RequestParam(name = "orderType", defaultValue = "asc")
                    String orderType,

            @ApiParam(value = "page number")
            @RequestParam(name = "page", defaultValue = "1")
                    Integer page,

            @ApiParam(value = "count of objects per one page")
            @RequestParam(name = "size", defaultValue = "10")
                    Integer size) {
        checkSortByKeyInGroup(sortBy);
        checkOrdering(orderType);
        checkPaginateParams(page, size);

        List<RoomDto> response = roomService.findAllRooms(sortBy, orderType, page, size, pagination);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "search for room by it room name", tags = "Room")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of Room objects",
                    responseContainer = "Set", response = RoomDto.class),
            @ApiResponse(code = 400, message = "Something wrong...")
    })
    @GetMapping(value = "/rooms/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchForExistedRooms(
            @ApiParam(value = "Searched query. At least 3 symbols exclude spaces in each word.", required = true)
            @RequestParam(name = "query")
                    String query) {
        List<RoomDto> response = roomService.searchForExistedRooms(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "get all Rooms of special Author", tags = {"Room"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of Room objects",
                    responseContainer = "Set", response = RoomDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Author not found")
    })
    @GetMapping(value = "/teacher/{teacherId}/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAuthorRooms(
            @ApiParam(required = true, value = "existed Author ID")
            @PathVariable
                    Long teacherId,

            @ApiParam(value = "custom sort parameter")
            @RequestParam(name = "sortBy", defaultValue = "roomId")
                    String sortBy,

            @ApiParam(allowableValues = "asc,desc", value = "sorting order")
            @RequestParam(name = "orderType", defaultValue = "asc")
                    String orderType) {
        checkSortByKeyInGroup(sortBy);
        checkOrdering(orderType);

        List<RoomDto> response = roomService.findAllRoomsOfTeacher(teacherId, sortBy, orderType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "create new Room", tags = {"Room"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "newly created Room", response = RoomDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 409, message = "Room with such id already exists")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/room",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewRoom(
            @ApiParam(required = true, value = "Room to add", name = "Room object")
            @RequestBody @Valid
                    RoomDto postRoom) {
        RoomDto response = roomService.addNewRoom(postRoom);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "update existed Room", tags = {"Room"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "updated Room object", response = RoomDto.class),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Room to update not found")
    })
    @PutMapping(value = "/room/{roomId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRoom(
            @ApiParam(required = true, value = "existed Room ID")
            @PathVariable
                    Long roomId,

            @ApiParam(required = true, value = "Room to update", name = "Room object")
            @RequestBody @Valid
                    RoomDto updateRoom) {
        RoomDto response = roomService.updateExistedRoom(roomId, updateRoom);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "delete existed Room", tags = {"Room"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Room deleted successfully"),
            @ApiResponse(code = 400, message = "Something wrong..."),
            @ApiResponse(code = 404, message = "Room to delete not found")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/room/{roomId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteRoom(
            @ApiParam(required = true, value = "existed Room ID")
            @PathVariable
                    Long roomId,

            @ApiParam(value = "if false and Author has related Books, it will produce fault")
            @RequestParam(name = "forcibly", defaultValue = "false")
                    Boolean forcibly) {
        roomService.deleteExistedRoom(roomId, forcibly);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
