package shortages;

import external.CurrentStock;

import java.time.LocalDate;

public class ShortagePrediction {
    private final String productRefNo;
    private final CurrentStock stock;
    private final DateRange dates;
    private final ProductionOutputs outputs;
    private final Demands demandsPerDay;

    public ShortagePrediction(String productRefNo, CurrentStock stock, DateRange dates, ProductionOutputs outputs, Demands demandsPerDay) {
        this.productRefNo = productRefNo;
        this.stock = stock;
        this.dates = dates;
        this.outputs = outputs;
        this.demandsPerDay = demandsPerDay;
    }

    public Shortages predict() {
        long level = stock.getLevel();
        Shortages shortages = Shortages.forProduct(productRefNo);
        for (LocalDate day : dates) {
            long produced = outputs.getLevel(day);
            if (demandsPerDay.hasNoDemand(day)) {
                level += produced;
                continue;
            }
            Demands.DailyDemand demand = demandsPerDay.get(day);
            long levelOnDelivery = demand.levelOnDelivery(produced, level);

            if (levelOnDelivery < 0) {
                shortages.add(day, levelOnDelivery);
            }
            long endOfDayLevel = level + produced - demand.level();
            level = endOfDayLevel >= 0 ? endOfDayLevel : 0;
        }
        return shortages;
    }
}
