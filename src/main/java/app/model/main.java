package app.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.control.PessoaController;

public class main {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		/*EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();*/
		
		Pessoa p = new Pessoa();
		p.setNome("teste");
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = formatter.parse("03/05/2016");
		p.setDatanasc(date);
		p.setSexo("M");
		p.setNacionalidade("Uber");
		p.setNaturalidade("Uberlandia");
		p.setUf("MG");
		p.setNomepai("Pai");
		p.setNomemae("Mae");
		p.setResponsavellegal("ninguem");
		p.setEmail("teste@teste");
		p.setNumerocelular("9 99901905");
		p.setNecessidadesespeciais("Nenhuma");
		p.setEtnia("amarelo");
		p.setNacionalidade("Brasil");
		p.setEstadocivil("Solteiro");
		p.setTipopessoas("Aluno");

		
		PessoaController pc = new PessoaController();
		pc.init();
		
		pc.salvar(p);
		
		
		/*em.persist(p);
		tx.commit();
		em.close();
		JpaUtil.close();*/
		
		
		
				
				
	}

}
