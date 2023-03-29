package ua.com.epam.entity.dto.teacher;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.epam.entity.dto.teacher.nested.NameDto;
import ua.com.epam.service.util.deserializer.CustomLongDeserializer;
import ua.com.epam.service.util.deserializer.CustomStringDeserializer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "Teacher")
public class TeacherDto {

    @ApiModelProperty(required = true)
    @JsonDeserialize(using = CustomLongDeserializer.class)
    @NotNull(message = "Value 'teacherId' is required!")
    @PositiveOrZero(message = "Value 'teacherId' must be positive!")
    private Long teacherId;

    @ApiModelProperty(required = true, position = 1)
    @Valid @NotNull(message = "Object 'teacherName' is required!")
    private NameDto teacherName;

    @ApiModelProperty(position = 2)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @Size(max = 30, message = "Value 'siteReference' cannot be longer than 30 characters!")
    private String siteReference = "";

    @ApiModelProperty(position = 4)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @Size(max = 1000, message = "Value 'teacherDescription' cannot be longer than 1000 characters!")
    private String teacherDescription = "";
}
