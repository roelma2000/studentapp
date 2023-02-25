package com.manage.student.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  //for creating the table
@Table(name = "tblstudent")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //generating auto inc
    private int id;

    private String name;
    private String email;
    private String course;
    private int age;

}
