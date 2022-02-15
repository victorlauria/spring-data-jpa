package br.com.atlantic.comum.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe de criptografia
 * 
 * @version 1.00
 * @author Raphael de Oliveira Silva
 */

public class Crypt
{

    int rc4_y;
    int rc4_x;
    byte rc4_state[];


    //MD5
    private MessageDigest md = null;   

    /**
     * @param texto
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String enCryptMD5(String texto) throws NoSuchAlgorithmException {
    	String hash;
    	if (md==null) md = MessageDigest.getInstance( "MD5" );
        md.update( texto.getBytes() );   
        hash = new BigInteger( 1, md.digest() ).toString( 16 );
        
        if (hash.length()<32) {
        	hash = ("00000000000000000000000000000000" + hash).substring(32 + hash.length() - 32);
        }
        return (hash.toUpperCase());
    }
    
    public Crypt()
    {
    }

    private String toString(byte abyte0[]) throws IOException
    {
        StringBuffer stringbuffer = new StringBuffer();
        int i = 0;
        for(int j = abyte0.length; i < j; i++)
            stringbuffer.append(hexToAscii(abyte0[i] >>> 4 & 0xf)).append(hexToAscii(abyte0[i] & 0xf));

        return stringbuffer.toString();
    }

    private int setkey(String s)
    {
        byte abyte0[] = s.getBytes();
        rc4_state = new byte[256];
        for(int i = 0; i < 256; i++)
            rc4_state[i] = (byte)i;

        rc4_x = 0;
        rc4_y = 0;
        int k = 0;
        int l = 0;
        for(int j = 0; j < 256; j++)
        {
            l = ((abyte0[k] & 0xff) + (rc4_state[j] & 0xff) + l) % 256;
            int i1 = rc4_state[j];
            rc4_state[j] = rc4_state[l];
            rc4_state[l] = (byte)i1;
            k = (k + 1) % abyte0.length;
        }

        return 0;
    }

    private byte[] rc4encode(byte abyte0[])
    {
        byte abyte1[] = new byte[abyte0.length];
        System.arraycopy(abyte0, 0, abyte1, 0, abyte0.length);
        int i = rc4_x;
        int j = rc4_y;
        for(int l = 0; l < abyte0.length; l++)
        {
            i = (i + 1) % 256;
            j = ((rc4_state[i] & 0xff) + j) % 256;
            int i1 = rc4_state[i];
            rc4_state[i] = rc4_state[j];
            rc4_state[j] = (byte)i1;
            int k = ((rc4_state[i] & 0xff) + rc4_state[j] & 0xff) % 256;
            abyte1[l] = (byte)(abyte1[l] & 0xff ^ rc4_state[k] & 0xff);
        }

        rc4_x = i;
        rc4_y = j;
        return abyte1;
    }

    private static char hexToAscii(int i)
    {
        if(i >= 10 && i <= 15)
            return (char)(65 + (i - 10));
        if(i >= 0 && i <= 9)
            return (char)(48 + i);
        else
            throw new Error("hex to ascii failed");
    }

    private byte[] fromString(String s)
    {
        int i = s.length();
        int j = 0;
        byte abyte0[] = new byte[(i + 1) / 2];
        if(i % 2 == 1)
        {
            abyte0[0] = (byte)asciiToHex(s.charAt(0));
            j = 1;
            i--;
        }
        int k = j;
        for(; i > 0; i -= 2)
            abyte0[j++] = (byte)(asciiToHex(s.charAt(k++)) << 4 | asciiToHex(s.charAt(k++)));

        return abyte0;
    }

    /**
     * M�todo que faz a criptografia
     * @param String s que � o valor a ser criptografado
     * @param String s1 que � a chave da criptografia
     * @return String criptografada
     */
	public String enCrypt(String s, String s1) throws IOException
    {
        String s2 = "ERROR";
        setkey(s1);
        byte abyte0[] = rc4encode(s.getBytes());
        s2 = toString(abyte0);
        return s2;
    }

	/**
     * M�todo que faz a decriptografia
     * @param String s � o valor a ser decriptografado
     * @param String s1 � a chave da decriptografia
     * @return String decriptografada
     */
    public String deCrypt(String s, String s1)
    {
        String s2 = "ERROR";
        byte abyte0[] = fromString(s);
        setkey(s1);
        s2 = new String(rc4encode(abyte0));
        return s2;
    }

    private static final int asciiToHex(char c)
    {
        if(c >= 'a' && c <= 'f')
            return (c - 97) + 10;
        if(c >= 'A' && c <= 'F')
            return (c - 65) + 10;
        if(c >= '0' && c <= '9')
            return c - 48;
        else
            throw new Error("ascii to hex failed");
    }
    
    public static void main (String[] args) {
	Crypt crypt = new Crypt();
	System.out.println(crypt.deCrypt("3D3BAC1CFEB41EF0FB42","sonda"));
    }
    
}
