package com.apap.tugas1.controller;

import java.math.BigInteger;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class JabatanController {
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping("/jabatan/view")
	public String view(@RequestParam("IdJabatan") long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);	
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("gajiPokok", jabatan.getGajiPokok().intValue());
		model.addAttribute("jumlahPegawai", jabatan.getPegawai().size());
		model.addAttribute("viewJabatan", true);
		model.addAttribute("notHome", true);
		return "view-jabatan";
	}
	
	@RequestMapping("/jabatan/viewall")
	public String viewAll(Model model) {
		model.addAttribute("listJabatan", jabatanService.getJabatanList());
		model.addAttribute("daftarJabatan", true);
		model.addAttribute("notHome", true);
		return "view-all-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String tambah(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		model.addAttribute("addJabatan", true);
		model.addAttribute("notHome", true);
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String confirmTambah(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("addJabatan", true);
		model.addAttribute("notHome", true);
		return "add";
	}
	
	@RequestMapping("/jabatan/hapus")
	private String hapus(@RequestParam("id") long id, RedirectAttributes rm, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);	
		if (!jabatan.getPegawai().isEmpty()) {
			rm.addFlashAttribute("existPegawai", true);
			rm.addFlashAttribute("notHome", true);
			return "redirect:/jabatan/view?IdJabatan=" + id;
		}
		model.addAttribute("jabatan", jabatan);
		jabatanService.deleteJabatan(id);
		return "delete";
	}
	
	@RequestMapping("/jabatan/ubah")
	private String ubah(@RequestParam("id") long id, Model model) {
		model.addAttribute("idJabatan", id);
		model.addAttribute("notHome", true);
		return "update-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	public String confirmUbah(@RequestParam("idJabatan") long id, 
			@RequestParam("newNama") String newNama, 
			@RequestParam("newDeskripsi") String newDeskripsi, 
			@RequestParam("newGajiPokok") Double newGajiPokok, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		
		jabatan.setNama(newNama);
		jabatan.setDeskripsi(newDeskripsi);
		jabatan.setGajiPokok(newGajiPokok);
		
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("updateJabatan", true);
		model.addAttribute("notHome", true);
		return "update";
	}

}
