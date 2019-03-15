/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemsparser;

import java.util.ArrayList;

/**
 *
 * @author Omar salem
 */
public class Assign extends Statement 
{

    private Rule expression;
    private Token id;
    private Token equal;
    public Assign(ArrayList<Token> tokens, int start, int end) 
    {
        super(tokens, start, end);
    }

    public boolean check() {

        //check if first token is not an id return false and then advance start index by one
        if (tokens.get(start++).getMeaning() == 16) 
            id = tokens.get(start-1);
        else
            return false;
        //check whether the next token is :=
        if (tokens.get(start++).getMeaning() == 12)
            equal = tokens.get(start-1);
        else
            return false;

        //start index should be now pointing to the beginning of an expression that ends at the end of assign
        this.expression = new Expression(tokens, start, end);
        return expression.check();
    }

    public void generateCode() 
    {
        expression.generateCode();
        Rule.writer.println("STA\t"+id.getSpecifier() );
    }
}