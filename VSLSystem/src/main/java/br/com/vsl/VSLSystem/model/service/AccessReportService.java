package br.com.vsl.VSLSystem.model.service;

import java.util.GregorianCalendar;
import java.util.List;

import br.com.vsl.VSLSystem.model.entity.Author;
import br.com.vsl.VSLSystem.model.exception.DBLPException;

public interface AccessReportService {
	void insertAccessLog(GregorianCalendar accessLog);
	List<GregorianCalendar> getAccessLog();
}
