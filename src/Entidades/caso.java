package Entidades;

public class caso {
	private particle particula;
	private problema problema; //Vizinho de momento
	private movimento movimento;
	private int sucesso;
	
	
	public caso(particle particula, Entidades.problema problema,
			Entidades.movimento movimento,int sucesso) {
		super();
		this.particula = particula;
		this.problema = problema;
		this.movimento = movimento;
		this.sucesso = sucesso;
	}


	public particle getParticula() {
		return particula;
	}


	public void setParticula(particle particula) {
		this.particula = particula;
	}


	public problema getProblema() {
		return problema;
	}


	public void setProblema(problema problema) {
		this.problema = problema;
	}


	public movimento getMovimento() {
		return movimento;
	}


	public void setMovimento(movimento movimento) {
		this.movimento = movimento;
	}


	public int getSucesso() {
		return sucesso;
	}


	public void setSucesso(int sucesso) {
		this.sucesso = sucesso;
	}
	
	
	

}
