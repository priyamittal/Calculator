package com.futurice.service;

import java.util.Arrays;
import java.util.Optional;

public enum CalculatorOperator {
    PLUS("+",4),
    MINUS("-",4),
    MULTIPLY("*",5),
    DIVIDE("/",5),
    BRACKET_START("(",0),
    BRACKET_CLOSE(")",0);


    private String operator;
    private int precedence;
    CalculatorOperator(final String operator, final int precedence) {
        this.operator = operator;
        this.precedence = precedence;
    }
    public  int getPrecedence() {
        return this.precedence;
    }

    public  String getOperator() {
        return this.operator;
    }
    public static Optional<CalculatorOperator> findOperator(String token) {
        return Arrays.stream(CalculatorOperator.values())
                .filter(calculatorOperator -> calculatorOperator.getOperator().equals(token))
                .findFirst();
    }
}
