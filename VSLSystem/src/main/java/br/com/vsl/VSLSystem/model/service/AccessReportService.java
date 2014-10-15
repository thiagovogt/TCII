package br.com.vsl.VSLSystem.model.service;

import java.util.GregorianCalendar;
import java.util.HashMap;

import br.com.vsl.VSLSystem.model.exception.AccessReportException;

public interface AccessReportService {
	HashMap<String, Integer> getAccessLogReport() throws AccessReportException;
	void insertAccessLog(GregorianCalendar accessLog) throws AccessReportException;
}
