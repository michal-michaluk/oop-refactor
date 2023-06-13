package shortages;

import enums.DeliverySchema;

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

    public record DailyDemand(long level, DeliverySchema schema) {

        public long levelOnDelivery(long produced, long level) {
            if (schema() == DeliverySchema.atDayStart) {
                return level - this.level();
            } else if (schema() == DeliverySchema.tillEndOfDay) {
                return level - this.level() + produced;
            } else if (schema() == DeliverySchema.every3hours) {
                // TODO WTF ?? we need to rewrite that app :/
                throw new UnsupportedOperationException();
            } else {
                // TODO implement other variants
                throw new UnsupportedOperationException();
            }
        }
    }
}
