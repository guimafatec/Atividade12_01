package br.edu.fateczl.atividade12_01.persistence;

import java.sql.SQLException;

public interface ITimeDao {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    public TimeDao open() throws SQLException;
    public void close();
}
