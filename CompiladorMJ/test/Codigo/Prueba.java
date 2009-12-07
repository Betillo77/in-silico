public class Prueba
{
	public static void main(String [] args){
		padre p1 = new padre();
		padre p2 = new hijo();
		hijo h = new hijo();
		p1.datos();
		p2.datos();
		System.out.println (p1.nombre + " " + p1.apellido + "  el hijo " + p2.nombre + " " + p2.apellido);
		p1.labora(300);
		p2.labora(200);
		System.out.println (p1.ingresos + " " + p2.ingresos);
		h.datos();
		h.labora(100);
		System.out.println (h.ingresos + "" + h.edad + h.novia());
		h.dararray()[1] = new hijo[2];
		h.h.h.h.h.h.h.dararray()[1][1]=new hijo();
		h.array[1][1].novia = "padre";
		System.out.println(h.dararray()[1][1].novia()); 
		}
}

class padre {

	String nombre;
	String apellido;
	int edad;
	int ingresos;

	void datos(){
		nombre="fulano";
		apellido = "de tal";
		edad = 30;
		}

	void labora(int sueldo){
		ingresos = sueldo * edad;
		}
	int ganacias(int salario){
		labora (salario);
		return ingresos;
		}
}

class hijo extends padre{
	String novia;
	hijo h;
	hijo [][] array;
	void datos(){
		nombre = "hijo de fulano";
		apellido = "de tal";
		novia = "ella";
		edad = 19;
		array = new hijo[2][2];
		}

	void labora(int d){
		if (edad > 18)
			ingresos = d * edad;
		else
			ingresos = 0;
		}

	String novia(){
		h = this;
		h.h.h.h.h.h.h.h.h.h.edad=this.edad + 1;
		return novia;
		}

	hijo[][] dararray(){
		return array;
		}
}
	