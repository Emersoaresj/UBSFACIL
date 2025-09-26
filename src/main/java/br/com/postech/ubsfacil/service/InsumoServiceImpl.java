package br.com.postech.ubsfacil.service;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.domain.Insumo;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;
import br.com.postech.ubsfacil.domain.exceptions.insumos.InsumoNotFoundException;
import br.com.postech.ubsfacil.gateway.ports.estoque.EstoqueRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.insumo.InsumoRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.insumo.InsumoServicePort;
import br.com.postech.ubsfacil.utils.ConstantUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InsumoServiceImpl implements InsumoServicePort {

    private final InsumoRepositoryPort repositoryPort;
    private final EstoqueRepositoryPort estoqueRepositoryPort;

    public InsumoServiceImpl(InsumoRepositoryPort repositoryPort, EstoqueRepositoryPort estoqueRepositoryPort) {
        this.repositoryPort = repositoryPort;
        this.estoqueRepositoryPort = estoqueRepositoryPort;
    }


    @Override
    public ResponseDto cadastrarInsumo(Insumo insumo) {
        try {

            insumo.validarCamposObrigatorios();

            if (repositoryPort.findByBarcode(insumo.getBarcode()).isPresent()) {
                log.error("Insumo com Barcode {} já cadastrado", insumo.getBarcode());
                throw new ErroNegocioException("Insumo com Barcode " + insumo.getBarcode() + " já cadastrado.");
            }

            insumo.validarRegraPorTipo();

            Insumo saved = repositoryPort.cadastrarInsumo(insumo);

            return montaResponse(saved, "cadastro");
        } catch (ErroNegocioException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao cadastrar Insumo", e);
            throw new ErroInternoException("Erro interno ao tentar cadastrar Insumo: " + e.getMessage());
        }
    }

    @Override
    public Insumo buscarInsumoPorSku(String sku) {
        try {
            return repositoryPort.findByBarcode(sku)
                    .orElseThrow(() -> new InsumoNotFoundException("Insumo com Barcode " + sku + " não localizado."));
        } catch (InsumoNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar Insumo", e);
            throw new ErroInternoException("Erro interno ao tentar buscar Insumo: " + e.getMessage());
        }
    }

    @Override
    public List<Insumo> buscarPorTipo(String tipo) {
        try {
            Insumo.validarTiposInsumo(tipo);
            return repositoryPort.findByTipo(tipo.toUpperCase());
        } catch (InsumoNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar Insumos por tipo", e);
            throw new ErroInternoException("Erro interno ao tentar buscar Insumos por tipo: " + e.getMessage());
        }
    }

    @Override
    public List<Insumo> buscarTodos() {
        try {
            return repositoryPort.buscarTodos();
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar todos os Insumos", e);
            throw new ErroInternoException("Erro interno ao tentar buscar todos os Insumos: " + e.getMessage());
        }
    }

    @Override
    public Insumo atualizarInsumo(Insumo insumo) {
        try {
            insumo.validarCamposObrigatorios();
            insumo.validarRegraPorTipo();

            Insumo existente = repositoryPort.findByBarcode(insumo.getSku())
                    .orElseThrow(() -> new InsumoNotFoundException("Insumo com Barcode " + insumo.getSku() + " não localizado."));

            insumo.setIdInsumo(existente.getIdInsumo());

            return repositoryPort.atualizarInsumo(insumo);
        } catch (InsumoNotFoundException | ErroNegocioException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar Insumo", e);
            throw new ErroInternoException("Erro interno ao tentar atualizar Insumo: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deletarInsumo(String sku) {
        try {
            repositoryPort.findByBarcode(sku)
                    .orElseThrow(() -> new InsumoNotFoundException(ConstantUtils.INSUMO_NAO_ENCONTRADO));

            estoqueRepositoryPort.deletarTodosPorInsumoSku(sku);
            repositoryPort.deletarInsumo(sku);

        } catch (InsumoNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao excluir insumo", e);
            throw new ErroInternoException("Erro interno ao tentar excluir insumo: " + e.getMessage());
        }
    }

    private ResponseDto montaResponse(Insumo insumo, String acao) {
        ResponseDto response = new ResponseDto();

        response.setMessage("cadastro".equals(acao) ? ConstantUtils.INSUMO_CADASTRADO : ConstantUtils.INSUMO_ATUALIZADO);

        Map<String, Object> data = new HashMap<>();
        data.put("nomeInsumo", insumo.getNome());
        data.put("skuInsumo", insumo.getSku());
        data.put("tipoInsumo", insumo.getTipo());
        data.put("validadeControlada", insumo.isValidadeControlada());

        response.setData(data);
        return response;
    }
}
