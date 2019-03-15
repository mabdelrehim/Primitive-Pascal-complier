/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemsparser;

import java.util.ArrayList;





public class StatementList extends Rule {

    private StatementList statementList;
    private Statement statement;
    private int statementStart,statementEnd; // to use when dividing into another statement, and statement list

    // array list to hold the statements to use later to generate code
    private static ArrayList < Statement > statements;
    static
    {
        statements = new ArrayList< Statement >();
    }



    // statementList indexes start at the first token of the first statement and end at the semi colon of the last statement ( inclusive of the semi colon )
    public StatementList(ArrayList<Token> tokens, int start, int end)
    {
        super(tokens, start, end);
    }

    @Override
    public boolean check()
    {
        statementStart = start;
        // get the index of the next semi-colon ( end of the first statement )
        statementEnd = super.searchToken(tokens, 11 , start);

        // check if it's one statement only
        if ( checkProduct1() )
            return true;
        else if( checkProduct2() )
            return true;
        else
            return false;
    }

    private boolean checkProduct1()
    {
        // if the end of the first statement is the same as the end of the statement list, then it's only one statement
        if( statementEnd == end )
        {
            // create new statement ( inclusive without semi colon )
            statement = new Statement( tokens, statementStart, statementEnd-1 );
            if ( statement.check() )
            {
                statements.add(statement);
                return true;
            }
        }
        return false;
    }

    private boolean checkProduct2()
    {
        // split into statement and statement list
        statement = new Statement( tokens, statementStart, statementEnd-1 ); // without semi colon
        statementList = new StatementList( tokens, statementEnd + 1 , end ); // with semi colon in last statement

        if( !statement.check() )
            return false;
        
        else if( !statementList.check() )
            return false;
        else
        {
            statements.add(statement);
            return true;
        }

    }


    @Override
    public void generateCode()
    {
        for( int i=statements.size()-1 ; i>-1 ; i-- )
            statements.get(i).generateCode();
    }


}
