package shortages;

import dao.DemandDao;
import entities.DemandEntity;
import tools.Util;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DemandsRepository {
    private final DemandDao demands;

    public DemandsRepository(DemandDao demands) {
        this.demands = demands;
    }

    public Demands findFrom(LocalDate day, String productRefNo) {
        List<DemandEntity> entities = demands.findFrom(day.atStartOfDay(), productRefNo);
        Map<LocalDate, Demands.DailyDemand> map = entities.stream()
                .collect(Collectors.toMap(
                        DemandEntity::getDay,
                        demand -> new Demands.DailyDemand(
                                Util.getLevel(demand),
                                Util.getDeliverySchema(demand)
                        )));
        return new Demands(map);
    }
}
