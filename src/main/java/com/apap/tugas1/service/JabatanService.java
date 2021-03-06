package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	JabatanModel getJabatanDetailById(long id);
	List<JabatanModel> getJabatanList();
	void addJabatan(JabatanModel jabatan);
	void deleteJabatan(long id);
}
