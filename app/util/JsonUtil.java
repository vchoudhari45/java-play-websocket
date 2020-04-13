package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Event;
import models.EventType;
import models.OhlcResponse;
import models.Trade;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class JsonUtil {

    private ObjectMapper mapper = new ObjectMapper();

    public JsonUtil() {
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    public Event jsonToEvent(JsonNode subscribeEvent) throws JsonProcessingException {
        return mapper.treeToValue(subscribeEvent, Event.class);
    }

    public JsonNode eventToJson(Event event) {
        return mapper.valueToTree(event);
    }

    public JsonNode ohlcResponseToJson(OhlcResponse ohlcResponse) {
        return mapper.valueToTree(ohlcResponse);
    }

    public Trade parseTrade(String trade) throws JsonProcessingException {
        return mapper.treeToValue(mapper.readTree(trade), Trade.class);
    }

    public JsonNode tradeToJsonNode(Trade trade) {
        return mapper.valueToTree(trade);
    }

    public JsonNode tradeToOhlcResponse(Trade current,
                                        AtomicLong barNumber,
                                        AtomicReference<Float> open,
                                        AtomicReference<Float> high,
                                        AtomicReference<Float> low,
                                        AtomicReference<Float> close,
                                        AtomicReference<Float> volume) {
        OhlcResponse ohlcResponse = new OhlcResponse(
                EventType.ohlc_notify,
                current.getSym(),
                barNumber.get(),
                open.get(),
                high.get(),
                low.get(),
                close.get(),
                volume.get()
        );
        return mapper.valueToTree(ohlcResponse);
    }
}

//class JsonUtilTest {
//    public static void main(String args[]) throws JsonProcessingException {
//        JsonUtil jsonUtil = new JsonUtil();
//        Event event = new Event(EventType.subscribe, "XETHZUSD", 15);
//        JsonNode jsonNode = jsonUtil.eventToJson(event);
//        Event eventConvertBack = jsonUtil.jsonToEvent(jsonNode);
//        System.out.println(jsonNode);
//        System.out.println(eventConvertBack);
//
//        OhlcResponse ohlcResponse = new OhlcResponse(EventType.ohlc_notify, "XETHZUSD", 1, 0.0f, 0.1f, 0.2f, 0.3f, 1);
//        JsonNode jsonNodeOhlc = jsonUtil.ohlcResponseToJson(ohlcResponse);
//        System.out.println(jsonNodeOhlc);
//
//        String tradeJson = "{\"sym\":\"XZECXXBT\", \"T\":\"Trade\",  \"P\":3.939e-05, \"Q\":0.1, \"TS\":1538409720.3813, \"side\": \"s\", \"TS2\":1538409725339216503}";
//        Trade trade = jsonUtil.parseTrade(tradeJson);
//        System.out.println(jsonUtil.tradeToJsonNode(trade));
//    }
//}
