package es.uji.ei1027.skillsharing.controller;

import es.uji.ei1027.skillsharing.dao.StudentDao;
import es.uji.ei1027.skillsharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentDao studentDao;

    @Autowired
    public void setStudentDao(StudentDao studentDao){
        this.studentDao=studentDao;
    }

    @RequestMapping("/list")
    public String listStudents(Model model) {
        model.addAttribute("students", studentDao.getStudents());
        return "student/list";
    }

    @RequestMapping(value="/add")
    public String addStudent(Model model) {
        model.addAttribute("student", new Student());
        return "student/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("student") Student student,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "student/add";
        studentDao.addStudent(student);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String updateStudent(Model model, @PathVariable String dni) {
        model.addAttribute("student", studentDao.getStudent(dni));
        return "student/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("student") Student student,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "student/update";
        studentDao.updateStudent(student);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
        studentDao.deleteStudent(dni);
        return "redirect:../list";
    }

    @RequestMapping(value="/ban/{dni}/{banned}")
    public String processBan(@PathVariable String dni, @PathVariable boolean banned) {
        studentDao.setBanStudent(dni, !banned);
        return "redirect:..";
    }

    @RequestMapping("/ban")
    public String listStudentsByBanStatus(Model model) {
        model.addAttribute("studentsNotBanned", studentDao.getStudentsByBanStatus(false));
        model.addAttribute("studentsBanned", studentDao.getStudentsByBanStatus(true));
        return "student/ban";
    }

}
