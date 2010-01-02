package zulutrade.grafica;


import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QSortFilterProxyModel;


public class ModeloOrdenar extends QSortFilterProxyModel 
{
    public ModeloOrdenar(QObject parent) 
    {
        super(parent);
    }
    
    @Override
    protected boolean filterAcceptsRow(int sourceRow, QModelIndex sourceParent) 
    {
    	return true;
    }

    @Override
    protected boolean lessThan(QModelIndex izquierda, QModelIndex derecha) 
    {
    	Object objIzquierda = sourceModel().data(izquierda);
    	Object objDerecha = sourceModel().data(derecha);
    	if(objIzquierda instanceof String)
    	{
    		try
    		{
    			int iz = Integer.parseInt((String)objIzquierda);
    			int der = Integer.parseInt((String)objDerecha);
    			return iz < der;
    		}
    		catch(Exception e)
    		{
    			try
    			{
        			double iz = Double.parseDouble(((String)objIzquierda).replace(',', '.'));
        			double der = Double.parseDouble(((String)objDerecha).replace(',', '.'));
        			return iz < der;
    			}
    			catch(Exception f)
    			{
    			}
    		}
    	}
		return false;
    }
}
