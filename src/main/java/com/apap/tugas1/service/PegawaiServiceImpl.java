package com.apap.tugas1.service;

import java.util.List;

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
		pegawaiDB.save(pegawai);
	}

	@Override
	public void generateNip(PegawaiModel pegawai) {
		String subNipKodeInstansi = pegawai.getInstansi().getId().toString();
		String subNipTanggalLahir = String.format("%td%tm%ty", pegawai.getTanggalLahir(), pegawai.getTanggalLahir(), pegawai.getTanggalLahir());
		String subNipTahunMasuk = pegawai.getTahunMasuk();
		
		
	}
}
