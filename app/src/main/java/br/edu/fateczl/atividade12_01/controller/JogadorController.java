package br.edu.fateczl.atividade12_01.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.atividade12_01.IController;
import br.edu.fateczl.atividade12_01.model.Jogador;
import br.edu.fateczl.atividade12_01.persistence.JogadorDao;

public class JogadorController implements IController<Jogador> {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private final JogadorDao jDao;

    public JogadorController(JogadorDao jDao) {
        this.jDao = jDao;
    }

    @Override
    public void inserir(Jogador jogador) throws SQLException {
        if (jDao.open() == null) {
            jDao.open();
        }
        jDao.insert(jogador);
        jDao.close();
    }

    @Override
    public void modificar(Jogador jogador) throws SQLException {
        if (jDao.open() == null) {
            jDao.open();
        }
        jDao.update(jogador);
        jDao.close();
    }

    @Override
    public void deletar(Jogador jogador) throws SQLException {
        if (jDao.open() == null) {
            jDao.open();
        }
        jDao.delete(jogador);
        jDao.close();
    }

    @Override
    public Jogador buscar(Jogador jogador) throws SQLException {
        if (jDao.open() == null) {
            jDao.open();
        }
        return jDao.findOne(jogador);
    }

    @Override
    public List<Jogador> listar() throws SQLException {
        if (jDao.open() == null) {
            jDao.open();
        }
        return jDao.findAll();
    }
}
