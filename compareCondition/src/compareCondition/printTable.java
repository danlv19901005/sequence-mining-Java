package compareCondition;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;



public class printTable {
	//argument
	//0.p-value-threshold
	//1.gap
	//2.scope
	//3.replacemulti or regular

	public static void main(String[] args) throws FileNotFoundException, IOException {

		
		int N=5;
		int gap=Integer.parseInt(args[1]);
		int scope=Integer.parseInt(args[2]);
		double p_threshold=Double.parseDouble(args[0]);
		
		String input_file="C:\\Users\\Dan Lv\\OneDrive\\Summer Intern\\OneDrive\\SimStudent Project\\Analysis (where we analyze data)\\Study VI Tutoring Pattern (Joyce & Dan)\\Differential Sequence Pattern Mining\\EQXRTUV_input.csv";
		String output_file="C:\\Users\\Dan Lv\\OneDrive\\Summer Intern\\OneDrive\\SimStudent Project\\Analysis (where we analyze data)\\Study VI Tutoring Pattern (Joyce & Dan)\\Differential Sequence Pattern Mining\\EQXRTUV_Condition_mNA";
		output_file=output_file+"g"+args[1]+"s"+args[2]+"p"+args[0];
		if(args[3].equals("replacemulti")) output_file+="-R";
		output_file+=".csv";

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
		while ((nextLine = reader.readNext()) != null) {
			Student newstudent=new Student(nextLine[ID_COLUMN],nextLine[ACTIVITY_COLUMN],nextLine[COND_COLUMN],nextLine[CLASS_COLUMN],Double.parseDouble(nextLine[GAIN_COLUMN]));
			System.out.println(newstudent.activity);

			if(args[3].equals("replacemulti")) newstudent.replaceBoth();//replaceMulti() or replaceBoth()
			System.out.println(newstudent.activity);
			students.add(newstudent);
		}
		System.out.println("We have total "+students.size()+"students.");
		System.out.println("Reading completed.\n");

		tools tool=new tools();
		HashMap<String,Integer> allPatterns=new HashMap<String,Integer>();
		
		
		
		
		
		for(int i=2;i<=N;i++){
			tool.countPatternFre(students,i,gap,scope,allPatterns);
			System.out.println(allPatterns.get("XQX"));
			
		}
		
		
		//write file
		
		CSVWriter writer = new CSVWriter(new FileWriter(output_file), ',');
		
		int len=5+allPatterns.keySet().size();
		String [] row1=new String[len];
		String [] pattern=new String[allPatterns.keySet().size()];
		row1[0]="ID";
		row1[1]="Condition";
		row1[2]="Class";
		row1[3]="Normalized Gain";
		row1[4]="Activity";
		int i=0;

		for(String temp:allPatterns.keySet()){
			pattern[i++]=temp;
		}
		Arrays.sort(pattern);
		
		for(i=5;i<len;i++){
			row1[i]=pattern[i-5];
		}
		
		
		
		writer.writeNext(row1);
		String [] row=new String[len];
		
		for(i=0;i<students.size();i++){
			row[0]=students.get(i).id;
			row[1]=students.get(i).condition;
			row[2]=students.get(i).class1;
			row[3]=Double.toString(students.get(i).norm_gain);
			row[4]=students.get(i).activity;
			
			int k=5;
			while(k<len){
				if(students.get(i).patternFre.containsKey(row1[k]))				
				row[k]=Integer.toString(students.get(i).patternFre.get(row1[k]));
				else row[k]="0";
				k++;
			}
			writer.writeNext(row);
				
		}


	}
}
