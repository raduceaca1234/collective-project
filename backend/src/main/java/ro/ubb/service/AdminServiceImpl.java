package ro.ubb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.ubb.model.Admin;
import ro.ubb.repository.AdminRepository;

import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }
}
