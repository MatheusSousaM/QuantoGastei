/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controll;

/**
 *
 * @author sousa
 */
public class Usuario {
    int id;
    String email, nome, senha;

public Usuario(int id, String email, String nome, String senha){
    this.id = id;
    this.email = email;
    this.nome = nome;
    this.senha = senha;
        
}   

    public Usuario() {
     
    }

  
 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
}
