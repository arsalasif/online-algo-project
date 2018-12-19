package project.algorithm.online;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.ZipfDistribution;

// Class to generate input values and access sequences
public class InputGenerator {
	int num;
	Integer count_state[];
	ArrayList<Double> percentages;
	Integer states[];
	
	public InputGenerator(int n)
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
	
	// Generates zipfian access sequence given a skew factor and sequence length
	public int[] zipfGenerator(double skew, int sequenceLength)
	{
		return zipfInput(skew, sequenceLength);
	}

	// Generates Markov high self loop access sequence given sequence length
	public int[] markovHighSelfLoopGenerator(int sequenceLength)
	{
		return markovInput(0.1, sequenceLength);
	}
	
	// Generates Markov medium self loop access sequence given sequence length
	public int[] markovMediumSelfLoopGenerator(int sequenceLength)
	{
		return markovInput(0.5, sequenceLength);
	}

	// Generates Markov low self loop access sequence given sequence length
	public int[] markovLowSelfLoopGenerator(int sequenceLength)
	{
		return markovInput(0.9, sequenceLength);
	}

	
	// Method to generate zipfian sequence
	private int[] zipfInput(double skew, int sequenceLength)
	{
		int[] sequence = new int[sequenceLength];

		// Create ZipfDistribution from Java Math library, given skew factor and number of input
		ZipfDistribution zipf = new ZipfDistribution(num, skew);
		
		double probabilities[][] = new double [num][num];

		// Get probabilities based on rank from zipfian distribution
		double zipfian[] = new double[num];
		for(int i = 0; i < num; i++)
		{
			zipfian[i] = zipf.probability(i+1);
		}
		
		// Add probabilities to state transitions
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
		

		// Keeps frequency count of states
		count_state = new Integer[num];
		for(int i = 0; i < num; i++)
			count_state[i] = 0;
	
		int current = 0;
		count_state[0] = 1;
		sequence[0] = states[0];
		// Generate access sequence
		for(int i = 1; i < sequenceLength; i++)
		{
			// Use EnumeratedIntegerDistribution to get state transition 
			// Based on state id and probability of going from that state to all other
			EnumeratedIntegerDistribution dist   = new EnumeratedIntegerDistribution(states_int, probabilities[current]);
			// Sample a state transition and move to that state
			int idx = dist.sample();
			count_state[idx]++;
			// Assign current state to that state
			current = idx;
			// Add that state in access sequence
			sequence[i] = states[current];
		}
		// Get frequency percentages from freqeuncy counts
		percentages = new ArrayList<Double>();
		for(int i = 0; i < num; i++)
		{
			percentages.add(count_state[i]*100.0/sequenceLength);
		}	
		return sequence;
	}
	
	private int[] markovInput(double probability, int sequenceLength)
	{
		int[] sequence = new int[sequenceLength];
		
		double probabilities[][] = new double [num][num];

		// Add probabilities to state transitions
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


		// Keeps frequency count of states
		count_state = new Integer[num];
		for(int i = 0; i < num; i++)
			count_state[i] = 0;
	
		int current = 0;
		count_state[0] = 1;
		sequence[0] = states[0];
		// Generate access sequence
		for(int i = 1; i < sequenceLength; i++)
		{
			// Use EnumeratedIntegerDistribution to get state transition 
			// Based on state id and probability of going from that state to all other
			EnumeratedIntegerDistribution dist   = new EnumeratedIntegerDistribution(states_int, probabilities[current]);
			// Sample a state transition and move to that state
			int idx = dist.sample();
			count_state[idx]++;
			// Assign current state to that state
			current = idx;
			// Add that state in access sequence
			sequence[i] = states[current];
		}
		// Get frequency percentages from freqeuncy counts
		percentages = new ArrayList<Double>();
		for(int i = 0; i < num; i++)
		{
			percentages.add(count_state[i]*100.0/sequenceLength);
		}	
		return sequence;
	}
	
	// Get frequency counts of states
	public Integer[] getCountState()
	{
		return count_state;
	}

	// Get percentage of frequencies of states
	public ArrayList<Double> getPercentages()
	{
		return percentages;
	}

	// Get states
	public Integer[] getStates()
	{
		return states;
	}
	
}
