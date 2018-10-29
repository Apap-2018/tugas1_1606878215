package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiService {
	List<ProvinsiModel> getProvinsiList();
	ProvinsiModel getProvinsiDetailById(long id);
	List<PegawaiModel> getPegawaiInInstansiInProvinsiById(long idProvinsi, long idInstansi);
	List<PegawaiModel> getPegawaiInProvinsiById(long idProvinsi);
}
