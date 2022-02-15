package br.com.atlantic.comum.utils;

import java.util.ArrayList;
import java.util.Collection;

import br.com.esocial.comum.dao.daogenerico.Erros;
import br.com.esocial.comum.dao.daogenerico.ErrosDAO;



public class MsgExcept extends Exception {

	private final String CLASS_NAME = getClass().getName().replaceFirst(getClass().getPackage().getName() + ".","");
	private Log log = new Log(CLASS_NAME);
	private String versao = "versão 3.00";
	private String message = "";

	private int errorNumber = 0;
	private String campo = "";
	private Collection listaDeErros = new ArrayList();

	/**
	 */
	public MsgExcept() {
		log.log( versao );
	}

	/**
	 * @param s mensagem de erro
	 */
	public MsgExcept(String s) {
		super(s);
		message = s;
		log.log( versao );
	}

	/**
	 * @param s c\u00F3digo da mensagem de erro
	 */
	public MsgExcept(int s) {
		message = getMsg(s);
		errorNumber = s;
		log.log( versao );
	}

	/**
	 * @param s c\u00F3digo da mensagem de erro
	 * @param s1 descri\u00E7\u00E3o a mais da mensagem
	 */
	public MsgExcept(int s, String s1) {
		//message = getMsg(s) + " (" + s1 + ")";
		if (s == 3098) { //Niedson Araujo TASK 2064 MSG DEVE VIR DA PAC  
			campo = s + " - " + s1;
		} else if (s == 300) {
			campo = s + " - " + s1;
		} else {
			message = getMsg(s);
			errorNumber = s;
			// Retirado para evitar a exibi\u00E7\u00E3o de mensagens indevidas.
	//		campo = s1;
		}
		
		log.log( versao );
		log.log( errorNumber + " - " + this.message + " (" + campo + ")" );
	}

	/**
	 * Retorna a mensagem de erro
	 * @param s c\u00F3digo da mensagem de erro
	 * @return a mensagem de erro
	 */
	public String getMsg(int s) {
		switch (s) {
		//Niedson Araujo 22/08/2013 TASK 2064
		case 301 : {
			message = "Endere\u00E7o n&atilde;o cadastrado.";
			break;
		}
		case 302 : {
			message = "Existe cr\u00E9dito para o m\u00EAs de refer\u00EAncia.";
			break;
		}
		//---------FIM------------------------
		//Niedson Araujo 11/11/2013 TASK 1298
		case 70501 : {
			message = "Rubrica existente na composi\u00E7\u00E3o individual.";
			break;
		}
		case 70502 : {
			message = "Rubrica existente no lancamento parcelado.";
			break;
		}
		case 70503 : {
			message = "Rubrica existente no status do lancamento parcelado.";
			break;
		}
		case 70504 : {
			message = "n&atilde;o foi poss&iacute;vel buscar as composi\u00E7\u00F5es, pois n&atilde;o existe per\u00EDodo aberto para este benefici&acute;rio.";
			break;
		}
		//FIM
		case 1000 :
		{
			message = "Erro na conex&atilde;o com o banco de dados.";
			break;
		}
		case 1001 :
		{
			message =
				"Um erro de processamento interno ocorreu. Caso o erro se repita contate o suporte.";
			break;
		}
		case 1002 :
		{
			message = "Erro na execu&ccedil;&atilde;o do comando.";
			break;
		}
		case 1004 :
		{
			message = "Nenhum registro foi encontrado para a pesquisa realizada.";
			break;
		}
		case 1005 :
		{
			message = "Voc&ecirc; perdeu a conex&atilde;o.";
			break;
		}
		case 1006 :
		{
			message = "Registro Duplicado.";
			break;
		}
		case 1007 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel excluir o registro. Existem refer\u00EAncias internas ao mesmo.";
			break;
		}
		case 2000 :
		{
			message = "Campo necess&aacute;rio faltando.";
			break;
		}
		case 2001 :
		{
			message = "CPF inv&acute;lido.";
			break;
		}
		case 2002 :
		{
			message = "CNPJ inv&acute;lido.";
			break;
		}
		case 2003 :
		{
			message = "Usu&aacute;rio ou Senha inv&aacute;lida.";
			break;
		}
		case 2004 :
		{
			message = "Senha e confirm&ccedil;&acirc;o de senha est&atilde;oo diferentes.";
			break;
		}
		case 2005 :
		{
			message = "J&acute; existe perfil cadastrado com este nome.";
			break;
		}
		case 2006 :
		{
			message = "J&acute; existe usu\u00E1rio cadastrado com este login neste instituto.";
			break;
		}
		case 2007 :
		{
			message = "Senha atual inv&aacute;lida.";
			break;
		}
		case 2008 :
		{
			message = "Assinatura eletr\u00F4nica n&atilde;o coincide com a senha de acesso.";
			break;
		}
		case 2009 :
		{
			message = "Usu&aacute;rio inexistente.";
			break;
		}
		case 2010 :
		{
			message = "J&acute; existe um servidor cadastrado com este cpf neste instituto.";
			break;
		}
		case 2011 :
		{
			message = "J&acute; existe um dependente cadastrado com este n&uacute;mero de depend&ecirc;ncia.";
			break;
		}
		case 2012 :
		{
			message = "J&acute; existe um empregador cadastrado com este cnpj neste instituto.";
			break;
		}
		case 2013 :
		{
			message = "Este cargo j&acute; foi cadastrado para o servidor especificado.";
			break;
		}
		case 2014 :
		{
			message =
				"J&acute; existe um \u00F3rg\u00E3o cadastrado com este c\u00F3digo para o empregador especificado.";
			break;
		}
		case 2015 :
		{
			message =
				"J&acute; existe uma se\u00E7\u00E3o cadastrada com este c\u00F3digo para o &eacute;rg\u00E3o especificado.";
			break;
		}
		case 2016 :
		{
			message =
				"J&acute; existe um trabalho cadastrado com estas informa\u00E7\u00F5es para o servidor especificado.";
			break;
		}
		case 2017 :
		{
			message = "O cargo escolhido n&atilde;o est&atilde;o associado a nenhum grupo.";
			break;
		}
		case 2018 :
		{
			message = "A quantidade m\u00E1xima de cargos por servidor foi excedida.";
			break;
		}
		case 2019 :
		{
			message = "A associ&ccedil;&acirc;o entre cargos escolhida \u00E9 inv&aacute;lida.";
			break;
		}
		case 2020 :
		{
			message = "J&acute; existe um grupo cadastrado com este c\u00F3digo.";
			break;
		}
		case 2021 :
		{
			message =
				"J&acute; existe um cargo cadastrado com este c\u00F3digo para a se\u00E7\u00E3o espec\u00EDficada.";
			break;
		}
		case 2022 :
		{
			message = "Quantidade m\u00E1xima de cargos excedida.";
			break;
		}
		case 2023 :
		{
			message =
				"O grupo espec\u00EDficado n&atilde;o pode ser exclu\u00EDdo porque possui cargos o referenciando.";
			break;
		}
		case 2024 :
		{
			message =
				"Este cargo n&atilde;o pode ser exclu\u00EDdo por estar sendo utilizado por um servidor ativo.";
			break;
		}
		case 2025 :
		{
			message =
				"Algum cargo n&atilde;o pode ser exclu\u00EDdo por estar sendo utilizado por um servidor ativo.";
			break;
		}
		case 2026 :
		{
			message =
				"Alguns cargos n&atilde;o puderam ser exclu\u00EDdos por estarem sendo utilizados por um servidor ativo.";
			break;
		}
		case 2027 :
		{
			message = "J&acute; existe um servidor cadastrado com PIS informado.";
			break;
		}
		case 2028 :
		{
			message =
				"Para este tipo de rel&ccedil;&acirc;o de Parentesco, a Data de Nasc. do Dependente deve ser maior que a Data de Nasc. do Servidor...";
			break;
		}
		case 2029 :
		{
			message =
				"n&atilde;o &eacute; poss&iacute;vel cadastrar um CPF de outro Servidor para um Dependente. Essa situ&ccedil;&acirc;o somente \u00E9 permitida se o Dependente for Conjuge.";
			break;
		}
		case 2030 :
		{
			message =
				"n&atilde;o &eacute; poss&iacute;vel cadastrar o mesmo CPF para dois Dependentes diferentes.";
			break;
		}
		case 2031 :
		{
			message =
				"n&atilde;o &eacute; poss&iacute;vel excluir o Dependente. Existem benef\u00EDcios associados para o Servidor.";
			break;
		}
		case 2032 :
		{
			message =
				"Voc&ecirc; n&atilde;o pode cadastrar um trabalho para um CNPJ que n&atilde;o est&atilde;o cadastrado como Empregador.";
			break;
		}
		case 2033 :
		{
			message =
				"O nome do arquivo &eacute; de preenchimento obrigat\u00F3rio no arquivo de arrecad&ccedil;&acirc;o.";
			break;
		}
		case 2034 :
		{
			message =
				"A data do arquivo &eacute; de preenchimento obrigat\u00F3rio no arquivo de arrecad&ccedil;&acirc;o";
			break;
		}
		case 2035 :
		{
			message = "O CNPJ do &eacute;rg\u00E3o est&atilde;o inv&acute;lido ou n&atilde;o foi preenchido.";
			break;
		}
		case 2036 :
		{
			message =
				"O nome do &eacute;rg\u00E3o &eacute; de preenchimento obrigat\u00F3rio no arquivo de arrecad&ccedil;&acirc;o.";
			break;
		}
		case 2037 :
		{
			message =
				"O tipo de pagamento &eacute; de preenchimento obrigat\u00F3rio no arquivo de arrecad&ccedil;&acirc;o.";
			break;
		}
		case 2038 :
		{
			message =
				"O per\u00EDodo de compet\u00EAncia \u00E9 de preenchimento obrigat\u00F3rio no arquivo de arrecad&ccedil;&acirc;o.";
			break;
		}
		case 2039 :
		{
			message =
				"O total de servidores \u00E9 de preenchimento obrigat\u00F3rio no arquivo de arrecad&ccedil;&acirc;o.";
			break;
		}
		case 2040 :
		{
			message =
				"O sal\u00E1rio de contribu\u00ED\u00E7\u00E3o &eacute; de preenchimento obrigat\u00F3rio no arquivo de arrecad&ccedil;&acirc;o.";
			break;
		}
		case 2041 :
		{
			message = "O CPF do servidor est&atilde;o inv&acute;lido ou n&atilde;o foi preenchido.";
			break;
		}
		case 2042 :
		{
			message =
				"A matr\u00EDcula \u00E9 de preenchimento obrigat\u00F3rio no arquivo de arrecad&ccedil;&acirc;o.";
			break;
		}
		case 2043 :
		{
			message =
				"O tipo do per\u00EDodo de pagamento &eacute; de preenchimento obrigat\u00F3rio no arquivo de arrecad&ccedil;&acirc;o.";
			break;
		}
		case 2044 :
		{
			message =
				"A remuner&ccedil;&acirc;o de contribu\u00ED\u00E7\u00E3o &eacute; de preenchimento obrigat\u00F3rio no arquivo de arrecad&ccedil;&acirc;o.";
			break;
		}
		case 2045 :
		{
			message =
				"O total de servidores n&atilde;o confere com o n&uacute;mero de linhas do arquivo.";
			break;
		}
		case 2046 :
		{
			message =
				"O total de sal\u00E1rio de contribu\u00ED\u00E7\u00E3o n&atilde;o confere com o total de remuner&ccedil;&acirc;o dos servidores contidos no arquivo.";
			break;
		}
		case 2047 :
		{
			message = "Arquivo n&atilde;o anexado.";
			break;
		}
		case 2048 :
		{
			message = "CNPJ n&atilde;o encontrado para esse instituto.";
			break;
		}
		case 2049 :
		{
			message = "Tamanho de arquivo inv&acute;lido.";
			break;
		}
		case 2050 :
		{
			message = "n&atilde;o existe o lote com o n&uacute;mero especificado.";
			break;
		}
		case 2051 :
		{
			message = "O CPF do servidor especificado n&atilde;o existe.";
			break;
		}
		case 2052 :
		{
			message = "O CNPJ informado n&atilde;o confere com o existente no arquivo carregado.";
			break;
		}
		case 2053 :
		{
			message =
				"O sal\u00E1rio de contribui\u00E7\u00E3o informado n&atilde;o confere com o existente no arquivo.";
			break;
		}
		case 2054 :
		{
			message = "J&acute; houve lan\u00E7amento de movimento para este item neste m\u00EAs.";
			break;
		}
		case 2055 :
		{
			message = "Este C\u00F3digo Geral j&acute; existe.";
			break;
		}
		case 2056 :
		{
			message = "Per\u00EDodo j&acute; cadastrado para para esse Tipo de Contribui&ccirc;&atilde;o.";
			break;
		}
		case 2057 :
		{
			message = "Existe um tramite em andamento para esta rel&ccedil;&acirc;o funcional.";
			break;
		}
		case 2058 :
		{
			message = "n&atilde;o existe item cont\u00E1bil no plano de contas deste ano...";
			break;
		}
		case 2059 :
		{
			message = "n&atilde;o &eacute; permitido cadastrar movimentos para uma conta bloqueada...";
			break;
		}
		case 2060 :
		{
			message =
				"n&atilde;o foi poss&iacute;vel fazer a recuper&ccedil;&acirc;o porque existem movimentos para este ano de destino.";
			break;
		}
		case 2061 :
		{
			message =
				"n&atilde;o foi poss&iacute;vel encontrar o diretorio de destino para UPLOAD. Contate o Administrador do sistema.";
			break;
		}
		case 2062 :
		{
			message =
				"O servidor n&atilde;o pode ter uma data de \u00F3bito at\u00E9 que os tramites de benef\u00EDcios que est&atilde;oo abertos para ele sejam cancelados.";
			break;
		}
		case 2063 :
		{
			message =
				"O servidor possui tramite corrente para Pens\u00E3o por Morte e deve possuir uma data de \u00F3bito.";
			break;
		}
		case 2064 :
		{
			message =
				"Para possuir data de \u00F3bito o servidor n&atilde;o deve ter mais nenhum cargo atual vigente.";
			break;
		}
		case 2065 :
		{
			message =
				"n&atilde;o pode haver um registro com o mesmo grupo de pagamento para um mesmo instituto e uma mesma compet\u00EAncia.";
			break;
		}
		case 2066 :
		{
			message =
				"Exclus\u00E3o Negada ! A exclus\u00E3o s\u00F3 ser\u00E1 permitida quando nenhuma profiss\u00E3o estiver associada ao plano de carreira que se deseja excluir.";
			break;
		}
		case 2067 :
		{
			message =
				"n&atilde;o &eacute; poss&iacute;vel realizar a opera&ccirc;&atilde;o pois a data de nascimento &eacute; posterior a data de in\u00EDcio de depend&ecirc;ncia.";
			break;
		}
		case 2068 :
		{
			message =
				"n&atilde;o &eacute; poss&iacute;vel realizar a opera&ccirc;&atilde;o pois a data de ingresso ao servi\uFFFDo p\u00FAblico n&atilde;o &eacute; posterior a data de nascimento.";
			break;
		}
		case 2069 :
		{
			message =
				"Data de emiss\u00E3o da carteira de trabalho deve ser posterior a data de nascimento.";
			break;
		}

		//Mensagem utilizada no fluxo de pens\u00E3o.
		case 2070 :
		{
			message =
				"Verifique o cadastro do(s) dependente(s). n&atilde;o foi encontrado grupo e/ou classe v&acute;lidos para os mesmos.";
			break;
		}

		//Mensagem utilizada no fluxo de entrega de documentos.
		case 2071 :
		{
			message =
				"n&atilde;o foi cadastrado documento faltante para o processo em quest&atilde;oo.";
			break;
		}


		case 2072 :
		{
			message =
				"Login solicitado para cadastro j&acute; existe para outro usu\u00E1rio.";
			break;
		}
		case 2073 :
		{
			message =
				"n&atilde;o podemos aplicar a Retroatividade, pois a data de in\u00EDcio de benef\u00EDcio &eacute; maior ou igual ao per\u00EDodo aberto da folha de pagamento.";
			break;
		}
		case 2074 :
		{
			message =
				"Periodo em aberto n&atilde;o encontrado para o grupo vinculado ao benefici&acute;rio em quest&atilde;oo";
			break;
		}
		case 2075 :
		{
			message =
				"Limite de 6 dias de falta abonada por ano foi ultrapassado. Favor rever cadastro.";
			break;
		}
		case 2076 :
		{
			message =
				"Limite de 24 dias de falta justificada por ano foi ultrapassado. Favor rever cadastro.";
			break;
		}
		case 2077 : 
		{
			message = 
				"Limite de 2 dias de falta m\u00E9dica / IAMSPE foi ultrapassado. Favor rever cadastro.";
			break;
		}
		case 2078 : 
		{
			message = 
				"O usu\u00E1rio logado n&atilde;o tem permiss\u00E3o para visualizar os registros nessa tela.";
			break;
		}
		case 2079 : 
		{
			message = 
				"Limite de 1 dia de falta abonada por m\u00EAs foi ultrapassado. Favor rever cadastro.";
			break;
		}
		case 2080 : 
		{
			message = 
				"n&atilde;o &eacute; poss&iacute;vel cadastrar faltas anteriores ao in\u00EDcio da rel&ccedil;&acirc;o funcional. Favor rever cadastro.";
			break;
		}
		case 2081 : 
		{
			message = 
				"n&atilde;o &eacute; poss&iacute;vel cadastrar afastamentos anteriores ao in\u00EDcio da rel&ccedil;&acirc;o funcional. Favor rever cadastro.";
			break;
		}
		case 2082 : 
		{
			message = 
				"n&atilde;o &eacute; poss&iacute;vel cadastrar penalidades anteriores ao in\u00EDcio da rel&ccedil;&acirc;o funcional. Favor rever cadastro.";
			break;
		}
		case 2083 :
		{
			message =
				"Limite de 15 dias de falta injustificada consecutivas (trabalhador tempor\u00E1rio/lei 500/74) foi ultrapassado. Favor rever cadastro.";
			break;
		}
		case 2084 :
		{
			message =
				"Limite de 30 dias de falta injustificada para (trabalhador tempor\u00E1rio/lei 500/74) por ano foi ultrapassado. Favor rever cadastro.";
			break;
		}
		case 2085 :
		{
			message =
				"Limite de 30 dias de falta injustificada consecutivas foi ultrapassado. Favor rever cadastro.";
			break;
		}
		case 2086 :
		{
			message =
				"Limite de 45 dias de falta injustificada por ano foi ultrapassado. Favor rever cadastro.";
			break;
		}
		case 2087 :
		{
			message =
				"Favor extinguir o benefici&acute;rio antes de formalizar.";
			break;
		}
		case 2088 :
		{
			message =
				"n&atilde;o existe data de nascimento para o servidor. Favor realizar a manuten\u00E7\u00E3o antes dessa opera&ccirc;&atilde;o.";
			break;
		}
		case 2089 :
		{
			message =
				"n&atilde;o existe atributo COM ISEN\u00C7\u00C3O DE IMPOSTO DE RENDA para o servidor em quest&atilde;oo. Favor cri\u00E1-lo no menu Folha de Pagamento > Manuten\u00E7\u00E3o > Cadastro de Atributos.";
			break;
		}
		case 2090 :
		{
			message =
				"n&atilde;o foi poss&iacute;vel calcular a m\u00E9dia aritm\u00E9tica dos 80% dos maiores \u00FAltimos sal\u00E1rios devido a falta de FATORES DE CORRE\u00C7\u00C3O cadastrados no sistema at\u00E9 o m\u00EAs de solicita\u00E7\u00E3o deste benef\u00EDcio";
			break;
		}
		case 2091 :
		{
			message =
				"n&atilde;o foi poss&iacute;vel calcular a m\u00E9dia aritm\u00E9tica dos 80% dos maiores \u00FAltimos sal\u00E1rios devido a falta de informa\u00E7\u00F5es sobre o hist\u00F3rico de contribui\u00E7\u00E3o deste servidor.";
			break;
		}
		case 2092 :
		{
			message =
				"n&atilde;o h&acute; registro de \u00FAltima ficha financeira para esse servidor.";
			break;
		}
		case 2093 :
		{
			message =
				"n&atilde;o h&acute; registro de fator de corre\u00E7\u00E3o anterior \u00E0 data de solicit&ccedil;&acirc;o.";
			break;
		}
		case 2094 :
		{
			message =
				"n&atilde;o existe uma ordem judicial com valor porcentual para esse servidor. Cadastre-o antes de prosseguir com esse protocolo.";
			break;
		}
		case 2095 :
		{
			message =
				"DIRF n&atilde;o encontrada para os par\u00E2metros selecionados pelo usu\u00E1rio.";
			break;
		}
		case 2096 :
		{
			message =
				"Op\u00E7\u00E3o de Benef\u00EDcio n&atilde;o selecionada. Favor selecionar antes de executar essa opera&ccirc;&atilde;o.";
			break;
		}
		case 2097 :
		{
			message =
				"n&atilde;o &eacute; poss&iacute;vel realizar o cancelamento desse fluxo pois j&acute; existe esse benef\u00EDcio em folha de pagamento.";
			break;
		}
		case 2098 :
		{
			message = 
				" n&atilde;o existe a data de in\u00EDcio da rel&ccedil;&acirc;o funcional. Favor verificar em Rel&ccedil;&acirc;o Funcional os dados do servidor.";

		}
		case 2099 :
		{
			message =
				"n&atilde;o existe data de laudo m\u00E9dico. Favor realizar a manuten\u00E7\u00E3o antes dessa opera&ccirc;&atilde;o.";
			break;
		}
		case 2100 :
		{
			message =
				"n&atilde;o existe cadastro de sexo ou o mesmo est&atilde;o indeterminado. Favor realizar a manuten\u00E7\u00E3o antes dessa opera&ccirc;&atilde;o.";
			break;
		}
		case 2101 :
		{
			message =
				"J&acute; existe um registro nesta compet\u00EAncia para este benefici&acute;rio.";
			break;
		}
		case 2102 :
		{
			message =
				"R\u00FAbrica n&atilde;o encontrada. Favor criar a r\u00FAbrica antes de prosseguir com o fluxo.";
			break;
		}
		case 2103 :
		{
			message =
				"H&acute; mais de uma evolu&ccirc;atilde;o funcional do benefici&acute;rio em aberto para o servidor em quest&atilde;oo. Favor realizar a manuten\u00E7\u00E3o antes de prosseguir com a formaliz&ccedil;&acirc;o.";
			break;
		}
		case 2104 :
		{
			message =
				"A conta banc\u00E1ria no cadastro do servidor n&atilde;o foi preenchida.";
			break;
		}   case 2105 :
		{
			message =
				"Cargo n&atilde;o encontrado. Favor escolher um novo c\u00F3digo de cargo v&acute;lido.";
			break;
		}
		case 2106 :
		{
			message = 
				" n&atilde;o existe a data de fim da rel&ccedil;&acirc;o funcional para servidor exonerado ou comissionado. Favor verificar em Rel&ccedil;&acirc;o Funcional os dados do servidor.";
			break;

		}
		case 2107 :
		{
			message = 
				"n&atilde;o existe a data de fim da evolu&ccirc;atilde;o funcional para servidor exonerado ou comissionado. Favor verificar em Evolu&ccirc;&atilde;o Funcional os dados do servidor.";
			break;

		}
		case 2108 :
		{
			message = 
				"Certid\u00E3o de Tempo de Contribui&ccirc;&atilde;o n&atilde;o foi realizada. Favor clicar em 'Certid\u00E3o de Tempo de Contribui&ccirc;&atilde;o' e seguir com o procedimento para a grava&ccirc;atilde;o da certid&atilde;o.";
			break;

		}
		case 2109 :
		{
			message = 
				"Manifesta&ccirc;atilde;o Analista da SPPREV n&atilde;o foi realizada. " +
				"Favor clicar sobre o documento e seguir com o procedimento para a grava&ccirc;atilde;o da manifesta&ccirc;atilde;o quando escolher o caminho 'n&atilde;o Homologado'.";
			break;

		}
		case 2110 :
		{
			message = "A matr\u00EDcula do servidor especificado n&atilde;o existe.";
			break;
		}
		//Msg e erros de contabilidade
		case 3000 :
		{
			message = "Unidade Or\u00E7ament\u00E1ria j&acute; esta associada a um PT.";
			break;
		}
		case 3001 :
		{
			message = "\u00D3rg\u00E3o j&acute; esta associado a um PT.";
			break;
		}
		case 3002 :
		{
			message = "Elemento j&acute; esta associado a uma Despesa.";
			break;
		}
		case 3003 :
		{
			message = "Item j&acute; esta associado a uma Despesa";
			break;
		}
		case 3004 :
		{
			message = "Natureza de Despesa j&acute; esta cadastrada.";
			break;
		}
		case 3005 :
		{
			message = "n&atilde;o foi encontrada nenhuma despesa cadastrada.";
			break;
		}
		case 3006 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel excluir uma conta que possua moviment&ccedil;&acirc;o.";
			break;
		}
		case 3007 :
		{
			message = "J&acute; existe uma conta com este c\u00F3digo.";
			break;
		}
		case 3008 :
		{
			message = "CPF j&acute; esta cadastrado.";
			break;
		}
		case 3009 :
		{
			message = "CNPJ j&acute; esta cadastrado.";
			break;
		}
		case 3010 :
		{
			message = "Dot&ccedil;&acirc;o or\u00E7ament\u00E1ria insuficiente.";
			break;
		}
		case 3011 :
		{
			message = "Natureza de Despesa j&acute; esta associada a um PT.";
			break;
		}
		case 3012 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel excluir o Programa, o mesmo esta associado a um PT.";
			break;
		}
		case 3013 :
		{
			message = "O saldo atual do empenho original \u00E9 menor que valor \u00E0 cancelar.";
			break;
		}
		case 3014 :
		{
			message = "Programa j&acute; esta cadastrado.";
			break;
		}
		case 3015 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel excluir a A\u00E7\u00E3o, a mesma esta associada a um PT.";
			break;
		}
		case 3016 :
		{
			message = "A\u00E7\u00E3o j&acute; esta cadastrada.";
			break;
		}
		case 3017 :
		{
			message = "Saldo de empenho original menor que valor do Sub-Empenho.";
			break;
		}
		case 3018 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel excluir o PT, o mesmo esta associado a uma Dot&ccedil;&acirc;o.";
			break;
		}
		case 3019 :
		{
			message = "Programa de Trabalho (PT) j&acute; esta cadastrado.";
			break;
		}
		case 3020 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel excluir a Natureza de Despesa / PT, a mesma esta associada a uma Dot&ccedil;&acirc;o.";
			break;
		}
		case 3021 :
		{
			message = "Natureza de Despesa / PT j&acute; esta cadastrada.";
			break;
		}
		case 3022 :
		{
			message = "Dot&ccedil;&acirc;o j&acute; esta cadastrada.";
			break;
		}
		case 3023 :
		{
			message = "SubAlinea j&acute; est&atilde;o cadastrada.";
			break;
		}
		case 3024 :
		{
			message = "Exclus\u00E3o Negada ! O valor da r\u00FAbrica comp\u00F5e o Quadro de Detalhamento de Receita(QDR) existente. Remova o QDR associado &eacute; r\u00FAbrica e tente novamente.";
			break;
		}
		case 3025 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel criar uma conta sem que haja uma conta pai.";
			break;
		}
		case 3026 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel criar um Empenho com este n&uacute;mero, pois j&acute; existe Empenho registrado com o mesmo.";
			break;
		}
		case 3027 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel criar um Empenho com este n&uacute;mero de processo, pois j&acute; existe Empenho registrado com o mesmo.";
			break;
		}
		case 3028 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel criar um Empenho com este n&uacute;mero de processo licitat\u00F3rio, pois j&acute; existe Empenho registrado com o mesmo.";
			break;
		}
		case 3029 :
		{
			message = "Grav&ccedil;&acirc;o Negada ! O valor da r\u00FAbrica comp\u00F5e o Quadro de Detalhamento de Receita(QDR) existente. Remova o QDR associado &eacute; r\u00FAbrica e tente novamente.";
			break;
		}
		case 3030 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel excluir uma conta n&atilde;o escriturada.";
			break;
		}
		case 3031 :
		{
			message = "n&atilde;o foi encontrado hist\u00F3rico padr\u00E3o para o evento informado.";
			break;
		}
		case 3032 :
		{
			message = "&Eacute; necess&aacute;rio cadastrar um contrato para que a liquid&ccedil;&acirc;o possa ser realizada.";
			break;
		}
		case 3033 :
		{
			message = "n&atilde;o h&acute; per\u00EDodo em execu&ccedil;&atilde;o.";
			break;
		}
		case 3034 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel excluir o Hist\u00F3rico Padr\u00E3o, o mesmo esta associado a um Evento Cont\u00E1bil.";
			break;
		}
		case 3035 :
		{
			message = "Hist\u00F3rico Padr\u00E3o j&acute; esta cadastrado.";
			break;
		}
		case 3036 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel excluir o Evento Cont\u00E1bil, o mesmo esta associado a contas para Lan\u00E7amentos.";
			break;
		}
		case 3037 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel excluir o Evento Cont\u00E1bil, o mesmo esta associado a Comportamentos.";
			break;
		}
		case 3038 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel excluir o Evento Cont\u00E1bil, o mesmo esta associado a Opera\u00E7\u00F5es.";
			break;
		}
		case 3039 :
		{
			message = "Evento Cont\u00E1bil j&acute; esta cadastrado.";
			break;
		}
		case 3040 :
		{
			message = "C\u00F3digo do credor inv&acute;lido.";
			break;
		}
		case 3041 :
		{
			message = "Quadro demonstrativo de receita QDR inexistente.";
			break;
		}
		case 3042:
		{
			message = "Estrutura de contas inexistente";
			break;
		}
		case 3043:
		{
			message = "Conta e eventos j&acute; associados";
			break;
		}
		case 3044:
		{
			message = "Evento e opera&ccirc;&atilde;o j&acute; associados";
			break;
		}
		case 3045:
		{
			message = "Evento j&acute; existente";
			break;
		}
		case 3046:
		{
			message = "Lan\u00E7amento n&atilde;o pode ser efetuado com sucesso";
			break;
		}
		case 3047:
		{
			message = "Valor informado excedeu o montante a ser pago";
			break;
		}
		case 3048:
		{
			message = "n&atilde;o existe dot&ccedil;&acirc;o suficiente para a reserva";
			break;
		}
		case 3049:
		{
			message = "Erro interno de servidor. Favor contactar um administrador";
			break;
		}
		case 3050:
		{
			message = "Evento escolhido n&atilde;o possui contas cont\u00E1beis associadas";
			break;
		}
		case 3051:
		{
			message = "n&atilde;o &eacute; poss&iacute;vel fazer um lan\u00E7amento de R$ 0,00";
			break;
		}
		case 3052 :
		{
			message = "ERRO! Aus\u00EAncia de dados importantes para dar continuidade \u00E9 execu&ccedil;&atilde;o.";
			break;
		}
		case 3053 :
		{
			message = "R\u00FAbrica de receita inv&aacute;lida.";
			break;
		}
		case 3054 :
		{
			message = "Programa de trabalho n&atilde;o pode ser usado por estar bloqueado.";
			break;
		}
		case 3055 :
		{
			message = "Evento precisa ter pelo menos uma conta de cr\u00E9dito e de d\u00F3bito.";
			break;
		}
		case 3056 :
		{
			message = "C\u00F3digo padr\u00E3o inexistente.";
			break;
		}
		case 3057 :
		{
			message = "O valor de contigenciamento &eacute; maior que o saldo atual.";
			break;
		}
		case 3058 :
		{
			message = "O valor de descontigenciamento &eacute; maior que o valor contigenciado.";
			break;
		}
		case 3059 :
		{
			message = "n&atilde;o pode haver mais lan\u00E7amentos no m\u00EAs em quest&atilde;oo. Este se encontra fechado para lan\u00E7amentos. Vide menu Controle de Per\u00EDodos - Per\u00EDodo/M\u00EAs.";
			break;
		}
		case 3060 :
		{
			message = "n&atilde;o existe registro cadastrado em Per\u00EDodo/M\u00EAs.";
			break;
		}
		case 3061 :
		{
			message = "O somat\u00F3rio do cronograma n&atilde;o confere.";
			break;
		}
		case 3062 :
		{
			message = "O evento informado n&atilde;o existe na lista de eventos cont\u00E1beis cadastrados.";
			break;
		}
		case 3063 :
		{
			message = "O evento informado como origem n&atilde;o pode ter hist\u00F3rico padr\u00E3o associado.";
			break;
		}
		case 3064 :
		{
			message = "O evento informado como origem n&atilde;o pode ter contas cont\u00E1beis associadas.";
			break;
		}
		case 3065 :
		{
			message = "O evento informado como origem n&atilde;o pode ser utilizado por este j&acute; ser evento de segundo n\u00EDvel (evento filho).";
			break;
		}
		case 3066 :
		{
			message = "O evento n&atilde;o pode ter contas cont\u00E1beis associadas por se tratar de um evento pai.";
			break;
		}
		case 3067 :
		{
			message = "O evento n&atilde;o pode ter valores totais de cr\u00E9dito e d\u00F3bito diferentes.";
			break;
		}
		case 3068 :
		{
			message = "J&acute; existe periodo Aberto para este atributo.";
			break;
		}
		case 3069 :
		{
			message = "J&acute; existe este periodo de vig\u00EAncia para esta faixa (In\u00EDcio e Fim de Vig\u00EAncia) que deseja cadastrar.";
			break;
		}
		case 3070 :
		{
			message = "Dependente n&atilde;o pode participar do Sal\u00E1rio Fam\u00EDlia, pois o mesmo n&atilde;o &eacute; inv&acute;lido nem idade \u00E9 inferior ou igual a 14 anos.";
			break;
		}
		case 3071 :
		{
			message = "n&atilde;o &eacute; possivel ter mais de um endere\u00E7o residencial.";
			break;
		}
		case 3072 :
		{
			message = "n&atilde;o &eacute; possivel realizar a inclus\u00E3o, pois um mesmo conceito j&acute; existe para um per\u00EDodo concomitante.";
			break;
		}
		case 3073 :
		{
			message = "n&atilde;o &eacute; possivel realizar a inclus\u00E3o, pois existe vari\u00E1vel que n&atilde;o se encontra cadastrada no sistema.";
			break;
		}
		case 3074 :
		{
			message = "n&atilde;o &eacute; possivel rodar o processo, pois a &ccedil;&acirc;o est&atilde;o configurada para n&atilde;o ser realizada.";
			break;
		}
		case 3075 :
		{
			message = "ALERTA: existem benefici&acute;rios falecidos, por\u00E9m com benef\u00EDcios vigentes. Configure a &ccedil;&acirc;o para que execute o processo de atualiz&ccedil;&acirc;o desses benefici&acute;rios";
			break;
		}
		case 3076 :
		{
			message = "ALERTA: existem benef\u00EDcios com benefici&acute;rios que excedem 21 anos mas inferior a 70 anos.";
			break;
		}
		case 3077 :
		{
			message = "ALERTA: existem benef\u00EDcios com documentos j&acute; vencidos. ";
			break;
		}
		case 3078 :
		{
			message = "ALERTA: existem benef\u00EDcios que seus documentos obrigat\u00F3rios pendentes/desatualizados.";
			break;
		}
		case 3079 :
		{
			message = "n&atilde;o foi poss&iacute;vel localizar o cargo atual";
			break;
		}
		case 3080 :
		{
			message = "Data de fim de efeito n&atilde;o pode ser superior \u00E9 data de promo&ccirc;&atilde;o";
			break;
		}
		case 3081 :
		{
			message = "n&atilde;o h&acute; periodo de processamento da folha aberto.";
			break;
		}
		case 3082 :
		{
			message = "n&atilde;o foi poss&iacute;vel localizar a classe respons&acute;vel.";
			break;
		}
		case 3083 :
		{
			message = "n&atilde;o foi poss&iacute;vel localizar o componente respons&acute;vel.";
			break;
		}
		case 3084 :
		{
			message = "n&atilde;o foi poss&iacute;vel fabricar o componente respons&acute;vel.";
			break;
		}
		case 3085 :
		{
			message = "n&atilde;o foi poss&iacute;vel fabricar o componente respons&acute;vel, n&atilde;o &eacute; um bean correto.";
			break;
		}
		case 3086 :
		{
			message = "Periodo j&acute; cadastrado para processamento da folha, n&atilde;o &eacute; poss&iacute;vel gravar novos registros para o mesmo.";
			break;
		}
		case 3087 :
		{
			message = "Registro n&atilde;o localizado";
			break;
		}
		case 3088 :
		{
			message = "Sigla do tipo do arquivo n&atilde;o v&acute;lida!";
			break;
		}
		case 3089 :
		{
			message = "Tipo do arquivo n&atilde;o v&acute;lido!";
			break;
		}
		case 3090 :
		{
			message = "Nome de arquivo fora das especifica\u00E7\u00F5es do layout!";
			break;
		}
		case 3092 :
		{
			message = "n&atilde;o existe arquivo para ser processado!";
			break;
		}
		case 3093 :
		{
			message = "n&atilde;o existe per\u00EDodo aberto para processamento da carga do arquivo.";
			break;
		}
		case 3094 :
		{
			message = "J&acute; foi anexado um arquivo com mesmo nome.";
			break;
		}
		case 3095 :
		{
			message = "n&atilde;o foi possivel ler conte\u00FAdo do arquivo.";
			break;
		}
		case 3096 :
		{
			message = "j&acute; foi anexado um arquivo com mesmo header.";
			break;
		}
		case 3097 :
		{
			message = "Grupo de Pagamento n&atilde;o encontrado para o benefici&acute;rio desse tipo de tr&acirc;mite.";
			break;
		}

		case 20055 :
		{
			message = "n&atilde;o &eacute; poss&iacute;vel concomit&acirc;ncia entre datas!";
			break;
		}
		case 20056 :
		{
			message = "Esta opera&ccirc;&atilde;o n&atilde;o &eacute; poss&iacute;vel, existe registro aberto anterior!";
			break;
		}
		default :
		{
			//Caso o erro n&atilde;o seja encontrado nos cases acima,
			//o mesmo ser\u00E1 procurado no banco de dados
			//na tabela PARERR.
			try {
				Erros erros = new Erros();
				ErrosDAO errosDAO = new ErrosDAO();

				erros.setCodIns("0");
				erros.setCodErro(String.valueOf(s));

				erros = errosDAO.findByPrimaryKey(erros);

				if (erros != null) {
					message = erros.getDesErro();
				}
				else {
					message = "Um erro ocorreu.";
				}
			}
			catch (Exception e) {
				message = "Erro ao tentar acessar a tabela de erros 'PARERR' no banco de dados.";
			}
		}
		}

		return message;

	}

	/**
	 * Retorna a mensagem de erro
	 * @return a mensagem de erro
	 */
	public String getMessage() {
		if (campo.equals("")) {
			return (errorNumber==0?"":(String.valueOf(errorNumber) + " - ")) + this.message;
		} else {
			return (errorNumber==0?"":(String.valueOf(errorNumber) + " - ")) + this.message + " (" + campo + ")";
		}

	}

	/**
	 * Retorna o c\u00F3digo da mensagem de erro
	 * @return o c\u00F3digo da mensagem de erro
	 */
	public int getErrorCode() {
		return errorNumber;
	}

	public void add(String message) {
		listaDeErros.add(message);
	}

	public void add(int codeMessage) {
		listaDeErros.add(getMsg(codeMessage));
	}

	public void add(int codeMessage, String additional) {
		listaDeErros.add(getMsg(codeMessage) + " (" + additional + ")");
	}

	public Collection getErrors() {
		return listaDeErros;
	}
}