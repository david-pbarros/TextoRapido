package br.com.dbcorp.textorapido;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogHelper {

	public enum ButtonType {
		POSITIVE, NEUTRAL, NEGATIVE;
	}
	
	protected Activity context;
	protected AlertDialog.Builder builder;
	 
	public DialogHelper(Activity context) {
		this.context = context;
		
		this.builder = new AlertDialog.Builder(this.context);
	}
	
	public DialogHelper setTitle(String titulo, int iconeResourceID) {
		View titleView = this.context.getLayoutInflater().inflate(R.layout.dialog_title, null);
		
		TextView title = (TextView) titleView.findViewById(R.id.dialog_title_text);
		title.setText(titulo);
		
		ImageView icon = (ImageView) titleView.findViewById(R.id.dialog_title_icon);
		icon.setImageResource(iconeResourceID);
		
		this.builder.setCustomTitle(titleView);
		
		return this;
	}
	
	public DialogHelper setTitle(int tituloResourceID, int iconeResourceID) {
		this.setTitle(this.context.getResources().getString(tituloResourceID), iconeResourceID);
		
		return this;
	}
	
	public DialogHelper setMessage(String message) {
		this.builder.setMessage(message);
		
		return this;
	}
	
	public DialogHelper setMessage(int messageResourceID) {
		this.builder.setMessage(this.context.getResources().getString(messageResourceID));
		
		return this;
	}
	
	public DialogHelper setbutton(String label, ButtonType type, DialogInterface.OnClickListener listener) {
		switch (type) {
		case POSITIVE:
			this.builder.setPositiveButton(label, listener);
			break;
			
		case NEUTRAL:
			this.builder.setNeutralButton(label, listener);
			break;
			
		case NEGATIVE:
			this.builder.setNegativeButton(label, listener);
			break;
		}
		
		return this;
	}
	
	public DialogHelper setbutton(int labelResourceID, ButtonType type, DialogInterface.OnClickListener listener) {
		this.setbutton(this.context.getResources().getString(labelResourceID), type, listener);
		
		return this;
	}
	
	public void show() {
		this.builder.create().show();
	}
}
