package br.com.vsl.VSLSystem.model.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.vsl.VSLSystem.model.exception.AccessReportException;

public class AccessReportParser {
	
	private static final String accessReportFilePath = "/Users/LucasGentile/Desktop/accessReport.txt";
    private static AccessReportParser instance;
    
    private AccessReportParser() {
    }

    static public AccessReportParser getInstance() {
        if (instance == null) {
            instance = new AccessReportParser();
        }
        return (instance);
    }
    
    /*
     * 
     * Método responsável por inserir um registro de acesso 
     * serializado no arquivo texto onde são armazenados os acessos ao sistema
     * 
     * */
	public void insertAccessLog(GregorianCalendar accessLog) throws AccessReportException{
		try{
			List<GregorianCalendar> accessLogList = getAccessLogList(); 
			
			FileOutputStream arquivoGrav = new FileOutputStream(accessReportFilePath);
			ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
			
			accessLogList.add(accessLog);
			
			objGravar.writeObject(accessLogList);
			objGravar.close();

		}catch (Exception e) {
			throw new AccessReportException("Failed to insert into access report file: " + e.getMessage(), e);
		}

	}
	
	/*
	 * 
	 * Método responsável por ler o arquivo de texto e 
	 * retornar uma lista dos acessos armazenados
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<GregorianCalendar> getAccessLogList() throws AccessReportException{
	
        List<GregorianCalendar> accessLogList = new ArrayList<GregorianCalendar>();
        try{
        	
            FileInputStream arquivoLeitura = new FileInputStream(accessReportFilePath);
            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);

            accessLogList = (List<GregorianCalendar>) objLeitura.readObject();
            
            objLeitura.close();
            arquivoLeitura.close();
            
        }catch( Exception e ){
        	throw new AccessReportException("Failed to read access log report: " + e.getMessage(), e);
        }
        return accessLogList;
	}
	
	
}