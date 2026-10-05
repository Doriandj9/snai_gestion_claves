package snai.projects.inventario.ldap;

import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(objectClasses = {"person","top","user"}, base = "OU=USER_MGP")
@Data
public class PersonLdap {
    @Id
    private Name dn;  // Distinguished Name (identificador LDAP completo)

    @Attribute(name = "samaccountname")
    private String username;

    @Attribute(name = "mail")
    private String correo;

    @Attribute(name = "givenName")
    private String nombre;

    @Attribute(name = "sn")
    private String apellido;
}
