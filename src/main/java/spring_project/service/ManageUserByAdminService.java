package spring_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_project.dto.UserDTO;
import spring_project.entity.User;
import spring_project.mapper.UserMapper;
import spring_project.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManageUserByAdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    // Lấy tất cả user
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Lấy user bằng id của user
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return userMapper.toDTO(user);
    }

    // Cập nhật User
    public boolean updateUserById(Long id, User user) {
        User userUpdate = userRepository.findById(id).orElse(null);
        if (userUpdate != null) {
            if (user.getName() != null) {
                userUpdate.setName(user.getName());
            }
            if (user.getEmail() != null) {
                userUpdate.setEmail(user.getEmail());
            }
            if (user.getNumberphone() != null) {
                userUpdate.setNumberphone(user.getNumberphone());
            }

            userRepository.save(userUpdate);
            return true;
        }
        return false;
    }

    //Xoá User khỏi databases;
    public boolean deleteUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user != null) {
            userRepository.delete(user);
        }
        return true;
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
}
