/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controll.Agenda;
import Model.DAOagenda;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sousa
 */
public class PesquisarAgenda extends javax.swing.JFrame {
 private int idUsuarioLogado;
    /**
     * Creates new form Pesquisar
     */
    public PesquisarAgenda(int userId) {
        initComponents();
        this.idUsuarioLogado = userId;
        configurarTabela();
        carregarTodasAgendasDoUsuario();
        setLocationRelativeTo(null); // Centraliza a janela
        
    }
       private void configurarTabela() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Agenda");
        model.addColumn("Gasto (R$)");
        model.addColumn("Data");
        model.addColumn("Hora");
        model.addColumn("Tipo Gasto");
        tabelaAgendas.setModel(model);
       }
        private void preencherTabela(ArrayList<Agenda> agendas) {
        DefaultTableModel model = (DefaultTableModel) tabelaAgendas.getModel();
        model.setRowCount(0); // Limpa as linhas existentes na tabela

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

        for (Agenda agenda : agendas) {
            model.addRow(new Object[]{
                agenda.getId(),
                String.format("%.2f", agenda.getGastos()),
                sdfDate.format(agenda.getData()),
                sdfTime.format(agenda.getHora()),
                agenda.getTipo_gasto()
            });
        }
    }
          private void carregarTodasAgendasDoUsuario() {
        DAOagenda daoAgenda = new DAOagenda();
        try {
            ArrayList<Agenda> agendas = daoAgenda.pesquisarAgendasPorUsuario(this.idUsuarioLogado);
            preencherTabela(agendas);
            if (agendas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma agenda encontrada para o seu usuário. Comece a cadastrar!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar todas as agendas: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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

        txtDataPesquisa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbTipoGastoPesquisa = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaAgendas = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtDataPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataPesquisaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Data");

        cmbTipoGastoPesquisa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Fixos", "Entreterimento ", "Alimentação", "Saúde", " ", " " }));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tipo");

        jButton1.setText("Pesquisar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tabelaAgendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabelaAgendas);

        jButton2.setText("Voltar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTipoGastoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtDataPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbTipoGastoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDataPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataPesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataPesquisaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String dataStr = txtDataPesquisa.getText().trim();
        String tipoGasto = (String) cmbTipoGastoPesquisa.getSelectedItem();

        boolean isDataFilled = !dataStr.isEmpty();
        boolean isTipoFilled = (tipoGasto != null && !tipoGasto.isEmpty() && !tipoGasto.equals("Selecione")); // "Selecione" é o texto padrão do combobox

        DAOagenda daoAgenda = new DAOagenda();
        ArrayList<Agenda> agendas = new ArrayList<>();

        try {
            if (isDataFilled && isTipoFilled) {
             
                JOptionPane.showMessageDialog(this, "Por favor, preencha apenas a Data OU o Tipo de Gasto para a pesquisa.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            } else if (isDataFilled) {
               
                SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
                sdfDate.setLenient(false);
                Date dataPesquisa = sdfDate.parse(dataStr);
                agendas = daoAgenda.pesquisarPorData(this.idUsuarioLogado, dataPesquisa);
            } else if (isTipoFilled) {
               
                agendas = daoAgenda.pesquisarPorTipoGasto(this.idUsuarioLogado, tipoGasto);
            } else {
               
                agendas = daoAgenda.pesquisarAgendasPorUsuario(this.idUsuarioLogado);
                
            }

            preencherTabela(agendas); // Preenche a tabela com os resultados

            if (agendas.isEmpty() && (isDataFilled || isTipoFilled)) {
                JOptionPane.showMessageDialog(this, "Nenhum gasto encontrado com os critérios informados.", "Pesquisa Vazia", JOptionPane.INFORMATION_MESSAGE);
            } else if (agendas.isEmpty() && !(isDataFilled || isTipoFilled)) {
             
                 JOptionPane.showMessageDialog(this, "Nenhuma agenda encontrada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use DD/MM/AAAA.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar agendas: " + e.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CadastrarAgenda telaCadastrar= new CadastrarAgenda(this.idUsuarioLogado);
        telaCadastrar.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PesquisarAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PesquisarAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PesquisarAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PesquisarAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PesquisarAgenda(1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbTipoGastoPesquisa;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaAgendas;
    private javax.swing.JTextField txtDataPesquisa;
    // End of variables declaration//GEN-END:variables
}
