package acl;

import external.CurrentStock;
import shortages.*;

import java.time.LocalDate;

public class ShortagePredictionACLRepository implements ShortagePredictionRepository {

    private final DemandsRepository demandDao;
    private final ProductionOutputsRepository productionDao;

    public ShortagePredictionACLRepository(DemandsRepository demandDao, ProductionOutputsRepository productionDao) {
        this.demandDao = demandDao;
        this.productionDao = productionDao;
    }

    @Override
    public ShortagePrediction get(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {
        DateRange dates = DateRange.of(today, daysAhead);
        ProductionOutputs outputs = productionDao.findFromTime(productRefNo, today);
        Demands demandsPerDay = demandDao.findFrom(today, productRefNo);

        return new ShortagePrediction(productRefNo, stock, dates, outputs, demandsPerDay);
    }
}
