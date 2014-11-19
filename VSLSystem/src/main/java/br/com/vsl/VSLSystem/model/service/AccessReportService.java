package br.com.vsl.VSLSystem.model.service;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import br.com.vsl.VSLSystem.model.exception.AccessReportException;

public interface AccessReportService {
	HashMap<String, Integer> getAccessLogReport() throws AccessReportException;
	void insertAccessLog(GregorianCalendar accessLog) throws AccessReportException;
	void cleanAccessReport(List<GregorianCalendar> accessLogListToClean) throws AccessReportException;
}
