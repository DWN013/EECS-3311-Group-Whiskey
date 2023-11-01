package co.yorku.nutrifit.ui;

import co.yorku.nutrifit.NutriFit;
import co.yorku.nutrifit.visualizer.IVisualizer;
import com.google.common.base.Preconditions;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class JFrameVisualizer extends NutrifitWindow {

    private IVisualizer iVisualizer;

    public JFrameVisualizer(String windowName, IVisualizer iVisualizer) {
        super(windowName, new GridLayout(2, 3));
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
    }

    public IVisualizer getiVisualizer() {
        return iVisualizer;
    }
}
