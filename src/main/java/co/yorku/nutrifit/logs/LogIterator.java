package co.yorku.nutrifit.logs;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * LogIterator class for iterating through a list of logs
 * Implementation of Iterator Design Pattern
 */

public class LogIterator {
    // Private instance variables
    private int currentPosition;
    private List<ILog> logs;

    // Constructor initializing a LogIterator object with a list of logs
    public LogIterator(List<ILog> logs) {
        this.logs = logs; // Assign the list of logs
        this.currentPosition = 0; // Set initial position to 0
    }

    // Method to check if there is a next log entry
    public boolean hasNext() {
        return currentPosition < logs.size();
    }

    // Method to retrieve the next log entry
    public ILog getNext() {
        if (!hasNext()) {
            return null;
        }

        ILog log = logs.get(currentPosition); // Get the log at current position
        currentPosition++; // Move to the next position
        return log; // Return the retrieved log
    }

    // Method to get the total number of log entries
    public int getTotalEntries() {
        return this.logs.size();
    }
    
    // Method to sort logs by date in ascending order
    public void sortByDateAscending() {
        this.logs.sort(Comparator.comparing(ILog::getDate));
    }

}

