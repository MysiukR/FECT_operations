package ua.com.epam.service.mapper.converter.teacher;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ua.com.epam.entity.Teacher;
import ua.com.epam.entity.dto.teacher.TeacherDto;

public class TeacherDtoToTeacher implements Converter<TeacherDto, Teacher> {

    @Override
    public Teacher convert(MappingContext<TeacherDto, Teacher> mappingContext) {
        TeacherDto source = mappingContext.getSource();

        Teacher author = new Teacher();
        author.setTeacherId(source.getTeacherId());
        author.setFirstName(source.getTeacherName().getFirst());
        author.setSecondName(source.getTeacherName().getSecond());
        author.setSiteReference(source.getSiteReference());
        author.setDescription(source.getTeacherDescription());

        return author;
    }
}
