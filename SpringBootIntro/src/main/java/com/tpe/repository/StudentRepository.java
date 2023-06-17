package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository//opsiyonel
public interface StudentRepository extends JpaRepository<Student,Long> {//JpaRepository<Entity Class,Id nin data tipi>


    Boolean existsByEmail(String email);//bu emaile sahip id varsa TRUE yoksa FALSE döndürecek


    List<Student> findAllByLastName(String lastName);//findAllById


    List<Student> findAllByGrade(Integer grade);

     //JPQL
  //  @Query("Select s FROM Student s WHERE s.grade=:pGrade")
    @Query("FROM Student s WHERE s.grade=:pGrade")
 List<Student> findAllGradeEqual(@Param("pGrade") Integer grade);

    //sql
  //  @Query(value = "Select * FROM student s WHERE s.grade=:pGrade",nativeQuery = true)//Student
  //  List<Student> findAllGradeEqual(@Param("pGrade") Integer grade);






    //DB den gelen studentı DTO ya çevirerek gönderiyor
    @Query("Select new com.tpe.dto.StudentDTO(s) from Student s where s.id=:pId")
    Optional<StudentDTO> findStudentDtoById(@Param("pId") Long id);




}
