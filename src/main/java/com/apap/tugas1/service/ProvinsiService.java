package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiService {
	List<ProvinsiModel> getProvinsiList();
	ProvinsiModel getProvinsiDetailById(BigInteger id);
	List<PegawaiModel> getPegawaiInInstansiInProvinsiById(BigInteger idProvinsi, BigInteger idInstansi);
	List<PegawaiModel> getPegawaiInProvinsiById(BigInteger idProvinsi);
}
