package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Assignment8Services {
	private List<CompletableFuture<List<Integer>>> gotNum = new ArrayList<>();
	private List<Integer> joinedList = new ArrayList<>();
	private ExecutorService cachedThread = Executors.newCachedThreadPool();
	private Map<Integer, Long> numberCount;
	Assignment8 assignment8 = new Assignment8();
	
	public void retrievedNumbers() {
		for (int i = 0; i < 1000; i++) {
			CompletableFuture<List<Integer>> list =
					CompletableFuture.supplyAsync(() -> assignment8.getNumbers(), cachedThread);
			gotNum.add(list);								
		}
				
		cachedThread.shutdown();
		
	}
	
	public void combineLists() {
		joinedList = gotNum.stream()
						   .flatMap(futureList -> futureList.join().stream())
						   .toList();
	}
	
	public void countOccurrencesOfNumbers() {
		numberCount = joinedList.stream()
				.sorted()
				.collect(Collectors.groupingBy(number -> number, Collectors.counting()));
		}
	
	public void printOutNicely() {		
		numberCount.forEach((number, count) ->
		System.out.println("Number " + number + " repeats " + count + " times."));
	}
}
