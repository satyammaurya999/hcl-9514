package com.example.SpringBootdemo.controller;

import com.example.SpringBootdemo.Entity.Student;
import com.example.SpringBootdemo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(
            @RequestBody Student student) {

        Student createdStudent = studentService.createStudent(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }
    //read
    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        Student studentres=studentService.getStudent(id);
        if(studentres==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentres);
    }
    //all read
    @GetMapping("/getall")
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> studentres=studentService.getAllStudent();
        if(studentres.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentres);
    }
    @PutMapping("/Update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student ){
        Student studentres=studentService.updateStudent(id,student);
        if(studentres==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentres);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        Boolean isdeleted=studentService.deleteStudent(id);
        if(!isdeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body("deleted table");


    }

}