package com.futurice.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CalulatorServiceTest {

    @Test
    public void return_reverse_polish_notation_for_a_query() {
        List<String> rpn = CalulatorService.convertToRPN("2*(33/(33))-23*(23)");

        assertEquals(rpn, Arrays.asList("2", "33", "33", "/", "*", "23", "23", "*", "-"));
    }

    @Test
    public void return_computed_result_for_reversed_polish_notation_query() {
        List<String> rpnQuery = Arrays.asList("2", "33", "33", "/", "*", "23", "23", "*", "-");

        double result = CalulatorService.calculateFromRpn(rpnQuery);
        assertEquals(result, -527);
    }

    @Test
    public void throw_illegal_argument_exception_if_illegal_argument_is_passed() {
        assertThrows(IllegalArgumentException.class, ()-> CalulatorService.convertToRPN("8a+(2+9)"));
    }

}