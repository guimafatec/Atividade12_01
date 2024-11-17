package br.edu.fateczl.atividade12_01;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.atividade12_01.controller.JogadorController;
import br.edu.fateczl.atividade12_01.controller.TimeController;
import br.edu.fateczl.atividade12_01.model.Jogador;
import br.edu.fateczl.atividade12_01.model.Time;
import br.edu.fateczl.atividade12_01.persistence.JogadorDao;
import br.edu.fateczl.atividade12_01.persistence.TimeDao;


public class JogadorFragment extends Fragment {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private View view;
    private EditText etCodigoJogador, etNomeJogador, etDataNascimento, etPesoJogador, etAlturaJogador;
    private Button btnBuscarJogador, btnInserirJogador, btnListarJogadores, btnModificarJogador, btnExcluirJogador;
    private Spinner spJogadorTime;
    private TextView tvListarJogador;
    private List<Time> times;
    private JogadorController jogadorCtrl;
    private TimeController timeCtrl;

    public JogadorFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jogador, container, false);
        etCodigoJogador = view.findViewById(R.id.etCodigoJogador);
        etNomeJogador = view.findViewById(R.id.etNomeJogador);
        etDataNascimento = view.findViewById(R.id.etDataNascimento);
        etPesoJogador = view.findViewById(R.id.etPesoJogador);
        etAlturaJogador = view.findViewById(R.id.etAlturaJogador);
        spJogadorTime = view.findViewById(R.id.spJogadorTime);
        btnBuscarJogador = view.findViewById(R.id.btnBuscarJogador);
        btnInserirJogador = view.findViewById(R.id.btnInserirJogador);
        btnModificarJogador = view.findViewById(R.id.btnModificarJogador);
        btnExcluirJogador = view.findViewById(R.id.btnExcluirJogador);
        btnListarJogadores = view.findViewById(R.id.btnListarJogadores);
        tvListarJogador = view.findViewById(R.id.tvListarJogador);
        tvListarJogador.setMovementMethod(new ScrollingMovementMethod());
        jogadorCtrl = new JogadorController(new JogadorDao(view.getContext()));
        timeCtrl = new TimeController(new TimeDao(view.getContext()));

        preencheSpinner();

        btnInserirJogador.setOnClickListener(op -> acaoInserir());
        btnModificarJogador.setOnClickListener(op -> acaoModificar());
        btnExcluirJogador.setOnClickListener(op -> acaoExcluir());
        btnBuscarJogador.setOnClickListener(op -> acaoBuscar());
        btnListarJogadores.setOnClickListener(op -> acaoListar());

        return view;
    }


    private void acaoInserir() {
        int spSelected = spJogadorTime.getSelectedItemPosition();
        if (spSelected > 0) {
            Jogador jogador = montaJogador();
            try {
                jogadorCtrl.inserir(jogador);
                Toast.makeText(view.getContext(), "Jogador INSERIDO com sucesso!", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(view.getContext(), "Selecione um Time ", Toast.LENGTH_LONG).show();
        }
    }

    private void acaoModificar() {
        int spSelected = spJogadorTime.getSelectedItemPosition();
        if (spSelected > 0) {
            Jogador jogador = montaJogador();
            try {
                jogadorCtrl.modificar(jogador);
                Toast.makeText(view.getContext(), "Jogador ATUALIZADO com sucesso!", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(view.getContext(), "Selecione um Time ", Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        Jogador jogador = montaJogador();
        try {
            jogadorCtrl.deletar(jogador);
            Toast.makeText(view.getContext(), "Jogador EXCLUÍDO com sucesso!", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void acaoBuscar() {
        Jogador jogador = montaJogador();
        try {
            times = timeCtrl.listar();
            jogador = jogadorCtrl.buscar(jogador);
            if (jogador.getNome() != null) {
                preencheCampos(jogador);
            } else {
                Toast.makeText(view.getContext(), "Jogador não encontrado", Toast.LENGTH_LONG).show();
                limparCampos();
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void acaoListar() {
        try {
            List<Jogador> jogadores = jogadorCtrl.listar();
            StringBuffer buffer = new StringBuffer();
            for (Jogador j: jogadores) {
                buffer.append(j.toString() + "\n");
            }
            tvListarJogador.setText(buffer.toString());
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void preencheSpinner(){
        Time time0 = new Time();
        time0.setCodigo(0);
        time0.setNome("Selecione um Time");
        time0.setCidade("");

        try {
            times = timeCtrl.listar();
            times.add(0, time0);

            ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, times);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spJogadorTime.setAdapter(adapter);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private Jogador montaJogador(){
        Jogador jogador = new Jogador();

        int id = Integer.parseInt(etCodigoJogador.getText().toString().isEmpty() ? "0" : etCodigoJogador.getText().toString());
        float peso = Float.parseFloat(etPesoJogador.getText().toString().isEmpty() ? "0" : etPesoJogador.getText().toString());
        float altura = Float.parseFloat(etAlturaJogador.getText().toString().isEmpty() ? "0" : etAlturaJogador.getText().toString());

        jogador.setId(id);
        jogador.setNome(etNomeJogador.getText().toString());
        jogador.setDataNasc(etDataNascimento.getText().toString());
        jogador.setPeso(peso);
        jogador.setAltura(altura);
        jogador.setTime((Time) spJogadorTime.getSelectedItem());
        return jogador;
    }

    private void limparCampos() {
        etCodigoJogador.setText("");
        etNomeJogador.setText("");
        etDataNascimento.setText("");
        etPesoJogador.setText("");
        etAlturaJogador.setText("");
        spJogadorTime.setSelection(0);
    }

    private void preencheCampos(Jogador jog){
        etCodigoJogador.setText(String.valueOf(jog.getId()));
        etNomeJogador.setText(jog.getNome());
        etDataNascimento.setText(jog.getDataNasc().toString());
        etPesoJogador.setText(String.valueOf(jog.getPeso()));
        etAlturaJogador.setText(String.valueOf(jog.getAltura()));

        int i = 1;
        for (Time time: times)  {
            if(time.getCodigo() == jog.getTime().getCodigo()) {
                spJogadorTime.setSelection(i);
            } else {
                i++;
            }
        }
        if (i > times.size()) {
            spJogadorTime.setSelection(0);
        }

    }
}