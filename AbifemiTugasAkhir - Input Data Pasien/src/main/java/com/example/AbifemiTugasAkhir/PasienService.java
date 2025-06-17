package com.example.AbifemiTugasAkhir;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PasienService {

    private final List<Pasien> daftarPasien = new java.util.ArrayList<>();

    public List<Pasien> getAllPasien() {
        return daftarPasien;
    }

    public void tambahPasien(Pasien pasien) {
        daftarPasien.add(pasien);
        simpanKeFile();
    }

    public void hapusPasien(int index) {
        if (index >= 0 && index < daftarPasien.size()) {
            daftarPasien.remove(index);
            simpanKeFile();
        }
    }

    public void updatePasien(int index, Pasien pasien) {
        if (index >= 0 && index < daftarPasien.size()) {
            daftarPasien.set(index, pasien);
            simpanKeFile();
        }
    }

    private void simpanKeFile() {
        try (FileWriter writer = new FileWriter("data_pasien.txt")) {
            for (Pasien p : daftarPasien) {
                writer.write("Nama: " + p.getNama() + ", Umur: " + p.getUmur() + ", Alamat: " + p.getAlamat() + ", Penyakit: " + p.getPenyakit() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
