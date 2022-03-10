
import acm.graphics.*;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;
import java.awt.*;
import java.util.Random;


public class Hangman extends GraphicsProgram {

    private int numberLetters;
    private Random rand = new Random();
    private String[] wordList = {"help","impound","test","teacher","apex","legends","snake","game","from","stage","offense","experienced","competition"};
    private String word;
    private int wrongGuesses;
    private boolean wrong;
    private int rightAnswers;
    GLine stage1 = new GLine(700, 350,500,350);
    GLine stage2 = new GLine(600, 350,600,100);
    GLine stage3 = new GLine(525, 100,600,100);
    GLine stage4 = new GLine(525, 100,525,150);


    @Override
    public void init(){
        word = wordList[rand.nextInt(wordList.length)].toUpperCase();
        numberLetters = word.length();
        wrongGuesses = 0;
        rightAnswers = 0;
        add(stage1);
        add(stage2);
        add(stage3);
        add(stage4);

        for (int i = 0; i < numberLetters; i++) {
            GRect letter = new GRect(25, 1);
            add(letter, 50 * i + letter.getWidth()+50, getHeight()/2);
            int letterNumber = i;
        }
        add(new GLabel("Wrong Guesses"), 0, 10);
        gameHappen();
    }

    private void gameHappen(){
        while (true){
            String guess = Dialog.getString("what is your guess").toUpperCase();


            if(guess.length() > 1 || guess.charAt(0) < 64 || guess.charAt(0) > 90){
                Dialog.showMessage("invalid guess");
            } else {
                GLabel GGuess = new GLabel(guess);
                checkRight( guess);
                checkWrong(GGuess);
                drawHangman();
            }
        }
    }

    private void checkRight( String guess){
        int right = 0;
        wrong = false;
        for (int i = 0; i < word.length(); i++) {
            if (guess.charAt(0) == word.charAt(i)) {
                GLabel GGuess = new GLabel(guess);
                add(GGuess, i * 50 + 85, getHeight() / 2 - 3);
                right++;
                rightAnswers++;
            }

        }
        if(right == 0){
            wrongGuesses++;
            wrong = true;
        }

        if(rightAnswers == word.length()){
            win();
        }
    }

    private void checkWrong(GLabel GGuess){
        if (wrong){
            add(GGuess,10, 12 * wrongGuesses +15 );
        }
    }

    private void drawHangman(){
       GOval  head = new GOval(40,50);
       GLine body = new GLine(525,200,525,260);
       GLine rArm = new GLine(525,200,510,245);
       GLine  lArm = new GLine(525,200,540,245);
       GLine rLeg = new GLine(525,260,510,310);
       GLine lLeg = new GLine(525,260,540,310);

        switch (wrongGuesses){
            case 0:
                break;
            case 1:
                double headX = 505;
                double headY = 150;
                add(head, headX, headY);
                break;
            case 2:
                add(body);
                break;
            case 3:
                add(rArm);
                break;
            case 4:
                add(lArm);
                break;
            case 5:
                add(rLeg);
                break;
            case 6:
                add(lLeg);
                Dialog.showMessage("You Lost");
                System.exit(1);
                break;
        }
    }

    private void win(){

        Dialog.showMessage("You won");
        GRect g = new GRect(10000000, 10000);
        add(g, 0, -3);
        g.setFillColor(Color.white);
        g.setFilled(true);
        init();
    }



    public static void main(String[] args) {
        new Hangman().start();
    }
}