package shortages;

import java.time.LocalDate;

public interface ProductionOutputsRepository {
    ProductionOutputs findFromTime(String productRefNo, LocalDate today);
}
