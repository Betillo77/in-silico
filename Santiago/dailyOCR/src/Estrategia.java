import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Estrategia 
{	
	public IdEstrategia id;
	public ArrayList <Senal> senales;
	
	public Estrategia(IdEstrategia id)
	{
		this.id = id;
	}
	
	public void agregar(SenalEntrada entrada, Senal afectada) 
	{
		if(entrada.tipo.equals(TipoSenal.HIT))
		{
			hit(entrada, afectada);
		}
		else
		{
			trade(entrada);
		}
	}
	
	private void hit(SenalEntrada entrada, Senal afectada) 
	{
		Escritor.cerrar(entrada, afectada);
		if(afectada.numeroLotes <= 0)
		{
			senales.remove(afectada);
		}
	}
	
	private void trade(SenalEntrada entrada) 
	{
		Senal nueva = new Senal(id, entrada.compra, entrada.par, entrada.numero, entrada.precioEntrada);
		if(tienePar(entrada.par) != null)
		{
			// TODO Manejo de errores
		}
		Escritor.abrir(entrada, nueva);
		senales.add(nueva);
	}
	
	public Senal tienePar(Par par) 
	{
		for(Senal senal : senales)
		{
			if(senal.par == par)
				return senal;
		}
		return null;
	}

	public void escribir(File archivo)
	{
		try 
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo));
			oos.writeObject(this);	
			oos.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Manejar errores
		}
		catch (IOException e) 
		{
			// TODO Manejar errores
		}	
	}
	
	public static Estrategia leer(File archivo)
	{
		try 
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
			Estrategia e = (Estrategia) ois.readObject();
			ois.close();
			return e;
		} 
		catch (ClassNotFoundException e) 
		{
			return null;
			// TODO Manejar errores
		}
		catch (IOException e) 
		{
			return null;
			// TODO Manejar errores
		}	
	}
}
