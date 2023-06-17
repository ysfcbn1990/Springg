package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/students")
public class StudentCNTROLExercise {

    @Autowired
    private StudentService studentService;



    //spring boot u selamlama :)
    //http://localhost:8080/students/greet

    @GetMapping("/greet")
    public String hiBoot(){

        return "Hello I am Spring Boot";
    }

    //1-tüm studentları listeleyelim-READ
    //http://localhost:8080/students+GET

    @GetMapping
    public ResponseEntity<List<Student>>getAllStudent(){
        List<Student>studentList=studentService.getAllStudent();
        return ResponseEntity.ok(studentList);
    }

//2-yeni bir student Create etme
    //http://localhost:8080/students + POST + RequestBody(JSON)

    @PostMapping
    public ResponseEntity<Map<String,String>>createStudent(@Valid @RequestBody Student student){

      studentService.saveStudent(student);
        Map<String,String>response=new HashMap<>();
        response.put("message","Student created successfully");
        response.put("status","success");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    //3-belirli bir id ile studenti görüntüleme+Request Param
    //http://localhost:8080/students/query?id=1 + GET +

    @GetMapping("/query")

    public ResponseEntity<Student>getStudentById(@RequestParam("id")Long id){

        Student student=studentService.getStudentById(id);

        return ResponseEntity.ok(student);

    }

    //4-belirli bir id ile studenti görüntüleme+Path Param
    //http://localhost:8080/students/1+ GET +

    @GetMapping("/{id}")

    public ResponseEntity<Student>getStudentById2(@PathVariable("id")Long id){

        Student student=studentService.getStudentById(id);

        return ResponseEntity.ok(student);

    }


    //5-id si verilen öğrenciyi silme
    //http://localhost:8080/students/1+ DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>>deletedStudent(@PathVariable("id")Long id){

        studentService.deleteStudent(id);
        Map<String,String>response=new HashMap<>();
        response.put("message","student is delete");

        return new ResponseEntity<>(response,HttpStatus.OK);


    }


//9-belirli bir id ile studentı update edelim.(name,lastName,grade,email,phoneNumber)-DTO

    //http://localhost:8080/students/1+ UPDATE +JSON

   @PutMapping("/{id}")

    public ResponseEntity<Map<String,String>>updateStudent(@PathVariable("id")Long id,@RequestBody StudentDTO studentDTO){

        studentService.updateStudent(id,studentDTO);
        Map<String,String>response=new HashMap<>();
        response.put("message","Student is updated");
        response.put("status","success");
        return ResponseEntity.ok(response);

   }













}
