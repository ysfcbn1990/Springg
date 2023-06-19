package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
@RestController
@RequestMapping("/students")//http://localhost:8080/students
public class StudentController {

Logger logger=LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

   //spring boot u selamlama :)
    //http://localhost:8080/students/great

    @GetMapping("/greet")
public String greet(){
        return "Hello Spring Boot";
    }

    //1-tüm studentları listeleyelim-READ
    //http://localhost:8080/students+GET


    @GetMapping
    public ResponseEntity<List<Student>> listAllStudents(){
     List<Student>studentList=studentService.getAllStudent();
     //return new ResponseEntity<>(studentList, HttpStatus.OK);//200
      return ResponseEntity.ok(studentList);
    }
//response:body+HTTP status code
//ResponseEntity:response body ile birlikte HTTP status code nu göndermemizi sağlar.
    //ResponseEntity.ok();methodu HTTP status olarak OK yada 200 dönmek için bir kısa yoldur


//2-yeni bir student Create etme
    //http://localhost:8080/students + POST + RequestBody(JSON)

    @PostMapping
    public ResponseEntity<Map<String,String>>createStudent(@Valid @RequestBody Student student){

        studentService.saveStudent(student);
        Map<String,String>response=new HashMap<>();
        response.put("message","Student is created successfully");
        response.put("status","successfully");

        return new ResponseEntity<>(response,HttpStatus.CREATED);//201
    }

//@RequestBody:HTTP requestin bodsindeki JSON formatındaki bilgiyi student objesine mapler.
// (Entity obje <-> JSON )--->Jackson

    //3-belirli bir id ile studenti görüntüleme+Request Param
    //http://localhost:8080/students/query?id=1 + GET +

    @GetMapping("/query")
    public ResponseEntity<Student>getStudent(@RequestParam("id") Long id){

        Student student=studentService.getStudentById(id);
        return new ResponseEntity<>(student,HttpStatus.OK);//200
    }

    //4-belirli bir id ile studenti görüntüleme+Path Param
    //http://localhost:8080/students/1+ GET +
    @GetMapping("/{id}")
    public ResponseEntity<Student>getStudentById(@PathVariable("id") Long id){

        Student student=studentService.getStudentById(id);
        return new ResponseEntity<>(student,HttpStatus.OK);//200
    }
//clienttan bilgi almak için:1-)JSON formatında request body
                          // 2-)request param query?id=1
                           //  3-)path param/1

    //5-id si verilen öğrenciyi silme
    //http://localhost:8080/students/1+ DELETE

    @DeleteMapping("/{id}")

    public ResponseEntity<Map<String,String>>deleteStudent(@PathVariable Long id){

        studentService.deleteStudent(id);

        Map<String,String>respponse=new HashMap<>();
        respponse.put("message","Student is deleted successfully");
        respponse.put("status","success");

        return ResponseEntity.ok(respponse);


    }

//9-belirli bir id ile studentı update edelim.(name,lastName,grade,email,phoneNumber)-DTO

    //http://localhost:8080/students/1+ UPDATE +JSON

@PutMapping("/{id}")

    public ResponseEntity<Map<String,String>> updateStudent(@PathVariable("id")Long id,@Valid @RequestBody StudentDTO studentDTO){

        studentService.updateStudent(id,studentDTO);

    Map<String,String>respponse=new HashMap<>();
    respponse.put("message","Student is updated successfully");
    respponse.put("status","success");

    return ResponseEntity.ok(respponse);

}


//put tamamını güncellerken.girilmeyen değerleride günceller
//patc kısmi  günceller.girilmeyen değerleri korur

//DTO:Data Transfer Object
//1-Güvenlik
//2-Hız


    //11-pagination-Sayfalandırma
    //Tüm Kayıtları page page listeleyelim
    //http://localhost:8080/students/page?page=1&
    //                                     size=10&
    //                                    sort=name&
    //                                    direction=DESC + GET

    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getAllStudentByPage(
            @RequestParam(value="page",required = false,defaultValue ="0" )int page,//hangi page gösteriilsin
            @RequestParam(value="size",required = false,defaultValue = "2")int size,//kaç kayıt bulunsun
            @RequestParam("sort")String prop,//hangi fielda göre
            @RequestParam("direction")Sort.Direction direction)//sıralama yönü
    {
        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,prop));
Page<Student>studentsByPage=studentService.getAllStudentPaging(pageable);
return new ResponseEntity<>(studentsByPage,HttpStatus.OK);

    }

    //page,size,sort,direction parametrelerinin girilmesini opsiyonel yapabiliriz.(required=false)
    //Parametrelerin girilmesi opsiyonel olduğunda defaultvalue vermeliyiz(defaultValue ="0")



    //13-lastName ile studentları listeleyelim
    //http://localhost:8080/students/querylastname?lastName=Bey


    @GetMapping("/querylastname")
    public ResponseEntity<List<Student>> getAllStudentsBylastName(@RequestParam("lastName")String lastName){

        List<Student>studentlist=studentService.getAllStudentByLastName(lastName);

        return ResponseEntity.ok(studentlist);

    }

    //15-grade ile studentları listeleyelim. **ÖDEV**
    ////http://localhost:8080/students/grade/99

@GetMapping("/grade/{grade}")

    public ResponseEntity<List<Student>>getAllStudentByGrade(@PathVariable("grade")Integer grade){

    List<Student>studentlist=studentService.getStudentByGrade(grade);
return ResponseEntity.ok(studentlist);
}

//17-id si verilen studentın görüntüleme requestine response olarak DTO dönelim
//http://localhost:8080/students/dto/2
    @GetMapping("/dto/{id}")
    public ResponseEntity<StudentDTO>getStudentDtoById(@PathVariable("id") Long id){

        StudentDTO studentDTO=studentService.getStudentDtoById(id);

        logger.warn("-----------------Servisten StudentDTO Objesi alındı: "+studentDTO.getName());

        return ResponseEntity.ok(studentDTO);

    }

    @GetMapping("/welcome")

    public String welcome(HttpServletRequest request){

        logger.info("welcome mesajı  {}",request.getServletPath());

        return "WELCOME :)";
    }




}
