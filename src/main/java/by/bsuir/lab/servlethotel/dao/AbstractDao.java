package by.bsuir.lab.servlethotel.dao;

import by.bsuir.lab.servlethotel.dao.infrastructure.ConnectionPool;
import by.bsuir.lab.servlethotel.entity.AbstractEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractDao<Entity extends AbstractEntity<Integer>> implements IDao<Entity, Integer> {

    public void save(Entity entity) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = prepareSaveStatement(connection, entity);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt(1));
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Entity getById(Integer id) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(prepareSelectTemplate() + " WHERE " + tableName() + ".id = ?");
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return processResultRow(resultSet);
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Entity> getByFields(Map<IEntityField<Entity>, Object> values) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection()) {
            List<Map.Entry<IEntityField<Entity>, Object>> orderedValues = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(
                    prepareSelectTemplate() + " WHERE " +
                            values.entrySet().stream()
                                    .map(entry -> {
                                        orderedValues.add(entry);
                                        return entry.getKey().getField() + " = ?";
                                    })
                                    .collect(Collectors.joining(", "))
            );
            for (int i = 0; i < orderedValues.size(); i++) {
                Map.Entry<IEntityField<Entity>, Object> entry =orderedValues.get(i);
                entry.getKey().setValue(statement, entry.getValue(), i + 1);
            }
            statement.execute();

            List<Entity> result = new ArrayList<>();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                result.add(processResultRow(resultSet));
            }
            return result;
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Entity> getAll() {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(prepareSelectTemplate());
            statement.execute();

            List<Entity> result = new ArrayList<>();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                result.add(processResultRow(resultSet));
            }
            return result;
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update(Entity entity) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = prepareUpdateStatement(connection, entity);
            statement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(Entity entity) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(prepareDeleteTemplate() + " WHERE " + tableName() + ".id = ? ");
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected abstract String tableName();

    protected abstract PreparedStatement prepareSaveStatement(Connection connection, Entity entity) throws SQLException;

    protected abstract PreparedStatement prepareUpdateStatement(Connection connection, Entity entity) throws SQLException;

    protected abstract String prepareDeleteTemplate();

    protected abstract String prepareSelectTemplate();

    protected abstract Entity processResultRow(ResultSet resultSet) throws SQLException;
}
