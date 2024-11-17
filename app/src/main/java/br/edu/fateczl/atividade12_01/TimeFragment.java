package br.edu.fateczl.atividade12_01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.atividade12_01.controller.TimeController;
import br.edu.fateczl.atividade12_01.model.Time;
import br.edu.fateczl.atividade12_01.persistence.TimeDao;


public class TimeFragment extends Fragment {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private View view;
    private EditText etCodigoTime, etNomeTime, etCidadeTime;
    private Button btnBuscarTime, btnInserirTime, btnListarTimes, btnModificarTime, btnExcluirTime;
    private TextView tvListarTimes;
    private TimeController timeCtrl;


    public TimeFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time, container, false);
        etCodigoTime = view.findViewById(R.id.etCodigoTime);
        etNomeTime = view.findViewById(R.id.etNomeTime);
        etCidadeTime = view.findViewById(R.id.etCidadeTime);
        btnBuscarTime = view.findViewById(R.id.btnBuscarTime);
        btnInserirTime = view.findViewById(R.id.btnInserirTime);
        btnModificarTime = view.findViewById(R.id.btnModificarTime);
        btnExcluirTime = view.findViewById(R.id.btnExcluirTime);
        btnListarTimes = view.findViewById(R.id.btnListarTimes);
        tvListarTimes = view.findViewById(R.id.tvListarTimes);

        timeCtrl = new TimeController(new TimeDao(view.getContext()));

        btnInserirTime.setOnClickListener(op -> acaoInserir());
        btnModificarTime.setOnClickListener(op -> acaoModificar());
        btnExcluirTime.setOnClickListener(op -> acaoExcluir());
        btnBuscarTime.setOnClickListener(op -> acaoBuscar());
        btnListarTimes.setOnClickListener(op -> acaoListar());
        return view;
    }

    private void acaoInserir() {
        Time time = montaTime();
        try {
            timeCtrl.inserir(time);
            Toast.makeText(view.getContext(), "Time INSERIDO com Sucesso", Toast.LENGTH_LONG).show();
            limpaCampos();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoModificar() {
        Time time = montaTime();
        try {
            timeCtrl.modificar(time);
            Toast.makeText(view.getContext(), "Time ATUALIZADO com Sucesso", Toast.LENGTH_LONG).show();
            limpaCampos();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        Time time = montaTime();
        try {
            timeCtrl.deletar(time);
            Toast.makeText(view.getContext(), "Time EXCLUÍDO com Sucesso", Toast.LENGTH_LONG).show();
            limpaCampos();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoBuscar() {
        System.out.println("BUSCANDO");
        Time time = montaTime();
        try {
            time = timeCtrl.buscar(time);
            if (time.getNome() != null) {
                preencheCampos(time);
            } else {
                Toast.makeText(view.getContext(), "Time NÃO encontrado", Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Time> times = timeCtrl.listar();
            StringBuffer buffer = new StringBuffer();
            for (Time t: times) {
                buffer.append(t.toString() + "\n");
            }
            tvListarTimes.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Time montaTime() {
        Time time = new Time();
        int codigo = Integer.parseInt(etCodigoTime.getText().toString().isEmpty() ? "0" : etCodigoTime.getText().toString());
        time.setCodigo(codigo);
        time.setNome(etNomeTime.getText().toString());
        time.setCidade(etCidadeTime.getText().toString());

        return time;
    }

    private void preencheCampos(Time time) {
        etCodigoTime.setText(String.valueOf(time.getCodigo()));
        etNomeTime.setText(time.getNome());
        etCidadeTime.setText(time.getCidade());
    }

    private void limpaCampos(){
        etCodigoTime.setText("");
        etNomeTime.setText("");
        etCidadeTime.setText("");
    }
}