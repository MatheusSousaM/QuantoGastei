/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controll;
import java.sql.Time;
import java.util.Date;
/**
 *
 * @author sousa
 */
public class Agenda {
    int id;
    int user_id;
    double gastos;
    Date data;
    Time hora;
    String tipo_gasto;
  
    
    public Agenda( int id, int user_id, double gastos, Date data, Time hora, String tipo_gastos){
        this.id = id;
        this.user_id = user_id;
        this.gastos = gastos;
        this.data = data;
        this.hora = hora;
        this.tipo_gasto = tipo_gastos;
}
  public Agenda(int user_id, double gastos, Date data, Time hora, String tipo_gastos){
        this.user_id = user_id;
        this.gastos = gastos;
        this.data = data;
        this.hora = hora;
        this.tipo_gasto = tipo_gastos;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getGastos() {
        return gastos;
    }

    public void setGastos(double gastos) {
        this.gastos = gastos;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo_gasto() {
        return tipo_gasto;
    }

    public void setTipo_gasto(String tipo_gasto) {
        this.tipo_gasto = tipo_gasto;
    }
    
    
}
