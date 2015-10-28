package br.com.dbcorp.textorapido.db;

import java.util.ArrayList;
import java.util.List;

import br.com.dbcorp.textorapido.FileManager;
import br.com.dbcorp.textorapido.LivrosBiblicos;
import br.com.dbcorp.textorapido.TextoDTO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String BANCO_DADOS = "TextoRapido";
	private static int VERSAO = 100;
	
	public DataBaseHelper(Context context) {
		super(context, BANCO_DADOS, null, VERSAO);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		new FileManager();
		
		db.execSQL("CREATE TABLE texto (" +
			       "_id INTEGER PRIMARY KEY," +
			       "livro INTEGER," +
			       "capitulo LONG," +
			       "versiculos TEXT," +
			       "texto TEXT," +
			       "abreviacao TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		new FileManager();
	}
	
	public List<TextoDTO> listaDeTextos(String textoParaProcura) {
		List<TextoDTO> textos = new ArrayList<TextoDTO>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		String where = "";
		if (textoParaProcura != null && textoParaProcura.length() > 0) {
			where = " where texto like '%" + textoParaProcura + "%'";
		}
		
		Cursor cursor = db.rawQuery("SELECT _id, livro, capitulo, versiculos, texto, abreviacao FROM texto" + where, null);
		
		
		TextoDTO texto = null;
		while (cursor.moveToNext()) {
			texto = new TextoDTO()
				.setId(cursor.getInt(0))
				.setLivro(LivrosBiblicos.values()[cursor.getInt(1)])
				.setCapitulo(cursor.getLong(2))
				.setVersiculos(cursor.getString(3))
				.setTexto(cursor.getString(4))
				.setAbreviacao(cursor.getString(5));
			
			textos.add(texto);
		}
		
		cursor.close();
		
		return textos;
	}
	
	public long persisteTexto(TextoDTO texto) {
		ContentValues values = new ContentValues();
		values.put("livro", texto.getLivro().ordinal());
		values.put("capitulo", texto.getCapitulo());
		values.put("versiculos", texto.getVersiculos());
		values.put("texto", texto.getTexto());
		values.put("abreviacao", texto.getAbreviacao());
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		if (texto.getId() == 0) {
			return db.insert("texto", null, values);
			
		} else {
			return db.update("texto", values, "_id = ?", new String[]{Integer.toString(texto.getId())});
		}
	}
	
	public boolean existe(TextoDTO texto) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		String[] values = {Integer.toString(texto.getLivro().ordinal()), Long.toString(texto.getCapitulo()), texto.getVersiculos()};
		
		Cursor cursor = db.rawQuery(
				"SELECT _id, livro, capitulo, versiculos, texto, abreviacao FROM texto WHERE livro = ? AND capitulo = ? AND versiculos = ?",
										values);
		
		return cursor.getCount() > 0;
	}
	
	public void remover(TextoDTO texto) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete("texto", "_id = ?", new String[]{Integer.toString(texto.getId())});
	}
}