package br.com.esocial.webservice.producaorestrita.consultarLote.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import br.com.esocial.webservice.producaorestrita.consultarLote.ConsultarLoteEventos.Consulta;
import br.com.esocial.webservice.producaorestrita.consultarLote.ConsultarLoteEventosResponse.ConsultarLoteEventosResult;
import br.com.esocial.webservice.producaorestrita.consultarLote.ServicoConsultarLoteEventos;
import br.com.esocial.webservice.producaorestrita.consultarLote.ServicoConsultarLoteEventos_Service;
import br.com.esocial.webservice.util.EsocialUtil;

/**
 *  Classe responsavel por ler o diret�rio onde cont�m TODOS os XMLS gravados que foram gerados
 *  pelo servi�o de ENVIO de LOTE, ou seja, o XML de retorno do envio puros.
 * 
 */
public class RenanConsultarLoteEventosTest {	
	
	public static String XML_EXEMPLO_CONSULTA = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
			"<eSocial xmlns=\"http://www.esocial.gov.br/schema/lote/eventos/envio/consulta/retornoProcessamento/v1_0_0\" " 
			+ "xsi:schemaLocation=\"http://www.esocial.gov.br/schema/lote/eventos/envio/consulta/retornoProcessamento/v1_0_0 schema.xsd\" "
			+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n" + 
			"	<consultaLoteEventos>\r\n" + 
			"		<protocoloEnvio>" + "[{numeroProtocoloDentroTag}]" + "</protocoloEnvio>\r\n" + 
			"	</consultaLoteEventos>\r\n" + 
			"</eSocial>";

	public static void main(String[] args) throws IOException, JAXBException {
		File file = new File("C:\\Users\\Vitor Lauria\\Desktop\\LotesDiogo\\xm_retorno_envio"); //Pasta onde est�o os XMLS puros retornados ap�s chamar o servi�o de envio inicialmente.
		
		List<String> listaProtocolos = new ArrayList<String>();
 		for(File arquivoXML : file.listFiles()) {
 			String conteudoArquivoXML = lerConteudoArquivo(arquivoXML);
            String numeroProtocoloDentroDaTag = obterNumeroProtocolo(conteudoArquivoXML);		
 			listaProtocolos.add(numeroProtocoloDentroDaTag);
		}
 		
 		int contador = 0;
 		for(String protocolo : listaProtocolos){
 			String xmlConsulta = XML_EXEMPLO_CONSULTA.replace("[{numeroProtocoloDentroTag}]", protocolo);
 			String xmlConsultaRetorno = chamarServicoConsulta(xmlConsulta);
			gravarArquivo(xmlConsultaRetorno, ++contador);
 		}
 		
	}
	
	private static String obterNumeroProtocolo(String conteudoXML) {
		int posicaoInicialDoProtocoloEnvio = conteudoXML.indexOf("<protocoloEnvio>");
		int posicaoFinalDoProtocoloEnvio    = conteudoXML.indexOf("</protocoloEnvio>");
		int qntCaracteresDaTagProtocoloEnvio = 16;
		String protocoloDentroDaTag = conteudoXML.substring(posicaoInicialDoProtocoloEnvio + qntCaracteresDaTagProtocoloEnvio, posicaoFinalDoProtocoloEnvio);
		return protocoloDentroDaTag;
	}

	private static String lerConteudoArquivo(File arquivoXML) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(arquivoXML));
		String line;
		StringBuilder sb = new StringBuilder();
		while((line = br.readLine()) != null){
		    sb.append(line.trim() + "\r\n");
		}
		br.close();
		return sb.toString();
	}	
	
	private static String chamarServicoConsulta(String xmlConsultaComProtocoloPreenchido) throws JAXBException {
		ServicoConsultarLoteEventos_Service service = new ServicoConsultarLoteEventos_Service();
		ServicoConsultarLoteEventos consultarLote = service.getServicosEmpregadorServicoConsultarLoteEventos();
		Consulta consulta = new Consulta();
		consulta.setAny(EsocialUtil.toDocument(xmlConsultaComProtocoloPreenchido).getDocumentElement());
		
		ConsultarLoteEventosResult result = consultarLote.consultarLoteEventos(consulta);
		
		String retornoConsulta = EsocialUtil.toObject(result);
		System.out.println(retornoConsulta); //TODO futuramente deve-se remover o sysout.
		return retornoConsulta;
	}	
	
	//TODO
	private static void gravarArquivo(String xmlRetornoAposConsultar, int contador) throws IOException, JAXBException {	
		FileWriter arq = new FileWriter("C:\\Users\\Vitor Lauria\\Desktop\\Envio Diogo\\xml_retorno_consulta\\retorno_consulta_diogo" + contador + ".xml");
	    PrintWriter gravarArq = new PrintWriter(arq);
	    gravarArq.printf(xmlRetornoAposConsultar);
	    arq.close();
	}	
}
