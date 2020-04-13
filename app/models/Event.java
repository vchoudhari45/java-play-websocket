package models;

/**
 * {"event": "subscribe", "symbol": "XXBTZUSD", "interval": 15}
 */
public class Event {
    private EventType event;
    private String symbol;
    private int interval;

    public Event() {
    }

    public Event(EventType event, String symbol, int interval) {
        this.event = event;
        this.symbol = symbol;
        this.interval = interval;
    }

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return event + " " + symbol + " " + interval;
    }
}
