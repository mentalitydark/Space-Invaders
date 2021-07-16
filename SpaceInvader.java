import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SpaceInvader extends JPanel implements Runnable, KeyListener {

    Font minhafonte = new Font("Consolas", Font.BOLD, 20);
    private Nave nave;
    private int move = 0;
    private ArrayList<Tiro> tiros; // Parecido com o Array, só que podemos adicionar e retirar elementos.
    private ArrayList<Inimigo> inimigos;
    private ArrayList<Explosao> explosoes;
    private Background fundo; 
    private BufferedImage imagemInimigo;
    private BufferedImage imagemExplosao;
    private boolean ganhou;
    private boolean perdeu;
    private double timer = 10;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    // CONSTRUTOR - É chamado quando se chama o new SpaceInvader();
    public SpaceInvader() {

        // Imagem do inimigo
        try {
            imagemInimigo = ImageIO.read(new File("img/inimigos.png"));
        } catch(IOException e) {
            System.out.println("Não foi possível carregar a imagem da nave inimiga.");
            e.printStackTrace();
        }

        // Imagem da explosão
        try {
            imagemExplosao = ImageIO.read(new File("img/explosion.png"));
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a Explosão");
            e.printStackTrace();
        }

        // Se ganhou ou Perdeu
        ganhou = false;
        perdeu = false;


        // Declarações
        nave = new Nave();
        tiros = new ArrayList<Tiro>();
        inimigos = new ArrayList<Inimigo>();
        explosoes = new ArrayList<Explosao>();
        fundo = new Background();


        // Gera inimigos
        for(int i = 0; i < 60; i++) {
            int nave = new Random().nextInt(7);
            inimigos.add(new Inimigo(imagemInimigo, 30 + i%10 * 60, 0 + i/10 * 50, 1, nave));
        }


        // Thread
        Thread lacoDoJogo = new Thread(this);
        lacoDoJogo.start();
    }

    public void run() {

        while(true) {
            long timeI = System.currentTimeMillis();

            update();
            repaint();

            long timeF = System.currentTimeMillis();
            long diferenca = 16 - (timeF - timeI);

            if (diferenca > 0)
                dorme(diferenca);

        }

    }
    private void update() {

        // Vitória
        if(inimigos.size() == 0) {
            ganhou = true;
        }

        // Atualiza a nave
        nave.move(move);

        // Atualiza os inimigos
        for(int i = 0; i < inimigos.size(); i++) {
            inimigos.get(i).atualizar();
            if(inimigos.get(i).getY() >= Main.monitor.getHeight() - 200) {
                perdeu = true;
            }
        }

        // Atualiza os tiros
        for (int i = 0; i < tiros.size(); i++) {
            tiros.get(i).atualiza();
            if(tiros.get(i).destruir()) {
                tiros.remove(i);
                i--;
            } else {
                for (int j = 0; j < inimigos.size(); j++) {
                    if (tiros.get(i).target(inimigos.get(j))) {
                        
                        explosoes.add(new Explosao(imagemExplosao, 
                                        inimigos.get(j).getX(), 
                                        inimigos.get(j).getY(), 
                                        inimigos.get(j).getVelocidade(),
                                        inimigos.get(j).getDirecao()));

                        inimigos.remove(j);
                        tiros.remove(i);
                         
                        break;
                    }
                }
            }

            

        }
        for (int i = 0; i < inimigos.size(); i++) {
            if(inimigos.get(i).getX() <= 0 || inimigos.get(i).getX() >= Main.monitor.getWidth() - 50) {
                for (int j = 0; j < inimigos.size(); j++) {
                    inimigos.get(j).voltar();
                }
                break;
            }
        }

        for (int i = 0; i < explosoes.size(); i++) {
            explosoes.get(i).atualizar();
            if (explosoes.get(i).getDuration()) {
                explosoes.remove(i);
                i--;
            }
        }
    }


    // Faz parte do JPanel
    int x = 0;
    public void paintComponent(Graphics g2) {
        super.paintComponent(g2); // limpa a tela

        
        // Para ligar o Anti-Aliasing
        Graphics2D g = (Graphics2D) g2.create();
        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                );
                
        fundo.pinta(g);


        // Desenha as explosões
        for (int i = 0; i < explosoes.size(); i++) {
            explosoes.get(i).pintar(g);
        }


        // Desenha os tiros
        for (int i = 0; i < tiros.size(); i++) {
            tiros.get(i).pintar(g);
        }

        // Desenha a nave
        nave.pintar(g);

        // Desenha os inimigos
        for(int i = 0; i < inimigos.size(); i++) {
            inimigos.get(i).pintar(g);
        }

        if(ganhou) {
            g.setColor(Color.white);
            g.setFont(minhafonte);
            g.drawString("VOCÊ GANHOU! TERMINOU O JOGO!\n Fechando o jogo em: "+df2.format(timer)+" segundos", Main.monitor.getWidth()/2 - 100, Main.monitor.getHeight()/2);

            timer -= 0.016f;
            if(timer <= 0) {
                System.exit(0);
            }
        }
        if(perdeu) {
            g.setColor(Color.white);
            g.setFont(minhafonte);
            g.drawString("VOCÊ PERDEU! TERMINOU O JOGO!\n Fechando o jogo em: "+df2.format(timer)+" segundos", Main.monitor.getWidth()/2 - 100, Main.monitor.getHeight()/2);
            
            timer -= 0.01666f;
            if(timer <= 0) {
                System.exit(0);
            }
        }
            g.setColor(Color.white);
            g.setFont(minhafonte);
            g.drawString("Aperte ESC para sair.", 0, Main.monitor.getHeight()-10);
            
    }



    private void dorme(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_D) {
            move = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            move = -1;
        }

    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D) {
            move = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            move = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE && nave.cooldown()) {
            tiros.add(nave.atirar());
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        // Cheats
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            ganhou = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_EQUALS) {
            nave.moreMove(1);
        }
        if(e.getKeyCode() == KeyEvent.VK_MINUS) {
            nave.moreMove(-1);
        }
    }
    public void keyTyped(KeyEvent e) {
        
    }
}
