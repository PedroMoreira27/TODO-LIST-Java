package tarefas.service;

import tarefas.model.Tarefa;
import tarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TarefaService {

    private final TarefaRepository repo;

    public TarefaService(TarefaRepository repo) {
        this.repo = repo;
    }

    public Tarefa criar(Tarefa tarefa) {
        tarefa.setDataCriacao(OffsetDateTime.now());
        tarefa.setDataAtualizacao(OffsetDateTime.now());
        return repo.save(tarefa);
    }

    public List<Tarefa> listarTodas() {
        return repo.findAll();
    }

    public Optional<Tarefa> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Optional<Tarefa> atualizar(Long id, Tarefa dados) {
        return repo.findById(id).map(existing -> {
            if (dados.getNome() != null) existing.setNome(dados.getNome());
            existing.setDescricao(dados.getDescricao());
            if (dados.getStatus() != null) existing.setStatus(dados.getStatus());
            existing.setObservacoes(dados.getObservacoes());
            existing.setDataAtualizacao(OffsetDateTime.now());
            return repo.save(existing);
        });
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }
}
