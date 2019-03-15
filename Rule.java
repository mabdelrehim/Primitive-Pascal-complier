/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemsparser;

import java.io.File;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class Rule {
    protected int start;
    protected int end;
    protected ArrayList<Token> tokens;
    public static PrintWriter writer;

    static {
        try {
            File file = new File("Code.txt");
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Rule(ArrayList<Token> tokens, int start, int end) {
        this.tokens = tokens;
        this.start = start;
        this.end = end;
    }

    //useful method to  get the index of the first occurrence of a token in a token array
    protected int searchToken(ArrayList<Token> tokenArray, int requiredVal, int begin) {
        for (int i = begin; i < tokenArray.size(); i++) {
            if (tokenArray.get(i).getMeaning() == requiredVal) {
                return  i;
            }
        }
        return -1;
    }

    //check whether the input array matches the suspected rule
    public abstract boolean check();

    public abstract void generateCode();

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }
}
