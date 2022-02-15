package br.com.esocial.webservice.producaorestrita.consultarLote.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import br.com.esocial.webservice.producaorestrita.consultarLote.ConsultarLoteEventos.Consulta;
import br.com.esocial.webservice.producaorestrita.consultarLote.ConsultarLoteEventosResponse.ConsultarLoteEventosResult;
import br.com.esocial.webservice.producaorestrita.consultarLote.ServicoConsultarLoteEventos;
import br.com.esocial.webservice.producaorestrita.consultarLote.ServicoConsultarLoteEventos_Service;
import br.com.esocial.webservice.util.EsocialUtil;

public class ConsultarLoteEventosTest {
	
	public static void main(String[] args) throws Exception {		
		int i=0;
		for (String arg : args) {
			System.out.println("args["+ i++ +"] : " + arg);	
		}
		
//		Map<String, String> map = DAOGenerico.executeQueryForUniqueValue("SELECT NUM_CPF AS RESULTADO FROM TB_PESSOA_FISICA PF WHERE ROWNUM = 1 ");
//		System.out.println("Resultado: " + map.get("RESULTADO"));
		
		File file = new File("D:\\Esocial\\consulta");
		
		for(File f: file.listFiles()) {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			StringBuilder sb = new StringBuilder();
			
			while((line = br.readLine()) != null){
			    sb.append(line.trim() + "\r\n");
			}
			
			ServicoConsultarLoteEventos_Service service = new ServicoConsultarLoteEventos_Service();
			ServicoConsultarLoteEventos consultarLote = service.getServicosEmpregadorServicoConsultarLoteEventos();
			Consulta consulta = new Consulta();
			consulta.setAny(EsocialUtil.toDocument(XML_CONSULTA).getDocumentElement());
			ConsultarLoteEventosResult result = consultarLote.consultarLoteEventos(consulta);
			
			System.out.println(EsocialUtil.toObject(result));
		}
		/*
		ServicoConsultarLoteEventos_Service service = new ServicoConsultarLoteEventos_Service();
		ServicoConsultarLoteEventos consultarLote = service.getServicosEmpregadorServicoConsultarLoteEventos();
		Consulta consulta = new Consulta();
		consulta.setAny(EsocialUtil.toDocument(XML_CONSULTA).getDocumentElement());
		ConsultarLoteEventosResult result = consultarLote.consultarLoteEventos(consulta);
		
		System.out.println(EsocialUtil.toObject(result));
		*/		
	}
	
	public static final String XML_CONSULTA = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
			"<eSocial xmlns=\"http://www.esocial.gov.br/schema/lote/eventos/envio/consulta/retornoProcessamento/v1_0_0\" " 
			+ "xsi:schemaLocation=\"http://www.esocial.gov.br/schema/lote/eventos/envio/consulta/retornoProcessamento/v1_0_0 schema.xsd\" "
			+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n" + 
			"	<consultaLoteEventos>\r\n" + 
			"		<protocoloEnvio>1.2.202202.0000000000107091842</protocoloEnvio>\r\n" + 
			"	</consultaLoteEventos>\r\n" + 
			"</eSocial>";

}
