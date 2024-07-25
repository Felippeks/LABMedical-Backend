package br.com.senai.lab365.labmedical.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {

    @Column(length = 9)
    @Schema(description = "CEP do endereço", example = "12345-678")
    private String cep;

    @Column(length = 50)
    @Schema(description = "Cidade do endereço", example = "São Paulo")
    private String cidade;

    @Column(length = 2)
    @Schema(description = "Estado do endereço", example = "SP")
    private String estado;

    @Column(length = 50)
    @Schema(description = "Logradouro do endereço", example = "Rua da Consolação")
    private String logradouro;

    @Column(length = 10)
    @Schema(description = "Número do endereço", example = "123")
    private String numero;

    @Column(length = 50)
    @Schema(description = "Complemento do endereço", example = "Apto 123")
    private String complemento;

    @Column(length = 50)
    @Schema(description = "Bairro do endereço", example = "Consolação")
    private String bairro;

    @Column(length = 50)
    @Schema(description = "Ponto de referência do endereço", example = "Próximo ao metrô")
    private String pontoDeReferencia;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getPontoDeReferencia() {
        return pontoDeReferencia;
    }

    public void setPontoDeReferencia(String pontoDeReferencia) {
        this.pontoDeReferencia = pontoDeReferencia;
    }
}