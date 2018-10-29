package com.apap.tugas1.controller;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
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
		for (JabatanModel jabatanPegawai : archive.getJabatan()) {
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
		
		if (pegawai.getJabatan() == null) {
			pegawai.setJabatan(new ArrayList<JabatanModel>());
		}
		
		pegawai.getJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("provinsiList", provinsiService.getProvinsiList());
		model.addAttribute("instansiList", instansiService.getInstansiList());
		model.addAttribute("jabatanList", jabatanService.getJabatanList());
		
		model.addAttribute("addPegawai", true);
		model.addAttribute("notHome", true);
		return "add-pegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", params = {"submit"}, method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		
		
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("notHome", true);
		return "add";
	}	
	
	/*@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(
			@RequestParam(value = "nama") String nama,
			@RequestParam(value = "tempatLahir") String tempatLahir,
			@RequestParam(value = "tanggalLahir") Date tanggalLahir,
			@RequestParam(value = "tahunMasuk") String tahunMasuk,
			@RequestParam(value = "instansi") BigInteger idInstansi,
			@RequestParam(value = "jabatanPegawai") BigInteger idJabatan, Model model) {
		
		String kodeInstansi = idInstansi.toString();
		String tanggalLahirToString = String.format("%td%tm%ty", tanggalLahir, tanggalLahir, tanggalLahir);
		String tahunMasukPegawai = tahunMasuk;
		String nip = kodeInstansi+tanggalLahirToString+tahunMasukPegawai+"01";
		
		System.out.println(nip);
		
		InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi);
		JabatanModel jabatanPegawai = jabatanService.getJabatanDetailById(idJabatan);
		
		PegawaiModel pegawai = new PegawaiModel();
		
		List<JabatanModel> jabatan = new ArrayList<>();
		jabatan.add(jabatanPegawai);
		
		pegawai.setNip(nip);
		pegawai.setNama(nama);
		pegawai.setTempatLahir(tempatLahir);
		pegawai.setTanggalLahir(tanggalLahir);
		pegawai.setTahunMasuk(tahunMasuk);
		pegawai.setInstansi(instansi);
		pegawai.setJabatan(jabatan);
		
		System.out.println(pegawai.getNip());
		System.out.println(pegawai.getNama());
		System.out.println(pegawai.getTempatLahir());
		System.out.println(pegawai.getTanggalLahir());
		System.out.println(pegawai.getTahunMasuk());
		System.out.println(pegawai.getInstansi().getNama());
		System.out.println(pegawai.getJabatan().toString());
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("notHome", true);
		model.addAttribute("nip", nip);
		return "add";
	}*/
	
	@RequestMapping(value = "/pegawai/tambah", params = {"addRow"}, method = RequestMethod.POST)
	private String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingresult, Model model) {
		pegawai.getJabatan().add(new JabatanModel());
		if (pegawai.getJabatan() == null) {
			pegawai.setJabatan(new ArrayList<JabatanModel>());
		}
		System.out.println(pegawai.getNama());
		System.out.println(pegawai.getTempatLahir());
		System.out.println(pegawai.getTahunMasuk());
		System.out.println(pegawai.getJabatan().size());
		System.out.println(pegawai.getInstansi() == null? "Ya" : "Tidak");
		System.out.println(pegawai.getNip());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("provinsiList", provinsiService.getProvinsiList());
		model.addAttribute("instansiList", instansiService.getInstansiList());
		model.addAttribute("jabatanList", jabatanService.getJabatanList());
		
		model.addAttribute("addPegawai", true);
		model.addAttribute("notHome", true);
		return "add-pegawai";
	}

	/*@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String daftarPegawai(@RequestParam(value = "provinsi", required = false) BigInteger provinsi,
			@RequestParam(value = "instansi", required = false) BigInteger instansi,
			@RequestParam(value = "jabatan", required = false) BigInteger jabatan, Model model) {
		List<PegawaiModel> listPegawai = pegawaiService.getAllPegawaiWithFilter(provinsi, instansi, jabatan);
		JabatanModel jabatanTujuan = jabatanService.getJabatanDetailById(jabatan);

		List<ProvinsiModel> listProvinsi = provinsiService.getProvinsiList();
		List<JabatanModel> listJabatan = jabatanService.getJabatanList();

		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listPegawai", listPegawai);

		model.addAttribute("jabatanTujuan", jabatanTujuan);
		return "search-pegawai";

	}*/
	
	@RequestMapping(value = "/pegawai/cari",method = RequestMethod.GET)
	public String cariPegawai(Model model) {
		model.addAttribute("provinsiList", provinsiService.getProvinsiList());
		model.addAttribute("instansiList", instansiService.getInstansiList());
		model.addAttribute("jabatanList", jabatanService.getJabatanList());
		model.addAttribute("cariPegawai", true);
		model.addAttribute("notHome", true);
		return "view-all-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari",  method = RequestMethod.POST)
	public String daftarPegawai(
			@RequestParam(value = "provinsi", required = false) BigInteger idProvinsi,
			@RequestParam(value = "instansi", required = false) BigInteger idInstansi,
			@RequestParam(value = "jabatanPegawai", required = false) BigInteger idJabatan, Model model) {
		System.out.println("====");
		System.out.println(idProvinsi);
		System.out.println(idInstansi);
		System.out.println(idJabatan);
		
		model.addAttribute("provinsiList", provinsiService.getProvinsiList());
		model.addAttribute("instansiList", instansiService.getInstansiList());
		model.addAttribute("jabatanList", jabatanService.getJabatanList());
		
		List<PegawaiModel> listPegawai = new ArrayList<>();
		
		if (idProvinsi == null && idInstansi == null && idJabatan == null) {
			System.out.println("\n1");
			listPegawai.addAll(pegawaiService.getPegawaiList());
		} else if (idProvinsi != null && idInstansi == null && idJabatan == null) {
			System.out.println("\n2");
			listPegawai = provinsiService.getPegawaiInProvinsiById(idProvinsi);
		} else if (idProvinsi != null && idInstansi != null && idJabatan == null) {
			System.out.println("\n3");
			listPegawai = provinsiService.getPegawaiInInstansiInProvinsiById(idProvinsi, idInstansi);
		}
		
		System.out.println("====");
		
		InstansiModel instansiTujuan = instansiService.getInstansiDetailById(idInstansi);
	    JabatanModel jabatanTujuan = jabatanService.getJabatanDetailById(idJabatan);
	    
	    model.addAttribute("instansiTujuan", instansiTujuan);
        model.addAttribute("jabatanTujuan", jabatanTujuan);
		
		model.addAttribute("listPegawai", listPegawai);
		model.addAttribute("cariPegawai", true);
		model.addAttribute("notHome", true);
		return "view-all-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua",method = RequestMethod.GET)
	public String termudaTertua(@RequestParam(value = "instansi") BigInteger idInstansi, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi);
		
		// get data pegawai tertua
		PegawaiModel pegawaiTertua = instansi.getPegawaiTertua();
		Double maxGajiPegawaiTertua = 0.0;
		for (JabatanModel jabatanPegawai : pegawaiTertua.getJabatan()) {
			if (jabatanPegawai.getGajiPokok() > maxGajiPegawaiTertua) {
				maxGajiPegawaiTertua = jabatanPegawai.getGajiPokok();
			}
		}
		
		Double tunjanganPegawaiTertua = (pegawaiTertua.getInstansi().getProvinsi().getPresentaseTunjangan() / 100) * maxGajiPegawaiTertua;
		Double gajiPegawaiTertua = maxGajiPegawaiTertua + tunjanganPegawaiTertua;
		
		// get data pegawai termuda
		PegawaiModel pegawaiTermuda = instansi.getPegawaiTermuda();
		Double maxGajiPegawaiTermuda = 0.0;
		for (JabatanModel jabatanPegawai : pegawaiTermuda.getJabatan()) {
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
	
}
