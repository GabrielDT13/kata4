package software.ulpgc.control.io;

import software.ulpgc.model.Title;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TsvTitleReader implements TitleReader, AutoCloseable {
    private final File file;
    private BufferedReader reader;

    public TsvTitleReader(File file) throws IOException {
        this.file = file;
        this.reader = new BufferedReader(new FileReader(file));
    }

    public Title read(){
        try {
            reader.readLine();
            return readWith(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Title readWith(BufferedReader reader) throws IOException {
        TsvTitleDeserializer deserializer = new TsvTitleDeserializer();
        String line = reader.readLine();
        if(line==null) return null;
        return deserializer.deserialize(line);
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
