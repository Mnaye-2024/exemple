package dette.boutique.data.repository.bdImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dette.boutique.core.repository.impl.RepositoryDbImpl;
import dette.boutique.data.entities.Article;
import dette.boutique.data.repository.ArticleRepository;

public class ArticleRepositoryDbImpl extends RepositoryDbImpl<Article> implements ArticleRepository {

    @Override
    public void insert(Article element) {
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public List<Article> selectAll() {
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }

    @Override
    public Article findByLibelle(String libelle) {
        throw new UnsupportedOperationException("Unimplemented method 'findByLibelle'");
    }

    @Override
    public void setFields(PreparedStatement pstmt, Article element) throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'setFields'");
    }

    @Override
    public String generateSql(Article element) {
        throw new UnsupportedOperationException("Unimplemented method 'generateSql'");
    }

    @Override
    public Article convertToObject(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'convertToObject'");
    }

    @Override
    public Article selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public void remove(Article element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

}
