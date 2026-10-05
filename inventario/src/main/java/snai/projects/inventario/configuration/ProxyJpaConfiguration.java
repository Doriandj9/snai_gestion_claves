package snai.projects.inventario.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hibernate.Hibernate;

import java.io.IOException;

public class ProxyJpaConfiguration extends  JsonSerializer<Object>{

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        // 1. Verificar si el objeto/proxy está inicializado
        if (!Hibernate.isInitialized(value)) {
            // Si no está inicializado, lo omitimos para evitar el LazyInitializationException
            gen.writeNull();
        } else {
            // 2. Si está inicializado, delegamos al serializador estándar de Jackson
            // Esto evita la recursión infinita
            JsonSerializer<Object> defaultSerializer = serializers.findValueSerializer(value.getClass(), null);
            defaultSerializer.serialize(value, gen, serializers);
        }
    }

}
