package View;

import compilador.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {

  private JTextArea editorArea;
  private JTextArea messageArea;
  private JLabel statusBar;
  private File arquivoAtual;

  public static String getClasse(int token) {
    switch (token) {
        case Constants.t_id: return "identificador";
        case Constants.t_cte_int: return "constante_int";
        case Constants.t_cte_float: return "constante_float";
        case Constants.t_cte_char: return "constante_char";
        case Constants.t_cte_string: return "constante_string";

        case Constants.t_pr_ask:
        case Constants.t_pr_bool:
        case Constants.t_pr_char:
        case Constants.t_pr_define:
        case Constants.t_pr_end:
        case Constants.t_pr_elif:
        case Constants.t_pr_else:
        case Constants.t_pr_false:
        case Constants.t_pr_float:
        case Constants.t_pr_if:
        case Constants.t_pr_int:
        case Constants.t_pr_main:
        case Constants.t_pr_repeat:
        case Constants.t_pr_string:
        case Constants.t_pr_tell:
        case Constants.t_pr_true:
        case Constants.t_pr_until:
        case Constants.t_pr_while:
            return "palavra reservada";

        default:
            return "símbolo especial";
    }
  }

  public GUI() {
    setTitle("Compilador");
    setSize(1500, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);

    setLayout(new BorderLayout());

    JToolBar toolBar = new JToolBar();
    toolBar.setFloatable(false);
    toolBar.setPreferredSize(new Dimension(getWidth(), 70));

    JButton btnNovo = criarBotao("Novo [Ctrl-N]", "new.png");
    JButton btnAbrir = criarBotao("Abrir [Ctrl-O]", "open.png");
    JButton btnSalvar = criarBotao("Salvar [Ctrl-S]", "save.png");
    JButton btnCopiar = criarBotao("Copiar [Ctrl-C]", "copy.png");
    JButton btnColar = criarBotao("Colar [Ctrl-V]", "paste.png");
    JButton btnRecortar = criarBotao("Recortar [Ctrl-X]", "cut.png");
    JButton btnCompilar = criarBotao("Compilar [F7]", "compile.png");
    JButton btnEquipe = criarBotao("Equipe [F1]", "team.png");

    toolBar.add(btnNovo);
    toolBar.add(btnAbrir);
    toolBar.add(btnSalvar);
    toolBar.addSeparator();
    toolBar.add(btnCopiar);
    toolBar.add(btnColar);
    toolBar.add(btnRecortar);
    toolBar.addSeparator();
    toolBar.add(btnCompilar);
    toolBar.addSeparator();
    toolBar.add(btnEquipe);

    add(toolBar, BorderLayout.NORTH);

    editorArea = new JTextArea();
    editorArea.setFont(new Font("Consolas", Font.PLAIN, 14));
    JScrollPane editorScroll = new JScrollPane(editorArea,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    editorArea.setBorder(new NumberedBorder());

    messageArea = new JTextArea();
    messageArea.setEditable(false);
    messageArea.setFont(new Font("Consolas", Font.PLAIN, 14));
    JScrollPane messageScroll = new JScrollPane(messageArea,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorScroll, messageScroll);
    splitPane.setDividerLocation(550);
    splitPane.setResizeWeight(0.8);

    add(splitPane, BorderLayout.CENTER);

    statusBar = new JLabel(" ");
    statusBar.setBorder(new EmptyBorder(5, 5, 5, 5));
    statusBar.setPreferredSize(new Dimension(getWidth(), 25));
    add(statusBar, BorderLayout.SOUTH);

    configurarAtalhos(btnNovo, btnAbrir, btnSalvar, btnCopiar, btnColar, btnRecortar, btnCompilar, btnEquipe);
  }

  private JButton criarBotao(String texto, String icone) {
    java.net.URL imgURL = getClass().getResource("/icons/" + icone);
    JButton botao;

    if (imgURL != null) {
      botao = new JButton(texto, new ImageIcon(imgURL));
    } else {
      botao = new JButton(texto);
    }

    botao.setPreferredSize(new Dimension(100, 70));
    botao.setHorizontalTextPosition(SwingConstants.CENTER);
    botao.setVerticalTextPosition(SwingConstants.BOTTOM);

    return botao;
  }

  private void configurarAtalhos(JButton btnNovo, JButton btnAbrir, JButton btnSalvar,
      JButton btnCopiar, JButton btnColar, JButton btnRecortar,
      JButton btnCompilar, JButton btnEquipe) {

    InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = getRootPane().getActionMap();

    int shortcutMask = InputEvent.CTRL_DOWN_MASK;

    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, shortcutMask), "novo");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, shortcutMask), "abrir");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, shortcutMask), "salvar");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, shortcutMask), "copiar");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, shortcutMask), "colar");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, shortcutMask), "recortar");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "compilar");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "equipe");

    am.put("novo", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        editorArea.setText("");
        messageArea.setText("");
        statusBar.setText(" ");
        arquivoAtual = null;
      }
    });

    am.put("abrir", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(
            new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt"));
        int result = chooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
          File file = chooser.getSelectedFile();
          try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            editorArea.read(br, null);
            messageArea.setText("");
            statusBar.setText("Arquivo: " + file.getAbsolutePath());
            arquivoAtual = file;
          } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir arquivo.");
          }
        }
      }
    });

    am.put("salvar", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        if (arquivoAtual == null) {
          JFileChooser chooser = new JFileChooser();
          chooser.setFileFilter(
              new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt"));
          int result = chooser.showSaveDialog(null);

          if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".txt")) {
              file = new File(file.getAbsolutePath() + ".txt");
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
              editorArea.write(bw);
              messageArea.setText("");
              statusBar.setText("Arquivo: " + file.getAbsolutePath());
              arquivoAtual = file;
            } catch (IOException ex) {
              JOptionPane.showMessageDialog(null, "Erro ao salvar arquivo.");
            }
          }
        } else {
          try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoAtual))) {
            editorArea.write(bw);
            messageArea.setText("");
          } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar arquivo.");
          }
        }
      }
    });

    am.put("copiar", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        editorArea.copy();
      }
    });

    am.put("colar", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        editorArea.paste();
      }
    });

    am.put("recortar", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        editorArea.cut();
      }
    });

    am.put("compilar", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {

        messageArea.setText("");
        String texto = editorArea.getText();
        String codigo = texto.replaceAll("[\n\t]", " ");
        Lexico lexico = new Lexico(codigo);
        Sintatico sintatico = new Sintatico();
        Semantico semantico = new Semantico();

        try{
          sintatico.parse(lexico, semantico);
          messageArea.setText("código compilado com sucesso");
        }
        catch(AnalysisError ex){
          var linha = getLinha(texto, ex.getPosition());
          messageArea.setText("linha " + linha+":\n\t" + ex.getMessage());
        }


      }
    });

    am.put("equipe", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        messageArea.setText("Equipe de Desenvolvimento:\n- Vinícius Gonsalves da Silva e Lucas Lewin Vilbert");
      }
    });
    

    btnNovo.addActionListener(am.get("novo"));
    btnAbrir.addActionListener(am.get("abrir"));
    btnSalvar.addActionListener(am.get("salvar"));
    btnCopiar.addActionListener(am.get("copiar"));
    btnColar.addActionListener(am.get("colar"));
    btnRecortar.addActionListener(am.get("recortar"));
    btnCompilar.addActionListener(am.get("compilar"));
    btnEquipe.addActionListener(am.get("equipe"));
  }

  private int getLinha(String texto, int posicao){
    var linhas = texto.split("\n");
    var linha = 0;
    for (int i = 0; i < linhas.length; i++){
      posicao = posicao - (linhas[i].length() + 1);
      linha = i+1;
      if(posicao <= 0){
        break;
      }
    }
    return  linha;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new GUI().setVisible(true);
      }
    });
  }
}