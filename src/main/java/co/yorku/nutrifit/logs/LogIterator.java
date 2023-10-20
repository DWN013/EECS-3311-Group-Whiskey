package co.yorku.nutrifit.logs;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LogIterator {

    private int currentPosition;
    private List<Log> logs;

    public LogIterator(List<Log> logs) {
        this.logs = logs;
        this.currentPosition = 0;
    }

    public boolean hasNext() {
        return currentPosition < logs.size();
    }

    public Log getNext() {
        if (!hasNext()) {
            return null;
        }

        Log log = logs.get(currentPosition);
        currentPosition++;
        return log;
    }

    public int getTotalEntries() {
        return this.logs.size();
    }

    public void reset() {
        this.currentPosition = 0;
    }

    public void sortByDateAscending() {
        this.logs.sort(Comparator.comparing(Log::getDate));
    }


    public void sortByDateDescending() {
        this.sortByDateAscending();
        Collections.reverse(this.logs);
    }

}
