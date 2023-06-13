package shortages;

import dao.DemandDao;

import java.time.LocalDate;

public class DemandsRepository {
    private final DemandDao demands;

    public DemandsRepository(DemandDao demands) {
        this.demands = demands;
    }

    public Demands findFrom(LocalDate day, String productRefNo) {
        return new Demands(demands.findFrom(day.atStartOfDay(), productRefNo));
    }
}
