package project.algorithm.online;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	
	// Sets path to ipython-workspace, no need to edit this if project was cloned from GitHub
	public static String path = Paths.get("").toAbsolutePath().toString()+"/ipython-workspace/";

	// Maintains lists of all access and rotation costs (i.e for each item in sequence list)
	// This is done to write all costs together in csv files
	public static ArrayList<Integer> staticListAccessCost = new ArrayList<Integer>();
	public static ArrayList<Integer> mtfListAccessCost = new ArrayList<Integer>();
	public static ArrayList<Integer> transposeListAccessCost = new ArrayList<Integer>();
	public static ArrayList<Integer> kInARowListAccessCost = new ArrayList<Integer>();

	public static ArrayList<Integer> staticTreesAccessCost = new ArrayList<Integer>();
	public static ArrayList<Integer> mtfTreesAccessCost = new ArrayList<Integer>();
	public static ArrayList<Integer> transposeTreesAccessCost = new ArrayList<Integer>();
	public static ArrayList<Integer> kInARowTreesAccessCost = new ArrayList<Integer>();
	

	public static ArrayList<Integer> mtfRotationCost = new ArrayList<Integer>();
	public static ArrayList<Integer> transposeRotationCost = new ArrayList<Integer>();
	public static ArrayList<Integer> kInARowRotationCost = new ArrayList<Integer>();
	
	// Number of input integers and access sequence SIZES array
	public static int num = 1000;
	public static int[] sequenceLengths = {100000, 200000, 300000, 400000, 500000, 
			600000, 700000, 800000, 900000, 1000000};
	
    
	public static void main(String[] args) throws IOException {
			SearchHelper helper = new SearchHelper(num);
			
			// Run the program to generate Markov high self loop sequences
			// Output access costs, rotation costs and percentages of frequency files
			System.out.println("MARKOV HIGH");
			for(int i = 0; i < sequenceLengths.length; i++)
			{
				helper.searchMarkovHighSelfLoop(sequenceLengths[i]);
	            addToCost(helper);
	            writePercentagesToFile("percentages/MarkovHigh" + "/"  + Integer.toString(i), helper.states, helper.percentages, false);
			}
			writeAllToFile("MarkovHigh");
			

			// Run the program to generate Markov Medium self loop sequences
			// Output access costs, rotation costs and percentages of frequency files
			System.out.println("MARKOV MEDIUM");
			for(int i = 0; i < sequenceLengths.length; i++)
			{
				helper.searchMarkovMediumSelfLoop(sequenceLengths[i]);
	            addToCost(helper);
	            writePercentagesToFile("percentages/MarkovMedium" + "/" + Integer.toString(i), helper.states, helper.percentages, false);
			}
			writeAllToFile("MarkovMedium");
			

			// Run the program to generate Markov low self loop sequences
			// Output access costs, rotation costs and percentages of frequency files
			System.out.println("MARKOV LOW");
			for(int i = 0; i < sequenceLengths.length; i++)
			{
	            helper.searchMarkovLowSelfLoop(sequenceLengths[i]);
	            addToCost(helper);
	            writePercentagesToFile("percentages/MarkovLow" + "/"  + Integer.toString(i), helper.states, helper.percentages, false);
			}
			writeAllToFile("MarkovLow");
			

			// Run the program to generate Zipfian sequences
			// Output access costs, rotation costs and percentages of frequency files
			System.out.println("ZIPFIAN");
			for(int i = 0; i < sequenceLengths.length; i++)
			{
    				helper.searchZipf(1.3, sequenceLengths[i]);
	            addToCost(helper);
	            writePercentagesToFile("percentages/Zipfian" + "/"  + Integer.toString(i), helper.states, helper.percentages, true);
			}
			writeAllToFile("Zipfian");
	}
	

	// Helper function to update access and rotation costs list
	public static void addToCost(SearchHelper helper)
	{
		staticListAccessCost.add(helper.staticListAccessCost);
	    mtfListAccessCost.add(helper.mtfListAccessCost); 
	    transposeListAccessCost.add(helper.transposeListAccessCost); 
	    kInARowListAccessCost.add(helper.kInARowListAccessCost); 

		staticTreesAccessCost.add(helper.staticTreesAccessCost); 
	    mtfTreesAccessCost.add(helper.mtfTreesAccessCost); 
	    transposeTreesAccessCost.add(helper.transposeTreesAccessCost); 
	    kInARowTreesAccessCost.add(helper.kInARowTreesAccessCost); 
	    

	    mtfRotationCost.add(helper.mtfTotalRotationCost); 
		transposeRotationCost.add(helper.transposeTotalRotationCost); 
		kInARowRotationCost.add(helper.kInARowTotalRotationCost); 
		
	}

	// Helper function to clear cost lists (needed before we run program on different type of input sequence)
	public static void flushCostLists()
	{

		staticListAccessCost = new ArrayList<Integer>();
		mtfListAccessCost = new ArrayList<Integer>();
		transposeListAccessCost = new ArrayList<Integer>();
		kInARowListAccessCost = new ArrayList<Integer>();

		staticTreesAccessCost = new ArrayList<Integer>();
		mtfTreesAccessCost = new ArrayList<Integer>();
		transposeTreesAccessCost = new ArrayList<Integer>();
		kInARowTreesAccessCost = new ArrayList<Integer>();
		

		mtfRotationCost = new ArrayList<Integer>();
		transposeRotationCost = new ArrayList<Integer>();
		kInARowRotationCost = new ArrayList<Integer>();
		
	}
	
	// Helper function to write percentages of frequencies to csv files
	public static void writePercentagesToFile(String fileName, Integer[] input, ArrayList<Double> percent, boolean zipfian) throws IOException
	{
		  String csvFile = path + fileName + ".csv";
	      FileWriter writer = new FileWriter(csvFile);
	      CSVUtils.writeLine(writer, Arrays.asList("Input", "Percentage"));
			
	  		for(int i = 0; i < num; i++)
	  		{
	  			if(zipfian)
		  	        CSVUtils.writeLine(writer, Arrays.asList(Integer.toString(i), Double.toString(percent.get(i))));
	  			else
	  				CSVUtils.writeLine(writer, Arrays.asList(Integer.toString(input[i]), Double.toString(percent.get(i))));

	  		}
	
	      writer.flush();
	      writer.close();
	}

	// Helper function to write costs to csv file
	public static void writeToFile(String fileName, ArrayList<Integer> costs) throws IOException
	{
		  String csvFile = path + fileName+ ".csv";
	      FileWriter writer = new FileWriter(csvFile);
	      CSVUtils.writeLine(writer, Arrays.asList("Sequence Length", "Access Costs"));
			
	  		for(int i = 0; i < sequenceLengths.length; i++)
	  		{
	  	        CSVUtils.writeLine(writer, Arrays.asList(Integer.toString(sequenceLengths[i]), Integer.toString(costs.get(i))));
	  			
	  		}
	
	      writer.flush();
	      writer.close();
	}

	// Helper function to write total costs to file, it takes access and rotation cost as input and writes their sum
	public static void writeTotalToFile(String fileName, ArrayList<Integer> accessCosts, ArrayList<Integer> rotationCosts) throws IOException
	{
		  String csvFile = path + fileName+ ".csv";
	      FileWriter writer = new FileWriter(csvFile);
	      CSVUtils.writeLine(writer, Arrays.asList("Sequence Length", "Access Costs"));
			
	  		for(int i = 0; i < sequenceLengths.length; i++)
	  		{
	  	        CSVUtils.writeLine(writer, Arrays.asList(Integer.toString(sequenceLengths[i]), Integer.toString(accessCosts.get(i)+rotationCosts.get(i))));
	  			
	  		}
	
	      writer.flush();
	      writer.close();
	}
	
	// Helper function to write histogram values to file
	public static void writeHistogramToFile(String fileName, ArrayList<Integer> staticCost, ArrayList<Integer> mtfCost, ArrayList<Integer> transposeCost, ArrayList<Integer> kInARowCost) throws IOException
	{
		  String csvFile = path + fileName+ ".csv";
	      FileWriter writer = new FileWriter(csvFile);
	      CSVUtils.writeLine(writer, Arrays.asList("Heuristic", "Cost"));
			
	  		for(int i = 0; i < sequenceLengths.length; i++)
	  		{
	  	        CSVUtils.writeLine(writer, Arrays.asList("OPT", Integer.toString(staticCost.get(i))));
	  	        CSVUtils.writeLine(writer, Arrays.asList("MTF", Integer.toString(mtfCost.get(i))));
	  	        CSVUtils.writeLine(writer, Arrays.asList("Transpose", Integer.toString(transposeCost.get(i))));
	  	        CSVUtils.writeLine(writer, Arrays.asList("2-in-a-Row", Integer.toString(kInARowCost.get(i))));
	  		}
	
	      writer.flush();
	      writer.close();
	}

	// Helper function to write histogram values to file for trees
	public static void writeTreeHistogramToFile(String fileName, ArrayList<Integer> staticCost, ArrayList<Integer> mtfCost, ArrayList<Integer> transposeCost, ArrayList<Integer> kInARowCost) throws IOException
	{
		  String csvFile = path + fileName+ ".csv";
	      FileWriter writer = new FileWriter(csvFile);
	      CSVUtils.writeLine(writer, Arrays.asList("Heuristic", "Cost"));
			
	  		for(int i = 0; i < sequenceLengths.length; i++)
	  		{
	  	        CSVUtils.writeLine(writer, Arrays.asList("OPT", Integer.toString(staticCost.get(i))));
	  	        CSVUtils.writeLine(writer, Arrays.asList("Splay", Integer.toString(mtfCost.get(i))));
	  	        CSVUtils.writeLine(writer, Arrays.asList("Transpose", Integer.toString(transposeCost.get(i))));
	  	        CSVUtils.writeLine(writer, Arrays.asList("2-in-a-Row", Integer.toString(kInARowCost.get(i))));
	  		}
	
	      writer.flush();
	      writer.close();
	}
	

	// Helper function to write table to file
	public static void writeTableToFile(String fileName, ArrayList<Integer> staticCost, ArrayList<Integer> mtfCost, ArrayList<Integer> transposeCost, ArrayList<Integer> kInARowCost) throws IOException
	{
		  String csvFile = path + fileName+ ".csv";
	      FileWriter writer = new FileWriter(csvFile);
	      CSVUtils.writeLine(writer, Arrays.asList("Sequence Length", "OPT", "MTF", "Transpose", "2-in-a-Row"));
			
	  		for(int i = 0; i < sequenceLengths.length; i++)
	  		{
	  	        CSVUtils.writeLine(writer, Arrays.asList(
	  	        		Integer.toString(sequenceLengths[i]), 
	  	        		Integer.toString(staticCost.get(i)),
	  	        		Integer.toString(mtfCost.get(i)), 
	  	        		Integer.toString(transposeCost.get(i)),
	  	        		Integer.toString(kInARowCost.get(i))
	  	        		));
	  		}
	
	      writer.flush();
	      writer.close();
	}
	
	// Helper function to write tree table to file
	public static void writeTreeTableToFile(String fileName, ArrayList<Integer> staticCost, ArrayList<Integer> mtfCost, ArrayList<Integer> transposeCost, ArrayList<Integer> kInARowCost) throws IOException
	{
		  String csvFile = path + fileName+ ".csv";
	      FileWriter writer = new FileWriter(csvFile);
	      CSVUtils.writeLine(writer, Arrays.asList("Sequence Length", "OPT", "Splay", "Transpose", "2-in-a-Row"));
			
	  		for(int i = 0; i < sequenceLengths.length; i++)
	  		{
	  	        CSVUtils.writeLine(writer, Arrays.asList(
	  	        		Integer.toString(sequenceLengths[i]), 
	  	        		Integer.toString(staticCost.get(i)),
	  	        		Integer.toString(mtfCost.get(i)), 
	  	        		Integer.toString(transposeCost.get(i)),
	  	        		Integer.toString(kInARowCost.get(i))
	  	        		));
	  		}
	
	      writer.flush();
	      writer.close();
	}
	
	// Helper function to write rotation table to file
	public static void writeRotationTableToFile(String fileName, ArrayList<Integer> mtfCost, ArrayList<Integer> transposeCost, ArrayList<Integer> kInARowCost) throws IOException
	{
		  String csvFile = path + fileName+ ".csv";
	      FileWriter writer = new FileWriter(csvFile);
	      CSVUtils.writeLine(writer, Arrays.asList("Sequence Length", "Splay", "Transpose", "2-in-a-Row"));
			
	  		for(int i = 0; i < sequenceLengths.length; i++)
	  		{
	  	        CSVUtils.writeLine(writer, Arrays.asList(
	  	        		Integer.toString(sequenceLengths[i]), 
	  	        		Integer.toString(mtfCost.get(i)), 
	  	        		Integer.toString(transposeCost.get(i)),
	  	        		Integer.toString(kInARowCost.get(i))
	  	        		));
	  		}
	
	      writer.flush();
	      writer.close();
	}
	
	// Helper function to write rotation histogram values to file
	public static void writeRotationHistogramToFile(String fileName, ArrayList<Integer> mtfCost, ArrayList<Integer> transposeCost, ArrayList<Integer> kInARowCost) throws IOException
	{
		  String csvFile = path + fileName+ ".csv";
	      FileWriter writer = new FileWriter(csvFile);
	      CSVUtils.writeLine(writer, Arrays.asList("Heuristic", "Cost"));
			
	  		for(int i = 0; i < sequenceLengths.length; i++)
	  		{
	  	        CSVUtils.writeLine(writer, Arrays.asList("Splay", Integer.toString(mtfCost.get(i))));
	  	        CSVUtils.writeLine(writer, Arrays.asList("Transpose", Integer.toString(transposeCost.get(i))));
	  	        CSVUtils.writeLine(writer, Arrays.asList("2-in-a-Row", Integer.toString(kInARowCost.get(i))));
	  		}
	
	      writer.flush();
	      writer.close();
	}
	
	// Helper function to write all values to files
	public static void writeAllToFile(String dataType) throws IOException
	{

		writeToFile("list/static" + dataType +"ListAccessCost", staticListAccessCost);
		writeToFile("list/mtf" + dataType + "ListAccessCost", mtfListAccessCost);
		writeToFile("list/transpose" + dataType + "ListAccessCost", transposeListAccessCost);
		writeToFile("list/kInARow" + dataType + "ListAccessCost", kInARowListAccessCost);
		

		writeToFile("trees/static" + dataType + "TreesAccessCost", staticTreesAccessCost);
		writeToFile("trees/mtf" + dataType + "TreesAccessCost", mtfTreesAccessCost);
		writeToFile("trees/transpose" + dataType + "TreesAccessCost", transposeTreesAccessCost);
		writeToFile("trees/kInARow" + dataType + "TreesAccessCost", kInARowTreesAccessCost);
		

		writeToFile("rotation/mtf" + dataType + "RotationCost", mtfRotationCost);
		writeToFile("rotation/transpose" + dataType + "RotationCost", transposeRotationCost);
		writeToFile("rotation/kInARow" + dataType + "RotationCost", kInARowRotationCost);
		

		writeTotalToFile("total/mtf" + dataType + "TotalCost", mtfTreesAccessCost, mtfRotationCost);
		writeTotalToFile("total/transpose" + dataType + "TotalCost", transposeTreesAccessCost, transposeRotationCost);
		writeTotalToFile("total/kInARow" + dataType + "TotalCost", kInARowTreesAccessCost, kInARowRotationCost);
		
		
		writeHistogramToFile("histogram/" + dataType + "List", staticListAccessCost, mtfListAccessCost, transposeListAccessCost, kInARowListAccessCost);
		writeTreeHistogramToFile("histogram/" + dataType + "Trees", staticTreesAccessCost, mtfTreesAccessCost, transposeTreesAccessCost, kInARowTreesAccessCost);
		writeRotationHistogramToFile("histogram/" + dataType + "Rotation", mtfRotationCost, transposeRotationCost, kInARowRotationCost);
		
		writeTableToFile("table/" + dataType + "List", staticListAccessCost, mtfListAccessCost, transposeListAccessCost, kInARowListAccessCost);
		writeTreeTableToFile("table/" + dataType + "Trees", staticTreesAccessCost, mtfTreesAccessCost, transposeTreesAccessCost, kInARowTreesAccessCost);
		writeRotationTableToFile("table/" + dataType + "Rotation", mtfRotationCost, transposeRotationCost, kInARowRotationCost);
	
		
		flushCostLists();
		
	}
}
