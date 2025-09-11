package br.com.postech.ubsfacil.domain;

import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;

import java.util.regex.Pattern;

import static io.micrometer.common.util.StringUtils.isBlank;

public class Ubs {
    private Integer idUbs;
    private String nome;
    private String cnes;  // Cadastro Nacional de Estabelecimentos de Saúde
    private String telefone;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private Double latitude;
    private Double longitude;


    public Ubs(Integer idUbs, String nome, String cnes, String telefone, String logradouro, String numero, String bairro, String cidade, String uf, String cep) {
        this.idUbs = idUbs;
        this.nome = nome;
        this.cnes = cnes;
        this.telefone = telefone;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    }

    public Integer getIdUbs() {
        return idUbs;
    }

    public void setIdUbs(Integer idUbs) {
        this.idUbs = idUbs;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnes() {
        return cnes;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    private static final Pattern CNES_PATTERN = Pattern.compile("^\\d{7}$");
    private static final Pattern TELEFONE_PATTERN = Pattern.compile("^\\(\\d{2}\\) ?\\d{4}-\\d{4}$");
    private static final Pattern UF_PATTERN = Pattern.compile("^[A-Z]{2}$");
    private static final Pattern CEP_PATTERN = Pattern.compile("^\\d{5}-?\\d{3}$");

    private void validarPatternUbs() {
        if (!CNES_PATTERN.matcher(cnes).matches()) throw new ErroNegocioException("O CNES deve conter exatamente 7 dígitos");
        if (!TELEFONE_PATTERN.matcher(telefone).matches()) throw new ErroNegocioException("O telefone deve estar no formato (XX) XXXX-XXXX");
        if (!UF_PATTERN.matcher(uf).matches()) throw new ErroNegocioException("UF deve ter 2 letras maiúsculas");
        if (!CEP_PATTERN.matcher(cep).matches()) throw new ErroNegocioException("CEP deve estar no formato 00000-000 ou 00000000");
    }

    private void validarCamposObrigatorios() {
        if (isBlank(nome)) {
            throw new ErroNegocioException("O nome da UBS é obrigatório");
        }
        if (isBlank(cnes)) {
            throw new ErroNegocioException("O código CNES é obrigatório");
        }
        if (isBlank(telefone)) {
            throw new ErroNegocioException("O telefone é obrigatório");
        }
        if (isBlank(logradouro)) {
            throw new ErroNegocioException("O logradouro é obrigatório");
        }
        if (isBlank(numero)) {
            throw new ErroNegocioException("O número é obrigatório");
        }
        if (isBlank(bairro)) {
            throw new ErroNegocioException("O bairro é obrigatório");
        }
        if (isBlank(cidade)) {
            throw new ErroNegocioException("A cidade é obrigatória");
        }
        if (isBlank(uf)) {
            throw new ErroNegocioException("O estado (UF) é obrigatório");
        }
        if (isBlank(cep)) {
            throw new ErroNegocioException("O CEP é obrigatório");
        }
    }

    public void validarUbs() {
        validarCamposObrigatorios();
        validarPatternUbs();
    }

    public static void validarCnes(String cnes){
        if (!CNES_PATTERN.matcher(cnes).matches()) throw new ErroNegocioException("O CNES deve conter exatamente 7 dígitos");
    }

    public static void validarUf(String uf) {
        if (isBlank(uf) || !UF_PATTERN.matcher(uf).matches()) {
            throw new ErroNegocioException("UF deve ter 2 letras maiúsculas.");
        }
    }

}
