package com.homework.patternquerytool;

import java.util.List;

/**
 * Demonstrates the usage of PatternCallRepository and PatternCallQueryService
 * for managing and querying PatternCall objects.
 *
 * <p>
 * This main program initializes a PatternCallRepository, adds sample
 * PatternCall entries, and then uses PatternCallQueryService to retrieve and
 * display information based on various query criteria.
 * </p>
 * 
 * @author soumya-s-goud
 * 
 */
public class Main {

	/**
	 * The main method initializes the repository, adds PatternCall objects, and
	 * demonstrates various queries using PatternCallQueryService.
	 *
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {

		// Initialize the repository
		PatternCallRepository repository = new PatternCallRepository();

		// Create some PatternCall instances
		repository.addPatternCall(new PatternCall(42, "myPattern", "src/patterns/Functional.pat", false));
		repository.addPatternCall(new PatternCall(43, "anotherPattern", "src/patterns/Another.pat", true));
		repository.addPatternCall(new PatternCall(44, "myPattern", "src/patterns/MyPattern.pat", true));
		repository.addPatternCall(new PatternCall(45, "thirdPattern", "src/patterns/Third.pat", false));

		// Initialize the query service
		PatternCallQueryService queryService = new PatternCallQueryService(repository);

		// Retrieve and display a pattern call by ID
		PatternCall retrievedById = queryService.getPatternCallById(42);
		System.out.println("Retrieved by ID 42: " + retrievedById.getName());

		// Retrieve and display pattern calls by name
		List<PatternCall> callsByName = queryService.getPatternCallsByName("myPattern");
		System.out.println("Pattern calls with name 'myPattern':");
		for (PatternCall pc : callsByName) {
			System.out
					.println(" - ID: " + pc.getId() + ", File: " + pc.getPatternFile() + ", Called: " + pc.isCalled());
		}

		// Retrieve and display pattern calls by file path
		List<PatternCall> callsByPath = queryService.getPatternCallsByPath("src/patterns/Another.pat");
		System.out.println("Pattern calls with path 'src/patterns/Another.pat':");
		for (PatternCall pc : callsByPath) {
			System.out.println(" - ID: " + pc.getId() + ", Name: " + pc.getName() + ", Called: " + pc.isCalled());
		}

		// List and display skipped pattern calls
		List<PatternCall> skippedCalls = queryService.getSkippedPatternCalls();
		System.out.println("Skipped Pattern Calls:");
		for (PatternCall pc : skippedCalls) {
			System.out.println(" - ID: " + pc.getId() + ", Name: " + pc.getName());
		}

		// List and display called pattern calls
		List<PatternCall> calledCalls = queryService.getCalledPatternCalls();
		System.out.println("Called Pattern Calls:");
		for (PatternCall pc : calledCalls) {
			System.out.println(" - ID: " + pc.getId() + ", Name: " + pc.getName());
		}
	}
}
