package br.com.postech.ubsfacil.service;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.domain.Estoque;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;
import br.com.postech.ubsfacil.gateway.ports.EstoqueRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.EstoqueServicePort;
import br.com.postech.ubsfacil.gateway.ports.ubs.UbsRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.insumo.InsumoRepositoryPort;
import br.com.postech.ubsfacil.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EstoqueServiceImpl implements EstoqueServicePort {


    private final EstoqueRepositoryPort estoqueRepositoryPort;
    private final UbsRepositoryPort ubsRepositoryPort;
    private final InsumoRepositoryPort insumoRepositoryPort;

    public EstoqueServiceImpl(EstoqueRepositoryPort estoqueRepositoryPort, UbsRepositoryPort ubsRepositoryPort, InsumoRepositoryPort insumoRepositoryPort) {
        this.estoqueRepositoryPort = estoqueRepositoryPort;
        this.ubsRepositoryPort = ubsRepositoryPort;
        this.insumoRepositoryPort = insumoRepositoryPort;
    }


    @Override
    public ResponseDto cadastrarEstoque(Estoque estoque) {
        try {
            estoque.validarCamposObrigatorios();
            estoque.validarDataValidade();

            if (estoqueRepositoryPort.findByInsumoSku(estoque.getInsumoSku()).isPresent()) {
                log.error("Estoque com SKU de Insumo {} já cadastrado", estoque.getInsumoSku());
                throw new ErroNegocioException("Estoque com SKU " + estoque.getInsumoSku() + " já cadastrado.");
            }
            if (ubsRepositoryPort.findByCnes(estoque.getUbsCnes()).isEmpty()){
                log.error("UBS com CNES {} não encontrada", estoque.getUbsCnes());
                throw new ErroNegocioException("UBS com CNES " + estoque.getUbsCnes() + " não encontrada.");
            }
            if(insumoRepositoryPort.findBySku(estoque.getInsumoSku()).isEmpty()){
                log.error("Insumo com SKU {} não encontrado", estoque.getInsumoSku());
                throw new ErroNegocioException("Insumo com SKU " + estoque.getInsumoSku() + " não encontrado.");
            }

            Estoque saved = estoqueRepositoryPort.cadastrarEstoque(estoque);

            return montaResponse(saved, "cadastro");
        } catch (ErroNegocioException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao cadastrar Estoque", e);
            throw new ErroInternoException("Erro interno ao tentar cadastrar Estoque: " + e.getMessage());
        }
    }



    private ResponseDto montaResponse(Estoque estoque, String acao) {
        ResponseDto response = new ResponseDto();

        response.setMessage("cadastro".equals(acao) ? ConstantUtils.ESTOQUE_CADASTRADO : ConstantUtils.ESTOQUE_ATUALIZADO);

        Map<String, Object> data = new HashMap<>();
        data.put("ubsCnes", estoque.getUbsCnes());
        data.put("skuInsumo", estoque.getInsumoSku());
        data.put("quantidade", estoque.getQuantidade());
        data.put("estoqueMinimo", estoque.getEstoqueMinimo());

        response.setData(data);
        return response;
    }
}
