/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocksucc;

import java.util.Date;

/**
 *
 * @author anon
 */
public class Person {    
    
    private String prename;
    private String lastname;
    private Date birthDate;
    private String email;
    private String username;
    private String password;
    
    public Person(String prename, String lastname, Date birthDate, 
            String username, String email, String password){
        this.prename = prename;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    public Person(){
    }

    /**
     * @return the prename
     */
    public String getPrename() {
        return prename;
    }

    /**
     * @param prename the prename to set
     */
    public void setPrename(String prename) {
        this.prename = prename;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
