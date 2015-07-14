# Introduction #

As we talk, is really important to make a good projects design to achieve our objectives. Use libraries is good for that. The idea is to develop reusable code in the most general possible way (Using interfaces and inheritance). So we can use that code further without getting worried of how is implemented as we know is working and is implemented with the best characteristics to improve performance that we found.


# Details #

Our library is "Busqueda", the code is spread in some logical units according to what it does. In Java, that units are named Packages.
For example, as we are talking of search, we have divided the project in Deep First Search (DFS) and Breadth First Search (BFS).

As we said in the introduction, we were going to make the code in the library as general as possible. But of course, there are some things that depends of the problem to be solved. For example, in all searches we need to know what are the children of a given node so we can build the tree. So what do we need in order to mix the general with the particular things?

Here is when we use the interfaces, in which we declare the particular things that we are going to need to solve any problem. In these way, we are sure that the object that implements that interface will have those characteristics and we can safely make our algorithm without thinking in particular problems.

To understand better the idea, We strongly recommend to download the source code and check the example of the puzzle. See what part of the source code is made in what project. To even understand better, you can solve another problem in "AppsBusqueda" that uses the library. You can notice that you don't need to rewrite the Breadth First Search algorithm.



Breve explicación en Español de qué son estos dos proyectos




# Introducción #

Como hemos hablado, es realmente importante hacer un buen diseño de proyectos para lograr nuestros objetivos. Usar librerias es una buena forma de hacerlo. La idea es desarrollar código reutilizable en la forma más general posible (Usando interfaces y herencia). Así podemos llevar ese código más lejos sin preocuparnos de cómo está implementado ya que sabemos éste trabaja y está implementado con las mejores características para mejorar el rendimiento que encontramos.


# Detalles #

Nuestra librería es "Búsqueda", cuyo código está distribuído en algunas unidades lógicas
de acuerdo a lo que cada una hace. En Java, esas unidades son llamadas "Packages" o "Paquetes". Por ejemplo, como estamos hablando de búsqueda, hemos dividido el proyecto en Búsqueda Primero en Profundidad (BPP) y Búsqueda Primero en Anchura (BPA).

Como dijimos en la introducción, vamos a hacer el código en la librería tan general como sea posible. Pero claro, hay algunas cosas que dependen del problema a ser resuelto. Por ejemplo, en todas las búsquedas necesitamos conocer cúales son los hijos de un nodo dado para poder construir el árbol. Luego, qué necesitamos en orden para mezclar las cosas generales con las particulares?

Aquí es cuando usamos las interfaces, en las cuales declaramos las cosas particulares que vamos a necesitar para solucionar algún problema. En este sentido, estamos seguros que el objeto que implementa la interface tendrá esas características y podremos hacer nuestro algoritmo sin pensar en casos particulares.

Para entender mejor la idea, recomendamos encarecidamente descargar el código fuente y comprobar el ejemplo del puzzle. Observar en qué parte del proyecto está el código fuente. Para entender aún mejor, puede solucionar otro problema en "AppsBusqueda" el cual usa la librería antes expuesta. Podrá darse cuenta que no necesita reescribir el algoritmo de Búsqueda Primero en Anchura.