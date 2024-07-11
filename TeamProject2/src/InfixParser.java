import java.util.Stack;

public class InfixParser {

    public static void main(String[] args) throws Exception {
        String expression = "5 ^ 2 % 7 && (4 - 4) "; // Expression to be parsed and solved
        expression = expression.replaceAll("\\s", ""); // Removes all whitespace from expression
        int answer = parseExpression(expression);
        System.out.println("Expression: " + expression);
        System.out.println("Solution: " + answer);
    }

    /** Parses an infix expression and solves
        @param equation: expression to be parsed
        @return: Solution of parsed expression
    */
    public static int parseExpression(String equation) throws Exception {
        Stack<Operator> stackOperators = new Stack<>(); // Stack of operators
        Stack<Integer> stackNumbers = new Stack<>(); // Stack of operands
        StringBuilder sbCurrentNumber = new StringBuilder(); // Used to parse multi-digit numbers

        for (int i = 0; i < equation.length(); i++) {
            char currentCharacter = equation.charAt(i);
            if (Character.isDigit(currentCharacter)) { // Current Character is a digit
                sbCurrentNumber.append(currentCharacter); // Starts bulding multi-digit number
            } else { // Current character is not a digit
                if (sbCurrentNumber.length() > 0) { // Checks if an operand is in the string builder
                    stackNumbers.push(Integer.parseInt(sbCurrentNumber.toString())); // Converts the multi-digit String into an int and pushes to stack
                    sbCurrentNumber.setLength(0); // Clears the StringBuilder
                }
                if (currentCharacter == '(') {
                    stackOperators.push(new Operator("(")); // Add "(" to operand stack
                } else if (isOperator(currentCharacter)) { // Is Operator
                    String operatorStr = String.valueOf(currentCharacter); // Used to parse multi-character operators
                    if (i + 1 < equation.length() && Operator.isOperatorPair(currentCharacter, equation.charAt(i + 1))) { // Check for multi-character operators
                        operatorStr += equation.charAt(i + 1);
                        i++; // Move to the next character
                    }

                    Operator NewOperator = new Operator(operatorStr); // Creates new operator from operator string
                    while (!stackOperators.isEmpty() && stackOperators.peek().precedence >= NewOperator.precedence) { // If stack is not empty and current presedence is <= last operand in stack
                        evaluateOperation(stackOperators, stackNumbers); // Evaluate previous operation in stack
                    }
                    stackOperators.push(NewOperator);// Push current operator to stack
                } else if (currentCharacter == ')') { // Evaluate Parenthesis
                    while (!stackOperators.peek().symbol.equals("(")) { // Ends when finds "("
                        evaluateOperation(stackOperators, stackNumbers);
                    }
                    stackOperators.pop(); // Remove the opening "(" from the stack
                }
            }
        }

        // Push any remaning operands into stack
        if (sbCurrentNumber.length() > 0) {
            stackNumbers.push(Integer.parseInt(sbCurrentNumber.toString()));
        }
        // Final evaluation of the remaining operations in the stacks
        while (!stackOperators.isEmpty()) {
            evaluateOperation(stackOperators, stackNumbers);
        }

        return stackNumbers.pop(); // Solution to Expression
    }

    /** Checks if current character is an operator 
        @param testCharacter: Current character being tested
        @return: True if operator symbol, false if not
    */
    public static boolean isOperator(char testCharacter) {
        if (testCharacter == '+' || testCharacter == '-' || testCharacter == '*' || 
            testCharacter == '/' || testCharacter == '%' || testCharacter == '^' ||
            testCharacter == '&' || testCharacter == '|' || testCharacter == '<' || 
            testCharacter == '>' || testCharacter == '=' || testCharacter == '!') {
            return true;
        }
        return false;
    }

    /** Evaluates top operation on the stack
        @param stackOperators: Stack of Operators
        @param stackNumbers: Stack of Operands
    */
    public static void evaluateOperation(Stack<Operator> stackOperators, Stack<Integer> stackNumbers) throws Exception {
        int rightOperand = stackNumbers.pop();
        int leftOperand = stackNumbers.pop();
        Operator operator = stackOperators.pop();
        int solution = operator.evaluate(leftOperand, rightOperand);
        stackNumbers.push(solution);
    }
}