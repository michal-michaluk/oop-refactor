package shortages;

import dao.ProductionDao;
import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductionOutputsRepository {
    private final ProductionDao productions;

    public ProductionOutputsRepository(ProductionDao productions) {
        this.productions = productions;
    }

    public ProductionOutputs findFromTime(String productRefNo, LocalDate today) {
        List<ProductionEntity> entities = productions.findFromTime(productRefNo, today.atStartOfDay());

        Map<LocalDate, Long> outputs = Collections.unmodifiableMap(
                entities.stream()
                        .collect(Collectors.groupingBy(
                                production -> production.getStart().toLocalDate(),
                                Collectors.summingLong(ProductionEntity::getOutput)
                        ))
        );
        return new ProductionOutputs(outputs);
    }
}
