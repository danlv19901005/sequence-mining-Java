/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package ngramexcel;

import java.util.*;
import java.lang.*;
import java.io.*;

/**
 *
 * @author zhaozhengzheng
 */
public class Ngram {

    //N is the length of Ngram;
    int N;
    //transit shows the new sequence;
    String[] transit;

    //Map<String, Integer> nGramHash;
    // HashMap<String, Integer> nGramHash = new HashMap<>();
    //New hashmap to store StuID and nGramHash for each student;
    HashMap<String, HashMap<String, HashMap<String, Double>>> condHashMap = new HashMap<>();
    
    HashMap<String, HashMap<String, HashMap<String, Double>>> condHashAllignRead = new HashMap<>();
  //HashMap<String, HashMap<String, HashMap<String, Double>>> condHashAllign = new HashMap<>();

    HashMap<String, HashMap<String, Double>> sumHashRead = new HashMap<>();
    HashMap<String, HashMap<String, Double>> sumTranHashRead = new HashMap<>();
    
    //HashMap<String, HashMap<String, Integer>> idHashMap = new HashMap<>();

    //numActionRead shows how many actions are add in
    int numActionRead = 0;

    //Constructor
    public Ngram(int type_n) {
        this.N = type_n;
        this.transit = new String[type_n];
    }

    //String stu_ID,
    public void add(String cond, String stu_ID, String action) {
        HashMap<String, HashMap<String, Double>> idHashMap;
        HashMap<String, Double> nGramHash;

        if (!condHashMap.containsKey(cond)) {
            idHashMap = new HashMap<>();

            condHashMap.put(cond, idHashMap);
            transit = new String[N];
            numActionRead = 0;
        } else {
            idHashMap = condHashMap.get(cond);
        }

        if (!idHashMap.containsKey(stu_ID)) {

            nGramHash = new HashMap<>();

            idHashMap.put(stu_ID, nGramHash);

            // Clear the Ngram
            transit = new String[N];
            // Reset the count
            numActionRead = 0;

        } else {
            nGramHash = idHashMap.get(stu_ID);
        }

        ///then begin adding action;
        if (numActionRead < N) {
            transit[numActionRead] = action;
        } //if the char array is full, just moving and replace the last one with action.//if the char array is full, just moving and replace the last one with action.
        else {
            for (int i = 0; i < N - 1; i++) {
                transit[i] = transit[i + 1];
            }
            transit[N - 1] = action;
        }
        numActionRead++;

        if (numActionRead >= N) {
            String s_tran = Arrays.toString(transit);
            if (nGramHash.containsKey(s_tran)) {
                nGramHash.put(s_tran, nGramHash.get(s_tran) + 1);
            } else {
                nGramHash.put(s_tran, 1.0);
            }
        }
        idHashMap.put(stu_ID, nGramHash);

    }

    /* public String gettransit() {
        // public List gettransit(){
        /* String n =Arrays.toString(transit);
        n= n.replace(',', ':');
        return n;
        //return transit_list;
        return Arrays.toString(transit);
    }

    public int getactionread() {
        return numActionRead;
    }*/

 /*public void printNgram() {
        for (String key : nGramHash.keySet()) {
            System.out.println(key + "..." + nGramHash.get(key));
        }
    }*/
    public void printNgram() {
        for (String con : condHashMap.keySet()) {
            for (String id : condHashMap.get(con).keySet()) {
                System.out.println(con + "...." + id + "..." + condHashMap.get(con).get(id));
            }
        }
    }

// summary() could return the result for each condition; get the number of student in each condition
    public HashMap summary() {
        HashMap<String, HashMap<String, Double>> sumHash = new HashMap<>();

        HashMap<String, Double> perconHash;
        
        for (String con : condHashMap.keySet()) {
            for (String id : condHashMap.get(con).keySet()) {
                for (String tran : condHashMap.get(con).get(id).keySet()) {

                    if (!sumHash.containsKey(con)) {
                        perconHash = new HashMap<>();
                        sumHash.put(con, perconHash);
                    } else {
                        //perconHash = sumHash.get(con);
                    }

                    if (!sumHash.get(con).containsKey(tran)) {

                        //sumHash.get(con).put(tran, condHashMap.get(con).get(id).get(tran));
                        sumHash.get(con).put(tran, condHashMap.get(con).get(id).get(tran)/condHashMap.get(con).size());
                    } else {
                       // sumHash.get(con).put(tran, sumHash.get(con).get(tran) + condHashMap.get(con).get(id).get(tran));
                        sumHash.get(con).put(tran, sumHash.get(con).get(tran) + condHashMap.get(con).get(id).get(tran)/condHashMap.get(con).size());
                    }
                }
            }
        //number od student in each condition
            System.out.print(con + "...." +condHashMap.get(con).size() + "\n");
        }
        
        
        sumHashRead = sumHash;
        //the frequency of each sequence under each condition
        return sumHash;
        
    }

    public HashMap summaryTran() {
        HashMap<String, HashMap<String, Double>> sumTranHash = new HashMap<>();
        HashMap<String, Double> pertranHash;
        
        for (String con : sumHashRead.keySet()) {
            for (String tran : sumHashRead.get(con).keySet()){
               
                
                if (!sumTranHash.containsKey(tran)) {
                        pertranHash = new HashMap<>();
                        sumTranHash.put(tran, pertranHash);
                    } else {
                        //perconHash = sumHash.get(con);
                    }
  
                   /* if (!sumTranHash.get(tran).containsKey(con)) {

                        //sumHash.get(con).put(tran, condHashMap.get(con).get(id).get(tran));
                        sumTranHash.get(tran).put(con, sumHashRead.get(con).get(tran));
                    } else {
                       // sumHash.get(con).put(tran, sumHash.get(con).get(tran) + condHashMap.get(con).get(id).get(tran));
                        //sumTranHash.get(con).put(tran, sumTranHash.get(con).get(tran) + sumHashRead.get(con).get(id).get(tran)/condHashMap.get(con).size());
                    }*/
                
            }
        }
        
    // build n conditions under each transitiion
    for (String tran: sumTranHash.keySet()){
        for(String con : sumHashRead.keySet()){
            if (!sumTranHash.get(tran).containsKey(con)){
             if(!sumHashRead.get(con).containsKey(tran)){
                sumTranHash.get(tran).put(con, 0.00);
             }
              else{
                 sumTranHash.get(tran).put(con,sumHashRead.get(con).get(tran));
              }
            }
        }
    
    }     
    sumTranHashRead = sumTranHash;
    return sumTranHash;
    }

   //Fuction to transfer sumTranHash into list;
    public List listTran(){
        //print all the source and destination

        //List<List> row = new ArrayList<>();
        ArrayList<ArrayList<String>> row = new ArrayList<>(); 
        List withinrow = new ArrayList<>();
        
        withinrow.add("Transition");
        withinrow.add("Source");
        if(N>2){
            for(int i=2; i<N; i++){
            withinrow.add("Activity"+i);}
        }
        withinrow.add("Destination");
        for(String con: condHashMap.keySet()){        
        withinrow.add(con+condHashMap.get(con).size());
        }
        row.add((ArrayList<String>) withinrow);
        
        for(String tran: sumTranHashRead.keySet()){
            String nodes=tran.substring(1,tran.length()-1);
            withinrow = new ArrayList<>();
            withinrow.add(tran);
            String nodessplit[] = new String[N];
            nodessplit=nodes.split(",");
            for(int i=0;i<N;i++){
                withinrow.add(nodessplit[i]);
            }

            for(String con:sumTranHashRead.get(tran).keySet()){
            withinrow.add(sumTranHashRead.get(tran).get(con));
            }
        row.add((ArrayList<String>) withinrow);
        }   
    return row;
    
    }
    
    //public 
    public HashMap condAllign(){
        //allign all the transition under all students
        
        HashMap<String, HashMap<String, HashMap<String, Double>>> condHashAllign = new HashMap<>();

        HashMap<String, HashMap<String, Double>> perconHash = new HashMap<>();
        HashMap<String, Double> peridHash = new HashMap<>();
        
        for (String con : condHashMap.keySet()) {
            perconHash = new HashMap<>();
            condHashAllign.put(con, perconHash);
            
            for (String id : condHashMap.get(con).keySet()) {
                peridHash = new HashMap<>();
                condHashAllign.get(con).put(id, peridHash);
                
                for (String tran : sumTranHashRead.keySet()) {
                if(!condHashMap.get(con).get(id).containsKey(tran)){
                    condHashAllign.get(con).get(id).put(tran, 0.00);
                }
                else{
                    condHashAllign.get(con).get(id).put(tran,condHashMap.get(con).get(id).get(tran));
                }
                //System.out.println(con + "...." + id + "..." + condHashMap.get(con).get(id));

                
            }
        }
        }
        
       condHashAllignRead = condHashAllign;
       return condHashAllign;
    }
    
   public List listcondHashAllign() {
        //List<List> row = new ArrayList<>();
        ArrayList<ArrayList<String>> row = new ArrayList<>();
        List withinrow = new ArrayList<>();

        withinrow.add("Condition");
        withinrow.add("ID");

        for (String tran : sumTranHashRead.keySet()) {
            withinrow.add(tran);
        }

        row.add((ArrayList<String>) withinrow);

        
        for (String con : condHashAllignRead.keySet()) {
            for (String id : condHashAllignRead.get(con).keySet()) {
                withinrow = new ArrayList<>();
                withinrow.add(con);
                withinrow.add(id);

                for (String tran : condHashAllignRead.get(con).get(id).keySet()) {
                    withinrow.add(condHashAllignRead.get(con).get(id).get(tran));
                   // withinrow.add(tran);
                }
             
            row.add((ArrayList<String>) withinrow);
            }
        }

        return row;
    }
    
}
