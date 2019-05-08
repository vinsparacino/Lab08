package it.polito.tdp.dizionariograph.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

	/*
	 * Ritorna tutte le parole di una data lunghezza
	 */
	public List<String> getAllWordsFixedLength(int length) {

		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ?;";
		List<String> parole = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, length);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				parole.add(res.getString("nome"));
			}

			conn.close();
			return parole;
			

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database");
		}
		
	}

	public List<String> getAllSimilarWords(String parola, int numeroLettere) {
		Connection conn = ConnectDB.getConnection();
		String sql = "SELECT nome FROM parola WHERE nome LIKE ? AND LENGTH(nome) = ?;";
		PreparedStatement st;
		
		try {
			List<String> parole = new ArrayList<String>();
			char[] parolaOriginale = parola.toCharArray();
			
			System.out.println(parola);
			
			for(int i = 0; i< parola.length(); i++) {
				
				char temp = parolaOriginale[i];
				parolaOriginale[i] = '_';
				String parolaDaCercare = String.copyValueOf(parolaOriginale);
				parolaOriginale[i] = temp;
				
				System.out.println(parolaDaCercare);
				
				st = conn.prepareStatement(sql);
				st.setString(1, parolaDaCercare);
				st.setInt(2, numeroLettere);
				ResultSet res = st.executeQuery();
				
				while (res.next()) {
					String nextWord = res.getString("nome");
					if(parola.compareToIgnoreCase(nextWord) !=0)
						parole.add(nextWord);
				}
			}
			conn.close();
			return parole;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connesione al database");
			throw new RuntimeException("Error Connection Database");
		}
	
	}

}
