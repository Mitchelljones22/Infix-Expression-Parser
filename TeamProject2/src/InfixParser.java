import java.util.Stack;

public class InfixParser {

    public static void main(String[] args) throws Exception {
        String expression = "(6^2 + 8) / (22)";
        expression = expression.replaceAll("\\s", ""); // Removes all whitespace from expression
        int answer = parseExpression(expression);
        System.out.println("Expression: " + expression);
        System.out.println("Solution: " + answer);
    }

    public static int parseExpression(String equation) {
        Stack<Character> stackOperators = new Stack<>(); // Stacks Operators (+, -, *, /, ^, &&, ||, (, ) )
        Stack<Integer> stackNumbers = new Stack<>(); // Stacks Operands 
        StringBuilder sbCurrentNumber = new StringBuilder(); // Used to parse multi-digit numbers

        for (int i = 0; i < equation.length(); i++) {
            char currentCharacter = equation.charAt(i);
            if (Character.isDigit(currentCharacter)) {
                sbCurrentNumber.append(currentCharacter);
            } else { // Current Character is not a digit
                if (sbCurrentNumber.length() > 0) { // Checks an Operand is in the string builder
                    stackNumbers.push(Integer.parseInt(sbCurrentNumber.toString())); // Converts the multi-digit String into a int, and pushes to Stack
                    sbCurrentNumber.setLength(0); // Clears the StringBuilder
                }

                if (currentCharacter == '(') {
                    stackOperators.push(currentCharacter);
                } else if (isOperator(currentCharacter)) {
                    while (!stackOperators.isEmpty() && precedence(stackOperators.peek()) >= precedence(currentCharacter)) {
                        evaluateOperation(stackOperators, stackNumbers);
                    }
                    stackOperators.push(currentCharacter);
                } else if (currentCharacter == ')') {
                    while (stackOperators.peek() != '(') {
                        evaluateOperation(stackOperators, stackNumbers);
                    }
                    stackOperators.pop(); // Delete the opening '(' from the stack
                }
            }
        }

        // Final evaluation of the remaining operations in the stacks
        if (sbCurrentNumber.length() > 0) {
            stackNumbers.push(Integer.parseInt(sbCurrentNumber.toString()));
        }

        while (!stackOperators.isEmpty()) {
            evaluateOperation(stackOperators, stackNumbers);
        }

        return stackNumbers.pop();
    }

    public static boolean isOperator(char testCharacter) {
        char c = testCharacter;
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^' || c == '&' || c == '|';
    }

    public static int precedence(char operator) {
        switch (operator) {
            case '^':
                return 3;
            case '*':
            case '/':
            case '%':
                return 2;
            case '+':
            case '-':
                return 1;
            case '&':
            case '|':
                return 0; 
            default:
                return -1;
            // Need to add cases for ( >, >=, <, <=, ==, != )
            
        }
    }

    public static void evaluateOperation(Stack<Character> stackOperators, Stack<Integer> stackNumbers) {
        int rightOperand = stackNumbers.pop();
        int leftOperand = stackNumbers.pop();
        char operator = stackOperators.pop();
        int solution = 0;

        switch (operator) {
            case '+':
                solution = leftOperand + rightOperand;
                break;
            case '-':
                solution = leftOperand - rightOperand;
                break;
            case '*':
                solution = leftOperand * rightOperand;
                break;
            case '/':
                solution = leftOperand / rightOperand;
                break;
            case '^':
                solution = (int) Math.pow(leftOperand, rightOperand);
                break;
        }
        stackNumbers.push(solution);
    }
}
