

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        
        conn = new conectaDAO().connectDB();
        
        String sql = "INSERT INTO produtos(nome, valor) VALUES (?, ?)";
        
        try{
            conn.setAutoCommit(false);
            
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.executeUpdate();
            
            conn.commit();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
            
        }catch(SQLException e){
            try{
                conn.rollback();
                JOptionPane.showConfirmDialog(null, "Erro ao cadastrar produto");
                
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            e.printStackTrace();
            
        }finally{
            try{
                conn.setAutoCommit(true);
                
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        conn = new conectaDAO().connectDB();
        
        String sql = "SELECT * FROM produtos";
        
        try{
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while(resultset.next()){
                ProdutosDTO p = new ProdutosDTO();
                p.setId(resultset.getInt("id"));
                p.setNome(resultset.getString("nome"));
                p.setValor(resultset.getInt("valor"));
                p.setStatus(resultset.getString("status"));
                
                listagem.add(p);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listagem;
    }
    
    public void venderProduto(int id){
        
        conn = new conectaDAO().connectDB();
        
        String sql = "UPDATE produtos SET status = ('Vendido') WHERE id = ?";
        
        try{
            conn.setAutoCommit(false);
            
            prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            prep.executeUpdate();
            
            conn.commit();
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso");
            
        }catch(SQLException e){
            try{
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Erro ao vender produto");
                
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            e.printStackTrace();
            
        }finally{
            try{
                conn.setAutoCommit(true);
                
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }  
    
        
}

