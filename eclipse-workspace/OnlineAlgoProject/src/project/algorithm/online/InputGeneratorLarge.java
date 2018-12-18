package project.algorithm.online;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.ZipfDistribution;

public class InputGeneratorLarge {
	int num;
	Integer count_state[];
	ArrayList<Double> percentages;
	Integer states[];
	
	public InputGeneratorLarge(int n)
	{
		num = n;
		states = new Integer[num];
		ArrayList<Integer> preShuffledList = new ArrayList<Integer>();
		
		for(int i = 0; i < num; i++)
		{
			preShuffledList.add(i+1);
		}
		Collections.shuffle(preShuffledList);
		states = preShuffledList.toArray(new Integer[preShuffledList.size()]);
	}
	
	public int[] zipfGenerator(double skew, int sequenceLength)
	{
		return zipfInput(skew, sequenceLength);
	}

	public int[] markovLowSelfLoopGenerator(int sequenceLength)
	{
		return markovInput(0.9, sequenceLength);
	}

	public int[] markovMediumSelfLoopGenerator(int sequenceLength)
	{
		return markovInput(0.5, sequenceLength);
	}

	public int[] markovHighSelfLoopGenerator(int sequenceLength)
	{
		return markovInput(0.1, sequenceLength);
	}
	
	private int[] zipfInput(double skew, int sequenceLength)
	{
		int[] sequence = new int[sequenceLength];

		ZipfDistribution zipf = new ZipfDistribution(num, skew);
		
		double probabilities[][] = new double [num][num];

		double zipfian[] = new double[num];
		for(int i = 0; i < num; i++)
		{
			zipfian[i] = zipf.probability(i+1);
//			System.out.printf("%.20f\n", zipfian[i]);
		}
		
		for(int i = 0; i < probabilities.length; i++)
		{
			for(int j = 0; j < probabilities[i].length; j++)
			{
				probabilities[i][j] = zipfian[j];
			}
			
		}

		int states_int[] = new int [num];
		for(int i = 0; i < states_int.length; i++)
		{
			states_int[i] = i;
		}
		

		count_state = new Integer[num];
		for(int i = 0; i < num; i++)
			count_state[i] = 0;
	
		int current = 0;
		count_state[0] = 1;
		sequence[0] = states[0];
		for(int i = 1; i < sequenceLength; i++)
		{
			EnumeratedIntegerDistribution dist   = new EnumeratedIntegerDistribution(states_int, probabilities[current]);
			int idx = dist.sample();
			count_state[idx]++;
			current = idx;
			sequence[i] = states[current];
//			System.out.print(states[current] +" ");
		}
//		System.out.println();
		percentages = new ArrayList<Double>();
		for(int i = 0; i < num; i++)
		{
			percentages.add(count_state[i]*100.0/sequenceLength);
//			System.out.println(states[i] + ": " + count_state[i] + " Percentage: " + percentages[i] + "%");
		}	
		return sequence;
	}
	
	private int[] markovInput(double probability, int sequenceLength)
	{
		int[] sequence = new int[sequenceLength];
		
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
		int states_int[] = new int [num];
		for(int i = 0; i < states_int.length; i++)
		{
			states_int[i] = i;
		}
		
	
		count_state = new Integer[num];
		for(int i = 0; i < num; i++)
			count_state[i] = 0;
	
		int current = 0;
		count_state[0] = 1;
		sequence[0] = states[0];
		for(int i = 1; i < sequenceLength; i++)
		{
			EnumeratedIntegerDistribution dist   = new EnumeratedIntegerDistribution(states_int, probabilities[current]);
			int idx = dist.sample();
			count_state[idx]++;
			current = idx;
			sequence[i] = states[current];
//			System.out.print(states[current] +" ");
		}
//		System.out.println();
		percentages = new ArrayList<Double>();
		for(int i = 0; i < num; i++)
		{
			percentages.add(count_state[i]*100.0/sequenceLength);
//			System.out.println(states[i]+ ": " + count_state[i] + " Percentage: " + percentages[i] + "%");
		}	
		return sequence;
	}
	
	
	public Integer[] getCountState()
	{
		return count_state;
	}
	
	public ArrayList<Double> getPercentages()
	{
		return percentages;
	}
	
	public Integer[] getStates()
	{
		return states;
	}
	
}
