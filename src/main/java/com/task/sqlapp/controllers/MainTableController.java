package com.task.sqlapp.controllers;

import com.task.sqlapp.models.MainTable;
import com.task.sqlapp.repos.MainTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class MainTableController {

    @Autowired
    private MainTableRepository mainTableRepository;

    @GetMapping("/MainTable")
    public String MainTablePage(Model model) {
        Iterable<MainTable> MainTableData = mainTableRepository.findAll();
        model.addAttribute("MainTableData", MainTableData);
        return "MainTable";
    }

    @GetMapping("/MainTable/insert")
    public String InsertPage(Model model) {
        return "insert";
    }

    @PostMapping("/MainTable/insert")
    public String insertPost(@RequestParam(required = false) String name, @RequestParam(required = false) String manufacturer, @RequestParam(required = false) Integer price, Model model) {
        if (Objects.equals(name, "") || Objects.equals(manufacturer, "") || price == null) return "redirect:/MainTable";

        MainTable mainTableData = new MainTable(name, manufacturer, price);
        mainTableRepository.save(mainTableData);

        return "redirect:/MainTable";
    }

    @GetMapping("/MainTable/{id}/delete")
    public String deleteData(@PathVariable(value = "id") Long id, Model model) {
        mainTableRepository.deleteById(id);
        return "redirect:/MainTable";
    }

    @GetMapping("/MainTable/{id}/update")
    public String updateData(@PathVariable(value = "id") Long id, Model model) {
        MainTable record = mainTableRepository.findById(id).orElseThrow();
        model.addAttribute("record", record);
        return "update";
    }

    @PostMapping("/MainTable/update")
    public String updatePost(@RequestParam Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String manufacturer, @RequestParam(required = false) Integer price, Model model) {
        if (Objects.equals(name, "") || Objects.equals(manufacturer, "") || price == null) return "redirect:/MainTable";

        MainTable record = mainTableRepository.findById(id).orElseThrow();
        record.setName(name);
        record.setManufacturer(manufacturer);
        record.setPrice(price);
        mainTableRepository.save(record);
        return "redirect:/MainTable";
    }

    @PostMapping("/MainTable/find")
    public String findPost(@RequestParam String column, @RequestParam(required = false) String data, Model model) {
        if (Objects.equals(data, "")) return "redirect:/MainTable";

        Iterable<MainTable> MainTableData = null;

        try {
            if (Objects.equals(column, "id")) {
                MainTableData = mainTableRepository.findAllById(Long.valueOf(data));
            } else if (Objects.equals(column, "name")) {
                MainTableData = mainTableRepository.findAllByName(data);
            } else if (Objects.equals(column, "manufacturer")) {
                MainTableData = mainTableRepository.findAllByManufacturer(data);
            } else if (Objects.equals(column, "price")) {
                MainTableData = mainTableRepository.findAllByPrice(Integer.valueOf(data));
            }
        }
        catch (Exception ex) {return "redirect:/MainTable";}

        model.addAttribute("MainTableData", MainTableData);
        return "MainTable";
    }

}
