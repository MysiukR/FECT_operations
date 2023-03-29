package ua.com.epam.service.mapper.converter.teacher;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ua.com.epam.entity.Teacher;
import ua.com.epam.entity.dto.teacher.TeacherDto;
import ua.com.epam.entity.dto.teacher.nested.NameDto;

public class TeacherToTeacherDto implements Converter<Teacher, TeacherDto> {

    @Override
    public TeacherDto convert(MappingContext<Teacher, TeacherDto> mappingContext) {
        Teacher source = mappingContext.getSource();

        TeacherDto authorDto = new TeacherDto();
        authorDto.setTeacherId(source.getTeacherId());
        authorDto.setTeacherName(new NameDto(source.getFirstName(), source.getSecondName()));
        authorDto.setSiteReference(source.getSiteReference());
        authorDto.setTeacherDescription(source.getDescription());
        return authorDto;
    }
}
