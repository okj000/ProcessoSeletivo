package okj.com.desafio2_crud;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by okj on 04/03/2016.
 */
public class listAdapter extends ArrayAdapter<Produtos> {

        private ArrayList<Produtos> lista;
        private Context c;
        int posicao;
        Banco banco;

        ImageButton btnDEL;
        ImageButton btnEDT;



        public listAdapter(Context context,ArrayList<Produtos>lista) {
            super(context,0, lista);
            this.c = context;
            this.lista = lista;
        }


        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final Produtos produtosPosicao = this.lista.get(position);


            convertView = LayoutInflater.from(this.c).inflate(R.layout.listviewmodelo1, null);

            //pega a posição do produto na lista e acessa os métodos get da classe produtos
            TextView txtIdlist = (TextView) convertView.findViewById(R.id.tvlistaId);
            txtIdlist.setText("ID: " + String.valueOf(produtosPosicao.getId()));

            TextView txtNomeList = (TextView) convertView.findViewById(R.id.tvlistaNome);
            txtNomeList.setText("Produto: " + produtosPosicao.getNomeProdutos());

            TextView txtValorList = (TextView) convertView.findViewById(R.id.tvlistaValor);
            txtValorList.setText("Valor: " + produtosPosicao.getValorProdutos().toString());

            TextView txtDescricaoList = (TextView) convertView.findViewById(R.id.tvlistaDescricao);
            txtDescricaoList.setText("Descrição: "+produtosPosicao.getDescricao().toString());

            TextView txtFabricanteList = (TextView) convertView.findViewById(R.id.tvlistaFabricante);
            txtFabricanteList.setText("Fabricante: "+produtosPosicao.getFabricante().toString());

            TextView txtCategoriaList = (TextView) convertView.findViewById(R.id.tvlistaCategoria);
            txtCategoriaList.setText("Categoria: "+produtosPosicao.getCategoria().toString());

////////////////////////////////////////////////////////////////////////
            //carregando imagem na listview com base no caminho da galley do android

            InputStream inputStream;
            try {
                Uri imgUri = Uri.parse(produtosPosicao.getImgUri());
                inputStream = c.getContentResolver().openInputStream(imgUri);
                Bitmap imagem = BitmapFactory.decodeStream(inputStream);
                ImageView imageView = (ImageView)convertView.findViewById(R.id.imgLista);
                imageView.setImageBitmap(imagem);

            }catch(Exception e){};
/////////////////////////////////////////////////////////////////////////

            btnEDT = (ImageButton)convertView.findViewById(R.id.btnListaEDT);
            btnEDT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int produtoID = produtosPosicao.getId();
                    Bundle b = new Bundle();
                    b.putInt("produtoID",produtoID);
                    Intent intent = new Intent(c, editaProduto.class);
                    intent.putExtras(b).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    c.startActivity(intent);


                }
            });

            btnDEL = (ImageButton) convertView.findViewById(R.id.btnListaDEL);
            btnDEL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    banco = new Banco(c);
                    banco.Deletar(String.valueOf(produtosPosicao.getId()), produtosPosicao.getNomeProdutos());

                    Intent intent = new Intent(c, PrincipalActivity.class);
                    c.startActivity(intent);



                }
            });


            return convertView;



        }



}
