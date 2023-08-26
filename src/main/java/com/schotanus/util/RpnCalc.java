/*
 * Copyright (C) 2012 Kees Schotanus
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.schotanus.util;

import java.io.Console;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;


/**
 * Basic RPN (Reverse Polish Notation) calculator.<br>
 * For an explanation of RPN, check out the article at:
 * <a href="http://en.wikipedia.org/wiki/Reverse_Polish_notation">Wikipedia</a><br>
 * An RPN calculator is stack based.
 * Let's examine the evaluation of the expression: "3 4 - 5 + sqrt".
 * The following steps are performed in order:
 * <ol>
 *   <li>3 is pushed on the stack</li>
 *   <li>4 is pushed on the stack</li>
 *   <li>The "-" operator is encountered</li>
 *   <li>pop the right operand (4) from the stack</li>
 *   <li>pop the left operand (3) from the stack</li>
 *   <li>push the result of "3 - 4" on the stack</li>
 *   <li>5 is pushed on the stack</li>
 *   <li>The "+" operator is encountered</li>
 *   <li>pop the right operand (5) from the stack</li>
 *   <li>pop the left operand (-1) from the stack</li>
 *   <li>push the result of "-1 + 5" on the stack</li>
 *   <li>The "sqrt" operator is encountered</li>
 *   <li>pop the operand (4) from the stack</li>
 *   <li>push the result of sqrt(4) on the stack</li>
 * </ol>
 * The stack should now contain the single value "2.0".<br>
 * Note: Besides the basic operators +, - , *, /, this class supports most methods of {@link Math} as operators.
 * So exp is a method on class Math and hence "exp" is also the name of a supported operator.
 */
public final class RpnCalc {

    /**
     * Set of commands (operators without operands).
     */
    private static final Set<String> commands = Set.of("ac");

    /**
     * The map of supported unary operators.
     */
    private static final Map<String, DoubleUnaryOperator> unaryOperators = new HashMap<>();

    /**
     * The map of supported binary operators.
     */
    private static final Map<String, DoubleBinaryOperator> binaryOperators = new HashMap<>();

    /**
     * The stack storing all the operands.
     */
    private final Deque<Double> stack = new ArrayDeque<>();

    // Store all unary operators in the map of unary operators.
    static {
        unaryOperators.put("abs", Math::abs);
        unaryOperators.put("acos", Math::acos);
        unaryOperators.put("asin", Math::asin);
        unaryOperators.put("atan", Math::atan);
        unaryOperators.put("cbrt", Math::cbrt);
        unaryOperators.put("ceil", Math::ceil);
        unaryOperators.put("cos", Math::cos);
        unaryOperators.put("cosh", Math::cosh);
        unaryOperators.put("exp", Math::exp);
        unaryOperators.put("expm1", Math::expm1);
        unaryOperators.put("floor", Math::floor);
        unaryOperators.put("log", Math::log);
        unaryOperators.put("log10", Math::log10);
        unaryOperators.put("rint", Math::rint);
        unaryOperators.put("round", Math::round);
        unaryOperators.put("sin", Math::sin);
        unaryOperators.put("sinh", Math::sinh);
        unaryOperators.put("sqrt", Math::sqrt);
        unaryOperators.put("tan", Math::tan);
        unaryOperators.put("tanh", Math::tanh);
        unaryOperators.put("toDegrees", Math::toDegrees);
        unaryOperators.put("toRadians", Math::toRadians);
    }

    // Store all binary operators in the map of binary operators.
    static {
        binaryOperators.put("atan2", Math::atan2);
        binaryOperators.put("hypot", Math::hypot);
        binaryOperators.put("max", Math::max);
        binaryOperators.put("min", Math::min);
        binaryOperators.put("pow", Math::pow);

        binaryOperators.put("+", Double::sum);
        binaryOperators.put("-", (left, right) -> left - right);
        binaryOperators.put("*", (left, right) -> left * right);
        binaryOperators.put("/", (left, right) -> left / right);
    }

    /**
     * Evaluates the supplied expression which should be written in RPN notation.<br>
     * A valid expression would be: "3 4 - 5 + sqrt".
     * The result is: sqrt((3 - 4) + 5) = 2.
     * @param expression The expression to evaluate.<br>
     *  Operands and operators should be separated by spaces or tabs.
     * @return The result of evaluating the expression.
     */
    public double evaluate(final String expression) {
        double result = 0;

        final String[] tokens = expression.split("\\s");
        for (final String token : tokens) {
            if (commands.contains(token)) {
                processCommand(token);
            } else if (unaryOperators.containsKey(token)) {
                result = evaluateUnaryOperator(unaryOperators.get(token));
            } else if (binaryOperators.containsKey(token)) {
                result = evaluateBinaryOperator(binaryOperators.get(token));
            } else {
                result = Double.parseDouble(token);
                this.stack.push(result);
            }
        }

        return result;
    }

    /**
     * Process the supplied command.
     * @param command The command to process.
     */
    private void processCommand(final String command) {
        if ("ac".equals(command)) {
            stack.clear();
        }
    }

    /**
     * Evaluates the supplied unaryOperator.
     * @param unaryOperator The unary operator to evaluate.
     * @return The result of evaluating the unary expression.
     */
    private double evaluateUnaryOperator(final DoubleUnaryOperator unaryOperator) {
        if (stack.isEmpty()) {
            throw new IllegalStateException(
                    String.format("The stack does not contain enough operands for unary operator: %s", unaryOperator));
        }

        final double result = unaryOperator.applyAsDouble(this.stack.pop());
        this.stack.push(result);

        return result;
    }

    /**
     * Evaluates the supplied binaryOperator.
     * @param binaryOperator The binary operator to evaluate.
     * @return The result of evaluating the binary expression.
     */
    private double evaluateBinaryOperator(final DoubleBinaryOperator binaryOperator) {
        if (stack.size() < 2) {
            throw new IllegalStateException(
                    String.format("The stack does not contain enough operands for the binary operator: %s",  binaryOperator));
        }

        final double rightOperand = this.stack.pop();
        final double leftOperand = this.stack.pop();

        final double result = binaryOperator.applyAsDouble(leftOperand, rightOperand);
        this.stack.push(result);
        return result;
    }

    /**
     * Command line version of this calculator.
     * <br>Simply enter an RPN expression and press enter to evaluate it.
     * Expressions can be entered using multiple arguments like this:<br>
     * 3 4 - 5 + sqrt<br>
     * Or as a single argument using quotes, like this: "3 4 - 5 + sqrt".
     * @param args Optional RPN expression<br>
     *  When present the command line arguments will be evaluated and no further user input is asked.
     */
    public static void main(final String[] args) {
        final Console console = System.console();
        if (console != null) {
            if (args.length == 0) {
                evaluateInputFromConsole(console);
            } else {
                evaluateCommandLineArguments(console, args);
            }

        } else {
            System.err.println("No console available!");
        }
    }

    /**
     * Evaluates RPN expressions entered at the console.
     * @param console The console accepting the RPN expressions.
     */
    private static void evaluateInputFromConsole(final Console console) {
        assert console != null : "Console may not be null!";

        final RpnCalc calculator = new RpnCalc();
        console.printf("%s, please enter an rpn expression: ", RpnCalc.class.getSimpleName());

        String expression = console.readLine();
        while (expression != null) {
            console.printf("%f\n", calculator.evaluate(expression));
            expression = console.readLine();
        }
    }

    /**
     * Evaluates the command line arguments.
     * @param console The console to write the result of the evaluation to.
     * @param args The command line arguments to evaluate.
     */
    private static void evaluateCommandLineArguments(final Console console, final String[] args) {
        assert console != null : "Console may not be null!";
        assert args != null : "Command line arguments are missing!";

        final RpnCalc calculator = new RpnCalc();

        for (final String argument : args) {
            console.printf("%f\n", calculator.evaluate(argument));
        }
    }

}
