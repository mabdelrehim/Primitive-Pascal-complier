/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemsparser;

/**
 *
 * @author Omar salem
 */
import java.util.ArrayList;
import java.util.Stack;

//InToPost taken from tutorials point and modified to match our code -- postfix evaluation taken from geeks for geeks
public class InToPost {
    private Stack<Token> theStack;
    private int start, end;
    private ArrayList<Token> tokens;
    private ArrayList<Token> postFix;

    public InToPost(ArrayList<Token> tokens, int start, int end) {
        this.start = start;
        this.end = end;
        this.tokens = tokens;
        this.postFix = new ArrayList<>();
        theStack = new Stack<>();
    }

    public ArrayList<Token> toPostFix() {
        for (int j = start; j <= end; j++) {
            int tokenType = tokens.get(j).getMeaning();
            switch (tokenType) {
                case 13:    //'+'
               // case '-':
                    gotOper(tokens.get(j), 1);
                    break;
                case 17:   //'*'
                //case '/':
                    gotOper(tokens.get(j), 2);
                    break;
                case 14:   //'('
                    theStack.push(tokens.get(j));
                    break;
                case 15:  //')'
                    gotParen(tokens.get(j));
                    break;
                default:
                    postFix.add(tokens.get(j));
                    break;
            }
        }
        while (!theStack.isEmpty()) {
            postFix.add(theStack.pop());
        }
        return postFix;
    }

    private void gotOper(Token opThis, int prec1) {
        while (!theStack.isEmpty()) {
            Token opTop = theStack.pop();
            if (opTop.getMeaning() == 14) {  //(ch == '(')
                theStack.push(opTop);
                break;
            } else {
                int prec2;
                if (opTop.getMeaning() == 13)  //(opTop == '+' || opTop == '-')
                    prec2 = 1;
                else
                    prec2 = 2;
                if (prec2 < prec1) {
                    theStack.push(opTop);
                    break;
                } else postFix.add(opTop);
            }
        }
        theStack.push(opThis);
    }

    private void gotParen(Token ch) {
        while (!theStack.isEmpty()) {
            Token chx = theStack.pop();
            if (chx.getMeaning() == 14)// (chx == '(')
                break;
            else postFix.add(chx);
        }
    }

    public String postFixToAssembly(ArrayList<Token> postFix) {
        boolean accumulator = false; // indicates whether the accumulator holds the value of last operation done or not.
        String output = "";
        Stack<Token> theStack = new Stack<>();
        for (int i = 0; i < postFix.size(); i++) {
            Token current = postFix.get(i);
            // if current is an operand push it to the stack
            if (current.getMeaning() == 16) {
                theStack.push(current);
            } else {
                int operation = current.getMeaning();
                if (!accumulator) {
                    Token val1 = theStack.pop();
                    output = output.concat("LDA\t" + val1.getSpecifier() + "\n\n");
                    accumulator = true;
                }
                Token val2 = theStack.pop();
                switch (operation) {
                    case 13:    //operation is +
                        output = output.concat("ADD\t" + val2.getSpecifier() + "\n\n");
                        break;
                    case 17:    // operation is *
                        output = output.concat("MULT\t" + val2.getSpecifier() + "\n\n");
                        break;
                    default:
                        System.out.println("Unsupported operation");
                        break;
                }
            }
        }

        return output;
    }
}