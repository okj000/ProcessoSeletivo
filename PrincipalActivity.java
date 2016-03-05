package okj.com.desafio2_crud;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    Banco banco = new Banco(this);
    Produtos produtos;
    EditText campoNome;
    EditText campoValor;
    EditText campoDescricao;
    EditText campoFabricante;
    Spinner spinnerCategoria;
    List spinList;
    ArrayList lista;
    ListView lv;
    String ordenacao = "SELECT * FROM tabCadastroProdutos";

    Cursor cursor;
    listAdapter adaptador;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        //cria uma novo ArrayList para carregar dados do banco
        lista = new ArrayList<>();


        //recupera tudo do banco. Agora temos id´s para cada objeto.
        lista = banco.Selecionatudo(ordenacao);
        spinList = banco.SelecionaSpinnerCategoria();

        ArrayAdapter<String> spinAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinList);
        spinAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoria);
        spinnerCategoria.setAdapter(spinAdapt);

        //adapta a lista para a listview da tela
        lv = (ListView) findViewById(R.id.lvExibe);
        final listAdapter adaptador = new listAdapter(this,lista);
        lv.setAdapter(adaptador);


    }

    public void cadastrarbtn(View view){
        ordenacao ="SELECT * FROM tabCadastroProdutos";
        campoNome = (EditText)findViewById(R.id.campoNomePoduto);
        campoValor = (EditText)findViewById(R.id.campoValorProduto);
        campoDescricao = (EditText)findViewById(R.id.campoDescricao);
        campoFabricante = (EditText)findViewById(R.id.campoFabricante);

        String nomeProduto = campoNome.getText().toString();
        Double valorProduto = Double.parseDouble(campoValor.getText().toString());
        String descricao = campoDescricao.getText().toString();
        String fabricante = campoFabricante.getText().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();
        Uri imgDefault = Uri.parse("android.resource://okj.com.desafio2-CRUD/" + R.drawable.ic_launcher);
        String imgUri = imgDefault.toString();



        //inserindo no objeto com id 0 - Banco vai setar o id no metodo "Selecionatudo" do banco para a listview.
        produtos = new Produtos(0,nomeProduto, valorProduto,descricao, fabricante, categoria, imgUri);

        //inserindo objeto no banco
        banco.Inserir(produtos);

        //cria uma novo ArrayList para carregar dados do banco
        lista = new ArrayList<>();


        //recupera tudo do banco. Agora temos id´s para cada objeto diferente de 0.
        lista = banco.Selecionatudo(ordenacao);

        //adapta a lista para a listview da tela
        lv = (ListView) findViewById(R.id.lvExibe);
        listAdapter adaptador = new listAdapter(this,lista);
        lv.setAdapter(adaptador);

        //tendo novidades, tela é atualizada
        adaptador.notifyDataSetChanged();

        // botões com métodos para editar item e deletar item serão encontrados dentro da classe listAdapter e
        //seão exibidos na listView para o usuário.


    }


    public void ordenarPorNome(View view) {
        ordenacao = "SELECT * FROM tabCadastroProdutos ORDER BY nomeProduto ASC";
        //cria uma novo ArrayList para carregar dados do banco
        lista = new ArrayList<>();

        //recupera tudo do banco.
        lista = banco.Selecionatudo(ordenacao);

        //adapta a lista para a listview da tela
        lv = (ListView) findViewById(R.id.lvExibe);
        listAdapter adaptador = new listAdapter(this,lista);
        lv.setAdapter(adaptador);
        //tendo novidades, tela é atualizada
        adaptador.notifyDataSetChanged();
    }

    public void ordenarPorCategoria(View view) {
        ordenacao = "SELECT * FROM tabCadastroProdutos ORDER BY categoria ASC";
        //cria uma novo ArrayList para carregar dados do banco
        lista = new ArrayList<>();

        //recupera tudo do banco.
        lista = banco.Selecionatudo(ordenacao);

        //adapta a lista para a listview da tela
        lv = (ListView) findViewById(R.id.lvExibe);
        listAdapter adaptador = new listAdapter(this,lista);
        lv.setAdapter(adaptador);
        //tendo novidades, tela é atualizada
        adaptador.notifyDataSetChanged();


    }


}
