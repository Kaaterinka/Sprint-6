package com.example.qwer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("/api")
class ControllerREST @Autowired constructor(val service:Service) {

    @PostMapping("/add")
    fun create(@RequestBody addressBook: AddressBook){
       return service.addAddress(addressBook)
    }

    @GetMapping("/list")
    fun show(@RequestParam(required = false) query: Map<String, String>):ConcurrentHashMap<Int, AddressBook>{
        return service.showAll(query)
    }

    @GetMapping("/{id}/view")
    fun view(@PathVariable id:Int):AddressBook?{
        return service.findById(id)
    }

    @PatchMapping("{id}/edit")
    fun edit(@PathVariable id:Int, @RequestBody addressBook: AddressBook){
        return service.update(id, addressBook)
    }

    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id:Int){
        return service.delete(id)
    }
}

