package acl;

import dao.ProductionDao;
import entities.ProductionEntity;
import shortages.ProductionOutputs;
import shortages.ProductionOutputsRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductionOutputsACLRepository implements ProductionOutputsRepository {
    private final ProductionDao productions;

    public ProductionOutputsACLRepository(ProductionDao productions) {
        this.productions = productions;
    }

    @Override
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
