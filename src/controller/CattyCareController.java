package controller;

import model.CattyCareModel;
import view.CattyCareView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class CattyCareController {
    private final CattyCareView view;
    private Connection conn;
    private int selectedId = -1;

    public CattyCareController(CattyCareView view) {
        this.view = view;
        connectDatabase();
        loadDataTabel();

        this.view.btnTambah.addActionListener(e -> tambahData());
        this.view.btnUbah.addActionListener(e -> ubahData());
        this.view.btnHapus.addActionListener(e -> hapusData());
        this.view.btnClear.addActionListener(e -> {
            view.clearFields();
            selectedId = -1;
        });

        this.view.tabelData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.tabelData.getSelectedRow();
                if (row != -1) {
                    selectedId = (int) view.tableModel.getValueAt(row, 0);
                    view.txtPemilik.setText(view.tableModel.getValueAt(row, 1).toString());
                    view.txtKucing.setText(view.tableModel.getValueAt(row, 2).toString());
                    view.txtTelepon.setText(view.tableModel.getValueAt(row, 3).toString());
                    view.txtLama.setText(view.tableModel.getValueAt(row, 4).toString());
                }
            }
        });
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/cattycare_db"; 
            String user = "root";
            String pass = "";
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(view, "Driver MySQL tidak ditemukan! Hubungkan connector JAR ke Libraries.", "Driver Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal koneksi database! Nyalakan Apache & MySQL di XAMPP.\nDetail: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDataTabel() {
        if (conn == null) return;

        view.tableModel.setRowCount(0);
        String query = "SELECT * FROM daycare";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                view.tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nama_pemilik"),
                        rs.getString("nama_kucing"),
                        rs.getString("nomor_telepon"),
                        rs.getInt("lama_penitipan"),
                        rs.getInt("total_biaya")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error load data: " + e.getMessage());
        }
    }

    private void tambahData() {
        try {
            if(view.txtPemilik.getText().isEmpty() || view.txtKucing.getText().isEmpty() || view.txtLama.getText().isEmpty()) {
                throw new IllegalArgumentException("Semua field wajib diisi!");
            }

            CattyCareModel model = new CattyCareModel(
                    view.txtPemilik.getText(),
                    view.txtKucing.getText(),
                    view.txtTelepon.getText(),
                    Integer.parseInt(view.txtLama.getText())
            );

            String sql = "INSERT INTO daycare (nama_pemilik, nama_kucing, nomor_telepon, lama_penitipan, total_biaya) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, model.getNamaPemilik());
                ps.setString(2, model.getNamaKucing());
                ps.setString(3, model.getNomorTelepon());
                ps.setInt(4, model.getLamaPenitipan());
                ps.setInt(5, model.getTotalBiaya());
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(view, "Data Berhasil Ditambahkan!");
                loadDataTabel();
                view.clearFields();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Lama penitipan harus berupa angka bilangan bulat!", "Input Salah", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ubahData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(view, "Pilih baris data pada tabel terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            if(view.txtPemilik.getText().isEmpty() || view.txtKucing.getText().isEmpty() || view.txtLama.getText().isEmpty()) {
                throw new IllegalArgumentException("Field tidak boleh kosong!");
            }

            CattyCareModel model = new CattyCareModel(
                    view.txtPemilik.getText(),
                    view.txtKucing.getText(),
                    view.txtTelepon.getText(),
                    Integer.parseInt(view.txtLama.getText())
            );

            String sql = "UPDATE daycare SET nama_pemilik=?, nama_kucing=?, nomor_telepon=?, lama_penitipan=?, total_biaya=? WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, model.getNamaPemilik());
                ps.setString(2, model.getNamaKucing());
                ps.setString(3, model.getNomorTelepon());
                ps.setInt(4, model.getLamaPenitipan());
                ps.setInt(5, model.getTotalBiaya());
                ps.setInt(6, selectedId);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(view, "Data Berhasil Diperbarui!");
                loadDataTabel();
                view.clearFields();
                selectedId = -1;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Lama penitipan harus berupa angka!", "Input Salah", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Gagal Mengubah Data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(view, "Pilih baris data pada tabel yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Apakah anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM daycare WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, selectedId);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(view, "Data Berhasil Dihapus!");
                loadDataTabel();
                view.clearFields();
                selectedId = -1;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Gagal Menghapus Data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}