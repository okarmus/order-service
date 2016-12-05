package org.okarmus.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.okarmus.OrderServiceApplication
import org.okarmus.domain.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.hamcrest.Matchers.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by mateusz on 01.12.16.
 */

@WebAppConfiguration
@ContextConfiguration(classes = [OrderServiceApplication.class])
class OrderControllerTest extends Specification {

    MockMvc mockMvc

    @Autowired
    WebApplicationContext wac

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    def "should test if order can be added sucessfuly"() {
        setup:
            Order order = new Order(description: "sample description")

        expect: "check that there is no order with specified id"
            mockMvc.perform(get("/order/1")
                    .contentType(APPLICATION_JSON_UTF8))
                    .andExpect(status().isNoContent())

        and: "add order"
            def orderId = mockMvc.perform(post("/order")
                             .contentType(APPLICATION_JSON_UTF8)
                             .content(toJsonBytes(order)))
                             .andExpect(status().isCreated())
                             .andReturn().getResponse().getContentAsString();
            orderId != null

        and: "retrieve order and check that data are filled correctly"
            mockMvc.perform(get("/order/" + orderId)
                    .contentType(APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath('$.id',is(Integer.parseInt(orderId))))
                    .andExpect(jsonPath('$.description', is(order.description)))
    }


    public static byte[] toJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }
}
