import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.stat.inference.TTest;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;



public class PrintTable {
	public static void main(String[] args) throws FileNotFoundException, IOException {
	//we only consider replace-multi in this algorithm 
	//args[0]: gap
		
	//filter this with p, then rank with t value.
		
		
		String input_file="C:\\Users\\Dan Lv\\OneDrive\\Summer Intern\\OneDrive\\SimStudent Project\\Analysis (where we analyze data)\\Study VI Tutoring Pattern (Joyce & Dan)\\Differential Sequence Pattern Mining\\EQXRTUV_input.csv";
		String output_file="C:\\Users\\Dan Lv\\Documents\\TXQ"+"_g"+args[0]+".csv";
		tools t=new tools();
		int gap=Integer.parseInt(args[0]);//the largest gap we can ignore in the sequence
		
		
				
		
		
		//reading data
		List<Student> students=new ArrayList<Student>();
		//the location of the input file
		//input file should have student's ID, sequence,condition,class,normalized gain
		CSVReader reader = new CSVReader(new FileReader(input_file));
		String [] nextLine=reader.readNext(); 
		final int ID_COLUMN=0;
		final int ACTIVITY_COLUMN=4;
		final int COND_COLUMN=1;
		final int CLASS_COLUMN=2;
		final int GAIN_COLUMN=3;

		System.out.println("Reading data from the input file: "+input_file);

		//input file is the pre-cleaned dropped-tail file
		int goodnum=0,badnum=0;
		while ((nextLine = reader.readNext()) != null) {
			Student newstudent=new Student(nextLine[ID_COLUMN],nextLine[ACTIVITY_COLUMN],nextLine[COND_COLUMN],nextLine[CLASS_COLUMN],Double.parseDouble(nextLine[GAIN_COLUMN]));
			//System.out.println(newstudent.activity);
			newstudent.replaceMulti();
			//System.out.println(newstudent.activity);
			if(newstudent.class1.equals("good")){students.add(newstudent);goodnum++;}
			if(newstudent.class1.equals("bad")){students.add(newstudent);badnum++;}	
		}
		
		System.out.println("We have total "+students.size()+" students.");
		System.out.println("Good student: "+goodnum);
		System.out.println("Bad student: "+badnum);
		
		
		System.out.println("Reading completed.\n");

		
		//put all the possible patterns in the list patterns
		
		
		
		
		int n=3;
		List<String> patterns=new ArrayList<String>();
		patterns.add("QXT");patterns.add("QTX");patterns.add("XTQ");patterns.add("XQT");patterns.add("TQX");patterns.add("TXQ");
		
		
		System.out.println(patterns);
		System.out.println("Total number of patterns: "+patterns.size());
		
		t.countPattern(students,patterns,gap);
		//System.out.println(students.get(4).activity);
		//System.out.println(students.get(4).patternFre);
		//System.out.println(students.get(4).totalQTX);
		
				
		System.out.println("Finish counting.");
		
		
		double[] goodstudent_TXQ=new double[goodnum];
		double[] badstudent_TXQ=new double[badnum];
		int p1=0,p2=0;
		for(Student stu:students){
			if(stu.class1.equals("good"))
				goodstudent_TXQ[p1++]=stu.totalQTX;
			else
				badstudent_TXQ[p2++]=stu.totalQTX;
		}
		TTest tt=new TTest();

		System.out.println("t test of the frequency of six patterns in goodstudents and bad students...");
		
		System.out.println("t Statistics: "+tt.t(goodstudent_TXQ, badstudent_TXQ));
		System.out.println("p: "+tt.tTest(goodstudent_TXQ, badstudent_TXQ));
		
		CSVWriter writer = new CSVWriter(new FileWriter(output_file), ',');
		String [] row={"Student ID","Class","condition","Total_XTQ","QXT","QTX","TQX","TXQ","XQT","XTQ","Sequence"};

	    //first row
	    writer.writeNext(row);
	    //write 2-gram
	    for(Student stu:students){
	    	row[0]=stu.id;
	    	row[1]=stu.class1;
	    	row[2]=stu.condition;
	    	row[3]=Integer.toString(stu.totalQTX);
	    	
	    	row[4]=Integer.toString(stu.patternFre.get("QXT"));
	    	row[5]=Integer.toString(stu.patternFre.get("QTX"));
	    	row[6]=Integer.toString(stu.patternFre.get("TQX"));
	    	row[7]=Integer.toString(stu.patternFre.get("TXQ"));
	    	row[8]=Integer.toString(stu.patternFre.get("XQT"));
	    	row[9]=Integer.toString(stu.patternFre.get("XTQ"));
	    	row[10]=stu.activity;
	    	
	    	writer.writeNext(row);	
	    }

		
		
		//writer.close(); 
		
	}

}
