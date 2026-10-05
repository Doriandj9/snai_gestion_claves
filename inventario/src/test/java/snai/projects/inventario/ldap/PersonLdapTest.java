package snai.projects.inventario.ldap;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import static org.assertj.core.api.BDDAssertions.then;
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class PersonLdapTest {

    @Autowired
    private PersonLdapRepository personLdapRepository;

    @Test
    void readAll(){
        List<PersonLdap> personLdap = StreamSupport
                .stream(personLdapRepository.findAll().spliterator(), false)
                .toList();
        then(personLdap.isEmpty()).isFalse();
    }

    @Test
    void checkUsername(){
        Optional<PersonLdap> person = personLdapRepository.findByUsername("dorian.armijos");

        then(person.isPresent()).isTrue();
    }
}
