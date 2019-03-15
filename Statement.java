/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemsparser;

import java.util.ArrayList;


public class Statement extends Rule{

    // a single reference to point at one of the 3 possible types of statements
    // will insert into arrayList with type Rule ( in statementList class ) so that we can have each one generate it's own code
    private Statement operation;



    // new statements indexes start at the first token and end before the semi colon ( inclusive )
    public Statement(ArrayList<Token> tokens, int start, int end)
    {
        super(tokens, start, end);
    }

    @Override
    public boolean check()
    {
        // if it's Assign, create a new Assign object
        if( checkAssign() )
            operation = new Assign( tokens, start, end );

        else if ( checkRead() )
            operation = new Read( tokens, start, end );

        else if( checkWrite())
            operation = new Write( tokens, start, end );
        else
            return false;


        // have the new Rule object check if it's valid
        if ( operation.check() )
        {
            return true;
        }
        else
            return false;
    }


    private boolean checkAssign()
    {
        // search for " := " token
        for (int i = start ; i <= end; i++)
            if ( tokens.get(i).getMeaning() == 12 )
                return  true;

        return false;
    }
    private boolean checkWrite()
    {
        // search for " Write " token
        for (int i = start ; i <= end ; i++)
            if ( tokens.get(i).getMeaning() == 8 )
                return  true;

        return false;
    }

    private boolean checkRead()
    {
        // search for " Read " token
        for (int i = start ; i <= end ; i++)
            if ( tokens.get(i).getMeaning() == 7 )
                return  true;

        return false;
    }

    @Override
    public void generateCode()
    {
        operation.generateCode();
    }


}
