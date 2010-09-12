#include <math.h>

using namespace std;

class Complex {
	double real, imag;
	
public:
	Complex() { }
	Complex(double r, double i) { real=r; imag=i; }
	
	double abs() {
		return sqrt(real*real + imag*imag);
	}
	
	double ang() {
	    return atan2(imag,real);
	}
	
	double getReal() { return real; }
	double getImag() { return imag; }
	
	Complex operator+(Complex b) {
	    double r = this->getReal() + b.getReal();
	    double i = this->getImag() + b.getImag();
	    Complex res (r,i);
	    return res;
	}
	
	Complex operator-(Complex b) {
	    double r = this->getReal() - b.getReal();
	    double i = this->getImag() - b.getImag();
	    Complex res (r,i);
	    return res;
	}
	
	Complex operator*(Complex b) {
	    double r = this->getReal()*b.getReal() - this->getImag()*b.getImag();
	    double i = this->getImag()*b.getReal() + this->getReal()*b.getImag();
	    Complex res (r,i);
	    return res;
	}
	
	Complex operator*(double k) {
	    Complex res (real*k,imag*k);
	    return res;
	}
	
	Complex inv() {
	    double den = real*real + imag*imag;
	    double r = real/den;
	    double i = -imag / den;
	    Complex res (r,i);
	    return res;
	}
	
	Complex operator/(Complex b) {
	    return (*this)*b.inv();
	}
	
	/**
	 * Retorna e^(this)
	 */
	Complex expC() {
	    double r = exp(real)*cos(imag);
	    double i = exp(real)*sin(imag);
	    Complex res (r,i);
	    return res;
	}
};


