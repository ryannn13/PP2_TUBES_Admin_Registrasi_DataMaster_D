package model;

import java.util.Date;

public class Penjemputan {
    private int penjemputanId;
    private int masyarakatId;
    private Integer kurirId;
    private int sampahId;
    private Integer dropboxId;
    private String status;
    private Date tanggalRequest;
    private Date tanggalSelesai;

    public Penjemputan() {
    }

    // Getters and Setters
    public int getPenjemputanId() {
        return penjemputanId;
    }

    public void setPenjemputanId(int penjemputanId) {
        this.penjemputanId = penjemputanId;
    }

    public int getMasyarakatId() {
        return masyarakatId;
    }

    public void setMasyarakatId(int masyarakatId) {
        this.masyarakatId = masyarakatId;
    }

    public Integer getKurirId() {
        return kurirId;
    }

    public void setKurirId(Integer kurirId) {
        this.kurirId = kurirId;
    }

    public int getSampahId() {
        return sampahId;
    }

    public void setSampahId(int sampahId) {
        this.sampahId = sampahId;
    }

    public Integer getDropboxId() {
        return dropboxId;
    }

    public void setDropboxId(Integer dropboxId) {
        this.dropboxId = dropboxId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTanggalRequest() {
        return tanggalRequest;
    }

    public void setTanggalRequest(Date tanggalRequest) {
        this.tanggalRequest = tanggalRequest;
    }

    public Date getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(Date tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }
}
