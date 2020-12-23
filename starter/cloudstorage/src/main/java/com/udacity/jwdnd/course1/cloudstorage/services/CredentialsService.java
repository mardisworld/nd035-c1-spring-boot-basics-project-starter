package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {
    private  UserService userService;
    private final HashService hashService;
    private CredentialsMapper credentialsMapper;
    public CredentialsService(UserService userService, CredentialsMapper credentialsMapper,HashService hashService) {
        this.userService = userService;
        this.credentialsMapper = credentialsMapper;
        this.hashService = hashService;
    }

    private String[] encodePassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(password, encodedSalt);
        return new String[]{hashedPassword, encodedSalt};
    }
    public long getCredIdByUserId(long userId){
        return credentialsMapper.getCredId(userId);
    }
    public String getusernameByUserId(long userId){
        return credentialsMapper.getUsernamebyuserId(userId);
    }
    public int deleteCreds(long credentialId,long userId){
        return credentialsMapper.delete(credentialId,userId);
    }
    public int insert(Credential creds , long userId){
        creds.setUserId(userId);
        creds.setUserName(creds.getUserName());
        String[] result = encodePassword(creds.getPassword());
        creds.setPassword(result[0]);
        creds.setKey(result[1]);
        return credentialsMapper.insert(creds);
    }
    public List<Credential> credsUpload(Long userId){
        return credentialsMapper.getCredsByUserId(userId);  }
}

