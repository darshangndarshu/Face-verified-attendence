package com.Face_detection.Face_detection.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@Entity

public class Student{


    // Getters and Setters
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String studentId;

        private LocalDate presentDate;

}

