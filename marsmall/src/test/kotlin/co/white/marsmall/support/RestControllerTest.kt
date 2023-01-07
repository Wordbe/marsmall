package co.white.marsmall.support

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockHttpServletRequestDsl
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.ContentResultMatchersDsl

abstract class RestControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    fun MockHttpServletRequestDsl.jsonRequest(value: Any) {
        contentType = MediaType.APPLICATION_JSON
        content = objectMapper.writeValueAsString(value)
    }

    fun ContentResultMatchersDsl.jsonResponse(value: Any) {
        return json(objectMapper.writeValueAsString(value))
    }
}
