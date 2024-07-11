import java.util.NoSuchElementException;

class Operator {
    String symbol;
    int precedence;

    // Constructor
    public Operator(String symbol) throws Exception { // Sets operator symbol and corresponding precedence
        this.symbol = symbol;
        switch (symbol) {
            case "^": // Operated first
                this.precedence = 7;
                break;
            case "*":
            case "/":
            case "%":
                this.precedence = 6;
                break;
            case "+":
            case "-":
                this.precedence = 5;
                break;
            case "<":
            case "<=":
            case ">":
            case ">=":
                this.precedence = 4;
                break;
            case "==":
            case "!=":
                this.precedence = 3;
                break;
            case "&&": // Logical and
                this.precedence = 2;
                break;
            case "||": // Logical or. Operated Last
                this.precedence = 1;
                break;
            case "(": // Operated only when corresponding closing ")" is found
                this.precedence = -1; 
                break;
            default: // Not an Operator
                throw new NoSuchElementException("Invalid operator: " + symbol);
        }
    }

    /** Evaluates the operators expression using passed through operands
        @param leftOperand: first number in the expression (2nd operand popped from stack)
        @param rightOperand: second number in the expression (from top of stack)
        @return: Solution of expression
        @throws NoSuchElementException: "Invalid operator: " + symbol
    */
    public int evaluate(int leftOperand, int rightOperand) throws Exception {
        switch (this.symbol) {
            case "+":
                return leftOperand + rightOperand;
            case "-":
                return leftOperand - rightOperand;
            case "*":
                return leftOperand * rightOperand;
            case "/":
                return leftOperand / rightOperand;
            case "^":
                return (int) Math.pow(leftOperand, rightOperand);
            case "%":
                return leftOperand % rightOperand;
            case "&&":
                return (leftOperand != 0 && rightOperand != 0) ? 1 : 0; // If both operands dont equal 0, return 1 else return 0
            case "||":
                return (leftOperand != 0 || rightOperand != 0) ? 1 : 0; // True if either operand is not 1
            case "<":
                return leftOperand < rightOperand ? 1 : 0; // If true return 1 if false return 0 (Boolean to Integer)
            case "<=":
                return leftOperand <= rightOperand ? 1 : 0;
            case ">":
                return leftOperand > rightOperand ? 1 : 0;
            case ">=":
                return leftOperand >= rightOperand ? 1 : 0;
            case "==":
                return leftOperand == rightOperand ? 1 : 0;
            case "!=":
                return leftOperand != rightOperand ? 1 : 0;
            default:
                throw new NoSuchElementException("Invalid operator: " + symbol);
        }
    }
    
    /** Checks if Operator could have a proceding operator ( >=, <=, ==, !=, &&, || )
        @param firstOp: Current Operator being parced
        @param secondOp: The following Character in the expression
        @return: True if matching pair is found
    */
    public static boolean isOperatorPair(char firstOp, char secondOp) {
        String pair = "" + firstOp + secondOp;
        return pair.equals("&&") || pair.equals("||") || pair.equals(">=") || pair.equals("<=") ||
               pair.equals("==") || pair.equals("!=");
    }
}