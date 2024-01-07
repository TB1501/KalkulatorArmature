package com.TBasic;

import javax.swing.*;
import java.awt.event.*;

public class Armatura {
    public JPanel panelMain;
    private JTextField dimenzijaLxTextField;
    private JTextField dimenzijaLyTextField;
    private JRadioButton jednostrukoArmiraniPresjekRadioButton;
    private JRadioButton dvostrukoArmiraniPresjekRadioButton;
    private JLabel KlasaBetona;
    private JLabel naslov;
    private JLabel dimenzije;
    private JComboBox klasaBetonaComboBox;
    private JTextField zastitniSlojTextField;
    private JTextField fi_x_TextField;
    private JTextField fi_y_TextField;
    private JButton izracunajButton;
    private JLabel dimenzijaLx;
    private JLabel dimenzijaLy;
    private JLabel pretpostavljenaArmatura;
    private JLabel fi_x;
    private JLabel fi_y;
    private JLabel minArmatura;
    private JLabel maxArmaturaa;
    private JLabel rezMinArmatura;
    private JLabel rezMaxArmatura;
    private JTextField dimenzijaHTextField;
    private JLabel dimenzijaH;
    private JLabel debljinaZastitnogSloja;

    public Armatura()
    {
        //Definiram string listu sa klasama betona za objekt klasaBetonaCombobox
        String[] klaseBetona={"12/15","16/20", "20/25", "25/30", "30/37", "35/45", "40/50", "45/55", "50/60"};
        klasaBetonaComboBox.setModel(new DefaultComboBoxModel<>(klaseBetona));

        //Definiram double listu sa čvrstoćom betona za valjak i srednju osnu vlačnu čvrstoću betona
        Double[] cvrstocaBetona={12.0,16.0,20.0,25.0,30.0,35.0,40.0,45.0, 50.0};
        Double[] srednjaOsnaVlacnaCvrstocaBetona={1.6, 1.9, 2.2, 2.6, 2.9, 3.2, 3.5, 3.8,4.1};

        izracunajButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Dohvaćamo string iz polja dimenzijaLxTextField koji sadrži duljinu stranice ploče u x smjeru
                String dimenzijaLxText = dimenzijaLxTextField.getText();
                try {
                    double dimenzijaLxValue = Double.parseDouble(dimenzijaLxText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Unos mora biti broj sa točkom kao decimalnim separatorom");
                }

                //Dohvaćamo string iz polja dimenzijaLyTextField koji sadrži duljinu stranice ploče u y smjeru
                String dimenzijaLyText = dimenzijaLyTextField.getText();
                try {
                    double dimenzijaLyValue = Double.parseDouble(dimenzijaLyText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Unos mora biti broj sa točkom kao decimalnim separatorom");
                }

                //Dohvaćamo string iz polja dimenzijaHTextField koji sadrži debljinu ploče
                String dimenzijaHText = dimenzijaHTextField.getText();
                try {
                    double dimenzijaHValue = Double.parseDouble(dimenzijaHText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Unos mora biti broj sa točkom kao decimalnim separatorom");
                }

                //Dohvaćamo string iz polja zastitniSlojTextField koji sadrži debljinu zaštitnog sloja betona
                String zastitniSlojText = zastitniSlojTextField.getText();
                try {
                    double zastitniSlojValue = Double.parseDouble(zastitniSlojText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Unos mora biti broj sa točkom kao decimalnim separatorom");
                }

                //Kreiramo instance klase za vrijendosti koje dobivama iz polja fi_x_TextField i fi_y_TextField, te instancu selectedValue putem koje upravljamo funkcija radio gumba
                String selectedValue = "";
                String fi_x_Text = fi_x_TextField.getText();
                String fi_y_Text = fi_y_TextField.getText();

                //Kreiramo if petlju u kojoj na temelju odabranog radio gumba za jednostruko ili dvostruko armirani presjek proračuna armaturu u ploči
                if (jednostrukoArmiraniPresjekRadioButton.isSelected())
                {
                    selectedValue = "Jednostruko Armirani Presjek";

                    //Dohvaćamo string iz polja fi_x_TextField koji sadrži promjer pretpostavljene armature u ploči
                    try {
                        double fi_x_Value = Double.parseDouble(fi_x_Text);
                        // Use fi_x_Value for further operations or calculations
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelMain, "Unos mora biti promjer šipki u milimetrima");
                    }

                    //Definiramo uvjet za armaturu u y smjeru da mora biti 0 za jednostruko armirani presjek
                    try {
                        double fi_y_Value = Double.parseDouble(fi_y_Text);
                        if (fi_y_Value != 0.0) {
                            JOptionPane.showMessageDialog(panelMain, "Unos za armaturu u y smjeru mora biti 0 za jednostruko armirani presjek");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelMain, "Unos za armaturu u y smjeru mora biti 0 za jednostruko armirani presjek");
                    }

                    //Na temelju indexa u listi klasaBetonaComboBox definiramo vrijednosti na istom indexu u listama srednjaOsnaVlacnaCvrstocaBetona i cvrstocaBetona
                    int selectedIndex = klasaBetonaComboBox.getSelectedIndex();
                    double selectedSrednjaOsnaVlacnaCvrstoca = 0;
                    double selectedCvrstocaBetona=0;
                    if (selectedIndex >= 0 && selectedIndex < srednjaOsnaVlacnaCvrstocaBetona.length)
                    {
                        selectedSrednjaOsnaVlacnaCvrstoca = srednjaOsnaVlacnaCvrstocaBetona[selectedIndex];
                        selectedCvrstocaBetona = cvrstocaBetona[selectedIndex];

                    }
                    //Izračun minimalne i maksimalne armature za jednostruko armirani presjek
                    double statickaVisina=  Double.parseDouble(dimenzijaHText) * 100 - Double.parseDouble(zastitniSlojText) - Double.parseDouble(fi_x_Text) / 20;
                    double minArm1 = 0.0013 * 100 * statickaVisina;
                    double minArm2 = 0.26 * 100 * statickaVisina * (selectedSrednjaOsnaVlacnaCvrstoca/500);
                    double maxArm1=0.022*100*(Double.parseDouble(dimenzijaHText)*100);
                    double maxArm2=0.365*100*statickaVisina*((selectedCvrstocaBetona/1.5)/434.78);

                    //Odabir  min i max armature te prikaz rezultata
                    if(minArm1>minArm2)
                    {
                        rezMinArmatura.setText(String.valueOf(minArm1));
                    }
                    else
                    {
                        rezMinArmatura.setText(String.valueOf(minArm2));
                    }

                    if(maxArm1>maxArm2)
                    {
                        rezMaxArmatura.setText(String.valueOf(maxArm2));
                    }
                    else
                    {
                        rezMaxArmatura.setText(String.valueOf(maxArm1));
                    }

                } else if (dvostrukoArmiraniPresjekRadioButton.isSelected()) {
                    selectedValue = "Dvostruko Armirani Presjek";

                    try {
                        double fi_x_Value = Double.parseDouble(fi_x_Text);
                        double fi_y_Value = Double.parseDouble(fi_y_Text);
                        // Use fi_x_Value and fi_y_Value for further operations or calculations
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelMain, "Unos mora biti broj u milimetrima");
                    }

                    //Dohvaćamo string iz polja fi_x_TextField koji sadrži promjer pretpostavljene armature u ploči
                    int selectedIndex = klasaBetonaComboBox.getSelectedIndex();
                    double selectedSrednjaOsnaVlacnaCvrstoca = 0;
                    double selectedCvrstocaBetona=0;
                    if (selectedIndex >= 0 && selectedIndex < srednjaOsnaVlacnaCvrstocaBetona.length)
                    {
                        selectedSrednjaOsnaVlacnaCvrstoca = srednjaOsnaVlacnaCvrstocaBetona[selectedIndex];
                        selectedCvrstocaBetona = cvrstocaBetona[selectedIndex];

                    }

                    //Izračun minimalne i maksimalne armature za dvostruko armirani presjek
                    double statickaVisina=  Double.parseDouble(dimenzijaHText) * 100 - Double.parseDouble(zastitniSlojText) - (Double.parseDouble(fi_x_Text)+Double.parseDouble(fi_y_Text))/ 20;
                    double minArm1 = 0.0013 * 100 * statickaVisina;
                    double minArm2 = 0.26 * 100 * statickaVisina * (selectedSrednjaOsnaVlacnaCvrstoca/500);
                    double maxArm1=0.031*100*(Double.parseDouble(dimenzijaHText)*100);
                    double maxArm2=0.365*100*statickaVisina*((selectedCvrstocaBetona/1.5)/434.78);

                    //Odabir  min i max armature te prikaz rezultata
                    if(minArm1>minArm2)
                    {
                        rezMinArmatura.setText(String.valueOf(minArm1));
                    }
                    else
                    {
                        rezMinArmatura.setText(String.valueOf(minArm2));
                    }

                    if(maxArm1>maxArm2)
                    {
                        rezMaxArmatura.setText(String.valueOf(maxArm2));
                    }
                    else
                    {
                        rezMaxArmatura.setText(String.valueOf(maxArm1));
                    }
                };

            }
        });

        dimenzijaLxTextField.addComponentListener(new ComponentAdapter()
        //Dodajem klase betona u comboBox
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                super.componentResized(e);
                dimenzijaLxTextField.setText("0");
            }
        });

        dimenzijaLyTextField.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                super.componentResized(e);
                dimenzijaLyTextField.setText("0");
            }
        });

        dimenzijaHTextField.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                super.componentResized(e);
                dimenzijaHTextField.setText("0");
            }
        });

        klasaBetonaComboBox.addContainerListener(new ContainerAdapter()
        {
            @Override
            public void componentAdded(ContainerEvent e)
            {
                super.componentAdded(e);

            }

        });

        jednostrukoArmiraniPresjekRadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e){ }
        });

        dvostrukoArmiraniPresjekRadioButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e){ }
        });

        zastitniSlojTextField.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                super.componentResized(e);
                zastitniSlojTextField.setText("0");
            }
        });

        fi_x_TextField.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                super.componentResized(e);
                fi_x_TextField.setText("0");
            }
        });

        fi_y_TextField.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                super.componentResized(e);
                fi_y_TextField.setText("0");
            }
        });

        rezMinArmatura.addContainerListener(new ContainerAdapter()
        {
            @Override
            public void componentAdded(ContainerEvent e)
            {
                super.componentAdded(e);
            }
        });

        rezMaxArmatura.addContainerListener(new ContainerAdapter()
        {
            @Override
            public void componentAdded(ContainerEvent e)
            {
                super.componentAdded(e);
            }
        });
    }

}
