/* Ivan Chacon
 * COMP 496 (Summer 2016)
 * Prof. Noga
 * Project 1
 */

import java.io.*;
import java.util.Scanner;

public class proj1{
		
	static Scanner userInput = new Scanner(System.in);
	static String txtPWD = System.getProperty("user.dir").toString(); //fetches current directory(same DIR as .java file)
	static String userFileName;
	static int[][] menPref;
	static int[][] womPref;
	static int[] matchList;
	static int[] matchList01;
	static int pairs = 0;
	static boolean isStable = true;
	static boolean fileFound = true;

	public static void main(String[] args){
		System.out.println("\nProject 1: Stable Match Check\n");
		System.out.print("Please enter input file name \n>");
		userFileName = userInput.nextLine();
		//userFileName = "testing";
		if(!userFileName.substring(userFileName.length() - 4).equals(".txt")){
			userFileName += ".txt";
		}
		fileRead();
		//listPrints();
		int menMatch, manWom, womMatch, womMan;
		for(int m = 0; m < pairs; m++){
			for(int w = 0; w < pairs; w++){
				menMatch = ranking(menPref, m, matchList[m]);
				manWom= ranking(menPref, m, (w+1));
				womMatch = ranking(womPref, w, matchList01[w]);
				womMan = ranking(womPref, w,(m+1));
				if(manWom < menMatch && womMan < womMatch){
					System.out.println("Not stable. (M" + (m+1) + ", W" + (w+1) + ") is one instability.");
					isStable = false;
				}
			}
		}
		if(isStable && fileFound){
			System.out.println("Stable.");
		}
	}//main 
	
	//finds the index of an instance of k in an array given the array, the [gen][] index for a gender
	public static int ranking(int list[][], int gen, int k){
		int x = -1;
		for(int i = 0; i < pairs; i++){
			if(list[gen][i] == k){
				x =  i;
			}
		}
		return x;
	}

	//for testing purposes, prints each array created
	public static void listPrints(){
		System.out.println("\nMen's Pref List:");
		for(int i = 0; i < pairs; i++){
			System.out.print("M" + (i+1) + ": ");
			for(int j = 0; j < pairs; j++){
				System.out.print("W" + menPref[i][j] + " ");
				if(j == (pairs - 1)){
					System.out.print("\n");
				}
			}
		}
		System.out.println("\nWoman's Pref List:");
		for(int i = 0; i < pairs; i++){
			System.out.print("W" + (i+1) + ": ");
			for(int j = 0; j < pairs; j++){
				System.out.print("M" + womPref[i][j] + " ");
				if(j == (pairs - 1)){
					System.out.print("\n");
				}
			}
		}
		System.out.println("\nSuggested Matching:");
		for(int i = 0; i < pairs; i++){
			System.out.println("M" + (i+1) + " - W" + matchList[i]);
			
		}
	}

	public static void fileRead(){
		//input from txt file
		BufferedReader theReader = null;
		try {
			String currentLine;
			pairs = 0;
			//theReader = new BufferedReader(new FileReader(fileDir + userFileName));
			theReader = new BufferedReader(new FileReader(txtPWD + "/" + userFileName));
			pairs = Integer.parseInt(theReader.readLine()); //assuming not empty; assuming integer, grabs first int
			menPref = new int[pairs][pairs];
			womPref = new int[pairs][pairs];
			matchList = new int[pairs];
			matchList01 = new int[pairs];
			int i = 0;//rows
			while ((currentLine = theReader.readLine()) != null) { //read each line 
				String temp[] = currentLine.split(" "); //split each line into sets
				for(int j = 0; j < pairs; j++){//columns					
					if(i < pairs){
						menPref[i][j] = Integer.parseInt(temp[j]);
						//System.out.println(menPref[i][j]);
					}
					else if(i >= pairs && i < (2*pairs) ){
						womPref[i - pairs][j] = Integer.parseInt(temp[j]);
						//System.out.println(womPref[i - pairs][j]);
					}
					else
						matchList[j] = Integer.parseInt(temp[j]);
				}
				i++;
				//System.out.println();
			}//while 
			theReader.close();
			for(int k = 0; k < pairs; k++){
				matchList01[matchList[k] - 1] = k + 1;
			}
		} 
		catch(FileNotFoundException e) {
			fileFound = false;
            System.out.println("file '" + userFileName + "' not found.");                
        }
		catch (IOException e) {
			fileFound = false;
			System.out.println("Error reading file.");
		}	
	}//fileRead 
}//class 
