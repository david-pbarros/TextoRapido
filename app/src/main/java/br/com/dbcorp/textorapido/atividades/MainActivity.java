package br.com.dbcorp.textorapido.atividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import br.com.dbcorp.textorapido.DialogHelper;
import br.com.dbcorp.textorapido.DialogHelper.ButtonType;
import br.com.dbcorp.textorapido.OptionMenuTools;
import br.com.dbcorp.textorapido.R;
import br.com.dbcorp.textorapido.TextoDTO;
import br.com.dbcorp.textorapido.db.DataBaseHelper;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener {
	
	private DataBaseHelper dbHelper;
	private ListView listView;
	private List<TextoDTO> textos;
	private EditText procura;
	private TextoDTO texto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.dbHelper = new DataBaseHelper(this);
		
		this.listView = (ListView) findViewById(R.id.list);
		this.listView.setOnItemClickListener(this);
		
		this.procura = (EditText) findViewById(R.id.textoProcura);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int itemPosition, long id) {
		this.texto = this.textos.get(itemPosition);
		
		new DialogHelper(this)
			.setTitle(this.texto.getLivro().getNome() + " " + this.texto.getCapitulo() + ":" + this.texto.getVersiculos(), R.drawable.ic_launcher)
			.setMessage(this.texto.getTexto())
			.setbutton("Voltar", ButtonType.POSITIVE, null)
			.setbutton("Apagar", ButtonType.NEUTRAL, this)
			.setbutton("Editar", ButtonType.NEGATIVE, this)
		.show();
	}
	
	@Override
	public void onClick(DialogInterface dialog, int posicao) {
		dialog.dismiss();

		if (posicao == -3) {
			this.dbHelper.remover(this.texto);
			this.listar();
			
		} else {
			Intent editar = new Intent(this, TextoActivity.class);
			editar.putExtra("texto", this.texto);
			startActivity(editar);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		this.listar();
	}
	
	@Override
	public void finish() {
		new DialogHelper(this)
			.setTitle("Texto Rapido", R.drawable.ic_launcher)
			.setMessage("Deseja realmente sair?")
			.setbutton("Não", ButtonType.POSITIVE, null)
			.setbutton("Sim", ButtonType.NEUTRAL, new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dbHelper.close();
							android.os.Process.killProcess(android.os.Process.myPid());
						}
					})
		.show();
		
		return;
	}
	
	//Menu de op�oes/ActionBar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return OptionMenuTools.createMenu(menu, getMenuInflater(), R.menu.main);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return OptionMenuTools.buttonListener(this, item);
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		return OptionMenuTools.showIcons(featureId, menu);
	}
	//Menu de op�oes/ActionBar / 
	
	public void adiciona(View view) {
		startActivity(new Intent(this, TextoActivity.class));
	}
	
	public void procurar(View view) {
		this.listar();
	}
	
	public void listar() {
		this.textos = this.dbHelper.listaDeTextos(this.procura.getText().toString());
		
		this.setAdapter();
	}
	
	private void setAdapter() {
		String[] de = {"texto", "sitacao"};
		int[] para = {R.id.textoLocal, R.id.textoSita};
		
		SimpleAdapter adapter = new SimpleAdapter(this, this.listarTextos(), R.layout.textos_list, de, para);
		
		this.listView.setAdapter(adapter);
	}
	
	private List<Map<String, Object>> listarTextos() {
		List<Map<String, Object>> textos = new ArrayList<Map<String,Object>>();
		
		for (TextoDTO texto : this.textos) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("texto", texto.getLivro().getAbreviacao() + " " + texto.getCapitulo() + ":" + texto.getVersiculos());
			item.put("sitacao", texto.getAbreviacao());
			textos.add(item);
		}
		
		return textos;
	}
}