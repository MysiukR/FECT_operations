package ua.com.epam.entity.dto.teacher.nested;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import ua.com.epam.service.util.deserializer.CustomStringDeserializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "Name")
public class NameDto {

    @ApiModelProperty(required = true)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @NotBlank(message = "Value 'first' is required!")
    @Size(min = 1, max = 50, message = "Value 'first' cannot be longer than 50 characters!")
    private String first;

    @ApiModelProperty(required = true, position = 1)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @NotBlank(message = "Value 'second' is required!")
    @Size(min = 1, max = 50, message = "Value 'second' cannot be longer than 50 characters!")
    private String second;
}
