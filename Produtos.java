package okj.com.desafio2_crud;

/**
 * Created by okj on 04/03/2016.
 */
public class Produtos {

    //variáveis private
    private int id;
    private String nomeProdutos;
    private Double valorProdutos;
    private String descricao;
    private String fabricante;
    private String categoria;
    private String imgUri;

    //construtor
    public Produtos(int id,String nomeProdutos, Double valorProdutos,String descricao, String fabricante,String categoria, String imgUri){
        this.id = id;
        this.nomeProdutos = nomeProdutos;
        this.valorProdutos = valorProdutos;
        this.descricao = descricao;
        this.fabricante = fabricante;
        this.categoria = categoria;
        this.imgUri = imgUri;
    }



    //metodos getter and setter para acesso externo encapsulado

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeProdutos() {
        return nomeProdutos;
    }

    public void setNomeProdutos(String nomeProdutos) {
        this.nomeProdutos = nomeProdutos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Double getValorProdutos() {
        return valorProdutos;
    }

    public void setValorProdutos(Double valorProdutos) {
        this.valorProdutos = valorProdutos;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
