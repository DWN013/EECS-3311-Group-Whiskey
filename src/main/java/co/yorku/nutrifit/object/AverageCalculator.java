package co.yorku.nutrifit.object;

// Class AverageCalculator for calculating the average of numbers
public class AverageCalculator {

    // Private instance variables to store sum and total count
    private double sum;
    private int total;

    // Constructor initializing sum and total count to 0
    public AverageCalculator() {
        this.sum = 0;
        this.total = 0;
    }

    // Method to add a number to the sum and increment the count
    public void add(double num) {
        this.sum += num; // Add the number to the sum
        total++; // Increment the count
    }

    // Method to calculate the average based on sum and total count
    public double calculateAverage() {
        if (total <= 0) return 0; // Return 0 if no numbers were added
        return sum / (double) total; // Calculate and return the average
    }
}

