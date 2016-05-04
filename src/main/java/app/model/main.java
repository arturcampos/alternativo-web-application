package app.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import app.control.PessoaController;
import app.dao.JpaUtil;

public class main {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Pessoa p = new Pessoa();
		p.setNome("Teste 2");
		p.setId(1L);
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = formatter.parse("03/05/2000");
		p.setDatanasc(date);
		p.setSexo("F");
		p.setNacionalidade("Brasil");
		p.setNaturalidade("Uberlandia");
		p.setUf("MG");
		p.setNomepai("Pai 1");
		p.setNomemae("Mae 1");
		p.setResponsavellegal("ninguem tbm");
		p.setEmail("teste2@teste2");
		p.setNumerocelular("9 99901905");
		p.setNecessidadesespeciais("Nenhuma");
		p.setEtnia("preta");
		p.setNacionalidade("Brasil");
		p.setEstadocivil("Solteiro(a)");
		p.setTipopessoas("Aluno");

		
		PessoaController pc = new PessoaController();
		pc.init();
		
		pc.setPessoa(p);
		pc.salvar();
		
		
		/*em.persist(p);
		tx.commit();
		em.close();
		JpaUtil.close();*/
				
	}

}
