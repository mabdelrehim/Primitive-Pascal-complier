/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemsparser;

import java.util.Hashtable;



public class Token {

    public static final Hashtable <String, Integer> tokensCoding = new Hashtable<String, Integer>() {
        {
            put("PROGRAM", 1);
            put("VAR", 2);
            put("BEGIN", 3);
            put("END", 4);
            put("END.", 5);
            put("FOR", 6);
            put("READ", 7);
            put("WRITE", 8);
            put("TO", 9);
            put("DO", 10);
            put(";", 11);
            put(":=", 12);
            put("+", 13);
            put("(", 14);
            put(")", 15);
            put("id", 16);
            put("*", 17);
            put(",", 18);
        }
    };
    private int meaning;
    private String specifier;

    public Token(String lexeme) {
        this.specifier = lexeme;
        if(Token.tokensCoding.containsKey(lexeme)) {
            this.meaning = Token.tokensCoding.get(lexeme);
        } else {
            this.meaning = Token.tokensCoding.get("id");
        }
    }

    public int getMeaning() {
        return meaning;
    }

    public String getSpecifier() {
        return specifier;
    }
}
