package com.apap.tugas1.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;

	@Autowired
	private ProvinsiService provinsiService;

	@Autowired
	private InstansiService instansiService;

	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	private String home(Model model) {
		model.addAttribute("home", true);
		model.addAttribute("jabatanList", jabatanService.getJabatanList());
		model.addAttribute("instansiList", instansiService.getInstansiList());
		return "home";
	}

	@RequestMapping("/pegawai")
	public String view(@RequestParam("nip") String nip, Model model) {
		PegawaiModel archive = pegawaiService.getPegawaiDetailByNip(nip);
		model.addAttribute("pegawai", archive);
		Double maxGaji = 0.0;
		for (JabatanModel jabatanPegawai : archive.getJabatanPegawai()) {
			if (jabatanPegawai.getGajiPokok() > maxGaji) {
				maxGaji = jabatanPegawai.getGajiPokok();
			}
		}
		Double tunjangan = (archive.getInstansi().getProvinsi().getPresentaseTunjangan() / 100) * maxGaji;
		Double gaji = maxGaji + tunjangan;
		model.addAttribute("gaji", gaji.intValue());
		model.addAttribute("viewPegawai", true);
		model.addAttribute("notHome", true);
		return "view-pegawai";	
	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		
		pegawai.setJabatanPegawai(new ArrayList<JabatanModel>());
		pegawai.getJabatanPegawai().add(new JabatanModel());		
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("provinsiList", provinsiService.getProvinsiList());
		model.addAttribute("instansiList", instansiService.getInstansiList());
		model.addAttribute("jabatanList", jabatanService.getJabatanList());
		
		model.addAttribute("addPegawai", true);
		model.addAttribute("notHome", true);
		return "add-pegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", params = {"submit"}, method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute @Valid PegawaiModel pegawai, Model model) {
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("nip", pegawai.getNip());
		model.addAttribute("addPegawai", true);
		model.addAttribute("notHome", true);
		return "add";
	}	
	
	@RequestMapping(value = "/pegawai/tambah", params = {"addRow"}, method = RequestMethod.POST)
	private String addRow(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawai.getJabatanPegawai().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("provinsiList", provinsiService.getProvinsiList());
		model.addAttribute("instansiList", instansiService.getInstansiList());
		model.addAttribute("jabatanList", jabatanService.getJabatanList());
		
		model.addAttribute("addPegawai", true);
		model.addAttribute("notHome", true);
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari",method = RequestMethod.GET)
	public String cariPegawai(Model model) {
		model.addAttribute("provinsiList", provinsiService.getProvinsiList());
		model.addAttribute("instansiList", instansiService.getInstansiList());
		model.addAttribute("jabatanList", jabatanService.getJabatanList());
		model.addAttribute("pegawaiList", pegawaiService.getPegawaiList());
		model.addAttribute("cariPegawai", true);
		model.addAttribute("notHome", true);
		return "view-all-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari",  method = RequestMethod.POST)
	public String daftarPegawai(
			@RequestParam(value = "provinsi", required = false) long id_Provinsi,
			@RequestParam(value = "instansi", required = false) long id_Instansi,
			@RequestParam(value = "jabatanPegawai", required = false) long id_Jabatan, Model model) {
		System.out.println("====");
		System.out.println(id_Provinsi);
		System.out.println(id_Instansi);
		System.out.println(id_Jabatan);
		

		
		List<PegawaiModel> listPegawai = new ArrayList<PegawaiModel>();
		
/*		if (idProvinsi == null && idInstansi == null && idJabatan == null) {
			System.out.println("\n1");
			listPegawai.addAll(pegawaiService.getPegawaiList());
		} else if (idProvinsi != null && idInstansi == null && idJabatan == null) {
			System.out.println("\n2");
			listPegawai = provinsiService.getPegawaiInProvinsiById(idProvinsi);
		} else if (idProvinsi != null && idInstansi != null && idJabatan == null) {
			System.out.println("\n3");
			listPegawai = provinsiService.getPegawaiInInstansiInProvinsiById(idProvinsi, idInstansi);
		}*/
		
		/*if(id_Provinsi != null && id_Instansi != null && id_Jabatan != null) {
			listPegawai = pegawaiService.getPegawaiDb().findByProvinsiAndInstansiAndJabatan(id_Provinsi, id_Instansi, id_Jabatan);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan provinsi, instansi, dan jabatan");
		} else if (id_Provinsi != null && id_Instansi != null && id_Jabatan == null) {
			listPegawai = pegawaiService.getPegawaiDb().findByProvinsiAndInstansi(id_Provinsi, id_Instansi);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan provinsi dan instansi");
		} else if (id_Provinsi != null && id_Instansi == null && id_Jabatan != null) {
			listPegawai = pegawaiService.getPegawaiDb().findByProvinsiAndJabatan(id_Provinsi, id_Jabatan);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan provinsi dan jabatan");
		} else if (id_Provinsi == null && id_Instansi != null && id_Jabatan != null) {
			listPegawai = pegawaiService.getPegawaiDb().findByInstansiAndJabatan(id_Instansi, id_Jabatan);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan instansi dan jabatan");		
		} else if (id_Provinsi != null && id_Instansi == null && id_Jabatan == null) {
			listPegawai = pegawaiService.getPegawaiDb().findByProvinsi(id_Provinsi);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan provinsi");
		} else if (id_Provinsi == null && id_Instansi != null && id_Jabatan == null) {
			listPegawai = pegawaiService.getPegawaiDb().findByInstansi(instansiService.getInstansiDetailById(id_Instansi));
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan instansi");
		} else if (id_Provinsi == null && id_Instansi == null && id_Jabatan != null) {
			listPegawai = pegawaiService.getPegawaiDb().findByJabatan(id_Jabatan);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan jabatan");
		}*/
		
		System.out.println("====");
		
		InstansiModel instansiTujuan = instansiService.getInstansiDetailById(id_Instansi);
	    JabatanModel jabatanTujuan = jabatanService.getJabatanDetailById(id_Jabatan);
	    
	    model.addAttribute("instansiTujuan", instansiTujuan);
        model.addAttribute("jabatanTujuan", jabatanTujuan);
		
		model.addAttribute("provinsiList", provinsiService.getProvinsiList());
		model.addAttribute("instansiList", instansiService.getInstansiList());
		model.addAttribute("jabatanList", jabatanService.getJabatanList());
        
		model.addAttribute("pegawaiList", listPegawai);
		model.addAttribute("cariPegawai", true);
		model.addAttribute("notHome", true);
		return "view-all-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua",method = RequestMethod.GET)
	public String termudaTertua(@RequestParam(value = "instansi") long idInstansi, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi);
		
		// get data pegawai tertua
		PegawaiModel pegawaiTertua = instansi.getPegawaiTertua();
		Double maxGajiPegawaiTertua = 0.0;
		for (JabatanModel jabatanPegawai : pegawaiTertua.getJabatanPegawai()) {
			if (jabatanPegawai.getGajiPokok() > maxGajiPegawaiTertua) {
				maxGajiPegawaiTertua = jabatanPegawai.getGajiPokok();
			}
		}
		
		Double tunjanganPegawaiTertua = (pegawaiTertua.getInstansi().getProvinsi().getPresentaseTunjangan() / 100) * maxGajiPegawaiTertua;
		Double gajiPegawaiTertua = maxGajiPegawaiTertua + tunjanganPegawaiTertua;
		
		// get data pegawai termuda
		PegawaiModel pegawaiTermuda = instansi.getPegawaiTermuda();
		Double maxGajiPegawaiTermuda = 0.0;
		for (JabatanModel jabatanPegawai : pegawaiTermuda.getJabatanPegawai()) {
			if (jabatanPegawai.getGajiPokok() > maxGajiPegawaiTermuda) {
				maxGajiPegawaiTermuda = jabatanPegawai.getGajiPokok();
			}
		}
		Double tunjanganPegawaiTermuda = (pegawaiTermuda.getInstansi().getProvinsi().getPresentaseTunjangan() / 100) * maxGajiPegawaiTermuda;
		Double gajiPegawaiTermuda = maxGajiPegawaiTermuda + tunjanganPegawaiTermuda;
		
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		model.addAttribute("gajiPegawaiTertua", gajiPegawaiTertua.intValue());
		
		System.out.println(gajiPegawaiTertua.intValue());
		
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("gajiPegawaiTermuda", gajiPegawaiTermuda.intValue());
		
		System.out.println(gajiPegawaiTermuda.intValue());
		return "view-pegawai-termuda-tertua";
	}
	
	@RequestMapping("/pegawai/ubah")
	private String ubah(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		
		model.addAttribute("provinsiList", provinsiService.getProvinsiList());
		model.addAttribute("instansiList", instansiService.getInstansiList());
		model.addAttribute("jabatanList", jabatanService.getJabatanList());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("notHome", true);
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	public String confirmUbah(@ModelAttribute PegawaiModel pegawai,Model model) {
		pegawaiService.updatePegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("updatePegawai", true);
		model.addAttribute("notHome", true);
		return "update";
	}
	
}
