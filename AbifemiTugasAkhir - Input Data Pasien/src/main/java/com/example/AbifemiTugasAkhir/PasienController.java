package com.example.AbifemiTugasAkhir;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PasienController {

    @Autowired
    private PasienService pasienService;

  
    @GetMapping("/")
    public String menuAwal() {
        return "menu"; 
    }


    @GetMapping("/form")
    public String formPasien(Model model) {
        model.addAttribute("pasienBaru", new Pasien());
        return "form"; 
    }

 
    @GetMapping("/daftar")
    public String daftarPasien(Model model) {
        model.addAttribute("pasienList", pasienService.getAllPasien());
        return "pasien"; 
    }

   
    private void simpanKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data_pasien.txt"))) {
            List<Pasien> daftarPasien = pasienService.getAllPasien();
            for (Pasien p : daftarPasien) {
                writer.write(p.getNama() + "," + p.getAlamat() + "," + p.getPenyakit() + "," + p.getUmur());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
    @PostMapping("/tambah")
    public String tambahPasien(@ModelAttribute Pasien pasien) {
        pasienService.tambahPasien(pasien);
        simpanKeFile();  
        return "redirect:/daftar"; 
    }

   
    @GetMapping("/hapus/{index}")
    public String hapusPasien(@PathVariable int index) {
        pasienService.hapusPasien(index);
        simpanKeFile(); 
        return "redirect:/daftar";
    }

    
    @GetMapping("/edit/{index}")
    public String editForm(@PathVariable int index, Model model) {
        List<Pasien> daftarPasien = pasienService.getAllPasien();

   
        if (index < 0 || index >= daftarPasien.size()) {
            return "redirect:/daftar"; 
        }

        Pasien pasien = daftarPasien.get(index);
        model.addAttribute("pasien", pasien);
        model.addAttribute("index", index);
        return "edit";
    }

  
    @PostMapping("/update/{index}")
    public String updatePasien(@PathVariable int index, @ModelAttribute Pasien pasien) {
        pasienService.updatePasien(index, pasien);
        simpanKeFile(); 
        return "redirect:/daftar";
    }
}
