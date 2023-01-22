package br.com.davimonteiro;

import br.com.davimonteiro.domain.Sensor;
import br.com.davimonteiro.service.SensorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest()
class AppDemoApplicationTests {

    private static final String URL = "/api/sensors/";
    private static final MediaType contentType = MediaType.APPLICATION_JSON;
    private static MockMvc mvc;
    private MockHttpServletRequestBuilder request;
    private Sensor sensor;
    @Autowired
    public ObjectMapper mapper;
    @Autowired
    private SensorService sensorService;

    @BeforeAll
    public static void setUp(WebApplicationContext context) throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void updateTest() throws Exception {
        // Given that I have a sensor with ID equal to 1
        Sensor sensor = sensorService.findById(1L);

        // When I update the metric values of an existing sensor
        request = put(URL + sensor.getId())
                .content(toJson(sensor))
                .contentType(contentType);

        // Then I should receive a sensor containing the following metrics
        mvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void queryTest() throws Exception {
        List<Long> sensorIds = Arrays.asList(1L);
        List<String> metrics = Arrays.asList("Temperature");//Arrays.asList("Temperature", "Humidity", "Wind speed");

        request = get(URL)
                .params(convertToParameters("sensorIds", sensorIds))
                .params(convertToParameters1("metrics", metrics))
                .param("statistic", "min")
                .param("creationDate", LocalDate.now().toString())
                .contentType(contentType);

        mvc.perform(request).andExpect(status().isOk());
    }

    private String toJson(Object src) throws JsonProcessingException {
        return mapper.writeValueAsString(src);
    }

    private MultiValueMap<String, String> convertToParameters(String name, List<Long> list) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.put(name, convert(list));
        return parameters;
    }

    private MultiValueMap<String, String> convertToParameters1(String name, List<String> list) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.put(name, list);
        return parameters;
    }

    public List<String> convert(List<Long> oldList) {
        List<String> newList = new ArrayList<>();

        if (Objects.nonNull(oldList)) {
            for (Long myInt : oldList) {
                newList.add(String.valueOf(myInt));
            }
        }

        return newList;
    }

}
