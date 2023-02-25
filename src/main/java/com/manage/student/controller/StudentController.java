package com.manage.student.controller;
import com.manage.student.model.Student;
import com.manage.student.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    @RequestMapping({"/","/list","/showStudents"})
    public String getIndexPage(Model model){
        model.addAttribute("students", studentRepository.findAll());
        return "list-students";
    }
    @GetMapping("/add")
    public String getAddstudentForm(Student student){
        return "add-student";
    }
    @PostMapping("/add")
    public String insertStudentData(@Valid Student student, BindingResult bindingResult, Model model){
        //name validation
        if(student.getName().isBlank()){
            bindingResult.addError(new FieldError("student","name","Student name should not be blank."));
        }
        // validate the email input
        if(student.getEmail() == null || !student.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            bindingResult.addError(new FieldError("student","email","Please enter a valid email address."));
        }
        if(student.getCourse().isBlank()){
            bindingResult.addError(new FieldError("student","course","Course name should not be blank."));
        }
        if(student.getAge() < 10){
            bindingResult.addError(new FieldError("student","age","Enter correct age."));
        }
        if(bindingResult.hasErrors()){
            return "add-student";
        }
        studentRepository.save(student);
        model.addAttribute("students", studentRepository.findAll());
        return "list-students";
    }
    @GetMapping("/delete/{id}")
    public String deleteStudentInformation(@PathVariable("id")int id, Model model){
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid Student ID" + id));
        studentRepository.delete(student);
        model.addAttribute("students", studentRepository.findAll());
        return "list-students";
    }

    @GetMapping("/update/{id}")
    public String moveToUpdateStudentPage(@PathVariable("id")int id, Model model){
        Student student = studentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid Student ID" + id));
        model.addAttribute("student",student);
        return "edit-student";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id")int id,@Valid Student student,BindingResult bindingResult,Model model){
        //name validation
        if(student.getName().isBlank()){
            bindingResult.addError(new FieldError("student","name","Student name should not be blank."));
        }
        // validate the email input
        if(student.getEmail() == null || !student.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            bindingResult.addError(new FieldError("student","email","Please enter a valid email address."));
        }
        if(student.getCourse().isBlank()){
            bindingResult.addError(new FieldError("student","course","Course name should not be blank."));
        }
        if(student.getAge() < 10){
            bindingResult.addError(new FieldError("student","age","Enter correct age."));
        }
        if(bindingResult.hasErrors()){
            return "edit-student";
        }
        studentRepository.save(student);
        model.addAttribute("students", studentRepository.findAll());
        return "list-students";
    }

    // method to handle all Whitelabel errors
    @ExceptionHandler(Throwable.class)
    public String handleException(Model model, Exception ex) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
