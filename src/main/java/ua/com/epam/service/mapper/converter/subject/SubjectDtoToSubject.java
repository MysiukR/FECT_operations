package ua.com.epam.service.mapper.converter.subject;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ua.com.epam.entity.Subject;
import ua.com.epam.entity.dto.subject.SubjectDto;

public class SubjectDtoToSubject implements Converter<SubjectDto, Subject> {
    @Override
    public Subject convert(MappingContext<SubjectDto, Subject> mappingContext) {
        SubjectDto source = mappingContext.getSource();

        Subject b = new Subject();
        b.setSubjectId(source.getSubjectId());
        b.setSubjectName(source.getSubjectName());
        b.setSubjectLang(source.getSubjectLanguage());
        b.setDescription(source.getSubjectDescription());

        return b;
    }
}
