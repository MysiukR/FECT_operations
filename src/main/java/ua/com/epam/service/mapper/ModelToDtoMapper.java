package ua.com.epam.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ua.com.epam.entity.DAY_OF_WEEK;
import ua.com.epam.entity.DetailedLesson;
import ua.com.epam.entity.Lesson;
import ua.com.epam.entity.Room;
import ua.com.epam.entity.Skills;
import ua.com.epam.entity.Subject;
import ua.com.epam.entity.Teacher;
import ua.com.epam.entity.dto.item.DetailedLessonDto;
import ua.com.epam.entity.dto.item.PerDayDto;
import ua.com.epam.entity.dto.lesson.LessonDto;
import ua.com.epam.entity.dto.room.RoomDto;
import ua.com.epam.entity.dto.skills.SkillsDto;
import ua.com.epam.entity.dto.subject.SubjectDto;
import ua.com.epam.entity.dto.teacher.TeacherDto;
import ua.com.epam.service.mapper.converter.room.RoomToRoomDto;
import ua.com.epam.service.mapper.converter.subject.SubjectToSubjectDto;
import ua.com.epam.service.mapper.converter.teacher.TeacherToTeacherDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
public class ModelToDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public ModelToDtoMapper() {
        modelMapper.addConverter(new TeacherToTeacherDto());
        modelMapper.addConverter(new RoomToRoomDto());
        modelMapper.addConverter(new SubjectToSubjectDto());
    }

    public TeacherDto mapTeacherToTeacherDto(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDto.class);
    }

    public RoomDto mapRoomToRoomDto(Room room) {
        return modelMapper.map(room, RoomDto.class);
    }

    public SubjectDto mapSubjectToSubjectDto(Subject subject) {
        return modelMapper.map(subject, SubjectDto.class);
    }

    public LessonDto mapLessonToLessonDto(Lesson lesson) {
        return modelMapper.map(lesson, LessonDto.class);
    }
    public SkillsDto mapSkillsToSkillsDto(Skills skills) {
        return modelMapper.map(skills, SkillsDto.class);
    }

    public DetailedLessonDto mapItemToItemDto(Map.Entry <Object, List<DetailedLesson>> item) {
        DetailedLessonDto detailedLessonDto = new DetailedLessonDto();
        detailedLessonDto.setGroupName(String.valueOf(item.getKey()));
        List<PerDayDto> list = new ArrayList<>();
        for(DetailedLesson detailedLesson : item.getValue()) {
            PerDayDto perDayDto = new PerDayDto();
            perDayDto.setLessonId(detailedLesson.getId());
            perDayDto.setLessonNumber(detailedLesson.getLessonNumber());
            perDayDto.setDayOfWeek(DAY_OF_WEEK.getWeekdayByNumber(detailedLesson.getDayOfWeek()).get().getWeekday());
            perDayDto.setRoomName(detailedLesson.getRoomName());
            perDayDto.setTeacher(detailedLesson.getTeacherName());
            perDayDto.setSubjectName(detailedLesson.getSubjectName());
            list.add(perDayDto);
        }
        detailedLessonDto.setPerDayDto(list);
        return detailedLessonDto;
    }

    public SkillsDto mapItemToItemDto(Skills item) {
        SkillsDto skillsDto = new SkillsDto();
        skillsDto.setSkillsId(item.getId());
        skillsDto.setTeacherName(item.getTeacherName());
        skillsDto.setPosition(item.getPosition());
        skillsDto.setEmail(item.getEmail());
        skillsDto.setSkills(item.getSkills());
        skillsDto.setImage(item.getPhoto());
        return skillsDto;
    }
}
