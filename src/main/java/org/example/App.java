package org.example;

import com.mongodb.client.*;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.example.entities.*;
import org.example.repositories.*;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class App
{
    public static void main( String[] args )
    {
        Direccion d1 = new Direccion("Monarca", 12, "Castellon", "Burriana");

        Profesor p1 = new Profesor("Pepe", "Marcos", "Lopez", "987987987", d1);
        p1.setId(new ObjectId());
        Profesor p2 = new Profesor("Lucas", "Gimeno", "Prado", "987456786",
                new Direccion("Lugubre", 3, "Valencia", "Cullera"));
        p2.setId(new ObjectId());

        Modulo m1 = new Modulo("Matematicas", "2", 3, p1);
        m1.setId(new ObjectId());
        Modulo m2 = new Modulo("Fisica y Quimica", "4", 5, p2);
        m2.setId(new ObjectId());
        Modulo m3 = new Modulo("Historia", "1", 2, p1);
        m3.setId(new ObjectId());
        Modulo m4 = new Modulo("Arte", "1", 5, p2);
        m4.setId(new ObjectId());

        Alumno a1 = new Alumno("Laura", "Lucena", "Parilla", "87967342D", "987432345");
        a1.addToListModulo(m2);
        a1.setId(new ObjectId());
        Alumno a2 = new Alumno("Marisa", "Mendoza", "Ripolles", "34267342D", "988762345");
        a2.addToListModulo(m1);
        a2.addToListModulo(m3);
        a2.setId(new ObjectId());
        Alumno a3 = new Alumno("Carles", "Bustean", "Prado", "87943242D", "934532345");
        a3.addToListModulo(m3);
        a3.addToListModulo(m4);
        a3.setId(new ObjectId());

        String uri = "mongodb://ec2-52-91-117-81.compute-1.amazonaws.com:27017";

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("instituto").withCodecRegistry(pojoCodecRegistry);

            ProfesorRepositoryImpl profesores = new ProfesorRepositoryImpl(database);
            AlumnoRepositoryImpl alumnos = new AlumnoRepositoryImpl(database);
            ModuloRepositoryImpl modulos = new ModuloRepositoryImpl(database);

            //Guardar Modulos
            modulos.save(m1);
            modulos.save(m2);
            modulos.save(m3);
            modulos.save(m4);

            //Mostrar todos los modulos
            modulos.findAll().forEach(System.out::println);

            //Guardar Alumnos
            alumnos.save(a1);
            alumnos.save(a2);
            alumnos.save(a3);

            //Mostrar todos los alumnos
            System.out.println();
            alumnos.findAll().forEach(System.out::println);

            //Update a un modulo
            m2.setCurso("3");
            modulos.updateById(m2);
            System.out.println();
            System.out.println(modulos.findOneById(m2.getId()));

            //Mostrar Cursos
            System.out.println();
            modulos.findByCurso("1").forEach(System.out::println);

            //Mostrar Alumnos con pendientes
            System.out.println();
            alumnos.findAllPendientes(database).forEach(System.out::println);

            //Eliminar los modulos
            modulos.deleteById(m1.getId());
            modulos.deleteById(m2.getId());
            modulos.deleteById(m3.getId());

            //Eliminar los Alumnos
            alumnos.deleteById(a1.getId());
            alumnos.deleteById(a2.getId());
            alumnos.deleteById(a3.getId());

            //Eliminar Profesores
            profesores.deleteById(p1.getId());
            profesores.deleteById(p2.getId());

            database.drop();
        }
    }
}
