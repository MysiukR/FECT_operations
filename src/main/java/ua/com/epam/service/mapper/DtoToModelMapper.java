package ua.com.epam.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ua.com.epam.entity.Room;
import ua.com.epam.entity.Subject;
import ua.com.epam.entity.Teacher;
import ua.com.epam.entity.dto.teacher.TeacherDto;
import ua.com.epam.entity.dto.subject.SubjectDto;
import ua.com.epam.entity.dto.room.RoomDto;
import ua.com.epam.service.mapper.converter.teacher.TeacherDtoToTeacher;
import ua.com.epam.service.mapper.converter.subject.SubjectDtoToSubject;
import ua.com.epam.service.mapper.converter.room.RoomDtoToRoom;

@Service
public class DtoToModelMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public DtoToModelMapper() {
        modelMapper.addConverter(new TeacherDtoToTeacher());
        modelMapper.addConverter(new RoomDtoToRoom());
        modelMapper.addConverter(new SubjectDtoToSubject());
    }

    public Teacher mapTeacherDtoToTeacher(TeacherDto author) {
        return modelMapper.map(author, Teacher.class);
    }

    public Room mapRoomDtoToRoom(RoomDto genre) {
        return modelMapper.map(genre, Room.class);
    }

    public Subject mapSubjectDtoToSubject(SubjectDto book) {
        return modelMapper.map(book, Subject.class);
    }
}
