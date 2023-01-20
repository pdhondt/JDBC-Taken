package be.vdab.repositories;

import be.vdab.domain.Brouwer;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BrouwerRepository extends AbstractRepository {
    public BigDecimal getGemiddeldeOmzet() throws SQLException {
        var sql = """
                select avg(omzet) as gemiddelde
                from brouwers
                """;
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql)) {
            var result = statement.executeQuery();
            result.next();
            return result.getBigDecimal("gemiddelde");
        }
    }
    public List<Brouwer> getBrouwersMetBovenGemiddeldeOmzet() throws SQLException {
        var sql = """
                select id, naam, adres, postcode, gemeente, omzet
                from brouwers
                where omzet > (select avg(omzet) from brouwers)
                """;
        var brouwers = new ArrayList<Brouwer>();
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql)) {
            var result = statement.executeQuery();
            while (result.next()) {
                brouwers.add(naarBrouwer(result));
            }
            return brouwers;
        }
    }
    private Brouwer naarBrouwer(ResultSet result) throws SQLException {
        return new Brouwer(result.getLong("id"), result.getString("naam"),
                result.getString("adres"), result.getInt("postcode"),
                result.getString("gemeente"), result.getInt("omzet"));
    }
    public List<Brouwer> getBrouwersMetOmzetTussenMinEnMax(int minimum, int maximum) throws SQLException {
        var brouwers = new ArrayList<Brouwer>();
        var sql = """
                select id, naam, adres, postcode, gemeente, omzet
                from brouwers
                where omzet between ? and ?
                order by omzet, id
                """;
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, minimum);
            statement.setInt(2, maximum);
            for (var result = statement.executeQuery(); result.next(); ) {
                brouwers.add(naarBrouwer(result));
            }
            return brouwers;
        }
    }
    public Optional<Brouwer> findById(long id) throws SQLException {
        var sql = """
                select id, naam, adres, postcode, gemeente, omzet
                from brouwers
                where id = ?
                """;
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            return result.next() ? Optional.of(naarBrouwer(result)) : Optional.empty();
        }
    }
    public List<Brouwer> getBrouwersMetOmzetTussenViaStoredProcedure(int minimum, int maximum) throws SQLException {
        var brouwers = new ArrayList<Brouwer>();
        try (var connection = super.getConnection();
             var statement = connection.prepareCall("{call BrouwersMetOmzetTussen(?, ?)}")) {
            statement.setInt(1, minimum);
            statement.setInt(2, maximum);
            for (var result = statement.executeQuery(); result.next(); ) {
                brouwers.add(naarBrouwer(result));
            }
            return brouwers;
        }
    }
    public int maakOmzetLeeg(Set<Long> brouwerIds) throws SQLException {
        if (brouwerIds.isEmpty()) {
            return 0;
        }
        var sql = """
                update brouwers
                set omzet = null
                where id in (
                """
                + "?,".repeat(brouwerIds.size() - 1)
                + "?)";
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            var index = 1;
            for (var id : brouwerIds) {
                statement.setLong(index++, id);
            }
            var aantalLeeggemaakt = statement.executeUpdate();
            connection.commit();
            return aantalLeeggemaakt;
        }
    }
    public Set<Long> controleerBrouwerIds(Set<Long> brouwerIds) throws SQLException {
        if (brouwerIds.isEmpty()) {
            return Set.of();
        }
        var gevondenIds = new HashSet<Long>();
        var sql = """
                select id
                from brouwers
                where id in (
                """
                + "?,".repeat(brouwerIds.size() - 1)
                + "?)";
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            var index = 1;
            for (var id : brouwerIds) {
                statement.setLong(index++, id);
            }
            var result = statement.executeQuery();
            while (result.next()) {
                gevondenIds.add(result.getLong("id"));
            }
            connection.commit();
            return gevondenIds;
        }
    }
}
