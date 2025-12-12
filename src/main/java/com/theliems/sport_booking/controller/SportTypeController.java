package com.theliems.sport_booking.controller;

import com.theliems.sport_booking.model.SportType;
import com.theliems.sport_booking.service.SportTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SportTypeController {

    private final SportTypeService service;

    public SportTypeController(SportTypeService service) {
        this.service = service;
    }

    // LIST PAGE
    @GetMapping("/admin/sport-type")
    public String list(Model model) {

        model.addAttribute("pageTitle", "Sport Types");
        model.addAttribute("active", "sporttype");
        model.addAttribute("list", service.getAll());

        model.addAttribute("pageContent", "admin/sporttype/list");

        return "admin/layouts/admin_layout";
    }

    // CREATE PAGE
    @GetMapping("/admin/sport-type/create")
    public String createPage(Model model) {

        model.addAttribute("pageTitle", "Add Sport Type");
        model.addAttribute("active", "sporttype");
        model.addAttribute("sportType", new SportType());

        model.addAttribute("pageContent", "admin/sporttype/create");

        return "admin/layouts/admin_layout";
    }

    @PostMapping("/admin/sport-type/create")
    public String create(@ModelAttribute SportType sportType) {
        service.create(sportType);
        return "redirect:/admin/sport-type";
    }

    // EDIT PAGE
    @GetMapping("/admin/sport-type/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {

        SportType st = service.getById(id);
        if (st == null) return "redirect:/admin/sport-type";

        model.addAttribute("pageTitle", "Edit Sport Type");
        model.addAttribute("active", "sporttype");
        model.addAttribute("sportType", st);

        model.addAttribute("pageContent", "admin/sporttype/edit");

        return "admin/layouts/admin_layout";
    }

    @PostMapping("/admin/sport-type/edit/{id}")
    public String edit(@PathVariable Integer id, @ModelAttribute SportType sportType) {
        service.update(id, sportType);
        return "redirect:/admin/sport-type";
    }

    // DELETE
    @GetMapping("/admin/sport-type/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/admin/sport-type";
    }

    // API JSON
    @GetMapping("/api/sport-type")
    @ResponseBody
    public List<SportType> getAllApi() {
        return service.getAll();
    }

    @GetMapping("/api/sport-type/{id}")
    @ResponseBody
    public ResponseEntity<?> getOneApi(@PathVariable Integer id) {
        SportType st = service.getById(id);
        if (st == null) return ResponseEntity.status(404).body("Not found");
        return ResponseEntity.ok(st);
    }

    @PostMapping("/api/sport-type")
    @ResponseBody
    public ResponseEntity<?> createApi(@RequestBody SportType sportType) {
        return ResponseEntity.ok(service.create(sportType));
    }

    @PutMapping("/api/sport-type/{id}")
    @ResponseBody
    public ResponseEntity<?> updateApi(@PathVariable Integer id, @RequestBody SportType sportType) {
        SportType updated = service.update(id, sportType);
        if (updated == null) return ResponseEntity.status(404).body("Not found");
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/api/sport-type/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteApi(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted ID = " + id);
    }
}
