package br.edu.fateczl.atividade12_01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class InicioFragment extends Fragment {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private View view;
    private TextView tvInicio;

    public InicioFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_inicio, container, false);
        tvInicio = view.findViewById(R.id.tvInicio);
        return view;
    }
}