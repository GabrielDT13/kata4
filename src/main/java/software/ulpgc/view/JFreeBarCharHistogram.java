package software.ulpgc.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class JFreeBarCharHistogram extends JPanel implements HistogramDisplay {
    @Override
    public void display(Histogram histogram) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(String key: histogram.labels()){
            dataset.addValue(histogram.valueOf(key), "Frequency", key);
        }

        JFreeChart barchart = ChartFactory.createBarChart(
                histogram.title(),
                "Categories",
                "Frequecy",
                dataset
        );

        add(new ChartPanel(barchart));
    }
}
