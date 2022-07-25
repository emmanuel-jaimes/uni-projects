
import java.util.*;
import java.util.Scanner;

/**
 *  Driver class to run and generate PostFixEval objects and calculate post fix expressions
 */
public class PostFixEvalDriver {

    /**
     * @param args
     * main method to generate PostFixEval objects and calculate post fix expressions
     */
    public static void main(String[] args)
    {
        String expr;
        String again;   //decision
        double result;     //evaluated calculation

        Scanner input = new Scanner(System.in);

        do {
                System.out.println("Enter a valid post-fix expression. One character at a time with space in between each character: \n" +
                                            "Example: 5 2 + 1 7 3 - / * ");
                expr = input.nextLine();
                PostFixEval evaluator = new PostFixEval();

                result = evaluator.calculate(expr);
                System.out.println("Expression result equals: " + result);

                System.out.println("Another expression ? [yes/no]");
                again = input.nextLine();
                System.out.println();

        }
        while(again.equalsIgnoreCase("yes")); //not case sensitive, ignores case sensitivity
    }
}
