package models;

public class OhlcResponse {

    private EventType event;
    private String symbol;
    private long bar_num;
    private float o, h, l, c, volume;

    public OhlcResponse() {
    }

    public OhlcResponse(EventType event, String symbol, long bar_num, float o, float h, float l, float c, float volume) {
        this.event = event;
        this.symbol = symbol;
        this.bar_num = bar_num;
        this.o = o;
        this.h = h;
        this.l = l;
        this.c = c;
        this.volume = volume;
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

    public long getBar_num() {
        return bar_num;
    }

    public void setBar_num(long bar_num) {
        this.bar_num = bar_num;
    }

    public float getO() {
        return o;
    }

    public void setO(float o) {
        this.o = o;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getL() {
        return l;
    }

    public void setL(float l) {
        this.l = l;
    }

    public float getC() {
        return c;
    }

    public void setC(float c) {
        this.c = c;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
