package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class GateView extends FixedPanel implements ItemListener, MouseListener {
    private final Gate gate;
    private final JCheckBox[] inputs;
    private final JCheckBox result;
    private final Image image;
    private final Switch[] switches;
    private Color color;
    private Color trueColor;

    public GateView(Gate gate) {
        super(180, 120);
        this.gate = gate;

        int inputSize = gate.getInputSize();
        switches = new Switch[inputSize];
        this.inputs = new JCheckBox[inputSize];

        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputs[i] = new JCheckBox();
            gate.connect(i, switches[i]);
        }

        result = new JCheckBox();

        int marginLeft, marginTop;

        marginLeft = 10;
        marginTop = 30;

        for (int i = 0; i < inputSize; i++) {
            if (inputSize == 2) {
                add(inputs[i], marginLeft, marginTop + 20 * i, 20, 15);
            } else {
                add(inputs[i], marginLeft, marginTop + 12, 20, 15);
            }
            inputs[i].addItemListener(this);
        }

        JLabel saidaLabel = new JLabel("Saída:");

        // Carregamento das imagens
        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

//        color = Color.BLACK;
        addMouseListener(this);

        result.setEnabled(false);

        update();
    }

    private void update() {
        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputs[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }

        boolean result = this.gate.read();
        if (result) {
            if (trueColor == null) {
                this.color = Color.RED;
            } else {
                this.color = this.trueColor;
            }

        } else {
            this.color = Color.BLACK;
        }

        // ...e chamamos repaint para atualizar a tela. rsrsrs
        repaint();
        this.result.setSelected(result);
    }

    private boolean clickInsideCircle(int x, int y) {
        if(Math.pow(x-130,2) + Math.pow(y-50,2) <= Math.pow(10,2)) {
            System.out.println("line 93 [debug purpose]: Click event inside the circle");
            return true;
        } else {
            System.out.println("line 96 [debug purpose]: Click event outside the circle");
            return false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        System.out.println("line 108 [debug purpose]: (x,y): ("+x+","+y+")");

        // Se o clique foi dentro do circulo colorido/preto...
        if ( clickInsideCircle(x,y) ) {
            /*
            * (x-x0)² + (y-y0)² <= r²
            * ...então abrimos a janela seletora de cor...
            */
            this.trueColor = JColorChooser.showDialog(this, null, this.color);


            // ...e chamamos repaint para atualizar a tela.
            update();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de pressionar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de soltar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // entrar no painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // sair do painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 20, 25, 100, 50, this);

        g.setColor(this.color);
//        g.fillRect(120,30,40,40); // linha comentada
        g.fillOval(120,40,20,20);

        getToolkit().sync();
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        update();
    }
}