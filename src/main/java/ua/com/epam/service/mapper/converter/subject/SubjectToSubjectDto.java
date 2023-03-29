package ua.com.epam.service.mapper.converter.subject;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ua.com.epam.entity.Subject;
import ua.com.epam.entity.dto.subject.SubjectDto;

public class SubjectToSubjectDto implements Converter<Subject, SubjectDto> {

    @Override
    public SubjectDto convert(MappingContext<Subject, SubjectDto> mappingContext) {
        Subject source = mappingContext.getSource();

        SubjectDto dto = new SubjectDto();
        dto.setSubjectId(source.getSubjectId());
        dto.setSubjectName(source.getSubjectName());
        dto.setSubjectLanguage(source.getSubjectLang());
        dto.setSubjectDescription(source.getDescription());

        return dto;
    }
}
