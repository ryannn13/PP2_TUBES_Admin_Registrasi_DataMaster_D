package model;

public class Dropbox {
    private int dropboxId;
    private String lokasi;
    private int kapasitas;

    public Dropbox() {
    }

    public Dropbox(String lokasi, int kapasitas) {
        this.lokasi = lokasi;
        this.kapasitas = kapasitas;
    }

    // Getters and Setters
    public int getDropboxId() {
        return dropboxId;
    }

    public void setDropboxId(int dropboxId) {
        this.dropboxId = dropboxId;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }
}