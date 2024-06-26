package Project.EM_CarRental.Service;

import Project.EM_CarRental.DTO.CreditCardDTO;
import Project.EM_CarRental.DTO.UserDTO;
import Project.EM_CarRental.Entities.CreditCard;
import Project.EM_CarRental.Entities.Role;
import Project.EM_CarRental.Entities.User;
import Project.EM_CarRental.Mapper.UserDTOMapper;
import Project.EM_CarRental.Repository.CreditCardRepository;
import Project.EM_CarRental.Repository.RoleRepository;
import Project.EM_CarRental.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import java.util.List;

import static Project.EM_CarRental.Mapper.CreditCardDTOMapper.mapToCreditCard;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CreditCardRepository creditCardRepository;



    public UserDTO saveUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UsernameNotFoundException("Username already exists!");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(UserDTOMapper.mapToUser(userDTO));
        return userDTO;
    }

    public User editUser(Long id, UserDTO userDTO) {
        User userEdited = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        userEdited.setUsername(userDTO.getUsername());
        userEdited.setName(userDTO.getName());
        userEdited.setEmail(userDTO.getEmail());
        userEdited.setPhone(userDTO.getPhone());
        userEdited.setLastname(userDTO.getLastName());
        userEdited.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(userEdited);
    }

    public void deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        userRepository.deleteById(id);
    }

    public Role saveRole(Role role) {
        if (roleRepository.findByRole(role.getRole()).isEmpty()) {
            throw new UsernameNotFoundException("Role not found!");
        }
        return roleRepository.save(role);

    }

    public User addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        Role role = roleRepository.findByRole(roleName).orElseThrow(() -> new UsernameNotFoundException("Role not found!"));
        if (user.getRoles().contains(role)) {
            throw new UsernameNotFoundException("Role already exists for the user!");

        }
        user.getRoles().add(role);
        //role.getUsers().add(user);
        return userRepository.save(user);


    }

    public void removeRoleFromUser(String username, String roleName) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        Role role = roleRepository.findByRole(roleName).orElseThrow(() -> new UsernameNotFoundException("Role not found!"));

        user.getRoles().remove(role);
       // role.getUsers().remove(user);
    }



    public User addCreditCardToUser(String username, CreditCardDTO creditCardDTO) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        if (user.getCreditCard() != null) {
            throw new UsernameNotFoundException("Credit Card already exists for the user!");
        }
        CreditCard creditCard = creditCardRepository.save(mapToCreditCard(creditCardDTO));
        user.setCreditCard(creditCard);
        creditCard.setUser(user);
        return userRepository.save(user);

    }

    public void deleteCreditCardFromUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

        if (user.getCreditCard() == null) {
            throw new UsernameNotFoundException("Credit Card not found!");
        }
        creditCardRepository.delete(user.getCreditCard());


    }

    public List<UserDTO> getAllUsers() {
        return UserDTOMapper.mapUserToUserDTO(userRepository.findAll());

    }
}
