import java.util.Stack;

public class InfixParser {
    
    public static void main(String[] args) throws Exception {
        
        String Expression = new String("5 + 1");
        Expression = Expression.replaceAll("\\s", "");// Removes all whitespace from expression
        System.out.println(Expression);
        int Answer = ParseExpression(Expression);
 
    }

    public static int ParseExpression(String Equation){
        Stack<Character> StackOperators = new Stack<Character>(); // Stacks Operators (+, -, *, /, ^, &&, ||, (, ) )
        Stack<Integer> StackNumbers = new Stack<Integer>(); // Stacks Operands 
        StringBuilder sbCurrentNumber = new StringBuilder(); // Used to parse multi-digit numbers

        
        for (int i = 0; i < Equation.length(); i++){
            char CurrentCharacter = Equation.charAt(i);
            if (Character.isDigit(CurrentCharacter)){
                sbCurrentNumber.append(CurrentCharacter);
            }
            else{ // Current Character is not a digit
                if (sbCurrentNumber.length() > 0) { // Checks an Operand is in the string builder
                    StackNumbers.push(Integer.parseInt(sbCurrentNumber.toString())); // Converts the multi-digit String into a int, and pushes to Stack
                    sbCurrentNumber.setLength(0);// Clears the StringBuilder
                }

                if (CurrentCharacter == '('){
                    StackOperators.push(CurrentCharacter);

                }
                else if (isOperator(CurrentCharacter)){
                    StackOperators.push(CurrentCharacter);

                } 

            }
        }
        return 1;
    }

    public static boolean isOperator(char testCharacter){
        char c = testCharacter;
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^' || c == '&' || c == '|' ;
    }
}
