package br.com.vsl.VSLSystem.model.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.vsl.VSLSystem.model.exception.AccessLogException;

public class AccessReportParser {
	
    private static AccessReportParser instance;
    
    private AccessReportParser() {
    }

    static public AccessReportParser getInstance() {
        if (instance == null) {
            instance = new AccessReportParser();
        }
        return (instance);
    }
    
	public void insertAccessLog(GregorianCalendar accessLog) throws AccessLogException{
		try{
			List<GregorianCalendar> accessLogList = getAccessLogList(); 
			
			FileOutputStream arquivoGrav = new FileOutputStream("/Users/LucasGentile/Desktop/teste.txt");
			ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
			
			accessLogList.add(accessLog);
			
			objGravar.writeObject(accessLogList);
			objGravar.close();

		}catch (Exception e) {
			throw new AccessLogException("Fail to insert into access log file: " + e.getMessage(), e);
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<GregorianCalendar> getAccessLogList() throws AccessLogException{
	
        List<GregorianCalendar> accessLogList = new ArrayList<GregorianCalendar>();
        try{
        	
            FileInputStream arquivoLeitura = new FileInputStream("/Users/LucasGentile/Desktop/teste.txt");
            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);

            accessLogList = (List<GregorianCalendar>) objLeitura.readObject();
            
            objLeitura.close();
            arquivoLeitura.close();
            
        }catch( Exception e ){
        	throw new AccessLogException("Fail to read access log file: " + e.getMessage(), e);
        }
        return accessLogList;
	}
	
	
}