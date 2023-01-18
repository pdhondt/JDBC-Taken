package be.vdab.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BierRepository extends AbstractRepository {
    public int verwijderBierenZonderAlcoholPercentage() throws SQLException {
        var sql = """
                delete from bieren
                where alcohol is null
                """;
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(sql)) {
            return statement.executeUpdate();
        }
    }
    public void brouwer1Failliet() throws SQLException {
        var sqlBierenVanaf8Punt5 = """
                update bieren
                set brouwerId = 2
                where brouwerId = 1 and alcohol >= 8.50
                """;
        var sqlBierenTot8Punt5 = """
                update bieren
                set brouwerId = 3
                where brouwerId = 1 and alcohol < 8.50
                """;
        var sqlDeleteBrouwer1 = """
                delete from brouwers
                where id = 1
                """;
        try (var connection = super.getConnection();
            var statementVanaf8Punt5 = connection.prepareStatement(sqlBierenVanaf8Punt5);
            var statementTot8Punt5 = connection.prepareStatement(sqlBierenTot8Punt5);
            var statementDeleteBrouwer1 = connection.prepareStatement(sqlDeleteBrouwer1)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            statementVanaf8Punt5.executeUpdate();
            statementTot8Punt5.executeUpdate();
            statementDeleteBrouwer1.executeUpdate();
            connection.commit();
        }
    }
    public List<String> findBierenVerkochtSinds(int maand) throws SQLException {
        var bierNamen = new ArrayList<String>();
        var sql = """
                select naam
                from bieren
                where {fn month(sinds)} = ?
                order by naam
                """;
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            statement.setInt(1, maand);
            var result = statement.executeQuery();
            while (result.next()) {
                bierNamen.add(result.getString("naam"));
            }
            connection.commit();
            return bierNamen;
        }
    }
}
