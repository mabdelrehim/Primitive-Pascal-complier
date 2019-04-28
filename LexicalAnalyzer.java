/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemsparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;



public class LexicalAnalyzer {

    private File sourceFile;

    public LexicalAnalyzer(String pathName) {
        this.sourceFile = new File(pathName);
    }

    public ArrayList<Token> analyze() {
        Hashtable<Integer, ArrayList<Token>> tokenStream = new Hashtable<>();
        ArrayList<Token> outTokens = new ArrayList<>();
        try {
            Scanner sc = new Scanner(sourceFile);
            String line;
            int lineCtr = 0;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                line = line.trim();
                lineCtr++;
                ArrayList<Token> lineTokens = new ArrayList<>();
                char[] charArr = line.toCharArray();
                String lexeme = "";
                StringBuilder sb = new StringBuilder(lexeme);
                sb.append(charArr[0]);
                for (int i = 1; i < charArr.length; i++) {
                    char peek = charArr[i];
                    if (Token.tokensCoding.containsKey(String.valueOf(peek)) || peek == ' ' ||
                            i == charArr.length - 1 || peek == ':') {
                        if(i == charArr.length - 1 && !Token.tokensCoding.containsKey(String.valueOf(peek))) {
                            sb.append(peek);
                            if (!sb.toString().equalsIgnoreCase("")) {
                                lineTokens.add(new Token(sb.toString()));
                            }
                            sb.setLength(0);
                        } else if (Token.tokensCoding.containsKey(String.valueOf(peek))) {
                            // ba3d mo7awalat habd 3adeeda tam be7amd ellah tazbeet moshkelet
                            // el simicolon bl if statement de
                            if (!sb.toString().equalsIgnoreCase("")) {
                                lineTokens.add(new Token(sb.toString()));
                            }
                            lineTokens.add(new Token(String.valueOf(peek)));
                            sb.setLength(0);
                        } else if (peek == ':') {
                            if (!sb.toString().equalsIgnoreCase("")) {
                                lineTokens.add(new Token(sb.toString()));
                            }
                            lineTokens.add(new Token(":="));
                            sb.setLength(0);
                            i++;
                        } else {
                            if (!sb.toString().equalsIgnoreCase("")) {
                                lineTokens.add(new Token(sb.toString()));
                            }
                            sb.setLength(0);
                        }
                    } else {
                        sb.append(peek);
                    }
                }
                tokenStream.put(lineCtr, lineTokens);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Set<Integer> keys = tokenStream.keySet();

        // el array btetla3 btarteeb mota5alef fa zabataha
        for (Integer key: keys) {
            ArrayList<Token> line = tokenStream.get(key);
            for (int i = line.size() - 1; i >= 0; i--) {
                outTokens.add(line.get(i));
            }
        }

        ArrayList<Token> outStream = new ArrayList<>();
        for (int i = outTokens.size() - 1; i>=0; i--) {
            outStream.add(outTokens.get(i));
        }
        return outStream;
    }

}
