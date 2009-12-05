package experimentacion;

public class cronometro {
	
	private long inicio;
	private long parar;
	
	public void inicio(){
		System.currentTimeMillis();
	}
	
	public void parar(){
		System.currentTimeMillis();
	}
	
	public long tiempoTranscurridoMilis(){
		return parar - inicio;
	}
	
	public String toString(){
		return "Tiempo transcurrido en milisegundos: " + Long.toString(tiempoTranscurridoMilis());
	}

}
