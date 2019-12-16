package com.library.service.users;

import com.library.beans.musers.user.RoleBean;
import com.library.beans.musers.user.UsersBean;
import com.library.proxies.IMicroserviceUsersProxy;
import com.library.technical.authentificationfacade.IAuthenticationFacade;
import com.library.technical.rolechecker.IRoleChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUsersService {

    @Autowired
    private IMicroserviceUsersProxy mUsersProxy;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private IRoleChecker roleChecker;

    private UsersBean currentUser;

    private String currentUserFullName;

    private String currentUserFirstName;

    private String currentUserLastName;

    private String currentUserEmail;

    private String currentUserPhone;

    private Long currentUserId;

    public UsersBean getCurrentUser() {
        return currentUser;
    }

    public String getCurrentUserFirstName() {
        return currentUserFirstName;
    }

    public String getCurrentUserLastName() {
        return currentUserLastName;
    }

    public String getCurrentUserFullName() {
        return currentUserFullName;
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public String getCurrentUserPhone() {
        return currentUserPhone;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }



    /**
     * On cherche un utilisateur
     *
     * @param email Email de l'utilisateur à chercher
     * @return L'utilisateur si l'utilisateur est trouvé.
     * @throws UsernameNotFoundException Exception de la présence de l'utilisateur recherché
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        currentUser = mUsersProxy.user(email);

        if (currentUser == null) {
            throw new UsernameNotFoundException("Utilisateur ou mot de passe incorrect.");
        }
        currentUserEmail = currentUser.getEmail();
        currentUserFirstName = currentUser.getFirstName();
        currentUserLastName = currentUser.getLastName();
        currentUserFullName = currentUser.getFirstName() + ' ' + currentUser.getLastName();
        currentUserId = currentUser.getId();
        currentUserPhone = currentUser.getPhone();

        return new org.springframework.security.core.userdetails.User(currentUser.getEmail(),
                currentUser.getPassword(),
                mapRolesToAuthorities(currentUser.getRoleList()));
    }

    /**
     * @param roles role de l'utilisateur
     * @return
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<RoleBean> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }


    /**
     * On recherche quel utilisateur est connecté
     * @return L'indentifiant d'authentification de l'utilisateur
     */
    public String currentUserNameSimple() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }

    /**
     * On vérifié si l'utilisateur connecté est administrateur
     * @return true si l'utilisateur est administrateur sinon false
     */
    public boolean isAdmin(){
        return roleChecker.hasRole("ADMIN");
    }

    /**
     * On vérifie si l'utilisateur connecté est membre de l'association
     * @return true si l'utilisateur est membre de l'association sinon false
     */
    public boolean isActuator(){
        return roleChecker.hasRole("ACTUATOR");
    }

    /**
     * On vérifié si l'utilisateur connecté est simple utilisateur
     * @return true l'utilisateur est simple utilisateur sinon false
     */
    public boolean isUser(){
        return roleChecker.hasRole("USER");
    }

    public String getUserReservation(Long id){
        return mUsersProxy.getUserNameReservation(id );
    }
}