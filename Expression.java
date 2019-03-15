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
 
public class Expression extends Rule {
    private Rule factor1,factor2;
    private Token arithmeticOperation;
    private int plusIndex,productIndex,firstIndex,closeIndex = -1;
    private static ArrayList<Integer> visitedClosed;
    private Expression newExpression;
   
    public Expression(ArrayList<Token> tokens, int start, int end) {
        super(tokens, start, end);
    }
 
    @Override
    public boolean check()
    {  
        /* check if there are parenthesis
        if the first token is a closed parenthesis
        make sure that there is a closing parenthesis
        then create a new expression without the parenthesis and check it
        */
        if( tokens.get(start).getMeaning() == 14 )
        {
            closeIndex = super.searchToken(tokens, 15, start);
            if( closeIndex == -1 )
                return false;
            // if the closing parenthesis found is the last one, then there's only one expression inside
            else if ( closeIndex == end )
            {
                newExpression = new Expression(tokens, start+1, closeIndex-1);
                if( newExpression.check() )
                    return true;
                else
                    return false;
            }
        }
        // get the first available operation to split fromm
        firstIndex = getIndex( start );
        // if there's a close index, and it's not at the end, make sure to split after it
        if( closeIndex != -1 )
        {
            if( firstIndex < closeIndex )
            {
                // get the index after the close
                firstIndex = getIndex ( closeIndex );
            }
        }
       
        factor1 = new Factor(tokens, start, firstIndex-1 );
        if( factor1.check() )
        {
            factor2 = new Factor(tokens, firstIndex+1,end );
            if( factor2.check() )
                return true;
            else
                return false;
        }
        return false;
    }
 
    @Override
    public void generateCode()
    {
        //todo generate code for expression
        InToPost inToPost = new InToPost(tokens, start, end); //start and end are to be sent inclusive
        String code = inToPost.postFixToAssembly(inToPost.toPostFix());
        writer.println(code+ "\n");
    }
    private int getIndex(int begin )
        {
            plusIndex = super.searchToken(tokens, 13 , begin);
            productIndex = super.searchToken(tokens,17, begin);
       
            // if there's only a product
            if( plusIndex == -1 && productIndex != -1 )
                firstIndex = productIndex;
            // if there is only a plus
            else if( productIndex == -1 && plusIndex != -1 )
                firstIndex = plusIndex;
            // if there is neither
            else if( productIndex == -1 && plusIndex == -1 )
                return -1;
            else if( productIndex < plusIndex )
                firstIndex = productIndex;
            else
                firstIndex = plusIndex;
           
            return firstIndex;
        }
}