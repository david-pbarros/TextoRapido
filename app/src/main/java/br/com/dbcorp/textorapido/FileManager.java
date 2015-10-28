package br.com.dbcorp.textorapido;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import android.app.Activity;
import android.os.Environment;
import android.widget.Toast;
import br.com.dbcorp.textorapido.db.DataBaseHelper;

public class FileManager {
	
	private File folder;
	private DataBaseHelper dbHelper;
	private Activity context;
	
	public FileManager() {
		this.creatFolder();
	}
	
	public FileManager(Activity context) {
		this.dbHelper = new DataBaseHelper(context);
		this.context = context;
		
		this.creatFolder();
	}
	
	public void export() {
		this.creatFolder();
		
		Toast.makeText(this.context, "Iniciando Exportação.", Toast.LENGTH_SHORT).show();
		
		for (File file : this.folder.listFiles()) {
			file.delete();
		}
		
		File arquivo = new File(this.folder.getAbsolutePath() + "/textos.csv");

		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(arquivo);
			
			pw.println("livro;capitulo;versiculos;texto;abreviacao;");
			
			for (TextoDTO texto : this.dbHelper.listaDeTextos(null)) {
				
				String valor = texto.getLivro().ordinal() + ";" + texto.getCapitulo() + ";" + this.trataTextoExport(texto.getVersiculos()) + ";" + 
						this.trataTextoExport(texto.getTexto()) + ";" + this.trataTextoExport(texto.getAbreviacao()) + ";";
				
				pw.println(valor);
			}
			
			arquivo.mkdirs();
			
		} catch (IOException e) {
			Toast.makeText(this.context, "Erro na Exportação.", Toast.LENGTH_LONG).show();
		
		} finally {
			this.dbHelper.close();
			
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}
		
		Toast.makeText(this.context, "Fim da Exportação.", Toast.LENGTH_LONG).show();
	}
	
	public void importTextos() {
		Toast.makeText(this.context, "Iniciando Importação.", Toast.LENGTH_SHORT).show();
		
		File arquivo = new File(this.folder.getAbsolutePath() + "/textos.csv");
		
		FileReader fr = null;
		BufferedReader bf = null;
		
		String temp = null;
		
		try {
			fr = new FileReader(arquivo);

			bf = new BufferedReader(fr);
			
			String linha = bf.readLine();
			
			while ((linha = bf.readLine()) != null) {
				String[] campos = linha.split(";");
				
				TextoDTO texto = new TextoDTO()
					.setLivro(LivrosBiblicos.values()[Integer.parseInt(campos[0])])
					.setCapitulo(Long.parseLong(campos[1]))
					.setVersiculos(this.trataTextoImport(campos[2]))
					.setTexto(this.trataTextoImport(campos[3]))
					.setAbreviacao(this.trataTextoImport(campos[4]));
				
				temp = texto.getLivro().getNome() + " " + texto.getCapitulo() + ":" + texto.getVersiculos();
				
				if ( !this.dbHelper.existe(texto) ) {
					this.dbHelper.persisteTexto(texto);
				}
			}
		} catch (Exception e) {
			String message = "Erro na Importação.";
			
			if (temp != null) {
				message = "Erro na Importação (" + temp + ").";
			}
			
			Toast.makeText(this.context, message, Toast.LENGTH_LONG).show();
			
		} finally {
			this.dbHelper.close();
			
			try {
				if (bf != null) {
					bf.close();
				}
				
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Toast.makeText(this.context, "Fim da Importação.", Toast.LENGTH_LONG).show();
	}
	
	private void creatFolder() {
		Boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		
		File root;
		
		if (isSDPresent) {
			root = Environment.getExternalStorageDirectory();
			
		} else {
			root = Environment.getRootDirectory();
		}
		
		try {
			this.folder = new File(root.getAbsolutePath() + "/TextoRapido");
			
			if (!this.folder.exists()) {
				this.folder.mkdir();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private String trataTextoExport(String valor) {
		return valor.replace(";", "(pv)").replace(System.getProperty("line.separator"), "(lf)");
	}
	
	private String trataTextoImport(String valor) {
		return valor.replace("(pv)", ";").replace("(lf)", System.getProperty("line.separator"));
	}
}
