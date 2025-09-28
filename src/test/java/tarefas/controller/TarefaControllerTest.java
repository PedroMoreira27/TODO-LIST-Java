package tarefas.controller;

import tarefas.Application;
import tarefas.model.Tarefa;
import tarefas.model.StatusTarefa;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TarefaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void criarListarObterExcluir_fluxoBasico() throws Exception {
        Tarefa t = new Tarefa();
        t.setNome("Teste unitário");
        t.setDescricao("Descrição");
        t.setStatus(StatusTarefa.PENDENTE);
        t.setObservacoes("Observ");

        String json = mapper.writeValueAsString(t);

        // criar
        String location = mvc.perform(post("/api/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        // listar
        mvc.perform(get("/api/tarefas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        // obter por id
        mvc.perform(get(location))
                .andExpect(status().isOk());

        // delete
        mvc.perform(delete(location))
                .andExpect(status().isNoContent());
    }
}
