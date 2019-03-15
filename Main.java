package systemsparser;

import java.util.ArrayList;

public class Main {

        public static void main(String[] args)
        {
            // write your code here
            LexicalAnalyzer analyzer = new LexicalAnalyzer("test.txt");
            ArrayList<Token> tokenStream = analyzer.analyze();
            Rule prog = new Prog(tokenStream, 0, tokenStream.size() - 1);
            boolean correct = prog.check();
            System.out.println(correct);
            if (correct)
                prog.generateCode();

        }
}
