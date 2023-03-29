package ua.com.epam.entity.dto.skills;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import ua.com.epam.service.util.deserializer.CustomLongDeserializer;
import ua.com.epam.service.util.deserializer.CustomStringDeserializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "Skills")
public class SkillsDto {
    @ApiModelProperty(required = true)
    @JsonDeserialize(using = CustomLongDeserializer.class)
    @NotNull(message = "Value 'skillsId' is required!")
    @PositiveOrZero(message = "Value 'skillsId' must be positive!")
    private Integer skillsId;

    @ApiModelProperty(required = true, position = 1)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @NotBlank(message = "Value 'teacherName' is required!")
    @Size(max = 50, message = "Value 'teacherName' cannot be longer than 50 characters!")
    private String teacherName;

    @ApiModelProperty(position = 2)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @Size(max = 1000, message = "Value 'position' cannot be longer than 1000 characters!")
    private String position = "";

    @ApiModelProperty(position = 3)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @Size(max = 1000, message = "Value 'email' cannot be longer than 1000 characters!")
    private String email = "";

    @ApiModelProperty(position = 4)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @Size(max = 1000, message = "Value 'skills' cannot be longer than 1000 characters!")
    private String skills = "";

    @ApiModelProperty(position = 5)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @Size(max = 1000, message = "Value 'image' cannot be longer than 1000 characters!")
    private String image = "";
}
