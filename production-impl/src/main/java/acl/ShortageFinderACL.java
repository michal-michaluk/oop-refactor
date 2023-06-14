package acl;

import entities.ShortageEntity;
import external.CurrentStock;
import services.impl.ShortageFinder;
import shortages.*;

import java.time.LocalDate;
import java.util.List;

public class ShortageFinderACL {

    private final DemandsRepository demands;
    private final ProductionOutputsRepository productions;
    private final ShortageFinder oldModel;

    public ShortageFinderACL(DemandsRepository demand, ProductionOutputsRepository production, ShortageFinder oldModel) {
        this.demands = demand;
        this.productions = production;
        this.oldModel = oldModel;
    }

    /**
     * Production at day of expected delivery is quite complex:
     * We are able to produce and deliver just in time at same day
     * but depending on delivery time or scheme of multiple deliveries,
     * we need to plan properly to have right amount of parts ready before delivery time.
     * <p/>
     * Typical schemas are:
     * <li>Delivery at prod day start</li>
     * <li>Delivery till prod day end</li>
     * <li>Delivery during specified shift</li>
     * <li>Multiple deliveries at specified times</li>
     * Schema changes the way how we calculate shortages.
     * Pick of schema depends on customer demand on daily basis and for each product differently.
     * Some customers includes that information in callof document,
     * other stick to single schema per product. By manual adjustments of demand,
     * customer always specifies desired delivery schema
     * (increase amount in scheduled transport or organize extra transport at given time)
     */
    public List<ShortageEntity> findShortages(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {
        if (true) {
            return oldModel.findShortages(productRefNo, today, daysAhead, stock);
        } else {

            ShortagePredictionService service = new ShortagePredictionService(
                    new ShortagePredictionRepository(demands, productions)
            );


            Shortages shortages = service.invoke(productRefNo, today, daysAhead, stock);

            return shortages.toEntities();
        }
    }

}
