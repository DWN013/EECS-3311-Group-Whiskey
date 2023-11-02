package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.visualizer.IVisualizer;
import com.google.common.base.Preconditions;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class JFrameVisualizer extends NutrifitWindow {

    private IVisualizer iVisualizer;

    public JFrameVisualizer(String windowName, IVisualizer iVisualizer) {
        super(windowName, new GridLayout(3, 3));
        Preconditions.checkNotNull(iVisualizer, "A Visualizer be provided");
        this.iVisualizer = iVisualizer;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Unregister this listener as the window is now closed
                NutriFit.getInstance().getEventManager().unsubscribe(iVisualizer);
                super.windowClosing(e);
            }
        });

        this.addDateRangeButtons();
    }

    // By Default we will show the last 7 days
    private void addDateRangeButtons() {

        addLabel("From Date:", 1);
        JDateChooser fromDate = new JDateChooser();
        fromDate.setDateFormatString("yyyy-MM-dd");
        fromDate.setDate(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)));
        this.addComponent(fromDate, 1);
        addLabel("", 1); // Empty Label

        addLabel("To Date:", 1);
        JDateChooser toDate = new JDateChooser();
        toDate.setDateFormatString("yyyy-MM-dd");
        toDate.setDate(new Date(System.currentTimeMillis()));
        this.addComponent(toDate, 1);
        addButton("Update Date Range", event -> NutriFit.getInstance().getEventManager().notify(fromDate.getDate(), toDate.getDate()),1);
    }

    public IVisualizer getiVisualizer() {
        return iVisualizer;
    }
}
