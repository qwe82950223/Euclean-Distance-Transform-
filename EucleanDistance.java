import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

public class EucleanDistance{
	
	double[][] zeroFramedAry;// 2d array to store the pixel
	double[] neighborAry;
	int numRows;
	int numCols;
	int minVal;
	int maxVal;
	double newMin;
	double newMax;
	
	public EucleanDistance(Scanner scan,PrintWriter printer){
		if(scan.hasNext()){//get the first line 
			if(scan.hasNextInt()){//read row, col, min, max number from first line
				numCols=scan.nextInt();
			}
			if(scan.hasNextInt()){
				numRows=scan.nextInt();
			}
			if(scan.hasNextInt()){
				minVal=scan.nextInt();
			}
			if(scan.hasNextInt()){
				maxVal=scan.nextInt();
			}
		}
		
		newMin =0.00;
		newMax =(double)minVal;
		
		
		
		zeroFramedAry = new double[numCols+2][numRows+2];//initallize zeroFramedAry
		neighborAry = new double[5];//initallize neighborAry
		
		zeroFramed();
		loadImage(scan);
		
		printer.println("pass1:");
		firstpassEucleanDistance();
		prettyPrint(printer);
		printer.println("pass2:");
		secondpassEucleanDistance();
		prettyPrint(printer);
	}


	private void loadImage(Scanner scan) {//load image into zeroFramedAry
		int i=1;
		int j=1;
		int number;
		while(j<numCols+1){
			if(scan.hasNext()){//get every line 
				while(i<numRows+1){
					if(scan.hasNextInt()){//get every number from each line
						number=scan.nextInt();
						zeroFramedAry[j][i]=(double)number;
					}
					i++;
				}
				i=1;
			}
			j++;
		}
	}
	
	private void zeroFramed() {//add zero to zeroFramedAry's board
		for(int i=0;i<numCols+2;i++){
			for(int j=0;j<numRows+2;j++){
				zeroFramedAry[i][j]=0;
			}
		}
		
	}
	
	private void loadNeighbors(int x, int y, boolean passOne){
		neighborAry[4]=zeroFramedAry[x][y];//itself
		if(passOne){
			neighborAry[0]=zeroFramedAry[x-1][y-1]+Math.sqrt(2);//top left neighbor
			neighborAry[1]=zeroFramedAry[x-1][y]+1;//top neighbor
			neighborAry[2]=zeroFramedAry[x-1][y+1]+Math.sqrt(2);//top right neighbor
			neighborAry[3]=zeroFramedAry[x][y-1]+1;//left neighbor
		}
		else{
			neighborAry[0]=zeroFramedAry[x+1][y+1]+Math.sqrt(2);//bottom right neighbor
			neighborAry[1]=zeroFramedAry[x+1][y]+1;//bottom neighbor
			neighborAry[2]=zeroFramedAry[x+1][y-1]+Math.sqrt(2);//bottom left neighbor
			neighborAry[3]=zeroFramedAry[x][y+1]+1;//right neighbor
		}
	}
	
	private double getMin(int i, int j, int number){//get the min of neighbor
		double min = neighborAry[0];
		for(int x=1;x<number;x++){
			if(neighborAry[x]<min){
				min=neighborAry[x];
			}
		}
		return min;
		
	}
	
	private void firstpassEucleanDistance(){
		for(int i=1;i<numCols+1;i++){
			for(int j=1;j<numRows+1;j++){
				if(zeroFramedAry[i][j]>0){
					loadNeighbors(i,j,true);
					zeroFramedAry[i][j]=getMin(i,j,4);
				}
			}
		}

	}
	
	private void secondpassEucleanDistance(){
		for(int i=numCols;i>0;i--){
			for(int j=numRows;j>0;j--){
				if(zeroFramedAry[i][j]>0){
					loadNeighbors(i,j,false);
					zeroFramedAry[i][j]=getMin(i,j,5);
					if(zeroFramedAry[i][j]>newMax){
						newMax=zeroFramedAry[i][j];
					}
				}
			}
		}
		
	}
	
	private void prettyPrint(PrintWriter printer){//pretty print the image
		double number;
		for(int i=1;i<numCols+1;i++){
			for(int j=1;j<numRows+1;j++){
				if(zeroFramedAry[i][j]==0){
					printer.print("  ");
				}
				else{
					number=zeroFramedAry[i][j]+0.5;
					printer.print((int)number+" ");
				}
			}
			printer.println();
		}
	}
	
	
	
	public void printBinaryImage(PrintWriter printer){//print Binary Image
		DecimalFormat df=new DecimalFormat("0.00");
		printer.println(numCols+" "+numRows+" "+df.format(newMin)+" "+df.format(newMax));
		for(int i=1;i<numCols+1;i++){
			for(int j=1;j<numRows+1;j++){
				printer.print(df.format(zeroFramedAry[i][j])+" ");
			}
			printer.println();
		}
	}
}