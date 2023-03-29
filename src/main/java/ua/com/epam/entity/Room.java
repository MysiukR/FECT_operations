package ua.com.epam.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor

@Entity
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", unique = true, nullable = false)
    private Long roomId;

    @Column(name = "room_name", length = 50, unique = true, nullable = false)
    private String roomName;

    @Column(name = "room_descr" , length = 1000)
    private String description;
}
