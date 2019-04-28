/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemsparser;

import java.util.ArrayList;




public class Prog extends Rule {
    private Rule progName;
    private Rule idList;
    private Rule statementlist;

    public Prog(ArrayList<Token> tokens, int start, int end) {
        super(tokens, start, end);
    }

    /*checks for the required keywords in the prog rule and calculates the start end end of the tokens sub array
    * required for each rule it checks*/
    public boolean check() {
        /*conditions that must be met for this method to return true*/
        boolean firstCondition = false;
        boolean secondCondition = false;
        boolean thirdCondition = false;
        boolean requiredKeywords = true;

        int progNameStart = 0;
        int progNameEnd = 0;
        int idListStart = 0;
        int idListEnd = 0;
        int statementListStart = 0;
        int statementListEnd = 0;

        /*first check for the PROGRAM keyword*/
        if (tokens.get(start).getMeaning() == 1) {
            progNameStart = this.start + 1;
        } else {
            System.out.println("SYNTAX ERROR: missing keyword PROGRAM at the start of the program");
            requiredKeywords = false;
        }

        /*check first if the Token VAR exists*/
        if(searchToken(tokens, 2, progNameStart) != -1) {
            progNameEnd = searchToken(tokens, 2, progNameStart) ;
            // the start and end are inclusive
            progNameEnd--;
        } else {
            System.out.println("SYNTAX ERROR: expected VAR not found");
            requiredKeywords = false;
        }

        /*first condition is to check whether the rule progName is correct*/
        this.progName = new ProgramName(tokens, progNameStart, progNameEnd);
        firstCondition = progName.check();

        idListStart = progNameEnd + 2;
        /*look for keyword BEGIN*/
        if(searchToken(tokens,3,idListStart)!=-1) {
          idListEnd = searchToken(tokens,3,idListStart);
          idListEnd--;
        } else {
            System.out.println("SYNTAX ERROR: expected BEGIN not found");
            requiredKeywords = false;
        }

        /*second condition is to check whether the rule idList is correct*/
        this.idList = new IdList(tokens, idListStart, idListEnd);
        secondCondition = idList.check();

        statementListStart = idListEnd + 2;
        /*look for keyword END.*/
        if (searchToken(tokens, 5, statementListStart) != -1) {
            statementListEnd = searchToken(tokens, 5, statementListStart);
            statementListEnd--;
        } else {
            System.out.println("SYNTAX ERROR: expected END. not found");
            requiredKeywords = false;
        }

        /*third condition is to check the the rule statement list is correct*/
        this.statementlist = new StatementList(tokens, statementListStart, statementListEnd);
        thirdCondition = statementlist.check();

        return requiredKeywords && firstCondition && secondCondition && thirdCondition;
    }

    public void generateCode() {
        progName.generateCode();
        idList.generateCode();
        statementlist.generateCode();
        Rule.writer.close ();       
    }
}
