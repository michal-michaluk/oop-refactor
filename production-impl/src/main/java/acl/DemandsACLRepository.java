package acl;

import dao.DemandDao;
import entities.DemandEntity;
import enums.DeliverySchema;
import shortages.Demands;
import shortages.DemandsRepository;
import shortages.LevelOnDeliveryCalculation;
import tools.Util;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DemandsACLRepository implements DemandsRepository {
    private final DemandDao demands;

    public DemandsACLRepository(DemandDao demands) {
        this.demands = demands;
    }

    @Override
    public Demands findFrom(LocalDate day, String productRefNo) {
        List<DemandEntity> entities = demands.findFrom(day.atStartOfDay(), productRefNo);
        Map<LocalDate, Demands.DailyDemand> map = entities.stream()
                .collect(Collectors.toMap(
                        DemandEntity::getDay,
                        demand -> new Demands.DailyDemand(
                                Util.getLevel(demand),
                                pick(Util.getDeliverySchema(demand))
                        )));
        return new Demands(map);
    }

    private static LevelOnDeliveryCalculation pick(DeliverySchema schema) {
        return Map.of(
                DeliverySchema.atDayStart, LevelOnDeliveryCalculation.atDayStart,
                DeliverySchema.tillEndOfDay, LevelOnDeliveryCalculation.tillEndOfDay
        ).getOrDefault(schema, LevelOnDeliveryCalculation.error);
    }
}
