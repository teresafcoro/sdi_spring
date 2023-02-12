package com.uniovi.sdi2223505spring.controllers;

import com.uniovi.sdi2223505spring.services.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.uniovi.sdi2223505spring.entities.Mark;

@RestController
public class MarksController {

    @Autowired // Inyectar el servicio
    private MarksService marksService;

    @RequestMapping("/mark/list")
    public String getList() {
        return marksService.getMarks().toString();
        // return "Getting List";
    }

    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@ModelAttribute Mark mark) {
        marksService.addMark(mark);
        return "Ok";
        /*return "added: " + mark.getDescription()
                + " with score: " + mark.getScore()
                + " id: " + mark.getId();*/
    }

    @RequestMapping("/mark/details/{id}")
    public String getDetail(@PathVariable Long id) {
        return marksService.getMark(id).toString();
        // return "Getting Details =>" + id;
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id) {
        marksService.deleteMark(id);
        return "Ok";
    }

}
