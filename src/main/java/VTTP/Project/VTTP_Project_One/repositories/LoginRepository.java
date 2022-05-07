package VTTP.Project.VTTP_Project_One.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import VTTP.Project.VTTP_Project_One.models.User;

@Repository
public class LoginRepository {

    private static final String SQL_VERIFY_USER = "select * from user where username = ? and password = sha1(?);";

    @Autowired
    private JdbcTemplate template;

    public Boolean verifyUser(User user){
        final SqlRowSet result = template.queryForRowSet(
            SQL_VERIFY_USER, user.getUsername(),user.getPassword());

        if(result.next()){
            // if(!result.getString("username").isEmpty()){
                return true;
            // }
        }
        return false;   
    }
    

}
