package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDB;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService {
	@Autowired
	private ProvinsiDB provinsiDB;

	@Override
	public List<ProvinsiModel> getProvinsiList() {
		List<ProvinsiModel> listOfProvinsi = provinsiDB.findAll();
		return listOfProvinsi;
	}

	@Override
	public ProvinsiModel getProvinsiDetailById(BigInteger id) {
		return provinsiDB.findById(id);
	}
	
	// Jika difilter dengan provinsi
	@Override
	public List<PegawaiModel> getPegawaiInProvinsiById(BigInteger idProvinsi) {
		ProvinsiModel provinsi = provinsiDB.findById(idProvinsi);
		List<PegawaiModel> listPegawai = new ArrayList<>();
		
		for (InstansiModel instansi : provinsi.getInstansi()) {
			for (PegawaiModel pegawai : instansi.getPegawai()) {
				listPegawai.add(pegawai);
			}
		}
		return listPegawai;
	}
	
	// Jika difilter dengan provinsi dan instansi
	@Override
	public List<PegawaiModel> getPegawaiInInstansiInProvinsiById(BigInteger idProvinsi, BigInteger idInstansi) {
		ProvinsiModel provinsi = provinsiDB.findById(idProvinsi);
		InstansiModel instansi = new InstansiModel();
		
		for (InstansiModel instansiTujuan : provinsi.getInstansi()) {
			if (instansiTujuan.getId().equals(idInstansi)) {
				instansi = instansiTujuan;
				break;
			}
		}
		
		List<PegawaiModel> listPegawai = new ArrayList<>();
		
		for (PegawaiModel pegawai : instansi.getPegawai()) {
			listPegawai.add(pegawai);
		}
		
		return listPegawai;
	}


}
