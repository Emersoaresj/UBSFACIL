package br.com.postech.ubsfacil.service;

import br.com.postech.ubsfacil.api.dto.ResponseDto;
import br.com.postech.ubsfacil.api.dto.estoque.MovimentacaoResponseDto;
import br.com.postech.ubsfacil.api.dto.estoque.TipoMovimentacao;
import br.com.postech.ubsfacil.api.mapper.EstoqueMapper;
import br.com.postech.ubsfacil.domain.Estoque;
import br.com.postech.ubsfacil.domain.Movimentacao;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.domain.exceptions.ErroNegocioException;
import br.com.postech.ubsfacil.domain.exceptions.estoque.EstoqueNotFoundException;
import br.com.postech.ubsfacil.gateway.ports.alertas.AlertaServicePort;
import br.com.postech.ubsfacil.gateway.ports.estoque.EstoqueRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.estoque.EstoqueServicePort;
import br.com.postech.ubsfacil.gateway.ports.MovimentacaoRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.ubs.UbsRepositoryPort;
import br.com.postech.ubsfacil.gateway.ports.insumo.InsumoRepositoryPort;
import br.com.postech.ubsfacil.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class EstoqueServiceImpl implements EstoqueServicePort {


    private final EstoqueRepositoryPort estoqueRepositoryPort;
    private final UbsRepositoryPort ubsRepositoryPort;
    private final InsumoRepositoryPort insumoRepositoryPort;
    private final MovimentacaoRepositoryPort movimentacaoRepositoryPort;
    private final AlertaServicePort alertaServicePort;

    public EstoqueServiceImpl(EstoqueRepositoryPort estoqueRepositoryPort, UbsRepositoryPort ubsRepositoryPort, InsumoRepositoryPort insumoRepositoryPort, MovimentacaoRepositoryPort movimentacaoRepositoryPort, AlertaServicePort alertaServicePort) {
        this.estoqueRepositoryPort = estoqueRepositoryPort;
        this.ubsRepositoryPort = ubsRepositoryPort;
        this.insumoRepositoryPort = insumoRepositoryPort;
        this.movimentacaoRepositoryPort = movimentacaoRepositoryPort;
        this.alertaServicePort = alertaServicePort;
    }


    @Override
    public ResponseDto cadastrarEstoque(Estoque estoque) {
        try {
            Estoque.validarCnes(estoque.getUbsCnes());
            estoque.validarCamposObrigatorios();
            estoque.validarDataValidade();

            if(estoque.isEstoqueBaixo()) {
                log.warn("Estoque está abaixo do estoque mínimo");
                throw new ErroNegocioException("A quantidade em estoque não pode ser menor que o estoque mínimo.");
            }
            if (estoqueRepositoryPort.findByCnesAndSku(estoque.getUbsCnes(), estoque.getInsumoSku()).isPresent()) {
                log.error("Estoque com SKU de Insumo {} já cadastrado para a UBS {}", estoque.getInsumoSku(), estoque.getUbsCnes());
                throw new ErroNegocioException("Estoque com SKU " + estoque.getInsumoSku() + " já cadastrado para a UBS " + estoque.getUbsCnes() + ".");
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

    @Override
    public Estoque buscarEstoquePorId(Integer idEstoque) {
        try {
            return estoqueRepositoryPort.findByIdEstoque(idEstoque)
                    .orElseThrow(() -> new EstoqueNotFoundException("Estoque com ID " + idEstoque + " não localizado."));
        } catch (EstoqueNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar Estoque", e);
            throw new ErroInternoException("Erro interno ao tentar buscar Estoque: " + e.getMessage());
        }
    }

    @Override
    public Optional<Estoque> buscarPorFiltro(String cnes, String sku) {
        try {
            Estoque.validarCnes(cnes);
            if (ubsRepositoryPort.findByCnes(cnes).isEmpty()){
                log.error("UBS com CNES {} não encontrada", cnes);
                throw new ErroNegocioException("Não foi possível localizar uma UBS com o CNES " + cnes + " fornecido.");
            }
            if (insumoRepositoryPort.findBySku(sku).isEmpty()){
                log.error("Insumo com SKU {} não encontrado", sku);
                throw new ErroNegocioException("Não foi possível localizar um Insumo com o SKU " + sku + " fornecido.");
            }

            return estoqueRepositoryPort.findByCnesAndSku(cnes, sku);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar Estoques por filtro", e);
            throw new ErroInternoException("Erro interno ao tentar buscar Estoques por filtro: " + e.getMessage());
        }
    }

    @Override
    public List<Estoque> buscarTodos() {
        try {
            return estoqueRepositoryPort.findAll();
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar todos os Estoques", e);
            throw new ErroInternoException("Erro interno ao tentar buscar todos os Estoques: " + e.getMessage());
        }
    }

    @Override
    public ResponseDto atualizarEstoque(Integer idEstoque, Estoque estoque) {
        try {
            estoque.validarDataValidade();
            if(estoque.isEstoqueBaixo()) {
                log.warn("Estoque com ID {} está abaixo do estoque mínimo", idEstoque);
                throw new ErroNegocioException("A quantidade em estoque não pode ser menor que o estoque mínimo.");
            }

            Estoque existente = estoqueRepositoryPort.findByIdEstoque(idEstoque)
                    .orElseThrow(() -> new EstoqueNotFoundException("Estoque com ID " + idEstoque + " não localizado."));

            estoque.setIdEstoque(existente.getIdEstoque());
            estoque.setUbsCnes(existente.getUbsCnes());
            estoque.setInsumoSku(existente.getInsumoSku());

            Estoque atualizado = estoqueRepositoryPort.atualizarEstoque(estoque);
            return montaResponse(atualizado, "atualizar");
        } catch (EstoqueNotFoundException | ErroNegocioException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar Estoque", e);
            throw new ErroInternoException("Erro interno ao tentar atualizar Estoque: " + e.getMessage());
        }
    }

    @Override
    public void deletarEstoque(Integer idEstoque) {
        try {
            Estoque existente = estoqueRepositoryPort.findByIdEstoque(idEstoque)
                    .orElseThrow(() -> new EstoqueNotFoundException("Estoque com ID " + idEstoque + " não localizado."));

            estoqueRepositoryPort.deletarEstoque(existente.getIdEstoque());
        } catch (EstoqueNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao deletar Estoque", e);
            throw new ErroInternoException("Erro interno ao tentar deletar Estoque: " + e.getMessage());
        }
    }

    @Override
    public MovimentacaoResponseDto registrarMovimentacao(Estoque estoque, String tipoMovimentacaoStr, String motivo) {
        try {
            estoque.validarCamposObrigatorios();
            estoque.validarDataValidade();
            Estoque.validarCnes(estoque.getUbsCnes());

            Movimentacao.validarTipoMovimentacao(tipoMovimentacaoStr);

            TipoMovimentacao tipoMovimentacao = TipoMovimentacao.valueOf(tipoMovimentacaoStr.toUpperCase());

            if (ubsRepositoryPort.findByCnes(estoque.getUbsCnes()).isEmpty()) {
                throw new ErroNegocioException("UBS com CNES " + estoque.getUbsCnes() + " não encontrada.");
            }
            if (insumoRepositoryPort.findBySku(estoque.getInsumoSku()).isEmpty()) {
                throw new ErroNegocioException("Insumo com SKU " + estoque.getInsumoSku() + " não encontrado.");
            }

            Estoque estoqueAtual = estoqueRepositoryPort.findByCnesAndSku(estoque.getUbsCnes(), estoque.getInsumoSku())
                    .orElse(null);

            boolean isEntrada = tipoMovimentacao == TipoMovimentacao.ENTRADA;

            if (estoqueAtual == null) {
                if (!isEntrada) {
                    throw new ErroNegocioException("Não é possível realizar saída. Estoque ainda não cadastrado.");
                }
                estoqueRepositoryPort.cadastrarEstoque(estoque);
                estoqueAtual = estoque;

                criarMovimentacao(estoque, motivo, tipoMovimentacao);

                alertaServicePort.verificarEAvisar(estoqueAtual);
                MovimentacaoResponseDto responseDto = EstoqueMapper.INSTANCE.domainToMovimentacaoResponse(estoqueAtual);
                responseDto.setMessage(String.format("Estoque criado e movimentação de %s registrada com sucesso.", tipoMovimentacao));
                responseDto.setTipoMovimentacao(tipoMovimentacao);
                return responseDto;
            } else {
                int novaQuantidade = isEntrada
                        ? estoqueAtual.getQuantidade() + estoque.getQuantidade()
                        : estoqueAtual.getQuantidade() - estoque.getQuantidade();

                if (novaQuantidade < 0) {
                    throw new ErroNegocioException("Estoque insuficiente para saída. Quantidade disponível: " + estoqueAtual.getQuantidade());
                }

                estoqueAtual.setQuantidade(novaQuantidade);
                estoqueRepositoryPort.atualizarEstoque(estoqueAtual);
            }

            criarMovimentacao(estoque, motivo, tipoMovimentacao);

            alertaServicePort.verificarEAvisar(estoqueAtual);

            MovimentacaoResponseDto responseDto = EstoqueMapper.INSTANCE.domainToMovimentacaoResponse(estoqueAtual);
            responseDto.setMessage(String.format("Estoque atualizado e movimentação de %s registrada com sucesso.", tipoMovimentacao));
            responseDto.setTipoMovimentacao(tipoMovimentacao);
            return responseDto;
        } catch (ErroNegocioException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao registrar movimentação de estoque", e);
            throw new ErroInternoException("Erro ao registrar movimentação: " + e.getMessage());
        }
    }

    @Override
    public List<Estoque> buscarPorCnes(String cnes) {
        try {
            Estoque.validarCnes(cnes);
            if (ubsRepositoryPort.findByCnes(cnes).isEmpty()){
                log.error("UBS com CNES {} não encontrada", cnes);
                throw new ErroNegocioException("Não foi possível localizar uma UBS com o CNES " + cnes + " fornecido.");
            }
            return estoqueRepositoryPort.findByUbsCnes(cnes);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar Estoques por CNES", e);
            throw new ErroInternoException("Erro interno ao tentar buscar Estoques por CNES: " + e.getMessage());
        }
    }

    @Override
    public List<Estoque> buscarPorSku(String sku) {
        try {
            if (insumoRepositoryPort.findBySku(sku).isEmpty()){
                log.error("Insumo com SKU {} não encontrado", sku);
                throw new ErroNegocioException("Não foi possível localizar um Insumo com o SKU " + sku + " fornecido.");
            }
            return estoqueRepositoryPort.buscaPorSku(sku);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar Estoques por filtro", e);
            throw new ErroInternoException("Erro interno ao tentar buscar Estoques por filtro: " + e.getMessage());
        }
    }

    private void criarMovimentacao(Estoque estoque, String motivo, TipoMovimentacao tipoMovimentacao) {
        Movimentacao movimentacao = new Movimentacao(
                null,
                estoque.getUbsCnes(),
                estoque.getInsumoSku(),
                tipoMovimentacao,
                estoque.getQuantidade(),
                motivo != null ? motivo : "Movimentação automática",
                LocalDate.now()
        );
        movimentacaoRepositoryPort.salvarMovimentacao(movimentacao);
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
