package ua.com.epam.service.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ua.com.epam.exception.entity.type.InvalidTypeException;

import java.io.IOException;

public class CustomLongDeserializer extends StdDeserializer<Long> {

    public CustomLongDeserializer() {
        this(null);
    }

    public CustomLongDeserializer(Class t) {
        super(t);
    }

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Long val;

        try {
            val = jsonParser.getLongValue();
        } catch (NumberFormatException | IOException e) {
            throw new InvalidTypeException(jsonParser.getCurrentName(), jsonParser.getText(), Long.class);
        }

        return val;
    }
}
