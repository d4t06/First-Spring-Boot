### Annotations
- @Controller to mark the class it self as a controller
- @Service to mark the class it self as a service
- @RequestMapping to indicate what url path will be serve by controller (like route.use() in Express or resource name in Nest)
    - @GetMapping... ("") ("/{id}")
    - @PostMapping
    - @PutMapping
    - @DeleteMapping
    - @PatchMapping
#### Handler methods
    - @PathVariable (param)
    - @RequestParam (query)
    - @RequestBody

### PK Generation Strategies
- Auto
- IDENTIFY
- SEQUENCE
- TABLE


java -jar target/*.jar

docker build -t d4t06/spring-mobile:latest .