package com.example.qwer

import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
internal class ControllerMVCTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var service: Service

    @BeforeEach
    fun setUp() {
        service.addAddress(AddressBook("Testov", "Test", "test@mail.ru", "777"))
    }

    @Test
    fun showForm() {
        mockMvc.perform(get("/app/add")).andExpect(status().isOk).andExpect(view().name("form"))
    }

    @Test
    fun create() {
        mockMvc.perform(
            post("/app/add").param("surname", "Testov").param("name", "Testov")
                .param("email", "test@mail.ru").param("phone", "777")
        )
            .andExpect(status().isOk).andExpect(view().name("success"))
    }

    @Test
    fun show() {
        mockMvc.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(view().name("show"))
            .andExpect(content().string(containsString("Testov")))
    }

    @Test
    fun certainInformation() {
        mockMvc.perform(get("/app/0/view"))
            .andExpect(status().isOk)
            .andExpect(view().name("currentNote"))
            .andExpect(content().string(containsString("Testov")))
    }

    @Test
    fun updateAddress() {
        mockMvc.perform(get("/app/0/edit"))
            .andExpect(status().isOk)
            .andExpect(view().name("update"))
    }

    @Test
    fun saveUpdated() {
        mockMvc.perform(
            post("/app/0/edit")
                .param("surname", "Change").param("name", "Testov")
                .param("email", "test@mail.ru").param("phone", "777")
        )
            .andExpect(status().isOk)
            .andExpect(view().name("success"))
    }

    @Test
    fun deleteAddress() {
        mockMvc.perform(get("/app/0/delete")).andExpect(status().isOk).andExpect(view().name("success"))
    }
}