package br.com.vsl.VSLSystem.controller;

import java.util.GregorianCalendar;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vsl.VSLSystem.model.exception.AccessReportException;
import br.com.vsl.VSLSystem.model.service.implementation.AccessReportServiceImpl;
 
@Controller
public class AccessReportController {
 
	private AccessReportServiceImpl accessReportService;
	
	public AccessReportController(){
		this.accessReportService = new AccessReportServiceImpl();
	}
	
	/*
	 * Inserir um registro de acesso ao arquivo texto (relatório de acessos)
	 * 
	 */
	public String insertAccessLog() {
		try {
			accessReportService.insertAccessLog(new GregorianCalendar());
			return "";
		} catch (AccessReportException e) {
			return e.getMessage();
		}
	}
	
	/*
	 * Buscar o arquivo texto referente ao relatório de acessos e exibir para o usuário
	 * 
	 */
	@RequestMapping("/AccessReport")
	public ModelAndView AccessReport() {
		ModelAndView mv = new ModelAndView("AccessReport");

		HashMap<String, Integer> accessReport;
		try {
			accessReport = accessReportService.getAccessLogReport();
			
			mv.addObject("accessDay", accessReport.get("accessDay"));
			mv.addObject("accessMonth", accessReport.get("accessMonth"));
			mv.addObject("accessYear", accessReport.get("accessYear"));
		} catch (AccessReportException e) {
			mv.addObject("msg", e.getMessage());
		}
		
		return mv;
	}
}