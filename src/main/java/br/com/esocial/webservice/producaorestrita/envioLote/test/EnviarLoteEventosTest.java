package br.com.esocial.webservice.producaorestrita.envioLote.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//import com.thoughtworks.xstream.XStream;

import br.com.esocial.webservice.producaorestrita.envioLote.EnviarLoteEventos.LoteEventos;
import br.com.esocial.webservice.producaorestrita.envioLote.EnviarLoteEventosResponse.EnviarLoteEventosResult;
import br.com.esocial.webservice.producaorestrita.envioLote.ServicoEnviarLoteEventos;
import br.com.esocial.webservice.producaorestrita.envioLote.ServicoEnviarLoteEventos_Service;
import br.com.esocial.webservice.util.EsocialUtil;

public class EnviarLoteEventosTest {
	public static void main(String[] args) throws Exception {
		int i=0;
		for (String arg : args) {
			System.out.println("args["+i+"] : " + arg);	
		}
		
//		Map<String, String> map = DAOGenerico.executeQueryForUniqueValue("SELECT NUM_CPF AS RESULTADO FROM TB_PESSOA_FISICA PF WHERE ROWNUM = 1 ");
//		System.out.println("Resultado: " + map.get("RESULTADO"));
		
		File file = new File("D:\\Esocial\\testeEnvio");
		
		int contador = 0;
		for(File f: file.listFiles()) {
			
			++contador;
			
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			StringBuilder sb = new StringBuilder();
			
			while((line = br.readLine()) != null){
			    sb.append(line.trim() + "\r\n");
			}
			    
			ServicoEnviarLoteEventos_Service service = new ServicoEnviarLoteEventos_Service();
			ServicoEnviarLoteEventos enviarLote = service.getWsEnviarLoteEventos();
			LoteEventos loteEventos = new LoteEventos();
			loteEventos.setAny(EsocialUtil.toDocument(sb.toString()).getDocumentElement());
			EnviarLoteEventosResult result = enviarLote.enviarLoteEventos(loteEventos);	
			
			String resultadoXML = EsocialUtil.toObject(result);
			System.out.println(resultadoXML);
			
			FileWriter arq = new FileWriter("D:\\Esocial\\testeRetornoEnvio\\retornoEnvio" + contador + ".xml");
		    PrintWriter gravarArq = new PrintWriter(arq);
		    gravarArq.printf(resultadoXML);
		    arq.close();
		}		
	}

	public static final String XML_CMC = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><eSocial xmlns=\"http://www.esocial.gov.br/schema/lote/eventos/envio/v1_1_1\">\r\n"
			+ "	<envioLoteEventos grupo=\"1\">\r\n" + "		<ideEmpregador>\r\n" + "			<tpInsc>1</tpInsc>\r\n"
			+ "			<nrInsc>06916689</nrInsc>\r\n" + "		</ideEmpregador>\r\n" + "		<ideTransmissor>\r\n"
			+ "			<tpInsc>1</tpInsc>\r\n" + "			<nrInsc>06916689000185</nrInsc>\r\n"
			+ "		</ideTransmissor>\r\n" + "		<eventos>\r\n"
			+ "			<evento Id=\"ID1069166890000002021111917274000001\">\r\n" +
			// INICIO BLOCO

			"<eSocial xmlns=\"http://www.esocial.gov.br/schema/evt/evtInfoEmpregador/v_S_01_00_00\">\r\n"
			+ "	<evtInfoEmpregador Id=\"ID1069166890000002021111917274000001\">\r\n" + "		<ideEvento>\r\n"
			+ "			<tpAmb>2</tpAmb>\r\n" + "			<procEmi>1</procEmi>\r\n"
			+ "			<verProc>1</verProc>\r\n" + "		</ideEvento>\r\n" + "		<ideEmpregador>\r\n"
			+ "			<tpInsc>1</tpInsc>\r\n" + "			<nrInsc>06916689</nrInsc>\r\n"
			+ "		</ideEmpregador>\r\n" + "		<infoEmpregador>\r\n" + "			<inclusao>\r\n"
			+ "				<idePeriodo>\r\n" + "					<iniValid>2021-07</iniValid>\r\n"
			+ "				</idePeriodo>\r\n" + "				<infoCadastro>\r\n"
			+ "					<classTrib>85</classTrib>\r\n" + "					<indCoop>0</indCoop>\r\n"
			+ "					<indConstr>0</indConstr>\r\n" + "					<indDesFolha>0</indDesFolha>\r\n"
			+ "					<indOptRegEletron>0</indOptRegEletron>\r\n"
			+ "					<cnpjEFR>51885242000140</cnpjEFR>\r\n" + "				</infoCadastro>\r\n"
			+ "			</inclusao>\r\n" + "		</infoEmpregador>\r\n" + "	</evtInfoEmpregador>\r\n"
			+ "<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/><Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><DigestValue>7+Hh8QCoJfHRPvBDgCaAW6//3KHeGT/XUnIlSNiP8Nc=</DigestValue></Reference></SignedInfo><SignatureValue>lozb/b5k06ehXnl3gkrr2EJ/XkLfcm7u5HEVhIaEWGHlAnJ3S5EIpfYaVrBx2HQU+IZIBcEcfUhJ\r\n"
			+ "nlIPEonBufMztwoPCkQIdFBmuwtrs6m5DlFT37FLdCcIey7I77i/U0FHn7MGoJx39MsO8GeNVpqn\r\n"
			+ "CcmVmquiZc4jaF01Sj1Vqb6Jta6vyWoKfiuCfqQ+217mewqMI3MWToXtMTiUakeS6tKq6mruaDmo\r\n"
			+ "xXuyrrzFx5PW6QF2jeEznuMrVbfIW/i2v8UBtnmAJNpL8TfUogSf0zF4bf9Ys3ZLdFQzctsa/bjb\r\n"
			+ "dLmHu3gDXfF6fH+Hcwivxg9/mFTqMYqYxdSLfQ==</SignatureValue><KeyInfo><X509Data><X509Certificate>MIIILjCCBhagAwIBAgIINEv0B6E36o4wDQYJKoZIhvcNAQELBQAwdTELMAkGA1UEBhMCQlIxEzAR\r\n"
			+ "BgNVBAoMCklDUC1CcmFzaWwxNjA0BgNVBAsMLVNlY3JldGFyaWEgZGEgUmVjZWl0YSBGZWRlcmFs\r\n"
			+ "IGRvIEJyYXNpbCAtIFJGQjEZMBcGA1UEAwwQQUMgU0VSQVNBIFJGQiB2NTAeFw0yMTA1MDcxMzE3\r\n"
			+ "MDBaFw0yMjA1MDcxMzE3MDBaMIIBQDELMAkGA1UEBhMCQlIxCzAJBgNVBAgMAlNQMREwDwYDVQQH\r\n"
			+ "DAhDQU1QSU5BUzETMBEGA1UECgwKSUNQLUJyYXNpbDEYMBYGA1UECwwPMDAwMDAxMDEwMjMzNDE3\r\n"
			+ "MTYwNAYDVQQLDC1TZWNyZXRhcmlhIGRhIFJlY2VpdGEgRmVkZXJhbCBkbyBCcmFzaWwgLSBSRkIx\r\n"
			+ "FjAUBgNVBAsMDVJGQiBlLUNOUEogQTExGTAXBgNVBAsMEEFDIFNFUkFTQSBSRkIgdjUxFzAVBgNV\r\n"
			+ "BAsMDjA5MzEzMTM1MDAwMTgxMRMwEQYDVQQLDApQUkVTRU5DSUFMMUkwRwYDVQQDDEBJTlNUSVRV\r\n"
			+ "VE8gREUgUFJFVklERU5DSUEgU09DSUFMIERPIE1VTklDSVBJTyBERSBDOjA2OTE2Njg5MDAwMTg1\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmwW1WVaNxnhn026WiDJCZHf5JkCsFl7r\r\n"
			+ "/QdD1dMljun7oz4mRZvHgoOIzI3Xm7YdPg1L8Ejwa+sARGLtymXsE0SE2BDjFAmcPKTuYsCuQFP2\r\n"
			+ "sgb6fxzjLfKtWBr/imufta5eFPbER17F7pDFK7IEx/0pivBCT6we3sJcxjPpaG9d60mGmox0FAzx\r\n"
			+ "z1fdZAJiOhjyiskE6qh55aUryee/6cpPDN8/jZczTV+vqf7q8cn0Bbl0+UdFILGtWyw3zz54eiqv\r\n"
			+ "SBlm4Vot6p9+5YX+yqk/zsqPulH6Mh/27+P3p7K4CWs5yRSL9tGF3AnDBSulSiM3MI7vV7lcVT4s\r\n"
			+ "UYLWSwIDAQABo4IC8zCCAu8wCQYDVR0TBAIwADAfBgNVHSMEGDAWgBTs8UFRV6jmOules6Ai+QiK\r\n"
			+ "tTqHjzCBmQYIKwYBBQUHAQEEgYwwgYkwSAYIKwYBBQUHMAKGPGh0dHA6Ly93d3cuY2VydGlmaWNh\r\n"
			+ "ZG9kaWdpdGFsLmNvbS5ici9jYWRlaWFzL3NlcmFzYXJmYnY1LnA3YjA9BggrBgEFBQcwAYYxaHR0\r\n"
			+ "cDovL29jc3AuY2VydGlmaWNhZG9kaWdpdGFsLmNvbS5ici9zZXJhc2FyZmJ2NTCBwwYDVR0RBIG7\r\n"
			+ "MIG4gSBMVUlaQS5CQVJCT1NBQENBTVBJTkFTLlNQLkdPVi5CUqAmBgVgTAEDAqAdExtNQVJJT05B\r\n"
			+ "TERPIEZFUk5BTkRFUyBNQUNJRUygGQYFYEwBAwOgEBMOMDY5MTY2ODkwMDAxODWgOAYFYEwBAwSg\r\n"
			+ "LxMtMTcwMTE5NjU1MjM2NDI0MDYyMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwoBcGBWBMAQMH\r\n"
			+ "oA4TDDAwMDAwMDAwMDAwMDBxBgNVHSAEajBoMGYGBmBMAQIBDTBcMFoGCCsGAQUFBwIBFk5odHRw\r\n"
			+ "Oi8vcHVibGljYWNhby5jZXJ0aWZpY2Fkb2RpZ2l0YWwuY29tLmJyL3JlcG9zaXRvcmlvL2RwYy9k\r\n"
			+ "ZWNsYXJhY2FvLXJmYi5wZGYwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMEMIGdBgNVHR8E\r\n"
			+ "gZUwgZIwSqBIoEaGRGh0dHA6Ly93d3cuY2VydGlmaWNhZG9kaWdpdGFsLmNvbS5ici9yZXBvc2l0\r\n"
			+ "b3Jpby9sY3Ivc2VyYXNhcmZidjUuY3JsMESgQqBAhj5odHRwOi8vbGNyLmNlcnRpZmljYWRvcy5j\r\n"
			+ "b20uYnIvcmVwb3NpdG9yaW8vbGNyL3NlcmFzYXJmYnY1LmNybDAdBgNVHQ4EFgQUqWVNoH8BGBRI\r\n"
			+ "VBbZ4Ewc+5SDg+4wDgYDVR0PAQH/BAQDAgXgMA0GCSqGSIb3DQEBCwUAA4ICAQB0n4RV9AMqlHkQ\r\n"
			+ "2/wmgVEcXgyRr48BfTjUCd063bLVmvijqrUkv1udRFFVrWnuO6RBf81Ne/CaVIWP7/qc2gBl8w08\r\n"
			+ "dmeb9nO4aoCowkbYFenRgZ4x65kK9i9U5VQOBUd9JnC9ve7MPVPx+HkHCNt0NOFnAMOrxip7iEIt\r\n"
			+ "b1qi/vWpqMZNQI/wvqakyU8FDGeQlMDKC5R0q+azXcqgpj2q9HYfuyN0MBYzJOK8rLHji73YQlaJ\r\n"
			+ "K9mIr9qLdxRWkZYAuF7EeiyXPod+jrpQ+opy6Dsc/ZcJNrCTkXUCTILhNO3lDcrvEHqcmynK3wFw\r\n"
			+ "P7O0FU8xqMvQX+kgHpKTBrCtGGtbkFXv5Q2H3a/HQf7Gg8DkPgLCjXLCcvbHpAi4NN5uQtFwlOnZ\r\n"
			+ "oUIWL6N0HBL0ornEE5AAxDGKS9oEHHPpazoqam+tjnDUlkUuj6WzGMWwUV2BVF2LUudPYbQOIj3c\r\n"
			+ "O3f5adFk7u8DOpraR/51L2X993t0eVx/pjTqyqULuxCdmbJEIOZiWngIMnxYKBnMdB4QMiqV4OoS\r\n"
			+ "bHWqX89hiEHoo2P85pTP7Lw8ExQcHv6MXT/CTVtGRETtcR0LZMe5Ndz3xGaW/dIkxG/GZiWybQqS\r\n"
			+ "dhYN8+bAd5iAnTUw9B/ntCxOtYqmLYl+zYqPB652BxbD7qGjtAYxPW2AjeNw5w==</X509Certificate></X509Data></KeyInfo></Signature></eSocial>"
			+

//FIM BLOCO DO EVENTO ASSSINADO
			"</evento>" + "</eventos>\r\n" + "</envioLoteEventos>\r\n" + "</eSocial>";

	public static final String XML_123 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
			+ "<eSocial xmlns=\"http://www.esocial.gov.br/schema/lote/eventos/envio/v1_1_1\">\r\n"
			+ "<envioLoteEventos grupo=\"1\">\r\n" + "<ideEmpregador>\r\n" + "<tpInsc>1</tpInsc>\r\n"
			+ "<nrInsc>09041213</nrInsc>\r\n" + "</ideEmpregador>\r\n" + "<ideTransmissor>\r\n"
			+ "<tpInsc>1</tpInsc>\r\n" + "<nrInsc>09041213000136</nrInsc>\r\n" + "</ideTransmissor>\r\n"
			+ "<eventos>\r\n" + "<evento Id=\"ID1090412130000002021112617183800031\">" +

			// INÍCIO BLOCO DO EVENTO ASSSINADO

			"<eSocial xmlns=\"http://www.esocial.gov.br/schema/evt/evtTabRubrica/v_S_01_00_00\">\r\n"
			+ "	<evtTabRubrica Id=\"ID1090412130000002021112617215900008\">\r\n" + "		<ideEvento>\r\n"
			+ "			<tpAmb>2</tpAmb>\r\n" + "			<procEmi>1</procEmi>\r\n"
			+ "			<verProc>1</verProc>\r\n" + "		</ideEvento>\r\n" + "		<ideEmpregador>\r\n"
			+ "			<tpInsc>1</tpInsc>\r\n" + "			<nrInsc>09041213</nrInsc>\r\n"
			+ "		</ideEmpregador>\r\n" + "		<infoRubrica>\r\n" + "			<inclusao>\r\n"
			+ "				<ideRubrica>\r\n" + "					<codRubr>821953</codRubr>\r\n"
			+ "					<ideTabRubr>R0200651</ideTabRubr>\r\n"
			+ "					<iniValid>2021-11</iniValid>\r\n" + "				</ideRubrica>\r\n"
			+ "				<dadosRubrica>\r\n" + "					<dscRubr>DIFVENCART133CHEFE PFISCALAJ</dscRubr>\r\n"
			+ "					<natRubr>1</natRubr>\r\n" + "					<tpRubr>1</tpRubr>\r\n"
			+ "					<codIncCP>00</codIncCP>\r\n" + "					<codIncIRRF>11</codIncIRRF>\r\n"
			+ "					<codIncFGTS>00</codIncFGTS>\r\n" + "					<codIncCPRP>00</codIncCPRP>\r\n"
			+ "					<tetoRemun>N</tetoRemun>\r\n" + "				</dadosRubrica>\r\n"
			+ "			</inclusao>\r\n" + "		</infoRubrica>\r\n" + "	</evtTabRubrica>\r\n"
			+ "	<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\r\n" + "		<SignedInfo>\r\n"
			+ "			<CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>\r\n"
			+ "			<SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/>\r\n"
			+ "			<Reference URI=\"\">\r\n" + "				<Transforms>\r\n"
			+ "					<Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>\r\n"
			+ "					<Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>\r\n"
			+ "				</Transforms>\r\n"
			+ "				<DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\r\n"
			+ "				<DigestValue>xcRXh7ZvMIJ0YVHH1WQxNgCkmYp7gUUhy44xwOGQ9eM=</DigestValue>\r\n"
			+ "			</Reference>\r\n" + "		</SignedInfo>\r\n"
			+ "		<SignatureValue>WGEcqbBJ2AcbYMpa3MruFlZcn6C9XGnkixNmpDKfK4K5eB796E6yrLi30ED6QQeO9gv9CnJRSG2N\r\n"
			+ "C04RbVuqaSPJ7lyel9LCFti7wh8SxcswwGsQ46jssf/IKrqc4DZpUqiqWm4hZvo3fI8vtsCzKCq6\r\n"
			+ "18Fv4RzP90tx7BJwrodituS2hevNwaDixMmanXDZ3wWQG2d1y7DJ4D3/r7IkzcDSrhBJoHfkTq8Y\r\n"
			+ "NbTEdsPf8ePAbugJHLVlfBTogHE0bg4/5b/Apq2BbhUOHglLdXnWjiE5fqTd0lzAxX7d//pJ757e\r\n"
			+ "QcAXTb+O98Lw4s6Zn+AzCwmxpm+aA+QxgezanQ==</SignatureValue>\r\n" + "		<KeyInfo>\r\n"
			+ "			<X509Data>\r\n"
			+ "				<X509Certificate>MIIH4TCCBcmgAwIBAgIJASDOsUP0JYQRMA0GCSqGSIb3DQEBCwUAMIGCMQswCQYDVQQGEwJCUjET\r\n"
			+ "MBEGA1UEChMKSUNQLUJyYXNpbDE2MDQGA1UECxMtU2VjcmV0YXJpYSBkYSBSZWNlaXRhIEZlZGVy\r\n"
			+ "YWwgZG8gQnJhc2lsIC0gUkZCMSYwJAYDVQQDEx1BQyBJbXByZW5zYSBPZmljaWFsIFNQIFJGQiBH\r\n"
			+ "NTAeFw0yMTAyMTAxNDA2MjRaFw0yMjAyMTAxNDA2MjRaMIH3MQswCQYDVQQGEwJCUjETMBEGA1UE\r\n"
			+ "ChMKSUNQLUJyYXNpbDELMAkGA1UECBMCU1AxEjAQBgNVBAcTCVNhbyBQYXVsbzE2MDQGA1UECxMt\r\n"
			+ "U2VjcmV0YXJpYSBkYSBSZWNlaXRhIEZlZGVyYWwgZG8gQnJhc2lsIC0gUkZCMRYwFAYDVQQLEw1S\r\n"
			+ "RkIgZS1DTlBKIEExMRMwEQYDVQQLEwpwcmVzZW5jaWFsMRcwFQYDVQQLEw4xMTczNTIzNjAwMDE5\r\n"
			+ "MjE0MDIGA1UEAxMrU0FPIFBBVUxPIFBSRVZJREVOQ0lBIFNQUFJFVjowOTA0MTIxMzAwMDEzNjCC\r\n"
			+ "ASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANaPFMui6FAwuUrQ/x+4pKG+TcV+mgv0O8Dk\r\n"
			+ "Wves5ITJj7LmkyibDZUHOlR27d0+O2Ra0Mw63OyqUbgwRu6bGYQAhhLVVz315NdGqq3/lpwZCuLD\r\n"
			+ "zzHWsDUwF/k50DaD8ROU53m8iL7BEznQqaWWxMJZUHUhIjTXEN+YLS7hWKRpH3Dxbf06v4fLcHBO\r\n"
			+ "EeC/r4AHu6Mqz2+EZMDVXupGk7+9cTPEW/byOT87VI1p3PErwzKvdGgHzClfm7WUebKtHJ9+pnbd\r\n"
			+ "RJMGgYryxmc/MhiRmUwT0ScfF/BIAQ+ltOnKpiNBI1ZZK1dOtfqWVLk2jCp46L0RU7vJ9Qorm+0D\r\n"
			+ "9DUCAwEAAaOCAuEwggLdMA4GA1UdDwEB/wQEAwIF4DCBpwYIKwYBBQUHAQEEgZowgZcwNwYIKwYB\r\n"
			+ "BQUHMAGGK2h0dHA6Ly9pby1vY3NwLWljcGJyLmltcHJlbnNhb2ZpY2lhbC5jb20uYnIwXAYIKwYB\r\n"
			+ "BQUHMAKGUGh0dHA6Ly9pby1jb20taWNwYnIuaW1wcmVuc2FvZmljaWFsLmNvbS5ici9yZXBvc2l0\r\n"
			+ "b3Jpby9JTUVTUFJGQi9BQ0lNRVNQUkZCRzUucDdiMB8GA1UdIwQYMBaAFEr/viv/WJqH/i3Nc/Qm\r\n"
			+ "RjLtsq5iMGIGA1UdIARbMFkwVwYGYEwBAgEUME0wSwYIKwYBBQUHAgEWP2h0dHA6Ly9pby1jb20t\r\n"
			+ "aWNwYnIuaW1wcmVuc2FvZmljaWFsLmNvbS5ici9yZXBvc2l0b3Jpby9JTUVTUFJGQjAJBgNVHRME\r\n"
			+ "AjAAMIGyBgNVHR8EgaowgacwVqBUoFKGUGh0dHA6Ly9pby1jb20taWNwYnIuaW1wcmVuc2FvZmlj\r\n"
			+ "aWFsLmNvbS5ici9yZXBvc2l0b3Jpby9JTUVTUFJGQi9BQ0lNRVNQUkZCRzUuY3JsME2gS6BJhkdo\r\n"
			+ "dHRwOi8vbGNyLmltcHJlbnNhb2ZpY2lhbC5jb20uYnIvcmVwb3NpdG9yaW8vSU1FU1BSRkIvQUNJ\r\n"
			+ "TUVTUFJGQkc1LmNybDCBsAYDVR0RBIGoMIGlgRJqcm1vcmFlc0BzcC5nb3YuYnKgOAYFYEwBAwSg\r\n"
			+ "LwQtMjkwMzE5NTE1MTkwNzQ4ODgwNDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwoCEGBWBMAQMC\r\n"
			+ "oBgEFkpPU0UgUk9CRVJUTyBERSBNT1JBRVOgGQYFYEwBAwOgEAQOMDkwNDEyMTMwMDAxMzagFwYF\r\n"
			+ "YEwBAwegDgQMMDAwMDAwMDAwMDAwMCkGA1UdJQQiMCAGCCsGAQUFBwMCBggrBgEFBQcDBAYKKwYB\r\n"
			+ "BAGCNxQCAjANBgkqhkiG9w0BAQsFAAOCAgEAI4hr9CBJPty3D/r4I1fv5IpUoGrZgIgHjjPwl+3A\r\n"
			+ "lgIA3ABosplF7SXpwmp2jo0/9muzOjo6YJLPVGdCSGv/1ypgaqoJ4pxXIHGTHXdFfzxen2K09WT3\r\n"
			+ "lypnL8r22ZFJoiULtlwn1jAQyinuSk8ah/C0yjg1yWsDMo3bv3JOLQX96f9e/+eRRAVsDey6zqBV\r\n"
			+ "c++s2j3sGMNoKeYmYJ+GLExGPPshyQlGBquaf1ow0kVegtyntS5tbXbDtA3lNTiU4R8RqMZEjw+j\r\n"
			+ "pO44AHMp4zBdFDxNOd/LvLHr2SHputFrZTYoZCsAQCCuH5VRb0z9bp5Kcds/SD8IXdrr+Xts2OUe\r\n"
			+ "1ohOgwuz3WSkwxOWt9VmeNFNXmCSwRuW2yHlRAYuRb/VNO0achjGu9lwiCnm/aJTygMqgqknoJhB\r\n"
			+ "zBDjlEcK6yi+AvzKKwZxtQ8LMIxjFhyFClT0HocgEkuWg6/Efadrg4Ya1laNaDqkENljCJepw6Ie\r\n"
			+ "Eda1A790hF8eAGQYfr8xfmEuGYu4Y7n+mttdKLL3RTwx7Qyl049eX9xLUlstPuK9PMiMz+Rj6NP3\r\n"
			+ "kOv5kgjLqYHNu58tddvZMZ4cW26lhWilD/KVSolQXr/2mleoWrZAwt8jF8MIp0/VPXZsUeEQ2WAs\r\n"
			+ "OEtBzeryWsVaJ/OfRYfp/oF8b1vZ4sKvTms=</X509Certificate>\r\n" + "			</X509Data>\r\n"
			+ "		</KeyInfo>\r\n" + "	</Signature>\r\n" + "</eSocial>" +

			// FIM BLOCO DO EVENTO ASSSINADO
			"</evento>" + "</eventos>\r\n" + "</envioLoteEventos>\r\n" + "</eSocial>";

	public static final String XML_DIOGO = "<eSocial xmlns=\"http://www.esocial.gov.br/schema/evt/evtTabRubrica/v_S_01_00_00\">\r\n"
			+ "	<evtTabRubrica Id=\"ID1090412130000002021112617215900008\">\r\n" + "		<ideEvento>\r\n"
			+ "			<tpAmb>2</tpAmb>\r\n" + "			<procEmi>1</procEmi>\r\n"
			+ "			<verProc>1</verProc>\r\n" + "		</ideEvento>\r\n" + "		<ideEmpregador>\r\n"
			+ "			<tpInsc>1</tpInsc>\r\n" + "			<nrInsc>09041213</nrInsc>\r\n"
			+ "		</ideEmpregador>\r\n" + "		<infoRubrica>\r\n" + "			<inclusao>\r\n"
			+ "				<ideRubrica>\r\n" + "					<codRubr>821953</codRubr>\r\n"
			+ "					<ideTabRubr>R0200651</ideTabRubr>\r\n"
			+ "					<iniValid>2021-11</iniValid>\r\n" + "				</ideRubrica>\r\n"
			+ "				<dadosRubrica>\r\n" + "					<dscRubr>DIFVENCART133CHEFE PFISCALAJ</dscRubr>\r\n"
			+ "					<natRubr>1</natRubr>\r\n" + "					<tpRubr>1</tpRubr>\r\n"
			+ "					<codIncCP>00</codIncCP>\r\n" + "					<codIncIRRF>11</codIncIRRF>\r\n"
			+ "					<codIncFGTS>00</codIncFGTS>\r\n" + "					<codIncCPRP>00</codIncCPRP>\r\n"
			+ "					<tetoRemun>N</tetoRemun>\r\n" + "				</dadosRubrica>\r\n"
			+ "			</inclusao>\r\n" + "		</infoRubrica>\r\n" + "	</evtTabRubrica>\r\n"
			+ "	<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">\r\n" + "		<SignedInfo>\r\n"
			+ "			<CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>\r\n"
			+ "			<SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/>\r\n"
			+ "			<Reference URI=\"\">\r\n" + "				<Transforms>\r\n"
			+ "					<Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>\r\n"
			+ "					<Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>\r\n"
			+ "				</Transforms>\r\n"
			+ "				<DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\r\n"
			+ "				<DigestValue>xcRXh7ZvMIJ0YVHH1WQxNgCkmYp7gUUhy44xwOGQ9eM=</DigestValue>\r\n"
			+ "			</Reference>\r\n" + "		</SignedInfo>\r\n"
			+ "		<SignatureValue>WGEcqbBJ2AcbYMpa3MruFlZcn6C9XGnkixNmpDKfK4K5eB796E6yrLi30ED6QQeO9gv9CnJRSG2N\r\n"
			+ "C04RbVuqaSPJ7lyel9LCFti7wh8SxcswwGsQ46jssf/IKrqc4DZpUqiqWm4hZvo3fI8vtsCzKCq6\r\n"
			+ "18Fv4RzP90tx7BJwrodituS2hevNwaDixMmanXDZ3wWQG2d1y7DJ4D3/r7IkzcDSrhBJoHfkTq8Y\r\n"
			+ "NbTEdsPf8ePAbugJHLVlfBTogHE0bg4/5b/Apq2BbhUOHglLdXnWjiE5fqTd0lzAxX7d//pJ757e\r\n"
			+ "QcAXTb+O98Lw4s6Zn+AzCwmxpm+aA+QxgezanQ==</SignatureValue>\r\n" + "		<KeyInfo>\r\n"
			+ "			<X509Data>\r\n"
			+ "				<X509Certificate>MIIH4TCCBcmgAwIBAgIJASDOsUP0JYQRMA0GCSqGSIb3DQEBCwUAMIGCMQswCQYDVQQGEwJCUjET\r\n"
			+ "MBEGA1UEChMKSUNQLUJyYXNpbDE2MDQGA1UECxMtU2VjcmV0YXJpYSBkYSBSZWNlaXRhIEZlZGVy\r\n"
			+ "YWwgZG8gQnJhc2lsIC0gUkZCMSYwJAYDVQQDEx1BQyBJbXByZW5zYSBPZmljaWFsIFNQIFJGQiBH\r\n"
			+ "NTAeFw0yMTAyMTAxNDA2MjRaFw0yMjAyMTAxNDA2MjRaMIH3MQswCQYDVQQGEwJCUjETMBEGA1UE\r\n"
			+ "ChMKSUNQLUJyYXNpbDELMAkGA1UECBMCU1AxEjAQBgNVBAcTCVNhbyBQYXVsbzE2MDQGA1UECxMt\r\n"
			+ "U2VjcmV0YXJpYSBkYSBSZWNlaXRhIEZlZGVyYWwgZG8gQnJhc2lsIC0gUkZCMRYwFAYDVQQLEw1S\r\n"
			+ "RkIgZS1DTlBKIEExMRMwEQYDVQQLEwpwcmVzZW5jaWFsMRcwFQYDVQQLEw4xMTczNTIzNjAwMDE5\r\n"
			+ "MjE0MDIGA1UEAxMrU0FPIFBBVUxPIFBSRVZJREVOQ0lBIFNQUFJFVjowOTA0MTIxMzAwMDEzNjCC\r\n"
			+ "ASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANaPFMui6FAwuUrQ/x+4pKG+TcV+mgv0O8Dk\r\n"
			+ "Wves5ITJj7LmkyibDZUHOlR27d0+O2Ra0Mw63OyqUbgwRu6bGYQAhhLVVz315NdGqq3/lpwZCuLD\r\n"
			+ "zzHWsDUwF/k50DaD8ROU53m8iL7BEznQqaWWxMJZUHUhIjTXEN+YLS7hWKRpH3Dxbf06v4fLcHBO\r\n"
			+ "EeC/r4AHu6Mqz2+EZMDVXupGk7+9cTPEW/byOT87VI1p3PErwzKvdGgHzClfm7WUebKtHJ9+pnbd\r\n"
			+ "RJMGgYryxmc/MhiRmUwT0ScfF/BIAQ+ltOnKpiNBI1ZZK1dOtfqWVLk2jCp46L0RU7vJ9Qorm+0D\r\n"
			+ "9DUCAwEAAaOCAuEwggLdMA4GA1UdDwEB/wQEAwIF4DCBpwYIKwYBBQUHAQEEgZowgZcwNwYIKwYB\r\n"
			+ "BQUHMAGGK2h0dHA6Ly9pby1vY3NwLWljcGJyLmltcHJlbnNhb2ZpY2lhbC5jb20uYnIwXAYIKwYB\r\n"
			+ "BQUHMAKGUGh0dHA6Ly9pby1jb20taWNwYnIuaW1wcmVuc2FvZmljaWFsLmNvbS5ici9yZXBvc2l0\r\n"
			+ "b3Jpby9JTUVTUFJGQi9BQ0lNRVNQUkZCRzUucDdiMB8GA1UdIwQYMBaAFEr/viv/WJqH/i3Nc/Qm\r\n"
			+ "RjLtsq5iMGIGA1UdIARbMFkwVwYGYEwBAgEUME0wSwYIKwYBBQUHAgEWP2h0dHA6Ly9pby1jb20t\r\n"
			+ "aWNwYnIuaW1wcmVuc2FvZmljaWFsLmNvbS5ici9yZXBvc2l0b3Jpby9JTUVTUFJGQjAJBgNVHRME\r\n"
			+ "AjAAMIGyBgNVHR8EgaowgacwVqBUoFKGUGh0dHA6Ly9pby1jb20taWNwYnIuaW1wcmVuc2FvZmlj\r\n"
			+ "aWFsLmNvbS5ici9yZXBvc2l0b3Jpby9JTUVTUFJGQi9BQ0lNRVNQUkZCRzUuY3JsME2gS6BJhkdo\r\n"
			+ "dHRwOi8vbGNyLmltcHJlbnNhb2ZpY2lhbC5jb20uYnIvcmVwb3NpdG9yaW8vSU1FU1BSRkIvQUNJ\r\n"
			+ "TUVTUFJGQkc1LmNybDCBsAYDVR0RBIGoMIGlgRJqcm1vcmFlc0BzcC5nb3YuYnKgOAYFYEwBAwSg\r\n"
			+ "LwQtMjkwMzE5NTE1MTkwNzQ4ODgwNDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwoCEGBWBMAQMC\r\n"
			+ "oBgEFkpPU0UgUk9CRVJUTyBERSBNT1JBRVOgGQYFYEwBAwOgEAQOMDkwNDEyMTMwMDAxMzagFwYF\r\n"
			+ "YEwBAwegDgQMMDAwMDAwMDAwMDAwMCkGA1UdJQQiMCAGCCsGAQUFBwMCBggrBgEFBQcDBAYKKwYB\r\n"
			+ "BAGCNxQCAjANBgkqhkiG9w0BAQsFAAOCAgEAI4hr9CBJPty3D/r4I1fv5IpUoGrZgIgHjjPwl+3A\r\n"
			+ "lgIA3ABosplF7SXpwmp2jo0/9muzOjo6YJLPVGdCSGv/1ypgaqoJ4pxXIHGTHXdFfzxen2K09WT3\r\n"
			+ "lypnL8r22ZFJoiULtlwn1jAQyinuSk8ah/C0yjg1yWsDMo3bv3JOLQX96f9e/+eRRAVsDey6zqBV\r\n"
			+ "c++s2j3sGMNoKeYmYJ+GLExGPPshyQlGBquaf1ow0kVegtyntS5tbXbDtA3lNTiU4R8RqMZEjw+j\r\n"
			+ "pO44AHMp4zBdFDxNOd/LvLHr2SHputFrZTYoZCsAQCCuH5VRb0z9bp5Kcds/SD8IXdrr+Xts2OUe\r\n"
			+ "1ohOgwuz3WSkwxOWt9VmeNFNXmCSwRuW2yHlRAYuRb/VNO0achjGu9lwiCnm/aJTygMqgqknoJhB\r\n"
			+ "zBDjlEcK6yi+AvzKKwZxtQ8LMIxjFhyFClT0HocgEkuWg6/Efadrg4Ya1laNaDqkENljCJepw6Ie\r\n"
			+ "Eda1A790hF8eAGQYfr8xfmEuGYu4Y7n+mttdKLL3RTwx7Qyl049eX9xLUlstPuK9PMiMz+Rj6NP3\r\n"
			+ "kOv5kgjLqYHNu58tddvZMZ4cW26lhWilD/KVSolQXr/2mleoWrZAwt8jF8MIp0/VPXZsUeEQ2WAs\r\n"
			+ "OEtBzeryWsVaJ/OfRYfp/oF8b1vZ4sKvTms=</X509Certificate>\r\n" + "			</X509Data>\r\n"
			+ "		</KeyInfo>\r\n" + "	</Signature>\r\n" + "</eSocial>";
}
