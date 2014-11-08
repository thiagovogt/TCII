package br.com.vsl.VSLSystem.model.service.implementation;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import br.com.vsl.VSLSystem.model.exception.AccessReportException;
import br.com.vsl.VSLSystem.model.repository.AccessReportParser;
import br.com.vsl.VSLSystem.model.service.AccessReportService;

public class AccessReportServiceImpl implements AccessReportService {

	@Override
	public HashMap<String, Integer> getAccessLogReport() throws AccessReportException {

		List<GregorianCalendar> accessLogList = new ArrayList<GregorianCalendar>();
		HashMap<String, Integer> accessLogCount = new HashMap<String, Integer>();

		accessLogList = AccessReportParser.getInstance().getAccessLogList();

		int accessDay = 0;
		int accessMonth = 0;
		int accessYear = 0;

		GregorianCalendar todayDate = new GregorianCalendar();

		for (GregorianCalendar accessLogDate : accessLogList) {
			if (todayDate.get(GregorianCalendar.DAY_OF_MONTH) == accessLogDate.get(GregorianCalendar.DAY_OF_MONTH) && 
					todayDate.get(GregorianCalendar.MONTH) == accessLogDate.get(GregorianCalendar.MONTH) && 
						todayDate.get(GregorianCalendar.YEAR) == accessLogDate.get(GregorianCalendar.YEAR)) {
				accessDay++;
				accessMonth++;
				accessYear++;
			} else if (todayDate.get(GregorianCalendar.MONTH) == accessLogDate.get(GregorianCalendar.MONTH) 
					&& todayDate.get(GregorianCalendar.YEAR) == accessLogDate.get(GregorianCalendar.YEAR)) {

				accessMonth++;
				accessYear++;
			} else if (todayDate.get(GregorianCalendar.YEAR) == accessLogDate.get(GregorianCalendar.YEAR)) {
				accessYear++;
			}else{
				accessLogList.remove(accessLogDate);
			}
		}

		accessLogCount.put("accessDay", accessDay);
		accessLogCount.put("accessMonth", accessMonth);
		accessLogCount.put("accessYear", accessYear);

		return accessLogCount;
	}

	@Override
	public void insertAccessLog(GregorianCalendar accessLog) throws AccessReportException {
		AccessReportParser.getInstance().insertAccessLog(accessLog);
	}
	
}
