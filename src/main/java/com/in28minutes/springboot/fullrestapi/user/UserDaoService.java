package com.in28minutes.springboot.fullrestapi.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 0;
    static {
        users.add(new User(++usersCount,"Adam", LocalDate.now().plusYears(20)));
        users.add(new User(++usersCount,"Hassan", LocalDate.now().plusYears(20)));
        users.add(new User(++usersCount,"Yasser", LocalDate.now().plusYears(20)));
        users.add(new User(++usersCount,"Khaled", LocalDate.now().plusYears(20)));
    }
    public List<User> findAll(){
        return users;
    }

    public User getUser(int userId) {
        Predicate<? super User> predicate = user -> user.getId()==userId;
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public User save(User user) {
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    public void deleteUser(int userId) {
        Predicate<? super User> predicate = user -> user.getId()==userId;
        users.removeIf(predicate);
    }
}
