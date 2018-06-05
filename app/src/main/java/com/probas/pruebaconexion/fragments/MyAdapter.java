package com.probas.pruebaconexion.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.probas.pruebaconexion.ClasesBasicas.Bebida;
import com.probas.pruebaconexion.ClasesBasicas.Ensalada;
import com.probas.pruebaconexion.ClasesBasicas.Hamburguesa;
import com.probas.pruebaconexion.ClasesBasicas.Ingrediente;
import com.probas.pruebaconexion.ClasesBasicas.Lasania;
import com.probas.pruebaconexion.ClasesBasicas.Pasta;
import com.probas.pruebaconexion.MainActivity;
import com.probas.pruebaconexion.Menu_principal;
import com.probas.pruebaconexion.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

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

    private ArrayList<Hamburguesa> listaHamb;
    private  ArrayList<Lasania> listaLas;
    private  ArrayList<Ensalada> listaEnsa;
    private  ArrayList<Bebida> listaBebs;
    private  ArrayList<Pasta> listaPasta;
    private static ArrayList<Ingrediente> listaIngredientes;

    private int tipoObjeto;


    static class PedidosViewHolder extends RecyclerView.ViewHolder {
        TextView total;
        TextView fecha;
        TextView numPedido;
        PedidosViewHolder(View v) {
            super(v);
            total = v.findViewById(R.id.total);
            fecha = v.findViewById(R.id.fecha);
            numPedido = v.findViewById(R.id.numPedido);
        }
    }

    static class IngredsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nombIngred, numIngreds;
        Button anhade, quita;
        int numeroDePizza;
        private WeakReference<ClickListener> listenerRef;

        IngredsViewHolder(View v, ClickListener clickListener, int numeroDePizza) {
            super(v);

            listenerRef = new WeakReference<>(clickListener);
            this.numeroDePizza = numeroDePizza;
            nombIngred = v.findViewById(R.id.nombre_comp_pedido);
            anhade = v.findViewById(R.id.anadir);
            numIngreds = v.findViewById(R.id.cantidad_comp_pedido);
            quita = v.findViewById(R.id.quitar);
            anhade.setOnClickListener(this);
            quita.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int numIngred = Integer.parseInt(numIngreds.getText().toString());
            switch (v.getId()) {
                case R.id.anadir:
                    if(numIngred>=0) {
                        numIngred++;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
                case R.id.quitar:
                    if(numIngred>0) {
                        numIngred--;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
            }
            listenerRef.get().onPositionClicked(v, getAdapterPosition());
        }
    }


    static class BebidasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        TextView nombBebida, numIngreds;
        Button anhade, quita;
        private WeakReference<ClickListener> listenerRef;

        BebidasViewHolder(View v, ClickListener clickListener) {
            super(v);
            listenerRef = new WeakReference<>(clickListener);
            nombBebida = v.findViewById(R.id.nombre_comp_pedido);
            anhade = v.findViewById(R.id.anadir);
            numIngreds = v.findViewById(R.id.cantidad_comp_pedido);
            quita = v.findViewById(R.id.quitar);
            anhade.setOnClickListener(this);
            quita.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int numIngred = Integer.parseInt(numIngreds.getText().toString());
            switch (v.getId()) {
                case R.id.anadir:
                    if(numIngred>=0) {
                        numIngred++;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
                case R.id.quitar:
                    if(numIngred>0) {
                        numIngred--;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
            }
            listenerRef.get().onPositionClicked(v, getAdapterPosition());
        }
    }

    static class HamburguesasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        TextView nombHamb, numIngreds;
        Button anhade, quita;
        private WeakReference<ClickListener> listenerRef;

        HamburguesasViewHolder(View v, ClickListener clickListener) {
            super(v);
            listenerRef = new WeakReference<>(clickListener);
            nombHamb = v.findViewById(R.id.nombre_comp_pedido);
            anhade = v.findViewById(R.id.anadir);
            numIngreds = v.findViewById(R.id.cantidad_comp_pedido);
            quita = v.findViewById(R.id.quitar);
            anhade.setOnClickListener(this);
            quita.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int numIngred = Integer.parseInt(numIngreds.getText().toString());
            switch (v.getId()) {
                case R.id.anadir:
                    if(numIngred>=0) {
                        numIngred++;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
                case R.id.quitar:
                    if(numIngred>0) {
                        numIngred--;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
            }
            listenerRef.get().onPositionClicked(v, getAdapterPosition());
        }
    }

    static class LasaniaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nombLas, numIngreds;
        Button anhade, quita;
        private WeakReference<ClickListener> listenerRef;

        LasaniaViewHolder(View v, ClickListener clickListener) {
            super(v);
            listenerRef = new WeakReference<>(clickListener);
            nombLas = v.findViewById(R.id.nombre_comp_pedido);
            anhade = v.findViewById(R.id.anadir);
            numIngreds = v.findViewById(R.id.cantidad_comp_pedido);
            quita = v.findViewById(R.id.quitar);
            anhade.setOnClickListener(this);
            quita.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int numIngred = Integer.parseInt(numIngreds.getText().toString());
            switch (v.getId()) {
                case R.id.anadir:
                    if(numIngred>=0) {
                        numIngred++;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
                case R.id.quitar:
                    if(numIngred>0) {
                        numIngred--;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
            }
            listenerRef.get().onPositionClicked(v, getAdapterPosition());
        }
    }

    static class EnsaladaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        TextView nombEnsal, numIngreds;
        Button anhade, quita;
        private WeakReference<ClickListener> listenerRef;

        EnsaladaViewHolder(View v, ClickListener clickListener) {
            super(v);
            listenerRef = new WeakReference<>(clickListener);
            nombEnsal = v.findViewById(R.id.nombre_comp_pedido);
            anhade = v.findViewById(R.id.anadir);
            numIngreds = v.findViewById(R.id.cantidad_comp_pedido);
            quita = v.findViewById(R.id.quitar);
            anhade.setOnClickListener(this);
            quita.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int numIngred = Integer.parseInt(numIngreds.getText().toString());
            switch (v.getId()) {
                case R.id.anadir:
                    if(numIngred>=0) {
                        numIngred++;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
                case R.id.quitar:
                    if(numIngred>0) {
                        numIngred--;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
            }
            listenerRef.get().onPositionClicked(v, getAdapterPosition());
        }
    }

    static class PastaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nombPasta, numIngreds;
        Button anhade, quita;
        private WeakReference<ClickListener> listenerRef;

        PastaViewHolder(View v, ClickListener clickListener) {
            super(v);
            listenerRef = new WeakReference<>(clickListener);
            nombPasta = v.findViewById(R.id.nombre_comp_pedido);
            anhade = v.findViewById(R.id.anadir);
            numIngreds = v.findViewById(R.id.cantidad_comp_pedido);
            quita = v.findViewById(R.id.quitar);
            anhade.setOnClickListener(this);
            quita.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int numIngred = Integer.parseInt(numIngreds.getText().toString());
            switch (v.getId()) {
                case R.id.anadir:
                    if(numIngred>=0) {
                        numIngred++;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
                case R.id.quitar:
                    if(numIngred>0) {
                        numIngred--;
                        numIngreds.setText(String.valueOf(numIngred));
                    }
                    break;
            }
            listenerRef.get().onPositionClicked(v, getAdapterPosition());
        }
    }

    private final ClickListener clickListener;
    private int numPizza;

    public MyAdapter(Bundle b, int tipo, ClickListener clickListener) {
        switch (tipo) {
            case TIPO_HIST_PEDIDO:
                numeroPedido = b.getStringArrayList(Menu_principal.context.getString(R.string.key_num_pedido_mis_pedidos));
                fecha = b.getStringArrayList(Menu_principal.context.getString(R.string.key_fecha_pedido_mis_pedidos));
                total = b.getStringArrayList(Menu_principal.context.getString(R.string.key_total_mis_pedidos));
                break;
            case TIPO_INGREDIENTES:
                numPizza = b.getInt(Menu_principal.context.getString(R.string.key_num_pizza_my_adapter));
                listaIngredientes = (ArrayList<Ingrediente>) MainActivity.listaIngredientes;
                break;
            case TIPO_BEBIDAS:
                listaBebs = (ArrayList<Bebida>) MainActivity.listaBebs;
                break;
            case TIPO_HAMBURGUESAS:
                listaHamb = (ArrayList<Hamburguesa>) MainActivity.listaHamb;
                break;
            case TIPO_LASANIA:
                listaLas = (ArrayList<Lasania>) MainActivity.listaLas;
                break;
            case TIPO_ENSALADAS:
                listaEnsa = (ArrayList<Ensalada>) MainActivity.listaEnsa;
                break;
            case TIPO_PASTA:
                listaPasta = (ArrayList<Pasta>) MainActivity.listaPasta;
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
                v = layoutInflater.inflate(R.layout.cont_recy_crea_pedido, parent, false);
                return new IngredsViewHolder(v, clickListener, numPizza);

            case TIPO_BEBIDAS:
                v = layoutInflater.inflate(R.layout.cont_recy_crea_pedido, parent, false);
                return new BebidasViewHolder(v, clickListener);

            case TIPO_HAMBURGUESAS:
                v = layoutInflater.inflate(R.layout.cont_recy_crea_pedido, parent, false);
                return new HamburguesasViewHolder(v, clickListener);

            case TIPO_LASANIA:
                v = layoutInflater.inflate(R.layout.cont_recy_crea_pedido, parent, false);
                return new LasaniaViewHolder(v, clickListener);

            case TIPO_ENSALADAS:
                v = layoutInflater.inflate(R.layout.cont_recy_crea_pedido, parent, false);
                return new EnsaladaViewHolder(v, clickListener);
            case TIPO_PASTA:
                v = layoutInflater.inflate(R.layout.cont_recy_crea_pedido, parent, false);
                return new PastaViewHolder(v, clickListener);
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
        else if(holder instanceof LasaniaViewHolder){
            ((LasaniaViewHolder) holder).nombLas.setText(String.valueOf(listaLas.get(position).getNombre()));
        }
        else if(holder instanceof EnsaladaViewHolder){
            ((EnsaladaViewHolder) holder).nombEnsal.setText(String.valueOf(listaEnsa.get(position).getNombre()));
        }
        else if(holder instanceof PastaViewHolder){
            ((PastaViewHolder) holder).nombPasta.setText(String.valueOf(listaPasta.get(position).getNombre()));
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
            case TIPO_LASANIA:
                res = listaLas.size();
                break;
            case TIPO_ENSALADAS:
                res = listaEnsa.size();
                break;
            case TIPO_PASTA:
                res = listaPasta.size();
                break;
        }
        return res;
    }
}
