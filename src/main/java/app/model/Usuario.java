package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="usuario", schema="futurodb")
@NamedQuery(name="Usuario.findByName", query="SELECT u from Usuario u where u.nomeUsuario = :name")
public class Usuario {

      @Id
      @Column(name="id", nullable=false, unique=true)
      private int id;

      @Column(name="nomeusuario", nullable=false, unique=true)
      private String nomeUsuario;

      @Column(name="senha", nullable=false, unique=false)
      private String senha;


	public String getNomeUsuario() {
            return nomeUsuario;
      }

      public void setNomeUsuario(String nomeUsuario) {
            this.nomeUsuario = nomeUsuario;
      }

      public String getSenha() {
            return senha;
      }

      public void setSenha(String senha) {
            this.senha = senha;
      }

 }