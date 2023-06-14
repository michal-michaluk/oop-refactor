package shortages;

import java.time.LocalDate;

public interface DemandsRepository {
    Demands findFrom(LocalDate day, String productRefNo);
}
