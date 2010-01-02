/********************************************************************************
** Form generated from reading ui file 'AnalisisG.jui'
**
** Created: mar 7. abr 23:15:24 2009
**      by: Qt User Interface Compiler version 4.4.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package zulutrade.grafica;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_AnalisisG
{
    public QTableView tableView;
    public QLabel nombre;
    public QLabel bajo;
    public QLabel label_4;
    public QLabel label_16;
    public QLabel alto;
    public QLabel label_10;
    public QLabel label_17;
    public QLabel cierred;
    public QLabel cierre;
    public QToolButton toolButton;

    public Ui_AnalisisG() { super(); }

    public void setupUi(QWidget AnalisisG)
    {
        AnalisisG.setObjectName("AnalisisG");
        AnalisisG.resize(new QSize(459, 350).expandedTo(AnalisisG.minimumSizeHint()));
        tableView = new QTableView(AnalisisG);
        tableView.setObjectName("tableView");
        tableView.setGeometry(new QRect(220, 70, 217, 176));
        tableView.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);
        nombre = new QLabel(AnalisisG);
        nombre.setObjectName("nombre");
        nombre.setGeometry(new QRect(35, 0, 281, 61));
        QFont font = new QFont();
        font.setFamily("Verdana");
        font.setPointSize(21);
        nombre.setFont(font);
        bajo = new QLabel(AnalisisG);
        bajo.setObjectName("bajo");
        bajo.setGeometry(new QRect(35, 90, 61, 16));
        QFont font1 = new QFont();
        font1.setFamily("Verdana");
        font1.setPointSize(11);
        bajo.setFont(font1);
        label_4 = new QLabel(AnalisisG);
        label_4.setObjectName("label_4");
        label_4.setGeometry(new QRect(35, 60, 121, 21));
        QFont font2 = new QFont();
        font2.setFamily("Verdana");
        font2.setPointSize(11);
        label_4.setFont(font2);
        label_16 = new QLabel(AnalisisG);
        label_16.setObjectName("label_16");
        label_16.setGeometry(new QRect(35, 120, 121, 21));
        QFont font3 = new QFont();
        font3.setFamily("Verdana");
        font3.setPointSize(11);
        label_16.setFont(font3);
        alto = new QLabel(AnalisisG);
        alto.setObjectName("alto");
        alto.setGeometry(new QRect(35, 150, 61, 16));
        QFont font4 = new QFont();
        font4.setFamily("Verdana");
        font4.setPointSize(11);
        alto.setFont(font4);
        label_10 = new QLabel(AnalisisG);
        label_10.setObjectName("label_10");
        label_10.setGeometry(new QRect(98, 210, 21, 16));
        QFont font5 = new QFont();
        font5.setFamily("Verdana");
        font5.setPointSize(11);
        label_10.setFont(font5);
        label_17 = new QLabel(AnalisisG);
        label_17.setObjectName("label_17");
        label_17.setGeometry(new QRect(35, 180, 121, 21));
        QFont font6 = new QFont();
        font6.setFamily("Verdana");
        font6.setPointSize(11);
        label_17.setFont(font6);
        cierred = new QLabel(AnalisisG);
        cierred.setObjectName("cierred");
        cierred.setGeometry(new QRect(120, 210, 101, 16));
        QFont font7 = new QFont();
        font7.setFamily("Verdana");
        font7.setPointSize(11);
        cierred.setFont(font7);
        cierre = new QLabel(AnalisisG);
        cierre.setObjectName("cierre");
        cierre.setGeometry(new QRect(35, 210, 61, 16));
        QFont font8 = new QFont();
        font8.setFamily("Verdana");
        font8.setPointSize(11);
        cierre.setFont(font8);
        toolButton = new QToolButton(AnalisisG);
        toolButton.setObjectName("toolButton");
        toolButton.setGeometry(new QRect(160, 290, 111, 41));
        QFont font9 = new QFont();
        font9.setPointSize(12);
        font9.setBold(true);
        font9.setWeight(75);
        toolButton.setFont(font9);
        toolButton.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.TabFocus);
        retranslateUi(AnalisisG);

        AnalisisG.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget AnalisisG)
    {
        AnalisisG.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("AnalisisG", "Form"));
        nombre.setText(com.trolltech.qt.core.QCoreApplication.translate("AnalisisG", "SGA"));
        bajo.setText(com.trolltech.qt.core.QCoreApplication.translate("AnalisisG", "-34.45"));
        label_4.setText(com.trolltech.qt.core.QCoreApplication.translate("AnalisisG", "Promedio bajo:"));
        label_16.setText(com.trolltech.qt.core.QCoreApplication.translate("AnalisisG", "Promedio alto:"));
        alto.setText(com.trolltech.qt.core.QCoreApplication.translate("AnalisisG", "-34.45"));
        label_10.setText(com.trolltech.qt.core.QCoreApplication.translate("AnalisisG", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\" \"http://www.w3.org/TR/REC-html40/strict.dtd\">\n"+
"<html><head><meta name=\"qrichtext\" content=\"1\" /><style type=\"text/css\">\n"+
"p, li { white-space: pre-wrap; }\n"+
"</style></head><body style=\" font-family:'Verdana'; font-size:11pt; font-weight:400; font-style:normal;\">\n"+
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\">\u00b1</p></body></html>"));
        label_17.setText(com.trolltech.qt.core.QCoreApplication.translate("AnalisisG", "Promedio cierre:"));
        cierred.setText(com.trolltech.qt.core.QCoreApplication.translate("AnalisisG", "(45.55)"));
        cierre.setText(com.trolltech.qt.core.QCoreApplication.translate("AnalisisG", "-34.45"));
        toolButton.setText(com.trolltech.qt.core.QCoreApplication.translate("AnalisisG", "Grafica Stop"));
    } // retranslateUi

}

