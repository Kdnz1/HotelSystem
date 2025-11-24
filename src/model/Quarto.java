package model;
import java.text.DecimalFormat;

public class Quarto {
    private int numero;
    private String tipo;
    private double valorDiaria;

    public Quarto(int numero, String tipo, double valorDiaria) {
        this.numero = numero;
        this.tipo = tipo;
        this.valorDiaria = valorDiaria;
    }

    public int getNumero() { return numero; }
    public double getValorDiaria() { return valorDiaria; }
    public String getTipo() { return tipo; }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("R$ #,##0.00");
        return "Quarto " + numero + " (" + tipo + ") - " + df.format(valorDiaria) + " por diária";
    }
}
