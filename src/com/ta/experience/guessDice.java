package com.ta.experience;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class guessDice {

    private JFrame frame;
    private JLabel diceLbl;
    private JLabel headerLbl;
    private JButton rollBtn;
    private JButton exitBtn;
    private JButton resultBtn;
    private JButton newGameBtn;
    private JRadioButton[] guessBtn;
    private ButtonGroup groupBtn;
    private ImageIcon[] diceImg;
    private int currentImageIndex;

    private JTable scoreTbl;
    private DefaultTableModel tableModel;
    private JScrollPane scroll;
    private int score;

    public guessDice(){
        score=0;
        frame = new JFrame("Can you guess the number on dice?");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setLayout(new BorderLayout());

        //menambahkan gambar yang banyak menggunakan Array
        diceImg = new ImageIcon[6];
        diceImg[0] = new ImageIcon("C:\\Users\\Arina\\IdeaProjects\\TA_DKP_ARINA_Tebak Lempar Dadu\\src\\com\\ta\\experience\\dice\\dice1.png");
        diceImg[1] = new ImageIcon("C:\\Users\\Arina\\IdeaProjects\\TA_DKP_ARINA_Tebak Lempar Dadu\\src\\com\\ta\\experience\\dice\\dice2.png");
        diceImg[2] = new ImageIcon("C:\\Users\\Arina\\IdeaProjects\\TA_DKP_ARINA_Tebak Lempar Dadu\\src\\com\\ta\\experience\\dice\\dice3.png");
        diceImg[3] = new ImageIcon("C:\\Users\\Arina\\IdeaProjects\\TA_DKP_ARINA_Tebak Lempar Dadu\\src\\com\\ta\\experience\\dice\\dice4.png");
        diceImg[4] = new ImageIcon("C:\\Users\\Arina\\IdeaProjects\\TA_DKP_ARINA_Tebak Lempar Dadu\\src\\com\\ta\\experience\\dice\\dice5.png");
        diceImg[5] = new ImageIcon("C:\\Users\\Arina\\IdeaProjects\\TA_DKP_ARINA_Tebak Lempar Dadu\\src\\com\\ta\\experience\\dice\\dice6.png");


        diceLbl = new JLabel(diceImg[0]);
        diceLbl.setHorizontalAlignment(JLabel.CENTER);
        diceLbl.setBackground(Color.lightGray);
        diceLbl.setOpaque(true);
        frame.getContentPane().add(diceLbl,BorderLayout.CENTER);

        JPanel headerPanel = new JPanel();
        headerLbl = new JLabel("Try to guess the Number if you can: ");
        frame.getContentPane().add(headerPanel, BorderLayout.BEFORE_LINE_BEGINS);

        JPanel radioPnl = new JPanel(new GridLayout(2,3));
        guessBtn = new JRadioButton[6];
        groupBtn = new ButtonGroup();

        //membuat nilai radio button pada button group
        for (int i=0; i<6; i++){
            guessBtn[i] = new JRadioButton(String.valueOf(i+1));
            guessBtn[i].setBackground(Color.GRAY);
            radioPnl.add(guessBtn[i]);
            groupBtn.add(guessBtn[i]);
        }
        frame.getContentPane().add(radioPnl, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.ORANGE);
        rollBtn = new JButton("Roll");
        exitBtn = new JButton("Exit");
        resultBtn = new JButton("Result");
        newGameBtn = new JButton("New Game");
        rollBtn.setBackground(Color.CYAN);
        exitBtn.setBackground(Color.RED);
        resultBtn.setBackground(Color.YELLOW);
        newGameBtn.setBackground(Color.GREEN);
        buttonPanel.setLayout(new GridLayout(1, 4));
        buttonPanel.add(rollBtn);
        buttonPanel.add(exitBtn);
        buttonPanel.add(resultBtn);
        buttonPanel.add(newGameBtn);
        frame.add(buttonPanel, BorderLayout.SOUTH);


        //memanggil method untuk menampilkan score ke dalam tabel
        initializeScoreTable();

        //event roll button
        rollBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (groupBtn.getSelection()==null){
                    JOptionPane.showMessageDialog(frame,"Please select a guess","No Guess Seleted", JOptionPane.WARNING_MESSAGE);
                }
                rollDice();

                diceLbl.setIcon(diceImg[currentImageIndex]);

                checkGuess();

            }
        });
        //event exit button
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        resultBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScore();
            }
        });
        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                score = 0;
            }
        });
    }

    //method menambahkan score
    private void initializeScoreTable(){
        String[] columnNames = {"No.","Guess","Result"};
        tableModel = new DefaultTableModel(columnNames,0);
        scoreTbl = new JTable(tableModel);
        scroll = new JScrollPane(scoreTbl);
        frame.getContentPane().add(scroll, BorderLayout.EAST);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(Color.PINK);
        scoreTbl.setDefaultRenderer(Object.class, renderer);
    }
    //method mengacak gambar
    private void rollDice(){
        currentImageIndex = (int)(Math.random()*6);
    }

    //method pengkondisian pada jawaban dan gambar yang muncul
    private void checkGuess(){
        for (int i=0; i<6; i++){
            if (guessBtn[i].isSelected()){
                int afterGuessNum = i+1;
                String result;
                if (afterGuessNum==currentImageIndex+1){
                    result = "Correct";
                    score++;
                }else {
                    result = "Wrong";
                }

                Object[] rowData = {(tableModel.getRowCount()+1), afterGuessNum, result};
                tableModel.addRow(rowData);

                if (afterGuessNum==currentImageIndex+1){
                    JOptionPane.showMessageDialog(frame,"Your Answer is Correct","Wohoo, You're so Lucky",JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Your Answer is Wrong, The Correct Number is: "+ (currentImageIndex+1),"Hahaha, Your hands don't smell good",JOptionPane.ERROR_MESSAGE);
                }
                break;
            }
        }
    }
    private void showScore(){
        JOptionPane.showMessageDialog(frame,"Your Total wins: "+score,"Score",JOptionPane.INFORMATION_MESSAGE);
    }
    public void show(){
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //contructor
                guessDice game = new guessDice();
                game.show();
            }
        });
    }
}
