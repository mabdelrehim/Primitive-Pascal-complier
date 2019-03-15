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
public class Factor extends Rule
{
    private Token id;
    private Rule expression;
    
    public Factor(ArrayList<Token> tokens, int start, int end)
    {
        super(tokens, start, end);
    }

    @Override
    public boolean check()
    {
        // check if it's one token only
        if( end-start == 0 )
        {
            // check if valid ID
            if( checkValidId( tokens.get(end) ) )
                return true;
            else
                return false;
        }
        // else, if expression, create and go check
        else
        {
            expression = new Expression( tokens, start, end );
            if ( expression.check() )
                return true;
        }
        return false;
    }

    @Override
    public void generateCode()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean checkValidId( Token id )
    {
        for( int i=0; i<IdList.arrId.size(); i++ )
            if( id.getSpecifier().equals(IdList.arrId.get((i)).getSpecifier()))
                return true;
        
        return false;
    }    
}
