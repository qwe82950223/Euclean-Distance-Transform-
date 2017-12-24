import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 
 * @author xiao lin
 *
 */
public class Main{
	public static void main(String[] args){
		File infile = new File(args[0]); //input file
		File output = new File(args[1]); //output file
		File output2 = new File(args[2]); //output file 2
		try{
			Scanner scan = new Scanner(infile);
			PrintWriter printer = new PrintWriter(output);
			PrintWriter printer2 = new PrintWriter(output2);
			
			EucleanDistance eucleanDistance = new EucleanDistance(scan,printer);
			eucleanDistance.printBinaryImage(printer2);
			
			scan.close();
			printer.close();
			printer2.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end catch
		
		
	}//end main
}//end class