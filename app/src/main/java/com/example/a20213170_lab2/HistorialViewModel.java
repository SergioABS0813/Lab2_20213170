// HistorialViewModel.java
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistorialViewModel extends ViewModel {
    private final List<String> tiemposString = new ArrayList<>();

    public List<String> getTiemposString() {
        return tiemposString;
    }
}
