package ua.com.epam.entity.dto.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.util.CustomObjectInputStream;
import ua.com.epam.service.util.deserializer.CustomIntegerDeserializer;
import ua.com.epam.service.util.deserializer.CustomLongDeserializer;
import ua.com.epam.service.util.deserializer.CustomStringDeserializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ApiModel(value = "Item")
public class DetailedLessonDto {
  @ApiModelProperty(required = true)
  @JsonDeserialize(using = CustomLongDeserializer.class)
  @NotNull(message = "Value 'subjectId' is required!")
  @PositiveOrZero(message = "Value 'subjectId' must be positive!")
  private Long lessonId;

  @ApiModelProperty(required = true, position = 2)
  @JsonDeserialize(using = CustomStringDeserializer.class)
  @NotBlank(message = "Value 'groupName' is required!")
  private String groupName;

  @ApiModelProperty(required = true, position = 2)
  @NotBlank(message = "Value 'perDayDto' is required!")
  private List<PerDayDto> perDayDto;
}
