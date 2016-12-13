import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;



public class printTable {
	public static void main(String[] args) throws FileNotFoundException, IOException {
	//args[0]: "replacemulti" or "regular"
	//args[1]: all activity "XEQT"
	//args[2]: gap
	//args[3]: select_top
	//args[4]: existThreshold
	//args[5]: pThreshold
		
	//filter this with p, then rank with t value.
		
		
		String input_file="C:\\Users\\Dan Lv\\OneDrive\\Summer Intern\\OneDrive\\SimStudent Project\\Analysis (where we analyze data)\\Study VI Tutoring Pattern (Joyce & Dan)\\Differential Sequence Pattern Mining\\EQXRTUV_input.csv";
		String output_file="C:\\Users\\Dan Lv\\Documents\\"+args[0]+"_"+args[1]+"_g"+args[2]+"_top"+args[3]+"_e"+args[4]+"_p"+args[5]+".csv";
		tools t=new tools();
		int gap=Integer.parseInt(args[2]);//the largest gap we can ignore in the sequence
		int selectTop=Integer.parseInt(args[3]);//select n top t-value pattern every step
		int existThreshold=Integer.parseInt(args[4]);//the minimum number of students who have the pattern
		double pThreshold=Double.parseDouble(args[5]);//the minimum p-value, the patterns which have greater p-value will be dropped.
		
		
				
		
		
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

			if(args[0].equals("replacemulti")) newstudent.replaceMulti();
			//System.out.println(newstudent.activity);
			if(newstudent.class1.equals("good")){students.add(newstudent);goodnum++;}
			if(newstudent.class1.equals("bad")){students.add(newstudent);badnum++;}
			
		}
		System.out.println("We have total "+students.size()+" students.");
		System.out.println("Good student: "+goodnum);
		System.out.println("Bad student: "+badnum);
		
		
		System.out.println("Reading completed.\n");

		
		//counting all the possible patterns with length n
		int n=2;
		List<String> patterns=new ArrayList<String>();
		char[] letters=args[1].toCharArray();
		System.out.println("The number of letters: "+letters.length);
		System.out.println(args[0]+": Counting all possible patterns...");
		if(args[0].equals("replacemulti")){
			t.patternMaker_replacemulti(letters,patterns,n);
			System.out.println(patterns);
		}
		if(args[0].equals("regular")){
			t.patternMaker_regular(letters,patterns,n);
			System.out.println(patterns);
		}
		System.out.println("");

		System.out.println("Total number of patterns: "+patterns.size());
		t.countPattern(students,patterns,gap);
		//System.out.println(students.get(1).activity);
		//System.out.println(students.get(1).patternFre);
		
				
		System.out.println("Finish counting.");
		System.out.println("Filtering out the patterns which occur in less student than exist threshold "+existThreshold+" ...");
		
	
		//Neglect the pattern which too few students have
		//put the pattern frequency matrices into the class patternTTest
		//put all class patternTTest into a list Patterns
		List<patternTTest> patterns_result=new ArrayList<patternTTest>();
		t.neglectPattern(patterns,patterns_result,students,existThreshold);
		System.out.println("Finish filtering.");
		System.out.println("");
		
		
		//t test
		t.T_Test(patterns_result,students,pThreshold,selectTop);
		CSVWriter writer = new CSVWriter(new FileWriter(output_file), ',');
		//String [] row={"aaa","bbb","333","444","555","666"};		
	    String [] row={"NGram","t statistics","p value","mean of frequency in good students","mean of frequency in bad students","absolute difference of means"};

	    //first row
	    writer.writeNext(row);
	    //write 2-gram
	    for(int i=0;i<Math.min(selectTop,patterns_result.size());i++){
	    	row[0]=patterns_result.get(i).pattern;
	    	row[1]=Double.toString(patterns_result.get(i).tStat);
	    	row[2]=Double.toString(patterns_result.get(i).p_value);
	    	row[3]=Double.toString(patterns_result.get(i).goodAve);
	    	row[4]=Double.toString(patterns_result.get(i).badAve);
	    	row[5]=Double.toString(Math.abs(patterns_result.get(i).goodAve-patterns_result.get(i).badAve));
	    	writer.writeNext(row);	
	    }

		
		
		
		//for n>2
		while(n<5){
			n++;
			patterns=new ArrayList<String>();

			System.out.println(args[0]+": Counting all possible patterns...");
			if(args[0].equals("replacemulti")){
				t.patternMaker_replacemulti(letters,patterns,n);
				System.out.println(patterns);
			}
			if(args[0].equals("regular")){
				t.patternMaker_regular(letters,patterns,n);
				System.out.println(patterns);
			}
			System.out.println("");

			
			System.out.println("Total number of patterns: "+patterns.size());
			t.countPattern(students,patterns,gap);
			
			//System.out.println(students.get(2).activity);
			//System.out.println(students.get(2).patternFre);
			
			
			
			System.out.println("Finish counting.");
			System.out.println("Filtering out the patterns which occur in less student than exist threshold "+existThreshold+" ...");
			patterns_result=new ArrayList<patternTTest>();
			t.neglectPattern(patterns,patterns_result,students,existThreshold);
			System.out.println("Finish filtering.");
			System.out.println("");
			t.T_Test(patterns_result,students,pThreshold,selectTop);
			
			 for(int i=0;i<Math.min(selectTop,patterns_result.size());i++){
			    	row[0]=patterns_result.get(i).pattern;
			    	row[1]=Double.toString(patterns_result.get(i).tStat);
			    	row[2]=Double.toString(patterns_result.get(i).p_value);
			    	row[3]=Double.toString(patterns_result.get(i).goodAve);
			    	row[4]=Double.toString(patterns_result.get(i).badAve);
			    	row[5]=Double.toString(Math.abs(patterns_result.get(i).goodAve-patterns_result.get(i).badAve));
			    	writer.writeNext(row);	
			 }

			
			
		}
		
		writer.close(); 
		
	}

}
