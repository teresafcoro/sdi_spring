package com.uniovi.sdi2223505spring.controllers;

import com.uniovi.sdi2223505spring.services.MarksService;
import com.uniovi.sdi2223505spring.services.UsersService;
import com.uniovi.sdi2223505spring.validators.MarkEditFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.uniovi.sdi2223505spring.entities.Mark;
import org.springframework.ui.Model;

@Controller
public class MarksController {

    @Autowired
    private MarksService marksService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private MarkEditFormValidator markEditFormValidator;

    @RequestMapping("/mark/list")
    public String getList(Model model) {
        model.addAttribute("markList", marksService.getMarks());
        return "mark/list";
    }

    @RequestMapping(value = "/mark/add")
    public String getMark(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/add";
    }

    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@ModelAttribute Mark mark) {
        marksService.addMark(mark);
        return "redirect:/mark/list";
    }

    @RequestMapping("/mark/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        return "mark/details";
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id) {
        marksService.deleteMark(id);
        return "redirect:/mark/list";
    }

    @RequestMapping(value = "/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/edit";
    }

    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id) {
        Mark originalMark = marksService.getMark(id);
        // Modifico solo score y description
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:/mark/details/"+id;
    }

    // Defino el endpoint para devolver el fragmento correspondiente, en lugar de la vista completa
    @RequestMapping("/mark/list/update")
    public String updateList(Model model) {
        model.addAttribute("markList", marksService.getMarks());
        return "mark/list :: tableMarks";
    }

    @RequestMapping(value = "/mark/edit", method = RequestMethod.GET)
    public String modify(Model model) {
        model.addAttribute("mark", new Mark());
        return "/mark/edit";
    }

    @RequestMapping(value = "/mark/edit", method = RequestMethod.POST)
    public String modify(@Validated Mark mark, BindingResult result) {
        markEditFormValidator.validate(mark, result);
        if (result.hasErrors())
            return "/mark/edit";

        Mark originalMark = marksService.getMark(mark.getId());
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:home";
    }

}
