# mda-enterprise-transformer
MDA Enterprise Transformer

DSL for DTO, Service, Entity models to build an enterprise application.

This is only the declaration of the model structure and generation of respective java.

## TODO
A template project is needed (To be added in the future).


## Generating code

Every time we change the `src/main/xsd/*.xsd` files we need to generate the java source.
We do this with:<br>
`mvn jaxb2:xjc` 