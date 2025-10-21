package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.Exceptions.UserNotFoundException;
import com.kirillkabylov.NauJava.database.AdminRepository;
import com.kirillkabylov.NauJava.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Реализация сервиса для управления учетными записями администраторов.
 * Сервис предоставляет операции для создания и удаления администраторов,
 *  взаимодействуя с {@link AdminRepository} для выполнения операций с базой данных.
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void createAdmin(long id, String login, String fullName, String password) {
        adminRepository.save(new Admin(login, fullName, password));
    }

    @Override
    public void deleteAdmin(Admin admin) {
        adminRepository.delete(admin);
    }

    @Override
    public void deleteAdmin(long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            adminRepository.delete(admin.get());
        } else {
            throw new UserNotFoundException(id);
        }
    }
}