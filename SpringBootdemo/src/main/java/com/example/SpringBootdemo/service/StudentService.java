package com.example.SpringBootdemo.service;

import com.example.SpringBootdemo.Entity.Student;
import com.example.SpringBootdemo.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student studentReq) {

        System.out.println("Inside Student Service");

        Student studentResp = studentRepository.save(studentReq);

        System.out.println("Exiting Student Service");

        return studentResp;
    }

    public Student getStudent(Long id) {
        Optional<Student> studentres=studentRepository.findById(id);
        if(studentres.isPresent()){
            return studentres.get();
        }
        return null;

    }

    public List<Student> getAllStudent() {
        List<Student> studentList=studentRepository.findAll();
        return studentList;
    }


    public Student updateStudent(Long id, Student student) {
        Optional<Student> existingstu=studentRepository.findById(id);
        if(existingstu.isEmpty()){
            return null;
        }
        Student studentTosave=existingstu.get();
        studentTosave.setName(student.getName());
        studentTosave.setAge(student.getAge());
        studentTosave.setRollNo(student.getRollNo());
        studentTosave.setEmail(student.getEmail());
        studentTosave.setSubject(student.getSubject());

        return studentRepository.save(studentTosave);
    }

    public Boolean deleteStudent(Long id) {
        Boolean isexist=studentRepository.existsById(id);
        if(!isexist) return false;
        studentRepository.deleteById(id);
        return true;
    }
}