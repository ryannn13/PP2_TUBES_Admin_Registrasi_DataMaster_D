package model;

public class KategoriSampah {
    private int kategoriId;
    private String namaKategori;
    private int poin;

    public KategoriSampah() {
    }

    public KategoriSampah(String namaKategori, int poin) {
        this.namaKategori = namaKategori;
        this.poin = poin;
    }

    // Getters and Setters
    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }
}
