package com.probas.pruebaconexion.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.probas.pruebaconexion.Bebida;
import com.probas.pruebaconexion.Ensalada;
import com.probas.pruebaconexion.Hamburguesa;
import com.probas.pruebaconexion.Ingrediente;
import com.probas.pruebaconexion.Lasania;
import com.probas.pruebaconexion.Pasta;
import com.probas.pruebaconexion.Pedido;
import com.probas.pruebaconexion.Pizza;
import com.probas.pruebaconexion.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    private static final String ARG_PARAM1 = "ingredientes";
    private static final String ARG_PARAM2 = "bebidas";
    private static final String ARG_PARAM3 = "ensaladas";
    private static final String ARG_PARAM4 = "hamburguesas";
    private static final String ARG_PARAM5 = "pasta";
    private static final String ARG_PARAM6 = "lasania";

    private static final int TIPO_HIST_PEDIDO = 0;

    private static final int TIPO_INGREDIENTES = 1;
    private static final int TIPO_BEBIDAS = 2;
    private static final int TIPO_ENSALADAS = 4;
    private static final int TIPO_HAMBURGUESAS = 3;
    private static final int TIPO_PASTA = 5;
    private static final int TIPO_LASANIA = 6;


    private ArrayList<String> numeroPedido;
    private ArrayList<String> fecha;
    private ArrayList<String> total;

    public ArrayList<Hamburguesa> listaHamb;
    public  ArrayList<Lasania> listaLas;
    public  ArrayList<Ensalada> listaEnsa;
    public  ArrayList<Bebida> listaBebs;
    public  ArrayList<Pasta> listaPasta;
    private static ArrayList<Ingrediente> listaIngredientes;

    private int tipoObjeto;


    public static class PedidosViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView total, fecha, numPedido;
        PedidosViewHolder(View v) {
            super(v);
            total = v.findViewById(R.id.total);
            fecha = v.findViewById(R.id.fecha);
            numPedido = v.findViewById(R.id.numPedido);
        }
    }

    public static class IngredsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nombIngred, numIngreds;
        Button anhade, quita;
        int numeroDePizza;
        private WeakReference<ClickListener> listenerRef;

        IngredsViewHolder(View v, ClickListener clickListener, int numeroDePizza) {
            super(v);

            listenerRef = new WeakReference<>(clickListener);
            this.numeroDePizza = numeroDePizza;
            nombIngred = v.findViewById(R.id.ingrediente);
            anhade = v.findViewById(R.id.anadirIngred);
            numIngreds = v.findViewById(R.id.cantidadIngred);
            quita = v.findViewById(R.id.quitarIngred);
            anhade.setOnClickListener(this);
            quita.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int numIngred = Integer.parseInt(numIngreds.getText().toString());
            switch (v.getId()) {
                case R.id.anadirIngred:
                    if(numIngred>=0) {
                        numIngred++;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
                case R.id.quitarIngred:
                    if(numIngred>0) {
                        numIngred--;
                        numIngreds.setText(String.valueOf(numIngred));
                        Crea_pedido.pedido.getListaPizzas().get(numeroDePizza).quitaIngrediente(listaIngredientes.get(getAdapterPosition()).getNombre());
                    }
                    break;
            }
            listenerRef.get().onPositionClicked(v, getAdapterPosition());
        }
    }


    public static class BebidasViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView nombBebida;
        private WeakReference<ClickListener> listenerRef;

        BebidasViewHolder(View v, ClickListener clickListener) {
            super(v);
            listenerRef = new WeakReference<>(clickListener);
            nombBebida = v.findViewById(R.id.bebida);
        }
    }

    public static class HamburguesasViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView nombHamb;
        private WeakReference<ClickListener> listenerRef;

        HamburguesasViewHolder(View v, ClickListener clickListener) {
            super(v);
            listenerRef = new WeakReference<>(clickListener);
            nombHamb = v.findViewById(R.id.hamburguesa);
        }
    }

    private final ClickListener clickListener;
    private int numPizza;

    public MyAdapter(Bundle b, int tipo, ClickListener clickListener) {
        switch (tipo) {
            case TIPO_HIST_PEDIDO:
                numeroPedido = b.getStringArrayList("numeroPedido");
                fecha = b.getStringArrayList("fecha");
                total = b.getStringArrayList("total");
                break;
            case TIPO_INGREDIENTES:
                numPizza = b.getInt("numeroDePizza");
                listaIngredientes = (ArrayList<Ingrediente>) b.getSerializable(ARG_PARAM1);
                break;
            case TIPO_BEBIDAS:
                listaBebs = (ArrayList<Bebida>) b.getSerializable(ARG_PARAM2);
                break;
            case TIPO_HAMBURGUESAS:
                listaHamb = (ArrayList<Hamburguesa>) b.getSerializable(ARG_PARAM4);
                break;
        }
        this.clickListener=clickListener;
        tipoObjeto=tipo;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v=null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (tipoObjeto){
            case TIPO_HIST_PEDIDO:
                v = layoutInflater.inflate(R.layout.cont_recy_mis_pedidos, parent, false);
                return new PedidosViewHolder(v);

            case TIPO_INGREDIENTES:
                v = layoutInflater.inflate(R.layout.cont_recy_crea_pedido_ingredientes, parent, false);
                return new IngredsViewHolder(v, clickListener, numPizza);

            case TIPO_BEBIDAS:
                v = layoutInflater.inflate(R.layout.cont_recy_crea_pedido_bebidas, parent, false);
                return new BebidasViewHolder(v, clickListener);

            case TIPO_HAMBURGUESAS:
                v = layoutInflater.inflate(R.layout.cont_recy_crea_pedido_hamburguesas, parent, false);
                return new HamburguesasViewHolder(v, clickListener);

                /*
            default:
                v=null;
                */
        }
        return new PedidosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PedidosViewHolder) {
            ((PedidosViewHolder) holder).numPedido.setText(String.valueOf(numeroPedido.get(position)));
            ((PedidosViewHolder) holder).fecha.setText(fecha.get(position));
            ((PedidosViewHolder) holder).total.setText(String.valueOf(total.get(position)));
        }
        else if(holder instanceof IngredsViewHolder){
            ((IngredsViewHolder) holder).nombIngred.setText(String.valueOf(listaIngredientes.get(position).getNombre()));
        }
        else if(holder instanceof BebidasViewHolder){
            ((BebidasViewHolder) holder).nombBebida.setText(String.valueOf(listaBebs.get(position).getNombre()));
        }
        else if(holder instanceof HamburguesasViewHolder){
            ((HamburguesasViewHolder) holder).nombHamb.setText(String.valueOf(listaHamb.get(position).getNombre()));
        }
    }

    @Override
    public int getItemCount() {
        int res=0;
        switch (tipoObjeto) {
            case TIPO_HIST_PEDIDO:
                res = numeroPedido.size();
                break;
            case TIPO_INGREDIENTES:
                res = listaIngredientes.size();
                break;
            case TIPO_BEBIDAS:
                res = listaBebs.size();
                break;
            case TIPO_HAMBURGUESAS:
                res = listaHamb.size();
                break;
        }
        return res;
    }
}
