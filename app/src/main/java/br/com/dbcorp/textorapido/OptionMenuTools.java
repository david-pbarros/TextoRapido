package br.com.dbcorp.textorapido;

import java.lang.reflect.Method;

import br.com.dbcorp.textorapido.DialogHelper.ButtonType;
import br.com.dbcorp.textorapido.atividades.MainActivity;
import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public class OptionMenuTools {

	/**
	 * Cria itens no menu.
	 * Adiciona no actionBar ou no optionMenu dependendo do Thema escolhido
	 * @param menu
	 * @param inflater
	 * @return
	 */
	public static boolean createMenu(Menu menu, MenuInflater inflater, int resource) {
		try {
			inflater.inflate(resource, menu);
			return true;
			
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Executa as a��es do itens do menu
	 * @param context
	 * @param item
	 * @return
	 */
	public static boolean buttonListener(Activity context, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_sair:
			context.finish();
			return true;
			
		case R.id.action_import:
			new FileManager(context).importTextos();
			((MainActivity) context).listar();
			return true;
			
		case R.id.action_export:
			new FileManager(context).export();
			return true;
		
		case R.id.action_info:
			String message = "2014 - Texto Rápido.";
			try {
				message = message + "\nVersão:" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName + ".";
			} catch (NameNotFoundException e) {
			} finally {
				message  = message + "\n\nDesenvolvedor: David Barros." + "\ndavid_pbarros@hotmail.com" + "\n\nEntre em contato em caso de dúvidas ou sugestões.";
			}
			
			new DialogHelper(context)
				.setTitle("Informação", R.drawable.ic_launcher)
				.setMessage(message)
				.setbutton("OK", ButtonType.NEGATIVE, null)
			.show();
			
			
		default:
			return false;
		}
	}
	
	/**
	 * Faz mostrar os icones no actionBar(overflow)
	 * @param featureId
	 * @param menu
	 * @return
	 */
	public static boolean showIcons(int featureId, Menu menu) {
		if( (featureId == Window.FEATURE_ACTION_BAR || featureId == Window.FEATURE_OPTIONS_PANEL) && menu != null){
	        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
	            try{
	                Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
	                m.setAccessible(true);
	                m.invoke(menu, true);
	           
	                return true;
	                
	            } catch(Exception e){
	                return false;
	            }
	        }
	    }
		
		return true;
	}
}
