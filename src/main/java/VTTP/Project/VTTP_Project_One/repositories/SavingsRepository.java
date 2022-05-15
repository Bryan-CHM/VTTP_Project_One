package VTTP.Project.VTTP_Project_One.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import VTTP.Project.VTTP_Project_One.models.User;

@Repository
public class SavingsRepository {
    
    public static final String SQL_GET_SAVINGS = "select savings from user where username = ?;";
    public static final String SQL_ADD_SAVINGS = "update user set savings = savings + ? where username = ?;";

    @Autowired
    private JdbcTemplate template;

    public Integer getSavingsFromUser(User user){
        final SqlRowSet result = template.queryForRowSet(
            SQL_GET_SAVINGS, user.getUsername());
        Integer savings;

        if(result.next()){
            savings = result.getInt("savings");
            return savings;
        }
        return -1;
    }

    public boolean addSavings(User user, Integer value){
        int count = template.update(SQL_ADD_SAVINGS, value, user.getUsername());
        return 1 == count;
    }

}
