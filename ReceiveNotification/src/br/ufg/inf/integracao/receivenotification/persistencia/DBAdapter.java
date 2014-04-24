package br.ufg.inf.integracao.receivenotification.persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufg.inf.integracao.receivenotification.model.Notificacao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	String[] colunas = new String[] { "IDENTIFICADOR", "MENSAGEM", "DATA_RECEBIMENTO", "DATA"};

	public DBAdapter(Context context) {
		dbHelper = new DBHelper(context);
		database = dbHelper.getReadableDatabase();
	}
	
	public List<Notificacao> getNotificacoes() {
		Cursor cursor = database.query(dbHelper.nomeTable, colunas, "", null,null, null, null);
		List<Notificacao> listaNotificacoes = construirNotificacaoPorCursor(cursor);
		
		return listaNotificacoes;
	}
	
	public boolean salvarNotificacao(String mensagem, Date data_recebimento) {
		ContentValues content = new ContentValues();
		content.put("MENSAGEM", mensagem);
		content.put("DATA_RECEBIMENTO", getLongDate(data_recebimento));
		
		long retorno = database.insert(dbHelper.nomeTable, null, content);
	       
        if (retorno != -1)
                return true;
        else
                return false;
	}
	
	public Notificacao getNotificacaoPeloCursor (Cursor cursor) {
		Notificacao notificacao = new Notificacao();
		
		if (cursor != null) {
			notificacao = new Notificacao();
			notificacao.setIdentificador(cursor.getInt(cursor.getColumnIndex("IDENTIFICADOR")));
			notificacao.setMensagem(cursor.getString(cursor.getColumnIndex("MENSAGEM")));
			notificacao.setData_recebimento(loadDate(cursor, "DATA_RECEBIMENTO"));
        }
		
		return notificacao;
	}
	
	public Notificacao getNotificacaoPeloIdentificador (int identificador) {
		String query = "IDENTIFICADOR = " +  identificador;
		Cursor cursor = database.query(dbHelper.nomeTable, colunas, query, null,null, null, null);
		
		return getNotificacaoPeloCursor(cursor); 
	}
	
	public void apagarNotificacao (int identificador){ 
		String query = "IDENTIFICADOR = " +  identificador;
        database.delete(dbHelper.nomeTable, query, null); 
	}
	
	public static Long getLongDate(Date date) {
	    long data_recebimento;
	    
	    try {
	    	data_recebimento = date.getTime();
		} catch (RuntimeException e) {
			throw new RuntimeException("(getLongDate) Data de Recebimento inv�lida.");
		}
		
	    return data_recebimento;
	}
	
	public static Date loadDate(Cursor cursor, String coluna) {
		Date data_recebimento;
		
		try {
			long longDate = cursor.getLong(cursor.getColumnIndex(coluna));
			data_recebimento = new Date(longDate);
		} catch (RuntimeException e) {
			throw new RuntimeException("(loadDate) Erro ao converter a coluna " + coluna);
		}
		
	    return data_recebimento;
	}
	
	private List<Notificacao> construirNotificacaoPorCursor(Cursor cursor) {
        List<Notificacao> notificacoes = new ArrayList<Notificacao>();
        
        if(cursor == null)
            return notificacoes;
         
        try {
            if (cursor != null) {
            	while (cursor.moveToNext()) {
            		Notificacao notificacao = getNotificacaoPeloCursor(cursor);
                    notificacoes.add(notificacao);
            	}
            }
        } catch(RuntimeException e) {
            throw new RuntimeException("(construirNotificacaoPorCursor) Erro ao acessar o cursor.");
        } finally {
        	cursor.close();
        }
        
        return notificacoes;
    }

}
