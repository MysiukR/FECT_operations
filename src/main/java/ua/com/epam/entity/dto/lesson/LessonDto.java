package ua.com.epam.entity.dto.lesson;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.epam.service.util.deserializer.CustomLongDeserializer;
import ua.com.epam.service.util.deserializer.CustomStringDeserializer;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ApiModel(value = "Lesson")
public class LessonDto {
  @ApiModelProperty(required = true)
  @JsonDeserialize(using = CustomLongDeserializer.class)
  @NotNull(message = "Value 'subjectId' is required!")
  @PositiveOrZero(message = "Value 'subjectId' must be positive!")
  private Long lessonId;

  @ApiModelProperty(required = true, position = 1)
  @JsonDeserialize(using = CustomStringDeserializer.class)
  @NotNull(message = "Value 'dayOfWeek' is required!")
  @PositiveOrZero(message = "Value 'subjectId' must be positive!")
  private Integer dayOfWeek;

  @ApiModelProperty(required = true, position = 2)
  @JsonDeserialize(using = CustomStringDeserializer.class)
  @NotBlank(message = "Value 'lessonNumber' is required!")
  @PositiveOrZero(message = "Value 'lessonNumber' must be positive!")
  private Integer lessonNumber;



}
