package com.example.a20213170_lab2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class Adaptador extends BaseAdapter {
    private String[] letters;
    private LayoutInflater letterInf;

    public Adaptador(Context context){
        letters = new String[26];
        for (int i=0; i < letters.length; i++ ){
            letters[i] = ""+(char)(i+'A'); //Inicia desde A y termina en Z
        }

        letterInf = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return letters.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Button btnLetter;
        if(view == null){
            btnLetter = (Button) letterInf.inflate(R.layout.letter, viewGroup, false);
        }else{
            btnLetter = (Button) view;
        }
        btnLetter.setText(letters[i]);
        return btnLetter;
    }
}
