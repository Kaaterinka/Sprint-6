package com.example.qwer

import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTest {
    @Autowired
    private lateinit var context: WebApplicationContext
    private lateinit var mvc: MockMvc

    @BeforeEach
    fun setUp() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder?>(springSecurity())
            .build()
    }

    @WithMockUser(username = "app", password = "app")
    @Test
    fun addAddress() {
        mvc.perform(
            post("/app/add").param("surname", "Testov").param("name", "Testov")
                .param("email", "test@mail.ru").param("phone", "777")
                .with(csrf())
        ).andExpect(status().isOk).andExpect(view().name("success"))
    }

    @WithMockUser(username = "api", password = "api", roles = ["API"])
    @Test
    fun showList() {
        mvc.perform(get("/api/list")).andExpect(status().isOk)
    }

    @WithMockUser(username = "app", password = "app")
    @Test
    fun checkAccess() {
        mvc.perform(get("/api/list").with(csrf()))
            .andExpect(status().isForbidden)
    }

    @WithMockUser(username = "admin", password = "admin", roles = ["ADMIN"])
    @Test
    fun adminDeleteAddress() {
        mvc.perform(get("/app/0/delete").with(csrf()))
            .andExpect(status().isOk)
            .andExpect(view().name("success"))
    }

    @WithMockUser(username = "app", password = "app")
    @Test
    fun notAccessDeleteAddress() {
        mvc.perform(get("/api/0/delete").with(csrf()))
            .andExpect(status().isForbidden)
    }

}