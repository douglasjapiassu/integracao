package br.ufg.inf.integracao.receivenotification.persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufg.inf.integracao.receivenotification.model.Notificacao;
import br.ufg.inf.integracao.receivenotification.model.Usuario;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	String[] colunasNotificacoes = new String[] { "IDENTIFICADOR", "MENSAGEM", "DATA_RECEBIMENTO", "DATA"};
	String[] colunasUsuario = new String[] { "IDENTIFICADOR", "NOME", "EMAIL", "DATA"};

	public DBAdapter(Context context) {
		dbHelper = new DBHelper(context);
		database = dbHelper.getReadableDatabase();
	}
	
	public List<Notificacao> getNotificacoes() {
		Cursor cursor = database.query(dbHelper.tableNotificacoes, colunasNotificacoes, "", null,null, null, null);
		List<Notificacao> listaNotificacoes = construirNotificacaoPorCursor(cursor);
		
		return listaNotificacoes;
	}
	
	public boolean salvarNotificacao(String mensagem, Date data_recebimento) {
		ContentValues content = new ContentValues();
		content.put("MENSAGEM", mensagem);
		content.put("DATA_RECEBIMENTO", getLongDate(data_recebimento));
		
		long retorno = database.insert(dbHelper.tableNotificacoes, null, content);
	       
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
		Cursor cursor = database.query(dbHelper.tableNotificacoes, colunasNotificacoes, query, null,null, null, null);
		
		return getNotificacaoPeloCursor(cursor); 
	}
	
	public void apagarNotificacao (int identificador){ 
		String query = "IDENTIFICADOR = " +  identificador;
        database.delete(dbHelper.tableNotificacoes, query, null); 
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
	
	public boolean salvarUsuario(String nome, String email) {
		ContentValues content = new ContentValues();
		content.put("IDENTIFICADOR", 1);
		content.put("NOME", nome);
		content.put("EMAIL", email);
		
		long retorno = database.insert(dbHelper.tableUsuarios, null, content);
	       
        if (retorno != -1)
                return true;
        else
                return false;
	}
	
	public Usuario getUsuario() {
		String query = "IDENTIFICADOR = 1";
		Cursor cursor = database.query(dbHelper.tableUsuarios, colunasUsuario, query, null,null, null, null);
		Usuario usuario = construirUsuarioPorCursor(cursor);
		
		return usuario;
	}
	
	private Usuario construirUsuarioPorCursor(Cursor cursor) {
		if(cursor == null)
            return null;

		Usuario usuario = new Usuario();
         
        try {
            if (cursor != null) {
            	while (cursor.moveToNext()) {
            		usuario = getUsuarioPeloCursor(cursor);
            	}
            }
        } catch(RuntimeException e) {
            throw new RuntimeException("(construirNotificacaoPorCursor) Erro ao acessar o cursor.");
        } finally {
        	cursor.close();
        }
        
        return usuario;
    }
	
	public Usuario getUsuarioPeloCursor (Cursor cursor) {
		Usuario usuario = new Usuario();
		
		if (cursor != null) {
			usuario.setIdentificador(cursor.getInt(cursor.getColumnIndex("IDENTIFICADOR")));
			usuario.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
			usuario.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
        }
		
		return usuario;
	}
	
	public void apagarUsuario (int identificador){ 
		String query = "IDENTIFICADOR = " +  identificador;
        database.delete(dbHelper.tableUsuarios, query, null); 
	}

}
