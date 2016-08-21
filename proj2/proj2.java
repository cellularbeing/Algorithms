/* Ivan Chacon
 * COMP 496 (Summer 2016)
 * Prof. Noga
 * Project 1
 */

import java.io.*;
import java.util.*;

public class proj2{
		
	static Scanner userInput = new Scanner(System.in);
	static String txtPWD = System.getProperty("user.dir").toString(); //fetches current directory(same DIR as .java file)
	static String userFileName;

	static boolean fileFound = true;

	static ArrayList<APoint> sortedX; 
	static ArrayList<APoint> sortedY;

	public static void main(String[] args){
		System.out.println("\nProject 2: Closest Pair of Points\n");
		System.out.print("Please enter input file name \n>");
		userFileName = userInput.nextLine();
		
		while(!userFileName.equals("/quit")){
			if(userFileName.substring(0,4).equals("/pri")) {
				//listPrints(sortedX);//more for testing purposes, but prints current readFile
				if(userFileName.substring(6).equals("x")){
					listPrints(sortedX);
				}
				else if(userFileName.substring(6).equals("y")){
					listPrints(sortedY);
				}
				else
					System.out.println("please include an x or y after '/print'\n");
			}
			else {
				if(userFileName.length() < 4 || !userFileName.substring(userFileName.length() - 4).equals(".txt")){
					userFileName += ".txt";
				}
				fileRead();
				
				if(fileFound){
					//stableMatchCheck();
					//System.out.println("Run algorithm here");
					System.out.println("sqrt(" + dist(sortedX)+")");
				}
			}	
			System.out.print("\n\nPlease enter new input file name (or '/quit' to end) \n>");
			userFileName = userInput.nextLine();
		}//while
	}//main 

	public static void listPrints(ArrayList<APoint> list){
		System.out.println("\nA list of Points contains " + list.size() + " points: \n");
		for(int k = 0; k < list.size(); k++){
			System.out.println("list[" + k + "] : " + list.get(k) +"\n");
		}
	}

	public static void fileRead(){
		//input from txt file
		BufferedReader theReader = null;
		try {
			fileFound = true;
			String currentLine;
			//theReader = new BufferedReader(new FileReader(fileDir + userFileName));
			sortedX = new ArrayList<APoint>();
			theReader = new BufferedReader(new FileReader(txtPWD + "/" + userFileName));
			while ((currentLine = theReader.readLine()) != null) { //read each line 
				String temp[] = currentLine.split(" "); //split each line into sets
				APoint n = new APoint(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
				sortedX.add(n);
			}//while 
			theReader.close();

			sortedY = new ArrayList<>(sortedX);
			Collections.sort(sortedX, new PointComparerX());
			Collections.sort(sortedY, new PointComparerY());
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

	public static int dist(ArrayList<APoint> list){
		int shortestDist = 0;
		int currentDist = 0;
		boolean firstRun = false;

		for(int i = 0; i < list.size(); i++){
			for(int j = i + 1; j < list.size(); j++){
				if(!firstRun){
					firstRun = true;
					shortestDist = (int)Math.pow( (list.get(i).getX() + list.get(j).getX()), 2 ) + (int)Math.pow( (list.get(i).getY() + list.get(j).getY()), 2 );
				}
				else{
					currentDist = (int)Math.pow( (list.get(i).getX() + list.get(j).getX()), 2 ) + (int)Math.pow( (list.get(i).getY() + list.get(j).getY()), 2 );
					if(currentDist < shortestDist){
						shortestDist = currentDist;
					}
				}
			}
		}
		return shortestDist;
	}

}//class 

class APoint {
	private int x;
	private int y;
	public APoint(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	@Override
	public String toString(){
		return "x: " + x + " y: " + y;
	}
}
class PointComparerX implements Comparator<APoint> {
	@Override
  	public int compare(APoint a, APoint b) {
  		int i = 0;
  		if(a.getX() > b.getX()){
  			i = 1; 
  		}
  		if(a.getX() < b.getX()){
  			i = -1; 
  		}
  		return i;
  	}
}

class PointComparerY implements Comparator<APoint> {
	@Override
  	public int compare(APoint a, APoint b) {
  		int i = 0;
  		if(a.getY() > b.getY()){
  			i = 1; 
  		}
  		if(a.getY() < b.getY()){
  			i = -1; 
  		}
  		return i;
  	}
}