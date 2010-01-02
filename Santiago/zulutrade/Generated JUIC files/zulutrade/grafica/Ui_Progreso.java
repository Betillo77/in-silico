/********************************************************************************
** Form generated from reading ui file 'Progreso.jui'
**
** Created: lun 15. jun 04:43:10 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package zulutrade.grafica;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_Progreso implements com.trolltech.qt.QUiForm<QDialog>
{
    public QProgressBar progressBar;
    public QLabel CV;
    public QLabel actual;

    public Ui_Progreso() { super(); }

    public void setupUi(QDialog Progreso)
    {
        Progreso.setObjectName("Progreso");
        Progreso.resize(new QSize(375, 259).expandedTo(Progreso.minimumSizeHint()));
        progressBar = new QProgressBar(Progreso);
        progressBar.setObjectName("progressBar");
        progressBar.setGeometry(new QRect(70, 150, 261, 51));
        progressBar.setValue(0);
        CV = new QLabel(Progreso);
        CV.setObjectName("CV");
        CV.setGeometry(new QRect(60, 20, 221, 41));
        QFont font = new QFont();
        font.setFamily("Verdana");
        font.setPointSize(22);
        CV.setFont(font);
        actual = new QLabel(Progreso);
        actual.setObjectName("actual");
        actual.setGeometry(new QRect(70, 90, 271, 51));
        QFont font1 = new QFont();
        font1.setFamily("Verdana");
        font1.setPointSize(16);
        actual.setFont(font1);
        retranslateUi(Progreso);

        Progreso.connectSlotsByName();
    } // setupUi

    void retranslateUi(QDialog Progreso)
    {
        Progreso.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Progreso", "Dialog", null));
        CV.setText(com.trolltech.qt.core.QCoreApplication.translate("Progreso", "Ejecutando:", null));
        actual.setText(com.trolltech.qt.core.QCoreApplication.translate("Progreso", "Creando proveedores", null));
    } // retranslateUi

}

