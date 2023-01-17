/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teclado;

/**
 *
 * @author tauro
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class JKeyboardPane extends JPanel{
 
 JTextField txt;
 //String teclas[]={"1","2","3","4","5","6","7","8","9","0","Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Ñ","Z","X","C","V","B","N","M","."};
 //Integer teclas[]={0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
 ArrayList<JButton> botones=new ArrayList<JButton>();
 JPanel pletras,pespacio;
 
 public JKeyboardPane(JTextField t){
  
  txt=t;
  pletras=new JPanel();
  setLayout(new BorderLayout());
  pletras.setLayout(new GridLayout(4,10,0,0));
  
  ActionListener accion=new ActionListener(){

   @Override
   public void actionPerformed(ActionEvent e) {
    JButton b=(JButton)e.getSource();
    if(!b.getText().equalsIgnoreCase(" ")){
     txt.setText(""+txt.getText()+b.getText());
    }else{
     txt.setText(txt.getText()+" ");
    }
   }
   
  };

  for(int i=0;i<=11;i++) {
   if (i < 9) {
    JButton b = new JButton("" + (i + 1));
    b.addActionListener(accion);
    pletras.add(b);
    botones.add(b);
   } else {
    if (i == 9) {
     JButton b = new JButton("");
     b.setEnabled(false);
     pletras.add(b);
     botones.add(b);
    }
    if (i == 10) {
     JButton b = new JButton("0");
     b.addActionListener(accion);
     pletras.add(b);
     botones.add(b);
    }
    if (i == 11) {
     JButton b = new JButton("");
     b.setEnabled(false);
     pletras.add(b);
     botones.add(b);
    }
   }
  }
  /*for(int i=0;i<37;i++){
   if(teclas[i].equalsIgnoreCase("Z")){
    JLabel l=new JLabel();
    pletras.add(l);
   }
   JButton b=new JButton(teclas[i]);
   b.addActionListener(accion);
   pletras.add(b);
   botones.add(b);
  }*/
  
  /*pespacio=new JPanel(new GridLayout(1,3));
  JButton bespacio=new JButton(" ");
  bespacio.addActionListener(accion);
  pespacio.add(new JLabel());
  pespacio.add(bespacio);
  pespacio.add(new JLabel());*/
  add(pletras);
  //add(pespacio,BorderLayout.SOUTH);
 }
}