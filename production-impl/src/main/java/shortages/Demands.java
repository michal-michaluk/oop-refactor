package shortages;

import java.time.LocalDate;
import java.util.Map;

public class Demands {

    private final Map<LocalDate, DailyDemand> demands;

    public Demands(Map<LocalDate, DailyDemand> demands) {
        this.demands = demands;
    }

    public boolean hasNoDemand(LocalDate day) {
        return !demands.containsKey(day);
    }

    public DailyDemand get(LocalDate day) {
        return demands.getOrDefault(day, null);
    }

    public record DailyDemand(long level, LevelOnDeliveryCalculation strategy) {

        public long levelOnDelivery(long produced, long level) {
            return strategy.calculate(produced, level, this.level);
        }
    }
}
