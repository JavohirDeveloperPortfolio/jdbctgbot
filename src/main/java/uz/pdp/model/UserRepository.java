package uz.pdp.model;

import uz.pdp.config.CustomDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private final String insertUser =
            "INSERT INTO botusers (chat_id,bot_state) VALUES (?,?)";
    private final String selectUserByChatId =
            "SELECT * FROM botusers WHERE chat_id = ?";
    private final String updateLang =
            "UPDATE botusers SET lang = ? WHERE chat_id = ?";

    public void updateLang(String chatId, String lang){
        try{
            Connection conn = CustomDataSource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(updateLang);

            ps.setString(1,lang);
            ps.setString(2,chatId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUser(String chatId, BotState botState) {
        try {
            Connection conn = CustomDataSource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(insertUser);

            ps.setString(1, chatId);
            ps.setString(2, botState.name());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public User getUserByChatId(String chatId) {
        User user = new User();
        try {
            Connection conn = CustomDataSource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(selectUserByChatId);

            ps.setString(1, chatId);

            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            user.setBotState(
                    BotState.valueOf(resultSet.getString("bot_state")));
            user.setChatId(resultSet.getString("chat_id"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
