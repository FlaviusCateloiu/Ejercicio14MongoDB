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
        Profesor p2 = new Profesor("Lucas", "Gimeno", "Prado", "987456786",
                new Direccion("Lugubre", 3, "Valencia", "Cullera"));

        Alumno a1 = new Alumno("Laura", "Lucena", "Parilla", "87967342D", "987432345");
        Alumno a2 = new Alumno("Marisa", "Mendoza", "Ripolles", "34267342D", "988762345");
        Alumno a3 = new Alumno("Carles", "Bustean", "Prado", "87943242D", "934532345");

        Modulo m1 = new Modulo("Matematicas", "2", 3, p1);
        m1.addToListAlumno(a1);
        Modulo m2 = new Modulo("Fisica y Quimica", "4", 5, p2);
        m2.addToListAlumno(a1);
        m2.addToListAlumno(a3);
        Modulo m3 = new Modulo("Historia", "1", 2, p1);
        m3.addToListAlumno(a2);


        String uri = "mongodb://ec2-54-234-15-174.compute-1.amazonaws.com:27017";

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("instituto").withCodecRegistry(pojoCodecRegistry);

            ProfesorRepositoryImpl profesores = new ProfesorRepositoryImpl(database);
            AlumnoRepositoryImpl alumnos = new AlumnoRepositoryImpl(database);
            ModuloRepositoryImpl modulos = new ModuloRepositoryImpl(database);

            //Guardar Profesores
            profesores.save(p1);
            profesores.save(p2);

            //Guardar Alumnos
            alumnos.save(a1);
            alumnos.save(a2);
            alumnos.save(a3);

            //Guardar Modulos
            modulos.save(m1);
            modulos.save(m2);
            modulos.save(m3);

            //Mostrar todos los modulos
            modulos.findAll().forEach(System.out::println);

            //Eliminar los modulos
            modulos.deleteById(m1.getId());
            modulos.deleteById(m2.getId());
            modulos.deleteById(m3.getId());

            //Eliminar los Alumnos
            alumnos.deleteById(a1.getId());
            alumnos.deleteById(a2.getId());
            alumnos.deleteById(a3.getId());

            //Elminar Profesores
            profesores.deleteById(p1.getId());
            profesores.deleteById(p2.getId());
        }
    }
}
