package com.tw.apistackbase;

import com.tw.apistackbase.controller.Employee;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class EmployeControlerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAll() throws Exception {
        String result = mockMvc.perform(get("/employees")).andDo(print()).andReturn().getResponse().getContentAsString();
        Employee employee = new Employee(0,"李栋",20,"male");
        JSONObject jsonObject = new JSONObject((new LinkedHashMap<>()));
        jsonObject.put("id",employee.getId());
        jsonObject.put("name", employee.getName());
        jsonObject.put("age",employee.getAge());
        jsonObject.put("gender", employee.getGender());
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        JSONArray jsonArray1 = new JSONArray(result);
        Assertions.assertEquals(jsonArray.getJSONObject(0).get("id"),jsonArray1.getJSONObject(0).get("id"));
    }
}
