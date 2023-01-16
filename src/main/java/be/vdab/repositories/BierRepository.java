package be.vdab.repositories;

import java.sql.SQLException;

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
}
