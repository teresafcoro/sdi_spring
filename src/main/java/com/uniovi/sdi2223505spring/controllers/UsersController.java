package com.uniovi.sdi2223505spring.controllers;

import com.uniovi.sdi2223505spring.entities.User;
import com.uniovi.sdi2223505spring.services.RolesService;
import com.uniovi.sdi2223505spring.services.SecurityService;
import com.uniovi.sdi2223505spring.services.UsersService;
import com.uniovi.sdi2223505spring.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// Gestión de usuarios
@Controller
public class UsersController {

    @Autowired
    private RolesService rolesService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SignUpFormValidator signUpFormValidator;

    @RequestMapping("/user/list")
    public String getList(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }

    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        // model.addAttribute("usersList", usersService.getUsers());
        // Obtengo la lista de roles y la envío a la vista (muestra los roles disponibles)
        model.addAttribute("rolesList", rolesService.getRoles());
        return "user/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "user/edit";
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@ModelAttribute User user, @PathVariable Long id) {
        User originalUser = usersService.getUser(id);
        // Modifico dni, nombre y apellidos
        originalUser.setDni(user.getDni());
        originalUser.setName(user.getName());
        originalUser.setLastName(user.getLastName());
        usersService.addUser(originalUser);
        return "redirect:/user/details/"+id;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors())
            return "signup";
        user.setRole(rolesService.getRoles()[0]); // ¡Debe tener un rol!
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() { return "login"; }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByDni(dni);
        model.addAttribute("markList", activeUser.getMarks());
        return "home";
    }

    // Defino el endpoint para devolver el fragmento correspondiente, en lugar de la vista completa
    @RequestMapping("/user/list/update")
    public String updateList(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list :: tableUsers";
    }

}
