package com.barclays.bcag;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.cryptomathic.hsmportal.client.common.exceptions.InvalidKeyStoreException;
import com.cryptomathic.hsmportal.client.common.exceptions.RejectedExecutionException;
import com.cryptomathic.hsmportal.client.java.ClientApi;
import com.cryptomathic.hsmportal.client.java.ClientApis;
import com.cryptomathic.hsmportal.client.java.datatype.CipherResult;
import com.cryptomathic.hsmportal.util.Hex;

public class BCAGClient {

    private static final String DISK = "disk";
    private static final String CUSTOMERDATA = "customerdata";
    private static final String UG_ENCRYPT = "UG_Encrypt";
    private static final String UN = "UN";
    private static final String KEY = "KEY";
    private static final String SERVERS = "SERVERS";
    private static final String KEYSTORE = "KEYSTORE";
    private static final String KEYSTORE_KEY = "KEYSTOREKEY";
    private static final String ENCRYPT_LOGIC = "ENCRYPTLOGIC";
    private static final String DECRYPT_LOGIC = "DECRYPTLOGIC";

    private ClientApi bcagclient;
    private static Map<String, BCAGClient> map = new HashMap<String, BCAGClient>();

    private KeyStore keyStore = null;
    private char[] keystorePwdCharArray = null;
    List<String> serverIPPortList = new ArrayList<String>();
    private char[] bcagUserID = null;
    private char[] bcagPwd = null;
    private String encryptLogic;
    private String decryptLogic;
    private String filename;

    private static final Logger LOGGER = Logger.getLogger(BCAGClient.class);

    private BCAGClient(Properties properties) throws Exception {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Setting the properties");
	}
	 LOGGER.info("Setting the properties");
	 System.out.println("Setting the properties");
	serverIPPortList = addServerstoServerIPList(properties.getProperty(SERVERS));
	bcagUserID = properties.getProperty(UN).toCharArray();
	bcagPwd = properties.getProperty(KEY).toCharArray();
	filename = properties.getProperty(KEYSTORE);
	keystorePwdCharArray = properties.getProperty(KEYSTORE_KEY).toCharArray();
	encryptLogic = properties.getProperty(ENCRYPT_LOGIC);
	decryptLogic = properties.getProperty(DECRYPT_LOGIC);

//	if (Thread.currentThread().getContextClassLoader().getResource(filename) != null) {
//		if (LOGGER.isDebugEnabled()) {
//		    LOGGER.debug("BCAGClient(properties) found the keystore file successfully");
//		}
//		 LOGGER.info("BCAGClient(properties) found the keystore file successfully");
//		 System.out.println("BCAGClient(properties) found the keystore file successfully");
//	    String keyStoreFilename = Thread.currentThread().getContextClassLoader().getResource(filename).getFile();
//	    keyStore = getKeyStore(keyStoreFilename, keystorePwdCharArray);
//	} else {
		if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("BCAGClient(properties) fetching the keystore from classpath");
		}
		System.out.println("BCAGClient --> filename:: "+filename);
		 LOGGER.info("BCAGClient(properties) fetching the keystore from classpath");
		 System.out.println("BCAGClient(properties) fetching the keystore from classpath");
	    keyStore = getKeyStore(filename, keystorePwdCharArray);
//	}

	bcagclient = getBCAGClient(serverIPPortList);
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("BCAGClient(properties) after getBCAGClient(serverIPPortList)");
	}
	 LOGGER.info("BCAGClient(properties) after getBCAGClient(serverIPPortList)");
	 System.out.println("BCAGClient(properties) after getBCAGClient(serverIPPortList)");
	logon();
    }

    private List<String> addServerstoServerIPList(String serverString) {

	String[] serverIPNPortArray = serverString.split(",");
	if (serverIPPortList != null && serverIPPortList.size() == 0) {
	    for (String serverIPNPort : serverIPNPortArray) {
		serverIPPortList.add(serverIPNPort);
	    }
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("BCAGClient addServerstoServerIPList size:"+serverIPPortList.size());
	}
	 LOGGER.info("BCAGClient addServerstoServerIPList size:"+serverIPPortList.size());
	 System.out.println("BCAGClient addServerstoServerIPList size:"+serverIPPortList.size());
	return serverIPPortList;

    }

    /**
     * This static method needs to be called to get a instance of BCAGClient. It is mandatory to pass either the name of a .properties file or an
     * instance of <link>java.util.Properties</link> which has all the details needed to create the BCAGClient.
     *
     * @param encryptionType
     * @param propertiesFile
     * @param properties
     * @return
     * @throws Exception
     */
    public static BCAGClient getClient(String encryptionType, String propertiesFile, Properties properties) throws Exception {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Get the BCAG client propertiesFile"+propertiesFile);
	}
	LOGGER.info("Get the BCAG client propertiesFile"+propertiesFile);
	System.out.println("Get the BCAG client propertiesFile"+propertiesFile);
	BCAGClient client = map.get(encryptionType);
	if (client == null) {
	    if (properties == null) {
	    	if (LOGGER.isDebugEnabled()) {
	    	    LOGGER.debug("BCAGClient getClient properties,client null");
	    	}
	    	LOGGER.info("BCAGClient getClient properties,client null");
	    	System.out.println("BCAGClient getClient properties,client null");
		properties = new Properties();
		FileInputStream in = new FileInputStream(propertiesFile);
		properties.load(in);
		in.close();
	    }
	    if (LOGGER.isDebugEnabled()) {
    	    LOGGER.debug("BCAGClient before getClient before client populated successfully ");
    	}
    	LOGGER.info("BCAGClientgetClient before client populated successfully");
    	System.out.println("BCAGClientgetClient before client populated successfully");
	    client = new BCAGClient(properties);
	    map.put(encryptionType, client);
	    if (LOGGER.isDebugEnabled()) {
    	    LOGGER.debug("BCAGClient after getClient after client populated successfully ");
    	}
    	LOGGER.info("BCAGClientgetClient after client populated successfully");
    	System.out.println("BCAGClientgetClient after client populated successfully");
	}
	return client;
    }

    private KeyStore getKeyStore(String filenm, char[] keystorePwdCharArray) throws KeyStoreException, NoSuchAlgorithmException,
	    CertificateException, IOException {
    	if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Entry BCAGClient getKeyStore()fetching the keystore from classpath");
		}
		 LOGGER.info("Entry BCAGClient getKeyStore() fetching the keystore from classpath");
		System.out.println("Entry BCAGClient getKeyStore() fetching the keystore from classpath");
	KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
	InputStream resourceAsStream = null;
	System.out.println("getKeyStore --> filenm:: "+filenm);
	try {

	    resourceAsStream = BCAGClient.class.getResourceAsStream(filenm);
	    if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("BCAGClient getKeyStore() after getResourceAsStream");
		}
		 LOGGER.info("BCAGClient getKeyStore() after getResourceAsStream");
		System.out.println("BCAGClient getKeyStore() after getResourceAsStream");
	    ks.load(resourceAsStream, keystorePwdCharArray);
	    if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("BCAGClient getKeyStore() kestore loaded successfully");
		}
		 LOGGER.info("BCAGClient getKeyStore() kestore loaded successfully");
		System.out.println("BCAGClient getKeyStore() kestore loaded successfully");
	} catch (Exception e) {
	    LOGGER.error(e.getMessage());

	} finally {
	    if (resourceAsStream != null) {
		resourceAsStream.close();
	    }
	}
	 if (LOGGER.isDebugEnabled()) {
		    LOGGER.debug("Exit BCAGClient getKeyStore()");
		}
		 LOGGER.info("Exit BCAGClient getKeyStore()");
		System.out.println("Exit BCAGClient getKeyStore()");
	return ks;
    }

    public ClientApi getBCAGClient(List<String> serverIPList) throws InvalidKeyStoreException, KeyStoreException, NoSuchAlgorithmException,
	    CertificateException, IOException {
	ClientApi bcagclient = ClientApis.clientApi();
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Entry BCAGClient getBCAGClient(serverIPList)");
	}
	 LOGGER.info("Entry BCAGClient getBCAGClient(serverIPList)");
	System.out.println("Entry BCAGClient getBCAGClient(serverIPList)");
	String serverIP = "";
	int portNumber = 0;
	for (String serverIPandPort : serverIPList) {
	    serverIP = serverIPandPort.split(":")[0];
	    portNumber = Integer.valueOf(serverIPandPort.split(":")[1]);
	    bcagclient.addServer(serverIP, serverIP, portNumber, 1, keyStore, keystorePwdCharArray);
	}
	bcagclient.setDefaultTimeout(3000);
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Exit BCAGClient getBCAGClient(serverIPList)");
	}
	 LOGGER.info("Exit BCAGClient getBCAGClient(serverIPList)");
	System.out.println("Exit BCAGClient getBCAGClient(serverIPList)");
	return bcagclient;
    }

    public void logon() throws Exception {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("LOGGING ON TO BCAG");
	}
	LOGGER.info("LOGGING ON TO BCAG");
	System.out.println("LOGGING ON TO BCAG");

	bcagclient.logon(String.valueOf(bcagUserID), String.valueOf(bcagPwd));

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("exit LOGGING ON TO BCAG");
	}
	LOGGER.info("exit LOGGING ON TO BCAG");
	System.out.println("exit LOGGING ON TO BCAG");
    }

    private String encrypt(String plainText, int counter) throws Exception {
	String encryptedValue = null;

	if (counter <= 2) {
	    try {
		CipherResult result;
		if (UG_ENCRYPT.equals(encryptLogic)) {
		    result = bcagclient.encrypt(encryptLogic, CUSTOMERDATA, DISK, Hex.decode(plainText));
		} else {
		    result = bcagclient.encrypt(encryptLogic, CUSTOMERDATA, DISK, plainText.getBytes());
		}
		byte[] cipherD = result.getData();
		encryptedValue = Hex.encode(cipherD);
	    } catch (RejectedExecutionException e) {
		LOGGER.error(e.getMessage());
		throw e;
	    } catch (Exception e) {
		LOGGER.error(e.getMessage());

	    }
	}
	return encryptedValue;
    }

    public String encrypt(String plainText) throws Exception {
	String encryptedValue = null;
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Entry BCAGClient encrypt");
	}
	LOGGER.info("Entry BCAGClient encrypt");
	System.out.println("Entry BCAGClient encrypt");

	try {
	    encryptedValue = encrypt(plainText, 1);
	} catch (RejectedExecutionException e) {
		LOGGER.error(e.getMessage());
	    bcagclient = getBCAGClient(serverIPPortList);
	    logon();
	} finally {
	    if (encryptedValue == null) {
		encryptedValue = encrypt(plainText, 2);
	    }
	}
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Exit BCAGClient encrypt encryptedValue:"+encryptedValue);
	}
	LOGGER.info("Exit BCAGClient encrypt encryptedValue:"+encryptedValue);
	System.out.println("Exit BCAGClient encrypt encryptedValue:"+encryptedValue);
	return encryptedValue;
    }

    private String decrypt(String encryptedText, int counter) throws Exception {
	String plainText = null;

	if (counter <= 2) {
	    try {
		byte[] encryptedBytes = Hex.decode(encryptedText);
		byte[] d = bcagclient.decrypt(decryptLogic, CUSTOMERDATA, DISK, encryptedBytes);
		plainText = new String(d);
	    } catch (RejectedExecutionException e) {
		LOGGER.error(e.getMessage());
		throw e;
	    } catch (Exception e) {
		LOGGER.error(e.getMessage());
	    }
	}

	return plainText;
    }

    public String decrypt(String encryptedText) throws Exception {
	String plainText = null;

	try {
	    plainText = decrypt(encryptedText, 1);
	} catch (RejectedExecutionException e) {
	    bcagclient = getBCAGClient(serverIPPortList);
	    logon();
	} finally {
	    if (plainText == null) {
		plainText = decrypt(encryptedText, 2);
	    }
	}

	return plainText;
    }

    @Override
    protected void finalize() throws Throwable {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Shutdown BCAG connection.");
	}
	bcagclient.shutdown();
	super.finalize();
    }

    public void close() {
	try {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Shutdown BCAG connection.");
	    }
	    bcagclient.shutdown();
	} catch (Exception e) {
	    LOGGER.error(e.getMessage());
	}
    }

}
