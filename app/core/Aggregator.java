package core;

import com.fasterxml.jackson.databind.JsonNode;
import models.Event;
import models.Trade;
import util.JsonUtil;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Aggregator {

    private final JsonUtil jsonUtil;

    @Inject
    public Aggregator(JsonUtil jsonUtil) {
        this.jsonUtil = jsonUtil;
    }

    public List<JsonNode> partitionByInterval(Event event,
                                              AtomicLong barNumber,
                                              AtomicReference<Float> open,
                                              AtomicReference<Float> high,
                                              AtomicReference<Float> low,
                                              AtomicReference<Float> close,
                                              AtomicReference<Float> volume,
                                              AtomicReference<Trade> prev,
                                              Trade current) {
        if (prev.get() == null) {
            prev.set(current);

            open.set(current.getP());
            high.set(current.getP());
            low.set(current.getP());
            close.set(0.0f);
            volume.set(current.getQ());

            return Collections.singletonList(jsonUtil.tradeToOhlcResponse(current, barNumber, open, high, low, close, volume));
        } else if (current.getTS2() - prev.get().getTS2() > event.getInterval()) {
            //System.out.println(current.getTS2()+" "+prev.get().getTS2());
            prev.set(current);

            high.set(Math.max(high.get(), current.getP()));
            low.set(Math.min(high.get(), current.getP()));
            close.set(current.getP());
            volume.set(volume.get() + current.getQ());
            JsonNode endingBar = jsonUtil.tradeToOhlcResponse(current, barNumber, open, high, low, close, volume);

            barNumber.getAndIncrement();
            prev.set(null);

            return Collections.singletonList(endingBar);
        } else {
            //System.out.println(current.getTS2()+" "+prev.get().getTS2());
            prev.set(current);
            high.set(Math.max(high.get(), current.getP()));
            low.set(Math.min(high.get(), current.getP()));
            volume.set(volume.get() + current.getQ());
            return Collections.singletonList(jsonUtil.tradeToOhlcResponse(current, barNumber, open, high, low, close, volume));
        }
    }
}
