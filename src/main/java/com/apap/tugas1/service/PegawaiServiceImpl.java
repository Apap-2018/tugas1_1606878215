package com.apap.tugas1.service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDB pegawaiDB;

	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDB.findByNip(nip);
	}

	@Override
	public List<PegawaiModel> getPegawaiList() {
		List<PegawaiModel> listOfPegawai = pegawaiDB.findAll();
		return listOfPegawai;
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		System.out.println(pegawai.getNip());;		
		this.generateNip(pegawai);
		System.out.println(pegawai.getNip());
		pegawaiDB.save(pegawai);
	}
	
	@Override
	public PegawaiDB getPegawaiDb() {
		return pegawaiDB;
	}

	@Override
	public void generateNip(PegawaiModel pegawai) {	
		String NIP = "";
		
		String subNipKodeInstansi = Long.toString(pegawai.getInstansi().getId());
		String subNipTanggalLahir = String.format("%td%tm%ty", pegawai.getTanggalLahir(), pegawai.getTanggalLahir(), pegawai.getTanggalLahir());
		String subNipTahunMasuk = pegawai.getTahunMasuk();
		String subNipUrutanPegawai = "";
		
		int urutanPegawai = 0;
		
		List<PegawaiModel> listPegawai = this.getPegawaiList();

		listPegawai = listPegawai.stream()
			.filter(pgw -> pgw.getInstansi().getId() == pegawai.getInstansi().getId())
			.filter(pgw -> pgw.getTanggalLahir().equals(pegawai.getTanggalLahir()))
			.filter(pgw -> pgw.getTahunMasuk().equalsIgnoreCase(pegawai.getTahunMasuk()))
			.collect(Collectors.toList());
		  
		if (listPegawai.isEmpty()) {
			  urutanPegawai += 1;
		}
		  
		else {
			urutanPegawai = listPegawai.size() + 1;
		}
		
		if (urutanPegawai <= 9) {
			subNipUrutanPegawai = "0" + Integer.toString(urutanPegawai); 
			NIP = subNipKodeInstansi + subNipTanggalLahir + subNipTahunMasuk + subNipUrutanPegawai;
		}
		
		else {
			subNipUrutanPegawai = Integer.toString(urutanPegawai);
			NIP = subNipKodeInstansi + subNipTanggalLahir + subNipTahunMasuk + subNipUrutanPegawai;
		}
		
		pegawai.setNip(NIP);
	}

	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		PegawaiModel pegawaiLama = pegawaiDB.findByNip(pegawai.getNip());
		
		pegawaiLama.setNama(pegawai.getNama());
		pegawaiLama.setTempatLahir(pegawai.getTempatLahir());
		pegawaiLama.setTanggalLahir(pegawai.getTanggalLahir());
		pegawaiLama.setTahunMasuk(pegawai.getTahunMasuk());
		pegawaiLama.setInstansi(pegawai.getInstansi());
		pegawaiLama.setJabatanPegawai(pegawai.getJabatanPegawai());
		
		this.generateNip(pegawaiLama);
		
	}
}
