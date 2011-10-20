#include <stdio.h>
#include <math.h>
#include <vector>
#include <list>

#ifdef _DEBUG
#include <fstream>
#include <iomanip>
#include <assert.h>
#endif

using namespace std;

#define forn( i, n ) for( int i = 0; i < n; i++ )
#define EPSILON 0.0000001l

inline double min( double a, double b ) { return a < b ? a : b; }
inline double max( double a, double b ) { return a < b ? b : a; }

// ------------------------------------------------------------------------- //
// ESTRUCTURAS

class Par {
public:
    Par(){};
    Par( double _x, double _y ) : x( _x ), y( _y ) {};
    Par( const Par& p ) : x( p.x ), y( p.y ) {};
    Par operator-( const Par& p ) const { return Par( x - p.x, y - p.y ); }
    bool operator<( const Par& p ) const { return y + EPSILON < p.x; }
    bool operator==( const Par& p ) const { return fabs(x - p.x) < EPSILON && fabs(y - p.y) < EPSILON; }
    bool operator!=( const Par& p ) const { return fabs(x - p.x) > EPSILON || fabs(y - p.y) > EPSILON; }

    double x;
    double y;
};

inline double prodInterno( const Par& p1, const Par& p2 ) { return p1.x * p2.x + p1.y * p2.y; }

class Segmento {
public:
    Segmento(){};
    Segmento( const Segmento& s ) : p1( s.p1 ), p2( s.p2 ) {};
    Segmento( const Par& _p1, const Par& _p2 ) : p1( _p1 ), p2( _p2 ) {};

    Par p1;
    Par p2;
};

class Semirrecta {
public:
    Semirrecta(){};
    Semirrecta( const Par& _p1, const Par& _p2 ) : origen( _p1 ), v( _p2 - _p1 ) {};
    Semirrecta( const Segmento& s, bool origenP1 = true ) { origen = origenP1 ? s.p1 : s.p2; v = origenP1 ? s.p2 - s.p1 : s.p1 - s.p2; }
    Semirrecta( const Semirrecta& sr ) : origen( sr.origen ), v( sr.v ) {};

    Par origen;
    Par v;
};

struct Columna {
    int x;
    int y;
    int r;
};

struct Luz {
    int x;
    int y;
};


// ------------------------------------------------------------------------- //
// VARIABLES GLOBALES

int cantLuces, cantColumnas, ancho, alto;
vector< Luz > luces;
vector< Columna > columnas;
vector< Segmento > pared;

// ------------------------------------------------------------------------- //
// METODOS

enum IdPared{ INFERIOR = 0, DERECHA, SUPERIOR, IZQUIERDA };

bool interSemirrectas( const Semirrecta& sr1, const Semirrecta& sr2, Par& res )
{
    double k = ( sr2.origen.x - sr1.origen.x ) * sr1.v.y + ( sr1.origen.y - sr2.origen.y ) * sr1.v.x;
    double det = sr1.v.x * sr2.v.y - sr1.v.y * sr2.v.x;
    if( fabs(det) < EPSILON )
    {
        // caso particular:
        // semirectas de sentido opuesto con mismo origen y misma direccion
        // para ver si tienen sentido opuesto o no usamos el producto interno
        if( sr1.origen == sr2.origen && prodInterno( sr1.v, sr2.v ) < 0 )
        {
            res = sr1.origen;
            return true;
        }
        return false;
    }

    double m = k / det;

    // p es el punto de interseccion
    // verificamos si el punto de interseccion esta hacia
    // el mismo sentido que los vectores de sr1 y sr2
    Par p( sr2.origen.x + m*(sr2.v.x), sr2.origen.y + m*(sr2.v.y) );
    if( prodInterno( p - sr1.origen, sr1.v ) < 0 )
        return false;

    if( prodInterno( p - sr2.origen, sr2.v ) < 0 )
        return false;

    res = p;
    return true;
}

bool interSemirrectaSeg( const Semirrecta& sr, const Segmento& s, Par& res )
{
    Par p;
    if( interSemirrectas( sr, s, p ) )
    {
        // para verificar si el punto de interseccion esta dentro de la semirecta
        // veo si el producto interno de los vectores s.p2 - s.p1 y p - s.p1 es positivo
        // y si el producto interno de los vectores s.p1 - s.p2 y p - s.p2 es positivo
        Par v1( s.p2.x - s.p1.x, s.p2.y - s.p1.y );
        Par v2( p.x - s.p1.x, p.y - s.p1.y );
        if( prodInterno( v1, v2 ) < 0 )
            return false;

        v1.x = s.p1.x - s.p2.x;
        v1.y = s.p1.y - s.p2.y;
        v2.x = p.x - s.p2.x;
        v2.y = p.y - s.p2.y;
        if( prodInterno( v1, v2 ) < 0 )
            return false;

        res = p;
        return true;
    }

    return false;
}

Par intervaloSombra( const Luz& l, const Columna& c )
{
    Par v( c.x - l.x, c.y - l.y );
    double hipot = sqrt( v.x * v.x + v.y * v.y );                                       // hypot( v.x, v.y );
    double sinAlf = c.r / hipot;                                                                        // sin(alfa) = opuesto / hipotenusa
//    double ady = sqrt( hipot * hipot - (double)(c.r * c.r) );         // adyacente = raiz( hipotenusa^2 - opuesto^2 )
//    double cosAlf = ady / hipot;                                                                      // cos(alfa) = adyacente / hipotenusa
    double cosAlf = sqrt(1 - sinAlf * sinAlf);                                          // cos(alfa) = sqrt( 1 - sin^2(alfa) )

    /* las tangentes las calculamos rotando el vector multiplicandolo con la matriz de rotación
     *
     * cos(alfa)   -sin(alfa)  *   v.x   =   t.x
     * sin(alfa)    cos(alfa)      v.y       t.y
     *
     * y luego movemos el punto sumandole la ubicacion de la luz
     */
    Par vTangente1( v.x * cosAlf - v.y * sinAlf + l.x, v.x * sinAlf + v.y * cosAlf + l.y );
    Par vTangente2( v.x * cosAlf + v.y * sinAlf + l.x, v.y * cosAlf - v.x * sinAlf + l.y );

    // construyo las tangentes
    Semirrecta tangente1( Par(l.x, l.y), vTangente1 );
    Semirrecta tangente2( Par(l.x, l.y), vTangente2 );

    Segmento* paredTang1 = NULL;
    Segmento* paredTang2 = NULL;
    Par itscc1, itscc2;

    int cantParedes = pared.size();
    int i;
    double x1 = 0;
    double x2 = 0;
    for( i = 0; i < cantParedes && !paredTang2; i++ ) {
        if( interSemirrectaSeg( tangente2, pared[i], itscc2 ) ) {
            paredTang2 = &pared[i];
            switch( i )
            {
            case INFERIOR:
                x1 += itscc2.x;
                break;
            case DERECHA:
                x1 += itscc2.y;
                break;
            case SUPERIOR:
                x1 += (double)ancho - itscc2.x;
                break;
            case IZQUIERDA:
                x1 += (double)alto - itscc2.y;
                break;
            }
        }
        else {
            x1 += ( (i + 1) % 2 ) * ancho + (i % 2) * alto;
            x2 += ( (i + 1) % 2 ) * ancho + (i % 2) * alto;
        }
    }

    int j = i - 1;
    while( !paredTang1 ) {
        if( j % cantParedes == 0 ) {
            j = 0;
            x2 = 0;
        }

        if( interSemirrectaSeg( tangente1, pared[j], itscc1 ) ) {
            paredTang1 = &pared[j];
            switch( j )
            {
            case INFERIOR:
                x2 += itscc1.x;
                break;
            case DERECHA:
                x2 += itscc1.y;
                break;
            case SUPERIOR:
                x2 += (double)ancho - itscc1.x;
                break;
            case IZQUIERDA:
                x2 += (double)alto - itscc1.y;
                break;
            }

        }
        else {
            x2 += ( (j + 1) % 2 ) * ancho + (j % 2) * alto;
        }

        j++;
    }

    double maximo = (ancho << 1) + (alto << 1);
    if( x1 >= maximo )
        x1 -= maximo;

    if( x2 >= maximo )
        x2 -= maximo;

    return Par( x1, x2 );
}

inline Par unionIntervalosND( const Par& p1, const Par& p2 )
{
    return Par( min( p1.x, p2.x ), max( p1.y, p2.y ) );
}

inline void unir( list< Par >& conj, const Par& par, double minVal, double maxVal )
{
    // Si el intervalo "pega la vuelta", lo dividimos
    Par p1 = par;
    Par p2 = par;
    if( par.x > par.y ) {
        p1 = Par( par.x, maxVal );
        p2 = Par( minVal, par.y );
    }

    list< Par >::iterator it;
    for( it = conj.begin(); it != conj.end() && (*it) < p1; it++ ){}

    if( it == conj.end() || it->x > p1.y )
        conj.insert( it, p1 );
    else {
        *it = unionIntervalosND( p1, *it );
        Par* p = &*it;
        it++;

        while( it != conj.end() && it->x <= p->y ) {
            if( it->y > p->y )
                p->y = it->y;
            it = conj.erase( it );
        }
    }

    if( p1 != p2 )
        unir( conj, p2, minVal, maxVal );
}

void complemento( list< Par >& conj, double minVal, double maxVal )
{
    int n = conj.size();
    double xInicial = conj.front().x;
    double yFinal = conj.back().y;

    if( n == 0 )
        conj.push_back( Par( minVal, maxVal ) );
    else {
        list< Par >::iterator actual = conj.begin();
        list< Par >::iterator anterior = actual;
        actual++;
        for( int i = 0; i < n - 1; actual++, anterior++, i++ )
            conj.push_back( Par( anterior->y, actual->x ) );
    }

    forn( i, n )
        conj.pop_front();

    if( fabs(xInicial - minVal) > EPSILON )
        conj.push_back( Par( yFinal, xInicial ) );
    else {
        if( fabs(yFinal - maxVal) > EPSILON )
            conj.push_back( Par( yFinal, xInicial ) );
    }
}

double perimIluminado()
{
    int minVal = 0, maxVal = (ancho << 1) + (alto << 1);

        if( cantLuces == 0 )
                return minVal;

        if( cantColumnas == 0 )
                return maxVal;

    list< Par > iluminado;

    for( vector< Luz >::const_iterator l = luces.begin(); l < luces.end(); l++ ) {
        list< Par > intervalos;

        for( vector< Columna >::const_iterator c = columnas.begin(); c < columnas.end(); c++ )
            unir( intervalos, intervaloSombra( *l, *c ), minVal, maxVal );
        
        complemento( intervalos, minVal, maxVal );
        
        for( list< Par >::iterator it = intervalos.begin(); it != intervalos.end(); it++ )
            unir( iluminado, *it, minVal, maxVal );
    }

    double res = 0;
    for( list< Par >::iterator it = iluminado.begin(); it != iluminado.end(); it++ )
        res += it->y - it->x;

    return res;
}

#ifdef _DEBUG
bool testInterSemirrectas()
{
    Semirrecta sr1;
    Semirrecta sr2;
    Par inter;
    bool paso = true;

    // 1
    sr1 = Semirrecta( Par( 4, 2 ), Par( 7, 3 ) );
    sr2 = Semirrecta( Par( 4, 3 ), Par( 7, 4 ) );

    paso = paso && !interSemirrectas( sr1, sr2, inter );

    // 2
    sr1 = Semirrecta( Par( 4, 7 ), Par( 7, 10 ) );
    sr2 = Semirrecta( Par( 4, 7 ), Par( 4, 6 ) );

    paso = paso && interSemirrectas( sr1, sr2, inter ) && inter.x == 4 && inter.y == 7;

    // 3
    sr1 = Semirrecta( Par( -2, 4 ), Par( -1, 3 ) );
    sr2 = Semirrecta( Par( -2, 3 ), Par( -1, 4 ) );

    paso = paso && interSemirrectas( sr1, sr2, inter ) && inter.x == -1.5 && inter.y == 3.5;

    // 4
    sr1 = Semirrecta( Par( -5, 3 ), Par( -7, 2 ) );
    sr2 = Semirrecta( Par( -6, 4 ), Par( -6, 2 ) );

    paso = paso && interSemirrectas( sr1, sr2, inter );

    // 5
    sr1 = Semirrecta( Par( -4, 8 ), Par( -6, 7 ) );
    sr2 = Semirrecta( Par( -6, 9 ), Par( -7, 7 ) );

    paso = paso && interSemirrectas( sr1, sr2, inter );

    // 6
    sr1 = Semirrecta( Par( -6, -3 ), Par( -2, -3 ) );
    sr2 = Semirrecta( Par( -4, -3 ), Par( -4, -6 ) );

    paso = paso && interSemirrectas( sr1, sr2, inter ) && inter.x == -4 && inter.y == -3;

    // 7
    sr1 = Semirrecta( Par( -2, -7 ), Par( -6, -7 ) );
    sr2 = Semirrecta( Par( -4, -7 ), Par( -4, -10 ) );

    paso = paso && interSemirrectas( sr1, sr2, inter ) && inter.x == -4 && inter.y == -7;

    // 8
    sr1 = Semirrecta( Par( 5, -4 ), Par( 7, -4 ) );
    sr2 = Semirrecta( Par( 5, -4 ), Par( 3, -4 ) );

    paso = paso && interSemirrectas( sr1, sr2, inter ) && inter.x == 5 && inter.y == -4;

    // 9
    sr1 = Semirrecta( Par( 5, -6 ), Par( 4, -8 ) );
    sr2 = Semirrecta( Par( 5, -8 ), Par( 8, -7 ) );

    paso = paso && !interSemirrectas( sr1, sr2, inter );

        // 10
        sr1 = Semirrecta( Par( 0, EPSILON ), Par( 1, 0 ) );
        sr2 = Semirrecta( Par( EPSILON, 1 ), Par( -EPSILON, -EPSILON ) );

    paso = paso && !interSemirrectas( sr1, sr2, inter );

        return paso;
}

bool testInterSemirrectaSeg()
{
    Semirrecta sr1;
    Segmento sr2;
    Par inter;
    bool paso = true;

    // 1
    sr1 = Semirrecta( Par( 4, 2 ), Par( 7, 3 ) );
    sr2 = Segmento( Par( 4, 3 ), Par( 7, 4 ) );

    paso = paso && !interSemirrectaSeg( sr1, sr2, inter );

    // 2
    sr1 = Semirrecta( Par( 4, 7 ), Par( 7, 10 ) );
    sr2 = Segmento( Par( 4, 7 ), Par( 4, 6 ) );

    paso = paso && interSemirrectaSeg( sr1, sr2, inter ) && inter.x == 4 && inter.y == 7;

    // 3
    sr1 = Semirrecta( Par( -2, 4 ), Par( -1, 3 ) );
    sr2 = Segmento( Par( -2, 3 ), Par( -1, 4 ) );

    paso = paso && interSemirrectaSeg( sr1, sr2, inter ) && inter.x == -1.5 && inter.y == 3.5;

    // 4
    sr1 = Semirrecta( Par( -5, 3 ), Par( -7, 2 ) );
    sr2 = Segmento( Par( -6, 4 ), Par( -6, 2 ) );

    paso = paso && interSemirrectaSeg( sr1, sr2, inter );

    // 5
    sr1 = Semirrecta( Par( -4, 8 ), Par( -6, 7 ) );
    sr2 = Segmento( Par( -6, 9 ), Par( -7, 7 ) );

    paso = paso && !interSemirrectaSeg( sr1, sr2, inter );

    // 6
    sr1 = Semirrecta( Par( -6, -3 ), Par( -2, -3 ) );
    sr2 = Segmento( Par( -4, -3 ), Par( -4, -6 ) );

    paso = paso && interSemirrectaSeg( sr1, sr2, inter ) && inter.x == -4 && inter.y == -3;

    // 7
    sr1 = Semirrecta( Par( -2, -7 ), Par( -6, -7 ) );
    sr2 = Segmento( Par( -4, -7 ), Par( -4, -10 ) );

    paso = paso && interSemirrectaSeg( sr1, sr2, inter ) && inter.x == -4 && inter.y == -7;

    // 8
    sr1 = Semirrecta( Par( 5, -4 ), Par( 7, -4 ) );
    sr2 = Segmento( Par( 5, -4 ), Par( 3, -4 ) );

    paso = paso && interSemirrectaSeg( sr1, sr2, inter ) && inter.x == 5 && inter.y == -4;

    // 9
    sr1 = Semirrecta( Par( 5, -6 ), Par( 4, -8 ) );
    sr2 = Segmento( Par( 5, -8 ), Par( 8, -7 ) );

    paso = paso && !interSemirrectaSeg( sr1, sr2, inter );

    // 10
    sr1 = Semirrecta( Par( 0, 0 ), Par( 1, 0 ) );
    sr2 = Segmento( Par( 0, 1 ), Par( -EPSILON, 0 ) );

    paso = paso && !interSemirrectaSeg( sr1, sr2, inter );

        return paso;
}
bool testIntervaloSombra()
{
    ancho = 10;
    alto = 6;
    pared.resize(4);
    pared[INFERIOR] = Segmento( Par( 0, 0 ), Par( ancho, 0 ) );
    pared[DERECHA] = Segmento( Par( ancho, 0 ), Par( ancho, alto ) );
    pared[SUPERIOR] = Segmento( Par( ancho, alto ), Par( 0, alto ) );
    pared[IZQUIERDA] = Segmento( Par( 0, alto ), Par( 0, 0 ) );

    Luz l;
    Columna c;
    Par intervalo;
    bool paso = true;
    int x1, x2;

    // 1
    l.x = 1;
    l.y = 2;

    c.x = 5;
    c.y = 4;
    c.r = 1;

    intervalo = intervaloSombra( l, c );
    x1 = (int)intervalo.x;
    x2 = (int)intervalo.y;
    paso = paso && x1 == 14 && x2 == 20;

    // 2
    l.x = 6;
    l.y = 3;

    c.x = 3;
    c.y = 3;
    c.r = 2;

    intervalo = intervaloSombra( l, c );
    x1 = (int)intervalo.x;
    x2 = (int)intervalo.y;
    paso = paso && x1 == 23 && x2 == 2;

    // 3
    l.x = 1;
    l.y = 3;

    c.x = 8;
    c.y = 3;
    c.r = 1;

    intervalo = intervaloSombra( l, c );
    x1 = (int)intervalo.x;
    x2 = (int)intervalo.y;
    paso = paso && x1 == 11 && x2 == 14;

    // 4
    l.x = 9;
    l.y = 5;

    c.x = 7;
    c.y = 3;
    c.r = 2;

    intervalo = intervaloSombra( l, c );
    x1 = (int)intervalo.x;
    x2 = (int)intervalo.y;
    paso = paso && x1 == 27 && x2 == 9;


    // 5
    l.x = 5;
    l.y = 4;

    c.x = 5;
    c.y = 2;
    c.r = 1;

    intervalo = intervaloSombra( l, c );
    x1 = (int)intervalo.x;
    x2 = (int)intervalo.y;
    paso = paso && x1 == 2 && x2 == 7;

    // 6
    l.x = 1;
    l.y = 5;

    c.x = 3;
    c.y = 3;
    c.r = 2;

    intervalo = intervaloSombra( l, c );
    x1 = (int)intervalo.x;
    x2 = (int)intervalo.y;
    paso = paso && x1 == 1 && x2 == 15;

    // 7
    l.x = 1;
    l.y = 1;

    c.x = 3;
    c.y = 3;
    c.r = 2;

    intervalo = intervaloSombra( l, c );
    x1 = (int)intervalo.x;
    x2 = (int)intervalo.y;
    paso = paso && x1 == 11 && x2 == 25;

    // 8
    l.x = 9;
    l.y = 1;

    c.x = 7;
    c.y = 3;
    c.r = 2;

    intervalo = intervaloSombra( l, c );
    x1 = (int)intervalo.x;
    x2 = (int)intervalo.y;
    paso = paso && x1 == 17 && x2 == 31;

    // 9
    l.x = 5;
    l.y = 1;

    c.x = 5;
    c.y = 3;
    c.r = 1;

    intervalo = intervaloSombra( l, c );
    x1 = (int)intervalo.x;
    x2 = (int)intervalo.y;
    paso = paso && x1 == 18 && x2 == 23;

    // 10
    l.x = 1;
    l.y = 3;

    c.x = 4;
    c.y = 3;
    c.r = 2;

    intervalo = intervaloSombra( l, c );
    x1 = (int)intervalo.x;
    x2 = (int)intervalo.y;
    paso = paso && x1 == 4 && x2 == 21;

    ancho = 6;
    alto = 10;
    pared.resize(4);
    pared[INFERIOR] = Segmento( Par( 0, 0 ), Par( ancho, 0 ) );
    pared[DERECHA] = Segmento( Par( ancho, 0 ), Par( ancho, alto ) );
    pared[SUPERIOR] = Segmento( Par( ancho, alto ), Par( 0, alto ) );
    pared[IZQUIERDA] = Segmento( Par( 0, alto ), Par( 0, 0 ) );

    // 11
    l.x = 3;
    l.y = 1;

    c.x = 3;
    c.y = 4;
    c.r = 2;

    intervalo = intervaloSombra( l, c );
    x1 = (int)intervalo.x;
    x2 = (int)intervalo.y;
    paso = paso && x1 == 10 && x2 == 27;

    // 12
    l.x = 3;
    l.y = 9;

    c.x = 3;
    c.y = 6;
    c.r = 2;

    intervalo = intervaloSombra( l, c );
    x1 = (int)intervalo.x;
    x2 = (int)intervalo.y;
    paso = paso && x1 == 26 && x2 == 11;

    return paso;
}
bool testUnir()
{
    bool paso = true;
    list< Par > l;
    int minVal = 0;
    int maxVal = (ancho << 1) + (alto << 1);

    unir( l, Par( 0, 1 ), minVal, maxVal );
    unir( l, Par( 3, 4 ), minVal, maxVal );
    unir( l, Par( 5, 6 ), minVal, maxVal );
    unir( l, Par( 0, 3 ), minVal, maxVal );
    unir( l, Par( 6, 10 ), minVal, maxVal );
    unir( l, Par( 6, 7 ), minVal, maxVal );
    unir( l, Par( 15, 16 ), minVal, maxVal );
    unir( l, Par( 17, 20 ), minVal, maxVal );
    unir( l, Par( 4, 17 ), minVal, maxVal );

    paso = paso && l.size() == 1 && l.front() == Par( 0, 20 );

    l.clear();
    unir( l, Par( 6, 26 ), minVal, maxVal );
    unir( l, Par( 14, 2 ), minVal, maxVal );

    paso = paso && l.size() == 2 && l.front() == Par( 0, 2 ) && l.back() == Par( 6, maxVal );

    return paso;
}

bool testComplemento()
{
    bool paso = true;
    list< Par > l;
    list< Par > resultado;

    l.push_back( Par( 0, 1 ) );
    l.push_back( Par( 2, 3 ) );
    l.push_back( Par( 4, 5 ) );
    l.push_back( Par( 6, 7 ) );
    l.push_back( Par( 8, 9 ) );
    complemento( l, 0, 10 );

    resultado.push_back( Par( 1, 2 ) );
    resultado.push_back( Par( 3, 4 ) );
    resultado.push_back( Par( 5, 6 ) );
    resultado.push_back( Par( 7, 8 ) );
    resultado.push_back( Par( 9, 0 ) );

    paso = paso && l == resultado;

    resultado.clear();

    return paso;
}
#endif

int main()
{
#ifdef _DEBUG
    assert( testInterSemirrectas() );
    assert( testInterSemirrectaSeg() );
    assert( testIntervaloSombra() );
    assert( testUnir() );
    assert( testComplemento() );

    ifstream entrada( "test2.txt", ios_base::in );
    ofstream salida( "test2.out", ios_base::out );
#endif

    while( true ) {
        columnas.clear();
        luces.clear();
        pared.clear();

#ifdef _DEBUG
        entrada >> cantLuces >> cantColumnas >> ancho >> alto;
#else
        scanf( "%d %d %d %d", &cantLuces, &cantColumnas, &ancho, &alto );
#endif

        if( cantLuces == 0 && cantColumnas == 0 && ancho == 0 && alto == 0 )
            break;

        pared.resize(4);
        pared[INFERIOR] = Segmento( Par( 0, 0 ), Par( ancho, 0 ) );
        pared[DERECHA] = Segmento( Par( ancho, 0 ), Par( ancho, alto ) );
        pared[SUPERIOR] = Segmento( Par( ancho, alto ), Par( 0, alto ) );
        pared[IZQUIERDA] = Segmento( Par( 0, alto ), Par( 0, 0 ) );

        forn( i, cantLuces ) {
            Luz l;
#ifdef _DEBUG
            entrada >> l.x >> l.y;
#else
            scanf( "%d %d", &l.x, &l.y );
#endif
            luces.push_back( l );
        }

        forn( i, cantColumnas )
        {
            Columna nuevaCol;
#ifdef _DEBUG
            entrada >> nuevaCol.x >> nuevaCol.y >> nuevaCol.r;
#else
            scanf( "%d %d %d", &nuevaCol.x, &nuevaCol.y, &nuevaCol.r );
#endif
            columnas.push_back( nuevaCol );
        }

#ifdef _DEBUG
        salida << fixed << setprecision(4) << perimIluminado() << endl;
#else
        printf( "%.4f\n", perimIluminado() );
#endif
    }

#ifdef _DEBUG
    entrada.close();
        salida.close();
#endif

    return 0;
}
