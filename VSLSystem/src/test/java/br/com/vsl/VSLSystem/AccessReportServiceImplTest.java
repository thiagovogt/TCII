package br.com.vsl.VSLSystem;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.vsl.VSLSystem.model.exception.AccessReportException;
import br.com.vsl.VSLSystem.model.service.implementation.AccessReportServiceImpl;

public class AccessReportServiceImplTest {

	AccessReportServiceImpl accessReportService = null; 
	HashMap<String, Integer> accessReport = null;
	
	@Before
	public void setUp() throws Exception {
		accessReportService = new AccessReportServiceImpl();
		accessReport = new HashMap<String, Integer>();
	}

	@After
	public void tearDown() throws Exception {
		accessReportService = null;
		accessReport = null;
	}
	
	@Test
	public void testGetAccessLogReportSize() throws AccessReportException {
		accessReport = accessReportService.getAccessLogReport();
		
		assertTrue(accessReport.size() > 1);
	}
	@Test
	public void testGetAccessLogReportDayAccess() throws AccessReportException {
		accessReport = accessReportService.getAccessLogReport();
		
		assertTrue(accessReport.get("accessDay") >= 0);
	}
	@Test
	public void testGetAccessLogReportMonthAccess() throws AccessReportException {
		accessReport = accessReportService.getAccessLogReport();
		
		assertTrue(accessReport.get("accessMonth") >= 0);
	}
	@Test
	public void testGetAccessLogReportYearAccess() throws AccessReportException {
		accessReport = accessReportService.getAccessLogReport();
		
		assertTrue(accessReport.get("accessYear") >= 0);
	}
}
