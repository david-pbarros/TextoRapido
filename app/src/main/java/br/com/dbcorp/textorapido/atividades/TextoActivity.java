package br.com.dbcorp.textorapido.atividades;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.dbcorp.textorapido.LivrosBiblicos;
import br.com.dbcorp.textorapido.R;
import br.com.dbcorp.textorapido.TextoDTO;
import br.com.dbcorp.textorapido.db.DataBaseHelper;

public class TextoActivity extends Activity {
	
	private DataBaseHelper dbHelper;
	private Spinner spinner;
	private EditText capitulo, versiculos, abreviacao, textoCompleto;
	private TextoDTO texto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_texto);

		this.dbHelper = new DataBaseHelper(this);
		
		this.spinner = (Spinner) findViewById(R.id.livros);
		this.capitulo = (EditText) findViewById(R.id.capitulo);
		this.versiculos = (EditText) findViewById(R.id.versiculos);
		this.abreviacao = (EditText) findViewById(R.id.abreviacao);
		this.textoCompleto = (EditText) findViewById(R.id.texto);
		
		this.setComboLivros();
		this.setContent();
	}
	
	@Override
	public void finish() {
		this.dbHelper.close();
		super.finish();
	}
	
	private void setComboLivros() {
		this.spinner.setFocusable(true); 
		
		String[] textos = new String[LivrosBiblicos.values().length];
		
		int index = 0;
		for (LivrosBiblicos livro : LivrosBiblicos.values()) {
			textos[index++] = livro.getNome();
		}
		
		this.spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spiner_item, textos));
	}
	
	public void voltar(View view) {
		this.finish();
	}
	
	public void confirmar(View view) {
		LivrosBiblicos livro = LivrosBiblicos.values()[this.spinner.getSelectedItemPosition()];

		String capituloSt = this.capitulo.getText().toString();
		String versiculos = this.versiculos.getText().toString();
		String abreviacao = this.abreviacao.getText().toString();
		String textoCompleto = this.textoCompleto.getText().toString();
		
		if ( isInvalido(capituloSt, "Capitulo não Informado.") || isInvalido(versiculos, "Versiculo(s) não Informado(s).") 
				|| isInvalido(abreviacao, "Abreviação não Informada.") ) {
			return;
		}
		
		this.texto.setLivro(livro)
			.setCapitulo(Long.parseLong(capituloSt))
			.setVersiculos(versiculos)
			.setTexto(textoCompleto)
			.setAbreviacao(abreviacao);
		
		if ( this.texto.getId() == 0 && this.dbHelper.existe(this.texto) ) {
			Toast.makeText(this, "Texto já Cadastrado.", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (this.dbHelper.persisteTexto(texto) != -1) {
			Toast.makeText(this, "Registro " + (this.texto.getId() == 0 ? "inserido" : "atualizado"), Toast.LENGTH_SHORT).show();
			
			this.finish();
		
		} else {
			Toast.makeText(this, "Erro ao " + (this.texto.getId() == 0 ? "inserir" : "atualizar") + " registro", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean isInvalido(String valor, String textoAviso) {
		if (valor == null || valor.length() == 0) {
			Toast.makeText(getApplicationContext(), textoAviso, Toast.LENGTH_LONG).show();
			return true;
		}
		
		return false;
	}
	
	private void setContent() {
		if (this.getIntent().getExtras() != null) {
			this.texto = (TextoDTO) this.getIntent().getExtras().getSerializable("texto");
			
			if (this.texto != null) {
				this.spinner.setSelection(this.texto.getLivro().ordinal());
				this.capitulo.setText(Long.toString(texto.getCapitulo()));
				this.versiculos.setText(this.texto.getVersiculos());
				this.abreviacao.setText(this.texto.getAbreviacao());
				this.textoCompleto.setText(this.texto.getTexto());
			}
		} 
		
		if (this.texto == null) {
			this.texto = new TextoDTO();
		}
	}
}
