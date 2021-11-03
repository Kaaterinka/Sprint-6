package com.example.qwer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

@Controller
@RequestMapping("/app")
class ControllerMVC @Autowired constructor(val service: Service) {

    @GetMapping("/add")
    fun showForm(): String = "form"

    @PostMapping("/add")
    fun create(@ModelAttribute addressBook: AddressBook, model: Model): String {
        service.addAddress(addressBook)
        model.addAttribute("information", "Information added")
        return "success"
    }

    @GetMapping("/list")
    fun show(@RequestParam(required = false) query: Map<String, String>, model: Model): String {
        model.addAttribute("information", service.showAll(query))
        return "show"
    }

    @GetMapping("/{id}/view")
    fun certainInformation(@PathVariable id: Int, model: Model): String {
        model.addAttribute("currentNote", service.findById(id))
        return "currentNote"
    }

    @GetMapping("/{id}/edit")
    fun updateAddress(@PathVariable id: Int, model: Model): String {
        model.addAttribute("currentNote", service.findById(id))
        return "update"
    }

    @PostMapping("/{id}/edit")
    fun saveUpdated(addressBook: AddressBook, model: Model, @PathVariable id: Int): String {
        service.update(id, addressBook)
        model.addAttribute("information", "Information updated")
        return "success"
    }

    @GetMapping("/{id}/delete")
    fun deleteAddress(@PathVariable id: Int, model: Model): String {
        service.delete(id)
        model.addAttribute("information", "Information deleted")
        return "success"
    }
}