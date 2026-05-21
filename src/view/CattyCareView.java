package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CattyCareView extends JFrame {
    public JTextField txtPemilik = new JTextField(20);
    public JTextField txtKucing = new JTextField(20);
    public JTextField txtTelepon = new JTextField(20);
    public JTextField txtLama = new JTextField(20);

    public JButton btnTambah = new JButton("Tambah");
    public JButton btnUbah = new JButton("Ubah");
    public JButton btnClear = new JButton("Clear");
    public JButton btnHapus = new JButton("Hapus");

    public JTable tabelData;
    public DefaultTableModel tableModel;

    public CattyCareView() {
        setTitle("CattyCare - Aplikasi Penitipan Kucing");
        setSize(750, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        String[] kolom = {"ID", "Nama Pemilik", "Nama Kucing", "Nomor Telepon", "Lama Penitipan", "Biaya"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelData = new JTable(tableModel);
        tabelData.removeColumn(tabelData.getColumnModel().getColumn(0)); 
        
        JScrollPane scrollPane = new JScrollPane(tabelData);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBawah = new JPanel(new GridBagLayout());
        panelBawah.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelBawah.add(new JLabel("Nama Pemilik:"), gbc);
        gbc.gridx = 1; panelBawah.add(txtPemilik, gbc);
        gbc.gridx = 2; panelBawah.add(btnTambah, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelBawah.add(new JLabel("Nama Kucing:"), gbc);
        gbc.gridx = 1; panelBawah.add(txtKucing, gbc);
        gbc.gridx = 2; panelBawah.add(btnUbah, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelBawah.add(new JLabel("Nomor Telepon:"), gbc);
        gbc.gridx = 1; panelBawah.add(txtTelepon, gbc);
        gbc.gridx = 2; panelBawah.add(btnClear, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelBawah.add(new JLabel("Lama Penitipan (Hari):"), gbc);
        gbc.gridx = 1; panelBawah.add(txtLama, gbc);
        gbc.gridx = 2; panelBawah.add(btnHapus, gbc);

        add(panelBawah, BorderLayout.SOUTH);
    }

    public void clearFields() {
        txtPemilik.setText("");
        txtKucing.setText("");
        txtTelepon.setText("");
        txtLama.setText("");
        tabelData.clearSelection();
    }
}