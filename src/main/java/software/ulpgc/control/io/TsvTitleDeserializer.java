package software.ulpgc.control.io;

import software.ulpgc.model.Title;

public class TsvTitleDeserializer {
    public Title deserialize(String line){
        return deserialize(line.split("\t"));
    }

    private Title deserialize(String[] fields){
        return new Title(
                fields[0],
                typeOf(fields[1]),
                fields[2]
        );
    }

    private Title.TitleType typeOf(String field) {
        return Title.TitleType.valueOf(normalize(field));
    }

    private String normalize(String field) {
        String upperCase = field.toUpperCase();
        String aux = field.replace("-", "");
        return upperCase.toCharArray()[0]+aux.substring(1);
    }
}
