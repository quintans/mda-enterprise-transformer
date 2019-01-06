# mda-enterprise-transformer
MDA Enterprise Transformer

DSL for DTO, Service, Entity models to build an enterprise application.

This is only the declaration of the model structure and generation of respective java.

This project aims to provide high level concepts often used when building an enterprise application.
Then the same concept can be reused to generate (transform) different artifacts (code, text, etc) depending the needs and guidelines (workflows and templates) of a projects.
These guidelines should be defined elsewhere, usually in the target project.
You can find an example [here](https://github.com/quintans/mda-model-example) of worflows, models and models all working together.

## Generating code

Every time we change the `src/main/xsd/*.xsd` files we need to generate the java source.
We do this with `mvn jaxb2:xjc` 

## Guide
The following instructions explain how to define the models and how to use resulting data in the templates.

### Transformers

#### FileSystemTransformer

### Entity


#### Model

#### Template