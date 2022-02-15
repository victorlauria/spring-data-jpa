package br.com.atlantic.esocial.enviarLoteProducao.test;

import br.com.atlantic.esocial.enviarLoteProducao.ServicoEnviarLoteEventos;
import br.com.atlantic.esocial.enviarLoteProducao.ServicoEnviarLoteEventos_Service;
import br.com.atlantic.esocial.enviarLoteProducao.EnviarLoteEventos.LoteEventos;
import br.com.atlantic.esocial.enviarLoteProducao.EnviarLoteEventosResponse.EnviarLoteEventosResult;
import br.com.esocial.webservice.util.EsocialUtil;

public class EnviarLoteEventosTest {
	public static void main(String[] args) throws Exception {
		int i=0;
		for (String arg : args) {
			System.out.println("args["+i+"] : " + arg);	
		}
		
//		Map<String, String> map = DAOGenerico.executeQueryForUniqueValue("SELECT NUM_CPF AS RESULTADO FROM TB_PESSOA_FISICA PF WHERE ROWNUM = 1 ");
//		System.out.println("Resultado: " + map.get("RESULTADO"));

		ServicoEnviarLoteEventos_Service service = new ServicoEnviarLoteEventos_Service();
		ServicoEnviarLoteEventos enviarLote = service.getWsEnviarLoteEventos();
		LoteEventos loteEventos = new LoteEventos();
		loteEventos.setAny(EsocialUtil.toDocument(XML_CMC).getDocumentElement());
		EnviarLoteEventosResult result = enviarLote.enviarLoteEventos(loteEventos);
		
		System.out.println(EsocialUtil.toObject(result));
	}	
	
	
	public static final String XML_CMC = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><eSocial xmlns=\"http://www.esocial.gov.br/schema/lote/eventos/envio/v1_1_1\">\r\n" + 
			"	<envioLoteEventos grupo=\"1\">\r\n" + 
			"		<ideEmpregador>\r\n" + 
			"			<tpInsc>1</tpInsc>\r\n" + 
			"			<nrInsc>08717299</nrInsc>\r\n" + 
			"		</ideEmpregador>\r\n" + 
			"		<ideTransmissor>\r\n" + 
			"			<tpInsc>1</tpInsc>\r\n" + 
			"			<nrInsc>08717299000101</nrInsc>\r\n" + 
			"		</ideTransmissor>\r\n" + 
			"		<eventos>\r\n" + 
			"			<evento Id=\"ID1087172990000002021111918334000001\">\r\n"+
			//INICIO BLOCO
			
"<eSocial xmlns=\"http://www.esocial.gov.br/schema/evt/evtTabLotacao/v_S_01_00_00\">\r\n" + 
"	<evtTabLotacao Id=\"ID1087172990000002021111918334000001\">\r\n" + 
"		<ideEvento>\r\n" + 
"			<tpAmb>1</tpAmb>\r\n" + 
"			<procEmi>1</procEmi>\r\n" + 
"			<verProc>1</verProc>\r\n" + 
"		</ideEvento>\r\n" + 
"		<ideEmpregador>\r\n" + 
"			<tpInsc>1</tpInsc>\r\n" + 
"			<nrInsc>08717299</nrInsc>\r\n" + 
"		</ideEmpregador>\r\n" + 
"		<infoLotacao>\r\n" + 
"			<inclusao>\r\n" + 
"				<ideLotacao>\r\n" + 
"					<codLotacao>IPREVGERAL</codLotacao>\r\n" + 
"					<iniValid>2021-07</iniValid>\r\n" + 
"				</ideLotacao>\r\n" + 
"				<dadosLotacao>\r\n" + 
"					<tpLotacao>01</tpLotacao>\r\n" + 
"					<fpasLotacao>\r\n" + 
"						<fpas>582</fpas>\r\n" + 
"						<codTercs>0000</codTercs>\r\n" + 
"					</fpasLotacao>\r\n" + 
"				</dadosLotacao>\r\n" + 
"			</inclusao>\r\n" + 
"		</infoLotacao>\r\n" + 
"	</evtTabLotacao>\r\n" + 
"<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/><Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><DigestValue>tkN/x9iGLx/q/NLKvetJw3PvF4NedWgsvHf81jv+O9U=</DigestValue></Reference></SignedInfo><SignatureValue>MW+DCl8TyPc/ZVJlKbPENFrfyN4jFA97Jk9RrwbdFX5iAgCqvasU9kYGbCElCIcqocnlzKa/FNBv\r\n" + 
"sOML6LVMO6Z84fzpELXjkD00ZvzfpKlSWntjKeFIXi6ibv5HmGncoYzT5catngK7LDhHD5YSRlKr\r\n" + 
"Klm8PXTexEWyz02R0ZwhrNocDfNMpLCLQygXFaPX/s8OxuTcev+O5hudgBgCVClEis/RPn3XHO+n\r\n" + 
"87ri6xpXrze2J8FjAYb/ukPUCxZf7aIOvW8oodunetOGfRCykcYDbnhrnpWHE30UqowqQHc7r/z6\r\n" + 
"HBJOfboId8tiWZm8YddzpDxW4+avgKj52Q3m9w==</SignatureValue><KeyInfo><X509Data><X509Certificate>MIIIMzCCBhugAwIBAgIIA7CnuGO/9RwwDQYJKoZIhvcNAQELBQAwdTELMAkGA1UEBhMCQlIxEzAR\r\n" + 
"BgNVBAoMCklDUC1CcmFzaWwxNjA0BgNVBAsMLVNlY3JldGFyaWEgZGEgUmVjZWl0YSBGZWRlcmFs\r\n" + 
"IGRvIEJyYXNpbCAtIFJGQjEZMBcGA1UEAwwQQUMgU0VSQVNBIFJGQiB2NTAeFw0yMTEwMjYxODA4\r\n" + 
"MDBaFw0yMjEwMjYxODA4MDBaMIIBOzELMAkGA1UEBhMCQlIxCzAJBgNVBAgMAlNQMQ8wDQYDVQQH\r\n" + 
"DAZTYW50b3MxEzARBgNVBAoMCklDUC1CcmFzaWwxGDAWBgNVBAsMDzAwMDAwMTAxMDQ4MTAyOTE2\r\n" + 
"MDQGA1UECwwtU2VjcmV0YXJpYSBkYSBSZWNlaXRhIEZlZGVyYWwgZG8gQnJhc2lsIC0gUkZCMRYw\r\n" + 
"FAYDVQQLDA1SRkIgZS1DTlBKIEExMRYwFAYDVQQLDA1BQyBTRVJBU0EgUkZCMRcwFQYDVQQLDA4w\r\n" + 
"MzgzNDA2NDAwMDE4NjETMBEGA1UECwwKUFJFU0VOQ0lBTDFJMEcGA1UEAwxASU5TVElUVVRPIERF\r\n" + 
"IFBSRVZJREVOQ0lBIFNPQ0lBTCBET1MgU0VSVklET1JFUyBQVTowODcxNzI5OTAwMDEwMTCCASIw\r\n" + 
"DQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALyxCNI2GcB01eEpihwwJxvh9Cg+1d5sbcT8b5jx\r\n" + 
"WCFEbBWdwvStxXPixlnUk7oyR9sWfb1YECZ0w6FpkUnsyys+CmUuKdFILwJijpoPhd2ZutFccmD4\r\n" + 
"+Cd10Uvwiv9bra3zIDLZHpAD07EIbp2Ji6EIk9OQ8PR9N+K0aXgHBq4ZdONGZgKJiuZqzYAkRyGH\r\n" + 
"ruv+W5eNWGyPgp5S26Wzq2liKhyUkev+uqTe4m6q8DaUhYXGhmaG1szCucCgtFC+jWdWpP4HwIcW\r\n" + 
"FNT5xbKxuP9Y39af6jZhJfYijTgZGGWhelw6Qfp2rs9vGVlW3xnz4y4RAQz0n/EJWkRvCED2MDkC\r\n" + 
"AwEAAaOCAv0wggL5MAkGA1UdEwQCMAAwHwYDVR0jBBgwFoAU7PFBUVeo5jrpXrOgIvkIirU6h48w\r\n" + 
"gZkGCCsGAQUFBwEBBIGMMIGJMEgGCCsGAQUFBzAChjxodHRwOi8vd3d3LmNlcnRpZmljYWRvZGln\r\n" + 
"aXRhbC5jb20uYnIvY2FkZWlhcy9zZXJhc2FyZmJ2NS5wN2IwPQYIKwYBBQUHMAGGMWh0dHA6Ly9v\r\n" + 
"Y3NwLmNlcnRpZmljYWRvZGlnaXRhbC5jb20uYnIvc2VyYXNhcmZidjUwgc0GA1UdEQSBxTCBwoEf\r\n" + 
"Q09TTUVGRVJOQU5ERVNAU0FOVE9TLlNQLkdPVi5CUqArBgVgTAEDAqAiEyBSVUkgU0VSR0lPIEdP\r\n" + 
"TUVTIERFIFJPU0lTIEpVTklPUqAZBgVgTAEDA6AQEw4wODcxNzI5OTAwMDEwMaA+BgVgTAEDBKA1\r\n" + 
"EzMwODEwMTk4NDMyNzM4MDM3ODk1MDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDCgFwYF\r\n" + 
"YEwBAwegDhMMMDAwMDAwMDAwMDAwMHEGA1UdIARqMGgwZgYGYEwBAgENMFwwWgYIKwYBBQUHAgEW\r\n" + 
"Tmh0dHA6Ly9wdWJsaWNhY2FvLmNlcnRpZmljYWRvZGlnaXRhbC5jb20uYnIvcmVwb3NpdG9yaW8v\r\n" + 
"ZHBjL2RlY2xhcmFjYW8tcmZiLnBkZjAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwgZ0G\r\n" + 
"A1UdHwSBlTCBkjBKoEigRoZEaHR0cDovL3d3dy5jZXJ0aWZpY2Fkb2RpZ2l0YWwuY29tLmJyL3Jl\r\n" + 
"cG9zaXRvcmlvL2xjci9zZXJhc2FyZmJ2NS5jcmwwRKBCoECGPmh0dHA6Ly9sY3IuY2VydGlmaWNh\r\n" + 
"ZG9zLmNvbS5ici9yZXBvc2l0b3Jpby9sY3Ivc2VyYXNhcmZidjUuY3JsMB0GA1UdDgQWBBQ0KTja\r\n" + 
"ijDVhFTAe92MrJ+gALh/JTAOBgNVHQ8BAf8EBAMCBeAwDQYJKoZIhvcNAQELBQADggIBAABrCzcA\r\n" + 
"fdl6dcobKVUgKe1xpQqy+bUzbEZX/VHr6bWkPKRDU46WOZAojCFpbf7whfDTOQbica2H6rnKKgyk\r\n" + 
"rkdpJIbEmFr3H7obNsylJE5UYmIRbmQlhMSgTkYn7t8yVA5V+v+QMboWrYlmrwRyMShlkExqpbvm\r\n" + 
"b/7LiXy4gioClnQn7FQ0WG/djEFPQMWf733daPL/RrEat5fZWxZrQVhjes2NC8nAJW+Sg908x/hH\r\n" + 
"FYRZ+hxpyqD8EEK9B1NlLUMNb5ye2Io+/cZ2eT71dlhs7+Hs6xreGOYkjI6v6ykbB0k76v1bo6vY\r\n" + 
"OIA4/4wIRcle5cPcGFhTOqORaYc0G9MRTeXxuPIDu/W+JuRG2C7JaLORrtf/VlzdGSBHmsgknFhv\r\n" + 
"GuvEX3gpV8rElcNp9R7l0ctu7Qz8SWR+iZM1mmv8If/UMci2HgLy7oiDe7oijeBHN9BiZMaGMsKN\r\n" + 
"PoxXQ/+V03SdskAR9cOMeV7ogyI6hPAqmd6CWZBfnM3Kn13tHXSZi1QGyL1RAFXrTnmgUVBmPv2E\r\n" + 
"wbx7sk8xrl2+BWVYSm2oc9La/6EWdwtPcG2qqbykIZFL74tEAdqBBWpxs9nYnC73TaAYBL6LQtow\r\n" + 
"W5uyOoY+D09Rz7293f/vhYh5dcTJxoQajWRIS6E5d6s+Cfls3NGYXHRnxG4xQOkwYmrA</X509Certificate></X509Data></KeyInfo></Signature></eSocial>"+
			
//FIM BLOCO DO EVENTO ASSSINADO
"</evento>" +
"</eventos>\r\n" + 
"</envioLoteEventos>\r\n" + 
"</eSocial>";
	
	
	public static final String XML_123 =
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
			"<eSocial xmlns=\"http://www.esocial.gov.br/schema/lote/eventos/envio/v1_1_1\">\r\n" + 
			"<envioLoteEventos grupo=\"1\">\r\n" + 
			"<ideEmpregador>\r\n" + 
			"<tpInsc>1</tpInsc>\r\n" + 
			"<nrInsc>09041213</nrInsc>\r\n" + 
			"</ideEmpregador>\r\n" + 
			"<ideTransmissor>\r\n" + 
			"<tpInsc>1</tpInsc>\r\n" + 
			"<nrInsc>09041213000136</nrInsc>\r\n" + 
			"</ideTransmissor>\r\n" + 
			"<eventos>\r\n" + 
			"<evento Id=\"ID1090412130000002021102110334000001\">" +

			//INÍCIO BLOCO DO EVENTO ASSSINADO
			
			"<eSocial xmlns=\"http://www.esocial.gov.br/schema/evt/evtInfoEmpregador/v_S_01_00_00\">  \r\n" + 
			"	<evtInfoEmpregador Id=\"ID1090412130000002021102110334000001\">  \r\n" + 
			"		<ideEvento>  \r\n" + 
			"			<tpAmb>2</tpAmb>  \r\n" + 
			"			<procEmi>1</procEmi>  \r\n" + 
			"			<verProc>1</verProc>  \r\n" + 
			"		</ideEvento>  \r\n" + 
			"		<ideEmpregador>  \r\n" + 
			"			<tpInsc>1</tpInsc>  \r\n" + 
			"			<nrInsc>09041213</nrInsc>  \r\n" + 
			"		</ideEmpregador>  \r\n" + 
			"		<infoEmpregador>  \r\n" + 
			"			<exclusao>\r\n" + 
			"               <idePeriodo>\r\n" + 
			"                 <iniValid>2021-07</iniValid>\r\n" + 
			"               </idePeriodo>\r\n" + 
			"			</exclusao>\r\n" + 
			"		</infoEmpregador>  \r\n" + 
			"	</evtInfoEmpregador>  \r\n" + 
			"<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/><Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><DigestValue>s5zmoAsGQbko2d4F1cX5AkOgW5oilMWxPTeIcvK8zjo=</DigestValue></Reference></SignedInfo><SignatureValue>mIVS8Z1qSks6/sCW6jX4I+5KPHj6+2I1XVJrcTLPivPST/5SLKglGHi/tTjY0ggqn00r+tp5Vs5Q\r\n" + 
			"F+bCbaExAOxiBMQsza+2yHD4oKcir2UK4nBIGRFjmoSokaditwXPZoAZekVDbOZL6rFiYRgXX9PU\r\n" + 
			"WU+tdtPtA81+uZkSS1abluKqnL8Y2pTHqxTfWl+7BT9IEbiuJVLj2LxHkgeQGHK6cyhxf/0Dx5ml\r\n" + 
			"sA1NzmZEN1eR7tYtBpzb7WoU2mfpwRBedibMyElztHxgC0dS/JFzWOsIEZ1yv61yVaDpBcfGH/qh\r\n" + 
			"bWYtgz7XhMR6dMOTyNq2OlY7FHS1k/ZDoUux0w==</SignatureValue><KeyInfo><X509Data><X509Certificate>MIIH4TCCBcmgAwIBAgIJASDOsUP0JYQRMA0GCSqGSIb3DQEBCwUAMIGCMQswCQYDVQQGEwJCUjET\r\n" + 
			"MBEGA1UEChMKSUNQLUJyYXNpbDE2MDQGA1UECxMtU2VjcmV0YXJpYSBkYSBSZWNlaXRhIEZlZGVy\r\n" + 
			"YWwgZG8gQnJhc2lsIC0gUkZCMSYwJAYDVQQDEx1BQyBJbXByZW5zYSBPZmljaWFsIFNQIFJGQiBH\r\n" + 
			"NTAeFw0yMTAyMTAxNDA2MjRaFw0yMjAyMTAxNDA2MjRaMIH3MQswCQYDVQQGEwJCUjETMBEGA1UE\r\n" + 
			"ChMKSUNQLUJyYXNpbDELMAkGA1UECBMCU1AxEjAQBgNVBAcTCVNhbyBQYXVsbzE2MDQGA1UECxMt\r\n" + 
			"U2VjcmV0YXJpYSBkYSBSZWNlaXRhIEZlZGVyYWwgZG8gQnJhc2lsIC0gUkZCMRYwFAYDVQQLEw1S\r\n" + 
			"RkIgZS1DTlBKIEExMRMwEQYDVQQLEwpwcmVzZW5jaWFsMRcwFQYDVQQLEw4xMTczNTIzNjAwMDE5\r\n" + 
			"MjE0MDIGA1UEAxMrU0FPIFBBVUxPIFBSRVZJREVOQ0lBIFNQUFJFVjowOTA0MTIxMzAwMDEzNjCC\r\n" + 
			"ASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANaPFMui6FAwuUrQ/x+4pKG+TcV+mgv0O8Dk\r\n" + 
			"Wves5ITJj7LmkyibDZUHOlR27d0+O2Ra0Mw63OyqUbgwRu6bGYQAhhLVVz315NdGqq3/lpwZCuLD\r\n" + 
			"zzHWsDUwF/k50DaD8ROU53m8iL7BEznQqaWWxMJZUHUhIjTXEN+YLS7hWKRpH3Dxbf06v4fLcHBO\r\n" + 
			"EeC/r4AHu6Mqz2+EZMDVXupGk7+9cTPEW/byOT87VI1p3PErwzKvdGgHzClfm7WUebKtHJ9+pnbd\r\n" + 
			"RJMGgYryxmc/MhiRmUwT0ScfF/BIAQ+ltOnKpiNBI1ZZK1dOtfqWVLk2jCp46L0RU7vJ9Qorm+0D\r\n" + 
			"9DUCAwEAAaOCAuEwggLdMA4GA1UdDwEB/wQEAwIF4DCBpwYIKwYBBQUHAQEEgZowgZcwNwYIKwYB\r\n" + 
			"BQUHMAGGK2h0dHA6Ly9pby1vY3NwLWljcGJyLmltcHJlbnNhb2ZpY2lhbC5jb20uYnIwXAYIKwYB\r\n" + 
			"BQUHMAKGUGh0dHA6Ly9pby1jb20taWNwYnIuaW1wcmVuc2FvZmljaWFsLmNvbS5ici9yZXBvc2l0\r\n" + 
			"b3Jpby9JTUVTUFJGQi9BQ0lNRVNQUkZCRzUucDdiMB8GA1UdIwQYMBaAFEr/viv/WJqH/i3Nc/Qm\r\n" + 
			"RjLtsq5iMGIGA1UdIARbMFkwVwYGYEwBAgEUME0wSwYIKwYBBQUHAgEWP2h0dHA6Ly9pby1jb20t\r\n" + 
			"aWNwYnIuaW1wcmVuc2FvZmljaWFsLmNvbS5ici9yZXBvc2l0b3Jpby9JTUVTUFJGQjAJBgNVHRME\r\n" + 
			"AjAAMIGyBgNVHR8EgaowgacwVqBUoFKGUGh0dHA6Ly9pby1jb20taWNwYnIuaW1wcmVuc2FvZmlj\r\n" + 
			"aWFsLmNvbS5ici9yZXBvc2l0b3Jpby9JTUVTUFJGQi9BQ0lNRVNQUkZCRzUuY3JsME2gS6BJhkdo\r\n" + 
			"dHRwOi8vbGNyLmltcHJlbnNhb2ZpY2lhbC5jb20uYnIvcmVwb3NpdG9yaW8vSU1FU1BSRkIvQUNJ\r\n" + 
			"TUVTUFJGQkc1LmNybDCBsAYDVR0RBIGoMIGlgRJqcm1vcmFlc0BzcC5nb3YuYnKgOAYFYEwBAwSg\r\n" + 
			"LwQtMjkwMzE5NTE1MTkwNzQ4ODgwNDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwoCEGBWBMAQMC\r\n" + 
			"oBgEFkpPU0UgUk9CRVJUTyBERSBNT1JBRVOgGQYFYEwBAwOgEAQOMDkwNDEyMTMwMDAxMzagFwYF\r\n" + 
			"YEwBAwegDgQMMDAwMDAwMDAwMDAwMCkGA1UdJQQiMCAGCCsGAQUFBwMCBggrBgEFBQcDBAYKKwYB\r\n" + 
			"BAGCNxQCAjANBgkqhkiG9w0BAQsFAAOCAgEAI4hr9CBJPty3D/r4I1fv5IpUoGrZgIgHjjPwl+3A\r\n" + 
			"lgIA3ABosplF7SXpwmp2jo0/9muzOjo6YJLPVGdCSGv/1ypgaqoJ4pxXIHGTHXdFfzxen2K09WT3\r\n" + 
			"lypnL8r22ZFJoiULtlwn1jAQyinuSk8ah/C0yjg1yWsDMo3bv3JOLQX96f9e/+eRRAVsDey6zqBV\r\n" + 
			"c++s2j3sGMNoKeYmYJ+GLExGPPshyQlGBquaf1ow0kVegtyntS5tbXbDtA3lNTiU4R8RqMZEjw+j\r\n" + 
			"pO44AHMp4zBdFDxNOd/LvLHr2SHputFrZTYoZCsAQCCuH5VRb0z9bp5Kcds/SD8IXdrr+Xts2OUe\r\n" + 
			"1ohOgwuz3WSkwxOWt9VmeNFNXmCSwRuW2yHlRAYuRb/VNO0achjGu9lwiCnm/aJTygMqgqknoJhB\r\n" + 
			"zBDjlEcK6yi+AvzKKwZxtQ8LMIxjFhyFClT0HocgEkuWg6/Efadrg4Ya1laNaDqkENljCJepw6Ie\r\n" + 
			"Eda1A790hF8eAGQYfr8xfmEuGYu4Y7n+mttdKLL3RTwx7Qyl049eX9xLUlstPuK9PMiMz+Rj6NP3\r\n" + 
			"kOv5kgjLqYHNu58tddvZMZ4cW26lhWilD/KVSolQXr/2mleoWrZAwt8jF8MIp0/VPXZsUeEQ2WAs\r\n" + 
			"OEtBzeryWsVaJ/OfRYfp/oF8b1vZ4sKvTms=</X509Certificate></X509Data></KeyInfo></Signature></eSocial>" +
			
			
			
			
			
			//FIM BLOCO DO EVENTO ASSSINADO
			"</evento>" +
			"</eventos>\r\n" + 
			"</envioLoteEventos>\r\n" + 
			"</eSocial>";
}
