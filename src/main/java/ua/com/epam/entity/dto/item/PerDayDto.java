package ua.com.epam.entity.dto.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import ua.com.epam.service.util.deserializer.CustomIntegerDeserializer;
import ua.com.epam.service.util.deserializer.CustomLongDeserializer;
import ua.com.epam.service.util.deserializer.CustomStringDeserializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class PerDayDto {
  @ApiModelProperty(required = true)
  @JsonDeserialize(using = CustomLongDeserializer.class)
  @NotNull(message = "Value 'subjectId' is required!")
  @PositiveOrZero(message = "Value 'subjectId' must be positive!")
  private Long lessonId;

  @ApiModelProperty(required = true, position = 1)
  @JsonDeserialize(using = CustomStringDeserializer.class)
  @NotNull(message = "Value 'dayOfWeek' is required!")
  private String dayOfWeek;

  @ApiModelProperty(required = true, position = 2)
  @JsonDeserialize(using = CustomIntegerDeserializer.class)
  @NotBlank(message = "Value 'lessonNumber' is required!")
  @PositiveOrZero(message = "Value 'lessonNumber' must be positive!")
  private Integer lessonNumber;

  @ApiModelProperty(required = true, position = 2)
  @JsonDeserialize(using = CustomStringDeserializer.class)
  @NotBlank(message = "Value 'teacher' is required!")
  private String teacher;

  @ApiModelProperty(required = true, position = 2)
  @JsonDeserialize(using = CustomStringDeserializer.class)
  @NotBlank(message = "Value 'roomName' is required!")
  private String roomName;

  @ApiModelProperty(required = true, position = 2)
  @JsonDeserialize(using = CustomStringDeserializer.class)
  @NotBlank(message = "Value 'subjectName' is required!")
  private String subjectName;


}
