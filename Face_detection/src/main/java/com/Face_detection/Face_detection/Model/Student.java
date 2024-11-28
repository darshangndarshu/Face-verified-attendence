package com.Face_detection.Face_detection.Model;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Student")
@Data
@AllArgsConstructor
public class Student {
    @Id
    private int id;
    private String name;
    private int age;
    private String email;
    }

