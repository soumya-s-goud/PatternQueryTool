package com.homework.patternquerytool;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class for managing and querying PatternCall objects. Supports adding
 * PatternCall instances, in-memory retrieval, and saving/loading from text or
 * serialized files.
 * 
 * <p>
 * Provides multiple lookup options by ID, name, and file path. Also includes
 * options to store data in plain text or serialized format for flexibility in
 * storage and retrieval.
 * </p>
 * 
 * @author soumya-s-goud
 */
public class PatternCallRepository {

	private final List<PatternCall> patternCallsList;
	private final Map<Integer, PatternCall> idIndexMap; // Maps id to PatternCall
	private final Map<String, List<PatternCall>> nameIndexMap; // Maps name to PatternCalls
	private final Map<String, List<PatternCall>> pathIndexMap; // Maps patternFile to PatternCalls

	/**
	 * Initializes an empty repository for storing and querying PatternCall objects.
	 */
	public PatternCallRepository() {
		patternCallsList = new ArrayList<>();
		idIndexMap = new HashMap<>();
		nameIndexMap = new HashMap<>();
		pathIndexMap = new HashMap<>();
	}

	/**
	 * Adds a PatternCall instance to the repository and indexes it by id, name, and
	 * file path.
	 *
	 * @param patternCall the PatternCall to add
	 */
	public void addPatternCall(PatternCall patternCall) {
		if (patternCall == null) {
			throw new IllegalArgumentException("PatternCall cannot be null");
		}

		// Check if ID already exists
		if (idIndexMap.containsKey(patternCall.getId())) {
			return; // Do not add if the ID already exists
		}

		// Add to the list and indexes since it's unique
		patternCallsList.add(patternCall);
		idIndexMap.put(patternCall.getId(), patternCall);
		nameIndexMap.computeIfAbsent(patternCall.getName(), k -> new ArrayList<>()).add(patternCall);
		pathIndexMap.computeIfAbsent(patternCall.getPatternFile(), k -> new ArrayList<>()).add(patternCall);
	}

	/**
	 * Retrieves a PatternCall by its unique identifier.
	 *
	 * @param id the unique identifier of the PatternCall
	 * @return the PatternCall with the specified id, or null if not found
	 */
	public PatternCall getPatternCallById(int id) {
		return idIndexMap.get(id);
	}

	/**
	 * Retrieves all PatternCall objects with the specified name.
	 *
	 * @param name the name of the PatternCalls to retrieve
	 * @return a list of PatternCalls with the given name, or an empty list if none
	 *         found
	 */
	public List<PatternCall> getPatternCallsByName(String name) {
		return nameIndexMap.getOrDefault(name, List.of());
	}

	/**
	 * Retrieves all PatternCall objects with the specified file path.
	 *
	 * @param patternFile the file path of the PatternCalls to retrieve
	 * @return a list of PatternCalls with the specified file path, or an empty list
	 *         if none found
	 */
	public List<PatternCall> getPatternCallsByPath(String patternFile) {
		return pathIndexMap.getOrDefault(patternFile, List.of());
	}

	/**
	 * Retrieves all PatternCall objects stored in the repository.
	 *
	 * @return a list of all PatternCalls in the repository
	 */
	public List<PatternCall> getPatternCalls() {
		return patternCallsList;
	}

	/**
	 * Retrieves all PatternCall objects stored in the repository.
	 *
	 * @return a collection of all PatternCall objects
	 */
	public Collection<PatternCall> getAllPatternCalls() {
		return idIndexMap.values();
	}

	/**
	 * Reads pattern calls from a specified plain text file and stores them in
	 * memory.
	 * 
	 * @param filePath the path to the text file to read from
	 * @throws IOException if an I/O error occurs during file reading
	 */
	public void readFromFile(String filePath) throws IOException {
		List<String> lines = Files.readAllLines(Path.of(filePath));
		Set<Integer> seenIds = new HashSet<>(); // To track added IDs

		for (String line : lines) {
			String[] parts = line.split(",");
			if (parts.length < 4)
				continue; // Skip invalid lines

			try {
				int id = Integer.parseInt(parts[0].trim());
				String name = parts[1].trim();
				String patternFile = parts[2].trim();

				// Custom logic to validate the boolean value
				String booleanString = parts[3].trim();
				if (!booleanString.equalsIgnoreCase("true") && !booleanString.equalsIgnoreCase("false")) {
					throw new IllegalArgumentException("Invalid boolean value: " + booleanString);
				}
				boolean isActive = Boolean.parseBoolean(booleanString);

				// Check for duplicate ID
				if (!seenIds.contains(id)) {
					seenIds.add(id); // Add the ID to the seen set
					PatternCall patternCall = new PatternCall(id, name, patternFile, isActive);
					addPatternCall(patternCall); // This will add the patternCall only if ID is unique

				}
			} catch (IllegalArgumentException e) {
				// Handle the exception (could log or simply ignore it)
				throw e; // Re-throw the exception to let the test case catch it
			}
		}
	}

	/**
	 * Writes the current pattern calls in memory to a specified plain text file.
	 * 
	 * @param filePath the path to the text file to write to
	 * @throws IOException if an I/O error occurs during file writing
	 */
	public void writeToFile(String filePath) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (PatternCall patternCall : patternCallsList) {
				writer.write(patternCall.getId() + "," + patternCall.getName() + "," + patternCall.getPatternFile()
						+ "," + patternCall.isCalled());
				writer.newLine();
			}
		}
	}
}
