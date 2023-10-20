package co.yorku.nutrifit.visualizer;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.event.IListener;
import org.jfree.data.category.DefaultCategoryDataset;

public abstract class IVisualizer implements IListener {

    // DefaultCategoryDataset will be stored in the child class for the implementation
    // so that they can be updated when the user updates the date range in the UI (IListener)

    public IVisualizer() {
        NutriFit.getInstance().getEventManager().subscribe(this);
    }

    protected abstract DefaultCategoryDataset getDataset();

    public void buildBarGraph() {
        // do something
    }
    public void buildPiechart() {
        // do something
    }
}
