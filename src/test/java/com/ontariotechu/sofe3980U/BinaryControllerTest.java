package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.runner.RunWith;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BinaryController.class)
public class BinaryControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void getDefault() throws Exception {
        this.mvc.perform(get("/"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("calculator"))
                .andExpect(model().attribute("operand1", ""))
                .andExpect(model().attribute("operand1Focused", false));
    }

    @Test
    public void getParameter() throws Exception {
        this.mvc.perform(get("/").param("operand1","111"))
                .andExpect(status().isOk())
                .andExpect(view().name("calculator"))
                .andExpect(model().attribute("operand1", "111"))
                .andExpect(model().attribute("operand1Focused", true));
    }
    @Test
    public void postParameter() throws Exception {
        this.mvc.perform(post("/").param("operand1","111").param("operator","+").param("operand2","111"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1110"))
                .andExpect(model().attribute("operand1", "111"));
    }

    // Add 3 more test cases for initial web services

    //Empty Operands in POST request
    @Test
    public void postEmptyOperands() throws Exception {
        this.mvc.perform(post("/")
                        .param("operand1", "")
                        .param("operator", "+")
                        .param("operand2", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "0")) // Assuming empty operands default to 0
                .andExpect(model().attribute("operand1", ""));
    }

    //No selected operator

    @Test
    public void postMissingOperator() throws Exception {
        this.mvc.perform(post("/")
                        .param("operand1", "1010")
                        .param("operand2", "110"))
                .andExpect(status().isOk())
                .andExpect(view().name("Error"));
    }

    //All fields are blank (operands and operator) in POST request
    @Test
    public void allEmpty() throws Exception {
        this.mvc.perform(post("/")
                        .param("operand1", "")
                        .param("operator", "")
                        .param("operand2", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("Error"));
    }

    //Two tests for the and operator
    // Test for bitwise logical AND (all bits are ones)
    @Test
    public void postAndOperator() throws Exception {
        this.mvc.perform(post("/").param("operand1","1111").param("operator","&").param("operand2","1111"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1111"))
                .andExpect(model().attribute("operand1", "1111"));
    }
    // Test for bitwise logical AND (normal case: some matching bits)
    @Test
    public void postAndOperator1() throws Exception {
        this.mvc.perform(post("/").param("operand1","1010").param("operator","&").param("operand2","1100"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1000"))
                .andExpect(model().attribute("operand1", "1010"));
    }


    //Two tests for the or operator
    // Test for bitwise logical OR (normal case: different bits)
    @Test
    public void postOrOperator() throws Exception {
        this.mvc.perform(post("/").param("operand1","101010").param("operator","|").param("operand2","010101"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "111111"))
                .andExpect(model().attribute("operand1", "101010"));
    }
    // Test for bitwise logical OR (unequal lengths: padding required)
    @Test
    public void postOrOperator1() throws Exception {
        this.mvc.perform(post("/").param("operand1","101").param("operator","|").param("operand2","11"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "111"))
                .andExpect(model().attribute("operand1", "101"));
    }

    //Two tests for the multiply operator
    // Test for multiplication (edge case: one input is zero)
    @Test
    public void postMultiplyOperator() throws Exception {
        this.mvc.perform(post("/").param("operand1","0").param("operator","*").param("operand2","111"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "0"))
                .andExpect(model().attribute("operand1", "0"));
    }
    // Test for multiplication (edge case: different lengths)
    @Test
    public void postMultiplyOperator1() throws Exception {
        this.mvc.perform(post("/").param("operand1","1").param("operator","*").param("operand2","10101"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "10101"))
                .andExpect(model().attribute("operand1", "1"));
    }

}