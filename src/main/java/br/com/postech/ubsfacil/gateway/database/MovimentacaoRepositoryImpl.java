package br.com.postech.ubsfacil.gateway.database;

import br.com.postech.ubsfacil.api.mapper.MovimentacaoMapper;
import br.com.postech.ubsfacil.domain.Movimentacao;
import br.com.postech.ubsfacil.domain.exceptions.ErroInternoException;
import br.com.postech.ubsfacil.gateway.database.entity.MovimentacaoEntity;
import br.com.postech.ubsfacil.gateway.database.repository.MovimentacaoJpaRepository;
import br.com.postech.ubsfacil.gateway.ports.MovimentacaoRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class MovimentacaoRepositoryImpl implements MovimentacaoRepositoryPort {

    private final MovimentacaoJpaRepository movimentacaoJpaRepository;

    public MovimentacaoRepositoryImpl(MovimentacaoJpaRepository movimentacaoJpaRepository) {
        this.movimentacaoJpaRepository = movimentacaoJpaRepository;
    }

    @Override
    public void salvarMovimentacao(Movimentacao movimentacao) {
        try {
            MovimentacaoEntity movimentacaoEntity = MovimentacaoMapper.INSTANCE.domainToEntity(movimentacao);
            movimentacaoJpaRepository.save(movimentacaoEntity);
        } catch (Exception e) {
            log.error("Erro ao cadastrar Movimentação", e);
            throw new ErroInternoException("Erro ao cadastrar Movimentação: " + e.getMessage());
        }
    }
}
