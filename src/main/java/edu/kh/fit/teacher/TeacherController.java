package edu.kh.fit.teacher;  // teacher 패키지 내에서 관리

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TeacherController {

    @GetMapping("/trainer")
    public String teacherPage() {
        return "trainer/trainer";  // templates/teacher/teacher.html로 이동
    }
   
}
