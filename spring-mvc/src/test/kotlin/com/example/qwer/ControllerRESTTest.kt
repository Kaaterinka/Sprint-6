package com.example.qwer


import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.util.concurrent.ConcurrentHashMap


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ControllerRESTTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var service: Service

    private val headers = HttpHeaders()

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    @BeforeEach
    fun setUp() {
        service.addAddress(AddressBook("Testov", "Test", "test@mail.ru", "777"))
    }

    @Test
    fun create() {
        restTemplate.exchange(
            url("api/add"),
            HttpMethod.POST,
            HttpEntity<AddressBook>(AddressBook("NewS", "New", "new@mail.ru", "111"), headers),
            AddressBook::class.java
        )
        Assertions.assertEquals("NewS", service.findById(1)!!.surname)
    }

    @Test
    fun show() {
        val response = restTemplate.exchange(
            url("api/list"),
            HttpMethod.GET,
            HttpEntity(null, headers),
            ConcurrentHashMap::class.java
        )
        Assertions.assertNotNull(response.body)
    }

    @Test
    fun view() {
        val response = restTemplate.exchange(
            url("api/0/view"),
            HttpMethod.GET,
            HttpEntity<AddressBook>(null, headers),
            AddressBook::class.java
        )
        Assertions.assertEquals("Testov", response.body!!.surname)
    }

    @Test
    fun edit() {
        val response = restTemplate.exchange(
            url("api/0/edit"),
            HttpMethod.PUT,
            HttpEntity<AddressBook>(AddressBook("NewTestov", "Test", "test@mail.ru", "777"), headers),
            AddressBook::class.java
        )
        Assertions.assertEquals("NewTestov", response.body!!.surname)
    }

    @Test
    fun delete() {
        restTemplate.exchange(
            url("api/0/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, headers),
            AddressBook::class.java
        )
        Assertions.assertEquals(null, service.findById(0))
    }
}