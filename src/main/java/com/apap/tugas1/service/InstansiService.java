package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;

import com.apap.tugas1.model.InstansiModel;

public interface InstansiService {
	InstansiModel getInstansiDetailById(long id);
	List<InstansiModel> getInstansiList();
}
