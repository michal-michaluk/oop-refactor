package services.impl;

import dao.ProductionDao;
import shortages.ProductionOutputs;

import java.time.LocalDate;

public class ProductionOutputsRepository {
    private final ProductionDao productions;

    public ProductionOutputsRepository(ProductionDao productions) {
        this.productions = productions;
    }

    public ProductionOutputs findFromTime(String productRefNo, LocalDate today) {
        return new ProductionOutputs(productions.findFromTime(productRefNo, today.atStartOfDay()));
    }
}
