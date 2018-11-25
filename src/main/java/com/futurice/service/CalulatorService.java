package com.futurice.service;

import io.vavr.control.Try;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.function.BiFunction;

import static com.futurice.service.CalculatorOperator.BRACKET_START;

public class CalulatorService {

    public static List<String> convertToRPN(final String query) {

        String queryWithoutWhiteSpaces = query.replaceAll("\\s+","");
        final String[] tokens = queryWithoutWhiteSpaces.split("(?=[-/*+()])|(?<=[-/*+()])");

        final Stack<CalculatorOperator> operators = new Stack<>();
        final Queue<String> rpn = new LinkedList<>();
        for(String token: tokens) {
            if(isNumeric(token)) {
                rpn.add(token);
                continue;
            }

            CalculatorOperator operatorToken = CalculatorOperator
                    .findOperator(token)
                    .orElseThrow(IllegalArgumentException::new);

            switch (operatorToken) {
                case BRACKET_START:
                    operators.push(BRACKET_START);
                    break;
                case PLUS:
                case MINUS:
                case DIVIDE:
                case MULTIPLY:
                    while (operatorsIsNotEmpty(operators)
                            && topElementOfOperatorsGreaterThanEqualCurrentOperator(operators, operatorToken)) {
                        rpn.add(operators.pop().getOperator());
                    }
                    operators.push(operatorToken);
                    break;
                case BRACKET_CLOSE:
                    while (!BRACKET_START.getOperator().equals(operators.peek().getOperator())) {
                        rpn.add(operators.pop().getOperator());
                    }
                    operators.pop();
                    break;
            }

        }
        while (!operators.isEmpty()) {
            rpn.add(operators.pop().getOperator());
        }

        return (List<String>) rpn;
    }

    public static Double calculateFromRpn(final List<String> rpn) {

            Stack<Double> numbers = new Stack<>();

            for (String token : rpn) {
                if (isNumeric(token)) {
                    numbers.push(Double.parseDouble(token));
                    continue;
                }

                CalculatorOperator operatorToken = CalculatorOperator
                        .findOperator(token)
                        .orElseThrow(IllegalArgumentException::new);
                try {
                    switch (operatorToken) {
                        case PLUS:
                            calcSign(numbers, (n1, n2) -> n1 + n2);
                            break;
                        case MINUS:
                            calcSign(numbers, (n1, n2) -> n2 - n1);
                            break;
                        case MULTIPLY:
                            calcSign(numbers, (n1, n2) -> n2 * n1);
                            break;
                        case DIVIDE:
                            calcSign(numbers, (n1, n2) -> n2 / n1);
                            break;
                    }
                } catch (EmptyStackException ex) {
                    throw ex;
                }
            }
            return numbers.pop();
    }

    private static Stack<Double> calcSign(Stack<Double> numbers, BiFunction<Double, Double, Double> operation) {
        numbers.push(operation.apply(numbers.pop(), numbers.pop()));
        return numbers;
    }

    private static boolean topElementOfOperatorsGreaterThanEqualCurrentOperator(Stack<CalculatorOperator> operators, CalculatorOperator operatorToken) {
        return operatorToken.getPrecedence() <= operators.peek().getPrecedence();
    }

    private static boolean operatorsIsNotEmpty(Stack<CalculatorOperator> operators) {
        return !operators.empty();
    }

    private static boolean isNumeric(final String token) {
        return Try.of(() -> Integer.parseInt(token))
                .isSuccess();
    }
}
