package ua.com.epam.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor

@Entity
public class Teacher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacher_id", unique = true, nullable = false)
    private Long teacherId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 50)
    private String secondName;

    @Formula("CONCAT(first_name, ' ', second_name)")
    private String fullName;

    @Column(name = "teacher_descr", length = 1000)
    private String description;

    @Column(length = 30)
    private String siteReference;

}
