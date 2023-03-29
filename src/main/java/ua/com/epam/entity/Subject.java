package ua.com.epam.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor

@Entity
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_id", unique = true, nullable = false)
    private Long subjectId;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "subject_language", nullable = false, length = 50)
    private String subjectLang;

    @Column(name = "subject_descr", length = 1000)
    private String description;

}