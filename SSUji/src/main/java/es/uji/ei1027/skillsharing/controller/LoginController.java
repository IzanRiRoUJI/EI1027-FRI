package es.uji.ei1027.skillsharing.controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.skillsharing.dao.UserDao;
import es.uji.ei1027.skillsharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Student.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        Student user = (Student) obj;
        if (user.getEmail().trim().equals(""))
            errors.rejectValue("email", "Obligatorio",
                    "No puedes dejar el campo vacio");
        if(user.getPassword().trim().equals(""))
            errors.rejectValue("password", "Obligatorio",
                    "No puedes dejar el campo vacio");
    }
}

@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new Student());
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") Student user,
                             BindingResult bindingResult, HttpSession session) {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }

        user = userDao.loadUserByUsername(user.getEmail(), user.getPassword());
        if (user == null) {
            bindingResult.rejectValue("password", "badpw", "Contraseña incorrecta");
            return "login";
        }

        session.setAttribute("user", user);
        // session.setAttribute("userDNI", user.getDni()); // Hacer user.dni en el html da error asi que guardo dni en otra variable
        System.out.println("INICIO SESIION " + user);


        if(user.isBanned()){
            session.setAttribute("user", null);
            return "student/banned";
        }

        if(session.getAttribute("nextUrl") == null){
            return "redirect:/";
        }else{
            String url = session.getAttribute("nextUrl").toString();
            session.removeAttribute("nextUrl");
            return "redirect:" + url;
        }

    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        System.out.println("FIN SESIION " + session.getAttribute("user"));
        session.invalidate();
        return "redirect:/";
    }
}
