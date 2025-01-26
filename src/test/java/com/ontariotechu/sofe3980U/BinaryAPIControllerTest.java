package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
@WebMvcTest(BinaryAPIController.class)
public class BinaryAPIControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void add() throws Exception {
        this.mvc.perform(get("/add").param("operand1","111").param("operand2","1010"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("10001"));
    }
    @Test
    public void add2() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1","111").param("operand2","1010"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1010))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10001))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }

    //Three more test cases for the already implemented functions (/add and /add_json)

    //Very large numbers test

    @Test
    public void add_largeNumbers() throws Exception {
        this.mvc.perform(get("/add")
                        .param("operand1", "111111111111111111111")
                        .param("operand2", "101010101010101010101"))
                .andExpect(status().isOk())
                .andExpect(content().string("1101010101010101010100")); // Correct binary sum
    }

    //One operand is null test
    @Test
    public void one_null() throws Exception{
        this.mvc.perform(get("/add")
                .param("operand1", (String) null)
                .param("operand2", "111"))
                .andExpect(status().isOk())
                .andExpect(content().string("111"));
    }

    //Both operands are null test
    @Test
    public void add_bothOperandsNull() throws Exception {
        this.mvc.perform(get("/add")
                        .param("operand1", (String) null)
                        .param("operand2", (String) null))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    //Two tests for the and operator

    //Test the and function with two binary numbers of the same length
    @Test
    public void and() throws Exception{
        this.mvc.perform(get("/and").param("operand1","111").param("operand2","101"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("101"));

    }
    // Test for one operand is zero

    @Test
    public void and1() throws Exception{
        this.mvc.perform(get("/and").param("operand1","000").param("operand2","1000"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));

    }
    //Two tests for the or operator

    // Test for bitwise logical OR (edge case: both inputs are zero)
    @Test
    public void or() throws Exception{
        this.mvc.perform(get("/or").param("operand1","0").param("operand2","0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));

    }
    // Test for bitwise logical OR (normal case: different bits)
    @Test
    public void or1() throws Exception{
        this.mvc.perform(get("/or").param("operand1","101010").param("operand2","010101"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("111111"));

    }
    //Two tests for the multiply operator

    // Test for multiplication (normal case: both inputs are non-zero)
    @Test
    public void multiply() throws Exception{
        this.mvc.perform(get("/multiply").param("operand1","101").param("operand2","11"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1111"));

    }

    // Test for multiplication (edge case: different lengths)
    @Test
    public void multiply1() throws Exception{
        this.mvc.perform(get("/multiply").param("operand1","1").param("operand2","10101"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("10101"));

    }


}