/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tugas_logindanregister;

import fuctions.connection_database;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Lenovo
 */
public class inputData extends homePage {

    /**
     * Creates new form TugasJtabel
     */
    private String SQL;

    DefaultTableModel model;
    int index, confirm;
    String noResi,
            namaPengririm,
            namaPenerima,
            noTlpPengirim,
            alamatPengirim,
            noTlpPenerima,
            alamatPenerima,
            jenisBarang,
            berat,
            jenisJasaPengiriman,
            modePengiriman,
            bw,
            pk,
            bk,
            pw,
            status;

    public inputData() {
        initComponents();
        Color bg = new Color(255, 255, 255, 255);
        getContentPane().setBackground(bg);

//        panggil class database
//        this.TampilData();

        Object kolom[] = {"No Resi", "Nama Pengirim", "No Tlp Pengirim", "Alamat Pengirim", "Nama Penerima", "No Tlp Penerima", "Alamat Penerima", "Jenis Barang", "Berat", "Jenis Pengiriman", "Mode Pengiriman", "Opsi Extra", "Status"};
        Object data[][] = {};

        model = new DefaultTableModel(data, kolom);
        jTableHasil.setModel(model);

        // Menambahkan TableRowSorter untuk penyaringan
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        jTableHasil.setRowSorter(sorter);

        // Set default filter combo box selection ke "Semua"
        jCBFilter.setSelectedIndex(0);  // Default "Semua"

        // ActionListener untuk mengubah filter berdasarkan pilihan di JComboBox
        jCBFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStatus = (String) jCBFilter.getSelectedItem();
                if (selectedStatus.equals("Semua")) {
                    sorter.setRowFilter(null);  // Menampilkan semua data
                } else {
                    // Filter hanya berdasarkan kolom "Status" (indeks ke-12)
                    sorter.setRowFilter(RowFilter.regexFilter(selectedStatus, 12));
                }
            }
        });

//        update
        jTableHasil.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = jTableHasil.getSelectedRow();
                if (selectedRow != -1) {
                    txtNoResi.setText(model.getValueAt(selectedRow, 0).toString());
                    txtNamaPengirim.setText(model.getValueAt(selectedRow, 1).toString());
                    txtNoTlpPengirim.setText(model.getValueAt(selectedRow, 2).toString());
                    jTextAreaAlamatPengirim.setText(model.getValueAt(selectedRow, 3).toString());
                    txtNamaPenerima.setText(model.getValueAt(selectedRow, 4).toString());
                    txtNoTlpPenerima.setText(model.getValueAt(selectedRow, 5).toString());
                    jTextAreaAlamatPenerima.setText(model.getValueAt(selectedRow, 6).toString());
                    jCBJenisbarang.setSelectedItem(model.getValueAt(selectedRow, 7).toString());
                    txtBeratBarang.setText(model.getValueAt(selectedRow, 8).toString());
                    jCBJenisPengiriman.setSelectedItem(model.getValueAt(selectedRow, 9).toString());

                    String modePengiriman = model.getValueAt(selectedRow, 10).toString();
                    if (modePengiriman.equals("Express")) {
                        jRadioButtonExpress.setSelected(true);
                    } else if (modePengiriman.equals("Reguler")) {
                        jRadioButtonReguler.setSelected(true);
                    }

                    jCheckBoxBubbleWrap.setSelected(model.getValueAt(selectedRow, 11).toString().contains("Bubble Wrap"));
                    jCheckBoxPackingKayu.setSelected(model.getValueAt(selectedRow, 11).toString().contains("Packing Kayu"));
                    jCheckBoxDoubleKarton.setSelected(model.getValueAt(selectedRow, 11).toString().contains("Double Karton"));
                    jCheckBoxPlastikWrap.setSelected(model.getValueAt(selectedRow, 11).toString().contains("Plastik Wrap"));

                    jCBStatusPengiriman.setSelectedItem(model.getValueAt(selectedRow, 12).toString());
                }
            }
        });
        
//        database
        Connection conn = connection_database.getConnection();

        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Koneksi database gagal!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String SQL = "SELECT * FROM data_table_logistik";
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery(SQL);

            if (!res.isBeforeFirst()) { // Jika result set kosong
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan!", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                while (res.next()) {
                    model.addRow(new Object[]{
                        res.getString("no_resi"),
                        res.getString("nama_pengirim"),
                        res.getString("no_tlp_pengirim"),
                        res.getString("alamat_pengirim"),
                        res.getString("nama_penerima"),
                        res.getString("no_tlp_penerima"),
                        res.getString("alamat_penerima"), // Perbaikan nama kolom
                        res.getString("jenis_barang"),
                        res.getString("berat"),
                        res.getString("jenis_pengiriman"),
                        res.getString("mode_pengiriman"),
                        res.getString("opsi_extra"),
                        res.getString("status_pengiriman"),});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

//        JOptionPane.showMessageDialog(this, "Hallo, Selamat datang diprogram pendataan Logistik.", "Info", JOptionPane.INFORMATION_MESSAGE);
//        JOptionPane.showMessageDialog(this, "Silahkan isi table sesuai dengan data customer.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

//    public void TampilData() {
//        model = new DefaultTableModel();
//        model.addColumn("No Resi");
//        model.addColumn("Nama Pengirim");
//        model.addColumn("No Tlp Pengirim");
//        model.addColumn("Alamat Pengirim");
//        model.addColumn("Nama Penerima");
//        model.addColumn("No Tlp Penerima");
//        model.addColumn("Alamat Penerima"); // Perbaikan nama kolom
//        model.addColumn("Jenis Barang");
//        model.addColumn("Berat");
//        model.addColumn("Jenis Pengiriman");
//        model.addColumn("Mode Pengiriman");
//        model.addColumn("Opsi Extra");
//        model.addColumn("Status Pengiriman");
//
//        jTableHasil.setModel(model);
//        Connection conn = connection_database.getConnection();
//
//        if (conn == null) {
//            JOptionPane.showMessageDialog(this, "Koneksi database gagal!", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        try {
//            String SQL = "SELECT * FROM data_table_logistik";
//            java.sql.Statement stmt = conn.createStatement();
//            java.sql.ResultSet res = stmt.executeQuery(SQL);
//
//            if (!res.isBeforeFirst()) { // Jika result set kosong
//                JOptionPane.showMessageDialog(this, "Data tidak ditemukan!", "Info", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                while (res.next()) {
//                    model.addRow(new Object[]{
//                        res.getString("no_resi"),
//                        res.getString("nama_pengirim"),
//                        res.getString("no_tlp_pengirim"),
//                        res.getString("alamat_pengirim"),
//                        res.getString("nama_penerima"),
//                        res.getString("no_tlp_penerima"),
//                        res.getString("alamat_penerima"), // Perbaikan nama kolom
//                        res.getString("jenis_barang"),
//                        res.getString("berat"),
//                        res.getString("jenis_pengiriman"),
//                        res.getString("mode_pengiriman"),
//                        res.getString("opsi_extra"),
//                        res.getString("status_pengiriman"),});
//                }
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupModePengiriman = new javax.swing.ButtonGroup();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableHasil = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCheckBoxBubbleWrap = new javax.swing.JCheckBox();
        jCheckBoxPackingKayu = new javax.swing.JCheckBox();
        jCheckBoxDoubleKarton = new javax.swing.JCheckBox();
        jCheckBoxPlastikWrap = new javax.swing.JCheckBox();
        jCBStatusPengiriman = new javax.swing.JComboBox<>();
        btnSimpan = new javax.swing.JButton();
        btnHapusisiTabel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnHapusBarisTabel = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtNoResi = new javax.swing.JTextField();
        btnHapusInputan = new javax.swing.JButton();
        txtNamaPengirim = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNamaPenerima = new javax.swing.JTextField();
        txtNoTlpPenerima = new javax.swing.JTextField();
        txtNoTlpPengirim = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaAlamatPengirim = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaAlamatPenerima = new javax.swing.JTextArea();
        jCBJenisbarang = new javax.swing.JComboBox<>();
        txtBeratBarang = new javax.swing.JTextField();
        jCBJenisPengiriman = new javax.swing.JComboBox<>();
        jRadioButtonExpress = new javax.swing.JRadioButton();
        jRadioButtonReguler = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jCBFilter = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableHasil.setBackground(new java.awt.Color(0, 102, 102));
        jTableHasil.setForeground(new java.awt.Color(255, 255, 255));
        jTableHasil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No Resi", "Nama Pengiriman", "No Tlp Pengirim", "Alamat Pengirim", "Nama Penerima", "No Tlp Penerima", "Alamat Penerima", "Jenis Barang", "Berat", "Jenis Pengiriman", "Mode Pengiriman", "Opsi Extra", "Status Pengiriman"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableHasil.setColumnSelectionAllowed(true);
        jTableHasil.setSelectionBackground(new java.awt.Color(102, 102, 102));
        jTableHasil.setSelectionForeground(new java.awt.Color(204, 204, 204));
        jTableHasil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableHasilMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableHasil);
        jTableHasil.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel2.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Penerima");

        jLabel3.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("No RESI");

        jCheckBoxBubbleWrap.setBackground(new java.awt.Color(0, 102, 102));
        jCheckBoxBubbleWrap.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxBubbleWrap.setText("Bubble wrap");

        jCheckBoxPackingKayu.setBackground(new java.awt.Color(0, 102, 102));
        jCheckBoxPackingKayu.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxPackingKayu.setText("Packing kayu");

        jCheckBoxDoubleKarton.setBackground(new java.awt.Color(0, 102, 102));
        jCheckBoxDoubleKarton.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxDoubleKarton.setText("Double karton");

        jCheckBoxPlastikWrap.setBackground(new java.awt.Color(0, 102, 102));
        jCheckBoxPlastikWrap.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxPlastikWrap.setText("Plastik wrapping");

        jCBStatusPengiriman.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Penjemputan Kurir", "Dalam Perjalanan", "Barang Diterima", "Retur" }));

        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(0, 102, 102));
        btnSimpan.setText("Simpan");
        btnSimpan.setPreferredSize(new java.awt.Dimension(76, 30));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapusisiTabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHapusisiTabel.setForeground(new java.awt.Color(0, 102, 102));
        btnHapusisiTabel.setText("Hapus Isi Tabel");
        btnHapusisiTabel.setPreferredSize(new java.awt.Dimension(116, 30));
        btnHapusisiTabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusisiTabelActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("No Tlp (Pengirim)");

        btnHapusBarisTabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHapusBarisTabel.setForeground(new java.awt.Color(0, 102, 102));
        btnHapusBarisTabel.setText("Hapus Baris Tabel");
        btnHapusBarisTabel.setPreferredSize(new java.awt.Dimension(131, 30));
        btnHapusBarisTabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusBarisTabelActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Status Pengiriman");

        jLabel6.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Berat (kg)");

        jLabel7.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Jenis barang");

        jLabel8.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Jenis Jasa Pengiriman");

        jLabel12.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Alamat pengirim");

        jLabel13.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Opsi Extra");

        jLabel14.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Mode pengiriman");

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Data pengiriman");

        txtNoResi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoResiActionPerformed(evt);
            }
        });

        btnHapusInputan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHapusInputan.setForeground(new java.awt.Color(0, 102, 102));
        btnHapusInputan.setText("Hapus inputan");
        btnHapusInputan.setPreferredSize(new java.awt.Dimension(108, 30));
        btnHapusInputan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusInputanActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("No Tlp (Penerima)");

        jLabel15.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Alamat tujuan");

        jTextAreaAlamatPengirim.setColumns(20);
        jTextAreaAlamatPengirim.setRows(5);
        jScrollPane1.setViewportView(jTextAreaAlamatPengirim);

        jTextAreaAlamatPenerima.setColumns(20);
        jTextAreaAlamatPenerima.setRows(5);
        jScrollPane2.setViewportView(jTextAreaAlamatPenerima);

        jCBJenisbarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Makanan", "Elektronik", "Pakaian", "Otomotif", "Furnitur dan Dekorasi", "Bahan Bangunan" }));

        jCBJenisPengiriman.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "JNE", "JNT", "Wahana", "Go Send" }));

        jRadioButtonExpress.setBackground(new java.awt.Color(0, 102, 102));
        btnGroupModePengiriman.add(jRadioButtonExpress);
        jRadioButtonExpress.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonExpress.setText("Express");

        jRadioButtonReguler.setBackground(new java.awt.Color(0, 102, 102));
        btnGroupModePengiriman.add(jRadioButtonReguler);
        jRadioButtonReguler.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonReguler.setText("Reguler");

        jLabel1.setFont(new java.awt.Font("Humnst777 Cn BT", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nama Pengirim");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Filter");

        jCBFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua", "Penjemputan Kurir", "Dalam Perjalanan", "Barang Diterima", "Retur" }));

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(0, 102, 102));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpdate)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCBFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 442, Short.MAX_VALUE)
                                .addComponent(btnHapusInputan, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapusBarisTabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel1))
                                        .addGap(29, 29, 29)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNoResi, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                            .addComponent(txtNamaPengirim)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(jLabel4))
                                            .addComponent(jLabel12))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(txtNoTlpPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel9)
                                                .addComponent(jLabel15))
                                            .addGap(18, 18, 18)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtNoTlpPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(27, 27, 27)
                                            .addComponent(txtNamaPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel5))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCBStatusPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCBJenisbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBeratBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCheckBoxBubbleWrap)
                                        .addGap(18, 18, 18)
                                        .addComponent(jCheckBoxPackingKayu))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jRadioButtonExpress)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButtonReguler))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCheckBoxDoubleKarton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckBoxPlastikWrap))
                                    .addComponent(jCBJenisPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnHapusisiTabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)))
                        .addGap(34, 34, 34))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jCBJenisbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtBeratBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtNoResi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNamaPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNoTlpPengirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNamaPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNoTlpPenerima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jCBJenisPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(jRadioButtonExpress)
                                    .addComponent(jRadioButtonReguler))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jCheckBoxBubbleWrap)
                                    .addComponent(jCheckBoxPackingKayu))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCheckBoxDoubleKarton)
                                    .addComponent(jCheckBoxPlastikWrap))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jCBStatusPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusBarisTabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusInputan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusisiTabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jCBFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNoResiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoResiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoResiActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:

//        data pelanggan
        noResi = txtNoResi.getText();
        namaPengririm = txtNamaPengirim.getText();
        noTlpPengirim = txtNoTlpPengirim.getText();
        alamatPengirim = jTextAreaAlamatPengirim.getText();
        namaPenerima = txtNamaPenerima.getText();
        noTlpPenerima = txtNoTlpPenerima.getText();
        alamatPenerima = jTextAreaAlamatPenerima.getText();

//        data barang
        jenisBarang = jCBJenisbarang.getSelectedItem().toString();
        berat = txtBeratBarang.getText();
        jenisJasaPengiriman = jCBJenisPengiriman.getSelectedItem().toString();
//      mode pengiriman
        if (jRadioButtonExpress.isSelected()) {
            modePengiriman = "Express";
        } else {
            modePengiriman = "Reguler";
        }
//        checkbox
        if (jCheckBoxBubbleWrap.isSelected()) {
            bw = "Bubble Wrap";
        } else {
            bw = "";
        }
        if (jCheckBoxPackingKayu.isSelected()) {
            pk = "Packing Kayu";
        } else {
            pk = "";
        }
        if (jCheckBoxDoubleKarton.isSelected()) {
            bk = "Bubble Karton";
        } else {
            bk = "";
        }
        if (jCheckBoxPlastikWrap.isSelected()) {
            pw = "Plastik Wrapping";
        } else {
            pw = "";
        }
        status = jCBStatusPengiriman.getSelectedItem().toString();
//        memasukkan ke table
        confirm = JOptionPane.showConfirmDialog(this, "Apakah inputan sudah benar ?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            model.addRow(new Object[]{
                noResi,
                namaPengririm,
                noTlpPengirim,
                alamatPengirim,
                namaPenerima,
                noTlpPenerima,
                alamatPenerima,
                jenisBarang,
                berat,
                jenisJasaPengiriman,
                modePengiriman, bw, status});
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusInputanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusInputanActionPerformed
        // TODO add your handling code here:
        confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus inputan ?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            txtNoResi.setText("");
            txtNamaPengirim.setText("");
            txtNoTlpPengirim.setText("");
            jTextAreaAlamatPengirim.setText("");
            txtNamaPenerima.setText("");
            txtNoTlpPenerima.setText("");
            jTextAreaAlamatPenerima.setText("");
            jCBJenisbarang.setSelectedItem("None");
            txtBeratBarang.setText("");
            jCBJenisPengiriman.setSelectedItem("None");
            btnGroupModePengiriman.clearSelection();
            jCheckBoxBubbleWrap.setSelected(false);
            jCheckBoxPackingKayu.setSelected(false);
            jCheckBoxDoubleKarton.setSelected(false);
            jCheckBoxPlastikWrap.setSelected(false);
            jCBStatusPengiriman.setSelectedItem("None");
            JOptionPane.showMessageDialog(this, "Inputan berhasil dihapus.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusInputanActionPerformed

    private void btnHapusBarisTabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusBarisTabelActionPerformed
        // TODO add your handling code here:
        confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data pada baris tabel yang anda pilih ?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            index = jTableHasil.getSelectedRow();
            model.removeRow(index);
            jTableHasil.setModel(model);
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusBarisTabelActionPerformed

    private void btnHapusisiTabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusisiTabelActionPerformed
        // TODO add your handling code here:
        confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus semua data di tabel?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            model.setRowCount(0); // Menghapus semua isi tabel
            JOptionPane.showMessageDialog(this, "Semua data berhasil dihapus.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusisiTabelActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTableHasil.getSelectedRow();
        if (selectedRow != -1) {
            // Update data pada model tabel
            model.setValueAt(txtNoResi.getText(), selectedRow, 0);
            model.setValueAt(txtNamaPengirim.getText(), selectedRow, 1);
            model.setValueAt(txtNoTlpPengirim.getText(), selectedRow, 2);
            model.setValueAt(jTextAreaAlamatPengirim.getText(), selectedRow, 3);
            model.setValueAt(txtNamaPenerima.getText(), selectedRow, 4);
            model.setValueAt(txtNoTlpPenerima.getText(), selectedRow, 5);
            model.setValueAt(jTextAreaAlamatPenerima.getText(), selectedRow, 6);
            model.setValueAt(jCBJenisbarang.getSelectedItem().toString(), selectedRow, 7);
            model.setValueAt(txtBeratBarang.getText(), selectedRow, 8);
            model.setValueAt(jCBJenisPengiriman.getSelectedItem().toString(), selectedRow, 9);

            String modePengiriman = jRadioButtonExpress.isSelected() ? "Express" : "Reguler";
            model.setValueAt(modePengiriman, selectedRow, 10);

            String opsiExtra = "";
            if (jCheckBoxBubbleWrap.isSelected()) {
                opsiExtra += "Bubble Wrap, ";
            }
            if (jCheckBoxPackingKayu.isSelected()) {
                opsiExtra += "Packing Kayu, ";
            }
            if (jCheckBoxDoubleKarton.isSelected()) {
                opsiExtra += "Double Karton, ";
            }
            if (jCheckBoxPlastikWrap.isSelected()) {
                opsiExtra += "Plastik Wrap";
            }

// Hapus koma di akhir string jika ada
            if (!opsiExtra.isEmpty()) {
                opsiExtra = opsiExtra.substring(0, opsiExtra.length() - 2);
            }
            model.setValueAt(opsiExtra, selectedRow, 11);

            model.setValueAt(jCBStatusPengiriman.getSelectedItem().toString(), selectedRow, 12);

            JOptionPane.showMessageDialog(null, "Data updated successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to update.");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void jTableHasilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableHasilMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableHasilMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(inputData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(inputData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(inputData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(inputData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new inputData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupModePengiriman;
    private javax.swing.JButton btnHapusBarisTabel;
    private javax.swing.JButton btnHapusInputan;
    private javax.swing.JButton btnHapusisiTabel;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> jCBFilter;
    private javax.swing.JComboBox<String> jCBJenisPengiriman;
    private javax.swing.JComboBox<String> jCBJenisbarang;
    private javax.swing.JComboBox<String> jCBStatusPengiriman;
    private javax.swing.JCheckBox jCheckBoxBubbleWrap;
    private javax.swing.JCheckBox jCheckBoxDoubleKarton;
    private javax.swing.JCheckBox jCheckBoxPackingKayu;
    private javax.swing.JCheckBox jCheckBoxPlastikWrap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButtonExpress;
    private javax.swing.JRadioButton jRadioButtonReguler;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableHasil;
    private javax.swing.JTextArea jTextAreaAlamatPenerima;
    private javax.swing.JTextArea jTextAreaAlamatPengirim;
    private javax.swing.JTextField txtBeratBarang;
    private javax.swing.JTextField txtNamaPenerima;
    private javax.swing.JTextField txtNamaPengirim;
    private javax.swing.JTextField txtNoResi;
    private javax.swing.JTextField txtNoTlpPenerima;
    private javax.swing.JTextField txtNoTlpPengirim;
    // End of variables declaration//GEN-END:variables
}
