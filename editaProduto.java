package okj.com.desafio2_crud;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class editaProduto extends AppCompatActivity {

        Banco banco;
        Produtos produtos;
        String novaImagem;
        int idProduto;


        String nomeProduto;
        Double valorProduto;
        String descricao;
        String fabricante;
        String categoria;
        String imgUri;


        EditText campoEditaNomeP;
        EditText campoEditaValorP;
        EditText campoEditaDescricaoP;
        EditText campoEditaFabricanteP;
        TextView tvCategoriaP;

    ImageView imageView;

        private static final int IMAGEM_GALERIA_REQUEST =1212;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edita_produto);
            Bundle b = getIntent().getExtras();
            idProduto = b.getInt("produtoID", 12);

            banco = new Banco(this);

            //carregando dados atuais do item a ser alterado posteriormente
            Cursor cursor = banco.SelecionaEspecifico(idProduto);
            cursor.moveToLast();
            nomeProduto = cursor.getString(1);
            valorProduto = cursor.getDouble(2);
            descricao = cursor.getString(3);
            fabricante = cursor.getString(4);
            categoria = cursor.getString(5);
            imgUri = cursor.getString(6);

            campoEditaNomeP = (EditText) findViewById(R.id.campoEditaNomeP);
            campoEditaValorP = (EditText) findViewById(R.id.campoEditaValorP);
            campoEditaDescricaoP = (EditText) findViewById(R.id.campoEditaDescricaoP);
            campoEditaFabricanteP = (EditText) findViewById(R.id.campoEditaFabricanteP);
            tvCategoriaP= (TextView) findViewById(R.id.tvCategoriaP);

            imageView = (ImageView) findViewById(R.id.imageView);

            campoEditaNomeP.setText(nomeProduto);
            campoEditaValorP.setText(valorProduto.toString());
            campoEditaDescricaoP.setText(descricao);
            campoEditaFabricanteP.setText(fabricante);
            tvCategoriaP.setText(tvCategoriaP.getText()+" "+categoria);


            ////////////////////////////////////////////////////////////////////////
            //carregando imagem na listview com base no caminho da galley do android

            InputStream inputStream;
            try {
                Uri u = Uri.parse(imgUri);
                inputStream = getContentResolver().openInputStream(u);
                Bitmap imagem = BitmapFactory.decodeStream(inputStream);

                if (imgUri == null) {
                    imageView.setImageResource(R.drawable.ic_launcher);
                } else {
                    imageView.setImageBitmap(imagem);
                }
            } catch (Exception e) {};

        }
             /////////////////////////////////////////////////////////////////////////

    public void btnSalvarAltercoes(View v){


        campoEditaNomeP = (EditText)findViewById(R.id.campoEditaNomeP);
        campoEditaValorP = (EditText)findViewById(R.id.campoEditaValorP);
        campoEditaDescricaoP = (EditText)findViewById(R.id.campoEditaDescricaoP);
        campoEditaFabricanteP = (EditText)findViewById(R.id.campoEditaFabricanteP);

        produtos = new Produtos(idProduto,nomeProduto,valorProduto,descricao,fabricante,categoria, imgUri);
        produtos.setNomeProdutos(campoEditaNomeP.getText().toString());
        produtos.setValorProdutos(Double.parseDouble(campoEditaValorP.getText().toString()));
        produtos.setDescricao(campoEditaDescricaoP.getText().toString());
        produtos.setFabricante(campoEditaFabricanteP.getText().toString());
        produtos.setImgUri(novaImagem);

        banco.Atualizar(idProduto, produtos);
        Intent retornaInicio = new Intent(this,PrincipalActivity.class);
        editaProduto.this.startActivity(retornaInicio);
    }

    //configurado para capturar imagens da gallery android
    public void tirarFoto(View v){

        try {
            Intent cameraIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

            File diretorioImagem = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String pastaDiretorioIMG = diretorioImagem.getPath();
            Uri data = Uri.parse(pastaDiretorioIMG);
            cameraIntent.setDataAndType(data, "image/*");
            startActivityForResult(cameraIntent, IMAGEM_GALERIA_REQUEST);
        }catch(Exception e){ Toast.makeText(getApplicationContext(),"Erro: Talvez você não possua imagens na galeria!",Toast.LENGTH_SHORT).show();};

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== RESULT_OK){
            Uri imgUri= data.getData();
            if(requestCode==IMAGEM_GALERIA_REQUEST){

                novaImagem = data.getData().toString();

                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imgUri);
                    Bitmap imagem = BitmapFactory.decodeStream(inputStream);
                    ImageView img = (ImageView)findViewById(R.id.imageView);
                    img.setImageBitmap(imagem);



                }catch(FileNotFoundException e){
                    Toast.makeText(getApplicationContext(),"Não foi possível abrir a imagem selecionada!",Toast.LENGTH_SHORT).show();
                };
            }

        }
    }



}
