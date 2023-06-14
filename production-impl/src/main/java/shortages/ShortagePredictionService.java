package shortages;

import external.CurrentStock;

import java.time.LocalDate;

public class ShortagePredictionService {
    private final ShortagePredictionRepository repository;

    public ShortagePredictionService(ShortagePredictionRepository repository) {
        this.repository = repository;
    }

    public Shortages invoke(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {
        ShortagePrediction prediction = repository.get(productRefNo, today, daysAhead, stock);

        return prediction.predict();
    }
}
