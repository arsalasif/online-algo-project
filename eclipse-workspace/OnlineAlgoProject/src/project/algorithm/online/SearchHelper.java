package project.algorithm.online;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class SearchHelper {
	InputGeneratorLarge igLarge;
	Integer[] states;
	int staticListAccessCost;
	int staticTreesAccessCost;
	int mtfListAccessCost;
	int mtfTreesAccessCost;
	int transposeListAccessCost;
	int transposeTreesAccessCost;
	int kInARowListAccessCost;
	int kInARowTreesAccessCost;
	
	int mtfTotalRotationCost;
	int transposeTotalRotationCost;
	int kInARowTotalRotationCost;
	
	ArrayList<Double> percentages;
	
	private class SortedInput
	{
	   private Integer input;
	   private Integer frequency;

	   public SortedInput(Integer input, Integer frequency){
	       this.input = input;
	       this.frequency = frequency;
	   }

	   public Integer getFrequency()
	   {
		   return frequency;
	   }
	   
	   public Integer getInput()
	   {
		   return input;
	   }
	}
	
	public SearchHelper(int num)
	{
		igLarge = new InputGeneratorLarge(num);
		states = igLarge.states;
	}
	
	public void searchZipf(double skew, int sequenceLength)
	{
		int[] sequence = igLarge.zipfGenerator(skew, sequenceLength);
		percentages = igLarge.percentages;
		searchLinkedList(sequence);
		searchTrees(sequence);
		
	}

	public void searchMarkovHighSelfLoop(int sequenceLength)
	{
		int[] sequence = igLarge.markovHighSelfLoopGenerator(sequenceLength);
		percentages = igLarge.percentages;
		searchLinkedList(sequence);
		searchTrees(sequence);
	}

	public void searchMarkovMediumSelfLoop(int sequenceLength)
	{
		int[] sequence = igLarge.markovMediumSelfLoopGenerator(sequenceLength);
		percentages = igLarge.percentages;
		searchLinkedList(sequence);
		searchTrees(sequence);
	}

	public void searchMarkovLowSelfLoop(int sequenceLength)
	{
		int[] sequence = igLarge.markovLowSelfLoopGenerator(sequenceLength);
		percentages = igLarge.percentages;
		searchLinkedList(sequence);
		searchTrees(sequence);
	}
	
	public void searchLinkedList(int[] sequence)
	{
		ArrayList<Integer> sorted_states;
		ArrayList<SortedInput> sortedInput = new ArrayList<SortedInput>();
		ArrayList<Integer> shuffled_states = new ArrayList<Integer>();
		
		for(int i = 0; i < states.length; i++)
		{
			sortedInput.add(new SortedInput(states[i], igLarge.count_state[i]));
			shuffled_states.add(states[i]);
		}
		
		sortedInput.sort(Comparator.comparingInt(SortedInput::getFrequency));

		sorted_states = new ArrayList<Integer>();

		for(int i = sortedInput.size()-1; i >= 0; i--)
		{
			sorted_states.add(sortedInput.get(i).getInput());
		}
		
//		Collections.shuffle(shuffled_states);
		
		LinkedListCustom list = new LinkedListCustom();
		LinkedListCustom mtfList = new LinkedListCustom();
		LinkedListCustom transposeList = new LinkedListCustom();
		LinkedListCustom kInARowList = new LinkedListCustom();
		for(int i = 0; i < sorted_states.size(); i++)
		{
			list.add(sorted_states.get(i));
			mtfList.add(shuffled_states.get(i));
			transposeList.add(shuffled_states.get(i));
			kInARowList.add(shuffled_states.get(i));
		}
		
		
		int accessCost = 0;
		int mtfAccessCost = 0;
		int transposeAccessCost = 0;
		int kInARowAccessCost = 0;
		int k = 2;
		int numberOfAccess = 0;
		for(int i = 0; i < sequence.length; i++)
		{
			accessCost += list.search(sequence[i]);
			mtfAccessCost += mtfList.searchMoveToFront(sequence[i]);
			transposeAccessCost += transposeList.searchTranspose(sequence[i]);
			
			if(i > 0 && sequence[i] == sequence[i-1])
			{
				numberOfAccess++;	
			}
			else 
				numberOfAccess = 0;
			if(numberOfAccess >= k)
			{
				kInARowAccessCost += kInARowList.searchMoveToFront(sequence[i]);
			}
			else 
				kInARowAccessCost += kInARowList.search(sequence[i]);
		}
		
		staticListAccessCost = accessCost;
		mtfListAccessCost = mtfAccessCost;
		transposeListAccessCost = transposeAccessCost;
		kInARowListAccessCost = kInARowAccessCost;
		
	}
	
	public void searchTrees(int[] sequence)
	{
		ArrayList<Integer> sorted_states;
		ArrayList<SortedInput> sortedInput = new ArrayList<SortedInput>();
		ArrayList<Integer> shuffled_states = new ArrayList<Integer>();
		
		for(int i = 0; i < states.length; i++)
		{
			sortedInput.add(new SortedInput(states[i], igLarge.count_state[i]));
			shuffled_states.add(states[i]);
		}
		
		sortedInput.sort(Comparator.comparingInt(SortedInput::getFrequency));

		sorted_states = new ArrayList<Integer>();
		
		for(int i = sortedInput.size()-1; i >= 0; i--)
		{
			sorted_states.add(sortedInput.get(i).getInput());
		}


//		System.out.println(Arrays.toString(states));
//		System.out.println(Arrays.toString(igLarge.count_state));
//		System.out.println(Arrays.toString(sorted_states.toArray()));
//		System.out.println();
//		Collections.shuffle(shuffled_states);
		
		BinarySearchTree bst = new BinarySearchTree();
		SplayTree mtfTree = new SplayTree();
		SplayTree transposeTree = new SplayTree();
		SplayTree kInARowTree = new SplayTree();
		for(int i = 0; i < sorted_states.size(); i++)
		{
			bst.insert(sorted_states.get(i));
			mtfTree.insert(shuffled_states.get(i));
			transposeTree.insert(shuffled_states.get(i));
			kInARowTree.insert(shuffled_states.get(i));
		}
		
		int accessCost = 0;
		int mtfAccessCost = 0;
		int transposeAccessCost = 0;
		int kInARowAccessCost = 0;
		int k = 2;
		int numberOfAccess = 0;
		int mtfRotationCost = 0;
		int transposeRotationCost = 0;
		int kInARowRotationCost = 0;
		for(int i = 0; i < sequence.length; i++)
		{
			accessCost += bst.search(sequence[i]);
			
			mtfAccessCost += mtfTree.searchMTF(sequence[i]);
			mtfRotationCost += mtfTree.rotationCost;
			
			transposeAccessCost += transposeTree.searchTranspose(sequence[i]);
			transposeRotationCost += transposeTree.rotationCost;
			
			if(i > 0 && sequence[i] == sequence[i-1])
			{
				numberOfAccess++;	
			}
			else 
				numberOfAccess = 0;
			if(numberOfAccess >= k)
			{
				kInARowAccessCost += kInARowTree.searchMTF(sequence[i]);
				kInARowRotationCost += kInARowTree.rotationCost;
			}
			else 
				kInARowAccessCost += kInARowTree.search(sequence[i]);
		}
		
		staticTreesAccessCost = accessCost;
		mtfTreesAccessCost = mtfAccessCost;
		transposeTreesAccessCost = transposeAccessCost;
		kInARowTreesAccessCost = kInARowAccessCost;
		

		mtfTotalRotationCost = mtfRotationCost;
		transposeTotalRotationCost = transposeRotationCost;
		kInARowTotalRotationCost = kInARowRotationCost;
	}
	
	
}
