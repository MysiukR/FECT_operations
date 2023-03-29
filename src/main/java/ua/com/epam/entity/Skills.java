package ua.com.epam.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor

@Entity
public class Skills implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String teacherName;

    @Column(name = "POSITION", nullable = false)
    private String position;


    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SKILLS")
    private String skills;

    @Column(name = "PHOTO")
    private String photo;
}
