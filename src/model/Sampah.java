package model;

public class Sampah {
    private int sampahId;
    private String namaSampah;
    private int jumlahSampah;
    private double beratSampah;
    private int kategoriId;

    public Sampah() {
    }

    public Sampah(String namaSampah, int jumlahSampah, double beratSampah, int kategoriId) {
        this.namaSampah = namaSampah;
        this.jumlahSampah = jumlahSampah;
        this.beratSampah = beratSampah;
        this.kategoriId = kategoriId;
    }

    // Getters and Setters
    public int getSampahId() {
        return sampahId;
    }

    public void setSampahId(int sampahId) {
        this.sampahId = sampahId;
    }

    public String getNamaSampah() {
        return namaSampah;
    }

    public void setNamaSampah(String namaSampah) {
        this.namaSampah = namaSampah;
    }

    public int getJumlahSampah() {
        return jumlahSampah;
    }

    public void setJumlahSampah(int jumlahSampah) {
        this.jumlahSampah = jumlahSampah;
    }

    public double getBeratSampah() {
        return beratSampah;
    }

    public void setBeratSampah(double beratSampah) {
        this.beratSampah = beratSampah;
    }

    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }
}
