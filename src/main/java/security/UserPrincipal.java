package security;

import entities.User;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserPrincipal implements Principal {

  private String username;
  private String role;

  /* Create a UserPrincipal, given the Entity class User*/
  public UserPrincipal(User user) {
    this.username = user.getUserName();
    this.role = user.getRole().getRoleName();
  }

  public UserPrincipal(String username, String role) {
    super();
    this.username = username;
    this.role = role;
  }
  public boolean isUserInRole(String role) {
    return this.role.equals(role);
  }

  @Override
  public String getName() {
    return username;
  }

}