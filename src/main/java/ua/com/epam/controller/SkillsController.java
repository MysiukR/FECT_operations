package ua.com.epam.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.epam.entity.dto.skills.SkillsDto;
import ua.com.epam.entity.dto.subject.SubjectDto;
import ua.com.epam.entity.dto.teacher.TeacherDto;
import ua.com.epam.service.SkillService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${server.base.url}")
@Api(value = "Skills", description = "Skills endpoints", tags = {"Skills"})
public class SkillsController {
    @Autowired
    private SkillService skillsService;

    @ApiOperation(value = "get all Skills", tags = {"Skills"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of Subject objects",
                    responseContainer = "Set", response = SubjectDto.class),
            @ApiResponse(code = 400, message = "Something wrong...")
    })
    @GetMapping(value = "/skills",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSkills()
    {
        HttpHeaders headers = new HttpHeaders();

        List<SkillsDto> response = skillsService.findAllSkills();
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "search for skills by it name and surname", tags = "Skills")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Array of skills objects",
                    responseContainer = "Set", response = TeacherDto.class),
            @ApiResponse(code = 400, message = "Something wrong...")
    })
    @GetMapping(value = "/skills/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchForTeachers(
            @ApiParam(value = "Searched query. At least 3 symbols exclude spaces in each word.", required = true)
            @RequestParam(name = "query")
            String query) {
        List<SkillsDto> response = skillsService.searchForExistedSkills(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
