package backend.security;

import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UtilisateurSecurite implements UserDetails {

    private final String email;
    private final String motDePasse;
    private final Collection<? extends GrantedAuthority> authorities;

    public UtilisateurSecurite(String email,
                               String motDePasse,
                               Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.motDePasse = motDePasse;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
