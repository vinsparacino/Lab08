package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {

	private Graph<String, DefaultEdge> graph;
	private int numLettere = 0;
	private WordDAO wordDao;
	
	public Model() {
		wordDao = new WordDAO();
	}
	public List<String> createGraph(int numeroLettere) {

		graph = new SimpleGraph<>(DefaultEdge.class);
		this.numLettere = numeroLettere;
		
		List<String> parole = wordDao.getAllWordsFixedLength(numeroLettere);
		
		//aggiungo tutti i vertici del grafo
		for(String parola : parole)
			graph.addVertex(parola);
		
		//per ogni parola aggiungo un arco di collegamento con 
		//le paroel che differiscono per una sola lettera (stessa lunghezza)
		/*for(String parola : parole) {
			//Alternativa 1: uso il database (soluz LENTA)
			List<String> paroleSimili = wordDao.getAllSimilarWords(parola, numeroLettere);
            //Creo l'arco
			for(String parolaSimile : paroleSimili) {
				graph.addEdge(parola, parolaSimile);
			}
			
		}*/
		
		for(String s : parole) {
			for(String p : parole) {
				if(s.equals(p)) continue;
				int diff = 0;
				for(int i = 0; i<s.length(); i++) {
					if(s.charAt(i) != p.charAt(i))
						diff++;
					if(diff>1) break;
				}
				if(diff == 1) {
					graph.addEdge(s, p);
				}
			}
		}
		//ritorno la lista di vertici
		return parole;
	}

	public List<String> displayNeighbours(String parolaInserita) {

		if(numLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero inserito");
		
		List<String> parole = new ArrayList<String>();
		
		//ottengo la lista dei vicini di un vertice
		parole.addAll(Graphs.neighborListOf(graph, parolaInserita));
		
		//ritorno la lista di vertici
		return parole;
		
		
	}

	public String findMaxDegree() {
		int max = 0;
		String temp = null;
		
		for(String vertex : graph.vertexSet()) {
			if(graph.degreeOf(vertex) > max) {
				max = graph.degreeOf(vertex);
				temp = vertex;
			}
		}
		if(max != 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("Max degree: %d from vertex: %s\n", max, temp));
			
			for(String v : Graphs.neighborListOf(graph, temp))
				sb.append(v + "\n");
			
			return sb.toString();
			
		}
		return "Non trovato.";
	}
}
