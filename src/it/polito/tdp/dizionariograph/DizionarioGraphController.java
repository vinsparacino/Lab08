
	package it.polito.tdp.dizionariograph;

	import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.TextArea;
	import javafx.scene.control.TextField;

	public class DizionarioGraphController {
		
		private Model model;

	    @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private TextField txtLettere;

	    @FXML
	    private TextField txtParola;

	    @FXML
	    private Button btnGrafo;

	    @FXML
	    private Button btnVicini;

	    @FXML
	    private Button btnGradoMax;

	    @FXML
	    private TextArea txtResult;

	    @FXML
	    private Button btnReset;

	    @FXML
	    void doReset(ActionEvent event) {

	    	txtResult.clear();
	    	txtLettere.clear();
	    	txtParola.clear();
	    	txtLettere.setDisable(false);
	    	txtParola.setDisable(true);
	    	btnGrafo.setDisable(false);
	    	btnVicini.setDisable(true);
	    	btnGradoMax.setDisable(true);
	    }

	    @FXML
	    void generaGrafo(ActionEvent event) {

	    	txtResult.clear();
	    	txtParola.clear();
	    	try {
	    		int numeroLettere = Integer.parseInt(txtLettere.getText());
	    		System.out.println("NUmero Lettere: " + numeroLettere);
	    		
	    		List<String> parole = model.createGraph(numeroLettere);
	    		
	    		if(parole != null) {
	    			txtResult.setText("Trovate " + parole.size() + " -- parole di lunghezza: " + numeroLettere);
	    			}
	    		
	    		else {
	    			txtResult.setText("Trovate 0 parole di lunghezza:" + numeroLettere);
	    			}
	    		
	    		txtLettere.setDisable(true);
	    		txtParola.setDisable(false);
	    		btnGrafo.setDisable(true);
	    		btnVicini.setDisable(false);
	    		btnGradoMax.setDisable(false);
	    		
	    	} catch (NumberFormatException nfe) {
	    		txtResult.setText("Inserire un numero corretto di lettere!");
	    	} catch (RuntimeException re) {
	    		txtResult.setText(re.getMessage());
	    	}
	    	
	    }

	    @FXML
	    void trovaGrado(ActionEvent event) {

	    	try {
	    		txtResult.setText(model.findMaxDegree());
	    	} catch (RuntimeException re) {
	    		txtResult.setText(re.getMessage());
	    	}
	    	
	    }

	    @FXML
	    void trovaVicini(ActionEvent event) {

	    	try {
	    		String parolaInserita = txtParola.getText();
	    		if(parolaInserita == null || parolaInserita.length() == 0) {
	    			txtResult.setText("Inserire una parola da cercare");
	    			return;
	    		}
	    		
	    		List<String> parole = model.displayNeighbours(parolaInserita);
	    		if(parole != null) {
	    			txtResult.setText(parole.toString());
	    		} else {
	    			txtResult.setText("Non è stato trovato nessun risultato");
	    		}
	    	} catch (RuntimeException re) {
	    		txtResult.setText(re.getMessage());
	    	}
	    	
	    }

	    @FXML
	    void initialize() {
	        assert txtLettere != null : "fx:id=\"txtLettere\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
	        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
	        assert btnGrafo != null : "fx:id=\"btnGrafo\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
	        assert btnVicini != null : "fx:id=\"btnVicini\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
	        assert btnGradoMax != null : "fx:id=\"btnGradoMax\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
	        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
	        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
	        
	    }

		public void setModel(Model model) {
			this.model = model;
		}
	}


