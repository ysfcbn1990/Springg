package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;


    public List<Student> getAllStudent() {

       //List<Student>students=studentRepository.findAll();//select * from Student:
       //return students;
        return studentRepository.findAll();
    }

    public void saveStudent(Student student) {
        //student daha önce kaydedilmiş mi?-->aynı emaile sahip student var mı?

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new ConflictException("Email  already exist!");

        }
        studentRepository.save(student);
    }


    public Student getStudentById(Long id) {

        Student student=studentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found by id: "+id));
        return student;
    }

    public void deleteStudent(Long id) {
        //bu idye sahip öğrenci var mı?
        Student foundStudent=getStudentById(id);
        studentRepository.delete(foundStudent);

    }



    public void updateStudent(Long id, StudentDTO studentDTO) {
//gelen id ile öğrenci var mı?varsa bulalım
        Student foundStudent=getStudentById(id);

        //studentDTO.email() zaten daha önceden DB de varsa
      boolean existsEmail=studentRepository.existsByEmail(studentDTO.getEmail());

        //existsEmail true ise bu email başka bir studentın olabilir, studentın kendi emaili olabilir??
        // id:3 student email:a@email.com
        //dto                student
        //b@email.com        id:4 b@email.com->existsEmail:true--->exception
        //c@email.com        DB de yok-------->existsEmail:false--update:OK
        //a@email.com        id:3 a@email.com ->existsEmail:true--update:OK

      if(existsEmail && !foundStudent.getEmail().equals(studentDTO.getEmail())){
          throw new ConflictException("Email already exists..");
      }

        foundStudent.setName(studentDTO.getName());
        foundStudent.setLastName(studentDTO.getLastName());
        foundStudent.setGrade(studentDTO.getGrade());
        foundStudent.setPhoneNumber(studentDTO.getPhoneNumber());
        foundStudent.setEmail(studentDTO.getEmail());//

        studentRepository.save(foundStudent);//saveOrUpdate gibi işlem yapar

    }



    public Page<Student> getAllStudentPaging(Pageable pageable) {

     return  studentRepository.findAll(pageable);

    }

    public List<Student> getAllStudentByLastName(String lastName) {

        return studentRepository.findAllByLastName(lastName);//select * from student where lastName;
    }

    public List<Student> getStudentByGrade(Integer grade) {
       // return studentRepository.findAllByGrade(grade);

        return studentRepository.findAllGradeEqual(grade);
    }

 //  public StudentDTO getStudentDtoById(Long id) {
 //      Student foundStudent = getStudentById(id);
 //      StudentDTO studentDTO = new StudentDTO(foundStudent);
 //      return studentDTO;
 //  }
      // studentDTO.setName(foundStudent.getName());
      // studentDTO.setLastName(foundStudent.getLastName());
      // studentDTO.setGrade(foundStudent.getGrade());
      // studentDTO.setPhoneNumber(foundStudent.getPhoneNumber());
      // studentDTO.setEmail(foundStudent.getEmail());


        //parametre olarak student objesinin kendisini verdiğimizde DTO oluşturan constructor

    //studentı DTO ya mapleme işlemini JPQL ile yapalım
    public StudentDTO getStudentDtoById(Long id) {

        StudentDTO studentDTO = studentRepository.findStudentDtoById(id).
                orElseThrow(()->new ResourceNotFoundException("Student not found by id :"+id));
        return studentDTO;
    }





}

