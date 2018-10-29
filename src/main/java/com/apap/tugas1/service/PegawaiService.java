package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	List<PegawaiModel> getPegawaiList();
	void addPegawai(PegawaiModel pegawai);
	void generateNip(PegawaiModel pegawai);
	void updatePegawai(PegawaiModel pegawai);
	PegawaiDB getPegawaiDb();
}
