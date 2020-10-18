package edu.mum.cs.onlinemarketplace.formatter;

import edu.mum.cs.onlinemarketplace.domain.Role;
import edu.mum.cs.onlinemarketplace.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class RoleFormatter implements Formatter<Role> {

    @Autowired
    RoleService roleService;

    @Override
    public Role parse(String s, Locale locale) throws ParseException {
        return roleService.getRoleById(Long.parseLong(s));
    }

    @Override
    public String print(Role role, Locale locale) {
        return String.valueOf(role.getId());
    }
}
