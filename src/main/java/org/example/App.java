package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.example.entities.Movie;
import org.example.entities.Personas;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        String uri = "mongodb://user:mipassword@ec2-54-146-67-141.compute-1.amazonaws.com:27017/pelis";

        // Paso 1: Query a base de datos
        // Por defecto, intentará conectar al puerto 27017
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Seleccionamos la base de datos para trabajar
            MongoDatabase database = mongoClient.getDatabase("pelis");
            // Recogemos la colección "movies" en una colección de documentos de MongoDB
            MongoCollection<Document> collection = database.getCollection("movies");
            System.out.println("La colección movies tiene " + collection.countDocuments() + " documentos");
            Document doc = collection.find(eq("title", "Back to the Future")).first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No matching documents found.");
            }
        }

        // Paso 2: Uso de CodecRegistry para mapear clases POJO a Documentos
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("pelis").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Movie> collection = database.getCollection("movies", Movie.class);
            Movie movie = collection.find(eq("title", "Back to the Future")).first();
            System.out.println(movie);
        }

        // Paso 3: Creacion de una coleccion nueva e introducir documentos.
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("pelis");
            MongoCollection<Document> collection = database.getCollection("personas");
            Document document = new Document("nombre", "Paco")
                    .append("primer_apellido", "Lorenzo")
                    .append("segundo_apellido", "Lucas")
                    .append("telefono", "789987432")
                    .append("direccion", new Document("ciudad", "Maramuses")
                            .append("calle", "Pascual Lopez")
                            .append("codigo_postal", "19234"));
            collection.insertOne(document);

            Document doc = collection.find().first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No matching documents found.");
            }
        }

        // Paso 4: Uso de CodecRegistry para mapear clases POJO a Documentos.
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("pelis").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Personas> collection = database.getCollection("personas", Personas.class);
            Personas persona = collection.find().first();
            persona.setTelefono("987432432");
            collection.replaceOne(eq("nombre", "Paco"), persona);
            System.out.println(persona);
        }

        // Paso 5: Eliminar la persona creada anterior mente.
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("pelis");
            MongoCollection<Document> collection = database.getCollection("personas");
            collection.deleteOne(eq("nombre", "Paco"));

            Document doc = collection.find().first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No matching documents found.");
            }
        }
    }
}
