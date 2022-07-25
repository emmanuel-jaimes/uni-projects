
import java.util.*;
import java.util.Scanner;

/**
 *  PostFixEval class that handles evaluating post fix expression as objects with methods to calculate those expressions
 */
public class PostFixEval {

    /**
     * final static char that represents exponent operation
     */
    private final static char EXPONENT = '^';
    /**
     * final static char that represents modulus operation
     */
    private final static char MOD = '%';
    /**
     * final static char that represents divide operation
     */
    private final static char DIVIDE = '/';
    /**
     * final static char that represents multiply operation
     */
    private final static char MULTIPLY = '*';
    /**
     * final static char that represents subtraction operation
     */
    private final static char SUBTRACT = '-';
    /**
     * final static char that represents addition operation
     */
    private final static char ADD = '+';

    /**
     *  values Stack object that holds Doubles and is used to evaluate expressions between operators
     */
    private final Stack<Double> values;

    /**
     * Constructor for PostFixEval expression and initializes stack object used to store values of
     */
    public PostFixEval()
    {
        values = new Stack<>();
    }

    /**
     * @param expression string representation of the entire postfix expression to be evaluated
     * @return double value representing the total value of the postfix expression
     */
    public double calculate(String expression) //evaluate
    {
        double op1, op2, result = 0;
        String t;
        Scanner scan = new Scanner(expression);

        while(scan.hasNext())
        {
            t = scan.next();

            if (isOperator(t))
            {
                op2 = values.pop();  //returns primitive type int
                op1 = values.pop();  //stacks can only hold object types

                result = evaluatePostfixExpression(t.charAt(0), op1, op2);

                values.push(result);
            }
            else
            {
                values.push(Double.parseDouble(t));
            }

        }
        return result;
    }

    /**
     * @param token string object used to determine if string of the character in the expression is an operator
     * @return true/false whether the string of the character passes the comparison checks of whether it is an operator
     */
    private boolean isOperator(String token)
    {
        return (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^") || token.equals("%"));
    }

    /**
     * @param operation the given operation to be operated on the two given operands within the expression
     * @param op1 the first input operand before the operator used to calculate segment of postfix expression
     * @param op2 the second input operand before the operator used to calculate segment of postfix expression
     * @return a double value representing the evaluation of the given postfix expression segment
     */
    private double evaluatePostfixExpression(char operation, double op1, double op2)  //evaluates single operations
    {
        double result = 0;
        switch (operation)
        {
            case ADD:
                result = op1 + op2;
                break;
            case SUBTRACT:
                result = op1 - op2;
                break;
            case MULTIPLY:
                result = op1 * op2;
                break;
            case DIVIDE:
                result = op1/op2;
                break;
            case MOD:
                result = op1%op2;
                break;
            case EXPONENT:
                result = Math.pow(op1, op2);
        }

        return result;
    }
}