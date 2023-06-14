package shortages;

public interface LevelOnDeliveryCalculation {
    LevelOnDeliveryCalculation atDayStart = (long produced, long level, long demand) -> level - demand;
    LevelOnDeliveryCalculation tillEndOfDay = (long produced, long level, long demand) -> level - demand + produced;
    LevelOnDeliveryCalculation error = (long produced, long level, long demand) -> {
        throw new UnsupportedOperationException();
    };

    long calculate(long produced, long level, long demand);
}
