package model;

public class CattyCareModel {
    private int id;
    private String namaPemilik;
    private String namaKucing;
    private String nomorTelepon;
    private int lamaPenitipan;
    private int totalBiaya;

    public CattyCareModel() {}

    public CattyCareModel(String namaPemilik, String namaKucing, String nomorTelepon, int lamaPenitipan) {
        this.namaPemilik = namaPemilik;
        this.namaKucing = namaKucing;
        this.nomorTelepon = nomorTelepon;
        this.lamaPenitipan = lamaPenitipan;
        this.totalBiaya = hitungBiaya(lamaPenitipan);
    }

    public final int hitungBiaya(int hari) {
        if (hari <= 2) {
            return hari * 40000;
        } else {
            return (2 * 40000) + ((hari - 2) * 30000);
        }
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNamaPemilik() { return namaPemilik; }
    public void setNamaPemilik(String namaPemilik) { this.namaPemilik = namaPemilik; }

    public String getNamaKucing() { return namaKucing; }
    public void setNamaKucing(String namaKucing) { this.namaKucing = namaKucing; }

    public String getNomorTelepon() { return nomorTelepon; }
    public void setNomorTelepon(String nomorTelepon) { this.nomorTelepon = nomorTelepon; }

    public int getLamaPenitipan() { return lamaPenitipan; }
    public void setLamaPenitipan(int lamaPenitipan) { 
        this.lamaPenitipan = lamaPenitipan; 
        this.totalBiaya = hitungBiaya(lamaPenitipan);
    }

    public int getTotalBiaya() { return totalBiaya; }
}