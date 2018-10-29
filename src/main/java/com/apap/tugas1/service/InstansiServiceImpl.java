package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.InstansiDB;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	@Autowired
	private InstansiDB instansiDB;
	
	@Override
	public List<InstansiModel> getInstansiList() {
		List<InstansiModel> listOfInstansi = instansiDB.findAll();
		return listOfInstansi;
	}

	@Override
	public InstansiModel getInstansiDetailById(long id) {
		return instansiDB.findById(id);
	}

}
