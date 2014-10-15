package br.com.vsl.VSLSystem.model.service;

import java.util.GregorianCalendar;
import java.util.HashMap;

import br.com.vsl.VSLSystem.model.exception.AccessLogException;

public interface AccessReportService {
	HashMap<String, Integer> getAccessLogReport() throws AccessLogException;
	void insertAccessLog(GregorianCalendar accessLog) throws AccessLogException;
}
