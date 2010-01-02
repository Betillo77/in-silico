package zulutrade.grafica;


import zulutrade.analizador.Proveedor;
import zulutrade.analizador.ProveedorDuplicado;

import com.trolltech.qt.gui.*;


public class ProveedorG extends QWidget 
{

    private Ui_ProveedorG ui = new Ui_ProveedorG();
    private Proveedor actual;
    private ProveedorDuplicado unaSemana;
    private ProveedorDuplicado dosSemanas;
    private ProveedorDuplicado cuatroSemanas;
    private ProveedorDuplicado ochoSemanas;
    private ProveedorDuplicado inicio;
    int lotes;

    public ProveedorG(QWidget parent, Proveedor actual)
    {
        super(parent);
        ui.setupUi(this);
        this.actual = actual;
        lotes = 1;
        ui.comboBox.currentIndexChanged.connect(this, "conectar(int)");
        ui.v1.clicked.connect(this, "una()");
        ui.v2.clicked.connect(this, "dos()");
        ui.v4.clicked.connect(this, "cuatro()");
        ui.v8.clicked.connect(this, "ocho()");
        ui.vi.clicked.connect(this, "inicio()");
        conectar(0);
    }
    
    public void conectar(int l)
    {
    	lotes = Integer.parseInt(ui.comboBox.currentText());
    	unaSemana = actual.darDuplicado(0);
    	dosSemanas = actual.darDuplicado(1);
    	cuatroSemanas = actual.darDuplicado(2);
    	ochoSemanas = actual.darDuplicado(3);
    	inicio = actual.darDuplicado(4);
    	ui.g1.setText(unaSemana.darMejorGanancia(lotes));
    	ui.n1.display(Integer.parseInt(unaSemana.darNumero(lotes)));
    	ui.s1.display(unaSemana.darMejorStop(lotes));
    	ui.g2.setText(dosSemanas.darMejorGanancia(lotes));
    	ui.n2.display(Integer.parseInt(dosSemanas.darNumero(lotes)));
    	ui.s2.display(dosSemanas.darMejorStop(lotes));
    	ui.g4.setText(cuatroSemanas.darMejorGanancia(lotes));
    	ui.n4.display(Integer.parseInt(cuatroSemanas.darNumero(lotes)));
    	ui.s4.display(cuatroSemanas.darMejorStop(lotes));
    	ui.g8.setText(ochoSemanas.darMejorGanancia(lotes));
    	ui.n8.display(Integer.parseInt(ochoSemanas.darNumero(lotes)));
    	ui.s8.display(ochoSemanas.darMejorStop(lotes));
    	ui.gi.setText(inicio.darMejorGanancia(lotes));
    	ui.ni.display(Integer.parseInt(inicio.darNumero(lotes)));
    	ui.si.display(inicio.darMejorStop(lotes));
    }
    
    public void una()
    {
    	AnalisisG ag = new AnalisisG(unaSemana, lotes);
    	ag.show();
    }
    
    public void dos()
    {
    	AnalisisG ag = new AnalisisG(dosSemanas, lotes);
    	ag.show();
    }
    
    public void cuatro()
    {
    	AnalisisG ag = new AnalisisG(cuatroSemanas, lotes);
    	ag.show();
    }
    
    public void ocho()
    {
    	AnalisisG ag = new AnalisisG(ochoSemanas, lotes);
    	ag.show();
    }
    
    public void inicio()
    {
    	AnalisisG ag = new AnalisisG(inicio, lotes);
    	ag.show();
    }
}

