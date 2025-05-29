package cursoSpringBoot.controllers;

import courseSpringBoot.domain.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private List<Student> students = new ArrayList<>(List.of(
            new Student(1, "Juan Pérez", "juan.perez@example.com", 20, "Matemáticas"),
            new Student(2, "Laura Gómez", "laura.gomez@example.com", 22, "Biología"),
            new Student(3, "Carlos Ruiz", "carlos.ruiz@example.com", 19, "Física"),
            new Student(4, "Ana Torres", "ana.torres@example.com", 21, "Literatura")
    ));

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        if (this.students.isEmpty()) {
            return ResponseEntity.noContent().build(); // http 204 no content
        }
        return ResponseEntity.ok(this.students); // http 200 ok con la lista
    }

    @GetMapping("/{name}")
    public ResponseEntity<Student> getStudentByName(@PathVariable String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return this.findStudentByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Student> postStudents(@RequestBody Student student) {
        int nextId = this.students.stream().mapToInt(Student::getID).max().orElse(0) + 1;
        student.setID(nextId);
        this.students.add(student);
        return ResponseEntity.ok(student);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Student> putStudents(@PathVariable int id, @RequestBody Student updatedStudent) {

//        for (Student s : this.students) {
//            if (s.getID() == id) {
//                s.setName(updatedStudent.getName());
//                s.setAge(updatedStudent.getAge());
//                s.setEmail(updatedStudent.getEmail());
//                s.setCourse(updatedStudent.getCourse());
//                return ResponseEntity.ok(updatedStudent);
//            }
//        }
//        return ResponseEntity.notFound().build();
        return findStudentByID(id).map(s -> {
            s.setName(updatedStudent.getName());
            s.setEmail(updatedStudent.getEmail());
            s.setAge(updatedStudent.getAge());
            s.setCourse(updatedStudent.getCourse());
            return ResponseEntity.ok(updatedStudent);
        }).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Student> patchStudents(@PathVariable int id, @RequestBody Student patch) {
//        for (Student s : this.students) {
//            if (s.getID() == id) {
//                if (s.getName() != null) s.setName(patch.getName());
//                if (s.getEmail() != null) s.setEmail(patch.getEmail());
//                if (s.getCourse() != null) s.setCourse(patch.getCourse());
//                return ResponseEntity.ok(s);
//            }
//        }
//        return ResponseEntity.notFound().build();
        return this.findStudentByID(id).map(s -> {
            if (patch.getName() != null) s.setName(patch.getName());
            if (patch.getEmail() != null) s.setEmail(patch.getEmail());
            if (patch.getCourse() != null) s.setCourse(patch.getCourse());
            if (patch.getAge() != 0) s.setAge(patch.getAge());
            return ResponseEntity.ok(s);
        }).orElse(ResponseEntity.notFound().build());

    }

    @RequestMapping(value = "/{}id", method = RequestMethod.DELETE)
    public ResponseEntity<Student> deleteStudents(@PathVariable int id) {
        Optional<Student> studentToDelete = this.students.stream()
                .filter(s -> s.getID() == id)
                .findFirst();

        studentToDelete.ifPresent(this.students::remove);

        return studentToDelete
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Optional<Student> findStudentByName(String name) {
        return this.students.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name.trim()))
                .findFirst();
    }

    private Optional<Student> findStudentByID(int ID) {
        return this.students.stream()
                .filter(s -> s.getID() == ID)
                .findFirst();
    }
}
