package okj.com.desafio2_crud;

/**
 * Created by okj on 04/03/2016.
 */

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class Banco extends SQLiteOpenHelper {


    Produtos produtos;
    Context context;
    private static final String NOME_DB= "bancoDesafio2.db";
    private static final int VERSAO_DB=3;
    SQLiteDatabase banco;

    //construtor
    public  Banco(Context context) {
        super(context, NOME_DB, null, VERSAO_DB);
        this.context = context;
    }


    //implementação dos métodos necessários da extenção SQLiteOpenHelper
    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            String SQL = "CREATE TABLE IF NOT EXISTS tabCadastroProdutos (_id INTEGER PRIMARY KEY AUTOINCREMENT, nomeProduto TEXT, valorProduto INTEGER, descricao TEXT, fabricante TEXT, categoria TEXT, imgUri TEXT)";
            db.execSQL(SQL);

            SQL = "CREATE TABLE IF NOT EXISTS tabSpinnerCategoria (_id INTEGER PRIMARY KEY AUTOINCREMENT, categorias TEXT)";
            db.execSQL(SQL);

            SQL = "INSERT INTO tabSpinnerCategoria (categorias) VALUES('Categoria1'), ('Categoria2'), ('Categoria3'), ('Categoria4')";
            db.execSQL(SQL);

        }catch(Exception e){Alerta("Criou Banco", "Banco foi criado!!");}

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        try{
            String SQL = "DROP TABLE tabCadastroProdutos";
            db.execSQL(SQL);

            SQL = "DROP TABLE tabSpinnerCategoria";
            db.execSQL(SQL);

            onCreate(db);
        }catch(Exception e){Alerta("Update no Banco", "Banco foi Atualizado!!");}

    }


    public void Inserir(Produtos produtos){

        try {
            Banco b = new Banco(context);
            banco = b.getWritableDatabase();
            String nomeProduto = produtos.getNomeProdutos();
            Double valorProduto = produtos.getValorProdutos();
            String descricao = produtos.getDescricao();
            String fabricante = produtos.getFabricante();
            String categoria = produtos.getCategoria();
            String imgUri= produtos.getImgUri();

            //Inserindo objeto produtos no banco
            String SQL = "INSERT INTO tabCadastroProdutos (nomeProduto, valorProduto, descricao, fabricante, categoria, imgUri) VALUES('" + nomeProduto + "','" + valorProduto+ "','" + descricao+ "','" + fabricante+"','" + categoria+"','" + imgUri+"')";
            banco.execSQL(SQL);
            Alerta("Inserir no Banco", "Foi inserido no banco!");
        } catch (SQLException e){Alerta("Inserir no Banco", "Ocorreu o seguinte erro: "+e.toString());}
    }

    public void Deletar(String idProduto, String nomeProduto){

        try {

            banco = getWritableDatabase();
            String SQL = "DELETE FROM tabCadastroProdutos WHERE _id = '"+idProduto+"'";
            banco.execSQL(SQL);
            Alerta("DELETE", "Foi Removido '"+nomeProduto+'"');
        } catch (SQLException e){Alerta("Delete no Banco", "Ocorreu o seguinte erro: "+e.toString());}
    }


    public void Atualizar(int idProduto, Produtos produtos){

        String nomeProduto = produtos.getNomeProdutos();
        Double valorProduto = produtos.getValorProdutos();
        String descricao = produtos.getDescricao();
        String fabricante = produtos.getFabricante();
        String categoria = produtos.getCategoria();
        String imgUri = produtos.getImgUri();

        try {

            banco = getWritableDatabase();
            String SQL = "UPDATE tabCadastroProdutos SET nomeProduto = '"+nomeProduto+"', valorProduto = '"+valorProduto+"', descricao = '"+descricao+"', fabricante = '"+fabricante+"', categoria = '"+categoria+"', imgUri = '"+imgUri+"' WHERE _id =" + idProduto;
            banco.execSQL(SQL);
            Alerta("Update", "Foi atualizado '"+nomeProduto+'"');
        } catch (SQLException e){Alerta("Atualização", "Ocorreu o seguinte erro: "+e.toString());}
    }

    public ArrayList<Produtos>Selecionatudo(String ordenacao) {
        String query = ordenacao;
        banco = getWritableDatabase();
        ArrayList<Produtos> produtosArray = new ArrayList<>();
        //carega do banco para um cursor que em seguida preenche a classe com x objetos produtos. Por fim
        //carrega a listview....tudo aqui
        Cursor cursor = banco.rawQuery(query, null);

        //loop até percorrer toda a tabela do banco
        while (cursor.moveToNext()) {

            //recuperando cada informação do banco como um novo objeto produto para depois usar na listview
            int id = cursor.getInt(0);
            String nomeProduto = cursor.getString(1);
            Double valorProduto = cursor.getDouble(2);
            String descricao = cursor.getString(3);
            String fabricante = cursor.getString(4);
            String categoria = cursor.getString(5);
            String imgUri = cursor.getString(6);
            Produtos produtos = new Produtos(id, nomeProduto, valorProduto, descricao, fabricante, categoria, imgUri);
            produtosArray.add(produtos);
        }


        return produtosArray;
    }


    public Cursor SelecionaEspecifico(int idProduto){
        banco = getWritableDatabase();
        Cursor cursor = banco.rawQuery("SELECT * FROM tabCadastroProdutos WHERE _id ="+idProduto,null);

        //recuperando cada informação do banco como um novo objeto produto
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nomeProduto = cursor.getString(1);
            Double valorProduto = cursor.getDouble(2);
            String descricao = cursor.getString(3);
            String fabricante = cursor.getString(4);
            String categoria = cursor.getString(5);
            String imgUri= cursor.getString(6);
            Produtos produtos = new Produtos(id, nomeProduto, valorProduto, descricao, fabricante,categoria, imgUri);
        }
        return cursor;
    }


    public void Alerta(String titulo, String mensagem){

        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setNeutralButton("OK", null);
        alerta.show();
    }



    public List<String> SelecionaSpinnerCategoria() {

            banco = getWritableDatabase();
             List<String> listSpinnerCategoria= new ArrayList<String>();

            Cursor cursor = banco.rawQuery("SELECT * FROM tabSpinnerCategoria", null);

            //loop até percorrer toda a tabela do banco
            while (cursor.moveToNext()) {

                String categoria = cursor.getString(1);
                listSpinnerCategoria.add(categoria);
            }

            return listSpinnerCategoria;
    }

}
