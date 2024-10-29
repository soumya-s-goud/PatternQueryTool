# Pattern Query Tool

This project manages pattern call tuples, allowing users to query and manipulate pattern calls based on specific criteria. 

## Project Structure

project-root 
├── src 
│ └── com 
│     └── homework 
│          └── patternquerytool 
│              ├── PatternCall.java # Class representing a pattern call 
│              ├── PatternCallRepository.java # Repository for managing pattern calls 
│              ├── PatternCallQueryService.java # Service for querying pattern calls 
│              └── Main.java # Main class for running the application 
├── test 
│   └── com 
│   │    └── homework 
│   │       └── patternquerytool 
│   │           ├── PatternCallTest.java # Unit tests for PatternCall class
│   │           ├── PatternCallRepositoryTest.java # Unit tests for PatternCallRepository class
│   │              └── PatternCallQueryServiceTest.java # Unit tests for PatternCallQueryService class 
│   └── resources 
│       └── valid_file.txt # Sample valid file for testing 
└── pom.xml # Maven Project Object Model file

## Requirements
- JDK 17
- Maven 3.8.6
- Junit 5
- Mockito 5.14.0

## Installation
1. Clone the repository/ Unzip the package **Software_Development_Task.zip**
    git clone https://github.com/soumya-s-goud/PatternQueryTool.git

    cd pattern-query-tool
2. Navigate to the project directory.

## Build and Run

1. **Build the Project**:

   **Without Maven**
   Compile the Java files manually.
   Run the main class directly in Eclipse.

   **Using Maven**
   Build the project by running the following command:
    ```bash
    mvn clean install
    ```

2. **Run Tests**:
   After a successful build, you can run the unit tests using the following command:
    ```bash
    mvn test
    ```
   This will execute all the unit tests and display the results.

## What are the benefits of your design?

- **Encapsulation of Operations**: The `PatternCallQueryService` class organizes all operations related to querying pattern calls, making the code well-structured and keeping query logic separate from data storage.

- **Object-Oriented Structure**: Using the `PatternCall` class to represent individual entries helps maintain modularity. This structure makes the code adaptable and easy to expand as new functionality is added.

- **Clear Query Methods**: Separate methods in `PatternCallQueryService` allow users to retrieve data based on specific criteria, improving code readability and usability.

- **Data Persistence**: File I/O is implemented to allow saving and reloading of pattern call data, which supports working with data beyond a single runtime session.


## Do you see improvement potential?
 
- **Implementing Thread Safety**: By making the system thread-safe, especially in a concurrent environment where multiple users may query or update pattern data simultaneously, we can prevent data inconsistency issues. This could involve synchronizing key methods or using thread-safe data structures to ensure smooth operation in a multi-threaded context.

- **Enhanced Error Handling**: Implementing more comprehensive error-handling mechanisms, especially for file read/write operations, could make the system more resilient to unexpected issues.
  
- **Optimizing for Large Data Sets**: For projects involving extensive data, efficiency could be improved by considering alternative data structures or algorithms that better handle large volumes.

## What assumptions did you make and what trade-offs did you consider?

- **Single-Threaded Operation**: The system is designed with a single-threaded environment in mind, assuming that operations will not be accessed concurrently. This allows for simpler code without needing to implement synchronization or thread-safe data structures. In future, this could be expanded to support multi-threaded or concurrent access, if needed.

- **In-Memory Data Handling**: The approach assumes that all pattern call records will fit comfortably in memory, which avoids the complexity of disk-based storage. This makes data access and manipulation faster, though it limits scalability for very large data sets.

- **Efficiency vs. Simplicity**: Retrieval methods (e.g., by ID, name, or path) are implemented with linear time complexity, \(O(n)\). This choice prioritizes ease of implementation and readability over raw efficiency. For this scope, the trade-off is acceptable, but optimization may be considered for larger data volumes.

## What is the complexity (Big-O notation) of the queries you provide?

- **Retrieving Pattern Calls (Current Approach)**: Currently, retrieving pattern calls by ID, name, or file path requires a linear search, resulting in a time complexity of \(O(n)\), where \(n\) is the number of pattern call records. This linear complexity arises from scanning the entire list for each query.

- **Optimizing with Constant-Time Lookups Using Maps**: By implementing a `Map` structure for ID-based lookups, retrieval complexity can be optimized to \(O(1)\) (constant time) for accessing records by ID. Here’s how this approach would work:
  - **Best and Average Case**: Utilizing a `HashMap` for ID-based lookups yields \(O(1)\) complexity in most cases, providing direct access without needing to search through other records.
  - **Worst Case**: In cases where hash collisions occur, performance could degrade, though it typically remains efficient and significantly outperforms a linear search. 

Using this approach with a `Map<Integer, PatternCall>` for ID retrieval, and similar mappings for name and file path lookups, results in both faster and more scalable access to records.

## Which part of your solution took the most time (e.g. design, coding, documentation) and why?
## Time Investment 

- The majority of the time was spent on design and coding, as it was essential to grasp the problem requirements thoroughly to implement a fitting solution.

 - Additionally, writing JUnit tests was a significant focus to ensure comprehensive coverage of various scenarios and branches. This step is critical for maintaining code reliability and identifying issues early. Documentation also required attention to ensure clarity for future developers.
