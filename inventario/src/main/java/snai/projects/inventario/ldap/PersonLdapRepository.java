package snai.projects.inventario.ldap;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonLdapRepository extends CrudRepository<PersonLdap, String> {
    Optional<PersonLdap> findByUsername(String username);
}
