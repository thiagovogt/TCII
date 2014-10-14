package br.com.vsl.VSLSystem.model.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

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
    
	public void insertAccessLog(GregorianCalendar accessLog){
		try{
			List<GregorianCalendar> accessLogList = getAccessLog(); 
//			List<GregorianCalendar> accessLogList = new ArrayList<>();
			
//			if(accessLogList == null){
//				accessLogList = new ArrayList<>();
//			}
			
			FileOutputStream arquivoGrav = new FileOutputStream("/Users/LucasGentile/Desktop/teste.txt");
			ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
			
			accessLogList.add(accessLog);
			
			objGravar.writeObject(accessLogList);
			objGravar.close();

			System.out.println("Objeto gravado com sucesso!");

		}catch (Exception e) {

			e.printStackTrace();

		}

	}
	
	public List<GregorianCalendar> getAccessLog(){
	
        System.out.println("Recuperando objeto: ");
        List<GregorianCalendar> accessLogList = new ArrayList<GregorianCalendar>();
        try{

            //Carrega o arquivo
        	
            FileInputStream arquivoLeitura = new FileInputStream("/Users/LucasGentile/Desktop/teste.txt");
            //Classe responsavel por recuperar os objetos do arquivo

            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);

            accessLogList = (List<GregorianCalendar>) objLeitura.readObject();
            
            int accessDay = 0;
            int accessMonth = 0;
            int accessYear = 0;
            
            GregorianCalendar today = new GregorianCalendar();
            
            for (GregorianCalendar gregorianCalendar : accessLogList) {
            	if(today.get(GregorianCalendar.DAY_OF_MONTH) == gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH) &&
            			today.get(GregorianCalendar.MONTH) == gregorianCalendar.get(GregorianCalendar.MONTH) &&
            				today.get(GregorianCalendar.YEAR) == gregorianCalendar.get(GregorianCalendar.YEAR)){
            		accessDay++;
            		accessMonth++;
            		accessYear++;
            	}else if(today.get(GregorianCalendar.MONTH) == gregorianCalendar.get(GregorianCalendar.MONTH) &&
            			today.get(GregorianCalendar.YEAR) == gregorianCalendar.get(GregorianCalendar.YEAR)){
            		
            		accessMonth++;
            		accessYear++;
            	} else if(today.get(GregorianCalendar.YEAR) == gregorianCalendar.get(GregorianCalendar.YEAR)){
            		accessYear++;
            	}
            	
            	System.out.println(gregorianCalendar.getTime());
			}
            System.out.println("Tamanho lista: " + accessLogList.size());
            System.out.println("Dia: " + accessDay + " | Mês: " + accessMonth + " | Ano: " + accessYear );
            objLeitura.close();

            arquivoLeitura.close();

            
        }catch( Exception e ){
                e.printStackTrace( );
        }
        return accessLogList;
	}
}