package software.ulpgc.view;

import java.util.List;

public interface Histogram {
    String title();
    List<String> labels();
    int valueOf(String label);
}
