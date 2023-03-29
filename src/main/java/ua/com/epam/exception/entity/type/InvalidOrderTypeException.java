package ua.com.epam.exception.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvalidOrderTypeException extends RuntimeException {
    private String invalidOrder;
}
