package com.kirillkabylov.NauJava.database;

import java.util.List;

import com.kirillkabylov.NauJava.exception.DublicateUserNameException;
import com.kirillkabylov.NauJava.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepository implements CrudRepository<User, Long>{
    private final List<User> userContainer;

    @Autowired
    public UserRepository(List<User> userContainer){
        this.userContainer = userContainer;
    }

    @Override
    public void create(User entity) throws DublicateUserNameException{
        for (User user : userContainer){
            if(user.getFullName().equals(entity.getFullName())){
                throw new DublicateUserNameException("???????????????????????? ?? ?????????? ???????????? ?? ???????????????? ????????????????????");
            }
        }
        userContainer.add(entity);
    }

    @Override
    public User read(Long id) {
        return userContainer.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(User entity) {
        for (int i = 0; i < userContainer.size(); i++){
            if (userContainer.get(i).getId() == entity.getId()){
                userContainer.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        userContainer.removeIf(x -> x.getId() == id);
    }
}
