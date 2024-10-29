package com.homework.patternquerytool;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides services for querying PatternCall objects stored in the
 * PatternCallRepository.
 * 
 * <p>
 * It encapsulates various methods to retrieve pattern calls based on their ID,
 * name, file path, and the 'called' status.
 * </p>
 * 
 * @author soumya-s-goud
 */
public class PatternCallQueryService {

	private final PatternCallRepository repository;

	/**
	 * Constructs a PatternCallQueryService with the specified
	 * PatternCallRepository.
	 *
	 * @param repository the repository used for storing and retrieving PatternCall
	 *                   objects
	 */
	public PatternCallQueryService(PatternCallRepository repository) {
		this.repository = repository;
	}

	/**
	 * Retrieves a PatternCall by its unique identifier.
	 *
	 * @param id the unique identifier of the PatternCall
	 * @return the PatternCall with the specified id, or null if not found
	 */
	public PatternCall getPatternCallById(int id) {
		return repository.getPatternCallById(id);
	}

	/**
	 * Retrieves all PatternCalls that match the specified name.
	 *
	 * @param name the name of the PatternCalls to retrieve
	 * @return a list of PatternCalls with the specified name, or an empty list if
	 *         none found
	 */
	public List<PatternCall> getPatternCallsByName(String name) {
		return repository.getPatternCallsByName(name);
	}

	/**
	 * Retrieves all PatternCalls that match the specified file path.
	 *
	 * @param patternFile the file path of the PatternCalls to retrieve
	 * @return a list of PatternCalls with the specified file path, or an empty list
	 *         if none found
	 */
	public List<PatternCall> getPatternCallsByPath(String patternFile) {
		return repository.getPatternCallsByPath(patternFile);
	}

	/**
	 * Retrieves all PatternCalls that are not marked as called (i.e., skipped).
	 *
	 * @return a list of PatternCalls that are skipped
	 */
	public List<PatternCall> getSkippedPatternCalls() {
		return repository.getPatternCalls().stream().filter(p -> !p.isCalled()).collect(Collectors.toList());
	}

	/**
	 * Retrieves all PatternCalls that are marked as called.
	 *
	 * @return a list of PatternCalls that are called
	 */
	public List<PatternCall> getCalledPatternCalls() {
		return repository.getPatternCalls().stream().filter(PatternCall::isCalled).collect(Collectors.toList());
	}
}
