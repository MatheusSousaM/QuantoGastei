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
import java.sql.Time;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sousa
 */
public class EditarAgenda extends javax.swing.JFrame {
        private int idUsuarioLogado;
    // Mapa para armazenar as agendas que foram modificadas pelo usuário na tabela
     private Map<Integer, Agenda> agendasModificadas; 
    
    /**
     * Creates new form Editar
     */
    public EditarAgenda(int userId) {
        initComponents();
        this.idUsuarioLogado = userId;
        this.agendasModificadas = new HashMap<>(); // Inicializa o mapa
        configurarTabela();
        carregarTodasAgendasDoUsuario(); 
        this.setLocationRelativeTo(null);
    }
     private void configurarTabela() {
        // Criamos um DefaultTableModel personalizado para tornar as células editáveis
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // A coluna "ID Agenda" (coluna 0) NÃO é editável.
                // As outras colunas (Gasto, Data, Hora, Tipo Gasto) SÃO editáveis.
                return column != 0; 
            }
        };
        model.addColumn("ID Agenda");
        model.addColumn("Gasto (R$)");
        model.addColumn("Data (dd/MM/yyyy)"); // Adiciona o formato para instruir o usuário
        model.addColumn("Hora (HH:mm)");     // Adiciona o formato para instruir o usuário
        model.addColumn("Tipo Gasto");
        
        tabelaEdicaoAgendas.setModel(model); // Define o modelo para a sua tabela
        
        // Adiciona um listener para capturar as mudanças que o usuário faz na tabela
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    
                    // Se a mudança não é na coluna 0 (ID), processa a alteração
                    if (column != 0) {
                        DefaultTableModel sourceModel = (DefaultTableModel)e.getSource();
                        
                        // Pega o ID da agenda na linha modificada (coluna 0)
                        int idAgenda = (int) sourceModel.getValueAt(row, 0);
                        
                        // Pega os valores ATUAIS da linha na tabela (já com a modificação do usuário)
                        double gastos = 0.0;
                        Date data = null;
                        Time hora = null;
                        String tipoGasto = "";
                        
                        try {
                            String gastoStr = sourceModel.getValueAt(row, 1).toString().replace("R$", "").trim().replace(",", ".");
                            gastos = Double.parseDouble(gastoStr);

                            data = new SimpleDateFormat("dd/MM/yyyy").parse(sourceModel.getValueAt(row, 2).toString());
                            
                            String horaStr = sourceModel.getValueAt(row, 3).toString();
                            if (horaStr.matches("^\\d{2}:\\d{2}$")) { // Se for HH:mm, adiciona segundos
                                horaStr += ":00";
                            }
                            hora = Time.valueOf(horaStr);
                            
                            tipoGasto = sourceModel.getValueAt(row, 4).toString();
                            
                            // Cria/Atualiza um objeto Agenda com os dados do usuário e armazena no mapa
                            Agenda agendaAtualizada = new Agenda(idAgenda, idUsuarioLogado, gastos, data, hora, tipoGasto);
                            agendasModificadas.put(idAgenda, agendaAtualizada);
                            
                            System.out.println("Agenda ID " + idAgenda + " marcada para atualização.");

                        } catch (NumberFormatException | ParseException ex) {
                            JOptionPane.showMessageDialog(null, 
                                "Erro de formato na célula editada! Por favor, verifique:\n" +
                                "- Gasto: apenas números (ex: 123.45)\n" +
                                "- Data: dd/MM/yyyy (ex: 25/05/2025)\n" +
                                "- Hora: HH:mm (ex: 14:30)", 
                                "Erro de Edição", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                            // Se houver um erro de formato, o valor na tabela já foi alterado.
                            // Você pode querer reverter a célula para o valor original. Isso seria mais complexo.
                            // Por enquanto, apenas avisamos o usuário.
                        }
                    }
                }
            }
        });
    }

    private void preencherTabela(ArrayList<Agenda> agendas) {
        DefaultTableModel model = (DefaultTableModel) tabelaEdicaoAgendas.getModel();
        model.setRowCount(0); // Limpa as linhas existentes na tabela

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

        for (Agenda agenda : agendas) {
            model.addRow(new Object[]{
                agenda.getId(),
                String.format("%.2f", agenda.getGastos()), // Formata o gasto
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
            this.agendasModificadas.clear(); 
        } catch (SQLException ex) {
         
            JOptionPane.showMessageDialog(this, "Erro de banco de dados ao carregar agendas: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
          
            JOptionPane.showMessageDialog(this, "Erro interno: Driver do banco de dados não encontrado ao carregar agendas: " + ex.getMessage(), "Erro de Driver", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaEdicaoAgendas = new javax.swing.JTable();
        txtData = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbTipoGasto = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnPesquisar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabelaEdicaoAgendas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tabelaEdicaoAgendas);

        txtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Data");

        cmbTipoGasto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Fixos", "Entreterimento ", "Alimentação", "Saúde", " ", " " }));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tipo");

        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        btnAtualizar.setText("Atualizar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        jButton1.setText("voltar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbTipoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(214, 214, 214)
                        .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbTipoGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
       String dataStr = txtData.getText().trim();
        String tipoGasto = (String) cmbTipoGasto.getSelectedItem();

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

            preencherTabela(agendas); 

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
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
         if (agendasModificadas.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nenhuma alteração foi feita na tabela para salvar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    DAOagenda daoAgenda = new DAOagenda();
    int atualizacoesComSucesso = 0;
    
    for (Agenda agenda : agendasModificadas.values()) {
        daoAgenda.Editar(agenda); 
        atualizacoesComSucesso++;
             
    }
    
    if (atualizacoesComSucesso > 0) {
        JOptionPane.showMessageDialog(this, atualizacoesComSucesso + " agenda(s) atualizada(s) com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        carregarTodasAgendasDoUsuario(); 
    } else {
        JOptionPane.showMessageDialog(this, "Nenhuma agenda foi atualizada devido a erros ou falta de modificações válidas.", "Aviso", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CadastrarAgenda telaCadastrar= new CadastrarAgenda(this.idUsuarioLogado);
        telaCadastrar.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(EditarAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarAgenda(1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JComboBox<String> cmbTipoGasto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaEdicaoAgendas;
    private javax.swing.JTextField txtData;
    // End of variables declaration//GEN-END:variables
}
