package project.algorithm.online;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.ZipfDistribution;

public class InputGenerator {
	int num = 26;
	int sequenceLength;
	int count_state[];
	double percentages[];
	char states[];
	
	public InputGenerator(int sL)
	{
		sequenceLength = sL;
		states = new char[num];
		
		for(int i = 0; i < states.length; i++)
		{
			states[i] = (char)(97+i);
		}
	}
	
	public ArrayList<Character> zipfGenerator(double skew)
	{
		return zipfInput(skew);
	}

	public ArrayList<Character> markovLowSelfLoopGenerator()
	{
		return markovInput(0.9);
	}

	public ArrayList<Character> markovMediumSelfLoopGenerator()
	{
		return markovInput(0.5);
	}

	public ArrayList<Character> markovHighSelfLoopGenerator()
	{
		return markovInput(0.1);
	}
	
	public ArrayList<Character> zipfInput(double skew)
	{
		ArrayList<Character> sequence = new ArrayList<Character>();

		ZipfDistribution zipf = new ZipfDistribution(num, skew);
		
		
		double probabilities[][] = new double [num][num];
	
		for(int i = 0; i < probabilities.length; i++)
		{
			for(int j = 0; j < probabilities[i].length; j++)
			{
				probabilities[i][j] = zipf.probability(j+1);
			}
			
		}
		
		int states_int[] = new int [states.length];
		for(int i = 0; i < states_int.length; i++)
		{
			states_int[i] = i;
		}
		
	
		count_state = new int [states.length];
	
		int current = 0;
		count_state[0] = 1;
		sequence.add(states[current]);
		for(int i =0; i < sequenceLength; i++)
		{
			EnumeratedIntegerDistribution dist   = new EnumeratedIntegerDistribution(states_int, probabilities[current]);
			int idx = dist.sample();
			count_state[idx]++;
			current = idx;
			sequence.add(states[current]);
		}
		
		double percentageTotal = 0;
		percentages = new double [states.length];
		for(int i = 0; i < states.length; i++)
		{
			percentages[i] = count_state[i]*100.0/sequenceLength;
			percentageTotal += percentages[i];
			System.out.println(states[i] + ": " + count_state[i] + " Percentage: " + percentages[i] + "%");
		}	
		System.out.println(percentageTotal);
		for(int i =0; i < sequence.size(); i++)
			System.out.print(sequence.get(i));
		System.out.println();
		return sequence;
	}
	
	public ArrayList<Character> markovInput(double probability)
	{
		ArrayList<Character> sequence = new ArrayList<Character>();
		
		
		double probabilities[][] = new double [num][num];
	
		for(int i = 0; i < probabilities.length; i++)
		{
			double sum =0;
			for(int j = 0; j < probabilities[i].length; j++)
			{
				if(i != j)
				{
					probabilities[i][j] = probability/(num-1); 
				}
				sum += probabilities[i][j];
			}
			probabilities[i][i] = 1-sum; // to resolve for float errors
			
		}
		int states_int[] = new int [states.length];
		for(int i = 0; i < states_int.length; i++)
		{
			states_int[i] = i;
		}
		
	
		count_state = new int [states.length];
	
		int current = 0;
		count_state[0] = 1;
		sequence.add(states[current]);
		for(int i =0; i < sequenceLength; i++)
		{
			EnumeratedIntegerDistribution dist   = new EnumeratedIntegerDistribution(states_int, probabilities[current]);
			int idx = dist.sample();
			count_state[idx]++;
			current = idx;
			sequence.add(states[current]);
		}
		double percentageTotal = 0;
		percentages = new double [states.length];
		for(int i = 0; i < states.length; i++)
		{
			percentages[i] = count_state[i]*100.0/sequenceLength;
			percentageTotal += percentages[i];
			System.out.println(states[i] + ": " + count_state[i] + " Percentage: " + percentages[i] + "%");
		}	
		System.out.println(percentageTotal);
		for(int i =0; i < sequence.size(); i++)
			System.out.print(sequence.get(i));
		System.out.println();
		return sequence;
	}
	
	public int[] getCountState()
	{
		return count_state;
	}
	
	public double[] getPercentages()
	{
		return percentages;
	}
	
	public char[] getStates()
	{
		return states;
	}
	
}
