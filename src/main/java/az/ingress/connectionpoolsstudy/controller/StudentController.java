package az.ingress.connectionpoolsstudy.controller;

import az.ingress.connectionpoolsstudy.dto.CreateStudentRequestDto;
import az.ingress.connectionpoolsstudy.dto.StudentResponseDto;
import az.ingress.connectionpoolsstudy.entity.Student;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@RequestMapping("/students")
@RequiredArgsConstructor
@RestController
public class StudentController {
    @PersistenceUnit
    private SessionFactory sessionFactory;

    private final ModelMapper modelMapper;

    @PostMapping
    public StudentResponseDto create(@RequestBody CreateStudentRequestDto request) {
        Student student = modelMapper.map(request, Student.class);
        Student studentSaved = sessionFactory.fromStatelessTransaction(session -> {
            session.insert(student);
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return student;
        });
        return modelMapper.map(studentSaved, StudentResponseDto.class);
    }

    @PostMapping("/bulk")
    public void createBulk(@RequestParam Integer number) {
        List<Student> students = IntStream.rangeClosed(1, number)
                .mapToObj(id -> {
                    Student student = new Student();
                    student.setName("Ali");
                    return student;
                })
                .toList();
        sessionFactory.inStatelessTransaction(session -> students.forEach(session::insert));
    }
}
