package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDB jabatanDB;
	
	@Override
	public JabatanModel getJabatanDetailById(long id) {
		return jabatanDB.findById(id);
	}

	@Override
	public List<JabatanModel> getJabatanList() {
		List<JabatanModel> listOfJabatan = jabatanDB.findAll();
		return listOfJabatan;
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDB.save(jabatan);
		
	}

	@Override
	public void deleteJabatan(long id) {
		jabatanDB.delete(jabatanDB.findById(id));
		
	}

}
