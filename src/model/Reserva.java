package model;
import java.text.DecimalFormat;

public class Reserva {
    private Hospede hospede;
    private Quarto quarto;
    private int dias;

    public Reserva(Hospede hospede, Quarto quarto, int dias) {
        this.hospede = hospede;
        this.quarto = quarto;
        this.dias = dias;
    }

    public int getDias() { return dias; }

    public double calcularTotal() {
        return quarto.getValorDiaria() * dias;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("R$ #,##0.00");
        return hospede.getNome() + " reservou o " + quarto.getTipo() + " (Quarto " + quarto.getNumero() + ") por " + dias + " dias. Total: " + df.format(calcularTotal());
    }
}
