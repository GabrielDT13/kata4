package software.ulpgc.control;

import software.ulpgc.control.io.SQLiteTitleWriter;
import software.ulpgc.control.io.TsvTitleReader;
import software.ulpgc.model.Title;

import java.io.File;

public class TitleLoader {
    private final File file;
    private final File dbFile;

    public TitleLoader(File file, File dbFile) {
        this.file = file;
        this.dbFile = dbFile;
    }

    public void execute() {
        try(TsvTitleReader reader = new TsvTitleReader(file); SQLiteTitleWriter writer = new SQLiteTitleWriter(dbFile)){
            boolean empty = writer.isEmpty();
            while(true){
                Title title = reader.read();
                if(title == null) break;
                if(empty) writer.write(title);
                else break;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
