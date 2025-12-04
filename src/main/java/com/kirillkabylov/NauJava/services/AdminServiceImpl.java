package com.kirillkabylov.NauJava.services;

import com.kirillkabylov.NauJava.database.AdminRepository;
import com.kirillkabylov.NauJava.domain.Admin;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createAdmin(String login, String fullName, String password) {
        if (adminRepository.findByLogin(login).isPresent()){
            throw new RuntimeException("Админ с таким логином уже существует");
        }
        adminRepository.save(new Admin(login, fullName, passwordEncoder.encode(password)));
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
            throw new EntityNotFoundException("Admin with id - " + id + " not found");
        }
    }
}