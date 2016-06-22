/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package ngramexcel;
import java.util.*;
import java.lang.*;
import java.io.*;
import com.opencsv.*;



/**
 *
 * @author zhaozhengzheng
 */
public class Ngramexcel {  
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        //Create a new object-class Ngram;
    
    //start from Ngram, N=2;
    
    Ngram nGram = new Ngram(2);
    
    CSVReader reader = new CSVReader(new FileReader("/Volumes/Macintosh HD 2/OneDrive/SimStudent Project/Analysis (where we analyze data)/Study VI Tutoring Pattern (Joyce & Dan)/Grep Data (Dan 2016)/Grep_TQ_Switch.csv"));

    String [] nextLine; 
    List<String> id = new ArrayList<>();
    List<String> activity =new ArrayList<>();
    List<String> cond =new ArrayList<>();
    
    final int STUDENT_ID_COLUMN=0;
    final int STUDENT_ACTIVITY_COLUMN=5;
    final int STUDENT_COND_COLUMN=1;
    
    //studentidcol, 
    while ((nextLine = reader.readNext()) != null) {
        
        // nextLine[] is an array of values from the line
        id.add(nextLine[STUDENT_ID_COLUMN]);
        activity.add(nextLine[STUDENT_ACTIVITY_COLUMN]);
        cond.add(nextLine[STUDENT_COND_COLUMN]);
        
        //System.out.println(nextLine[0] + nextLine[1]);
    }
    
    /*System.out.println(id);
    System.out.println(activity);
    System.out.println(cond);*/
    
    System.out.println(cond.size());   //the value is from 1 to cond.size() as 0 is the column name;
    
    //String cond, String stu_ID, String action
    for(int i=1; i<cond.size();i++){
        
        nGram.add(cond.get(i), id.get(i), activity.get(i));
    
    }
    nGram.summary();   //can't delete;
    nGram.summaryTran();        // can't delete;
    nGram.condAllign();        // can't delete;
    
  //  System.out.println(nGram.summary());
    
    //System.out.println(nGram.sumHashRead);
     
    
    //System.out.println("Testlisttran"+nGram.listTran());
    //System.out.println("Testlistcon"+nGram.listcondHashAllign());
    //ArrayList<ArrayList<String>> output = new ArrayList<>(); 
    //output = (ArrayList<ArrayList<String>>) nGram.listTran();
    //output = (ArrayList<ArrayList<String>>) nGram.listcondHashAllign();
    
    //System.out.println(output);
   // System.out.println("TestconallignRead"+nGram.condHashAllignRead);
            
            
    //output test 
    CSVWriter writer = new CSVWriter(new FileWriter("/Volumes/Macintosh HD 2/OneDrive/SimStudent Project/Analysis (where we analyze data)/Study VI Tutoring Pattern (Joyce & Dan)/Ngram (Dan 2016)/2Gram_TQ_QT_Switch.csv"), ',');


    if(args.length==0){
        ArrayList<ArrayList<String>> output = new ArrayList<>(); 
        //output = (ArrayList<ArrayList<String>>) nGram.listTran();
        output = (ArrayList<ArrayList<String>>) nGram.listcondHashAllign();//information for each student
        for (int i=0;i<output.size();i++ ){
            String[] row = new String[output.get(i).size()];
            // row= output.get(i).;
            for(int j=0; j<output.get(i).size();j++){
                    row[j]=String.valueOf(output.get(i).get(j));
               
            }
            writer.writeNext(row); 
           
        }
        writer.close();  
        return;
    }
    if(args[0].equals("summary")){
        System.out.println("condition 2");
        ArrayList<ArrayList<String>> output = new ArrayList<>(); 
        output = (ArrayList<ArrayList<String>>) nGram.listTran();
        System.out.println("size"+output.size());
        for (int i=0;i<output.size();i++ ){
            String[] row = new String[output.get(i).size()];
            // row= output.get(i).;
            for(int j=0; j<output.get(i).size();j++){
                    row[j]=String.valueOf(output.get(i).get(j));
               
            }
            writer.writeNext(row); 
           
        }
        writer.close();  
    }
    
}
}
