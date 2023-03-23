import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.*;
import java.util.Base64;

import javax.swing.*;


public class Interface {

	public static String Signature_DSA(String Texte, KeyPair keyPair) throws Exception {

		// Créer une signature DSA
		Signature dsa = Signature.getInstance("SHA256withDSA");
		dsa.initSign(keyPair.getPrivate());
		dsa.update(Texte.getBytes("UTF-8"));
		byte[] signature = dsa.sign();
		

		// Convertir la signature en base64 pour l'affichage
		String encodedSignature = Base64.getEncoder().encodeToString(signature);
		return encodedSignature;
	}
	
	public static String Signature_RSA(String Texte, KeyPair keyPair) throws Exception {

		// Créer une signature DSA
		Signature rsa = Signature.getInstance("SHA256withRSA");
		rsa.initSign(keyPair.getPrivate());
		rsa.update(Texte.getBytes("UTF-8"));
		byte[] signature = rsa.sign();

		// Convertir la signature en base64 pour l'affichage
		String encodedSignature = Base64.getEncoder().encodeToString(signature);
		return encodedSignature;
	}
	
	public static boolean verifySignature_DSA(String data, byte[] signature, PublicKey publicKey) throws Exception {
	      // Créer une signature DSA
	      Signature dsa = Signature.getInstance("SHA256withDSA");
	      dsa.initVerify(publicKey);
	      dsa.update(data.getBytes("UTF-8"));

	      // Vérifier la signature
	      boolean verified = dsa.verify(signature);

	      return verified;
	}
	
	public static boolean verifySignature_RSA(String data, byte[] signature, PublicKey publicKey) throws Exception {
	      // Créer une signature DSA
	      Signature dsa = Signature.getInstance("SHA256withRSA");
	      dsa.initVerify(publicKey);
	      dsa.update(data.getBytes("UTF-8"));

	      // Vérifier la signature
	      boolean verified = dsa.verify(signature);

	      return verified;
	}

	public static void main(String[] args) throws Exception {
		
		JFrame Interface = new JFrame();
		Interface.setTitle("Test d'intégrité et d'authentification");
		Interface.setSize(400,330);
		Interface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel1 = new JPanel(), panel2 = new JPanel(), panel3 = new JPanel();
		JPanel panel11 = new JPanel(), panel12 = new JPanel();
		JPanel panel21 = new JPanel(), panel22 = new JPanel();
		JPanel panel31 = new JPanel(), panel311 = new JPanel(), panel32 = new JPanel();
		
		JLabel resultat = new JLabel("Texte indisponible");
		
		JButton signer = new JButton("Signer"), verifier = new JButton("Vérifier");
		JButton reset1 = new JButton("Reset"), reset2 = new JButton("Reset");
		
		JTextField Texte = new JTextField(250), SignatureTexte = new JTextField(600);
		
		String[] signatures = {"DSA","RSA"};
		JComboBox<String> signature = new JComboBox<>(signatures);
		
		panel1.setPreferredSize(new Dimension(200,100));
		panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,2),"Texte à signer"));
		
		panel2.setPreferredSize(new Dimension(200,100));
		panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,2),"Signature "));
		
		panel11.setPreferredSize(new Dimension(300,100));
		panel12.setPreferredSize(new Dimension(80,100));
		
		panel21.setPreferredSize(new Dimension(290,100));
		panel22.setPreferredSize(new Dimension(90,100));
		
		panel3.setPreferredSize(new Dimension(200,100));
		panel31.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,2),"Resultat"));
		panel32.setPreferredSize(new Dimension(120,100));
		panel32.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,2),"Algorithme"));
		
		panel1.setLayout(new BorderLayout());
		panel1.add(panel11, BorderLayout.WEST);
		panel1.add(panel12,BorderLayout.EAST);
		panel12.add(signer);
		panel12.add(reset1);
		panel11.setLayout(new BorderLayout());
		panel11.add(Texte,BorderLayout.CENTER);
		panel21.setLayout(new BorderLayout());
		panel21.add(SignatureTexte, BorderLayout.CENTER);
		panel22.add(verifier);
		panel22.add(reset2);
		panel2.setLayout(new BorderLayout());
		panel2.add(panel21, BorderLayout.WEST);
		panel2.add(panel22, BorderLayout.EAST);
		panel3.setLayout(new BorderLayout());
		panel32.add(signature);
		panel31.setLayout(new BorderLayout());
		panel311.add(resultat, BorderLayout.CENTER);
		panel31.add(panel311, BorderLayout.CENTER);
		panel3.add(panel31, BorderLayout.CENTER);
		panel3.add(panel32, BorderLayout.EAST);
		Interface.setLayout(new BorderLayout());
		Interface.add(panel1,BorderLayout.NORTH);
		Interface.add(panel2,BorderLayout.CENTER);
		Interface.add(panel3,BorderLayout.SOUTH);
		
		reset1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				Texte.setText("");
			}
		});
		
		reset2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				SignatureTexte.setText("");
			}
		});
		
		//DSA
		KeyPairGenerator keyGen_DSA = KeyPairGenerator.getInstance("DSA");
	    SecureRandom random1 = SecureRandom.getInstanceStrong();
	    keyGen_DSA.initialize(1024, random1);
	    KeyPair keyPair_DSA = keyGen_DSA.generateKeyPair();
	    
	    //RSA
	    KeyPairGenerator keyGen_RSA = KeyPairGenerator.getInstance("RSA");
	    SecureRandom random2 = SecureRandom.getInstanceStrong();
	    keyGen_RSA.initialize(1024, random2);
	    KeyPair keyPair_RSA = keyGen_RSA.generateKeyPair();
		
		signer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				String texte = Texte.getText();
				if (signature.getSelectedItem().toString().equalsIgnoreCase("DSA")) {
					try {
						String Signature_texte = Signature_DSA(texte, keyPair_DSA);
						SignatureTexte.setText(Signature_texte);
					}
					catch(Exception e){
						
					}
				}
				else {
					try {
						String Signature_texte = Signature_RSA(texte, keyPair_RSA);
						SignatureTexte.setText(Signature_texte);
					}
					catch(Exception e){
						
					}
				}
			}
		});
		
		verifier.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				String texte = Texte.getText();
				String Sign = SignatureTexte.getText();
				if (signature.getSelectedItem().toString().equalsIgnoreCase("DSA")) {
					try {
						if (verifySignature_DSA(texte, Base64.getDecoder().decode(Sign), keyPair_DSA.getPublic())) {
							resultat.setText("Vérification réussie de la signature");
						}
						else{
							resultat.setText("La signature n'est pas vérifiée");
						}
					}
					catch(Exception e){
						resultat.setText("Texte indisponible");
					}
				}
				else {
					try {
						if (verifySignature_RSA(texte, Base64.getDecoder().decode(Sign), keyPair_RSA.getPublic())) {
							resultat.setText("Vérification réussie de la signature");
						}
						else{
							resultat.setText("La signature n'est pas vérifiée");
						}
					}
					catch(Exception e){
						resultat.setText("Texte indisponible");
					}
				}
			}
		});
		
        Interface.setLocationRelativeTo(null);
        Interface.setVisible(true);
	}

}
