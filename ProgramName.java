/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemsparser;

import java.util.ArrayList;




public class ProgramName extends Rule {

    public ProgramName(ArrayList<Token> programToken, int start, int end)
    {
        super(programToken, start, end);
    }

    @Override
    public boolean check() {
        if((this.end-this.start) == 0 && tokens.get(start).getMeaning() == 16)
            return true;
        else
            return false;
    }

    @Override
    public void generateCode() {
        Rule.writer.println(tokens.get(start).getSpecifier() + "\tSTART" + "\t0");
    }

}
