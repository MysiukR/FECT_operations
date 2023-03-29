package ua.com.epam.service.mapper.converter.room;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ua.com.epam.entity.Room;
import ua.com.epam.entity.dto.room.RoomDto;

public class RoomDtoToRoom implements Converter<Room, RoomDto> {
    @Override
    public RoomDto convert(MappingContext<Room, RoomDto> mappingContext) {
        Room source = mappingContext.getSource();

        RoomDto genre = new RoomDto();
        genre.setRoomId(source.getRoomId());
        genre.setRoomName(source.getRoomName());
        genre.setRoomDescription(source.getDescription());

        return genre;
    }
}
