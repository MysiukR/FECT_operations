package ua.com.epam.entity.dto.subject;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import ua.com.epam.service.util.deserializer.CustomIntegerDeserializer;
import ua.com.epam.service.util.deserializer.CustomLongDeserializer;
import ua.com.epam.service.util.deserializer.CustomStringDeserializer;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ApiModel(value = "Subject")
public class SubjectDto {

    @ApiModelProperty(required = true)
    @JsonDeserialize(using = CustomLongDeserializer.class)
    @NotNull(message = "Value 'subjectId' is required!")
    @PositiveOrZero(message = "Value 'subjectId' must be positive!")
    private Long subjectId;

    @ApiModelProperty(required = true, position = 1)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @NotBlank(message = "Value 'subjectName' is required!")
    @Size(min = 1, max = 255, message = "Value 'subjectName' cannot be longer than 255 characters!")
    private String subjectName;

    @ApiModelProperty(required = true, position = 2)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @NotBlank(message = "Value 'subjectLanguage' is required!")
    @Size(min = 1, max = 50, message = "Value 'subjectLanguage' cannot be longer than 50 characters!")
    private String subjectLanguage;

    @ApiModelProperty(position = 3)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @Size(max = 1000, message = "Value 'description' cannot be longer than 1000 characters!")
    private String subjectDescription = "";

}
