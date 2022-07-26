# Modify Json Schemas

## Description
I was looking for a way to generate json schema's from xsd files. After checking out a few projects and generators 
I found online, I ended up with the [Jsonix Schema Compiler](https://github.com/highsource/jsonix-schema-compiler)
This was the closes match I found, but with a couple  of side-notes.

 - It uses Jsonix schema definitions instead of plain json definitions. <br />
![img.png](img.png)
 - Generates a more complex local type structure<br />
![img_1.png](img_1.png)
 - No default json schema is added <br />
![img_2.png](img_2.png)
 - The closing anyOff selector is not applicable for my case and more complex than should be<br />
![img_3.png](img_3.png)

It does generate and inline all xsd dependencies, which is a nice touch.<br />

## Details
In order to make the generated jsonix schemas usable, I needed to do some small changes
 - Replace jsonix schema definitions with plain json schema definitions<br />
![img_6.png](img_6.png)
 - Replace the final anyOff selector with a proper allOf selector<br />
![img_5.png](img_5.png)
 - Add default json schema to the beginning of the file<br />
![img_7.png](img_7.png)

## Usage
After running the jsonix compile

`java -jar jsonix-schema-compiler-full-2.3.9.jar -compact -generateJsonSchema [Input XSD Schema] -d [Output directory] -p [Schema Object]
`

(more info on cli can be found on [GitHub](https://github.com/highsource/jsonix-schema-compiler/wiki/Command-Line-Usage))
You run the UpdateJsonSchema code:

`#java -jar .\ModifyJsonSchema.jar [input file path] [Json schema object]`<br />
`java -jar .\ModifyJsonSchema.jar C:\Matthias\jsonschema\VisitTruck.jsonix.schema.json VisitTruck` 

A file is generated under "[inputFolder]\generated\" with the name "[json schema object].schema.json"

## Resources 
Jsonix Schema compiler: Generating json schema's from xsd's.<br />
[Jsonix schema compiler - GitHub home](https://github.com/highsource/jsonix-schema-compiler)

Jsonix Schema compiler: command line interface and manual. <br />
[Jsonix command line](https://github.com/highsource/jsonix-schema-compiler/wiki/Command-Line-Usage)

JSON Schema Faker: generate example date from json schemas <br />
[Json Schema faker - Generate json files from a json schema](https://json-schema-faker.js.org/)

JSON Schema Validator: Validating JSON schemas on their own and validating data against a JSON Schema. <br />
[Json Schema Validator](https://www.jsonschemavalidator.net/)

JSON Schema Validator - 2: Validating JSON schemas on their own and validating data against a JSON Schema. <br />
[JSON Schema Validator](https://json-schema-validator.herokuapp.com/)

JSON Parser: parsing json data (also validates json structure) <br />
[JSON Parser](https://codebeautify.org/json-parser-online)

XML to JSON Converter: converting xml messages to json <br />
[XML to JSON Converter](https://codebeautify.org/xmltojson)

IntelliJ plugin - JSON to Schema: create json schemas from json input file <br />
[JSON to Schema](https://plugins.jetbrains.com/plugin/17611-json-to-schema)

IntelliJ plugin - XSD to JSON Schema: generate a JSON schema from an XSD input file <br />
[XSD to JSON Schema](https://plugins.jetbrains.com/plugin/19024-xsd-to-json-schema)