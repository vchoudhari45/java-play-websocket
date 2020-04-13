package models;

//{"sym":"XETHXXBT", "T":"Trade",  "P":0.03484, "Q":0.05, "TS":1538416464.1801, "side": "s", "TS2":1538416487229979504}
public class Trade {

    private String sym;
    private String T;
    private float P;
    private float Q;
    private float TS;
    private String side;
    private String TS2;

    public Trade() {
    }

    public Trade(String sym, String T, float P, float Q, float TS, String side, String TS2) {
        this.sym = sym;
        this.T = T;
        this.P = P;
        this.Q = Q;
        this.TS = TS;
        this.TS2 = TS2;
        this.side = side;
    }

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public String getT() {
        return T;
    }

    public void setT(String T) {
        T = T;
    }

    public float getP() {
        return P;
    }

    public void setP(float p) {
        P = p;
    }

    public float getQ() {
        return Q;
    }

    public void setQ(float q) {
        Q = q;
    }

    public float getTS() {
        return TS;
    }

    public void setTS(float TS) {
        this.TS = TS;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public long getTS2() {
        return Long.parseLong(TS2.substring(0, 10));
    }

    public void setTS2(String TS2) {
        this.TS2 = TS2;
    }

    @Override
    public String toString() {
        return sym + " " + TS2.substring(0, 10);
    }
}
