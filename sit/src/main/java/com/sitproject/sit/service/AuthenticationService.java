package com.sitproject.sit.service;

import com.sitproject.sit.dto.ResponseDTO;
import com.sitproject.sit.dto.UserDTO;
import com.sitproject.sit.entity.User;
import com.sitproject.sit.repository.UserRepo;
import com.sitproject.sit.util.varList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
}
