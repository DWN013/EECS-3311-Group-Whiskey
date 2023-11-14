package co.yorku.nutrifit.object;

public class AverageCalculator {

    private double sum;
    private int total;

    public AverageCalculator() {
        this.sum = 0;
        this.total = 0;
    }

    public void add(double num) {
        this.sum+=num;
        total++;
    }

    public double calculateAverage() {
        if (total <= 0) return 0;
        return sum / (double) total;
    }
}
