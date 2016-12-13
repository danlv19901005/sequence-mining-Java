package compareCondition;

import java.util.HashMap;
import java.util.List;


public class tools {
	public void countPatternFre(List<Student> students,int n,int gap,int scope,HashMap<String,Integer> allPatterns){
		for(int i=0;i<students.size();i++){
			for(int start=0;start<students.get(i).activity.length()-n;start++)
				findPattern(students.get(i),start,start,n,gap,scope,"",allPatterns);
			System.out.println(students.get(i).patternFre);
		
		}
	}


	public void findPattern(Student student,int start,int end,int n,int gap,int scope,String subPattern,HashMap<String,Integer> allPatterns){
		if((end-start+1<=scope)&&(end<student.activity.length())){
			subPattern+=student.activity.charAt(end);
			if(subPattern.length()==n){
				if(allPatterns.containsKey(subPattern)==false) allPatterns.put(subPattern,1); 
				
				if(student.patternFre.containsKey(subPattern)) student.patternFre.put(subPattern, student.patternFre.get(subPattern)+1);
				else student.patternFre.put(subPattern,1);
			}
			else{
				for(int currentGap=0;currentGap<=gap;currentGap++)
					findPattern(student,start,end+currentGap+1,n,gap,scope,subPattern,allPatterns);
			}
		}
	}



}
