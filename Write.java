/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemsparser;

import java.util.ArrayList;




public class Write extends Statement
{
    private int id = 0;
    private ArrayList < String > idNames;

    public Write(ArrayList<Token> tokens, int start, int end) {
        super(tokens, start, end);
        idNames = new ArrayList<>();
    }

    @Override
    public boolean check()
    {
        // check that first token is READ
        if ( tokens.get(start++).getMeaning() != 8 )
            return returnFalse();
        // check that second token is " ( "
        if ( tokens.get(start++).getMeaning() != 14 )
            return returnFalse();
        // check that the following tokens up till " ) " are a proper idList
        if ( !checkIdList() )
            return returnFalse();

        // check the last token is a closing parentheses and the one before it is an id
        if( tokens.get(end).getMeaning() != 15 || tokens.get(end-1).getMeaning() != 16 )
            return returnFalse();

        return true;
    }

    private boolean checkIdList( )
    {
        // check that the first item is a valid Id
        if( !checkValidId( tokens.get(start++) ) )
            return false;

        // id = 1 when it's supposed to be an id
        // id = 0 when it's supposed to be a comma
        int id=0;

        // to end to skip the closing parentheses
        for( int i=start ; i<end; i++)
        {
            if( id == 1 )
            {
                if ( !checkValidId( tokens.get(i) ) )
                    return returnFalse();
                // look for comma next
                id = 0;
            }
            // if it's supposed to be a comma, but it's not
            else if ( tokens.get(i).getMeaning() != 18 )
               return false;

                // if it's a comma, set so that the next token is supposed to be an id
            else if ( tokens.get(i).getMeaning() == 18 )
                id = 1;
        }
        return true;
    }

    public boolean checkValidId( Token id )
    {
        for( int i=0; i<IdList.arrId.size(); i++ )
        {
            if( id.getSpecifier().equals(IdList.arrId.get((i)).getSpecifier()))
            {
                idNames.add( IdList.arrId.get(i).getSpecifier() );
                return true;
            }
        }
        return false;
    }

    @Override
    public void generateCode()
    {
        // write the number of arguments
        Rule.writer.println("EXTREF\tXWRITE");
        Rule.writer.println("+JSUB\tXWRITE");
        Rule.writer.println("WORD"+"\t"+ String.valueOf(idNames.size() ) );
        for( int i=0; i<idNames.size(); i++ )
            Rule.writer.println("WORD"+"\t"+ idNames.get(i) );

    }

    public boolean returnFalse()
    {
        System.out.println("Read statement is invalid");
        return false;
    }

}
