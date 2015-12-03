package projeto_estatistica;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//Impedir usuario de digitar letras e caracteres exceto ponto
/**
 *
 * @author DANIEL
 */
public class CriarProjeto extends javax.swing.JFrame {

    /**
     * Creates new form Cadastro_Cliente
     */
    double totalgeral = 0, c, somatoriotrat;
    int tratamentos = 4, repetiçoes = 4, numero = 0;
    private double somatoriox2;

    public CriarProjeto(int tratamentos, int repetiçoes) {

        this.tratamentos = tratamentos;
        this.repetiçoes = repetiçoes;
        initComponents();
        //jLabel1.setVisible(false);
        //setIconImage(new ImageIcon("src\\Icons\\icon_C_Movie.png").getImage());
        iniciar();
        /*jTable.setColumnSelectionAllowed(true);
         jTable.setRowSelectionAllowed(true);
         jTable.setSurrendersFocusOnKeystroke(true);
         */

        //ajustarcolunas();
    }

    public CriarProjeto(String caminho) {
        initComponents();
        String arquivo = caminho;
        String c, f = "";
        try {
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            try {
                while (br.ready()) {
                    c = "";
                    c += br.readLine();
                    if (!c.equals("")) {
                        f += c + "\n";
                    }
                }
                System.out.println("");
                String tratamentostxt[] = f.split("\n");
                tratamentos = tratamentostxt.length;
                String repetiçoestxt[] = tratamentostxt[0].split(";");
                repetiçoes = repetiçoestxt.length;
                iniciar();
                for (int i = 0; i < tratamentos; i++) {
                    repetiçoestxt = tratamentostxt[i].split(";");
                    System.out.println("r: " + repetiçoestxt.length);
                    for (int j = 1; j <= repetiçoes; j++) {
                        if (repetiçoestxt[j - 1].equals(" ")) {
                            jTable.setValueAt("", i, j);
                        } else {
                            jTable.setValueAt(repetiçoestxt[j - 1], i, j);
                        }
                    }
                }
                soma();
            } catch (IOException ex) {
                Logger.getLogger(CriarProjeto.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
        }
    }

    public void iniciar() {
        jLabel2.setHorizontalAlignment(JLabel.HORIZONTAL);
        int tamanho = 1031 / (repetiçoes + 2);
        jLabel1.setBounds(0, 0, tamanho, 32);
        jLabel2.setBounds(tamanho, 0, (tamanho * repetiçoes), 32);
        jLabel3.setBounds(tamanho * (repetiçoes + 1), 0, tamanho + 4, 32);
        setLocationRelativeTo(null);
        JTableHeader cabecalho = jTable.getTableHeader();
        cabecalho.setFont(new Font("Tahoma", 1, 16));
        final int repet = repetiçoes;
        //Linhas e colunas que podem ser editadas
        DefaultTableModel dtm = new DefaultTableModel(null, new Object[]{"<html><body><p><font size='3'>Tratamentos</p></font></body></html>"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == repet + 1 || row == jTable.getRowCount() - 1) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        //Setar linhas e colunas
        for (int i = 1; i <= repetiçoes + 1; i++) {
            if (i != repetiçoes + 1) {
                dtm.addColumn("" + i);
            } else {
                dtm.addColumn("Total");
            }
        }

        for (int i = 0; i < tratamentos; i++) {
            String array[] = new String[repetiçoes + 2];
            for (int j = 0; j <= repetiçoes + 1; j++) {
                if (j == 0) {

                    if (dtm.getRowCount() == 0) {
                        array[j] = "A";
                    }
                    if (dtm.getRowCount() == 1) {
                        array[j] = "B";
                    }
                    if (dtm.getRowCount() == 2) {
                        array[j] = "C";
                    }
                    if (dtm.getRowCount() == 3) {
                        array[j] = "D";
                    }
                    if (dtm.getRowCount() == 4) {
                        array[j] = "E";
                    }
                    if (dtm.getRowCount() == 5) {
                        array[j] = "F";
                    }
                    if (dtm.getRowCount() == 6) {
                        array[j] = "G";
                    }
                    if (dtm.getRowCount() == 7) {
                        array[j] = "H";
                    }
                    if (dtm.getRowCount() == 8) {
                        array[j] = "I";
                    }
                    if (dtm.getRowCount() == 9) {
                        array[j] = "J";
                    }
                } else {
                    array[j] = "";
                }
            }
            dtm.addRow(array);
        }

        String array[] = new String[repetiçoes + 2];
        for (int i = 0; i < repetiçoes; i++) {
            array[i] = "\n";
        }
        array[repetiçoes] = "Total: ";
        array[repetiçoes + 1] = "";
        dtm.addRow(array);
        jTable.setModel(dtm);
        //Setar cor cinza para colunas que não podem ser editadas
        DefaultTableCellRenderer alterarcoluna = new DefaultTableCellRenderer() {
            public void setValue(Object value) {
                setBackground(new Color(238, 238, 238));
                setHorizontalAlignment(JLabel.CENTER);
                //setForeground((value=="1")?new Color(230):new Color(100));
                super.setValue(value);
            }
        };
        //Alinhar ao centro
        jTable.getColumn("<html><body><p><font size='3'>Tratamentos</p></font></body></html>").setCellRenderer(alterarcoluna);
        jTable.getColumn("Total").setCellRenderer(alterarcoluna);
        int t = jTable.getColumn("<html><body><p><font size='3'>Tratamentos</p></font></body></html>").getMinWidth();
        System.out.println("" + t);
        for (int j = 0; j < repetiçoes; j++) {
            final int ij = j;
            System.out.println("enter" + j);
            DefaultTableCellRenderer alterar = new DefaultTableCellRenderer() {
                public void setValue(Object value) {
                    setHorizontalAlignment(JLabel.CENTER);
                    if (value.equals("\n") || value.equals("Total: ")) {
                        setBackground(new Color(238, 238, 238));
                    } else {
                        setBackground(Color.white);
                    }
                    // System.out.println("value " + value);
                    super.setValue(value);
                }
            };
            jTable.getColumn("" + (j + 1)).setCellRenderer(alterar);

        }
        jTable.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                soma();
            }
        });
        for (int i = 1; i <= repetiçoes; i++) {
            TableColumn col = jTable.getColumnModel().getColumn(i);
            col.setCellEditor(new MyTableCellEditor());
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Criar Projeto");
        setResizable(false);

        jTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descrição", "Valor", "Vencimento", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable.setAutoscrolls(false);
        jTable.setCellSelectionEnabled(true);
        jTable.setFillsViewportHeight(true);
        jTable.setSelectionBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        jTable.getTableHeader().setResizingAllowed(false);
        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable);
        if (jTable.getColumnModel().getColumnCount() > 0) {
            jTable.getColumnModel().getColumn(0).setResizable(false);
            jTable.getColumnModel().getColumn(2).setResizable(false);
            jTable.getColumnModel().getColumn(3).setResizable(false);
            jTable.getColumnModel().getColumn(4).setResizable(false);
        }

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Ok");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Voltar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 310, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Repetições");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel2);
        jLabel2.setBounds(330, 0, 550, 30);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel3);
        jLabel3.setBounds(890, 0, 130, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(450, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(44, 44, 44)
                .addComponent(jButton3)
                .addGap(37, 37, 37)
                .addComponent(jButton1)
                .addGap(327, 327, 327))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        //
    }//GEN-LAST:event_jTableMouseClicked

    private void jTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMousePressed
        soma();
    }//GEN-LAST:event_jTableMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jTable.isEditing()) {
            jTable.getCellEditor().stopCellEditing();
        }

        JFileChooser salvandoArquivo = new JFileChooser();

        int resultado = salvandoArquivo.showSaveDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File projeto = new File(salvandoArquivo.getSelectedFile().getPath() + ".csv");
            criarArquivo(projeto, projeto.getName());
        }
        /*  soma();
         boolean preenchidos = true;
         for (int i = 1; i < tratamentos; i++) {
         for (int j = 1; j <= repetiçoes + 1; j++) {
         if (jTable.getValueAt(i, j).equals("")) {
         preenchidos = false;
         }
         }
         }
         if (preenchidos) {
         boolean criou = false;
         while (!criou) {
         String nomeprojeto;
         nomeprojeto = JOptionPane.showInputDialog(null, "Digite nome do projeto: ");
         if (nomeprojeto != null) {
         if (!nomeprojeto.equals("")) {
         File diretorio = new File("C:/Projeto/");
         File projeto = new File("C:/Projeto/" + nomeprojeto + ".csv");
         if (!diretorio.exists()) {
         diretorio.mkdirs();
         criou = criarArquivo(projeto, nomeprojeto);
         } else {
         criou = criarArquivo(projeto, nomeprojeto);

         }
         }
         } else {
         criou = true;
         }
         }
         } else {
         JOptionPane.showMessageDialog(null, "Preencha todas as células.");
         }*/
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        totalgeral = Double.parseDouble("" + jTable.getValueAt(tratamentos, repetiçoes + 1));
        numero = tratamentos * repetiçoes;
        c = (totalgeral * totalgeral) / numero;
        double somar = 0;
        for (int i = 0; i < tratamentos; i++) {
            for (int j = 1; j <= repetiçoes; j++) {
                double somartemp = Double.parseDouble("" + jTable.getValueAt(i, j));
                System.out.println("temp " + somartemp);
                somar += (somartemp * somartemp);
            }
        }
        System.out.println("somar" + somar);
        somatoriox2 = somar;
        somar = 0;
        for (int i = 0; i < tratamentos; i++) {
            double somartemp = Double.parseDouble("" + jTable.getValueAt(i, jTable.getColumnCount() - 1));
            somar += somartemp * somartemp;
        }

        somatoriotrat = somar;
        System.out.println("numero: " + numero + "\n total geral: " + totalgeral + "\nC: " + c + "\nx2:" + somatoriox2 + "\nsomatorio trat:" + somatoriotrat);
        TabelaFinal t = new TabelaFinal(tratamentos, repetiçoes, totalgeral, c, somatoriotrat, somatoriox2, numero);
        t.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?");
        if (sair == 0) {
            ProjetoEstatistica p = new ProjetoEstatistica();
            p.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    public boolean criarArquivo(File projeto, String nomeprojeto) {
        if (projeto.exists()) {
            int i = JOptionPane.showOptionDialog(null, "Projeto já existe, deseja sobrescrever?", "Ja existe", 1, 1, null, null, null);
            if (i == 0) {
                salvar(projeto.getAbsolutePath());
                return true;
            }
            return false;
        } else {
            try {
                new File(projeto.getAbsolutePath()).createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(CriarProjeto.class.getName()).log(Level.SEVERE, null, ex);
            }
            salvar(projeto.getAbsolutePath());

            return true;
        }
    }

    public void soma() {
        double soma = 0;
        for (int i = 0; i < tratamentos; i++) {
            for (int j = 1; j <= repetiçoes; j++) {
                double valorCelula = 0;
                if (jTable.getValueAt(i, j).equals("")) {
                } else {
                    valorCelula = Double.parseDouble(jTable.getValueAt(i, j).toString());
                }
                soma += valorCelula;
            }
            String somar = format(soma);
            String partes[] = somar.split(",");
            jTable.setValueAt(partes[0] + "." + partes[1], i, repetiçoes + 1);
            soma = 0;
        }
        for (int i = 0; i < tratamentos; i++) {
            double valorCelula = 0;
            if (jTable.getValueAt(i, repetiçoes + 1).equals("")) {
            } else {
                valorCelula = Double.parseDouble(jTable.getValueAt(i, repetiçoes + 1).toString());
            }
            soma += valorCelula;
        }
        String somar = format(soma);
        String partes[] = somar.split(",");
        jTable.setValueAt(partes[0] + "." + partes[1], tratamentos, repetiçoes + 1);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CriarProjeto(3, 3).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables
    //Formata resultado com duas casas decimais

    public String format(double x) {
        return String.format("%.2f", x);
    }

    private void salvar(String caminho) {
        FileWriter fw;
        try {
            fw = new FileWriter(caminho);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < tratamentos; i++) {
                for (int j = 1; j <= repetiçoes; j++) {
                    System.out.println("" + jTable.getValueAt(i, j));
                    if (jTable.getValueAt(i, j).equals("")) {
                        bw.write(" " + ";");
                    } else {
                        bw.write("" + jTable.getValueAt(i, j) + ";");
                    }
                }
                System.out.println("");
                if (i != tratamentos - 1) {
                    bw.write("\n");
                }
            }
            bw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(CriarProjeto.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
    }
}

class MyTableCellEditor extends AbstractCellEditor
        implements TableCellEditor {

    JTextField component = new JTextField();

    public boolean stopCellEditing() {
        String s = (String) getCellEditorValue();
        boolean valido = true;
        int contarpontos = 0;
        for (int i = 0; i < s.length(); i++) {
            String squebrado = s.substring(i, i + 1);
            if (squebrado.equals("0") || squebrado.equals("1") || squebrado.equals("2") || squebrado.equals("3") || squebrado.equals("4") || squebrado.equals("5") || squebrado.equals("6") || squebrado.equals("7") || squebrado.equals("8") || squebrado.equals("9") || squebrado.equals(".")) {
                if (squebrado.equals(".")) {
                    contarpontos++;
                }
            } else {
                valido = false;
            }
            if (i == s.length() - 1 && squebrado.equals(".")) {
                valido = false;
            }
        }
        if (contarpontos > 1) {
            valido = false;
        }
        if (!valido) {
            JOptionPane.showMessageDialog(null,
                    "Valor inválido");
            return false;
        }
        return super.stopCellEditing();
    }

    public Component getTableCellEditorComponent(
            JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
        if (isSelected) {
//
        }
        (component).setText((String) value);
        return component;
    }

    public Object getCellEditorValue() {
        return (component).getText();
    }
}
