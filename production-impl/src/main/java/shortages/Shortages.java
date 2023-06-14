package shortages;

import entities.ShortageEntity;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Shortages {
    private final String productRefNo;
    private final List<ShortageEntity> gap = new LinkedList<>();

    public Shortages(String productRefNo) {
        this.productRefNo = productRefNo;
    }

    public static Shortages forProduct(String productRefNo) {
        return new Shortages(productRefNo);
    }

    public void add(LocalDate day, long levelOnDelivery) {
        ShortageEntity entity = new ShortageEntity();
        entity.setRefNo(productRefNo);
        entity.setFound(LocalDate.now());
        entity.setAtDay(day);
        entity.setMissing(-levelOnDelivery);
        gap.add(entity);
    }

    public List<ShortageEntity> toEntities() {
        return gap;
    }
}
