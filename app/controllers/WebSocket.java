package controllers;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Framing;
import akka.stream.javadsl.FramingTruncation;
import akka.stream.javadsl.StreamConverters;
import akka.util.ByteString;
import com.fasterxml.jackson.databind.JsonNode;
import core.Aggregator;
import models.Event;
import models.Trade;
import play.Environment;
import play.mvc.Controller;
import play.mvc.Result;
import util.JsonUtil;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class WebSocket extends Controller {

    private final Environment environment;
    private final JsonUtil jsonUtil;
    private final Aggregator aggregator;
    private Flow<ByteString, ByteString, NotUsed> lineDelimiter = Framing.delimiter(ByteString.fromString("\n"), 1028, FramingTruncation.ALLOW);

    @Inject
    public WebSocket(Environment environment, JsonUtil jsonUtil, Aggregator aggregator) {
        this.environment = environment;
        this.jsonUtil = jsonUtil;
        this.aggregator = aggregator;
    }

    public play.mvc.WebSocket socket() {
        return play.mvc.WebSocket.Json.accept(request -> {
            return Flow.of(JsonNode.class).flatMapConcat(subscribeEvent -> {

                final Event event = jsonUtil.jsonToEvent(subscribeEvent);
                InputStream inputStream = environment.resourceAsStream("trades_big.json");

                return StreamConverters.fromInputStream(() -> inputStream)
                        .via(lineDelimiter)                                             //split file line by line
                        .map(ByteString::utf8String)                                    //convert byteString to String
                        .map(jsonUtil::parseTrade)                                      //parse String to Trade
                        .filter((trade) -> trade.getSym().equals(event.getSymbol()))   //filter trade by symbol
                        .statefulMapConcat(() -> {                                     //partitionByInterval
                            AtomicLong barNumber = new AtomicLong(1);
                            AtomicReference<Trade> prev = new AtomicReference<>();
                            AtomicReference<Float> open = new AtomicReference<>();
                            AtomicReference<Float> close = new AtomicReference<>();
                            AtomicReference<Float> high = new AtomicReference<>();
                            AtomicReference<Float> low = new AtomicReference<>();
                            AtomicReference<Float> volume = new AtomicReference<>();
                            return (current) -> aggregator.partitionByInterval(event, barNumber, open, high, low, close, volume, prev, current);
                        });
            });
        });
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }
}
