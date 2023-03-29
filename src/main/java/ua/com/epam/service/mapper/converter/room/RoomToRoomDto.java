package ua.com.epam.service.mapper.converter.room;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ua.com.epam.entity.Room;
import ua.com.epam.entity.dto.room.RoomDto;

public class RoomToRoomDto implements Converter<Room, RoomDto> {
    @Override
    public RoomDto convert(MappingContext<Room, RoomDto> mappingContext) {
        Room g = mappingContext.getSource();

        RoomDto gt = new RoomDto();
        gt.setRoomId(g.getRoomId());
        gt.setRoomName(g.getRoomName());
        gt.setRoomDescription(g.getDescription());

        return gt;
    }
}
