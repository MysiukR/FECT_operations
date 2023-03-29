package ua.com.epam.entity.dto.room;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import ua.com.epam.service.util.deserializer.CustomLongDeserializer;
import ua.com.epam.service.util.deserializer.CustomStringDeserializer;

import javax.validation.constraints.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "Room")
public class RoomDto {

    @ApiModelProperty(required = true)
    @JsonDeserialize(using = CustomLongDeserializer.class)
    @NotNull(message = "Value 'roomId' is required!")
    @PositiveOrZero(message = "Value 'roomId' must be positive!")
    private Long roomId;

    @ApiModelProperty(required = true, position = 1)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @NotBlank(message = "Value 'roomName' is required!")
    @Size(max = 50, message = "Value 'roomName' cannot be longer than 50 characters!")
    private String roomName;

    @ApiModelProperty(position = 2)
    @JsonDeserialize(using = CustomStringDeserializer.class)
    @Size(max = 1000, message = "Value 'description' cannot be longer than 1000 characters!")
    private String roomDescription = "";
}
