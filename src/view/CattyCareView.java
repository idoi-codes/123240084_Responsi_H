package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CattyCareView extends JFrame {
    // Komponen Input
    public JTextField txtPemilik = new JTextField();
    public JTextField txtKucing = new JTextField();
    public JTextField txtTelepon = new JTextField();
    public JTextField txtLama = new JTextField();

    // Tombol (Grid 2x2 di bagian bawah kanan)
    public JButton btnTambah = new JButton("Tambah");
    public JButton btnUbah = new JButton("Ubah");
    public JButton btnClear = new JButton("Clear");
    public JButton btnHapus = new JButton("Hapus");

    // Tabel
    public JTable tabelData;
    public DefaultTableModel tableModel;

    public CattyCareView() {
        // Pengaturan Dasar Frame
        setTitle("CattyCare - Aplikasi Penitipan Kucing");
        setSize(1000, 550); // Ukuran lebih lebar sesuai gambar
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main Panel dengan BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(15, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- BAGIAN KIRI: TABEL ---
        String[] kolom = {"ID", "Nama Pemilik", "Nama Kucing", "Nomor Telepon", "Lama Penitipan", "Biaya"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelData = new JTable(tableModel);
        tabelData.setRowHeight(25);
        // Sembunyikan kolom ID (Primary Key)
        tabelData.removeColumn(tabelData.getColumnModel().getColumn(0));
        
        JScrollPane scrollPane = new JScrollPane(tabelData);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- BAGIAN KANAN: FORMULIR (SIDEBAR) ---
        JPanel panelKanan = new JPanel(new BorderLayout());
        panelKanan.setPreferredSize(new Dimension(350, 0));
        panelKanan.setBackground(new Color(230, 240, 250)); // Warna background soft blue sesuai gambar
        panelKanan.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Container untuk Input (Atas)
        JPanel panelInput = new JPanel(new GridBagLayout());
        panelInput.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 0); // Jarak antar komponen
        gbc.weightx = 1.0;

        // Nama Pemilik
        gbc.gridy = 0; panelInput.add(new JLabel("Nama Pemilik:"), gbc);
        gbc.gridy = 1; panelInput.add(txtPemilik, gbc);
        gbc.insets = new Insets(20, 0, 5, 0); // Gap besar antar field sesuai gambar

        // Nama Kucing
        gbc.gridy = 2; panelInput.add(new JLabel("Nama Kucing:"), gbc);
        gbc.gridy = 3; panelInput.add(txtKucing, gbc);

        // Nomor Telepon
        gbc.gridy = 4; panelInput.add(new JLabel("Nomor Telepon:"), gbc);
        gbc.gridy = 5; panelInput.add(txtTelepon, gbc);

        // Lama Penitipan
        gbc.gridy = 6; panelInput.add(new JLabel("Lama Penitipan (Hari):"), gbc);
        gbc.gridy = 7; panelInput.add(txtLama, gbc);

        panelKanan.add(panelInput, BorderLayout.NORTH);

        // Container untuk Tombol (Bawah - Grid 2x2)
        JPanel panelTombol = new JPanel(new GridLayout(2, 2, 10, 10));
        panelTombol.setOpaque(false);
        panelTombol.add(btnTambah);
        panelTombol.add(btnUbah);
        panelTombol.add(btnClear);
        panelTombol.add(btnHapus);

        panelKanan.add(panelTombol, BorderLayout.SOUTH);

        mainPanel.add(panelKanan, BorderLayout.EAST);
        add(mainPanel);
    }

    public void clearFields() {
        txtPemilik.setText("");
        txtKucing.setText("");
        txtTelepon.setText("");
        txtLama.setText("");
        tabelData.clearSelection();
    }
}