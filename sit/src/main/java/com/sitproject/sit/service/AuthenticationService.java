package com.sitproject.sit.service;

import com.sitproject.sit.dto.ResponseDTO;
import com.sitproject.sit.dto.UserDTO;
import com.sitproject.sit.entity.User;
import com.sitproject.sit.repository.UserRepo;
import com.sitproject.sit.util.JWTUtils;
import com.sitproject.sit.util.varList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JWTUtils jwtUtils;

    public ResponseDTO saveUser(UserDTO userDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if(userRepo.existsById(userDTO.getUsername())){
                responseDTO.setCode(varList.RSP_DUPLICATED);
                responseDTO.setMessage("Duplicated");
                responseDTO.setContent(null);
                responseDTO.setStatus(HttpStatus.ALREADY_REPORTED);
            }
            else{
                User user= modelMapper.map(userDTO, User.class);
                String EncodedPassword = passwordEncoder.encode(userDTO.getPassword());
                user.setPassword(EncodedPassword);
                userRepo.save(user);
                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
                responseDTO.setUsername(userDTO.getUsername());
                userDTO.setPassword("");
                responseDTO.setContent(userDTO);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
            }
        }catch (Exception e){
            responseDTO.setCode(varList.RSP_FAIL);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setMessage(e.getMessage());
            return responseDTO;
        }

        return responseDTO;
    }
    public ResponseDTO validUser(UserDTO usersDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            // Fetch the user by username
            if(userRepo.existsById(usersDTO.getUsername())){
                Optional<User> userOptional = userRepo.findById(usersDTO.getUsername());
                User user = userOptional.get();
                String storedPassword = user.getPassword();
                if ((userRepo.existsById(usersDTO.getUsername()))&&(passwordEncoder.matches(usersDTO.getPassword(), storedPassword))) {
                    var jwt = jwtUtils.generateToken(user);
                    // Valid user
                    responseDTO.setToken(jwt);
                    responseDTO.setCode(varList.RSP_SUCCES);
                    responseDTO.setMessage("Valid User");
                    responseDTO.setRole(user.getRole());
                    responseDTO.setContent(true);
                    responseDTO.setUsername(user.getUsername());
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                } else {
                    responseDTO.setCode(varList.RSP_FAIL);
                    responseDTO.setMessage("Invalid User");
                    responseDTO.setContent(false);
                    responseDTO.setStatus(HttpStatus.ACCEPTED);
                }
            }else{
                responseDTO.setCode(varList.RSP_FAIL);
                responseDTO.setMessage("Invalid User");
                responseDTO.setContent(false);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
            }

        } catch (Exception e) {
            // Handle exceptions
            responseDTO.setCode(varList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            responseDTO.setStatus(HttpStatus.BAD_REQUEST);
        }
        return responseDTO;
    }
}
