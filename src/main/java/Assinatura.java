import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Assinatura {

	static String certAlias;
	static String certPassword;
	
	public static String getAssinatura(String xml) throws SQLException {

		try {
			getProp();
			XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
			List<Transform> transforms = new ArrayList<>();
			transforms.add(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null));
			transforms.add(
					fac.newTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (TransformParameterSpec) null));

			Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA256, null), transforms, null,
					null);

			// Create the SignedInfo.
			SignedInfo si = fac.newSignedInfo(
					fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
					fac.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256",
							(SignatureMethodParameterSpec) null),
					Collections.singletonList(ref));

//			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore ks = KeyStore.getInstance("PKCS12");
			InputStream readStream = new FileInputStream("C:\\development\\Esocial\\Certificados\\spprev2021.pfx");
			//InputStream readStream = new FileInputStream("/opt/configurations/security/keystores/spprev_a1.pfx");
			ks.load(readStream, certPassword.toCharArray());
//			Key key = ks.getKey("keyAlias", null);
			readStream.close();
			Enumeration<String> aliases = ks.aliases();
			while(aliases.hasMoreElements()){
				String alias = aliases.nextElement();
		        if(ks.getCertificate(alias).getType().equals("X.509")){
		        	System.out.println(alias + " expires " + ((X509Certificate) ks.getCertificate(alias)).getNotAfter());
		        }
			}
			KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(certAlias,
					new KeyStore.PasswordProtection(certPassword.toCharArray()));

			X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

			KeyInfoFactory kif = fac.getKeyInfoFactory();
			List<Serializable> x509Content = new ArrayList<Serializable>();
			// x509Content.add(cert.getSubjectX500Principal().getName());
			x509Content.add(cert);
			X509Data xd = kif.newX509Data(x509Content);
			KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			Document doc = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(xml.toString())));

			DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement());

			XMLSignature signature = fac.newXMLSignature(si, ki);

			signature.sign(dsc);

			StringWriter xmlAssinado = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			transformer.transform(new DOMSource(doc), new StreamResult(xmlAssinado));
//			validateSign(doc, fac);
			return xmlAssinado.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return e.getClass() + " - " + e.getCause() + " - " + e.getMessage();
		}

	}



	private static void validateSign(Document doc, XMLSignatureFactory fac) throws Exception {

		// Find Signature element.
		NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
		if (nl.getLength() == 0) {
			throw new Exception("Cannot find Signature element");
		}

		// Create a DOMValidateContext and specify a KeySelector
		// and document context.
		DOMValidateContext valContext = new DOMValidateContext(new X509KeySelector(), nl.item(0));

		// Unmarshal the XMLSignature.
		XMLSignature signature = fac.unmarshalXMLSignature(valContext);

		// Validate the XMLSignature.
		boolean coreValidity = signature.validate(valContext);

		// Check core validation status.
		if (coreValidity == false) {
			System.err.println("Signature failed core validation");
			boolean sv = signature.getSignatureValue().validate(valContext);
			System.out.println("signature validation status: " + sv);
			if (sv == false) {
				// Check the validation status of each Reference.
				Iterator i = signature.getSignedInfo().getReferences().iterator();
				for (int j = 0; i.hasNext(); j++) {
					boolean refValid = ((Reference) i.next()).validate(valContext);
					System.out.println("ref[" + j + "] validity status: " + refValid);
				}
			}
		} else {
			System.out.println("Signature passed core validation");
		}

		valContext.setProperty("javax.xml.crypto.dsig.cacheReference", Boolean.TRUE);
		// Unmarshal the XMLSignature.
		XMLSignature signature1 = fac.unmarshalXMLSignature(valContext);
		// Validate the XMLSignature.
		boolean coreValidity1 = signature1.validate(valContext);

		Iterator i = signature1.getSignedInfo().getReferences().iterator();
		for (int j = 0; i.hasNext(); j++) {
			InputStream is = ((Reference) i.next()).getDigestInputStream();
			// Display the data.
		}

	}
	
    static void getProp() throws IOException {
		Properties props = new Properties();
//		FileInputStream file = new FileInputStream("D:\\Esocial\\workspace_assinaturas\\AssinaturaWS\\config.properties");
//		FileInputStream file = new FileInputStream("/opt/configurations/esocial/config.properties");
//		props.load(file);

		certAlias = "SAO_PAULO_PREVIDENCIA_SPPREV_09041213000136.p12";
		certPassword = "spprev2021";

//		certAlias = "698a987f-f8c4-4e28-9cdd-d25393c0029a"; //IPREV
//		certPassword = "junior123";
		
//		certAlias = "0c1b5152-6819-4bc4-9c22-868f9de17b3f"; //CAMPREV
//		certPassword = "camprev30062004";
		
		
	}
}

class X509KeySelector extends KeySelector {
    public KeySelectorResult select(KeyInfo keyInfo,
                                    KeySelector.Purpose purpose,
                                    AlgorithmMethod method,
                                    XMLCryptoContext context)
        throws KeySelectorException {
        Iterator ki = keyInfo.getContent().iterator();
        while (ki.hasNext()) {
            XMLStructure info = (XMLStructure) ki.next();
            if (!(info instanceof X509Data))
                continue;
            X509Data x509Data = (X509Data) info;
            Iterator xi = x509Data.getContent().iterator();
            while (xi.hasNext()) {
                Object o = xi.next();
                if (!(o instanceof X509Certificate))
                    continue;
                final PublicKey key = ((X509Certificate)o).getPublicKey();
                // Make sure the algorithm is compatible
                // with the method.
                if (algEquals(method.getAlgorithm(), key.getAlgorithm())) {
                    return new KeySelectorResult() {
                        public Key getKey() { return key; }
                    };
                }
            }
        }
        throw new KeySelectorException("No key found!");
    }

    static boolean algEquals(String algURI, String algName) {
        if ((algName.equalsIgnoreCase("DSA") &&
            algURI.equalsIgnoreCase(SignatureMethod.DSA_SHA1)) ||
            (algName.equalsIgnoreCase("RSA") &&
            algURI.equalsIgnoreCase(SignatureMethod.RSA_SHA1))) {
            return true;
        } else {
            return false;
        }
    }

}
