package br.com.vsl.VSLSystem.model.service.implementation;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import br.com.vsl.VSLSystem.model.entity.Author;
import br.com.vsl.VSLSystem.model.exception.DBLPException;
import br.com.vsl.VSLSystem.model.repository.AccessReportParser;
import br.com.vsl.VSLSystem.model.repository.AuthorDBLP;
import br.com.vsl.VSLSystem.model.service.AccessReportService;
import br.com.vsl.VSLSystem.model.service.AuthorService;

public class AccessReportServiceImpl implements AccessReportService{
	

	@Override
	public void insertAccessLog(GregorianCalendar accessLog) {
		AccessReportParser.getInstance().insertAccessLog(accessLog);
	}

	@Override
	public List<GregorianCalendar> getAccessLog() {
		return AccessReportParser.getInstance().getAccessLog();
	}
}
