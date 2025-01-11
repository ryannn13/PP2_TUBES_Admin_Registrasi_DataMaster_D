package model;

import java.util.Date;

public class Points {
    private int pointId;
    private int masyarakatId;
    private int jumlah;
    private Date tanggal;

    public Points() {
    }

    public Points(int masyarakatId, int jumlah) {
        this.masyarakatId = masyarakatId;
        this.jumlah = jumlah;
    }

    // Getters and Setters
    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public int getMasyarakatId() {
        return masyarakatId;
    }

    public void setMasyarakatId(int masyarakatId) {
        this.masyarakatId = masyarakatId;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
}
