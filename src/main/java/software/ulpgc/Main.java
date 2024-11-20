package software.ulpgc;

import software.ulpgc.control.HistogramGenerator;
import software.ulpgc.control.TitleLoader;
import software.ulpgc.control.io.SQLiteTitleReader;
import software.ulpgc.model.Title;
import software.ulpgc.view.Histogram;
import software.ulpgc.view.MainFrame;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("title.basics.tsv");
        File database = new File("database.db");

        TitleLoader loader = new TitleLoader(file, database);
        loader.execute();

        Histogram histogram = generateHistogram(database);
        display(histogram, database);
    }

    private static void display(Histogram histogram, File dbFile) {
        MainFrame mainFrame = new MainFrame(dbFile);
        mainFrame.put(histogram);
        mainFrame.setVisible(true);
    }

    private static Histogram generateHistogram(File dbFile) {
        HistogramGenerator generator = new HistogramGenerator();

        try(SQLiteTitleReader reader = new SQLiteTitleReader(dbFile)){
            while(true){
                Title title = reader.read();
                if(title == null) break;
                generator.feed(title);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return generator.get();
    }
}
