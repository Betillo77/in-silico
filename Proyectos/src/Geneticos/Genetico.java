package Geneticos;

/* VARIABLES
 * peso: Es un arreglo que indica en cada una de sus posiciones el peso de cada uno de los elementos
 *  utilidad: Es un arreglo que asigna un valor a cada elemento según la utilidad que proporciona
 * pesoMaximo: Es el peso máximo que puede cargar la mochila
 * FactorMutacion: Es el factor de mutación dado en porcentaje
 * 
 */

 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class Genetico
{
        int peso [] = {20, 10, 15, 50, 30, 25, 35, 40};
        int utilidad [] = {10, 4, 8, 22, 16, 13, 17, 20};
        int pesoMaximo = 500;
        Random generator = new Random();
        static final double FactorMutacion = 0.1;
        
        static Genetico g = new Genetico();
        
        /**
         * Método que imprime el número de soluciones que hay con una utilidad 
         * mayor o igual a un numero dado. Con 266 (la utilidad mas alta posible, 
         * imprime que hay 15 soluciones).
         * nota: se demora 2 minutos (en mi computador) porque revisa todas las
         * posibilidades.
         * @param numero Es un entero que representa una utilidad
         */
        public void imprimirMejores(int numero)
        {
                int cuenta = 0;
                for(int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++)
                {
                        if(validar(i) && calcularAptitud(i) >= numero)
                        {
                                cuenta++;
                        }
                }
                System.out.println(cuenta);
        }
        
        public int azar(int a, int b)
        {
                return generator.nextInt(b - a + 1) + a;
        }
        
        
        /**
         * Crea una población inicial válida , es decir, que el peso de todos 
         * los elementos sea menor o igual que pesoMaximo
         *
         * @return resultado Es un arreglo bidimensional que representa
         * la población inicial.
         */
        public int [][] inicializar()
        {
                // Columna 0 contiene la solución codificada como un entero
                // de 32 bits (cada variable es representada por 4 bits)
                // Columna 1 contiene la aptitud de la solución i
                int [][] resultado = new int[64][2];
                for(int i = 0; i < 64; i++)
                {
                        int posible = 0;
                        do
                        {
                                posible = azar(0, Integer.MAX_VALUE - 1);
                        }
                        while(!validar(posible));
                        resultado[i][0] = posible;
                        resultado[i][1] = calcularAptitud(posible);
                }
                return resultado;
        }
        
        /**
         * 
         * Combina las dos soluciones iniciales para generar una nueva
         * 
         * @param c1 Representa una solución
         * @param c2 Representa otra solución
         * @return hijo, que es el producto del cruce de las soluciones 
         * anteriores
         */
         public int cruzar(int c1, int c2)
         {
                int hijo;
                do
                {
                        hijo = 0;
                        for(int i = 0; i < 8; i++)
                        {
                                // Representa la parte i de la solución c1
                                int d1 = (c1 >> i * 4) & 15;
                                // Representa la parte i de la solución c2
                                int d2 = (c2 >> i * 4) & 15;
                                // Selecciona la parte i de la nueva solución,
                                // tomando bien sea d1 o d2.
                                if(azar(0, 1) == 0)
                                {
                                        hijo |= d1 << i * 4;
                                }
                                else
                                {
                                        hijo |= d2 << i * 4;
                                }
                        }
                        // 
                        if(azar(0, 1000) < FactorMutacion * 10)
                        {
                                int a = azar(0, 31);
                                if(((hijo >> a) & 1) == 1)
                                {
                                        hijo -= 1 << a;
                                }
                                else
                                {
                                        hijo += 1 << a;
                                }
                        }
                }
                while(!validar(hijo));
                return hijo;
        }
        
        /**
         * Muestra la cantidad de cada tipo de elemento que la solución permite
         * @param solucion Es una solución (Elemento de la población)
         */ 
        public void calcularNumero(int solucion)
        {        
                for(int i = 0; i < 8; i++)
                {
                        int nActual = solucion >> i * 4;
                        nActual &= 15;
                        System.out.print(" " +  (char)('a' + i) + ":" + nActual);
                }
        }
        
        /**
         * Calcula la aptitud de una solución, que es la utilidad que
         * genera el llevar tantos elementos de cada item como indique la
         * solución entregada
         * @param solucion Es una solución (elemento de la población)
         * @return ap Es la aptitud de la solución dada
         */
        public int calcularAptitud(int solucion)
        {
                int ap = 0;
                for(int i = 0; i < 8; i++)
                {
                        int nActual = solucion >> i * 4;
                        nActual &= 15;
                        ap += utilidad[i] * nActual;
                }
                return ap;
        }

        /**
         * Valida la solución entregada; es decir, dice si el número de 
         * elementos que sugiere la solución, se puede llevar en la mochila, 
         * de acuerdo a su capacidad: pesoMaximo
         * @param solucion Es una solución (elemento de la población)
         * @return booleano
         */
        public boolean validar(int solucion)
        {
                int ap = 0;
                for(int i = 0; i < 8; i++)
                {
                        int nActual = solucion >> i * 4;
                        nActual &= 15;
                        ap += peso[i] * nActual;
                        if(ap > pesoMaximo)
                                return false;
                }
                return ap <= pesoMaximo;
        }
        
        /**
         * Genera y selecciona la nueva población de soluciones a partir
         * de la población inicial dada, utilizando el esquema de selección
         * proporcional. Este esquema permite que cada solución se reproduzca
         * de acuerdo a su aptitud y la media de aptitud de las demás
         * @param pobInicial Es un arreglo bidimensional de soluciones que
         * representa la población inicial
         * @return nuevos Es la nueva población de soluciones
         */
        public int [][] seleccion(int [][] pobInicial)
        {
                double media = 0;
                int mejor = 0;
                int mejorE = 0;
                
                for(int i = 0; i < pobInicial.length; i++)
                {
                        media += pobInicial[i][1];
                        if(pobInicial[i][1] > mejor)
                        {
                                mejor = pobInicial[i][1];
                                mejorE = pobInicial[i][0];
                        }
                }
                media /= pobInicial.length;
                System.out.print("media = " + media + " mejor = " + mejor);
                calcularNumero(mejorE);
                System.out.println();
                int [] nDescendientes = new int[pobInicial.length];
                // determina el número de descendientes que puede tener
                // cada solución
                for(int i = 0; i < pobInicial.length; i++)
                {
                        nDescendientes[i] = (int) (pobInicial[i][1] * 64 / media);
                }
                ArrayList <Integer> descendientes = new ArrayList <Integer> (4096);
                // Calcula las nuevas soluciones posibles de la nueva generación
                for(int i = 0; i < pobInicial.length; i++)
                {
                        for(int j = 0; j < nDescendientes[i]; j++)
                        {
                                int pareja = -1;
                                do
                                {
                                        pareja = azar(0, pobInicial.length - 1);
                                }
                                while(pareja == i);
                                descendientes.add(cruzar(pobInicial[i][0], pobInicial[pareja][0]));
                        }
                }
                // este cambio fue hecho para que escogiera a los 64 mejores 
                // y no al azar y mejoró mucho
                Collections.sort(descendientes, new Comparador()); 
                ArrayList <Integer> elegidos = new ArrayList <Integer> (64);
                for(int i = 0; i < 64; i++)
                {
                        // este cambio fue hecho para que escogiera a los 64 
                        // mejores y no al azar y mejoro mucho
                        elegidos.add(descendientes.get(descendientes.size() - 1 - i)); 
//                      int d = azar(0, descendientes.size() - 1); // esta es la opcion al azar
//                      elegidos.add(descendientes.remove(d)); // esta es la opcion al azar
                }
                int [][] nuevos = new int[64][2];
                for(int i = 0; i < 64; i++)
                {
                        nuevos[i][0] = elegidos.get(i);
                        nuevos[i][1] = calcularAptitud(nuevos[i][0]);
                }
                return nuevos;
        }
        
        public static void main(String [] args)
        {
                int [][] primero = g.inicializar();
                for(int i = 0; i < 1000; i++)
                {
                        System.out.print("Generacion " + i + ": ");
                        primero = g.seleccion(primero);
                        
                }
                g.imprimirMejores(266);
        }
        
        // Comparador para ordenar la lista de hijos
        class Comparador implements Comparator <Integer>
        {
                @Override
                public int compare(Integer o1, Integer o2) 
                {
                        int a = g.calcularAptitud(o1);
                        int b = g.calcularAptitud(o2);
                        return a - b;
                }
        }
}
