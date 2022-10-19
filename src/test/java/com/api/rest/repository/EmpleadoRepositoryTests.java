package com.api.rest.repository;

import com.api.rest.model.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // solo prueba entidades y respositorios
public class EmpleadoRepositoryTests {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    private Empleado empleado;

    // se va ejecutar antes de los metodos
    @BeforeEach
    void setup() {
        System.out.println("-- Inicio --");
        empleado = Empleado.builder()
                .nombre("Christian")
                .apellido("Ramirez")
                .email("cr@gmail.com")
                .build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado() {
        // BDD
        // given - dado - dado o condicion previa o configuracion
        Empleado empleado1 = Empleado.builder()
                .nombre("Pepe")
                .apellido("Perez")
                .email("pp@gmail.com")
                .build();
        // when - cuando - accion o el compartamiento que vamos a probar
        Empleado empleadoGuardado = empleadoRepository.save(empleado1);
        // then - entonces -verificar la salida
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);

    }

    @DisplayName("Test para listar a los empleados")
    @Test
    void testListarEmpleados() {
        // given
        Empleado empleado1 = Empleado.builder()
                .nombre("Julen")
                .apellido("Oliva")
                .email("jo@gmail.com")
                .build();
        empleadoRepository.save(empleado1);
        empleadoRepository.save(empleado);
        //when
        List<Empleado> listaEmpleados = empleadoRepository.findAll();
        // then
        assertThat(listaEmpleados).isNotNull();
        assertThat(listaEmpleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener un empleado por ID")
    @Test
    void testObtenerEmpleadoPorId() {
        empleadoRepository.save(empleado);

        // when - comportamiento o accion que vamos a probar
        Empleado empleadoBD = empleadoRepository.findById(empleado.getId()).get();
        // then
        assertThat(empleadoBD).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado() {
        empleadoRepository.save(empleado);

        // when
        Empleado empleadoGuardado = empleadoRepository.findById(empleado.getId()).get();
        empleadoGuardado.setEmail("c232@gmail.com");
        empleadoGuardado.setNombre("Chrstian Raul");
        empleadoGuardado.setApellido("Remirez Loco");
        Empleado empleadoActualizado = empleadoRepository.save(empleadoGuardado);
        // then
        assertThat(empleadoActualizado.getEmail()).isEqualTo("c232@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Chrstian Raul");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado() {
        empleadoRepository.save(empleado);

        // when
        empleadoRepository.deleteById(empleado.getId());
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleado.getId());
        // then
        assertThat(empleadoOptional).isEmpty();
    }

}
