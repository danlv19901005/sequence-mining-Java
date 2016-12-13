

import java.util.HashMap;

public class Student {

	//initialize
		String id;
		String activity;
		String condition;
		String class1;
		double norm_gain;
		int totalQTX;
		HashMap<String,Integer> patternFre;

		//constructor

		public Student(String ID,String Activity,String Condition,String Class1,double gain){
			//initialize
			id=ID;
			activity=Activity;
			condition=Condition;
			class1=Class1;
			norm_gain=gain;
			patternFre=new HashMap<String,Integer>();
			totalQTX=0;
		}

		//Drop tails of Q if count_Q > 4
		
		public void replaceMulti(){
			//replace the multiple activity 
			int n=this.activity.length();
			String temp="";
			int count=0;
			for(int i=1;i<n;i++){
				if(this.activity.charAt(i)==this.activity.charAt(i-1))
					count++;
				else{
					if(count>0)temp+=(char)(this.activity.charAt(i-1));//(this.activity.charAt(i-1)+32)
					else temp+=this.activity.charAt(i-1);
					count=0;
				}
			}
			if(count>0) temp+=(char)(this.activity.charAt(n-1));//(this.activity.charAt(n-1)+32)
			else temp+=(char)this.activity.charAt(n-1);
				
			this.activity=temp;
		}
		
		
		
		/*
		public void replaceBoth(){
			//replace the multiple activity 
			int n=this.activity.length();
			String temp="";
			int count=0;
			for(int i=1;i<n;i++){
				if(this.activity.charAt(i)!=this.activity.charAt(i-1))
					temp+=(char)(this.activity.charAt(i-1)+32);
			}
			temp+=(char)(this.activity.charAt(n-1)+32);
			this.activity=temp;
		}
		*/
}

