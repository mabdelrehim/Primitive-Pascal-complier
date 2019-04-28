/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemsparser;



import java.util.ArrayList;

public class Expression extends Rule {
    private Rule factor1,factor2;
    private Token arithmeticOperation;
    private int plusIndex,productIndex,firstIndex;
    
    public Expression(ArrayList<Token> tokens, int start, int end) {

        super(tokens, start, end);
    }

    @Override
    public boolean check() {
        // look for +
        plusIndex = super.searchToken(tokens, 13 , start);
        productIndex = super.searchToken(tokens,17,start);
        
        // if there's only a product
        if( plusIndex == -1 && productIndex != -1 )
            firstIndex = productIndex;
        // if there is only a plus
        else if( productIndex == -1 && plusIndex != -1 )
            firstIndex = plusIndex;
        // if there is neither
        else if( productIndex == -1 && plusIndex == -1 )
            return false;
        else if( productIndex < plusIndex )
            firstIndex = productIndex;
        else
            firstIndex = plusIndex;
        
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
    public void generateCode() {
        //todo generate code for expression
        InToPost inToPost = new InToPost(tokens, start, end); //start and end are to be sent inclusive
        String code = inToPost.postFixToAssembly(inToPost.toPostFix());
        writer.println(code);
    }
}