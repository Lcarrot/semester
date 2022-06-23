package ru.lanya.factory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.ResourceResolver;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import ru.lanya.service.ApiServiceImpl;

@Factory
public class GraphqlFactory {

  @Bean
  @Singleton
  public GraphQL graphQL(ResourceResolver resourceResolver, ApiServiceImpl apiService) {
    SchemaParser schemaParser = new SchemaParser();

    TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
    Optional<InputStream> graphqlSchema = resourceResolver.getResourceAsStream("classpath:schema.graphqls");
    if (graphqlSchema.isPresent()) {
      typeRegistry.merge(schemaParser.parse(new BufferedReader(new InputStreamReader(graphqlSchema.get()))));
      RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
          .type(TypeRuntimeWiring.newTypeWiring("Query")
              .dataFetcher("recipeByName", apiService.getRecipesByName()))
          .build();
      SchemaGenerator schemaGenerator = new SchemaGenerator();
      GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
      return GraphQL.newGraphQL(graphQLSchema).build();
    } else {
      return new GraphQL.Builder(GraphQLSchema.newSchema().build()).build();
    }
  }
}
