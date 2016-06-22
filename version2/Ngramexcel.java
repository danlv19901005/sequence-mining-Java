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
 * @author Dan Lv
 */
public class Ngramexcel {  
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        //Create a new object-class Ngram;
    
    //start from Ngram, N=2;
    
    int STUDENT_ID_COLUMN=0;
    int STUDENT_ACTIVITY=2;
    
    CSVReader reader = new CSVReader(new FileReader("/Volumes/Macintosh HD 2/OneDrive/SimStudent Project/Analysis (where we analyze data)/Study VI Tutoring Pattern (Joyce & Dan)/Ngram (Dan 2016)/TQ_improved2/Grep_TQ_Switch.csv"));
    String [] nextLine; 
    String temp="";
    List<String> id = new ArrayList<>();
    List<String> activity = new ArrayList<>();
    nextLine = reader.readNext();
    id.add(nextLine[STUDENT_ID_COLUMN]);
    int index=0;



    while ((nextLine = reader.readNext()) != null) {
        if(id.get(index).equals(nextLine[STUDENT_ID_COLUMN])){
            temp+=nextLine[STUDENT_ACTIVITY];
        }
        else{
            index++;
            id.add(nextLine[STUDENT_ID_COLUMN]);
            activity.add(temp);
            temp="";
        }
    }
    activity.add(temp);

    for(int i=0;i<id.size();i++){
        System.out.println(id.get(i)+","+activity.get(i));
    }
    }
}








