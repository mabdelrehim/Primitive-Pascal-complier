/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemsparser;


import java.util.ArrayList;


public class IdList extends Rule {

    //defined id's in var will be put in arrId to be a reference when find id in stmt
    public static ArrayList<Token> arrId=new ArrayList<Token>();
    public static ArrayList<Token> arrIdForRead=new ArrayList<Token>();
    public static ArrayList<Token> arrIdForWrite=new ArrayList<Token>();

    public IdList(ArrayList<Token> programToken, int start, int end)
    {
        super(programToken,start,end);
    }

    @Override
    public boolean check() {
        //check if only one id put it in reference array of tokens "RESW"
        //(end-1) bec: end stands at after actual end //changed the input end to look at actual end
        if(  (end-start)==0 && tokens.get(start).getMeaning()== 16)
        {
            arrId.add(tokens.get(start));
            return true;
        }
        //more than one id
        else if(((end)-start) !=0)
        {   //end-2 to check","
            if(tokens.get((end)).getMeaning()==16 && tokens.get((end-1)).getMeaning()== 18)
            {
                arrId.add(tokens.get(end));
                Rule idList=new IdList(tokens, start, end-2);
                return idList.check();
            }
            else if(tokens.get((end)).getMeaning()!=16)
            {
                System.out.println("Syntax Error: expected identifier not found");
                return false;
            }
            else
            {
                System.out.println("Syntax Error: expected , not found");
                return false;
            }
        }
        else
        {
            System.out.println("Syntax Error: expected identifier not found");
            return false;
        }

    }

    @Override
    public void generateCode() {
        for (int i = 0; i < arrId.size(); i++) {
            writer.println(arrId.get(i).getSpecifier() + "\tRESW" + "\t1");
        }

    }

}
